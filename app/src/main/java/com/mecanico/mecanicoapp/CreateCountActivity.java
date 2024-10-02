package com.mecanico.mecanicoapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

public class CreateCountActivity extends AppCompatActivity {

    private static final Pattern PASSWORD_PATTERN =
            Pattern.compile("^" +
                    // "(?=.*[0-9])" +         //at least 1 digit
                    // "(?=.*[a-z])" +         //at least 1 lower case letter
                    //   "(?=.*[A-Z])" +         //at least 1 upper case letter
                    //    "(?=.*[a-zA-Z])" +      //any letter
                    "(?=.*[@#$%^&+=])" +    //at least 1 special character
                    "(?=\\S+$)" +           //no white spaces
                    ".{6,}" +               //at least 8 characters
                    "$");
    private EditText mEtName, mEtApel, mEtDir, mEtBarr, mEtNumT, mEtMail, mEtPass, mEtCpass;
    private Spinner mEtDep, mEtCiu, mEtPais;
    private ImageView mBack;
    private Button mBtnCreate;

    private RequestQueue requestQueue;

    private String apiToken = "Bm5Md1r9wSzwK625mAz5vym64TeSmfNCPvyNVBd0wHYJ4oqXbPB9jxeE8rCnUsWH-tM";
    private String userEmail = "diego040413223@gmail.com";

    private String accessToken;


    //Variables q vamos a registrar
    private String name, apel, pais, dep, ciu, dir, barr, numt, mail, pass, cpass;

