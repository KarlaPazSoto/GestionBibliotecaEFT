package model;

import strategy.Autenticable;

public abstract class Usuario implements Autenticable {

    protected int id;
    protected String nombre;
    protected String rut;
    protected String correo;
    protected String password;
    protected String rol;

    public Usuario() {

    }

    public Usuario (int id, String nombre, String rut, String correo, String password, String rol) {
        this.id = id;
        this.nombre = nombre;
        this.rut = rut;
        this.correo = correo;
        this.password = password;
        this.rol = rol;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getRut() {
        return rut;
    }

    public void setRut(String rut) {
        this.rut = rut;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRol() {
        return rol;
    }

    public void setRol(String rol) {
        this.rol = rol;
    }

    @Override public boolean autenticar(String correo, String password) {
        return this.correo.equals(correo) && this.password.equals(password);
    }

    @Override
    public String toString() {
        return "Usuario{" +
                "id=" + id +
                ", nombre='" + nombre + '\'' +
                ", rut='" + rut + '\'' +
                ", correo='" + correo + '\'' +
                ", password='" + password + '\'' +
                ", rol='" + rol + '\'' +
                '}';
    }
}
