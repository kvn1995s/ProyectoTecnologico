package com.proyecto_citas_medicas.medicos;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.proyecto_citas_medicas.calendario.Horario_calendar;
import com.proyecto_citas_medicas.databinding.ActivityMedicosBinding;

public class Medicos_Activity extends AppCompatActivity {
    private FirebaseAuth firebaseAuth;
    private ActivityMedicosBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding =  ActivityMedicosBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        firebaseAuth= FirebaseAuth.getInstance();


        binding.btnSalir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                firebaseAuth.signOut();

            }
        });
        binding.btnActualizar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Medicos_Activity.this, update_medico.class));
            }
        });

        binding.btnCalendario.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Medicos_Activity.this, Horario_calendar.class));
            }
        });

    }

}