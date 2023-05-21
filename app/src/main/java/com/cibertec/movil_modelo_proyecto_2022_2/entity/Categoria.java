package com.cibertec.movil_modelo_proyecto_2022_2.entity;

import java.io.Serializable;

public class Categoria implements Serializable {

    private int idCategoria;
    private String  descripcion;
    private int tipo;

    public int getIdCategoria() {
        return idCategoria;
    }

    public void setIdCategoria(int idCategoria) {
        this.idCategoria = idCategoria;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public int getTipo() {
        return tipo;
    }

    public void setTipo(int tipo) {
        this.tipo = tipo;
    }

    public Categoria() {
    }

    public Categoria(int idCategoria, String descripcion, int tipo) {
        this.idCategoria = idCategoria;
        this.descripcion = descripcion;
        this.tipo = tipo;
    }
}
