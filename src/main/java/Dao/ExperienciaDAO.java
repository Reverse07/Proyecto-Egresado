
package Dao;

import Modelo.Conexion;
import Modelo.Experiencia;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;


public class ExperienciaDAO {
    
    private Connection conn;

    public ExperienciaDAO() {
        conn = Conexion.conectar();
    }

    /**
     * ✅ Insertar nueva experiencia
     */
    public boolean insertarExperiencia(Experiencia experiencia) {
        String sql = "INSERT INTO egresados.experiencias (egresado_id, empresa, cargo, fecha_inicio, fecha_fin, descripcion) " +
                     "VALUES (?, ?, ?, ?, ?, ?)";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, experiencia.getEgresadoId());
            stmt.setString(2, experiencia.getEmpresa());
            stmt.setString(3, experiencia.getCargo());
            stmt.setDate(4, new java.sql.Date(experiencia.getFechaInicio().getTime()));
            
            if (experiencia.getFechaFin() != null) {
                stmt.setDate(5, new java.sql.Date(experiencia.getFechaFin().getTime()));
            } else {
                stmt.setNull(5, java.sql.Types.DATE);
            }
            
            stmt.setString(6, experiencia.getDescripcion());

            int filasAfectadas = stmt.executeUpdate();
            return filasAfectadas > 0;
        } catch (SQLException e) {
            System.err.println("❌ Error al insertar experiencia: " + e.getMessage());
            return false;
        }
    }

    /**
     * ✅ Obtener todas las experiencias de un egresado
     */
    public List<Experiencia> listarPorEgresado(int egresadoId) {
        List<Experiencia> lista = new ArrayList<>();
        String sql = "SELECT * FROM egresados.experiencias WHERE egresado_id = ? ORDER BY fecha_inicio DESC";

        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, egresadoId);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                Experiencia exp = new Experiencia();
                exp.setId(rs.getInt("id"));
                exp.setEgresadoId(rs.getInt("egresado_id"));
                exp.setEmpresa(rs.getString("empresa"));
                exp.setCargo(rs.getString("cargo"));
                exp.setFechaInicio(rs.getDate("fecha_inicio"));
                exp.setFechaFin(rs.getDate("fecha_fin"));
                exp.setDescripcion(rs.getString("descripcion"));

                lista.add(exp);
            }
        } catch (SQLException e) {
            System.err.println("❌ Error al listar experiencias: " + e.getMessage());
        }
        return lista;
    }

    /**
     * ✅ Actualizar una experiencia existente
     */
    public boolean actualizarExperiencia(Experiencia experiencia) {
        String sql = "UPDATE egresados.experiencias SET empresa=?, cargo=?, fecha_inicio=?, fecha_fin=?, descripcion=? WHERE id=?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, experiencia.getEmpresa());
            stmt.setString(2, experiencia.getCargo());
            stmt.setDate(3, new java.sql.Date(experiencia.getFechaInicio().getTime()));

            if (experiencia.getFechaFin() != null) {
                stmt.setDate(4, new java.sql.Date(experiencia.getFechaFin().getTime()));
            } else {
                stmt.setNull(4, java.sql.Types.DATE);
            }

            stmt.setString(5, experiencia.getDescripcion());
            stmt.setInt(6, experiencia.getId());

            int filasAfectadas = stmt.executeUpdate();
            return filasAfectadas > 0;
        } catch (SQLException e) {
            System.err.println("❌ Error al actualizar experiencia: " + e.getMessage());
            return false;
        }
    }

    /**
     * ✅ Eliminar experiencia por ID
     */
    public boolean eliminarExperiencia(int id) {
        String sql = "DELETE FROM egresados.experiencias WHERE id=?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            int filasAfectadas = stmt.executeUpdate();
            return filasAfectadas > 0;
        } catch (SQLException e) {
            System.err.println("❌ Error al eliminar experiencia: " + e.getMessage());
            return false;
        }
    }
}

