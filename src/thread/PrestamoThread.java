package thread;

import dao.PrestamoDAO;
import model.Prestamo;

public class PrestamoThread extends Thread {

    private final Prestamo prestamo;

    public PrestamoThread(Prestamo prestamo) {

        this.prestamo = prestamo;

    }

    @Override
    public void run() {

        PrestamoDAO prestamoDAO = new PrestamoDAO();

        boolean resultado = prestamoDAO.registrarPrestamo(prestamo);

        if (resultado) {
            System.out.println("Préstamo registrado correctamente");

        } else {
            System.out.println("Error al registrar préstamo");
        }
    }
}