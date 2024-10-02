package com.mecanico.mecanicoapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ViewFlipper;

import com.google.android.material.tabs.TabItem;
import com.google.android.material.tabs.TabLayout;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.GoogleAuthProvider;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;



public class InicioActivity extends AppCompatActivity {

    TabLayout tabLayout;
    ViewPager viewPager;
    TabItem tabhome, tabmotors, tabpedidos;
    PagerController pagerController;
    private ImageView mPerfil;

    ViewFlipper v_flipper;
    private ImageView mBack;
    FirebaseAuth mAuth;
    DatabaseReference mDatebase;
    TextView mName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inicio);


        tabLayout = findViewById(R.id.tablayout);
        viewPager = findViewById(R.id.viewpager);


        tabhome = findViewById(R.id.tabhome);
        tabmotors = findViewById(R.id.tabmotors);
        tabpedidos = findViewById(R.id.tabpedidos);
        mAuth = FirebaseAuth.getInstance();
        mName = findViewById(R.id.id_name);
        mDatebase = FirebaseDatabase.getInstance().getReference();


        mPerfil = findViewById(R.id.btn_profile);

        mPerfil.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(InicioActivity.this, PerfilActivity.class));
                finish();
            }
        });


        pagerController = new PagerController(getSupportFragmentManager(), tabLayout.getTabCount());
        viewPager.setAdapter(pagerController);
        tabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition());
                if (tab.getPosition() == 0) {
                    pagerController.notifyDataSetChanged();
                }

                if (tab.getPosition() == 1) {
                    pagerController.notifyDataSetChanged();
                }

            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
    }

    private void getUserInfo() {
        final String id = mAuth.getCurrentUser().getUid();

        mDatebase.child("Users").child(id).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    try {
                        String name = dataSnapshot.child("name").getValue().toString();
                        String apel = dataSnapshot.child("apel").getValue().toString();

                        mName.setText(name + " " + apel);

                    } catch (Exception e) {
                        startActivity(new Intent(InicioActivity.this, Datos.class));

                        try {
                            throw e;
                        } catch (Exception ex) {
                            throw new RuntimeException(ex);
                        }
                    }

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    public void getGoogleCredentials() {
        String googleIdToken = "";
        // [START auth_google_cred]
        AuthCredential credential = GoogleAuthProvider.getCredential(googleIdToken, null);
        // [END auth_google_cred]
    }

    @Override
    protected void onStart() {
        super.onStart();

        try {
            DataEmpty();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        getUserInfo();

    }

    private void DataEmpty() throws Exception {
        final String id = mAuth.getCurrentUser().getUid();

        try {
            mDatebase.child("Users").child(id).addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    if (snapshot.hasChild("name")){
                        Toast.makeText(InicioActivity.this,"Todo en orden",Toast.LENGTH_SHORT).show();

                    }else{
                        startActivity(new Intent(InicioActivity.this,Datos.class));
                    }

                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });
        }catch(Exception e){

            try {
                throw e;
            } catch (Exception ex) {
                throw new RuntimeException(ex);
            }
        }

    }



    // mAuth.signOut;
}
