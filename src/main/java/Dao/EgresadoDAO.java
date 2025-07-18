package Dao;

import Modelo.Carrera;
import Modelo.Conexion;
import Modelo.Egresado;
import Modelo.Usuario;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class EgresadoDAO {

    private Connection conn;

    public EgresadoDAO() {
        conn = Conexion.conectar();
    }

    public boolean insertarEgresado(Egresado egresado) {
        String sql = "INSERT INTO egresados.egresados (nombres, apellidos, dni, correo, telefono, fecha_nacimiento, sexo, direccion, carrera_id, fecha_egreso, usuario_id) "
                + "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, egresado.getNombres());
            stmt.setString(2, egresado.getApellidos());
            stmt.setString(3, egresado.getDni());
            stmt.setString(4, egresado.getCorreo());
            stmt.setString(5, egresado.getTelefono());
            stmt.setDate(6, egresado.getFechaNacimiento() != null ? new java.sql.Date(egresado.getFechaNacimiento().getTime()) : null);
            stmt.setString(7, egresado.getSexo());
            stmt.setString(8, egresado.getDireccion());
            stmt.setInt(9, egresado.getCarrera().getId());
            stmt.setDate(10, egresado.getFechaEgreso() != null ? new java.sql.Date(egresado.getFechaEgreso().getTime()) : null);
            stmt.setInt(11, egresado.getUsuario() != null ? egresado.getUsuario().getId() : null);

            int filasAfectadas = stmt.executeUpdate();
            return filasAfectadas > 0;
        } catch (SQLException e) {
            System.err.println("❌ Error al insertar egresado: " + e.getMessage());
            return false;
        }
    }

    /**
     * ✅ Buscar un egresado por ID
     */
    public Egresado buscarPorId(int id) {
        String sql = "SELECT e.*, c.id AS carrera_id, c.nombre AS carrera_nombre, u.id AS usuario_id, u.username, u.rol "
                + "FROM egresados.egresados e "
                + "JOIN egresados.carreras c ON e.carrera_id = c.id "
                + "LEFT JOIN egresados.usuarios u ON e.usuario_id = u.id "
                + "WHERE e.id = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                // 📄 Construir objeto Carrera
                Carrera carrera = new Carrera(
                        rs.getInt("carrera_id"),
                        rs.getString("carrera_nombre")
                );

                // 📄 Construir objeto Usuario
                Usuario usuario = null;
                int usuarioId = rs.getInt("usuario_id");
                if (!rs.wasNull()) {
                    usuario = new Usuario(
                            usuarioId,
                            rs.getString("username"),
                            null, // No cargamos contraseña
                            rs.getString("rol")
                    );
                }

                // 📄 Construir objeto Egresado
                Egresado egresado = new Egresado();
                egresado.setId(rs.getInt("id"));
                egresado.setNombres(rs.getString("nombres"));
                egresado.setApellidos(rs.getString("apellidos"));
                egresado.setDni(rs.getString("dni"));
                egresado.setCorreo(rs.getString("correo"));
                egresado.setTelefono(rs.getString("telefono"));
                egresado.setFechaNacimiento(rs.getDate("fecha_nacimiento"));
                egresado.setSexo(rs.getString("sexo"));
                egresado.setDireccion(rs.getString("direccion"));
                egresado.setCarrera(carrera);
                egresado.setFechaEgreso(rs.getDate("fecha_egreso"));
                egresado.setUsuario(usuario);

                // Inicializar listas vacías
                egresado.setExperiencias(new ArrayList<>());
                egresado.setEstudiosAdicionales(new ArrayList<>());

                return egresado;
            }
        } catch (SQLException e) {
            System.err.println("❌ Error al buscar egresado: " + e.getMessage());
        }
        return null;
    }

    /**
     * ✅ Listar todos los egresados (solo datos básicos)
     */
    public List<Egresado> listarTodos() {
        List<Egresado> lista = new ArrayList<>();
        String sql = "SELECT e.id, e.nombres, e.apellidos, e.dni, c.id AS carrera_id, c.nombre AS carrera_nombre "
                + "FROM egresados.egresados e "
                + "JOIN egresados.carreras c ON e.carrera_id = c.id "
                + "ORDER BY e.apellidos, e.nombres";
        try (Statement stmt = conn.createStatement(); ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                Carrera carrera = new Carrera(
                        rs.getInt("carrera_id"),
                        rs.getString("carrera_nombre")
                );

                Egresado egresado = new Egresado();
                egresado.setId(rs.getInt("id"));
                egresado.setNombres(rs.getString("nombres"));
                egresado.setApellidos(rs.getString("apellidos"));
                egresado.setDni(rs.getString("dni"));
                egresado.setCarrera(carrera);

                // Inicializar listas vacías
                egresado.setExperiencias(new ArrayList<>());
                egresado.setEstudiosAdicionales(new ArrayList<>());

                lista.add(egresado);
            }
        } catch (SQLException e) {
            System.err.println("❌ Error al listar egresados: " + e.getMessage());
        }
        return lista;
    }

    /**
     * ✅ Obtener carrera por nombre
     */
    public Carrera obtenerCarreraPorNombre(String nombreCarrera) {
        String sql = "SELECT id, nombre FROM egresados.carreras WHERE nombre = ?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, nombreCarrera);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return new Carrera(rs.getInt("id"), rs.getString("nombre"));
            }
        } catch (SQLException e) {
            System.err.println("❌ Error al obtener carrera: " + e.getMessage());
        }
        return null; // Si no encuentra la carrera
    }

    /**
     * 📋 Obtener todas las carreras
     */
    public List<Carrera> obtenerTodasLasCarreras() {
        List<Carrera> carreras = new ArrayList<>();
        String sql = "SELECT id, nombre FROM egresados.carreras ORDER BY nombre";
        try (PreparedStatement ps = conn.prepareStatement(sql); ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                Carrera carrera = new Carrera(rs.getInt("id"), rs.getString("nombre"));
                carreras.add(carrera);
            }
        } catch (SQLException e) {
            System.err.println("❌ Error al listar carreras: " + e.getMessage());
        }
        return carreras;
    }

    public Egresado obtenerPorUsuario(int usuarioId) {
        String sql = "SELECT e.*, c.id AS carrera_id, c.nombre AS carrera_nombre, u.id AS usuario_id, u.username, u.rol "
                + "FROM egresados.egresados e "
                + "JOIN egresados.carreras c ON e.carrera_id = c.id "
                + "JOIN egresados.usuarios u ON e.usuario_id = u.id "
                + "WHERE e.usuario_id = ?";

        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, usuarioId);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                // 📄 Construir objeto Carrera
                Carrera carrera = new Carrera(
                        rs.getInt("carrera_id"),
                        rs.getString("carrera_nombre")
                );

                // 📄 Construir objeto Usuario
                Usuario usuario = new Usuario(
                        rs.getInt("usuario_id"),
                        rs.getString("username"),
                        null, // No cargamos la contraseña
                        rs.getString("rol")
                );

                // 📄 Construir objeto Egresado
                Egresado egresado = new Egresado();
                egresado.setId(rs.getInt("id"));
                egresado.setNombres(rs.getString("nombres"));
                egresado.setApellidos(rs.getString("apellidos"));
                egresado.setDni(rs.getString("dni"));
                egresado.setCorreo(rs.getString("correo"));
                egresado.setTelefono(rs.getString("telefono"));
                egresado.setFechaNacimiento(rs.getDate("fecha_nacimiento"));
                egresado.setSexo(rs.getString("sexo"));
                egresado.setDireccion(rs.getString("direccion"));
                egresado.setCarrera(carrera);
                egresado.setFechaEgreso(rs.getDate("fecha_egreso"));
                egresado.setUsuario(usuario);

                // Inicializar listas vacías
                egresado.setExperiencias(new ArrayList<>());
                egresado.setEstudiosAdicionales(new ArrayList<>());

                return egresado;
            }
        } catch (SQLException e) {
            System.err.println("❌ Error al buscar egresado por usuario_id: " + e.getMessage());
        }
        return null;
    }

    /**
     * ✅ Actualizar los datos de un egresado existente
     */
    public boolean actualizarEgresado(Egresado egresado) {
        String sql = "UPDATE egresados.egresados SET "
                + "nombres = ?, "
                + "apellidos = ?, "
                + "dni = ?, "
                + "correo = ?, "
                + "telefono = ?, "
                + "fecha_nacimiento = ?, "
                + "sexo = ?, "
                + "direccion = ?, "
                + "carrera_id = ?, "
                + "fecha_egreso = ? "
                + "WHERE id = ?";

        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, egresado.getNombres());
            stmt.setString(2, egresado.getApellidos());
            stmt.setString(3, egresado.getDni());
            stmt.setString(4, egresado.getCorreo());
            stmt.setString(5, egresado.getTelefono());
            stmt.setDate(6, egresado.getFechaNacimiento() != null ? new java.sql.Date(egresado.getFechaNacimiento().getTime()) : null);
            stmt.setString(7, egresado.getSexo());
            stmt.setString(8, egresado.getDireccion());
            stmt.setInt(9, egresado.getCarrera().getId());
            stmt.setDate(10, egresado.getFechaEgreso() != null ? new java.sql.Date(egresado.getFechaEgreso().getTime()) : null);
            stmt.setInt(11, egresado.getId());

            int filasAfectadas = stmt.executeUpdate();
            return filasAfectadas > 0; // ✅ Devuelve true si se actualizó
        } catch (SQLException e) {
            System.err.println("❌ Error al actualizar egresado: " + e.getMessage());
            return false;
        }
    }

    public List<Egresado> buscarEgresadosPorNombreODni(String criterio) {
        List<Egresado> lista = new ArrayList<>();
        String sql = "SELECT e.id, e.nombres, e.apellidos, e.dni, e.correo, e.telefono, c.nombre AS carrera "
                + "FROM egresados.egresados e "
                + "JOIN egresados.carreras c ON e.carrera_id = c.id "
                + "WHERE LOWER(e.nombres) LIKE LOWER(?) OR e.dni LIKE ? "
                + "ORDER BY e.nombres ASC";
        try (Connection conn = Conexion.conectar(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, "%" + criterio + "%");
            stmt.setString(2, "%" + criterio + "%");
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                Carrera carrera = new Carrera();
                carrera.setNombre(rs.getString("carrera"));

                Egresado egresado = new Egresado();
                egresado.setId(rs.getInt("id"));
                egresado.setNombres(rs.getString("nombres"));
                egresado.setApellidos(rs.getString("apellidos"));
                egresado.setDni(rs.getString("dni"));
                egresado.setCorreo(rs.getString("correo"));
                egresado.setTelefono(rs.getString("telefono"));
                egresado.setCarrera(carrera);
                lista.add(egresado);
            }
        } catch (SQLException ex) {
            System.err.println("Error al buscar egresados: " + ex.getMessage());
        }
        return lista;
    }

    public List<Egresado> obtenerTodosEgresados() {
        List<Egresado> lista = new ArrayList<>();
        String sql = "SELECT e.id, e.nombres, e.apellidos, e.dni, e.correo, e.telefono, e.fecha_egreso, "
                + "c.id AS carrera_id, c.nombre AS carrera_nombre "
                + "FROM egresados.egresados e "
                + "LEFT JOIN egresados.carreras c ON e.carrera_id = c.id";

        try (Connection conn = Conexion.conectar(); PreparedStatement stmt = conn.prepareStatement(sql); ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                Carrera carrera = null;
                if (rs.getInt("carrera_id") != 0) {
                    carrera = new Carrera();
                    carrera.setId(rs.getInt("carrera_id"));
                    carrera.setNombre(rs.getString("carrera_nombre"));
                }

                Egresado e = new Egresado();
                e.setId(rs.getInt("id"));
                e.setNombres(rs.getString("nombres"));
                e.setApellidos(rs.getString("apellidos"));
                e.setDni(rs.getString("dni"));
                e.setCorreo(rs.getString("correo"));
                e.setTelefono(rs.getString("telefono"));
                e.setFechaEgreso(rs.getDate("fecha_egreso"));
                e.setCarrera(carrera); // ✅ Asignamos la carrera

                lista.add(e);
            }

        } catch (SQLException ex) {
            ex.printStackTrace();
        }

        return lista;
    }

    public Map<String, Integer> contarEgresadosPorCarrera() {
        Map<String, Integer> conteo = new HashMap<>();

        String sql = """
        SELECT c.nombre AS carrera, COUNT(e.id) AS total
        FROM egresados.egresados e
        INNER JOIN egresados.carreras c ON e.carrera_id = c.id
        GROUP BY c.nombre
        ORDER BY total DESC
    """;

        try (Connection conn = Conexion.conectar(); PreparedStatement stmt = conn.prepareStatement(sql); ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                String carrera = rs.getString("carrera");
                int total = rs.getInt("total");
                conteo.put(carrera, total);
            }

        } catch (SQLException e) {
            System.err.println("❌ Error al contar egresados por carrera: " + e.getMessage());
        }

        return conteo;
    }
}
