package com.mediateca.view;

import com.mediateca.dao.RevistaDAO;
import com.mediateca.model.Revista;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class VentanaRevista extends JFrame {

    private JTextField txtId;
    private JTextField txtCodigo;
    private JTextField txtTitulo;
    private JTextField txtUnidades;
    private JTextField txtPeriodicidad;
    private JTextField txtEditorial;
    private JTextField txtFechaPublicacion;

    private JButton btnGuardar;
    private JButton btnActualizar;
    private JButton btnEliminar;
    private JButton btnLimpiar;

    private JTable tablaRevistas;
    private DefaultTableModel modeloTabla;

    private RevistaDAO revistaDAO;

    public VentanaRevista() {
        revistaDAO = new RevistaDAO();

        setTitle("Gestión de Revistas");
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

        panelCampos.add(new JLabel("Periodicidad:"));
        txtPeriodicidad = new JTextField();
        panelCampos.add(txtPeriodicidad);

        panelCampos.add(new JLabel("Editorial:"));
        txtEditorial = new JTextField();
        panelCampos.add(txtEditorial);

        panelCampos.add(new JLabel("Fecha de publicación:"));
        txtFechaPublicacion = new JTextField();
        panelCampos.add(txtFechaPublicacion);

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
        modeloTabla.addColumn("Periodicidad");
        modeloTabla.addColumn("Editorial");
        modeloTabla.addColumn("Fecha Publicación");

        tablaRevistas = new JTable(modeloTabla);
        JScrollPane scrollPane = new JScrollPane(tablaRevistas);
        add(scrollPane, BorderLayout.CENTER);

        btnGuardar.addActionListener(e -> guardarRevista());
        btnActualizar.addActionListener(e -> actualizarRevista());
        btnEliminar.addActionListener(e -> eliminarRevista());
        btnLimpiar.addActionListener(e -> limpiarCampos());

        tablaRevistas.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                cargarRevistaDesdeTabla();
            }
        });

        cargarTablaRevistas();
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

        if (txtPeriodicidad.getText().trim().isEmpty()) {
            return "El campo Periodicidad es obligatorio.";
        }

        if (txtEditorial.getText().trim().isEmpty()) {
            return "El campo Editorial es obligatorio.";
        }

        if (txtFechaPublicacion.getText().trim().isEmpty()) {
            return "El campo Fecha de publicación es obligatorio.";
        }

        try {
            Integer.parseInt(txtUnidades.getText().trim());
        } catch (NumberFormatException e) {
            return "El campo Unidades debe contener solo números.";
        }

        return null;
    }

    private Revista obtenerDatosRevista() {
        Revista revista = new Revista();

        if (!txtId.getText().trim().isEmpty()) {
            revista.setIdMaterial(Integer.parseInt(txtId.getText().trim()));
        }

        revista.setCodigoInterno(txtCodigo.getText().trim());
        revista.setTitulo(txtTitulo.getText().trim());
        revista.setUnidades(Integer.parseInt(txtUnidades.getText().trim()));
        revista.setPeriodicidad(txtPeriodicidad.getText().trim());
        revista.setEditorial(txtEditorial.getText().trim());
        revista.setFechaPublicacion(txtFechaPublicacion.getText().trim());

        return revista;
    }

    private void cargarTablaRevistas() {
        modeloTabla.setRowCount(0);

        List<Revista> listaRevistas = revistaDAO.obtenerRevistas();

        for (Revista revista : listaRevistas) {
            Object[] fila = {
                    revista.getIdMaterial(),
                    revista.getCodigoInterno(),
                    revista.getTitulo(),
                    revista.getUnidades(),
                    revista.getPeriodicidad(),
                    revista.getEditorial(),
                    revista.getFechaPublicacion()
            };

            modeloTabla.addRow(fila);
        }
    }

    private void cargarRevistaDesdeTabla() {
        int filaSeleccionada = tablaRevistas.getSelectedRow();

        if (filaSeleccionada != -1) {
            txtId.setText(modeloTabla.getValueAt(filaSeleccionada, 0).toString());
            txtCodigo.setText(modeloTabla.getValueAt(filaSeleccionada, 1).toString());
            txtTitulo.setText(modeloTabla.getValueAt(filaSeleccionada, 2).toString());
            txtUnidades.setText(modeloTabla.getValueAt(filaSeleccionada, 3).toString());
            txtPeriodicidad.setText(modeloTabla.getValueAt(filaSeleccionada, 4).toString());
            txtEditorial.setText(modeloTabla.getValueAt(filaSeleccionada, 5).toString());
            txtFechaPublicacion.setText(modeloTabla.getValueAt(filaSeleccionada, 6).toString());
        }
    }

    private void guardarRevista() {
        String mensajeValidacion = validarCampos();

        if (mensajeValidacion != null) {
            JOptionPane.showMessageDialog(this, mensajeValidacion);
            return;
        }

        try {
            Revista revista = obtenerDatosRevista();
            boolean guardado = revistaDAO.insertarRevista(revista);

            if (guardado) {
                JOptionPane.showMessageDialog(this, "Revista guardada correctamente.");
                limpiarCampos();
                cargarTablaRevistas();
            } else {
                JOptionPane.showMessageDialog(this, "No se pudo guardar la revista.");
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Ocurrió un error al guardar la revista.");
        }
    }

    private void actualizarRevista() {
        if (txtId.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Seleccione una revista de la tabla para actualizar.");
            return;
        }

        String mensajeValidacion = validarCampos();

        if (mensajeValidacion != null) {
            JOptionPane.showMessageDialog(this, mensajeValidacion);
            return;
        }

        try {
            Revista revista = obtenerDatosRevista();
            revistaDAO.actualizarRevista(revista);
            JOptionPane.showMessageDialog(this, "Proceso de actualización ejecutado.");
            limpiarCampos();
            cargarTablaRevistas();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Ocurrió un error al actualizar la revista.");
        }
    }

    private void eliminarRevista() {
        if (txtId.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Seleccione una revista de la tabla para eliminar.");
            return;
        }

        try {
            int id = Integer.parseInt(txtId.getText().trim());
            boolean eliminado = revistaDAO.eliminarRevista(id);

            if (eliminado) {
                JOptionPane.showMessageDialog(this, "Revista eliminada correctamente.");
                limpiarCampos();
                cargarTablaRevistas();
            } else {
                JOptionPane.showMessageDialog(this, "No se pudo eliminar la revista.");
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Ocurrió un error al eliminar la revista.");
        }
    }

    private void limpiarCampos() {
        txtId.setText("");
        txtCodigo.setText("");
        txtTitulo.setText("");
        txtUnidades.setText("");
        txtPeriodicidad.setText("");
        txtEditorial.setText("");
        txtFechaPublicacion.setText("");
        tablaRevistas.clearSelection();
    }
}