package dao;

import database.DatabaseConnection;
import model.Libro;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class LibroDAO implements CrudDAO<Libro> {

    private final Connection conn;

    public LibroDAO() {
        conn = DatabaseConnection
                .getInstance()
                .getConnection();
    }

    @Override
    public boolean crear(Libro libro) {

        String sql =
                "INSERT INTO libros(titulo, autor, isbn, editorial, stock, id_categoria) " +
                        "VALUES(?, ?, ?, ?, ?, ?)";

        try (PreparedStatement stmt =
                     conn.prepareStatement(sql)) {

            stmt.setString(1, libro.getTitulo());
            stmt.setString(2, libro.getAutor());
            stmt.setString(3, libro.getIsbn());
            stmt.setString(4, libro.getEditorial());
            stmt.setInt(5, libro.getStock());
            stmt.setInt(6, libro.getCategoria().getId());

            return stmt.executeUpdate() > 0;

        } catch (SQLException e) {

            System.out.println(
                    "Error al crear libro: "
                            + e.getMessage()
            );

            return false;
        }
    }

    @Override
    public boolean actualizar(Libro libro) {

        String sql =
                "UPDATE libros " +
                        "SET titulo=?, autor=?, isbn=?, editorial=?, stock=?, id_categoria=? " +
                        "WHERE id=?";

        try (PreparedStatement stmt =
                     conn.prepareStatement(sql)) {

            stmt.setString(1, libro.getTitulo());
            stmt.setString(2, libro.getAutor());
            stmt.setString(3, libro.getIsbn());
            stmt.setString(4, libro.getEditorial());
            stmt.setInt(5, libro.getStock());
            stmt.setInt(6, libro.getCategoria().getId());
            stmt.setInt(7, libro.getId());

            return stmt.executeUpdate() > 0;

        } catch (SQLException e) {

            System.out.println(
                    "Error al actualizar libro: "
                            + e.getMessage()
            );

            return false;
        }
    }

    @Override
    public boolean eliminar(Libro libro) {

        String sql =
                "DELETE FROM libros WHERE id=?";

        try (PreparedStatement stmt =
                     conn.prepareStatement(sql)) {

            stmt.setInt(1, libro.getId());

            return stmt.executeUpdate() > 0;

        } catch (SQLException e) {

            System.out.println(
                    "Error al eliminar libro: "
                            + e.getMessage()
            );

            return false;
        }
    }

    @Override
    public Libro buscarPorId(int id) {

        String sql =
                "SELECT * FROM libros WHERE id=?";

        try (PreparedStatement stmt =
                     conn.prepareStatement(sql)) {

            stmt.setInt(1, id);

            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {

                Libro libro = new Libro();

                libro.setId(rs.getInt("id"));
                libro.setTitulo(rs.getString("titulo"));
                libro.setAutor(rs.getString("autor"));
                libro.setIsbn(rs.getString("isbn"));
                libro.setEditorial(rs.getString("editorial"));
                libro.setStock(rs.getInt("stock"));

                return libro;
            }

        } catch (SQLException e) {

            System.out.println(
                    "Error al buscar libro: "
                            + e.getMessage()
            );
        }

        return null;
    }

    @Override
    public List<Libro> listar() {

        List<Libro> libros =
                new ArrayList<>();

        String sql =
                "SELECT * FROM libros";

        try (PreparedStatement stmt =
                     conn.prepareStatement(sql);

             ResultSet rs =
                     stmt.executeQuery()) {

            while (rs.next()) {

                Libro libro = new Libro();

                libro.setId(rs.getInt("id"));
                libro.setTitulo(rs.getString("titulo"));
                libro.setAutor(rs.getString("autor"));
                libro.setIsbn(rs.getString("isbn"));
                libro.setEditorial(rs.getString("editorial"));
                libro.setStock(rs.getInt("stock"));

                libros.add(libro);
            }

        } catch (SQLException e) {

            System.out.println(
                    "Error al listar libros: "
                            + e.getMessage()
            );
        }

        return libros;
    }
}