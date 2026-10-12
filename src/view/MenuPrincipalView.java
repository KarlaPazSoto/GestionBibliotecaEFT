package view;

import model.Usuario;

import javax.swing.*;
import java.awt.*;

public class MenuPrincipalView extends JFrame {

    private JButton btnLibros;
    private JButton btnEstudiantes;
    private JButton btnPrestamos;
    private JButton btnReportes;
    private JButton btnSalir;

    private Usuario usuario;

    public MenuPrincipalView(Usuario usuario) {

        this.usuario = usuario;

        setTitle("Menú Principal");
        setSize(500, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);

        inicializarComponentes();
        configurarPermisos();
    }

    private void inicializarComponentes() {

        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(5, 1, 10, 10));

        panel.setBorder(
                BorderFactory.createEmptyBorder(
                        30,
                        50,
                        30,
                        50
                )
        );

        btnLibros = new JButton("Gestión de Libros");
        btnEstudiantes = new JButton("Gestión de Estudiantes");
        btnPrestamos = new JButton("Préstamos");
        btnReportes = new JButton("Reportes");
        btnSalir = new JButton("Cerrar Sesión");

        panel.add(btnLibros);
        panel.add(btnEstudiantes);
        panel.add(btnPrestamos);
        panel.add(btnReportes);
        panel.add(btnSalir);

        add(panel);

        btnLibros.addActionListener(e ->
                new LibroView().setVisible(true));

        btnEstudiantes.addActionListener(e ->
                new EstudianteView().setVisible(true));

        btnPrestamos.addActionListener(e ->
                new PrestamoView().setVisible(true));

        btnReportes.addActionListener(e ->
                new ReporteView().setVisible(true));

        btnSalir.addActionListener(e -> {

            dispose();

            new LoginView().setVisible(true);

        });
    }

    private void configurarPermisos() {

        if(usuario.getRol().equalsIgnoreCase("estudiante")) {

            btnLibros.setEnabled(false);
            btnEstudiantes.setEnabled(false);

        }
    }
}