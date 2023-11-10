package com.proyecto_citas_medicas.medicos;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.firebase.auth.FirebaseAuth;
import com.proyecto_citas_medicas.R;
import com.proyecto_citas_medicas.databinding.ActivityCrudMedicosBinding;

import java.util.HashMap;
import java.util.Map;

public class crud_medicos extends AppCompatActivity {

    private ActivityCrudMedicosBinding binding;
    private FirebaseAuth firebaseAuth;

    private static final String URL1 ="http://10.0.2.2/appCitas/save.php/";

    RequestQueue requestQueue;

    EditText edtcorreo,edtcontra,edtnoms,edtapeP,edtapeM,edtdni,edtcmp;
    private Spinner spDireccion;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityCrudMedicosBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        firebaseAuth= FirebaseAuth.getInstance();

        requestQueue = Volley.newRequestQueue(this);
        initUi();


        binding.btnActualizar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                    String correoMed = edtcorreo.getText().toString().trim();
                    String contraseñaMed = edtcontra.getText().toString().trim();
                    String nomMed = edtnoms.getText().toString().trim();
                    String apellidoPaternoM = edtapeP.getText().toString().trim();
                    String apellidoMaternoM	 = edtapeM.getText().toString().trim();
                    String dniMe = edtdni.getText().toString().trim();
                    String direccionMe = spDireccion.getSelectedItem().toString().trim();
                    String cmpMedico = edtcmp.getText().toString().trim();

                    createMedic(correoMed,contraseñaMed,nomMed,apellidoPaternoM,apellidoMaternoM,dniMe,direccionMe,cmpMedico);
            }
        });

    }

    private void createMedic(final String correoMed,final String contraseñaMed,final String nomMed ,final String apellidoPaternoM, final String apellidoMaternoM, final String dniMe, final String direccionMe,final String cmpMedico) {

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
                params.put("cmpMedico",cmpMedico);

                return params;
            }
        };

        requestQueue.add(stringRequest);
    }

    private void initUi() {
        edtcorreo = findViewById(R.id.edtCorreo);
        edtcontra = findViewById(R.id.edtContrasena);
        edtnoms = findViewById(R.id.edtNombres);
        edtapeP = findViewById(R.id.edtApellidos);
        edtapeM = findViewById(R.id.edtApellidoMat);
        edtdni = findViewById(R.id.edtNumDoc);
        spDireccion = (Spinner) findViewById(R.id.spnDistrito);
        edtcmp=findViewById(R.id.edtCmp);
    }


}