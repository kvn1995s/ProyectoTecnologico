package com.proyecto_citas_medicas;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.app.DownloadManager;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.ktx.Firebase;
import com.proyecto_citas_medicas.databinding.ActivityCrudMedicosBinding;

import java.util.HashMap;
import java.util.Map;

public class crud_medicos extends AppCompatActivity {

    private ActivityCrudMedicosBinding binding;
    private FirebaseAuth firebaseAuth;

    private static final String URL1 ="http://192.168.0.3/AppCitas/save.php";

    RequestQueue requestQueue;

    EditText edtcorreo,edtcontra,edtnoms,edtapeP,edtapeM,edtdni,edtdirect;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityCrudMedicosBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        firebaseAuth= FirebaseAuth.getInstance();
        checkUser();

        requestQueue = Volley.newRequestQueue(this);
        initUi();

        binding.logutBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                firebaseAuth.signOut();
                checkUser();
            }
        });
        binding.btnRegis.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                    String correoMed = edtcorreo.getText().toString().trim();
                    String contraseñaMed = edtcontra.getText().toString().trim();
                    String nomMed = edtnoms.getText().toString().trim();
                    String apellidoPaternoM = edtapeP.getText().toString().trim();
                    String apellidoMaternoM	 = edtapeM.getText().toString().trim();
                    String dniMe = edtdni.getText().toString().trim();
                    String direccionMe = edtdirect.getText().toString().trim();

                    createMedic(correoMed,contraseñaMed,nomMed,apellidoPaternoM,apellidoMaternoM,dniMe,direccionMe);
            }
        });

    }

    private void createMedic(final String correoMed,final String contraseñaMed,final String nomMed ,final String apellidoPaternoM, final String apellidoMaternoM, final String dniMe, final String direccionMe) {

        StringRequest stringRequest =new StringRequest(
                Request.Method.POST,
                URL1,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Toast.makeText(crud_medicos.this,"Correct",Toast.LENGTH_SHORT).show();
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                }
        ){
            @Nullable
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> params = new HashMap<>();
                params.put("correoMed",correoMed);
                params.put("contraseñaMed",contraseñaMed);
                params.put("nomMed",nomMed);
                params.put("apellidoPaternoM",apellidoPaternoM);
                params.put("apellidoMaternoM",apellidoMaternoM);
                params.put("dniMe",dniMe);
                params.put("direccionMe",direccionMe);

                return params;
            }
        };

        requestQueue.add(stringRequest);
    }

    private void initUi() {
        edtcorreo = findViewById(R.id.edtcorreo);
        edtcontra = findViewById(R.id.edtcontra);
        edtnoms = findViewById(R.id.edtnom);
        edtapeP = findViewById(R.id.edtapeP);
        edtapeM = findViewById(R.id.edtapeM);
        edtdni = findViewById(R.id.edtdni);
        edtdirect = findViewById(R.id.edtdirec);
    }

    private void checkUser() {
        FirebaseUser firebaseUser = firebaseAuth.getCurrentUser();
        if (firebaseUser != null){
            String email = firebaseUser.getEmail();
            binding.emailTv.setText(email);

        }
        else {
               startActivity(new Intent(this, MainActivity.class));
            finish();

        }
    }
}