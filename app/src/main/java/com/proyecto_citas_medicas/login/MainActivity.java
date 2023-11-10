package com.proyecto_citas_medicas.login;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

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
import com.proyecto_citas_medicas.R;
import com.proyecto_citas_medicas.administrador.administrador_activity;
import com.proyecto_citas_medicas.api.MyApi;
import com.proyecto_citas_medicas.databinding.ActivityMainBinding;
import com.proyecto_citas_medicas.medicos.Medicos_Activity;
import com.proyecto_citas_medicas.pacientes.HomePacienteActivity;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {
    private ActivityMainBinding binding;

    EditText edtCorreo,edtContraseña;

    private GoogleSignInClient googleSignInClient;
    private FirebaseAuth firebaseAuth;
    private static final String TAG ="GOOGLE_SING_IN_TAG";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        edtCorreo = findViewById(R.id.edtCorreo);
        edtContraseña = findViewById(R.id.edtContrasena);

        GoogleSignInOptions googleSignInOptions = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();
        googleSignInClient = GoogleSignIn.getClient(this,googleSignInOptions);

        firebaseAuth = FirebaseAuth.getInstance();



        binding.btnIngresaGoogle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                resultLauncher.launch(new Intent(googleSignInClient.getSignInIntent()));

            }
        });

        binding.btnIngresar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String correo1=edtCorreo.getText().toString();
                String contra1=edtContraseña.getText().toString();

                Retrofit retrofit=new Retrofit.Builder()
                        .baseUrl("http://192.168.0.5/appCitas/login.php/")
                        .addConverterFactory(GsonConverterFactory.create())
                        .build();

                MyApi myApi=retrofit.create(MyApi.class);
                Call<User> call=myApi.login(correo1,contra1);

                call.enqueue(new Callback<User>() {
                    @Override
                    public void onResponse(Call<User> call, Response<User> response) {
                        if(response.isSuccessful())
                        {
                            User user= response.body();
                            String userType2= user.getUserType();

                            if (userType2 != null) {
                                if (userType2.equals("PACIENTE")) {
                                    startActivity(new Intent(MainActivity.this,  HomePacienteActivity.class));

                                }else {
                                    startActivity(new Intent(MainActivity.this, Medicos_Activity.class));

                                }
                            } else {
                                // El tipo de usuario no se pudo determinar
                                Toast.makeText(MainActivity.this, "Error: Tipo de usuario desconocido", Toast.LENGTH_SHORT).show();
                            }
                        } else {
                            // La respuesta no fue exitosa
                            Toast.makeText(MainActivity.this, "Error al iniciar sesión, por favor intente de nuevo", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<User> call, Throwable t) {

                    }
                });
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
                           startActivity(new Intent(MainActivity.this,administrador_activity.class));
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