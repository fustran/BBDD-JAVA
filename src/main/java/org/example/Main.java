package org.example;


import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import static java.lang.System.*;

public class Main {

    public static void main(String[] args) {

        Connection bd = conectar();
        //insertar(bd);
        //modificar(bd);
        //borrar(bd);
        //consultar(bd);

        List<Estudiante> lista = consultarALista(bd);
        out.println("La Lista tiene " + lista.size() + " alumnos.");

        desconectar(bd);

    }

    public static Connection conectar() {
        Connection conexion;
        String host = "jdbc:mariadb://localhost:3307/";
        String user = "root";
        String password = "";
        String bd = "instituto";

        try {
            conexion = DriverManager.getConnection(host + bd, user, password);
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

    public static void consultar(Connection conexion){

        String query = "SELECT * FROM estudiante";
        Statement statement;
        ResultSet resultado; // Guardar lo que devuelve

        try {
            statement = conexion.createStatement();
            resultado = statement.executeQuery(query);

            while (resultado.next()){
                int nia = resultado.getInt("Nia");
                String nombre = resultado.getString("Nombre");
                LocalDate fechaNacimiento = resultado.getDate("fechaNacimiento").toLocalDate();

                out.println("NIA: " + nia + ", NOMBRE: " + nombre + ", FECHA NACIMIENTO: " + fechaNacimiento);
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    public static void insertar(Connection conexion){

        String query = "INSERT INTO estudiante (nia, nombre, fechaNacimiento) VALUES ('76859123', 'Roberto', '2000-10-05')";
        Statement statement;

        try {
            statement = conexion.createStatement();
            statement.executeUpdate(query);

            out.println("Fila insertada con éxito...");

        } catch (SQLException e) {
            out.println(e.getMessage());
            throw new RuntimeException(e);
        }

    }

    public static void modificar(Connection conexion){

        String query = "UPDATE estudiante SET FechaNacimiento = '2001-11-10' WHERE nia = '76859123'";
        Statement statement;

        try {
            statement = conexion.createStatement();
            statement.executeUpdate(query);

            out.println("Fila Modificada con éxito...");

        } catch (SQLException e) {
            out.println(e.getMessage());
            throw new RuntimeException(e);
        }

    }

    public static void borrar(Connection conexion){

        String query = "DELETE FROM estudiante WHERE nombre = 'Roberto Fresh'";
        Statement statement;

        try {
            statement = conexion.createStatement();
            statement.executeUpdate(query);

            out.println("Fila Eliminada con éxito...");

        } catch (SQLException e) {
            out.println(e.getMessage());
            throw new RuntimeException(e);
        }
    }

    public static List<Estudiante> consultarALista(Connection conexion){

        ArrayList<Estudiante> listaEstudiantes = new ArrayList<>();

        String query = "SELECT * FROM estudiante";
        Statement statement;
        ResultSet resultado; // Guardar lo que devuelve

        try {
            statement = conexion.createStatement();
            resultado = statement.executeQuery(query);

            while (resultado.next()){
                int nia = resultado.getInt("Nia");
                String nombre = resultado.getString("Nombre");
                LocalDate fechaNacimiento = resultado.getDate("fechaNacimiento").toLocalDate();

                listaEstudiantes.add(new Estudiante(nia, nombre, fechaNacimiento));
            }

            out.println();
            out.println("      LISTA DE ESTUDIANTES: ");
            out.println();
            int cont = 0;

            for (Estudiante estudiantes : listaEstudiantes){

                if (cont < listaEstudiantes.size()){
                    cont++;
                }

                out.println("ESTUDIANTE " + "[" + cont + "]:");
                out.println(" Nombre: " + estudiantes.getNombre());
                out.println(" Nia: " + estudiantes.getNia());
                out.println(" Fecha de nacimiento: " + estudiantes.getFechaNacimiento());
                out.println();
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return Collections.unmodifiableList(listaEstudiantes);
    }
}