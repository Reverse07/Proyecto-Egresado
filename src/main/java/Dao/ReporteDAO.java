
package Dao;

import Modelo.Conexion;
import Modelo.Reporte;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;


public class ReporteDAO {
    
      /**
     * ✅ Obtiene la cantidad de egresados por carrera
     */
    public ArrayList<Reporte> obtenerEgresadosPorCarrera() {
        ArrayList<Reporte> lista = new ArrayList<>();
        String sql = "SELECT c.nombre AS carrera, COUNT(e.id) AS cantidad, " +
                     "(COUNT(e.id) * 100.0 / (SELECT COUNT(*) FROM egresados.egresados)) AS porcentaje " +
                     "FROM egresados.egresados e " +
                     "INNER JOIN egresados.carreras c ON e.carrera_id = c.id " +
                     "GROUP BY c.nombre " +
                     "ORDER BY cantidad DESC";

        try (Connection conn = Conexion.conectar();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                String carrera = rs.getString("carrera");
                int cantidad = rs.getInt("cantidad");
                double porcentaje = rs.getDouble("porcentaje");

                Reporte reporte = new Reporte("Egresados por Carrera", carrera, cantidad, porcentaje);
                lista.add(reporte);
            }

        } catch (SQLException e) {
            System.err.println("❌ Error al obtener egresados por carrera: " + e.getMessage());
        }

        return lista;
    }

    /**
     * ✅ Obtiene la cantidad de usuarios por rol
     */
    public ArrayList<Reporte> obtenerUsuariosPorRol() {
        ArrayList<Reporte> lista = new ArrayList<>();
        String sql = "SELECT rol, COUNT(id) AS cantidad, " +
                     "(COUNT(id) * 100.0 / (SELECT COUNT(*) FROM egresados.usuarios)) AS porcentaje " +
                     "FROM egresados.usuarios " +
                     "GROUP BY rol " +
                     "ORDER BY cantidad DESC";

        try (Connection conn = Conexion.conectar();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                String rol = rs.getString("rol");
                int cantidad = rs.getInt("cantidad");
                double porcentaje = rs.getDouble("porcentaje");

                Reporte reporte = new Reporte("Usuarios por Rol", rol, cantidad, porcentaje);
                lista.add(reporte);
            }

        } catch (SQLException e) {
            System.err.println("❌ Error al obtener usuarios por rol: " + e.getMessage());
        }

        return lista;
    }
}

