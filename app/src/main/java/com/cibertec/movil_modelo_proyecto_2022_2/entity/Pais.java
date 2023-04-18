package com.cibertec.movil_modelo_proyecto_2022_2.entity;

public class Pais {
    private int idPais;
    private String nombre;
    private String iso;

    public int getIdPais() {
        return idPais;
    }

    public void setIdPais(int idPais) {
        this.idPais = idPais;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getIso() {
        return iso;
    }

    public void setIso(String iso) {
        this.iso = iso;
    }

    public Pais() {
    }

    public Pais(int idPais, String nombre, String iso) {
        this.idPais = idPais;
        this.nombre = nombre;
        this.iso = iso;
    }
}
