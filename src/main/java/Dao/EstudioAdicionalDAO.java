
package Dao;

import Modelo.Conexion;
import Modelo.EstudioAdicional;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;


public class EstudioAdicionalDAO {
    
     private Connection conn;

    public EstudioAdicionalDAO() {
        conn = Conexion.conectar();
    }

    /**
     * ✅ Insertar un nuevo estudio adicional
     */
    public boolean insertarEstudio(EstudioAdicional estudio) {
        String sql = "INSERT INTO egresados.estudios_adicionales " +
                     "(egresado_id, institucion, tipo_estudio, fecha_inicio, fecha_fin, descripcion) " +
                     "VALUES (?, ?, ?, ?, ?, ?)";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, estudio.getEgresadoId());
            stmt.setString(2, estudio.getInstitucion());
            stmt.setString(3, estudio.getTipoEstudio());
            stmt.setDate(4, new java.sql.Date(estudio.getFechaInicio().getTime()));
            
            if (estudio.getFechaFin() != null) {
                stmt.setDate(5, new java.sql.Date(estudio.getFechaFin().getTime()));
            } else {
                stmt.setNull(5, java.sql.Types.DATE);
            }

            stmt.setString(6, estudio.getDescripcion());

            int filasAfectadas = stmt.executeUpdate();
            return filasAfectadas > 0;
        } catch (SQLException e) {
            System.err.println("❌ Error al insertar estudio adicional: " + e.getMessage());
            return false;
        }
    }

    /**
     * ✅ Listar estudios adicionales por egresado
     */
    public List<EstudioAdicional> listarPorEgresado(int egresadoId) {
        List<EstudioAdicional> lista = new ArrayList<>();
        String sql = "SELECT * FROM egresados.estudios_adicionales WHERE egresado_id = ? ORDER BY fecha_inicio DESC";

        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, egresadoId);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                EstudioAdicional est = new EstudioAdicional();
                est.setId(rs.getInt("id"));
                est.setEgresadoId(rs.getInt("egresado_id"));
                est.setInstitucion(rs.getString("institucion"));
                est.setTipoEstudio(rs.getString("tipo_estudio"));
                est.setFechaInicio(rs.getDate("fecha_inicio"));
                est.setFechaFin(rs.getDate("fecha_fin"));
                est.setDescripcion(rs.getString("descripcion"));

                lista.add(est);
            }
        } catch (SQLException e) {
            System.err.println("❌ Error al listar estudios adicionales: " + e.getMessage());
        }
        return lista;
    }

    /**
     * ✅ Actualizar un estudio adicional existente
     */
    public boolean actualizarEstudio(EstudioAdicional estudio) {
        String sql = "UPDATE egresados.estudios_adicionales SET institucion=?, tipo_estudio=?, fecha_inicio=?, fecha_fin=?, descripcion=? WHERE id=?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, estudio.getInstitucion());
            stmt.setString(2, estudio.getTipoEstudio());
            stmt.setDate(3, new java.sql.Date(estudio.getFechaInicio().getTime()));

            if (estudio.getFechaFin() != null) {
                stmt.setDate(4, new java.sql.Date(estudio.getFechaFin().getTime()));
            } else {
                stmt.setNull(4, java.sql.Types.DATE);
            }

            stmt.setString(5, estudio.getDescripcion());
            stmt.setInt(6, estudio.getId());

            int filasAfectadas = stmt.executeUpdate();
            return filasAfectadas > 0;
        } catch (SQLException e) {
            System.err.println("❌ Error al actualizar estudio adicional: " + e.getMessage());
            return false;
        }
    }

    /**
     * ✅ Eliminar un estudio adicional por ID
     */
    public boolean eliminarEstudio(int id) {
        String sql = "DELETE FROM egresados.estudios_adicionales WHERE id=?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            int filasAfectadas = stmt.executeUpdate();
            return filasAfectadas > 0;
        } catch (SQLException e) {
            System.err.println("❌ Error al eliminar estudio adicional: " + e.getMessage());
            return false;
        }
    }
}

