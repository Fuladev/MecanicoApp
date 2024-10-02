package com.mecanico.mecanicoapp;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;


import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Map;


public class SelectorLogActivity extends AppCompatActivity {




    // [START declare_auth]
    private FirebaseAuth mAuth;
    private FirebaseDatabase database;
    private GoogleSignInClient googleSignInClient;
    private static final int RC_SIGN_IN = 2;

    private static final String TAG = "GoogleActivity";

    // [END declare_auth]

    //private Button mBtnFaceLog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_selector_log);

        mAuth = FirebaseAuth.getInstance();
        database = FirebaseDatabase.getInstance();


        Button mBtnLog = findViewById(R.id.btn_login);
       // mBtnFaceLog = findViewById(R.id.btn_facebook);
        Button mBtnCreateCount = findViewById(R.id.btn_createcount);
        Button mBtnGoogleSign = findViewById(R.id.btn_google);

        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                        .requestEmail()
                                .build();


        googleSignInClient = GoogleSignIn.getClient(this,gso);

        googleSignInClient.signOut();


        mBtnGoogleSign.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = googleSignInClient.getSignInIntent();
                startActivityForResult(i,RC_SIGN_IN);
            }
        });

        mBtnLog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(SelectorLogActivity.this,MainActivity.class));
            }
        });



        mBtnCreateCount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(SelectorLogActivity.this,CreateCountActivity.class));
            }
        });



    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable @org.jetbrains.annotations.Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);


        if (requestCode == RC_SIGN_IN){

            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            try {
                GoogleSignInAccount account = task.getResult(ApiException.class);
                Log.d(TAG,"firebaseAuth:" + account.getId());
                firebaseAuth(account.getIdToken());
            }catch (ApiException e){
                Log.w(TAG, "Google sign in failed", e);
            }

        }
    }

    private void firebaseAuth(String idToken) {
        AuthCredential credential = GoogleAuthProvider.getCredential(idToken,null);

        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()){
                            Log.d(TAG, "signInWithCredential:success");
                            FirebaseUser user = mAuth.getCurrentUser();
                            updateUI(user);

                            Map<String,Object> users = new HashMap<>();
                            assert user != null;
                            users.put("name","");
                            users.put("apel","");
                            users.put("mail",user.getEmail());
                            users.put("phone","");




                            database.getReference().child("Users").child(user.getUid()).setValue(users);

                            startActivity(new Intent(SelectorLogActivity.this,InicioActivity.class));

                        }else {
                            Log.w(TAG, "signInWithCredential:failure", task.getException());
                            updateUI(null);
                        }
                    }
                });
    }

    @Override
    protected void onStart() {
        super.onStart();

        FirebaseUser currentUser = mAuth.getCurrentUser();
        updateUI(currentUser);
    }

    private void updateUI(FirebaseUser currentUser) {

    }
}
