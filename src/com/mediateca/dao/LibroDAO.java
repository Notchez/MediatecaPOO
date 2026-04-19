package com.mediateca.dao;

import com.mediateca.db.Conexion;
import com.mediateca.model.Libro;
import com.mediateca.util.LoggerConfig;
import org.apache.logging.log4j.Logger;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class LibroDAO {

    private static final Logger logger = LoggerConfig.getLogger(LibroDAO.class);

    private static final String SQL_INSERT_MATERIAL =
            "INSERT INTO material (codigo_interno, titulo, unidades) VALUES (?, ?, ?)";

    private static final String SQL_INSERT_LIBRO =
            "INSERT INTO libro (id_material, autor, editorial, numero_paginas, isbn, anio_publicado) VALUES (?, ?, ?, ?, ?, ?)";

    private static final String SQL_SELECT_LIBROS =
            "SELECT * FROM material m INNER JOIN libro l ON m.id_material = l.id_material";

    private static final String SQL_DELETE_LIBRO =
            "DELETE FROM libro WHERE id_material = ?";

    private static final String SQL_DELETE_MATERIAL =
            "DELETE FROM material WHERE id_material = ?";

    public boolean insertarLibro(Libro libro) {
        Connection conn = null;
        PreparedStatement stmtMaterial = null;
        PreparedStatement stmtLibro = null;
        ResultSet rs = null;

        try {
            conn = Conexion.getConnection();

            stmtMaterial = conn.prepareStatement(SQL_INSERT_MATERIAL, Statement.RETURN_GENERATED_KEYS);
            stmtMaterial.setString(1, libro.getCodigoInterno());
            stmtMaterial.setString(2, libro.getTitulo());
            stmtMaterial.setInt(3, libro.getUnidades());

            int filasMaterial = stmtMaterial.executeUpdate();

            if (filasMaterial == 0) {
                logger.warn("No se insertó el material para el libro.");
                return false;
            }

            rs = stmtMaterial.getGeneratedKeys();

            if (rs.next()) {
                int idGenerado = rs.getInt(1);

                stmtLibro = conn.prepareStatement(SQL_INSERT_LIBRO);
                stmtLibro.setInt(1, idGenerado);
                stmtLibro.setString(2, libro.getAutor());
                stmtLibro.setString(3, libro.getEditorial());
                stmtLibro.setInt(4, libro.getNumeroPaginas());
                stmtLibro.setString(5, libro.getIsbn());
                stmtLibro.setInt(6, libro.getAnioPublicado());

                int filasLibro = stmtLibro.executeUpdate();

                if (filasLibro > 0) {
                    logger.info("Libro insertado correctamente con ID: {}", idGenerado);
                    return true;
                }
            }

            logger.warn("No se pudo insertar el libro en la base de datos.");
            return false;

        } catch (Exception e) {
            logger.error("Error al insertar libro.", e);
            return false;
        } finally {
            Conexion.close(rs);
            Conexion.close(stmtMaterial);
            Conexion.close(stmtLibro);
            Conexion.close(conn);
        }
    }

    public List<Libro> obtenerLibros() {
        List<Libro> listaLibros = new ArrayList<>();
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;

        try {
            conn = Conexion.getConnection();
            stmt = conn.prepareStatement(SQL_SELECT_LIBROS);
            rs = stmt.executeQuery();

            while (rs.next()) {
                Libro libro = new Libro();

                libro.setIdMaterial(rs.getInt("id_material"));
                libro.setCodigoInterno(rs.getString("codigo_interno"));
                libro.setTitulo(rs.getString("titulo"));
                libro.setUnidades(rs.getInt("unidades"));
                libro.setAutor(rs.getString("autor"));
                libro.setEditorial(rs.getString("editorial"));
                libro.setNumeroPaginas(rs.getInt("numero_paginas"));
                libro.setIsbn(rs.getString("isbn"));
                libro.setAnioPublicado(rs.getInt("anio_publicado"));

                listaLibros.add(libro);
            }

            logger.info("Consulta de libros realizada correctamente. Total encontrados: {}", listaLibros.size());

        } catch (Exception e) {
            logger.error("Error al obtener libros.", e);
        } finally {
            Conexion.close(rs);
            Conexion.close(stmt);
            Conexion.close(conn);
        }

        return listaLibros;
    }

    public void listarLibros() {
        List<Libro> libros = obtenerLibros();

        for (Libro libro : libros) {
            System.out.println("------ LIBRO ------");
            System.out.println("ID: " + libro.getIdMaterial());
            System.out.println("Codigo: " + libro.getCodigoInterno());
            System.out.println("Titulo: " + libro.getTitulo());
            System.out.println("Unidades: " + libro.getUnidades());
            System.out.println("Autor: " + libro.getAutor());
            System.out.println("Editorial: " + libro.getEditorial());
            System.out.println("Paginas: " + libro.getNumeroPaginas());
            System.out.println("ISBN: " + libro.getIsbn());
            System.out.println("Año: " + libro.getAnioPublicado());
            System.out.println("-------------------\n");
        }
    }

    public boolean eliminarLibro(int idMaterial) {
        Connection conn = null;
        PreparedStatement stmtLibro = null;
        PreparedStatement stmtMaterial = null;

        try {
            conn = Conexion.getConnection();

            stmtLibro = conn.prepareStatement(SQL_DELETE_LIBRO);
            stmtLibro.setInt(1, idMaterial);
            int filasLibro = stmtLibro.executeUpdate();

            stmtMaterial = conn.prepareStatement(SQL_DELETE_MATERIAL);
            stmtMaterial.setInt(1, idMaterial);
            int filasMaterial = stmtMaterial.executeUpdate();

            boolean eliminado = filasLibro > 0 && filasMaterial > 0;

            if (eliminado) {
                logger.info("Libro eliminado correctamente. ID: {}", idMaterial);
            } else {
                logger.warn("No se pudo eliminar el libro. ID: {}", idMaterial);
            }

            return eliminado;

        } catch (Exception e) {
            logger.error("Error al eliminar libro. ID: " + idMaterial, e);
            return false;
        } finally {
            Conexion.close(stmtLibro);
            Conexion.close(stmtMaterial);
            Conexion.close(conn);
        }
    }

    public void actualizarLibro(Libro libro) {
        Connection conn = null;
        PreparedStatement stmtMaterial = null;
        PreparedStatement stmtLibro = null;

        try {
            conn = Conexion.getConnection();

            String sqlMaterial = "UPDATE material SET codigo_interno = ?, titulo = ?, unidades = ? WHERE id_material = ?";
            stmtMaterial = conn.prepareStatement(sqlMaterial);
            stmtMaterial.setString(1, libro.getCodigoInterno());
            stmtMaterial.setString(2, libro.getTitulo());
            stmtMaterial.setInt(3, libro.getUnidades());
            stmtMaterial.setInt(4, libro.getIdMaterial());
            int filasMaterial = stmtMaterial.executeUpdate();

            String sqlLibro = "UPDATE libro SET autor = ?, editorial = ?, numero_paginas = ?, isbn = ?, anio_publicado = ? WHERE id_material = ?";
            stmtLibro = conn.prepareStatement(sqlLibro);
            stmtLibro.setString(1, libro.getAutor());
            stmtLibro.setString(2, libro.getEditorial());
            stmtLibro.setInt(3, libro.getNumeroPaginas());
            stmtLibro.setString(4, libro.getIsbn());
            stmtLibro.setInt(5, libro.getAnioPublicado());
            stmtLibro.setInt(6, libro.getIdMaterial());
            int filasLibro = stmtLibro.executeUpdate();

            if (filasMaterial > 0 && filasLibro > 0) {
                logger.info("Libro actualizado correctamente. ID: {}", libro.getIdMaterial());
                System.out.println("Libro actualizado correctamente.");
            } else {
                logger.warn("No se encontró el libro para actualizar. ID: {}", libro.getIdMaterial());
                System.out.println("No se encontró el libro para actualizar.");
            }

        } catch (Exception e) {
            logger.error("Error al actualizar libro. ID: " + libro.getIdMaterial(), e);
        } finally {
            Conexion.close(stmtMaterial);
            Conexion.close(stmtLibro);
            Conexion.close(conn);
        }
    }
}