package com.cibertec.movil_modelo_proyecto_2022_2.service;

import com.cibertec.movil_modelo_proyecto_2022_2.entity.Revista;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface ServiceRevista {



  //REST ESPECIFICO



    //CL1 INSERTAR REVISTA
    @POST("revista")
    public abstract Call<Revista> insertaRevista(@Body Revista objRevista);



    //CL1 CONSULTA REVISTA
    @GET("revista")
    public abstract Call<List<Revista>> listarevista();







}
