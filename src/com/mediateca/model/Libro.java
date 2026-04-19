package com.mediateca.model;

public class Libro extends Material {

    private String autor;
    private String editorial;
    private int numeroPaginas;
    private String isbn;
    private int anioPublicado;

    public Libro() {

    }

    public Libro(int idMaterial, String codigoInterno, String titulo, int unidades,
                 String autor, String editorial, int numeroPaginas, String isbn, int anioPublicado) {

        super(idMaterial, codigoInterno, titulo, unidades);

        this.autor = autor;
        this.editorial = editorial;
        this.numeroPaginas = numeroPaginas;
        this.isbn = isbn;
        this.anioPublicado = anioPublicado;
    }

    public String getAutor() {
        return autor;
    }
    public void setAutor(String autor) {
        this.autor = autor;
    }

    public String getEditorial() {
        return editorial;
    }
    public void setEditorial(String editorial) {
        this.editorial = editorial;
    }

    public int getNumeroPaginas () {
        return numeroPaginas;
    }
    public void setNumeroPaginas (int numeroPaginas) {
        this.numeroPaginas = numeroPaginas;
    }

    public String getIsbn() {
        return isbn;
    }
    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    public int getAnioPublicado() {
        return anioPublicado;
    }
    public void setAnioPublicado(int anioPublicado) {
        this.anioPublicado = anioPublicado;
    }

    @Override
    public String toString() {
        return super.toString() +
                ", Libro{" +
                "autor='" + autor + '\'' +
                ", editorial='" + editorial + '\'' +
                ", numeroPaginas=" + numeroPaginas +
                ", isbn='" + isbn + '\'' +
                ", anioPublicado=" + anioPublicado +
                '}';
    }
}
