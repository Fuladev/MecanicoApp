#!/usr/bin/env node
/**
 * Limpieza de datos de Realtime Database (punto 6 del reporte pre-migración).
 *
 * Por defecto NO escribe nada: muestra lo que cambiaría. Para aplicar los cambios: --apply
 *
 * Requiere una clave de cuenta de servicio del proyecto mecanico-2d3de:
 *   export GOOGLE_APPLICATION_CREDENTIALS=/ruta/a/clave.json
 *   npm install && npm run limpieza            # simulación
 *   npm run limpieza:aplicar                    # aplica
 *
 * Hace una copia de seguridad (backup-<fecha>.json) de Users, Pedido y Pedidos antes de aplicar.
 *
 * Qué corrige:
 *  1. Borra Users/{uid}/pass (contraseñas en texto plano).
 *  2. Direcciones sin `name` → "Principal" / "Dirección N".
 *  3. `dirComplete` dañado por la pantalla Datos (texto "androidx...EditText{...}") → se rehace.
 *  4. Tipo "Comp." → "Equipos" en Pedidos y Pedido/{uid}.
 *  5. Agrega `createdAt` (ms) a los pedidos que no lo tienen, calculado desde `date` (hora de Colombia).
 *
 * Qué NO puede corregir (se lista para revisión manual):
 *  - Pedidos de Belleza / Máquina de coser / Maquinaria pesada guardados como "Lav.": no hay forma de
 *    distinguirlos de una lavadora real. Se listan los "Lav." cuya marca no es una marca de electrodomésticos.
 */
import { writeFileSync } from 'node:fs';
import { applicationDefault, initializeApp } from 'firebase-admin/app';
import { getDatabase } from 'firebase-admin/database';

const APPLY = process.argv.includes('--apply');
const DATABASE_URL = process.env.FIREBASE_DATABASE_URL ?? 'https://mecanico-2d3de.firebaseio.com';
const ELECTRO_BRANDS = new Set(['LG', 'Samsung', 'Whirlpool', 'Centrales', 'Mabe', 'Haceb', 'Electrolux', 'Abba', 'Icasa', 'Indurama', 'Otra']);

initializeApp({ credential: applicationDefault(), databaseURL: DATABASE_URL });
const db = getDatabase();

const broken = (s) => typeof s === 'string' && /android(x)?\.|widget\.|AppCompatEditText|@[0-9a-f]{5,}/i.test(s);
const clean = (s) => (typeof s === 'string' ? s.trim().replace(/\s+/g, ' ') : '');
const dirComplete = (a) => [clean(broken(a.direccion) ? '' : a.direccion), clean(a.casa), clean(a.barrio), clean(a.ciudad)].filter(Boolean).join(', ');

/** "dd/MM/yy / HH:mm:ss" en hora de Colombia (UTC-5, sin horario de verano) → ms */
function parseLegacyDate(s) {
  const m = /^(\d{2})\/(\d{2})\/(\d{2})\s*\/\s*(\d{2}):(\d{2}):(\d{2})$/.exec(String(s ?? '').trim());
  if (!m) return null;
  const [, dd, mm, yy, hh, mi, ss] = m.map(Number);
  return Date.UTC(2000 + yy, mm - 1, dd, hh + 5, mi, ss);
}

const [usersSnap, pedidoSnap, pedidosSnap] = await Promise.all(['Users', 'Pedido', 'Pedidos'].map((p) => db.ref(p).get()));
const users = usersSnap.val() ?? {};
const pedido = pedidoSnap.val() ?? {};
const pedidos = pedidosSnap.val() ?? {};

const updates = {};
const stats = { pass: 0, dirName: 0, dirComplete: 0, comp: 0, createdAt: 0 };
const revisar = [];

for (const [uid, u] of Object.entries(users)) {
  if (u && 'pass' in u) {
    updates[`Users/${uid}/pass`] = null;
    stats.pass++;
  }
  Object.entries(u?.Direcciones ?? {}).forEach(([dirId, d], i) => {
    if (!d || typeof d !== 'object') return;
    if (!clean(d.name)) {
      updates[`Users/${uid}/Direcciones/${dirId}/name`] = i === 0 ? 'Principal' : `Dirección ${i + 1}`;
      stats.dirName++;
    }
    if (broken(d.dirComplete) || broken(d.direccion) || !clean(d.dirComplete)) {
      updates[`Users/${uid}/Direcciones/${dirId}/dirComplete`] = dirComplete(d);
      if (broken(d.direccion)) updates[`Users/${uid}/Direcciones/${dirId}/direccion`] = '';
      stats.dirComplete++;
    }
  });
}

function fixPedido(path, p) {
  if (!p || typeof p !== 'object') return;
  if (p.Tipo === 'Comp.') {
    updates[`${path}/Tipo`] = 'Equipos';
    updates[`${path}/servicio`] = 'computadores';
    stats.comp++;
  }
  if (typeof p.createdAt !== 'number') {
    const t = parseLegacyDate(p.date);
    if (t) {
      updates[`${path}/createdAt`] = t;
      stats.createdAt++;
    }
  }
  if (p.Tipo === 'Lav.' && p.marca && !ELECTRO_BRANDS.has(String(p.marca).trim())) {
    revisar.push({ path, marca: p.marca, des: p.des });
  }
}

for (const [id, p] of Object.entries(pedidos)) fixPedido(`Pedidos/${id}`, p);
for (const [uid, list] of Object.entries(pedido)) for (const [id, p] of Object.entries(list ?? {})) fixPedido(`Pedido/${uid}/${id}`, p);

console.log(APPLY ? 'MODO: APLICAR' : 'MODO: SIMULACIÓN (usa --apply para escribir)');
console.log('Cambios:', stats, `→ ${Object.keys(updates).length} rutas`);
if (revisar.length) {
  console.log(`\n${revisar.length} pedidos "Lav." que podrían ser Belleza / Coser / Maquinaria (revisar a mano):`);
  for (const r of revisar.slice(0, 50)) console.log(` - ${r.path}  marca="${r.marca}"  des="${String(r.des ?? '').slice(0, 60)}"`);
}

if (APPLY && Object.keys(updates).length) {
  const file = `backup-${new Date().toISOString().replace(/[:.]/g, '-')}.json`;
  writeFileSync(file, JSON.stringify({ Users: users, Pedido: pedido, Pedidos: pedidos }));
  console.log(`\nCopia de seguridad: ${file}`);
  await db.ref().update(updates);
  console.log('Listo.');
}
process.exit(0);
