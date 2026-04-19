package com.mediateca.view;

import com.mediateca.dao.DvdDAO;
import com.mediateca.model.Dvd;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class VentanaDvd extends JFrame {

    private JTextField txtId;
    private JTextField txtCodigo;
    private JTextField txtTitulo;
    private JTextField txtUnidades;
    private JTextField txtDirector;
    private JTextField txtGenero;
    private JTextField txtDuracion;

    private JButton btnGuardar;
    private JButton btnActualizar;
    private JButton btnEliminar;
    private JButton btnLimpiar;

    private JTable tablaDvd;
    private DefaultTableModel modeloTabla;

    private DvdDAO dvdDAO;

    public VentanaDvd() {
        dvdDAO = new DvdDAO();

        setTitle("Gestión de DVD");
        setSize(900, 600);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout(10, 10));

        JPanel panelCampos = new JPanel(new GridLayout(7, 2, 5, 5));

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

        panelCampos.add(new JLabel("Director:"));
        txtDirector = new JTextField();
        panelCampos.add(txtDirector);

        panelCampos.add(new JLabel("Género:"));
        txtGenero = new JTextField();
        panelCampos.add(txtGenero);

        panelCampos.add(new JLabel("Duración:"));
        txtDuracion = new JTextField();
        panelCampos.add(txtDuracion);

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
        modeloTabla.addColumn("Director");
        modeloTabla.addColumn("Género");
        modeloTabla.addColumn("Duración");

        tablaDvd = new JTable(modeloTabla);
        JScrollPane scrollPane = new JScrollPane(tablaDvd);
        add(scrollPane, BorderLayout.CENTER);

        btnGuardar.addActionListener(e -> guardarDvd());
        btnActualizar.addActionListener(e -> actualizarDvd());
        btnEliminar.addActionListener(e -> eliminarDvd());
        btnLimpiar.addActionListener(e -> limpiarCampos());

        tablaDvd.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                cargarDvdDesdeTabla();
            }
        });

        cargarTablaDvd();
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

        if (txtDirector.getText().trim().isEmpty()) {
            return "El campo Director es obligatorio.";
        }

        if (txtGenero.getText().trim().isEmpty()) {
            return "El campo Género es obligatorio.";
        }

        if (txtDuracion.getText().trim().isEmpty()) {
            return "El campo Duración es obligatorio.";
        }

        try {
            Integer.parseInt(txtUnidades.getText().trim());
        } catch (NumberFormatException e) {
            return "El campo Unidades debe contener solo números.";
        }

        return null;
    }

    private Dvd obtenerDatosDvd() {
        Dvd dvd = new Dvd();

        if (!txtId.getText().trim().isEmpty()) {
            dvd.setIdMaterial(Integer.parseInt(txtId.getText().trim()));
        }

        dvd.setCodigoInterno(txtCodigo.getText().trim());
        dvd.setTitulo(txtTitulo.getText().trim());
        dvd.setUnidades(Integer.parseInt(txtUnidades.getText().trim()));
        dvd.setDirector(txtDirector.getText().trim());
        dvd.setGenero(txtGenero.getText().trim());
        dvd.setDuracion(txtDuracion.getText().trim());

        return dvd;
    }

    private void cargarTablaDvd() {
        modeloTabla.setRowCount(0);

        List<Dvd> listaDvd = dvdDAO.obtenerDvd();

        for (Dvd dvd : listaDvd) {
            Object[] fila = {
                    dvd.getIdMaterial(),
                    dvd.getCodigoInterno(),
                    dvd.getTitulo(),
                    dvd.getUnidades(),
                    dvd.getDirector(),
                    dvd.getGenero(),
                    dvd.getDuracion()
            };

            modeloTabla.addRow(fila);
        }
    }

    private void cargarDvdDesdeTabla() {
        int filaSeleccionada = tablaDvd.getSelectedRow();

        if (filaSeleccionada != -1) {
            txtId.setText(modeloTabla.getValueAt(filaSeleccionada, 0).toString());
            txtCodigo.setText(modeloTabla.getValueAt(filaSeleccionada, 1).toString());
            txtTitulo.setText(modeloTabla.getValueAt(filaSeleccionada, 2).toString());
            txtUnidades.setText(modeloTabla.getValueAt(filaSeleccionada, 3).toString());
            txtDirector.setText(modeloTabla.getValueAt(filaSeleccionada, 4).toString());
            txtGenero.setText(modeloTabla.getValueAt(filaSeleccionada, 5).toString());
            txtDuracion.setText(modeloTabla.getValueAt(filaSeleccionada, 6).toString());
        }
    }

    private void guardarDvd() {
        String mensajeValidacion = validarCampos();

        if (mensajeValidacion != null) {
            JOptionPane.showMessageDialog(this, mensajeValidacion);
            return;
        }

        try {
            Dvd dvd = obtenerDatosDvd();
            boolean guardado = dvdDAO.insertarDvd(dvd);

            if (guardado) {
                JOptionPane.showMessageDialog(this, "DVD guardado correctamente.");
                limpiarCampos();
                cargarTablaDvd();
            } else {
                JOptionPane.showMessageDialog(this, "No se pudo guardar el DVD.");
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Ocurrió un error al guardar el DVD.");
        }
    }

    private void actualizarDvd() {
        if (txtId.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Seleccione un DVD de la tabla para actualizar.");
            return;
        }

        String mensajeValidacion = validarCampos();

        if (mensajeValidacion != null) {
            JOptionPane.showMessageDialog(this, mensajeValidacion);
            return;
        }

        try {
            Dvd dvd = obtenerDatosDvd();
            dvdDAO.actualizarDvd(dvd);
            JOptionPane.showMessageDialog(this, "Proceso de actualización ejecutado.");
            limpiarCampos();
            cargarTablaDvd();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Ocurrió un error al actualizar el DVD.");
        }
    }

    private void eliminarDvd() {
        if (txtId.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Seleccione un DVD de la tabla para eliminar.");
            return;
        }

        try {
            int id = Integer.parseInt(txtId.getText().trim());
            boolean eliminado = dvdDAO.eliminarDvd(id);

            if (eliminado) {
                JOptionPane.showMessageDialog(this, "DVD eliminado correctamente.");
                limpiarCampos();
                cargarTablaDvd();
            } else {
                JOptionPane.showMessageDialog(this, "No se pudo eliminar el DVD.");
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Ocurrió un error al eliminar el DVD.");
        }
    }

    private void limpiarCampos() {
        txtId.setText("");
        txtCodigo.setText("");
        txtTitulo.setText("");
        txtUnidades.setText("");
        txtDirector.setText("");
        txtGenero.setText("");
        txtDuracion.setText("");
        tablaDvd.clearSelection();
    }
}