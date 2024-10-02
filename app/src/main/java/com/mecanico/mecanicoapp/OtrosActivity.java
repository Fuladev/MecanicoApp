package com.mecanico.mecanicoapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.mecanico.mecanicoapp.servicios.pBellezaActivity;
import com.mecanico.mecanicoapp.servicios.pCompActivity;
import com.mecanico.mecanicoapp.servicios.pCoserActivity;
import com.mecanico.mecanicoapp.servicios.pDoctorActivity;
import com.mecanico.mecanicoapp.servicios.pObrasActivity;

public class OtrosActivity extends AppCompatActivity {

    private Button btnCoser, btnBelleza, btnObras, btnPc, btnDoctor;
    private ImageView mBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_otros);

        btnCoser = findViewById(R.id.btn_coser);
        btnBelleza = findViewById(R.id.btn_belleza);
        btnObras = findViewById(R.id.btn_obras);
        btnPc = findViewById(R.id.btn_pc);
        btnDoctor = findViewById(R.id.btn_doctor);

        mBack = findViewById(R.id.back_btn);

        mBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent back = new Intent(OtrosActivity.this, InicioActivity.class);
                startActivity(back);
            }
        });

        btnCoser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(OtrosActivity.this, pCoserActivity.class));
            }
        });

        btnBelleza.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(OtrosActivity.this, pBellezaActivity.class));
            }
        });

        btnObras.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(OtrosActivity.this, pObrasActivity.class));
            }
        });

        btnPc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(OtrosActivity.this, pCompActivity.class));
            }
        });

        btnDoctor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(OtrosActivity.this, pDoctorActivity.class));
            }
        });
    }
}
