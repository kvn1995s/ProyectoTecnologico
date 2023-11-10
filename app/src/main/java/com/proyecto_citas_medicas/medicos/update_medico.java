package com.proyecto_citas_medicas.medicos;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.proyecto_citas_medicas.databinding.ActivityUpdateMedicoBinding;

public class update_medico extends AppCompatActivity {
    private ActivityUpdateMedicoBinding binding;
    private FirebaseAuth firebaseAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityUpdateMedicoBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        firebaseAuth= FirebaseAuth.getInstance();




    }


}