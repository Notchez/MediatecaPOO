package com.mediateca.model;

public abstract class Material {

    private int idMaterial;
    private String codigoInterno;
    private String titulo;
    private int unidades;

    public Material() {

    }
    public Material(int idMaterial, String codigoInterno, String titulo, int unidades) {
        this.idMaterial = idMaterial;
        this.codigoInterno = codigoInterno;
        this.titulo = titulo;
        this.unidades = unidades;
    }

    public int getIdMaterial() {
        return idMaterial;
    }
    public void setIdMaterial(int idMaterial) {
        this.idMaterial = idMaterial;
    }

    public String getCodigoInterno() {
        return codigoInterno;
    }
    public void setCodigoInterno(String codigoInterno) {
        this.codigoInterno = codigoInterno;
    }

    public String getTitulo() {
        return titulo;
    }
    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public int getUnidades() {
        return unidades;
    }
    public void setUnidades(int unidades) {
        this.unidades = unidades;
    }

    @Override
    public String toString() {
        return "Material{" +
                "idMaterial=" + idMaterial +
                ", codigoInterno='" + codigoInterno + '\'' +
                ", titulo='" + titulo + '\'' +
                ", unidades=" + unidades +
                '}';
    }
}
