package com.mediateca.model;

public class CdAudio extends Material {

    private String artista;
    private String duracion;
    private String genero;
    private int numeroCanciones;

    public CdAudio() {

    }

    public CdAudio(int idMaterial, String codigoInterno, String titulo, int unidades,
                   String artista, String duracion, String genero, int numeroCanciones) {

        super(idMaterial, codigoInterno, titulo, unidades);

        this.artista = artista;
        this.duracion = duracion;
        this.genero = genero;
        this.numeroCanciones = numeroCanciones;
    }

    public String getArtista() {
        return artista;
    }
    public void setArtista(String artista) {
        this.artista = artista;
    }

    public String getDuracion() {
        return duracion;
    }
    public void setDuracion(String duracion) {
        this.duracion = duracion;
    }

    public String getGenero() {
        return genero;
    }
    public void setGenero(String genero) {
        this.genero = genero;
    }

    public int getNumeroCanciones() {
        return numeroCanciones;
    }
    public void setNumeroCanciones(int numeroCanciones) {
        this.numeroCanciones = numeroCanciones;
    }

    @Override
    public String toString() {
        return super.toString() +
                ", CdAudio{" +
                "artista='" + artista + '\'' +
                ", duracion='" + duracion + '\'' +
                ", genero='" + genero + '\'' +
                ", numeroCanciones=" + numeroCanciones +
                '}';
    }
}
