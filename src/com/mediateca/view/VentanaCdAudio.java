package com.mediateca.view;

import com.mediateca.dao.CdAudioDAO;
import com.mediateca.model.CdAudio;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class VentanaCdAudio extends JFrame {

    private JTextField txtId;
    private JTextField txtCodigo;
    private JTextField txtTitulo;
    private JTextField txtUnidades;
    private JTextField txtArtista;
    private JTextField txtDuracion;
    private JTextField txtGenero;
    private JTextField txtNumeroCanciones;

    private JButton btnGuardar;
    private JButton btnActualizar;
    private JButton btnEliminar;
    private JButton btnLimpiar;

    private JTable tablaCdAudio;
    private DefaultTableModel modeloTabla;

    private CdAudioDAO cdAudioDAO;

    public VentanaCdAudio() {
        cdAudioDAO = new CdAudioDAO();

        setTitle("Gestión de CD Audio");
        setSize(950, 600);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout(10, 10));

        JPanel panelCampos = new JPanel(new GridLayout(8, 2, 5, 5));

        panelCampos.add(new JLabel("ID Material:"));
        txtId = new JTextField();
        txtId.setEditable(false);
        panelCampos.add(txtId);

        panelCampos.add(new JLabel("Código Interno:"));
        txtCodigo = new JTextField();
        panelCampos.add(txtCodigo);

        panelCampos.add(new JLabel("Título:"));
        txtTitulo = new JTextField();
        panelCampos.add(txtTitulo);

        panelCampos.add(new JLabel("Unidades:"));
        txtUnidades = new JTextField();
        panelCampos.add(txtUnidades);

        panelCampos.add(new JLabel("Artista:"));
        txtArtista = new JTextField();
        panelCampos.add(txtArtista);

        panelCampos.add(new JLabel("Duración:"));
        txtDuracion = new JTextField();
        panelCampos.add(txtDuracion);

        panelCampos.add(new JLabel("Género:"));
        txtGenero = new JTextField();
        panelCampos.add(txtGenero);

        panelCampos.add(new JLabel("Número de canciones:"));
        txtNumeroCanciones = new JTextField();
        panelCampos.add(txtNumeroCanciones);

        JPanel panelBotones = new JPanel(new GridLayout(1, 4, 10, 10));

        btnGuardar = new JButton("Guardar");
        btnActualizar = new JButton("Actualizar");
        btnEliminar = new JButton("Eliminar");
        btnLimpiar = new JButton("Limpiar");

        panelBotones.add(btnGuardar);
        panelBotones.add(btnActualizar);
        panelBotones.add(btnEliminar);
        panelBotones.add(btnLimpiar);

        JPanel panelSuperior = new JPanel(new BorderLayout(10, 10));
        panelSuperior.add(panelCampos, BorderLayout.CENTER);
        panelSuperior.add(panelBotones, BorderLayout.SOUTH);

        add(panelSuperior, BorderLayout.NORTH);

        modeloTabla = new DefaultTableModel();
        modeloTabla.addColumn("ID");
        modeloTabla.addColumn("Código");
        modeloTabla.addColumn("Título");
        modeloTabla.addColumn("Unidades");
        modeloTabla.addColumn("Artista");
        modeloTabla.addColumn("Duración");
        modeloTabla.addColumn("Género");
        modeloTabla.addColumn("N° Canciones");

        tablaCdAudio = new JTable(modeloTabla);
        JScrollPane scrollPane = new JScrollPane(tablaCdAudio);
        add(scrollPane, BorderLayout.CENTER);

        btnGuardar.addActionListener(e -> guardarCdAudio());
        btnActualizar.addActionListener(e -> actualizarCdAudio());
        btnEliminar.addActionListener(e -> eliminarCdAudio());
        btnLimpiar.addActionListener(e -> limpiarCampos());

        tablaCdAudio.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                cargarCdAudioDesdeTabla();
            }
        });

        cargarTablaCdAudio();
    }

    private String validarCampos() {
        if (txtCodigo.getText().trim().isEmpty()) {
            return "El campo Código Interno es obligatorio.";
        }

        if (txtTitulo.getText().trim().isEmpty()) {
            return "El campo Título es obligatorio.";
        }

        if (txtUnidades.getText().trim().isEmpty()) {
            return "El campo Unidades es obligatorio.";
        }

        if (txtArtista.getText().trim().isEmpty()) {
            return "El campo Artista es obligatorio.";
        }

        if (txtDuracion.getText().trim().isEmpty()) {
            return "El campo Duración es obligatorio.";
        }

        if (txtGenero.getText().trim().isEmpty()) {
            return "El campo Género es obligatorio.";
        }

        if (txtNumeroCanciones.getText().trim().isEmpty()) {
            return "El campo Número de canciones es obligatorio.";
        }

        try {
            Integer.parseInt(txtUnidades.getText().trim());
        } catch (NumberFormatException e) {
            return "El campo Unidades debe contener solo números.";
        }

        try {
            Integer.parseInt(txtNumeroCanciones.getText().trim());
        } catch (NumberFormatException e) {
            return "El campo Número de canciones debe contener solo números.";
        }

        return null;
    }

    private CdAudio obtenerDatosCdAudio() {
        CdAudio cdAudio = new CdAudio();

        if (!txtId.getText().trim().isEmpty()) {
            cdAudio.setIdMaterial(Integer.parseInt(txtId.getText().trim()));
        }

        cdAudio.setCodigoInterno(txtCodigo.getText().trim());
        cdAudio.setTitulo(txtTitulo.getText().trim());
        cdAudio.setUnidades(Integer.parseInt(txtUnidades.getText().trim()));
        cdAudio.setArtista(txtArtista.getText().trim());
        cdAudio.setDuracion(txtDuracion.getText().trim());
        cdAudio.setGenero(txtGenero.getText().trim());
        cdAudio.setNumeroCanciones(Integer.parseInt(txtNumeroCanciones.getText().trim()));

        return cdAudio;
    }

    private void cargarTablaCdAudio() {
        modeloTabla.setRowCount(0);

        List<CdAudio> listaCdAudio = cdAudioDAO.obtenerCdAudio();

        for (CdAudio cdAudio : listaCdAudio) {
            Object[] fila = {
                    cdAudio.getIdMaterial(),
                    cdAudio.getCodigoInterno(),
                    cdAudio.getTitulo(),
                    cdAudio.getUnidades(),
                    cdAudio.getArtista(),
                    cdAudio.getDuracion(),
                    cdAudio.getGenero(),
                    cdAudio.getNumeroCanciones()
            };

            modeloTabla.addRow(fila);
        }
    }

    private void cargarCdAudioDesdeTabla() {
        int filaSeleccionada = tablaCdAudio.getSelectedRow();

        if (filaSeleccionada != -1) {
            txtId.setText(modeloTabla.getValueAt(filaSeleccionada, 0).toString());
            txtCodigo.setText(modeloTabla.getValueAt(filaSeleccionada, 1).toString());
            txtTitulo.setText(modeloTabla.getValueAt(filaSeleccionada, 2).toString());
            txtUnidades.setText(modeloTabla.getValueAt(filaSeleccionada, 3).toString());
            txtArtista.setText(modeloTabla.getValueAt(filaSeleccionada, 4).toString());
            txtDuracion.setText(modeloTabla.getValueAt(filaSeleccionada, 5).toString());
            txtGenero.setText(modeloTabla.getValueAt(filaSeleccionada, 6).toString());
            txtNumeroCanciones.setText(modeloTabla.getValueAt(filaSeleccionada, 7).toString());
        }
    }

    private void guardarCdAudio() {
        String mensajeValidacion = validarCampos();

        if (mensajeValidacion != null) {
            JOptionPane.showMessageDialog(this, mensajeValidacion);
            return;
        }

        try {
            CdAudio cdAudio = obtenerDatosCdAudio();
            boolean guardado = cdAudioDAO.insertarCdAudio(cdAudio);

            if (guardado) {
                JOptionPane.showMessageDialog(this, "CD Audio guardado correctamente.");
                limpiarCampos();
                cargarTablaCdAudio();
            } else {
                JOptionPane.showMessageDialog(this, "No se pudo guardar el CD Audio.");
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Ocurrió un error al guardar el CD Audio.");
        }
    }

    private void actualizarCdAudio() {
        if (txtId.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Seleccione un CD Audio de la tabla para actualizar.");
            return;
        }

        String mensajeValidacion = validarCampos();

        if (mensajeValidacion != null) {
            JOptionPane.showMessageDialog(this, mensajeValidacion);
            return;
        }

        try {
            CdAudio cdAudio = obtenerDatosCdAudio();
            cdAudioDAO.actualizarCdAudio(cdAudio);
            JOptionPane.showMessageDialog(this, "Proceso de actualización ejecutado.");
            limpiarCampos();
            cargarTablaCdAudio();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Ocurrió un error al actualizar el CD Audio.");
        }
    }

    private void eliminarCdAudio() {
        if (txtId.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Seleccione un CD Audio de la tabla para eliminar.");
            return;
        }

        try {
            int id = Integer.parseInt(txtId.getText().trim());
            boolean eliminado = cdAudioDAO.eliminarCdAudio(id);

            if (eliminado) {
                JOptionPane.showMessageDialog(this, "CD Audio eliminado correctamente.");
                limpiarCampos();
                cargarTablaCdAudio();
            } else {
                JOptionPane.showMessageDialog(this, "No se pudo eliminar el CD Audio.");
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Ocurrió un error al eliminar el CD Audio.");
        }
    }

    private void limpiarCampos() {
        txtId.setText("");
        txtCodigo.setText("");
        txtTitulo.setText("");
        txtUnidades.setText("");
        txtArtista.setText("");
        txtDuracion.setText("");
        txtGenero.setText("");
        txtNumeroCanciones.setText("");
        tablaCdAudio.clearSelection();
    }
}