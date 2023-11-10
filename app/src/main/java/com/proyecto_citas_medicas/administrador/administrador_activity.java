package com.proyecto_citas_medicas.administrador;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.proyecto_citas_medicas.calendario.Horario_calendar;
import com.proyecto_citas_medicas.databinding.ActivityAdministradorBinding;
import com.proyecto_citas_medicas.login.MainActivity;
import com.proyecto_citas_medicas.medicos.medicos_list;

public class administrador_activity extends AppCompatActivity {
    private FirebaseAuth firebaseAuth;
    private ActivityAdministradorBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityAdministradorBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        firebaseAuth= FirebaseAuth.getInstance();
        checkUser();

        binding.btnCalendario.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(administrador_activity.this, Horario_calendar.class));
            }
         });

        binding.btnSalir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                firebaseAuth.signOut();
                checkUser();

            }
        });

        binding.btnDoctores.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(administrador_activity.this, medicos_list.class));
            }
        });
    }

    private void checkUser() {
        FirebaseUser firebaseUser = firebaseAuth.getCurrentUser();
        if (firebaseUser != null){

        }
        else {
            startActivity(new Intent(this, MainActivity.class));
            finish();

        }
    }


}