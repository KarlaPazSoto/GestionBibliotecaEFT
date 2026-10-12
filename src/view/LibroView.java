package view;

import controller.LibroController;
import dao.CategoriaDAO;
import model.Categoria;
import model.Libro;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class LibroView extends JFrame {

    private JTextField txtTitulo;
    private JTextField txtAutor;
    private JTextField txtIsbn;
    private JTextField txtEditorial;
    private JTextField txtStock;

    private JComboBox<String> cboCategoria;

    private JButton btnGuardar;
    private JButton btnActualizar;
    private JButton btnEliminar;
    private JButton btnLimpiar;

    private JTable tabla;

    private LibroController libroController;
    private CategoriaDAO categoriaDAO;

    public LibroView() {

        libroController = new LibroController();
        categoriaDAO = new CategoriaDAO();

        setTitle("Gestión de Libros");
        setSize(900, 600);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        inicializarComponentes();
        cargarCategorias();
        cargarTabla();
        eventos();
    }

    private void inicializarComponentes() {

        JPanel formulario =
                new JPanel(new GridLayout(6,2,5,5));

        formulario.setBorder(
                BorderFactory.createTitledBorder(
                        "Datos del Libro"
                )
        );

        txtTitulo = new JTextField();
        txtAutor = new JTextField();
        txtIsbn = new JTextField();
        txtEditorial = new JTextField();
        txtStock = new JTextField();

        cboCategoria = new JComboBox<>();

        formulario.add(new JLabel("Título"));
        formulario.add(txtTitulo);

        formulario.add(new JLabel("Autor"));
        formulario.add(txtAutor);

        formulario.add(new JLabel("ISBN"));
        formulario.add(txtIsbn);

        formulario.add(new JLabel("Editorial"));
        formulario.add(txtEditorial);

        formulario.add(new JLabel("Stock"));
        formulario.add(txtStock);

        formulario.add(new JLabel("Categoría"));
        formulario.add(cboCategoria);

        JPanel panelBotones = new JPanel();

        btnGuardar = new JButton("Guardar");
        btnActualizar = new JButton("Actualizar");
        btnEliminar = new JButton("Eliminar");
        btnLimpiar = new JButton("Limpiar");

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
                                "Título",
                                "Autor",
                                "ISBN",
                                "Editorial",
                                "Stock",
                                "Categoría"
                        }
                )
        );

        JScrollPane scrollPane =
                new JScrollPane(tabla);

        setLayout(new BorderLayout());

        add(formulario, BorderLayout.NORTH);
        add(scrollPane, BorderLayout.CENTER);
        add(panelBotones, BorderLayout.SOUTH);
    }

    private void cargarCategorias() {

        cboCategoria.removeAllItems();

        for(Categoria categoria :
                categoriaDAO.listar()) {

            cboCategoria.addItem(
                    categoria.getNombre()
            );
        }
    }

    private void cargarTabla() {

        DefaultTableModel modelo =
                (DefaultTableModel)
                        tabla.getModel();

        modelo.setRowCount(0);

        for(Libro libro :
                libroController.listarLibros()) {

            String categoria = "";

            if(libro.getCategoria() != null) {

                categoria =
                        libro.getCategoria()
                                .getNombre();
            }

            modelo.addRow(new Object[]{

                    libro.getId(),
                    libro.getTitulo(),
                    libro.getAutor(),
                    libro.getIsbn(),
                    libro.getEditorial(),
                    libro.getStock(),
                    categoria

            });
        }
    }

    private void limpiarCampos() {

        txtTitulo.setText("");
        txtAutor.setText("");
        txtIsbn.setText("");
        txtEditorial.setText("");
        txtStock.setText("");

        if(cboCategoria.getItemCount() > 0) {

            cboCategoria.setSelectedIndex(0);

        }
    }

    private void eventos() {

        btnGuardar.addActionListener(e -> {

            try {

                Libro libro = new Libro();

                libro.setTitulo(
                        txtTitulo.getText()
                );

                libro.setAutor(
                        txtAutor.getText()
                );

                libro.setIsbn(
                        txtIsbn.getText()
                );

                libro.setEditorial(
                        txtEditorial.getText()
                );

                libro.setStock(
                        Integer.parseInt(
                                txtStock.getText()
                        )
                );

                Categoria categoria =
                        categoriaDAO.buscarPorNombre(
                                cboCategoria
                                        .getSelectedItem()
                                        .toString()
                        );

                libro.setCategoria(categoria);

                if(libroController
                        .registrarLibro(libro)) {

                    JOptionPane
                            .showMessageDialog(
                                    this,
                                    "Libro registrado"
                            );

                    cargarTabla();
                    limpiarCampos();

                }

            } catch (Exception ex) {

                JOptionPane.showMessageDialog(
                        this,
                        "Datos inválidos"
                );

            }
        });

        btnActualizar.addActionListener(e -> {

            int fila = tabla.getSelectedRow();

            if (fila < 0) {

                JOptionPane.showMessageDialog(
                        this,
                        "Seleccione un libro"
                );

                return;
            }

            try {

                Libro libro = new Libro();

                libro.setId(
                        Integer.parseInt(
                                tabla.getValueAt(fila, 0)
                                        .toString()
                        )
                );

                libro.setTitulo(
                        txtTitulo.getText()
                );

                libro.setAutor(
                        txtAutor.getText()
                );

                libro.setIsbn(
                        txtIsbn.getText()
                );

                libro.setEditorial(
                        txtEditorial.getText()
                );

                libro.setStock(
                        Integer.parseInt(
                                txtStock.getText()
                        )
                );

                Categoria categoria =
                        categoriaDAO.buscarPorNombre(
                                cboCategoria
                                        .getSelectedItem()
                                        .toString()
                        );

                libro.setCategoria(categoria);

                if (libroController
                        .actualizarLibro(libro)) {

                    JOptionPane.showMessageDialog(
                            this,
                            "Libro actualizado"
                    );

                    cargarTabla();
                    limpiarCampos();
                }

            } catch (Exception ex) {

                JOptionPane.showMessageDialog(
                        this,
                        "Error al actualizar"
                );

            }

        });

        btnEliminar.addActionListener(e -> {

            int fila =
                    tabla.getSelectedRow();

            if(fila < 0) {

                JOptionPane.showMessageDialog(
                        this,
                        "Seleccione un libro"
                );

                return;
            }

            Libro libro = new Libro();

            libro.setId(
                    Integer.parseInt(
                            tabla.getValueAt(
                                    fila,
                                    0
                            ).toString()
                    )
            );

            libroController
                    .eliminarLibro(libro);

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

                        txtTitulo.setText(
                                tabla.getValueAt(fila,1)
                                        .toString()
                        );

                        txtAutor.setText(
                                tabla.getValueAt(fila,2)
                                        .toString()
                        );

                        txtIsbn.setText(
                                tabla.getValueAt(fila,3)
                                        .toString()
                        );

                        txtEditorial.setText(
                                tabla.getValueAt(fila,4)
                                        .toString()
                        );

                        txtStock.setText(
                                tabla.getValueAt(fila,5)
                                        .toString()
                        );

                    }

                });

    }
}