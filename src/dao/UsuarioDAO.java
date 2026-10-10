package dao;

import database.DatabaseConnection;
import model.Bibliotecario;
import model.EstudianteUsuario;
import model.Usuario;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UsuarioDAO implements CrudDAO<Usuario> {

    private final Connection conn;

    public UsuarioDAO() {

        conn = DatabaseConnection
                .getInstance()
                .getConnection();

    }

    @Override
    public boolean crear(Usuario usuario) {

        String sql =
                "INSERT INTO usuarios(nombre, rut, correo, password, rol) " +
                        "VALUES(?, ?, ?, ?, ?)";

        try (PreparedStatement stmt =
                     conn.prepareStatement(sql)) {

            stmt.setString(1, usuario.getNombre());
            stmt.setString(2, usuario.getRut());
            stmt.setString(3, usuario.getCorreo());
            stmt.setString(4, usuario.getPassword());
            stmt.setString(5, usuario.getRol());

            return stmt.executeUpdate() > 0;

        } catch (SQLException e) {

            System.out.println(
                    "Error al crear usuario: "
                            + e.getMessage()
            );

            return false;
        }
    }

    @Override
    public boolean actualizar(Usuario usuario) {

        String sql =
                "UPDATE usuarios " +
                        "SET nombre=?, rut=?, correo=?, password=?, rol=? " +
                        "WHERE id=?";

        try (PreparedStatement stmt =
                     conn.prepareStatement(sql)) {

            stmt.setString(1, usuario.getNombre());
            stmt.setString(2, usuario.getRut());
            stmt.setString(3, usuario.getCorreo());
            stmt.setString(4, usuario.getPassword());
            stmt.setString(5, usuario.getRol());
            stmt.setInt(6, usuario.getId());

            return stmt.executeUpdate() > 0;

        } catch (SQLException e) {

            System.out.println(
                    "Error al actualizar usuario: "
                            + e.getMessage()
            );

            return false;
        }
    }

    @Override
    public boolean eliminar(Usuario usuario) {

        String sql =
                "DELETE FROM usuarios WHERE id=?";

        try (PreparedStatement stmt =
                     conn.prepareStatement(sql)) {

            stmt.setInt(1, usuario.getId());

            return stmt.executeUpdate() > 0;

        } catch (SQLException e) {

            System.out.println(
                    "Error al eliminar usuario: "
                            + e.getMessage()
            );

            return false;
        }
    }

    @Override
    public Usuario buscarPorId(int id) {

        String sql =
                "SELECT * FROM usuarios WHERE id=?";

        try (PreparedStatement stmt =
                     conn.prepareStatement(sql)) {

            stmt.setInt(1, id);

            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {

                return crearUsuarioDesdeResultSet(rs);

            }

        } catch (SQLException e) {

            System.out.println(
                    "Error al buscar usuario: "
                            + e.getMessage()
            );

        }

        return null;
    }

    @Override
    public List<Usuario> listar() {

        List<Usuario> usuarios =
                new ArrayList<>();

        String sql =
                "SELECT * FROM usuarios";

        try (PreparedStatement stmt =
                     conn.prepareStatement(sql);

             ResultSet rs =
                     stmt.executeQuery()) {

            while (rs.next()) {

                usuarios.add(
                        crearUsuarioDesdeResultSet(rs)
                );

            }

        } catch (SQLException e) {

            System.out.println(
                    "Error al listar usuarios: "
                            + e.getMessage()
            );

        }

        return usuarios;
    }

    public Usuario autenticar(
            String correo,
            String password) {

        String sql =
                "SELECT * FROM usuarios " +
                        "WHERE correo=? AND password=?";

        try (PreparedStatement stmt =
                     conn.prepareStatement(sql)) {

            stmt.setString(1, correo);
            stmt.setString(2, password);

            ResultSet rs =
                    stmt.executeQuery();

            if (rs.next()) {

                return crearUsuarioDesdeResultSet(rs);

            }

        } catch (SQLException e) {

            System.out.println(
                    "Error en autenticación: "
                            + e.getMessage()
            );

        }

        return null;
    }

    private Usuario crearUsuarioDesdeResultSet(
            ResultSet rs)
            throws SQLException {

        String rol =
                rs.getString("rol");

        Usuario usuario;

        if ("bibliotecario".equalsIgnoreCase(rol)) {

            usuario = new Bibliotecario();

        } else {

            usuario = new EstudianteUsuario();

        }

        usuario.setId(
                rs.getInt("id")
        );

        usuario.setNombre(
                rs.getString("nombre")
        );

        usuario.setRut(
                rs.getString("rut")
        );

        usuario.setCorreo(
                rs.getString("correo")
        );

        usuario.setPassword(
                rs.getString("password")
        );

        usuario.setRol(
                rs.getString("rol")
        );

        return usuario;
    }
}