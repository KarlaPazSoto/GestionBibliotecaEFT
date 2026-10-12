package view;

import dao.PrestamoDAO;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class ReporteView extends JFrame {

    private JTable tabla;

    private JButton btnLibrosPrestados;
    private JButton btnHistorial;
    private JButton btnPrestamosActivos;

    private PrestamoDAO prestamoDAO;

    public ReporteView() {

        prestamoDAO = new PrestamoDAO();

        setTitle("Reportes");
        setSize(900, 600);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        inicializarComponentes();
    }

    private void inicializarComponentes() {

        JPanel superior = new JPanel();

        btnLibrosPrestados =
                new JButton("Libros Más Prestados");

        btnHistorial =
                new JButton("Historial Estudiante");

        btnPrestamosActivos =
                new JButton("Préstamos Activos");

        superior.add(btnLibrosPrestados);
        superior.add(btnHistorial);
        superior.add(btnPrestamosActivos);

        tabla = new JTable();

        tabla.setModel(
                new DefaultTableModel()
        );

        setLayout(new BorderLayout());

        add(superior, BorderLayout.NORTH);

        add(
                new JScrollPane(tabla),
                BorderLayout.CENTER
        );

        eventos();
    }

    private void eventos() {

        btnLibrosPrestados.addActionListener(e -> {

            DefaultTableModel modelo =
                    new DefaultTableModel();

            modelo.addColumn("Libro");
            modelo.addColumn("Cantidad");

            List<Object[]> datos =
                    prestamoDAO.librosMasPrestados();

            for (Object[] fila : datos) {

                modelo.addRow(fila);
            }

            tabla.setModel(modelo);
        });

        btnHistorial.addActionListener(e -> {

            DefaultTableModel modelo =
                    new DefaultTableModel();

            modelo.addColumn("Estudiante");
            modelo.addColumn("Libro");
            modelo.addColumn("Fecha Préstamo");
            modelo.addColumn("Fecha Devolución");

            List<Object[]> datos =
                    prestamoDAO.historialEstudiantes();

            for (Object[] fila : datos) {

                modelo.addRow(fila);
            }

            tabla.setModel(modelo);
        });

        btnPrestamosActivos.addActionListener(e -> {

            DefaultTableModel modelo =
                    new DefaultTableModel();

            modelo.addColumn("Libro");
            modelo.addColumn("Estudiante");
            modelo.addColumn("Fecha Devolución");

            List<Object[]> datos =
                    prestamoDAO.prestamosActivos();

            for (Object[] fila : datos) {

                modelo.addRow(fila);
            }

            tabla.setModel(modelo);
        });
    }
}