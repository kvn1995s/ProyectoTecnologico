package com.proyecto_citas_medicas;

import com.proyecto_citas_medicas.medicos.ListMedicos;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.http.GET;

public interface MyApi {
    @GET("docs.php")
    Call<ArrayList<ListMedicos>> callMedicos();


}
