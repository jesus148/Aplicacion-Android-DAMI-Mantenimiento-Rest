package com.cibertec.movil_modelo_proyecto_2022_2.service;

import com.cibertec.movil_modelo_proyecto_2022_2.entity.Editorial;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;

public interface ServiceEditorial {

    @POST("editorial")
    public abstract Call<Editorial> insertaEditorial(@Body Editorial objEditorial);

    @GET("editorial")
    public abstract Call<List<Editorial>> listaEditorial();

    @PUT("editorial")
    public abstract Call<Editorial> actualizaEditorial(@Body Editorial objEditorial);


}
