package view;

import controller.LoginController;
import model.Usuario;

import javax.swing.*;
import java.awt.*;

public class LoginView extends JFrame {

    private JTextField txtCorreo;
    private JPasswordField txtPassword;
    private JButton btnIngresar;

    private LoginController loginController;

    public LoginView() {

        loginController = new LoginController();

        inicializarComponentes();

        setTitle("Sistema de Biblioteca Escolar");
        setSize(400, 250);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);
    }

    private void inicializarComponentes() {

        JPanel panelPrincipal = new JPanel();
        panelPrincipal.setLayout(new BorderLayout());

        JLabel lblTitulo = new JLabel(
                "SISTEMA DE BIBLIOTECA",
                SwingConstants.CENTER
        );

        lblTitulo.setFont(
                new Font("Segoe UI",
                        Font.BOLD,
                        20)
        );

        lblTitulo.setBorder(
                BorderFactory.createEmptyBorder(
                        20,
                        10,
                        20,
                        10
                )
        );

        panelPrincipal.add(
                lblTitulo,
                BorderLayout.NORTH
        );

        JPanel panelFormulario = new JPanel(
                new GridLayout(3, 2, 10, 10)
        );

        panelFormulario.setBorder(
                BorderFactory.createEmptyBorder(
                        10,
                        30,
                        20,
                        30
                )
        );

        panelFormulario.add(
                new JLabel("Correo:")
        );

        txtCorreo = new JTextField();

        panelFormulario.add(
                txtCorreo
        );

        panelFormulario.add(
                new JLabel("Contraseña:")
        );

        txtPassword = new JPasswordField();

        panelFormulario.add(
                txtPassword
        );

        panelFormulario.add(
                new JLabel("")
        );

        btnIngresar = new JButton(
                "Iniciar Sesión"
        );

        panelFormulario.add(
                btnIngresar
        );

        panelPrincipal.add(
                panelFormulario,
                BorderLayout.CENTER
        );

        add(panelPrincipal);

        eventos();
    }

    private void eventos() {

        btnIngresar.addActionListener(e -> {

            String correo =
                    txtCorreo.getText();

            String password =
                    new String(
                            txtPassword.getPassword()
                    );

            Usuario usuario =
                    loginController
                            .iniciarSesion(
                                    correo,
                                    password
                            );

            if (usuario != null) {

                JOptionPane.showMessageDialog(
                        this,
                        "Bienvenido " +
                                usuario.getNombre()
                );

                MenuPrincipalView menu =
                        new MenuPrincipalView(
                                usuario
                        );

                menu.setVisible(true);

                dispose();

            } else {

                JOptionPane.showMessageDialog(
                        this,
                        "Correo o contraseña incorrectos",
                        "Error",
                        JOptionPane.ERROR_MESSAGE
                );

            }

        });

    }

}