package com.proyecto_citas_medicas;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;
import com.google.firebase.ktx.Firebase;
import com.proyecto_citas_medicas.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {
    private ActivityMainBinding binding;

    private GoogleSignInClient googleSignInClient;
    private FirebaseAuth firebaseAuth;
    private static final String TAG ="GOOGLE_SING_IN_TAG";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        GoogleSignInOptions googleSignInOptions = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();
        googleSignInClient = GoogleSignIn.getClient(this,googleSignInOptions);

        firebaseAuth = FirebaseAuth.getInstance();
        checkUser();

        binding.imgbuttongoogle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                resultLauncher.launch(new Intent(googleSignInClient.getSignInIntent()));

            }
        });
    }
    ActivityResultLauncher<Intent> resultLauncher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), new ActivityResultCallback<ActivityResult>() {
        @Override
        public void onActivityResult(ActivityResult result) {
            if(result.getResultCode()== Activity.RESULT_OK){
                Intent intent = result.getData();
                Task<GoogleSignInAccount> accountTask = GoogleSignIn.getSignedInAccountFromIntent(intent);
                try {
                    GoogleSignInAccount account = accountTask.getResult(ApiException.class);
                    firebaseAuthWithGoogleAccount(account);

                }catch (Exception e){
                    Log.d(TAG,"onActivityResult:"+e.getMessage());
                }
            }
        }
    });
    private void checkUser() {
        FirebaseUser firebaseUser = firebaseAuth.getCurrentUser();
        if (firebaseUser != null){
            String emailMed = firebaseUser.getEmail().toString();
                if (emailMed.equals("kamc123456@gmail.com")){
                    startActivity(new Intent(this,crud_medicos.class));

                }else{
                    startActivity(new Intent(this,Medicos_Activity.class));
                }
            finish();
        }
    }
    private void firebaseAuthWithGoogleAccount(GoogleSignInAccount account) {
        Log.d(TAG,"firebaseAuthGoogleAccount: begin firebase auth with google account");
        AuthCredential credential = GoogleAuthProvider.getCredential(account.getIdToken(),null);
        firebaseAuth.signInWithCredential(credential)
                .addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                    @Override
                    public void onSuccess(AuthResult authResult) {

                        Log.d(TAG,"onSuccess: Logged In");

                        FirebaseUser firebaseUser = firebaseAuth.getCurrentUser();

                        String uid = firebaseUser.getUid();
                        String email = firebaseUser.getEmail();

                        Log.d(TAG,"onSuccess:Email:"+email);
                        Log.d(TAG,"onSuccess:UID"+uid);

                        if (authResult.getAdditionalUserInfo().isNewUser()){
                            Log.d(TAG,"onSuccess: Account Created...\n"+email);
                            Toast.makeText(MainActivity.this,"Account Created...\n"+email,Toast.LENGTH_SHORT).show();
                        }
                        else {
                            Log.d(TAG,"onSuccess:Existing user...\n"+email);
                            Toast.makeText(MainActivity.this,"Existing User....\n"+email,Toast.LENGTH_SHORT).show();
                        }
                        //Inicio de activity
                       if (email.equals("kamc123456@gmail.com")){
                           startActivity(new Intent(MainActivity.this,crud_medicos.class));
                       }else {
                           startActivity(new Intent(MainActivity.this,Medicos_Activity.class));
                           finish();
                       }
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.d(TAG,"onFailure: Loggin failed"+ e.getMessage());
                    }
                });

    }
}