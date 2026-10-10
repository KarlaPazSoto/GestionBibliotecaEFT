package controller;

import dao.PrestamoDAO;
import model.Prestamo;
import thread.PrestamoThread;

import java.util.List;

public class PrestamoController {

    private final PrestamoDAO prestamoDAO;

    public PrestamoController() {

        prestamoDAO = new PrestamoDAO();

    }

    public void registrarPrestamo(Prestamo prestamo) {

        PrestamoThread hilo = new PrestamoThread(prestamo);

        hilo.start();

    }

    public boolean registrarDevolucion(
            Prestamo prestamo) {

        return prestamoDAO.registrarDevolucion(prestamo);

    }

    public Prestamo buscarPrestamo(int id) {

        return prestamoDAO.buscarPorId(id);

    }

    public List<Prestamo> listarPrestamos() {

        return prestamoDAO.listar();

    }
}