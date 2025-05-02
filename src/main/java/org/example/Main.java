package org.example;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import static java.lang.System.*;

public class Main {

    public static void main(String[] args) {

        Connection bd = conectar();
        //desconectar(bd);
    }

    public static Connection conectar() {
        Connection conexion;
        String host = "jdbc:mariadb://localhost:3307/";
        String user = "root";
        String password = "";
        String bd = "Instituto";

        try {
            conexion = DriverManager.getConnection(host + bd,user,password);
            out.println("Conexión realizada con éxito!");
        } catch (SQLException e) {
            out.println(e.getMessage());
            throw new RuntimeException(e);
        }

        return conexion;
    }

    public static void desconectar(Connection conexion) {
        try {
            conexion.close();
            out.println("Desconexión realizada con éxito");
        } catch (SQLException e) {
            out.println("Error de desconexión");
            throw new RuntimeException(e);
        }
    }
}