package com.mediateca.view;

import com.mediateca.dao.LibroDAO;
import com.mediateca.model.Libro;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class VentanaLibro extends JFrame {

    private JTextField txtId;
    private JTextField txtCodigo;
    private JTextField txtTitulo;
    private JTextField txtUnidades;
    private JTextField txtAutor;
    private JTextField txtEditorial;
    private JTextField txtPaginas;
    private JTextField txtIsbn;
    private JTextField txtAnio;

    private JButton btnGuardar;
    private JButton btnActualizar;
    private JButton btnEliminar;
    private JButton btnLimpiar;

    private JTable tablaLibros;
    private DefaultTableModel modeloTabla;

    private LibroDAO libroDAO;

    public VentanaLibro() {
        libroDAO = new LibroDAO();

        setTitle("Gestión de Libros");
        setSize(950, 600);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout(10, 10));

        JPanel panelCampos = new JPanel(new GridLayout(9, 2, 5, 5));

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

        panelCampos.add(new JLabel("Autor:"));
        txtAutor = new JTextField();
        panelCampos.add(txtAutor);

        panelCampos.add(new JLabel("Editorial:"));
        txtEditorial = new JTextField();
        panelCampos.add(txtEditorial);

        panelCampos.add(new JLabel("Número de páginas:"));
        txtPaginas = new JTextField();
        panelCampos.add(txtPaginas);

        panelCampos.add(new JLabel("ISBN:"));
        txtIsbn = new JTextField();
        panelCampos.add(txtIsbn);

        panelCampos.add(new JLabel("Año publicado:"));
        txtAnio = new JTextField();
        panelCampos.add(txtAnio);

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
        modeloTabla.addColumn("Autor");
        modeloTabla.addColumn("Editorial");
        modeloTabla.addColumn("Páginas");
        modeloTabla.addColumn("ISBN");
        modeloTabla.addColumn("Año");

        tablaLibros = new JTable(modeloTabla);
        JScrollPane scrollPane = new JScrollPane(tablaLibros);
        add(scrollPane, BorderLayout.CENTER);

        btnGuardar.addActionListener(e -> guardarLibro());
        btnActualizar.addActionListener(e -> actualizarLibro());
        btnEliminar.addActionListener(e -> eliminarLibro());
        btnLimpiar.addActionListener(e -> limpiarCampos());

        tablaLibros.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                cargarLibroDesdeTabla();
            }
        });

        cargarTablaLibros();
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

        if (txtAutor.getText().trim().isEmpty()) {
            return "El campo Autor es obligatorio.";
        }

        if (txtEditorial.getText().trim().isEmpty()) {
            return "El campo Editorial es obligatorio.";
        }

        if (txtPaginas.getText().trim().isEmpty()) {
            return "El campo Número de páginas es obligatorio.";
        }

        if (txtIsbn.getText().trim().isEmpty()) {
            return "El campo ISBN es obligatorio.";
        }

        if (txtAnio.getText().trim().isEmpty()) {
            return "El campo Año publicado es obligatorio.";
        }

        try {
            Integer.parseInt(txtUnidades.getText().trim());
        } catch (NumberFormatException e) {
            return "El campo Unidades debe contener solo números.";
        }

        try {
            Integer.parseInt(txtPaginas.getText().trim());
        } catch (NumberFormatException e) {
            return "El campo Número de páginas debe contener solo números.";
        }

        try {
            Integer.parseInt(txtAnio.getText().trim());
        } catch (NumberFormatException e) {
            return "El campo Año publicado debe contener solo números.";
        }

        return null;
    }

    private Libro obtenerDatosLibro() {
        Libro libro = new Libro();

        if (!txtId.getText().trim().isEmpty()) {
            libro.setIdMaterial(Integer.parseInt(txtId.getText().trim()));
        }

        libro.setCodigoInterno(txtCodigo.getText().trim());
        libro.setTitulo(txtTitulo.getText().trim());
        libro.setUnidades(Integer.parseInt(txtUnidades.getText().trim()));
        libro.setAutor(txtAutor.getText().trim());
        libro.setEditorial(txtEditorial.getText().trim());
        libro.setNumeroPaginas(Integer.parseInt(txtPaginas.getText().trim()));
        libro.setIsbn(txtIsbn.getText().trim());
        libro.setAnioPublicado(Integer.parseInt(txtAnio.getText().trim()));

        return libro;
    }

    private void cargarTablaLibros() {
        modeloTabla.setRowCount(0);

        List<Libro> listaLibros = libroDAO.obtenerLibros();

        for (Libro libro : listaLibros) {
            Object[] fila = {
                    libro.getIdMaterial(),
                    libro.getCodigoInterno(),
                    libro.getTitulo(),
                    libro.getUnidades(),
                    libro.getAutor(),
                    libro.getEditorial(),
                    libro.getNumeroPaginas(),
                    libro.getIsbn(),
                    libro.getAnioPublicado()
            };

            modeloTabla.addRow(fila);
        }
    }

    private void cargarLibroDesdeTabla() {
        int filaSeleccionada = tablaLibros.getSelectedRow();

        if (filaSeleccionada != -1) {
            txtId.setText(modeloTabla.getValueAt(filaSeleccionada, 0).toString());
            txtCodigo.setText(modeloTabla.getValueAt(filaSeleccionada, 1).toString());
            txtTitulo.setText(modeloTabla.getValueAt(filaSeleccionada, 2).toString());
            txtUnidades.setText(modeloTabla.getValueAt(filaSeleccionada, 3).toString());
            txtAutor.setText(modeloTabla.getValueAt(filaSeleccionada, 4).toString());
            txtEditorial.setText(modeloTabla.getValueAt(filaSeleccionada, 5).toString());
            txtPaginas.setText(modeloTabla.getValueAt(filaSeleccionada, 6).toString());
            txtIsbn.setText(modeloTabla.getValueAt(filaSeleccionada, 7).toString());
            txtAnio.setText(modeloTabla.getValueAt(filaSeleccionada, 8).toString());
        }
    }

    private void guardarLibro() {
        String mensajeValidacion = validarCampos();

        if (mensajeValidacion != null) {
            JOptionPane.showMessageDialog(this, mensajeValidacion);
            return;
        }

        try {
            Libro libro = obtenerDatosLibro();
            boolean guardado = libroDAO.insertarLibro(libro);

            if (guardado) {
                JOptionPane.showMessageDialog(this, "Libro guardado correctamente.");
                limpiarCampos();
                cargarTablaLibros();
            } else {
                JOptionPane.showMessageDialog(this, "No se pudo guardar el libro.");
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Ocurrió un error al guardar el libro.");
        }
    }

    private void actualizarLibro() {
        if (txtId.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Seleccione un libro de la tabla para actualizar.");
            return;
        }

        String mensajeValidacion = validarCampos();

        if (mensajeValidacion != null) {
            JOptionPane.showMessageDialog(this, mensajeValidacion);
            return;
        }

        try {
            Libro libro = obtenerDatosLibro();
            libroDAO.actualizarLibro(libro);
            JOptionPane.showMessageDialog(this, "Libro actualizado correctamente.");
            limpiarCampos();
            cargarTablaLibros();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Ocurrió un error al actualizar el libro.");
        }
    }

    private void eliminarLibro() {
        if (txtId.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Seleccione un libro de la tabla para eliminar.");
            return;
        }

        try {
            int id = Integer.parseInt(txtId.getText().trim());
            boolean eliminado = libroDAO.eliminarLibro(id);

            if (eliminado) {
                JOptionPane.showMessageDialog(this, "Libro eliminado correctamente.");
                limpiarCampos();
                cargarTablaLibros();
            } else {
                JOptionPane.showMessageDialog(this, "No se pudo eliminar el libro.");
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Ocurrió un error al eliminar el libro.");
        }
    }

    private void limpiarCampos() {
        txtId.setText("");
        txtCodigo.setText("");
        txtTitulo.setText("");
        txtUnidades.setText("");
        txtAutor.setText("");
        txtEditorial.setText("");
        txtPaginas.setText("");
        txtIsbn.setText("");
        txtAnio.setText("");
        tablaLibros.clearSelection();
    }
}