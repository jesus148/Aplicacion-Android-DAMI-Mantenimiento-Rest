package com.cibertec.movil_modelo_proyecto_2022_2.service;

import com.cibertec.movil_modelo_proyecto_2022_2.entity.Alumno;
import com.cibertec.movil_modelo_proyecto_2022_2.entity.Autor;
import com.cibertec.movil_modelo_proyecto_2022_2.entity.Editorial;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;

public interface ServiceAlumno {
    @POST("alumno")
    public Call<Alumno> insertaAlumno(@Body Alumno obj);
    @GET("alumno")
    public abstract Call<List<Alumno>> listaAlumno();
    @PUT("alumno")
    public abstract Call<Alumno> actualizaAlumno(@Body Alumno objAlumno);
}
