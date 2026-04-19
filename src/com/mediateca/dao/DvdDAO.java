package com.mediateca.dao;

import com.mediateca.db.Conexion;
import com.mediateca.model.Dvd;
import com.mediateca.util.LoggerConfig;
import org.apache.logging.log4j.Logger;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class DvdDAO {

    private static final Logger logger = LoggerConfig.getLogger(DvdDAO.class);

    private static final String SQL_INSERT_MATERIAL =
            "INSERT INTO material (codigo_interno, titulo, unidades) VALUES (?, ?, ?)";

    private static final String SQL_INSERT_DVD =
            "INSERT INTO dvd (id_material, director, genero, duracion) VALUES (?, ?, ?, ?)";

    private static final String SQL_SELECT_DVD =
            "SELECT * FROM material m INNER JOIN dvd d ON m.id_material = d.id_material";

    private static final String SQL_DELETE_DVD =
            "DELETE FROM dvd WHERE id_material = ?";

    private static final String SQL_DELETE_MATERIAL =
            "DELETE FROM material WHERE id_material = ?";

    public boolean insertarDvd(Dvd dvd) {
        Connection conn = null;
        PreparedStatement stmtMaterial = null;
        PreparedStatement stmtDvd = null;
        ResultSet rs = null;

        try {
            conn = Conexion.getConnection();

            stmtMaterial = conn.prepareStatement(SQL_INSERT_MATERIAL, Statement.RETURN_GENERATED_KEYS);
            stmtMaterial.setString(1, dvd.getCodigoInterno());
            stmtMaterial.setString(2, dvd.getTitulo());
            stmtMaterial.setInt(3, dvd.getUnidades());

            int filasMaterial = stmtMaterial.executeUpdate();

            if (filasMaterial == 0) {
                logger.warn("No se insertó el material para el DVD.");
                return false;
            }

            rs = stmtMaterial.getGeneratedKeys();

            if (rs.next()) {
                int idGenerado = rs.getInt(1);

                stmtDvd = conn.prepareStatement(SQL_INSERT_DVD);
                stmtDvd.setInt(1, idGenerado);
                stmtDvd.setString(2, dvd.getDirector());
                stmtDvd.setString(3, dvd.getGenero());
                stmtDvd.setString(4, dvd.getDuracion());

                int filasDvd = stmtDvd.executeUpdate();

                if (filasDvd > 0) {
                    logger.info("DVD insertado correctamente con ID: {}", idGenerado);
                    return true;
                }
            }

            logger.warn("No se pudo insertar el DVD en la base de datos.");
            return false;

        } catch (Exception e) {
            logger.error("Error al insertar DVD.", e);
            return false;
        } finally {
            Conexion.close(rs);
            Conexion.close(stmtMaterial);
            Conexion.close(stmtDvd);
            Conexion.close(conn);
        }
    }

    public List<Dvd> obtenerDvd() {
        List<Dvd> listaDvd = new ArrayList<>();
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;

        try {
            conn = Conexion.getConnection();
            stmt = conn.prepareStatement(SQL_SELECT_DVD);
            rs = stmt.executeQuery();

            while (rs.next()) {
                Dvd dvd = new Dvd();

                dvd.setIdMaterial(rs.getInt("id_material"));
                dvd.setCodigoInterno(rs.getString("codigo_interno"));
                dvd.setTitulo(rs.getString("titulo"));
                dvd.setUnidades(rs.getInt("unidades"));
                dvd.setDirector(rs.getString("director"));
                dvd.setGenero(rs.getString("genero"));
                dvd.setDuracion(rs.getString("duracion"));

                listaDvd.add(dvd);
            }

            logger.info("Consulta de DVD realizada correctamente. Total encontrados: {}", listaDvd.size());

        } catch (Exception e) {
            logger.error("Error al obtener DVD.", e);
        } finally {
            Conexion.close(rs);
            Conexion.close(stmt);
            Conexion.close(conn);
        }

        return listaDvd;
    }

    public void listarDvd() {
        List<Dvd> dvds = obtenerDvd();

        for (Dvd dvd : dvds) {
            System.out.println("------- DVD -------");
            System.out.println("ID: " + dvd.getIdMaterial());
            System.out.println("Codigo: " + dvd.getCodigoInterno());
            System.out.println("Titulo: " + dvd.getTitulo());
            System.out.println("Unidades: " + dvd.getUnidades());
            System.out.println("Director: " + dvd.getDirector());
            System.out.println("Genero: " + dvd.getGenero());
            System.out.println("Duracion: " + dvd.getDuracion());
            System.out.println("-------------------\n");
        }
    }

    public boolean eliminarDvd(int idMaterial) {
        Connection conn = null;
        PreparedStatement stmtDvd = null;
        PreparedStatement stmtMaterial = null;

        try {
            conn = Conexion.getConnection();

            stmtDvd = conn.prepareStatement(SQL_DELETE_DVD);
            stmtDvd.setInt(1, idMaterial);
            int filasDvd = stmtDvd.executeUpdate();

            stmtMaterial = conn.prepareStatement(SQL_DELETE_MATERIAL);
            stmtMaterial.setInt(1, idMaterial);
            int filasMaterial = stmtMaterial.executeUpdate();

            boolean eliminado = filasDvd > 0 && filasMaterial > 0;

            if (eliminado) {
                logger.info("DVD eliminado correctamente. ID: {}", idMaterial);
            } else {
                logger.warn("No se pudo eliminar el DVD. ID: {}", idMaterial);
            }

            return eliminado;

        } catch (Exception e) {
            logger.error("Error al eliminar DVD. ID: " + idMaterial, e);
            return false;
        } finally {
            Conexion.close(stmtDvd);
            Conexion.close(stmtMaterial);
            Conexion.close(conn);
        }
    }

    public void actualizarDvd(Dvd dvd) {
        Connection conn = null;
        PreparedStatement stmtMaterial = null;
        PreparedStatement stmtDvd = null;

        try {
            conn = Conexion.getConnection();

            String sqlMaterial = "UPDATE material SET codigo_interno = ?, titulo = ?, unidades = ? WHERE id_material = ?";
            stmtMaterial = conn.prepareStatement(sqlMaterial);
            stmtMaterial.setString(1, dvd.getCodigoInterno());
            stmtMaterial.setString(2, dvd.getTitulo());
            stmtMaterial.setInt(3, dvd.getUnidades());
            stmtMaterial.setInt(4, dvd.getIdMaterial());
            int filasMaterial = stmtMaterial.executeUpdate();

            String sqlDvd = "UPDATE dvd SET director = ?, genero = ?, duracion = ? WHERE id_material = ?";
            stmtDvd = conn.prepareStatement(sqlDvd);
            stmtDvd.setString(1, dvd.getDirector());
            stmtDvd.setString(2, dvd.getGenero());
            stmtDvd.setString(3, dvd.getDuracion());
            stmtDvd.setInt(4, dvd.getIdMaterial());
            int filasDvd = stmtDvd.executeUpdate();

            if (filasMaterial > 0 && filasDvd > 0) {
                logger.info("DVD actualizado correctamente. ID: {}", dvd.getIdMaterial());
                System.out.println("DVD actualizado correctamente.");
            } else {
                logger.warn("No se encontró el DVD para actualizar. ID: {}", dvd.getIdMaterial());
                System.out.println("No se encontró el DVD para actualizar.");
            }

        } catch (Exception e) {
            logger.error("Error al actualizar DVD. ID: " + dvd.getIdMaterial(), e);
        } finally {
            Conexion.close(stmtMaterial);
            Conexion.close(stmtDvd);
            Conexion.close(conn);
        }
    }
}