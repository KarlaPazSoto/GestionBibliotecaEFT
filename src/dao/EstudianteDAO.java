package dao;

import database.DatabaseConnection;
import model.Estudiante;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class EstudianteDAO implements CrudDAO<Estudiante> {

    private final Connection conn;

    public EstudianteDAO() {

        conn = DatabaseConnection
                .getInstance()
                .getConnection();

    }

    @Override
    public boolean crear(Estudiante estudiante) {

        String sql =
                "INSERT INTO estudiantes(nombre, rut, curso, correo) " +
                        "VALUES(?, ?, ?, ?)";

        try (PreparedStatement stmt =
                     conn.prepareStatement(sql)) {

            stmt.setString(1, estudiante.getNombre());
            stmt.setString(2, estudiante.getRut());
            stmt.setString(3, estudiante.getCurso());
            stmt.setString(4, estudiante.getCorreo());

            return stmt.executeUpdate() > 0;

        } catch (SQLException e) {

            System.out.println(
                    "Error al crear estudiante: "
                            + e.getMessage()
            );

            return false;
        }
    }

    @Override
    public boolean actualizar(Estudiante estudiante) {

        String sql =
                "UPDATE estudiantes " +
                        "SET nombre=?, rut=?, curso=?, correo=? " +
                        "WHERE id=?";

        try (PreparedStatement stmt =
                     conn.prepareStatement(sql)) {

            stmt.setString(1, estudiante.getNombre());
            stmt.setString(2, estudiante.getRut());
            stmt.setString(3, estudiante.getCurso());
            stmt.setString(4, estudiante.getCorreo());
            stmt.setInt(5, estudiante.getId());

            return stmt.executeUpdate() > 0;

        } catch (SQLException e) {

            System.out.println(
                    "Error al actualizar estudiante: "
                            + e.getMessage()
            );

            return false;
        }
    }

    @Override
    public boolean eliminar(Estudiante estudiante) {

        String sql =
                "DELETE FROM estudiantes WHERE id=?";

        try (PreparedStatement stmt =
                     conn.prepareStatement(sql)) {

            stmt.setInt(1, estudiante.getId());

            return stmt.executeUpdate() > 0;

        } catch (SQLException e) {

            System.out.println(
                    "Error al eliminar estudiante: "
                            + e.getMessage()
            );

            return false;
        }
    }

    @Override
    public Estudiante buscarPorId(int id) {

        String sql =
                "SELECT * FROM estudiantes WHERE id=?";

        try (PreparedStatement stmt =
                     conn.prepareStatement(sql)) {

            stmt.setInt(1, id);

            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {

                Estudiante estudiante =
                        new Estudiante();

                estudiante.setId(
                        rs.getInt("id")
                );

                estudiante.setNombre(
                        rs.getString("nombre")
                );

                estudiante.setRut(
                        rs.getString("rut")
                );

                estudiante.setCurso(
                        rs.getString("curso")
                );

                estudiante.setCorreo(
                        rs.getString("correo")
                );

                return estudiante;
            }

        } catch (SQLException e) {

            System.out.println(
                    "Error al buscar estudiante: "
                            + e.getMessage()
            );

        }

        return null;
    }

    @Override
    public List<Estudiante> listar() {

        List<Estudiante> estudiantes =
                new ArrayList<>();

        String sql =
                "SELECT * FROM estudiantes";

        try (PreparedStatement stmt =
                     conn.prepareStatement(sql);

             ResultSet rs =
                     stmt.executeQuery()) {

            while (rs.next()) {

                Estudiante estudiante =
                        new Estudiante();

                estudiante.setId(
                        rs.getInt("id")
                );

                estudiante.setNombre(
                        rs.getString("nombre")
                );

                estudiante.setRut(
                        rs.getString("rut")
                );

                estudiante.setCurso(
                        rs.getString("curso")
                );

                estudiante.setCorreo(
                        rs.getString("correo")
                );

                estudiantes.add(estudiante);

            }

        } catch (SQLException e) {

            System.out.println(
                    "Error al listar estudiantes: "
                            + e.getMessage()
            );

        }

        return estudiantes;
    }
}