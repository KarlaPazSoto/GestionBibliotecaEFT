package controller;

import dao.LibroDAO;
import model.Libro;

import java.util.List;

public class LibroController {

    private final LibroDAO libroDAO;

    public LibroController() {

        libroDAO = new LibroDAO();

    }

    public boolean registrarLibro(Libro libro) {

        return libroDAO.crear(libro);

    }

    public boolean actualizarLibro(Libro libro) {

        return libroDAO.actualizar(libro);

    }

    public boolean eliminarLibro(Libro libro) {

        return libroDAO.eliminar(libro);

    }

    public Libro buscarLibro(int id) {

        return libroDAO.buscarPorId(id);

    }

    public List<Libro> listarLibros() {

        return libroDAO.listar();

    }
}