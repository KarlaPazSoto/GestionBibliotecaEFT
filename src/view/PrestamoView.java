package view;

import controller.EstudianteController;
import controller.LibroController;
import controller.PrestamoController;
import model.Estudiante;
import model.Libro;
import model.Prestamo;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;
import java.time.LocalDate;

public class PrestamoView extends JFrame {

    private JComboBox<Estudiante> cboEstudiante;
    private JComboBox<Libro> cboLibro;

    private JTextField txtFechaPrestamo;
    private JTextField txtFechaDevolucion;

    private JButton btnRegistrar;
    private JButton btnDevolver;

    private JTable tabla;

    private EstudianteController estudianteController;
    private LibroController libroController;
    private PrestamoController prestamoController;

    public PrestamoView() {

        estudianteController = new EstudianteController();
        libroController = new LibroController();
        prestamoController = new PrestamoController();

        setTitle("Préstamos");
        setSize(900, 600);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        inicializarComponentes();

        cargarEstudiantes();
        cargarLibros();
        cargarTablaPrestamos();
        eventos();
    }

    private void inicializarComponentes() {

        JPanel formulario =
                new JPanel(new GridLayout(4, 2, 5, 5));

        cboEstudiante = new JComboBox<>();
        cboLibro = new JComboBox<>();

        txtFechaPrestamo = new JTextField();
        txtFechaDevolucion = new JTextField();

        formulario.add(new JLabel("Estudiante"));
        formulario.add(cboEstudiante);

        formulario.add(new JLabel("Libro"));
        formulario.add(cboLibro);

        formulario.add(new JLabel("Fecha Préstamo"));
        formulario.add(txtFechaPrestamo);

        formulario.add(new JLabel("Fecha Devolución"));
        formulario.add(txtFechaDevolucion);

        JPanel botones = new JPanel();

        btnRegistrar = new JButton("Registrar");
        btnDevolver = new JButton("Devolver");

        botones.add(btnRegistrar);
        botones.add(btnDevolver);

        tabla = new JTable();

        tabla.setModel(
                new DefaultTableModel(
                        new Object[][]{},
                        new String[]{
                                "ID",
                                "Estudiante",
                                "Libro",
                                "Fecha Préstamo",
                                "Fecha Devolución",
                                "Devuelto"
                        }
                )
        );

        setLayout(new BorderLayout());

        add(formulario, BorderLayout.NORTH);
        add(new JScrollPane(tabla), BorderLayout.CENTER);
        add(botones, BorderLayout.SOUTH);
    }

    private void cargarEstudiantes() {

        cboEstudiante.removeAllItems();

        List<Estudiante> estudiantes =
                estudianteController.listarEstudiantes();

        for (Estudiante estudiante : estudiantes) {
            cboEstudiante.addItem(estudiante);
        }
    }

    private void cargarLibros() {

        cboLibro.removeAllItems();

        List<Libro> libros =
                libroController.listarLibros();

        for (Libro libro : libros) {
            cboLibro.addItem(libro);
        }
    }

    private void cargarTablaPrestamos() {

        cargarPrestamos(
                prestamoController.listarPrestamos()
        );
    }

    public void cargarPrestamos(List<Prestamo> prestamos) {

        DefaultTableModel modelo =
                (DefaultTableModel) tabla.getModel();

        modelo.setRowCount(0);

        for (Prestamo p : prestamos) {

            modelo.addRow(new Object[]{
                    p.getId(),
                    p.getEstudiante().getNombre(),
                    p.getLibro().getTitulo(),
                    p.getFechaPrestamo(),
                    p.getFechaDevolucion(),
                    p.isDevuelto() ? "Sí" : "No"
            });
        }
    }

    private void eventos() {

        btnRegistrar.addActionListener(e -> {

            try {

                Estudiante estudiante =
                        (Estudiante) cboEstudiante.getSelectedItem();

                Libro libro =
                        (Libro) cboLibro.getSelectedItem();

                LocalDate fechaPrestamo =
                        LocalDate.parse(
                                txtFechaPrestamo.getText()
                        );

                LocalDate fechaDevolucion =
                        LocalDate.parse(
                                txtFechaDevolucion.getText()
                        );

                Prestamo prestamo =
                        new Prestamo();

                prestamo.setEstudiante(estudiante);
                prestamo.setLibro(libro);
                prestamo.setFechaPrestamo(fechaPrestamo);
                prestamo.setFechaDevolucion(fechaDevolucion);
                prestamo.setDevuelto(false);

                prestamoController.registrarPrestamo(prestamo);

                JOptionPane.showMessageDialog(
                        this,
                        "Préstamo registrado"
                );

                cargarTablaPrestamos();

            } catch (Exception ex) {

                JOptionPane.showMessageDialog(
                        this,
                        "Error al registrar préstamo.\n" +
                                "Formato fecha: yyyy-MM-dd"
                );
            }
        });

        btnDevolver.addActionListener(e -> {

            try {

                int fila =
                        tabla.getSelectedRow();

                if (fila == -1) {

                    JOptionPane.showMessageDialog(
                            this,
                            "Seleccione un préstamo"
                    );

                    return;
                }

                int idPrestamo =
                        (Integer) tabla.getValueAt(
                                fila,
                                0
                        );

                Prestamo prestamo =
                        prestamoController.buscarPrestamo(
                                idPrestamo
                        );

                if (prestamo == null) {

                    JOptionPane.showMessageDialog(
                            this,
                            "Préstamo no encontrado"
                    );

                    return;
                }

                boolean resultado =
                        prestamoController
                                .registrarDevolucion(
                                        prestamo
                                );

                if (resultado) {

                    JOptionPane.showMessageDialog(
                            this,
                            "Devolución registrada"
                    );

                    cargarTablaPrestamos();

                } else {

                    JOptionPane.showMessageDialog(
                            this,
                            "No fue posible registrar la devolución"
                    );
                }

            } catch (Exception ex) {

                JOptionPane.showMessageDialog(
                        this,
                        "Error al devolver libro"
                );
            }
        });

    }

    public JComboBox<Estudiante> getCboEstudiante() {
        return cboEstudiante;
    }

    public JComboBox<Libro> getCboLibro() {
        return cboLibro;
    }

    public JTextField getTxtFechaPrestamo() {
        return txtFechaPrestamo;
    }

    public JTextField getTxtFechaDevolucion() {
        return txtFechaDevolucion;
    }

    public JButton getBtnRegistrar() {
        return btnRegistrar;
    }

    public JButton getBtnDevolver() {
        return btnDevolver;
    }

    public JTable getTabla() {
        return tabla;
    }

    public int getFilaSeleccionada() {
        return tabla.getSelectedRow();
    }
}