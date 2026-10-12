package view;

import controller.EstudianteController;
import model.Estudiante;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class EstudianteView extends JFrame {

    private JTextField txtNombre;
    private JTextField txtRut;
    private JTextField txtCurso;
    private JTextField txtCorreo;

    private JButton btnGuardar;
    private JButton btnActualizar;
    private JButton btnEliminar;
    private JButton btnLimpiar;

    private JTable tabla;

    private EstudianteController estudianteController;

    public EstudianteView() {

        estudianteController =
                new EstudianteController();

        setTitle("Gestión de Estudiantes");
        setSize(850, 600);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(
                JFrame.DISPOSE_ON_CLOSE
        );

        inicializarComponentes();
        cargarTabla();
        eventos();
    }

    private void inicializarComponentes() {

        JPanel formulario =
                new JPanel(
                        new GridLayout(4,2,5,5)
                );

        formulario.setBorder(
                BorderFactory.createTitledBorder(
                        "Datos del Estudiante"
                )
        );

        txtNombre = new JTextField();
        txtRut = new JTextField();
        txtCurso = new JTextField();
        txtCorreo = new JTextField();

        formulario.add(
                new JLabel("Nombre")
        );

        formulario.add(txtNombre);

        formulario.add(
                new JLabel("RUT")
        );

        formulario.add(txtRut);

        formulario.add(
                new JLabel("Curso")
        );

        formulario.add(txtCurso);

        formulario.add(
                new JLabel("Correo")
        );

        formulario.add(txtCorreo);

        JPanel panelBotones =
                new JPanel();

        btnGuardar =
                new JButton("Guardar");

        btnActualizar =
                new JButton("Actualizar");

        btnEliminar =
                new JButton("Eliminar");

        btnLimpiar =
                new JButton("Limpiar");

        panelBotones.add(btnGuardar);
        panelBotones.add(btnActualizar);
        panelBotones.add(btnEliminar);
        panelBotones.add(btnLimpiar);

        tabla = new JTable();

        tabla.setModel(
                new DefaultTableModel(
                        new Object[][]{},
                        new String[]{
                                "ID",
                                "Nombre",
                                "RUT",
                                "Curso",
                                "Correo"
                        }
                )
        );

        JScrollPane scrollPane =
                new JScrollPane(tabla);

        setLayout(new BorderLayout());

        add(formulario,
                BorderLayout.NORTH);

        add(scrollPane,
                BorderLayout.CENTER);

        add(panelBotones,
                BorderLayout.SOUTH);
    }

    private void cargarTabla() {

        DefaultTableModel modelo =
                (DefaultTableModel)
                        tabla.getModel();

        modelo.setRowCount(0);

        for (Estudiante estudiante :
                estudianteController
                        .listarEstudiantes()) {

            modelo.addRow(new Object[]{

                    estudiante.getId(),
                    estudiante.getNombre(),
                    estudiante.getRut(),
                    estudiante.getCurso(),
                    estudiante.getCorreo()

            });

        }

    }

    private void limpiarCampos() {

        txtNombre.setText("");
        txtRut.setText("");
        txtCurso.setText("");
        txtCorreo.setText("");

    }

    private void eventos() {

        btnGuardar.addActionListener(e -> {

            Estudiante estudiante =
                    new Estudiante();

            estudiante.setNombre(
                    txtNombre.getText()
            );

            estudiante.setRut(
                    txtRut.getText()
            );

            estudiante.setCurso(
                    txtCurso.getText()
            );

            estudiante.setCorreo(
                    txtCorreo.getText()
            );

            if(estudianteController
                    .registrarEstudiante(
                            estudiante
                    )) {

                JOptionPane.showMessageDialog(
                        this,
                        "Estudiante registrado"
                );

                cargarTabla();
                limpiarCampos();
            }

        });

        btnActualizar.addActionListener(e -> {

            int fila =
                    tabla.getSelectedRow();

            if(fila < 0) {

                JOptionPane.showMessageDialog(
                        this,
                        "Seleccione un estudiante"
                );

                return;
            }

            Estudiante estudiante =
                    new Estudiante();

            estudiante.setId(
                    Integer.parseInt(
                            tabla.getValueAt(
                                    fila,
                                    0
                            ).toString()
                    )
            );

            estudiante.setNombre(
                    txtNombre.getText()
            );

            estudiante.setRut(
                    txtRut.getText()
            );

            estudiante.setCurso(
                    txtCurso.getText()
            );

            estudiante.setCorreo(
                    txtCorreo.getText()
            );

            estudianteController
                    .actualizarEstudiante(
                            estudiante
                    );

            cargarTabla();
            limpiarCampos();

        });

        btnEliminar.addActionListener(e -> {

            int fila =
                    tabla.getSelectedRow();

            if(fila < 0) {

                JOptionPane.showMessageDialog(
                        this,
                        "Seleccione un estudiante"
                );

                return;
            }

            Estudiante estudiante =
                    new Estudiante();

            estudiante.setId(
                    Integer.parseInt(
                            tabla.getValueAt(
                                    fila,
                                    0
                            ).toString()
                    )
            );

            estudianteController
                    .eliminarEstudiante(
                            estudiante
                    );

            cargarTabla();
            limpiarCampos();

        });

        btnLimpiar.addActionListener(e ->
                limpiarCampos());

        tabla.getSelectionModel()
                .addListSelectionListener(e -> {

                    int fila =
                            tabla.getSelectedRow();

                    if(fila >= 0) {

                        txtNombre.setText(
                                tabla.getValueAt(
                                        fila,
                                        1
                                ).toString()
                        );

                        txtRut.setText(
                                tabla.getValueAt(
                                        fila,
                                        2
                                ).toString()
                        );

                        txtCurso.setText(
                                tabla.getValueAt(
                                        fila,
                                        3
                                ).toString()
                        );

                        txtCorreo.setText(
                                tabla.getValueAt(
                                        fila,
                                        4
                                ).toString()
                        );

                    }

                });

    }
}