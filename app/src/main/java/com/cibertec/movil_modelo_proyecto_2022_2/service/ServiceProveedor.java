package com.cibertec.movil_modelo_proyecto_2022_2.service;

import com.cibertec.movil_modelo_proyecto_2022_2.entity.Autor;
import com.cibertec.movil_modelo_proyecto_2022_2.entity.Proveedor;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface ServiceProveedor {
    @POST("proveedor")
    public abstract Call<Proveedor> insertaProveedor(@Body Proveedor obj);

    @GET("proveedor")
    public abstract Call<List<Proveedor>> listaProveedor();
}
