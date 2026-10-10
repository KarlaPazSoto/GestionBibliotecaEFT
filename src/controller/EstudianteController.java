package controller;

import dao.EstudianteDAO;
import model.Estudiante;

import java.util.List;

public class EstudianteController {

    private final EstudianteDAO estudianteDAO;

    public EstudianteController() {

        estudianteDAO = new EstudianteDAO();

    }

    public boolean registrarEstudiante(Estudiante estudiante) {

        return estudianteDAO.crear(estudiante);

    }

    public boolean actualizarEstudiante(Estudiante estudiante) {

        return estudianteDAO.actualizar(estudiante);

    }

    public boolean eliminarEstudiante(Estudiante estudiante) {

        return estudianteDAO.eliminar(estudiante);

    }

    public Estudiante buscarEstudiante(int id) {

        return estudianteDAO.buscarPorId(id);

    }

    public List<Estudiante> listarEstudiantes() {

        return estudianteDAO.listar();

    }
}