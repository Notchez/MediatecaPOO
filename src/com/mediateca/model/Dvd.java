package com.mediateca.model;

public class Dvd extends Material {

    private String director;
    private String genero;
    private String duracion;

    public Dvd() {

    }

    public Dvd(int idMaterial, String codigoInterno, String titulo, int unidades,
               String director, String genero, String duracion) {

        super(idMaterial, codigoInterno, titulo, unidades);
        this.director = director;
        this.genero = genero;
        this.duracion = duracion;
    }

    public String getDirector() {
        return director;
    }
    public void setDirector(String director) {
        this.director = director;
    }

    public String getGenero() {
        return genero;
    }
    public void setGenero(String genero) {
        this.genero = genero;
    }

    public String getDuracion() {
        return duracion;
    }
    public void setDuracion(String duracion) {
        this.duracion = duracion;
    }

    @Override
    public String toString() {
        return super.toString() +
                ", Dvd{" +
                "director='" + director + '\'' +
                ", genero='" + genero + '\'' +
                ", duracion='" + duracion + '\'' +
                '}';
    }
}
