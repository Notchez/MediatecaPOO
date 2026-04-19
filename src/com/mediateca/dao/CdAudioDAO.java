package com.mediateca.dao;

import com.mediateca.db.Conexion;
import com.mediateca.model.CdAudio;
import com.mediateca.util.LoggerConfig;
import org.apache.logging.log4j.Logger;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class CdAudioDAO {

    private static final Logger logger = LoggerConfig.getLogger(CdAudioDAO.class);

    private static final String SQL_INSERT_MATERIAL =
            "INSERT INTO material (codigo_interno, titulo, unidades) VALUES (?, ?, ?)";

    private static final String SQL_INSERT_CD_AUDIO =
            "INSERT INTO cd_audio (id_material, artista, duracion, genero, numero_canciones) VALUES (?, ?, ?, ?, ?)";

    private static final String SQL_SELECT_CD_AUDIO =
            "SELECT * FROM material m INNER JOIN cd_audio c ON m.id_material = c.id_material";

    private static final String SQL_DELETE_CD_AUDIO =
            "DELETE FROM cd_audio WHERE id_material = ?";

    private static final String SQL_DELETE_MATERIAL =
            "DELETE FROM material WHERE id_material = ?";

    public boolean insertarCdAudio(CdAudio cdAudio) {
        Connection conn = null;
        PreparedStatement stmtMaterial = null;
        PreparedStatement stmtCdAudio = null;
        ResultSet rs = null;

        try {
            conn = Conexion.getConnection();

            stmtMaterial = conn.prepareStatement(SQL_INSERT_MATERIAL, Statement.RETURN_GENERATED_KEYS);
            stmtMaterial.setString(1, cdAudio.getCodigoInterno());
            stmtMaterial.setString(2, cdAudio.getTitulo());
            stmtMaterial.setInt(3, cdAudio.getUnidades());

            int filasMaterial = stmtMaterial.executeUpdate();

            if (filasMaterial == 0) {
                logger.warn("No se insertó el material para el CD Audio.");
                return false;
            }

            rs = stmtMaterial.getGeneratedKeys();

            if (rs.next()) {
                int idGenerado = rs.getInt(1);

                stmtCdAudio = conn.prepareStatement(SQL_INSERT_CD_AUDIO);
                stmtCdAudio.setInt(1, idGenerado);
                stmtCdAudio.setString(2, cdAudio.getArtista());
                stmtCdAudio.setString(3, cdAudio.getDuracion());
                stmtCdAudio.setString(4, cdAudio.getGenero());
                stmtCdAudio.setInt(5, cdAudio.getNumeroCanciones());

                int filasCdAudio = stmtCdAudio.executeUpdate();

                if (filasCdAudio > 0) {
                    logger.info("CD Audio insertado correctamente con ID: {}", idGenerado);
                    return true;
                }
            }

            logger.warn("No se pudo insertar el CD Audio en la base de datos.");
            return false;

        } catch (Exception e) {
            logger.error("Error al insertar CD Audio.", e);
            return false;
        } finally {
            Conexion.close(rs);
            Conexion.close(stmtMaterial);
            Conexion.close(stmtCdAudio);
            Conexion.close(conn);
        }
    }

    public List<CdAudio> obtenerCdAudio() {
        List<CdAudio> listaCdAudio = new ArrayList<>();
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;

        try {
            conn = Conexion.getConnection();
            stmt = conn.prepareStatement(SQL_SELECT_CD_AUDIO);
            rs = stmt.executeQuery();

            while (rs.next()) {
                CdAudio cdAudio = new CdAudio();

                cdAudio.setIdMaterial(rs.getInt("id_material"));
                cdAudio.setCodigoInterno(rs.getString("codigo_interno"));
                cdAudio.setTitulo(rs.getString("titulo"));
                cdAudio.setUnidades(rs.getInt("unidades"));
                cdAudio.setArtista(rs.getString("artista"));
                cdAudio.setDuracion(rs.getString("duracion"));
                cdAudio.setGenero(rs.getString("genero"));
                cdAudio.setNumeroCanciones(rs.getInt("numero_canciones"));

                listaCdAudio.add(cdAudio);
            }

            logger.info("Consulta de CD Audio realizada correctamente. Total encontrados: {}", listaCdAudio.size());

        } catch (Exception e) {
            logger.error("Error al obtener CD Audio.", e);
        } finally {
            Conexion.close(rs);
            Conexion.close(stmt);
            Conexion.close(conn);
        }

        return listaCdAudio;
    }

    public void listarCdAudio() {
        List<CdAudio> cds = obtenerCdAudio();

        for (CdAudio cdAudio : cds) {
            System.out.println("----- CD AUDIO -----");
            System.out.println("ID: " + cdAudio.getIdMaterial());
            System.out.println("Codigo: " + cdAudio.getCodigoInterno());
            System.out.println("Titulo: " + cdAudio.getTitulo());
            System.out.println("Unidades: " + cdAudio.getUnidades());
            System.out.println("Artista: " + cdAudio.getArtista());
            System.out.println("Duracion: " + cdAudio.getDuracion());
            System.out.println("Genero: " + cdAudio.getGenero());
            System.out.println("Numero de canciones: " + cdAudio.getNumeroCanciones());
            System.out.println("--------------------\n");
        }
    }

    public boolean eliminarCdAudio(int idMaterial) {
        Connection conn = null;
        PreparedStatement stmtCdAudio = null;
        PreparedStatement stmtMaterial = null;

        try {
            conn = Conexion.getConnection();

            stmtCdAudio = conn.prepareStatement(SQL_DELETE_CD_AUDIO);
            stmtCdAudio.setInt(1, idMaterial);
            int filasCdAudio = stmtCdAudio.executeUpdate();

            stmtMaterial = conn.prepareStatement(SQL_DELETE_MATERIAL);
            stmtMaterial.setInt(1, idMaterial);
            int filasMaterial = stmtMaterial.executeUpdate();

            boolean eliminado = filasCdAudio > 0 && filasMaterial > 0;

            if (eliminado) {
                logger.info("CD Audio eliminado correctamente. ID: {}", idMaterial);
            } else {
                logger.warn("No se pudo eliminar el CD Audio. ID: {}", idMaterial);
            }

            return eliminado;

        } catch (Exception e) {
            logger.error("Error al eliminar CD Audio. ID: " + idMaterial, e);
            return false;
        } finally {
            Conexion.close(stmtCdAudio);
            Conexion.close(stmtMaterial);
            Conexion.close(conn);
        }
    }

    public void actualizarCdAudio(CdAudio cdAudio) {
        Connection conn = null;
        PreparedStatement stmtMaterial = null;
        PreparedStatement stmtCdAudio = null;

        try {
            conn = Conexion.getConnection();

            String sqlMaterial = "UPDATE material SET codigo_interno = ?, titulo = ?, unidades = ? WHERE id_material = ?";
            stmtMaterial = conn.prepareStatement(sqlMaterial);
            stmtMaterial.setString(1, cdAudio.getCodigoInterno());
            stmtMaterial.setString(2, cdAudio.getTitulo());
            stmtMaterial.setInt(3, cdAudio.getUnidades());
            stmtMaterial.setInt(4, cdAudio.getIdMaterial());
            int filasMaterial = stmtMaterial.executeUpdate();

            String sqlCdAudio = "UPDATE cd_audio SET artista = ?, duracion = ?, genero = ?, numero_canciones = ? WHERE id_material = ?";
            stmtCdAudio = conn.prepareStatement(sqlCdAudio);
            stmtCdAudio.setString(1, cdAudio.getArtista());
            stmtCdAudio.setString(2, cdAudio.getDuracion());
            stmtCdAudio.setString(3, cdAudio.getGenero());
            stmtCdAudio.setInt(4, cdAudio.getNumeroCanciones());
            stmtCdAudio.setInt(5, cdAudio.getIdMaterial());
            int filasCdAudio = stmtCdAudio.executeUpdate();

            if (filasMaterial > 0 && filasCdAudio > 0) {
                logger.info("CD Audio actualizado correctamente. ID: {}", cdAudio.getIdMaterial());
                System.out.println("CD Audio actualizado correctamente.");
            } else {
                logger.warn("No se encontró el CD Audio para actualizar. ID: {}", cdAudio.getIdMaterial());
                System.out.println("No se encontró el CD Audio para actualizar.");
            }

        } catch (Exception e) {
            logger.error("Error al actualizar CD Audio. ID: " + cdAudio.getIdMaterial(), e);
        } finally {
            Conexion.close(stmtMaterial);
            Conexion.close(stmtCdAudio);
            Conexion.close(conn);
        }
    }
}