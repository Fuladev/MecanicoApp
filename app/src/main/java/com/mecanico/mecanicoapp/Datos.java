package com.mecanico.mecanicoapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.database.core.Tag;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class Datos extends AppCompatActivity {

    private String email;
    private String uid;

    DatabaseReference mDatabase;
    FirebaseAuth mAuth;

    private EditText mEtName, mEtApel, mEtDir, mEtBarr, mEtNumT, mEtMail, mEtCasa, mEtNDir;
    private Spinner mEtDep, mEtCiu, mEtPais;
    private Button mBtnSave, mBtnCancel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_datos);

        mDatabase = FirebaseDatabase.getInstance().getReference();
        mAuth = FirebaseAuth.getInstance();

        uid = mAuth.getCurrentUser().getUid();
        email = mAuth.getCurrentUser().getEmail();

        mEtName = findViewById(R.id.et_name);
        mEtApel = findViewById(R.id.et_apel);
        mEtDir = findViewById(R.id.et_dir);
        mEtBarr = findViewById(R.id.et_barr);
        mEtNumT = findViewById(R.id.et_numt);
        mEtMail = findViewById(R.id.et_mail);
        mEtDep = findViewById(R.id.sp_dep);
        mEtCiu = findViewById(R.id.sp_ciu);
        mEtPais = findViewById(R.id.sp_pais);
        mEtCasa = findViewById(R.id.et_casa);
        mEtNDir = findViewById(R.id.et_ndir);

        mBtnSave = findViewById(R.id.btn_save);
        mBtnCancel = findViewById(R.id.btn_cancel);

        mEtMail.setText(email);



        mBtnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DataSave();
            }
        });

        mBtnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mAuth.signOut();
                startActivity(new Intent(Datos.this,MainActivity.class));
            }
        });




    }
    private static final String CARACTERES = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
    private static final int LONGITUD_ID = 20;


    String generarID() {
        Random random = new Random();
        StringBuilder idBuilder = new StringBuilder();

        for (int i = 0; i < LONGITUD_ID; i++) {
            int indice = random.nextInt(CARACTERES.length());
            char caracter = CARACTERES.charAt(indice);
            idBuilder.append(caracter);
        }

        return idBuilder.toString();
    }

    private void DataSave() {
        Map<String, Object> data = new HashMap<>();
        data.put("name",mEtName.getText().toString());
        data.put("apel",mEtApel.getText().toString());
        data.put("mail",mEtMail.getText().toString());
        data.put("phone",mEtNumT.getText().toString());
        data.put("Direcciones",null);

        Map<String, Object> address = new HashMap<>();
        address.put("barrio",mEtBarr.getText().toString());
        address.put("casa",mEtCasa.getText().toString());
        address.put("ciudad",mEtCiu.getSelectedItem().toString().toString());
        address.put("departamento",mEtDep.getSelectedItem().toString());
        Object DirC = mEtDir + " " + mEtBarr + " " + mEtCasa;
        address.put("dirComplete",DirC.toString());
        address.put("direccion",mEtDir.getText());
        address.put("name",mEtNDir.getText().toString());
        address.put("pais",mEtPais.getSelectedItem().toString());

        Log.d(null,uid.toString());
        Log.d(null,email);


        mDatabase.child("Users").child(uid).setValue(data).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()){
                    mDatabase.child("Users").child(uid).child("Direcciones").child(generarID()).setValue(address).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()){
                                Toast.makeText(Datos.this, "Se guardaron los datos correctamente", Toast.LENGTH_SHORT).show();
                                startActivity(new Intent(Datos.this,InicioActivity.class));

                            }else {
                                Toast.makeText(Datos.this, "No se guardaron los datos correctamente", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                }else {
                    Toast.makeText(Datos.this, "No se guardaron los datos correctamente", Toast.LENGTH_SHORT).show();
                }
            }
        });


    }
}