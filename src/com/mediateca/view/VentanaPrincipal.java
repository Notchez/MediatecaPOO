package com.mediateca.view;

import javax.swing.*;
import java.awt.*;

public class VentanaPrincipal extends JFrame {

    private JButton btnLibros;
    private JButton btnRevistas;
    private JButton btnCdAudio;
    private JButton btnDvd;

    public VentanaPrincipal() {
        setTitle("Sistema de Mediateca");
        setSize(500, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new GridLayout(4, 1, 10, 10));

        btnLibros = new JButton("Gestionar Libros");
        btnRevistas = new JButton("Gestionar Revistas");
        btnCdAudio = new JButton("Gestionar CD Audio");
        btnDvd = new JButton("Gestionar DVD");

        add(btnLibros);
        add(btnRevistas);
        add(btnCdAudio);
        add(btnDvd);

        btnLibros.addActionListener(e -> {
            VentanaLibro ventanaLibro = new VentanaLibro();
            ventanaLibro.setVisible(true);
        });

        btnRevistas.addActionListener(e -> {
            VentanaRevista ventanaRevista = new VentanaRevista();
            ventanaRevista.setVisible(true);
        });

        btnCdAudio.addActionListener(e -> {
            VentanaCdAudio ventanaCdAudio = new VentanaCdAudio();
            ventanaCdAudio.setVisible(true);
        });

        btnDvd.addActionListener(e -> {
            VentanaDvd ventanaDvd = new VentanaDvd();
            ventanaDvd.setVisible(true);
        });
    }
}