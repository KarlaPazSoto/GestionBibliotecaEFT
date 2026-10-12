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

        EstudianteDAO estudianteDAO =
                new EstudianteDAO();

        LibroDAO libroDAO =
                new LibroDAO();

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

                prestamo.setEstudiante(
                        estudianteDAO.buscarPorId(
                                rs.getInt("id_estudiante")
                        )
                );

                prestamo.setLibro(
                        libroDAO.buscarPorId(
                                rs.getInt("id_libro")
                        )
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

        EstudianteDAO estudianteDAO =
                new EstudianteDAO();

        LibroDAO libroDAO =
                new LibroDAO();

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

                prestamo.setEstudiante(
                        estudianteDAO.buscarPorId(
                                rs.getInt("id_estudiante")
                        )
                );

                prestamo.setLibro(
                        libroDAO.buscarPorId(
                                rs.getInt("id_libro")
                        )
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

    public List<Object[]> librosMasPrestados() {

        List<Object[]> datos = new ArrayList<>();

        String sql =
                "SELECT l.titulo, COUNT(*) cantidad " +
                        "FROM prestamos p " +
                        "INNER JOIN libros l ON p.id_libro = l.id " +
                        "GROUP BY l.titulo " +
                        "ORDER BY cantidad DESC";

        try (
                PreparedStatement stmt =
                        conn.prepareStatement(sql);

                ResultSet rs =
                        stmt.executeQuery()
        ) {

            while (rs.next()) {

                datos.add(new Object[]{
                        rs.getString("titulo"),
                        rs.getInt("cantidad")
                });
            }

        } catch (SQLException e) {

            System.out.println(
                    "Error reporte libros más prestados: "
                            + e.getMessage()
            );
        }

        return datos;
    }

    public List<Object[]> historialEstudiantes() {

        List<Object[]> datos = new ArrayList<>();

        String sql =
                "SELECT e.nombre, l.titulo, " +
                        "p.fecha_prestamo, p.fecha_devolucion " +
                        "FROM prestamos p " +
                        "INNER JOIN estudiantes e ON p.id_estudiante = e.id " +
                        "INNER JOIN libros l ON p.id_libro = l.id";

        try (
                PreparedStatement stmt =
                        conn.prepareStatement(sql);

                ResultSet rs =
                        stmt.executeQuery()
        ) {

            while (rs.next()) {

                datos.add(new Object[]{
                        rs.getString("nombre"),
                        rs.getString("titulo"),
                        rs.getDate("fecha_prestamo"),
                        rs.getDate("fecha_devolucion")
                });
            }

        } catch (SQLException e) {

            System.out.println(
                    "Error historial estudiantes: "
                            + e.getMessage()
            );
        }

        return datos;
    }

    public List<Object[]> prestamosActivos() {

        List<Object[]> datos = new ArrayList<>();

        String sql =
                "SELECT l.titulo, e.nombre, p.fecha_devolucion " +
                        "FROM prestamos p " +
                        "INNER JOIN libros l ON p.id_libro = l.id " +
                        "INNER JOIN estudiantes e ON p.id_estudiante = e.id " +
                        "WHERE p.devuelto = false";

        try (
                PreparedStatement stmt =
                        conn.prepareStatement(sql);

                ResultSet rs =
                        stmt.executeQuery()
        ) {

            while (rs.next()) {

                datos.add(new Object[]{
                        rs.getString("titulo"),
                        rs.getString("nombre"),
                        rs.getDate("fecha_devolucion")
                });
            }

        } catch (SQLException e) {

            System.out.println(
                    "Error préstamos activos: "
                            + e.getMessage()
            );
        }

        return datos;
    }

}