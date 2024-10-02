package com.mecanico.mecanicoapp.servicios;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.mecanico.mecanicoapp.R;
import com.mecanico.mecanicoapp.recursos.SpinnerAdapter;
import com.mecanico.mecanicoapp.recursos.SpinnerItem;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

public class pLavAutosActivity extends AppCompatActivity {


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
        setContentView(R.layout.activity_p_lav_autos);


    }
}
