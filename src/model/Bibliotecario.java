package model;

public class Bibliotecario extends Usuario{

    public Bibliotecario() {
    }

    public Bibliotecario(int id, String nombre, String rut, String correo, String password) {
        super(id, nombre, rut, correo, password, "bibliotecario");

    }
}
