package dao;

import database.DatabaseConnection;
import model.Libro;
import model.Prestamo;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class PrestamoDAO implements CrudDAO<Prestamo> {

    private final Connection conn;

    public PrestamoDAO() {

        conn = DatabaseConnection
                .getInstance()
                .getConnection();

    }

    @Override
    public synchronized boolean crear(Prestamo prestamo) {

        String sql =
                "INSERT INTO prestamos " +
                        "(id_estudiante, id_libro, fecha_prestamo, fecha_devolucion, devuelto) " +
                        "VALUES (?, ?, ?, ?, ?)";

        try (PreparedStatement stmt =
                     conn.prepareStatement(sql)) {

            stmt.setInt(
                    1,
                    prestamo.getEstudiante().getId()
            );

            stmt.setInt(
                    2,
                    prestamo.getLibro().getId()
            );

            stmt.setDate(
                    3,
                    Date.valueOf(
                            prestamo.getFechaPrestamo()
                    )
            );

            stmt.setDate(
                    4,
                    Date.valueOf(
                            prestamo.getFechaDevolucion()
                    )
            );

            stmt.setBoolean(
                    5,
                    prestamo.isDevuelto()
            );

            return stmt.executeUpdate() > 0;

        } catch (SQLException e) {

            System.out.println(
                    "Error al crear préstamo: "
                            + e.getMessage()
            );

            return false;
        }
    }

    @Override
    public boolean actualizar(Prestamo prestamo) {

        String sql =
                "UPDATE prestamos " +
                        "SET id_estudiante=?, " +
                        "id_libro=?, " +
                        "fecha_prestamo=?, " +
                        "fecha_devolucion=?, " +
                        "devuelto=? " +
                        "WHERE id=?";

        try (PreparedStatement stmt =
                     conn.prepareStatement(sql)) {

            stmt.setInt(
                    1,
                    prestamo.getEstudiante().getId()
            );

            stmt.setInt(
                    2,
                    prestamo.getLibro().getId()
            );

            stmt.setDate(
                    3,
                    Date.valueOf(
                            prestamo.getFechaPrestamo()
                    )
            );

            stmt.setDate(
                    4,
                    Date.valueOf(
                            prestamo.getFechaDevolucion()
                    )
            );

            stmt.setBoolean(
                    5,
                    prestamo.isDevuelto()
            );

            stmt.setInt(
                    6,
                    prestamo.getId()
            );

            return stmt.executeUpdate() > 0;

        } catch (SQLException e) {

            System.out.println(
                    "Error al actualizar préstamo: "
                            + e.getMessage()
            );

            return false;
        }
    }

    @Override
    public boolean eliminar(Prestamo prestamo) {

        String sql =
                "DELETE FROM prestamos WHERE id=?";

        try (PreparedStatement stmt =
                     conn.prepareStatement(sql)) {

            stmt.setInt(
                    1,
                    prestamo.getId()
            );

            return stmt.executeUpdate() > 0;

        } catch (SQLException e) {

            System.out.println(
                    "Error al eliminar préstamo: "
                            + e.getMessage()
            );

            return false;
        }
    }

    @Override
    public Prestamo buscarPorId(int id) {

        String sql =
                "SELECT * FROM prestamos WHERE id=?";

        try (PreparedStatement stmt =
                     conn.prepareStatement(sql)) {

            stmt.setInt(1, id);

            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {

                Prestamo prestamo =
                        new Prestamo();

                prestamo.setId(
                        rs.getInt("id")
                );

                prestamo.setFechaPrestamo(
                        rs.getDate("fecha_prestamo")
                                .toLocalDate()
                );

                prestamo.setFechaDevolucion(
                        rs.getDate("fecha_devolucion")
                                .toLocalDate()
                );

                prestamo.setDevuelto(
                        rs.getBoolean("devuelto")
                );

                return prestamo;
            }

        } catch (SQLException e) {

            System.out.println(
                    "Error al buscar préstamo: "
                            + e.getMessage()
            );

        }

        return null;
    }

    @Override
    public List<Prestamo> listar() {

        List<Prestamo> prestamos =
                new ArrayList<>();

        String sql =
                "SELECT * FROM prestamos";

        try (PreparedStatement stmt =
                     conn.prepareStatement(sql);

             ResultSet rs =
                     stmt.executeQuery()) {

            while (rs.next()) {

                Prestamo prestamo =
                        new Prestamo();

                prestamo.setId(
                        rs.getInt("id")
                );

                prestamo.setFechaPrestamo(
                        rs.getDate(
                                "fecha_prestamo"
                        ).toLocalDate()
                );

                prestamo.setFechaDevolucion(
                        rs.getDate(
                                "fecha_devolucion"
                        ).toLocalDate()
                );

                prestamo.setDevuelto(
                        rs.getBoolean(
                                "devuelto"
                        )
                );

                prestamos.add(prestamo);

            }

        } catch (SQLException e) {

            System.out.println(
                    "Error al listar préstamos: "
                            + e.getMessage()
            );
        }

        return prestamos;
    }

    public synchronized boolean registrarPrestamo(
            Prestamo prestamo) {

        LibroDAO libroDAO =
                new LibroDAO();

        int stock =
                prestamo.getLibro().getStock();

        if (stock <= 0) {

            System.out.println(
                    "No hay stock disponible"
            );

            return false;
        }

        prestamo.getLibro()
                .setStock(stock - 1);

        libroDAO.actualizar(
                prestamo.getLibro()
        );

        return crear(prestamo);
    }

    public synchronized boolean registrarDevolucion(
            Prestamo prestamo) {

        LibroDAO libroDAO =
                new LibroDAO();

        Libro libro =
                prestamo.getLibro();

        libro.setStock(
                libro.getStock() + 1
        );

        libroDAO.actualizar(libro);

        prestamo.setDevuelto(true);

        return actualizar(prestamo);
    }

}