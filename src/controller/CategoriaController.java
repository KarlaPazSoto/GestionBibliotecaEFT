package controller;

import dao.CategoriaDAO;
import model.Categoria;

import java.util.List;

public class CategoriaController {

    private final CategoriaDAO categoriaDAO;

    public CategoriaController() {

        categoriaDAO = new CategoriaDAO();

    }

    public boolean registrarCategoria(Categoria categoria) {

        return categoriaDAO.crear(categoria);

    }

    public boolean actualizarCategoria(Categoria categoria) {

        return categoriaDAO.actualizar(categoria);

    }

    public boolean eliminarCategoria(Categoria categoria) {

        return categoriaDAO.eliminar(categoria);

    }

    public Categoria buscarCategoria(int id) {

        return categoriaDAO.buscarPorId(id);

    }

    public List<Categoria> listarCategorias() {

        return categoriaDAO.listar();

    }
}