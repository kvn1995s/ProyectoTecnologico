package com.proyecto_citas_medicas;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.proyecto_citas_medicas.databinding.ActivityMedicosListBinding;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class medicos_list extends AppCompatActivity {
    private MyApi myApi;
    private ArrayList<ListMedicos> listMedicosArrayList;
    private Adapter_medicos adapter_medicos;
    private RecyclerView recyclerView;
    private  String BaseUrl="http://10.0.2.2/appCitas/docs.php/";
    private ActivityMedicosListBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMedicosListBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        //create array list
        listMedicosArrayList=new ArrayList<>();
        findId();
        viewJsonData();
        binding.btnRegis.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(medicos_list.this,crud_medicos.class));
            }
        });

    }

    private void findId() {
        recyclerView=findViewById ( R.id.rv_med);
    }

    private void viewJsonData() {

        Retrofit retrofit=new Retrofit.Builder ()
                .baseUrl (BaseUrl)
                .addConverterFactory (GsonConverterFactory.create())
                .build();

        myApi=retrofit.create ( MyApi.class );

        Call<ArrayList<ListMedicos>> arrayListCall=myApi.callMedicos();
        arrayListCall.enqueue(new Callback<ArrayList<ListMedicos>>() {
            @Override
            public void onResponse(Call<ArrayList<ListMedicos>> call, Response<ArrayList<ListMedicos>> response) {
                listMedicosArrayList=response.body();

                for( int i=0;i < listMedicosArrayList.size();i++){

                    LinearLayoutManager manager=new LinearLayoutManager (medicos_list.this );
                    recyclerView.setLayoutManager ( manager );
                    //set adapter
                    adapter_medicos=new Adapter_medicos ( listMedicosArrayList,medicos_list.this);
                    recyclerView.setAdapter ( adapter_medicos );
                }
            }
            @Override
            public void onFailure(Call<ArrayList<ListMedicos>> call, Throwable t) {
                Toast.makeText ( medicos_list.this, "data does not fetch", Toast.LENGTH_SHORT ).show ();
            }

        });
    }


}