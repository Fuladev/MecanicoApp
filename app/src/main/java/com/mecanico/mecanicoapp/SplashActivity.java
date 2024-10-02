package com.mecanico.mecanicoapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.Toast;

public class SplashActivity extends AppCompatActivity {
    private final int DURACION_SPLASH = 3000;
    private Button mBtnReload;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        mBtnReload=findViewById(R.id.btn_reload);
        mBtnReload.setVisibility(View.INVISIBLE);
        mBtnReload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                conected();
            }
        });

        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_UNSPECIFIED);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.activity_splash);

        conected();

    }

    public void conected(){
        new Handler().postDelayed(new Runnable() {
            public void run() {

                ConnectivityManager connectivity = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
                assert connectivity != null;
                NetworkInfo networkInfo = connectivity.getActiveNetworkInfo();
                mBtnReload.setVisibility(View.INVISIBLE);

                if (networkInfo!=null && networkInfo.isConnected()){
                    Intent intent = new Intent(SplashActivity.this, StartHomeActivity.class);
                    startActivity(intent);
                    finish();
                }else {
                    Toast.makeText(SplashActivity.this, "No se pudo conectar, verifique el acceso a internet e intente nuevante", Toast.LENGTH_LONG).show();
                    mBtnReload.setVisibility(View.VISIBLE);
                }

            }

            ;
        }, DURACION_SPLASH);
    }
}
