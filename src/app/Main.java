package app;

import database.DatabaseConnection;

public class Main {

    public static void main(String[] args) {

        if (DatabaseConnection
                .getInstance()
                .getConnection() != null) {

            System.out.println("Conexion exitosa");

        } else {

            System.out.println("Error de conexion");

        }

    }
}