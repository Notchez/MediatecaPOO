package com.mediateca.dao;

import com.mediateca.db.Conexion;
import com.mediateca.model.Revista;
import com.mediateca.util.LoggerConfig;
import org.apache.logging.log4j.Logger;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class RevistaDAO {

    private static final Logger logger = LoggerConfig.getLogger(RevistaDAO.class);

    private static final String SQL_INSERT_MATERIAL =
            "INSERT INTO material (codigo_interno, titulo, unidades) VALUES (?, ?, ?)";

    private static final String SQL_INSERT_REVISTA =
            "INSERT INTO revista (id_material, periodicidad, editorial, fecha_publicacion) VALUES (?, ?, ?, ?)";

    private static final String SQL_SELECT_REVISTAS =
            "SELECT * FROM material m INNER JOIN revista r ON m.id_material = r.id_material";

    private static final String SQL_DELETE_REVISTA =
            "DELETE FROM revista WHERE id_material = ?";

    private static final String SQL_DELETE_MATERIAL =
            "DELETE FROM material WHERE id_material = ?";

    public boolean insertarRevista(Revista revista) {
        Connection conn = null;
        PreparedStatement stmtMaterial = null;
        PreparedStatement stmtRevista = null;
        ResultSet rs = null;

        try {
            conn = Conexion.getConnection();

            stmtMaterial = conn.prepareStatement(SQL_INSERT_MATERIAL, Statement.RETURN_GENERATED_KEYS);
            stmtMaterial.setString(1, revista.getCodigoInterno());
            stmtMaterial.setString(2, revista.getTitulo());
            stmtMaterial.setInt(3, revista.getUnidades());

            int filasMaterial = stmtMaterial.executeUpdate();

            if (filasMaterial == 0) {
                logger.warn("No se insertó el material para la revista.");
                return false;
            }

            rs = stmtMaterial.getGeneratedKeys();

            if (rs.next()) {
                int idGenerado = rs.getInt(1);

                stmtRevista = conn.prepareStatement(SQL_INSERT_REVISTA);
                stmtRevista.setInt(1, idGenerado);
                stmtRevista.setString(2, revista.getPeriodicidad());
                stmtRevista.setString(3, revista.getEditorial());
                stmtRevista.setString(4, revista.getFechaPublicacion());

                int filasRevista = stmtRevista.executeUpdate();

                if (filasRevista > 0) {
                    logger.info("Revista insertada correctamente con ID: {}", idGenerado);
                    return true;
                }
            }

            logger.warn("No se pudo insertar la revista en la base de datos.");
            return false;

        } catch (Exception e) {
            logger.error("Error al insertar revista.", e);
            return false;
        } finally {
            Conexion.close(rs);
            Conexion.close(stmtMaterial);
            Conexion.close(stmtRevista);
            Conexion.close(conn);
        }
    }

    public List<Revista> obtenerRevistas() {
        List<Revista> listaRevistas = new ArrayList<>();
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;

        try {
            conn = Conexion.getConnection();
            stmt = conn.prepareStatement(SQL_SELECT_REVISTAS);
            rs = stmt.executeQuery();

            while (rs.next()) {
                Revista revista = new Revista();

                revista.setIdMaterial(rs.getInt("id_material"));
                revista.setCodigoInterno(rs.getString("codigo_interno"));
                revista.setTitulo(rs.getString("titulo"));
                revista.setUnidades(rs.getInt("unidades"));
                revista.setPeriodicidad(rs.getString("periodicidad"));
                revista.setEditorial(rs.getString("editorial"));
                revista.setFechaPublicacion(rs.getString("fecha_publicacion"));

                listaRevistas.add(revista);
            }

            logger.info("Consulta de revistas realizada correctamente. Total encontradas: {}", listaRevistas.size());

        } catch (Exception e) {
            logger.error("Error al obtener revistas.", e);
        } finally {
            Conexion.close(rs);
            Conexion.close(stmt);
            Conexion.close(conn);
        }

        return listaRevistas;
    }

    public void listarRevistas() {
        List<Revista> revistas = obtenerRevistas();

        for (Revista revista : revistas) {
            System.out.println("----- REVISTA -----");
            System.out.println("ID: " + revista.getIdMaterial());
            System.out.println("Codigo: " + revista.getCodigoInterno());
            System.out.println("Titulo: " + revista.getTitulo());
            System.out.println("Unidades: " + revista.getUnidades());
            System.out.println("Periodicidad: " + revista.getPeriodicidad());
            System.out.println("Editorial: " + revista.getEditorial());
            System.out.println("Fecha de publicacion: " + revista.getFechaPublicacion());
            System.out.println("-------------------\n");
        }
    }

    public boolean eliminarRevista(int idMaterial) {
        Connection conn = null;
        PreparedStatement stmtRevista = null;
        PreparedStatement stmtMaterial = null;

        try {
            conn = Conexion.getConnection();

            stmtRevista = conn.prepareStatement(SQL_DELETE_REVISTA);
            stmtRevista.setInt(1, idMaterial);
            int filasRevista = stmtRevista.executeUpdate();

            stmtMaterial = conn.prepareStatement(SQL_DELETE_MATERIAL);
            stmtMaterial.setInt(1, idMaterial);
            int filasMaterial = stmtMaterial.executeUpdate();

            boolean eliminado = filasRevista > 0 && filasMaterial > 0;

            if (eliminado) {
                logger.info("Revista eliminada correctamente. ID: {}", idMaterial);
            } else {
                logger.warn("No se pudo eliminar la revista. ID: {}", idMaterial);
            }

            return eliminado;

        } catch (Exception e) {
            logger.error("Error al eliminar revista. ID: " + idMaterial, e);
            return false;
        } finally {
            Conexion.close(stmtRevista);
            Conexion.close(stmtMaterial);
            Conexion.close(conn);
        }
    }

    public void actualizarRevista(Revista revista) {
        Connection conn = null;
        PreparedStatement stmtMaterial = null;
        PreparedStatement stmtRevista = null;

        try {
            conn = Conexion.getConnection();

            String sqlMaterial = "UPDATE material SET codigo_interno = ?, titulo = ?, unidades = ? WHERE id_material = ?";
            stmtMaterial = conn.prepareStatement(sqlMaterial);
            stmtMaterial.setString(1, revista.getCodigoInterno());
            stmtMaterial.setString(2, revista.getTitulo());
            stmtMaterial.setInt(3, revista.getUnidades());
            stmtMaterial.setInt(4, revista.getIdMaterial());
            int filasMaterial = stmtMaterial.executeUpdate();

            String sqlRevista = "UPDATE revista SET periodicidad = ?, editorial = ?, fecha_publicacion = ? WHERE id_material = ?";
            stmtRevista = conn.prepareStatement(sqlRevista);
            stmtRevista.setString(1, revista.getPeriodicidad());
            stmtRevista.setString(2, revista.getEditorial());
            stmtRevista.setString(3, revista.getFechaPublicacion());
            stmtRevista.setInt(4, revista.getIdMaterial());
            int filasRevista = stmtRevista.executeUpdate();

            if (filasMaterial > 0 && filasRevista > 0) {
                logger.info("Revista actualizada correctamente. ID: {}", revista.getIdMaterial());
                System.out.println("Revista actualizada correctamente.");
            } else {
                logger.warn("No se encontró la revista para actualizar. ID: {}", revista.getIdMaterial());
                System.out.println("No se encontró la revista para actualizar.");
            }

        } catch (Exception e) {
            logger.error("Error al actualizar revista. ID: " + revista.getIdMaterial(), e);
        } finally {
            Conexion.close(stmtMaterial);
            Conexion.close(stmtRevista);
            Conexion.close(conn);
        }
    }
}