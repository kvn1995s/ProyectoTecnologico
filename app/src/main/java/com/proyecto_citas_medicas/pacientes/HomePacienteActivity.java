package com.proyecto_citas_medicas.pacientes;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.proyecto_citas_medicas.calendario.Horario_calendar;
import com.proyecto_citas_medicas.databinding.ActivityPacienteHomeBinding;
import com.proyecto_citas_medicas.medicos.update_medico;

public class HomePacienteActivity extends AppCompatActivity {

    private ActivityPacienteHomeBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding =  ActivityPacienteHomeBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.btnCalendario.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(HomePacienteActivity.this, Horario_calendar.class));
            }
        });
        binding.btnActualizar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(HomePacienteActivity.this, update_medico.class));
            }
        });
    }
}