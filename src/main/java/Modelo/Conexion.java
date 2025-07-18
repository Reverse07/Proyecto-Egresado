
package Modelo;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;


public class Conexion {
    
    
    private static final String URL = "jdbc:postgresql://localhost:5432/Egresados_db";
    private static final String USER = "postgres"; 
    private static final String PASSWORD = "Nomeacuerdo2006@";

    public static Connection conectar() {
        try {
            Class.forName("org.postgresql.Driver");
            Connection conexion = DriverManager.getConnection(URL, USER, PASSWORD);
            System.out.println("✅ Conexión exitosa a la base de datos");
            return conexion;
        } catch (ClassNotFoundException e) {
            System.err.println("❌ Error: No se encontró el driver PostgreSQL");
            e.printStackTrace();
            return null;
        } catch (SQLException e) {
            System.err.println("❌ Error al conectar con la base de datos");
            e.printStackTrace();
            return null;
        }
    }
}
 
