package com.mediateca.model;

public class Revista extends Material {

    private String periodicidad;
    private String editorial;
    private String fechaPublicacion;

    public Revista() {

    }

    public Revista(int idMaterial, String codigoInterno, String titulo, int unidades,
                   String periodicidad, String editorial, String fechaPublicacion) {

        super(idMaterial, codigoInterno, titulo, unidades);

        this.periodicidad = periodicidad;
        this.editorial = editorial;
        this.fechaPublicacion = fechaPublicacion;
    }

    public String getPeriodicidad() {
        return periodicidad;
    }
    public void setPeriodicidad(String periodicidad) {
        this.periodicidad = periodicidad;
    }

    public String getEditorial() {
        return editorial;
    }
    public void setEditorial(String editorial) {
        this.editorial = editorial;
    }

    public String getFechaPublicacion() {
        return fechaPublicacion;
    }
    public void setFechaPublicacion(String fechaPublicacion) {
        this.fechaPublicacion = fechaPublicacion;
    }

    @Override
    public String toString() {
        return super.toString() +
                ", Revista{" +
                "periodicidad='" + periodicidad + '\'' +
                ", editorial='" + editorial + '\'' +
                ", fechaPublicacion='" + fechaPublicacion + '\'' +
                '}';
    }
}