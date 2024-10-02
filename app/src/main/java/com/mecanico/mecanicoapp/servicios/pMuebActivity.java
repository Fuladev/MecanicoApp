package com.mecanico.mecanicoapp.servicios;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.mecanico.mecanicoapp.InicioActivity;
import com.mecanico.mecanicoapp.R;
import com.mecanico.mecanicoapp.recursos.SpinnerAdapter;
import com.mecanico.mecanicoapp.recursos.SpinnerDirections;
import com.mecanico.mecanicoapp.recursos.SpinnerItem;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

public class pMuebActivity extends AppCompatActivity {

    private String currentDate = new SimpleDateFormat("dd/MM/yy", Locale.getDefault()).format(new Date());
    private String currentTime = new SimpleDateFormat("HH:mm:ss", Locale.getDefault()).format(new Date());

    private String dir, marca, des;
    private String date, Uid, Did;
    private String name, apel, cel, mail,ciu,barr;
    private String clickeadSpinnerName = "";
    private Boolean op = false;
    private ImageView BtnEdit;

    private EditText dirText;
    private Button mSend;
    private EditText mProblem;
    private Button mCancel;
    private Spinner sp_marc;
    private Spinner sp_dir;
    private TextView etDir;
    private ArrayList<SpinnerItem> mSpinnerList;
    private SpinnerAdapter mAdapter;

    FirebaseAuth mAuth;
    DatabaseReference mDatebase;

    private ImageView mBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_p_mueb);


        sp_marc = findViewById(R.id.sp_marcas);
        sp_dir = findViewById(R.id.sp_dir);
        etDir = findViewById(R.id.dir_txt);
        mSend = findViewById(R.id.btn_send);
        mCancel = findViewById(R.id.btn_cancel);
        mProblem = findViewById(R.id.txt_problem);


        BtnEdit = findViewById(R.id.btn_newdir);

        BtnEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


            }
        });

        mSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                des = mProblem.getText().toString();


                if (!name.isEmpty() && !apel.isEmpty()  && dir != null && !des.isEmpty() && !cel.isEmpty() && !mail.isEmpty()) {
                    registrarPedido();

                } else {
                    Toast.makeText(pMuebActivity.this, "Debe llenar todos los campos", Toast.LENGTH_SHORT).show();

                }

            }
        });

        mCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(pMuebActivity.this, InicioActivity.class));
                Toast.makeText(pMuebActivity.this, "Pedido cancelado", Toast.LENGTH_SHORT).show();
                finish();
            }
        });

        mBack = findViewById(R.id.back_btn);
        mBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent back = new Intent(pMuebActivity.this, InicioActivity.class);
                startActivity(back);
            }
        });


        mAuth = FirebaseAuth.getInstance();
        mDatebase = FirebaseDatabase.getInstance().getReference();

        getInfo();

        loadDirections();


    }


    public void loadDirections() {
        String userID = mAuth.getUid();
        final List<SpinnerDirections> directionsList = new ArrayList<>();
        mDatebase.child("Users").child(userID).child("Direcciones").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()){
                    for(DataSnapshot ds: snapshot.getChildren()){
                        String id = ds.getKey();
                        String name = ds.child("name").getValue().toString();
                        String dir = ds.child("dirComplete").getValue().toString();

                        directionsList.add(new SpinnerDirections(id,name,dir));
                    }

                    ArrayAdapter<SpinnerDirections> arrayAdapter = new ArrayAdapter<>(pMuebActivity.this, android.R.layout.simple_dropdown_item_1line, directionsList);
                    sp_dir.setAdapter(arrayAdapter);
                    sp_dir.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                            dir = directionsList.get(position).getDir();
                            Did = directionsList.get(position).getDirId();
                            etDir.setText(dir);
                        }

                        @Override
                        public void onNothingSelected(AdapterView<?> parent) {

                        }
                    });
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void getInfo() {
        String id = mAuth.getCurrentUser().getUid();

        //  Info personal
        mDatebase.child("Users").child(id).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    name = dataSnapshot.child("name").getValue().toString();
                    apel = dataSnapshot.child("apel").getValue().toString();
                    cel = dataSnapshot.child("phone").getValue().toString();
                    mail = dataSnapshot.child("mail").getValue().toString();
                    Uid = dataSnapshot.getKey();

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        System.out.println("Dir ID: " + Did);

        //  Info dirección
        if (Did != null){
            mDatebase.child("Users").child(id).child("Direcciones").child(Did).addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    barr = snapshot.child("barrio").getValue().toString();
                    ciu = snapshot.child("ciudad").getValue().toString();
                    dir = snapshot.child("dirComplete").getValue().toString();

                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });
        }

    }

    private void registrarPedido() {
        String currentDate = new SimpleDateFormat("dd/MM/yy", Locale.getDefault()).format(new Date());
        String currentTime = new SimpleDateFormat("HH:mm:ss", Locale.getDefault()).format(new Date());
        date = currentDate + " / " + currentTime;

        Map<String, Object> map = new HashMap<>();
        map.put("name", name);
        map.put("apel", apel);
        map.put("dir", dir);
        map.put("ciu", ciu);
        map.put("barr", barr);
        map.put("cel", cel);
        map.put("des", des);
        map.put("marca", marca);
        map.put("mail", mail);
        map.put("Tipo", "Mueb.");
        map.put("date", date);
        map.put("id", Uid);
        map.put("mecanico", "No se ha asignado un mecanico");
        map.put("state", "Processing");

        ConnectivityManager connectivity = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivity.getActiveNetworkInfo();

        if (networkInfo!=null && networkInfo.isConnected()){
            String id = mAuth.getCurrentUser().getUid();
            mDatebase.child("Pedido").child(id).push().setValue(map).addOnSuccessListener(new OnSuccessListener<Void>() {
                @Override
                public void onSuccess(Void aVoid) {
                    Toast.makeText(pMuebActivity.this, "En breve se le asignara un Mecanico", Toast.LENGTH_SHORT).show();
                    finish();
                }
            });
            mDatebase.child("Pedidos").push().setValue(map).addOnSuccessListener(new OnSuccessListener<Void>() {
                @Override
                public void onSuccess(Void aVoid) {
                    Toast.makeText(pMuebActivity.this, "En breve se le asignara un Mecanico", Toast.LENGTH_SHORT).show();
                    finish();
                }
            });
        }else {
            Toast.makeText(this, "No es posible efectuar su solicitud, revice su conexion a internet", Toast.LENGTH_SHORT).show();
        }

    }
}
