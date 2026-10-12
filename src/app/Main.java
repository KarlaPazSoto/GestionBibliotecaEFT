package app;

import database.DatabaseConnection;
import view.LoginView;

public class Main {

    public static void main(String[] args) {

        System.out.println(
                DatabaseConnection
                        .getInstance()
                        .getConnection()
        );

        LoginView loginView =
                new LoginView();

        loginView.setVisible(true);
    }
}