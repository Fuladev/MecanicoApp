package com.mecanico.mecanicoapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class pruebaActivity extends AppCompatActivity {

    Button btn ;
    TextView txt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_prueba);

        btn = findViewById(R.id.btn_next);
        txt = findViewById(R.id.txt_boto);

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                txt.setText("Hola");
            }
        });
    }
}
