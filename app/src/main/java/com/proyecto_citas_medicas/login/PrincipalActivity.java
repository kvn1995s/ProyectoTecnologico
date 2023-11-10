package com.proyecto_citas_medicas.login;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.proyecto_citas_medicas.R;
import com.proyecto_citas_medicas.pacientes.RegistroPacienteActivity;

public class PrincipalActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_principal);
    }

    public void ingresarLogin(View view) {
        startActivity(new Intent(this, MainActivity.class));
    }

    public void registrarseLogin(View view) {
        startActivity(new Intent(this, RegistroPacienteActivity.class));
    }


}