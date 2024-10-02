package com.mecanico.mecanicoapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class PerfilActivity extends AppCompatActivity  {
    private TextView mTexName, mTexMail, mTextApel, mTextPhone;
    private FirebaseAuth mAuth;
    private Button mSingOut;
    private ImageView mBack;
    private DatabaseReference mDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_perfil);

        mAuth = FirebaseAuth.getInstance();
        mDatabase = FirebaseDatabase.getInstance().getReference();
        mSingOut = findViewById(R.id.sign_out);
        mBack = findViewById(R.id.back_btn);
        mTexName = findViewById(R.id.tx_name);
        mTextApel = findViewById(R.id.tx_apel);
        mTexMail = findViewById(R.id.tx_mail);
        mTextPhone = findViewById(R.id.tx_cel);

        getUserInfo();

        mBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(PerfilActivity.this,InicioActivity.class));
            }
        });

        mSingOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mAuth.signOut();
                startActivity(new Intent(PerfilActivity.this, MainActivity.class));
                finish();
            }
        });
    }



    private void getUserInfo() {
        String id = mAuth.getCurrentUser().getUid();
        mDatabase.child("Users").child(id).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    String name = dataSnapshot.child("name").getValue().toString();
                    String apel = dataSnapshot.child("apel").getValue().toString();
                    String mail = dataSnapshot.child("mail").getValue().toString();
                    String phone = dataSnapshot.child("phone").getValue().toString();


                    mTexName.setText(name);
                    mTextApel.setText(apel);
                    mTexMail.setText(mail);
                    mTextPhone.setText(phone);

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }


}
