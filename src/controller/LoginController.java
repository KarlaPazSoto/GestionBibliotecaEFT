package controller;

import dao.UsuarioDAO;
import model.Usuario;

public class LoginController {

    private final UsuarioDAO usuarioDAO;

    public LoginController() {

        usuarioDAO = new UsuarioDAO();

    }

    public Usuario iniciarSesion(String correo, String password) {

        return usuarioDAO.autenticar(correo, password);
    }
}