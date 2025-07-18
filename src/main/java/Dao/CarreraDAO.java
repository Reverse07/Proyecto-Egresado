
package Dao;

import Modelo.Carrera;
import Modelo.Conexion;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;


public class CarreraDAO {
    
     private Connection conn;

    public CarreraDAO() {
        conn = Conexion.conectar();
    }

    /**
     * ✅ Insertar una nueva carrera
     */
    public boolean insertarCarrera(Carrera carrera) {
        String sql = "INSERT INTO egresados.carreras (nombre) VALUES (?)";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, carrera.getNombre());

            int filasAfectadas = stmt.executeUpdate();
            return filasAfectadas > 0;
        } catch (SQLException e) {
            System.err.println("❌ Error al insertar carrera: " + e.getMessage());
            return false;
        }
    }

    /**
     * ✅ Listar todas las carreras
     */
    public List<Carrera> listarCarreras() {
        List<Carrera> lista = new ArrayList<>();
        String sql = "SELECT * FROM egresados.carreras ORDER BY nombre ASC";

        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                Carrera carrera = new Carrera();
                carrera.setId(rs.getInt("id"));
                carrera.setNombre(rs.getString("nombre"));
                lista.add(carrera);
            }
        } catch (SQLException e) {
            System.err.println("❌ Error al listar carreras: " + e.getMessage());
        }
        return lista;
    }

    /**
     * ✅ Actualizar una carrera existente
     */
    public boolean actualizarCarrera(Carrera carrera) {
        String sql = "UPDATE egresados.carreras SET nombre=? WHERE id=?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, carrera.getNombre());
            stmt.setInt(2, carrera.getId());

            int filasAfectadas = stmt.executeUpdate();
            return filasAfectadas > 0;
        } catch (SQLException e) {
            System.err.println("❌ Error al actualizar carrera: " + e.getMessage());
            return false;
        }
    }

    /**
     * ✅ Eliminar una carrera por ID
     */
    public boolean eliminarCarrera(int id) {
        String sql = "DELETE FROM egresados.carreras WHERE id=?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            int filasAfectadas = stmt.executeUpdate();
            return filasAfectadas > 0;
        } catch (SQLException e) {
            System.err.println("❌ Error al eliminar carrera: " + e.getMessage());
            return false;
        }
    }
}
    