    FirebaseAuth mAuth;
    DatabaseReference mDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_count);



        mBack = findViewById(R.id.back_btn);
        mBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent back = new Intent(CreateCountActivity.this, MainActivity.class);
                startActivity(back);
            }
        });
        mAuth = FirebaseAuth.getInstance();
        mDatabase = FirebaseDatabase.getInstance().getReference();


        mEtName = findViewById(R.id.et_name);
        mEtApel = findViewById(R.id.et_apel);
        mEtPais = findViewById(R.id.sp_pais);
        mEtDep = findViewById(R.id.sp_dep);
        mEtDir = findViewById(R.id.et_dir);
        mEtCiu = findViewById(R.id.sp_ciu);
        mEtBarr = findViewById(R.id.et_barr);
        mEtNumT = findViewById(R.id.et_numt);
        mEtMail = findViewById(R.id.et_mail);
        mEtPass = findViewById(R.id.et_pass);
        mEtCpass = findViewById(R.id.et_cpass);

        mBtnCreate = findViewById(R.id.btn_create);

        mBtnCreate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                name = mEtName.getText().toString();
                apel = mEtApel.getText().toString();
                pais = mEtPais.getSelectedItem().toString();
                dep = mEtDep.getSelectedItem().toString();
                ciu = mEtCiu.getSelectedItem().toString();
                dir = mEtDir.getText().toString();
                barr = mEtBarr.getText().toString();
                numt = mEtNumT.getText().toString();
                mail = mEtMail.getText().toString();
                pass = mEtPass.getText().toString();
                cpass = mEtCpass.getText().toString();

                if (!name.isEmpty() && !apel.isEmpty() && !dep.isEmpty() && !ciu.isEmpty() && !dir.isEmpty() && !barr.isEmpty() && !numt.isEmpty() && validateEmail(mail) && validatePassword(pass, cpass) && !cpass.isEmpty()) {
                    registerUser();
                } else {
                    Toast.makeText(CreateCountActivity.this, "Hay campos que no estan correctos o vacios", Toast.LENGTH_SHORT).show();

                }
            }

        });

        // Implementacion de la api de las ciudades
        requestQueue = Volley.newRequestQueue(this);
        getAccessToken();
        System.out.println("Token de acceso: " + accessToken);

    }
    private void fetchCities(String stateName) {
        String url = "https://www.universal-tutorial.com/api/cities/" + stateName;
        JsonArrayRequest request = new JsonArrayRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        List<String> cities = new ArrayList<>();
                        for (int i = 0; i < response.length(); i++) {
                            try {
                                JSONObject city = response.getJSONObject(i);
                                String cityName = city.getString("city_name");
                                cities.add(cityName);
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                        ArrayAdapter<String> adapter = new ArrayAdapter<>(CreateCountActivity.this,
                                android.R.layout.simple_spinner_item, cities);
                        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                        mEtCiu.setAdapter(adapter);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        error.printStackTrace();
                    }
                }) {
            @Override
            public Map<String, String> getHeaders() {
                Map<String, String> headers = new HashMap<>();
                headers.put("Authorization", "Bearer " + accessToken);
                return headers;
            }
        };

        requestQueue.add(request);
    }

    private void registerUser() {
        mAuth.createUserWithEmailAndPassword(mail, pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    Map<String, Object> map = new HashMap<>();
                    map.put("name", name);
                    map.put("apel", apel);
                    map.put("phone", numt);
                    map.put("mail", mail);
                    map.put("pass", pass);

                    Map<String, Object> addres = new HashMap<>();
                    addres.put("pais", pais);
                    addres.put("departamento", dep);
                    addres.put("ciudad", ciu);
                    addres.put("direccion", dir);
                    addres.put("dirComplete", dir + " " + barr + " ");
                    addres.put("barrio", barr);

                    String id = mAuth.getCurrentUser().getUid();
                    String addresID = mDatabase.child("Users").child(id).child("Direcciones").push().getKey();
                    System.out.println("AddresID: " + addresID);

                    mDatabase.child("Users").child(id).setValue(map).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task2) {
                            if (task2.isSuccessful()) {
                                assert addresID != null;
                                mDatabase.child("Users").child(id).child("Direcciones").child(addresID).setValue(addres).addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {
                                        if (task.isSuccessful()){
                                            startActivity(new Intent(CreateCountActivity.this, InicioActivity.class));
                                            Toast.makeText(CreateCountActivity.this, "Verifique su cuenta de E-Mail", Toast.LENGTH_SHORT).show();
                                            finish();
                                        }else{
                                            Toast.makeText(CreateCountActivity.this, "No se creo", Toast.LENGTH_SHORT).show();
                                            System.out.println("Task: " + task.toString());
                                        }

                                    }
                                });


                            } else {
                                Toast.makeText(CreateCountActivity.this, "No se pudieron crear los datos correctamente", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });

                } else {
                    Toast.makeText(CreateCountActivity.this, "No se puede registrar este usuario", Toast.LENGTH_SHORT).show();
                }

            }
        });
    }



    private boolean validateEmail(String mail) {
        String emailInput = mail;
        if (emailInput.isEmpty()) {

            return false;
        } else if (!Patterns.EMAIL_ADDRESS.matcher(emailInput).matches()) {
            Toast.makeText(this, "Por favor ingrese un correo valido", Toast.LENGTH_SHORT).show();
            return false;
        } else {

            return true;
        }
    }

    private boolean validatePassword(String pass, String cpass) {
        String input = pass;
        String confirm = cpass;
        if (input.isEmpty()) {
            return false;
        } /*else if (!PASSWORD_PATTERN.matcher(input).matches()) {
            Toast.makeText(this, "La contraseña debe ser una combinación de mayusculas, minusculas con minimo 6 caracteres y sin espacios", Toast.LENGTH_SHORT).show();
            return false;
        } */ else if (!pass.equals(cpass)) {
            Toast.makeText(this, "La contraseñas no coinciden", Toast.LENGTH_SHORT).show();
            return false;
        } else return true;
    }
    private void getAccessToken() {
        String url = "https://www.universal-tutorial.com/api/getaccesstoken";
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            accessToken = response.getString("auth_token");
                            Log.d("Access Token", accessToken);
                            fetchStates("Colombia");
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        error.printStackTrace();
                    }
                }) {
            @Override
            public Map<String, String> getHeaders() {
                Map<String, String> headers = new HashMap<>();
                headers.put("Accept", "application/json");
                headers.put("api-token", apiToken);
                headers.put("user-email", userEmail);
                return headers;
            }
        };

        requestQueue.add(request);
    }

    private void fetchStates(String countryName) {
        String url = "https://www.universal-tutorial.com/api/states/" + countryName;
        JsonArrayRequest request = new JsonArrayRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        List<String> states = new ArrayList<>();
                        for (int i = 0; i < response.length(); i++) {
                            try {
                                JSONObject state = response.getJSONObject(i);
                                String stateName = state.getString("state_name");
                                states.add(stateName);
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                        ArrayAdapter<String> adapter = new ArrayAdapter<>(CreateCountActivity.this,
                                android.R.layout.simple_spinner_item, states);
                        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                        mEtDep.setAdapter(adapter);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        error.printStackTrace();
                    }
                }) {
            @Override
            public String getBodyContentType() {
                return "application/json";
            }


            @Override
            public Map<String, String> getHeaders() {
                Map<String, String> headers = new HashMap<>();
                headers.put("Authorization", "Bearer " + accessToken);
                return headers;
            }
        };

        requestQueue.add(request);

        mEtDep.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String selectedState = parent.getItemAtPosition(position).toString();
                fetchCities(selectedState);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

}
