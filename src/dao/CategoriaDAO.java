package dao;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import database.DatabaseConnection;
import model.Categoria;

public class CategoriaDAO implements CrudDAO<Categoria> {

    private final Connection conn;

    public CategoriaDAO() {
        conn = DatabaseConnection.getInstance().getConnection();
    }

    @Override public boolean crear(Categoria categoria) {
        String sql = "INSERT INTO categorias(nombre) VALUES(?)";

        try(PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, categoria.getNombre());

            return stmt.executeUpdate() > 0;

        }catch(SQLException e) {
            System.out.println("Error al crear categoria: " + e.getMessage());
        }
        return false;
    }

    @Override public boolean actualizar(Categoria categoria) {
        String sql = "UPDATE categorias SET nombre = ? WHERE id = ?";

        try(PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, categoria.getNombre());
            stmt.setInt(2, categoria.getId());

            return stmt.executeUpdate() > 0;
        }catch(SQLException e) {
            System.out.println("Error al actualizar categoria: " + e.getMessage());
        }
        return false;
    }

    @Override public boolean eliminar(Categoria categoria) {
        String sql = "DELETE FROM categorias WHERE id = ?";

        try(PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, categoria.getId());

            return stmt.executeUpdate() > 0;
        }catch(SQLException e) {
            System.out.println("Error al eliminar categoria: " + e.getMessage());
        }
        return false;
    }

    @Override public Categoria buscarPorId(int id) {
        String sql = "SELECT * FROM categorias WHERE id = ?";

        try(PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);

            ResultSet rs = stmt.executeQuery();

            if(rs.next()) {

                return new Categoria(rs.getInt("id"), rs.getString("nombre"));
            }
        }catch(SQLException e) {
            System.out.println("Error al buscar categoria por ID: " + e.getMessage());
        }
        return null;
    }

    @Override public List<Categoria> listar() {

        List<Categoria> categorias = new ArrayList<>();

        String sql = "SELECT * FROM categorias";

        try(PreparedStatement stmt = conn.prepareStatement(sql);
        ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {

                Categoria categoria = new Categoria(rs.getInt("id"), rs.getString("nombre"));
                categorias.add(categoria);
            }
        }catch(SQLException e) {
            System.out.println("Error al listar categorias: " + e.getMessage());
        }
        return categorias;
    }

}
