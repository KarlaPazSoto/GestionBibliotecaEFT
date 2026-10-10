package model;

public class EstudianteUsuario extends Usuario {
    public EstudianteUsuario() {

    }

    public EstudianteUsuario(int id, String nombre, String rut, String correo, String password) {
        super(id, nombre, rut, correo, password, "estudiante");
    }
}
