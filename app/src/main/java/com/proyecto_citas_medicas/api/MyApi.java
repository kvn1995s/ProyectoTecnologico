package com.proyecto_citas_medicas.api;

import com.proyecto_citas_medicas.login.User;
import com.proyecto_citas_medicas.medicos.ListMedicos;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface MyApi {
    @GET("docs.php")
    Call<ArrayList<ListMedicos>> callMedicos();

    @FormUrlEncoded
    @POST("login.php")
    Call<User> login(
            @Field("correo") String correo1,
            @Field("contraseña") String contra1);

}
