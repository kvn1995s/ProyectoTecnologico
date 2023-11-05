package com.proyecto_citas_medicas;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
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
        checkUser();

        binding.logutBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                                    finish();
            }
        });

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