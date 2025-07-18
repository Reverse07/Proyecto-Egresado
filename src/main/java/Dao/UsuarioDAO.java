package Dao;

import Modelo.Conexion;
import Modelo.Usuario;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class UsuarioDAO {

    private Connection conexion;

    public UsuarioDAO() {
          conexion = Conexion.conectar();
    }

    /**
     * ✅ Autenticar usuario (texto plano)
     */
    public Usuario autenticar(String username, String password) throws SQLException {
        String sql = "SELECT * FROM egresados.usuarios WHERE username = ? AND password = ?";
        try (PreparedStatement stmt = conexion.prepareStatement(sql)) {
            stmt.setString(1, username);
            stmt.setString(2, password);

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    Usuario usuario = new Usuario();
                    usuario.setId(rs.getInt("id"));
                    usuario.setNombreUsuario(rs.getString("username"));
                    usuario.setRol(rs.getString("rol"));
                    return usuario;
                } else {
                    return null; // ❌ Usuario no encontrado
                }
            }
        }
    }

    /**
     * ✅ Registrar un nuevo usuario (sin encriptar la contraseña)
     */
    public boolean registrarUsuario(Usuario usuario) throws SQLException {
        String sql = "INSERT INTO egresados.usuarios (username, password, rol) "
                + "VALUES (?, ?, ?)";
        try (PreparedStatement stmt = conexion.prepareStatement(sql)) {
            stmt.setString(1, usuario.getNombreUsuario());
            stmt.setString(2, usuario.getPassword()); // Guarda tal cual
            stmt.setString(3, usuario.getRol());
            return stmt.executeUpdate() > 0;
        }
    }
     public List<Usuario> obtenerTodosUsuarios() {
        List<Usuario> listaUsuarios = new ArrayList<>();

        String sql = "SELECT id, username, rol FROM egresados.usuarios";

        try (PreparedStatement ps = conexion.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                Usuario usuario = new Usuario();
                usuario.setId(rs.getInt("id"));
                usuario.setNombreUsuario(rs.getString("username"));
                usuario.setRol(rs.getString("rol"));

                listaUsuarios.add(usuario);
            }

        } catch (SQLException e) {
            System.err.println("❌ Error al obtener todos los usuarios: " + e.getMessage());
        }

        return listaUsuarios;
    }
     
     public Map<String, Integer> contarUsuariosPorRol() {
        Map<String, Integer> conteoPorRol = new HashMap<>();
        String sql = "SELECT rol, COUNT(*) AS cantidad FROM egresados.usuarios GROUP BY rol";

        try (PreparedStatement ps = conexion.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                String rol = rs.getString("rol");
                int cantidad = rs.getInt("cantidad");
                conteoPorRol.put(rol, cantidad);
            }

        } catch (SQLException e) {
            System.err.println("❌ Error al contar usuarios por rol: " + e.getMessage());
        }

        return conteoPorRol;
    }
   public List<Usuario> buscarUsuariosPorNombreOUsername(String criterio) {
    List<Usuario> usuarios = new ArrayList<>();
    String sql = "SELECT id, username, rol FROM egresados.usuarios WHERE username ILIKE ? OR rol ILIKE ?";

    try (Connection conn = Conexion.conectar();
         PreparedStatement stmt = conn.prepareStatement(sql)) {

        String filtro = "%" + criterio + "%"; // Para búsqueda parcial
        stmt.setString(1, filtro);
        stmt.setString(2, filtro);

        ResultSet rs = stmt.executeQuery();
        while (rs.next()) {
            Usuario usuario = new Usuario();
            usuario.setId(rs.getInt("id"));
            usuario.setNombreUsuario(rs.getString("username"));
            usuario.setRol(rs.getString("rol"));
            usuarios.add(usuario);
        }

    } catch (SQLException e) {
        System.err.println("❌ Error al buscar usuarios: " + e.getMessage());
    }

    return usuarios;
}
   public boolean insertarUsuario(Usuario usuario) {
    String sql = "INSERT INTO egresados.usuarios (username, password, rol) VALUES (?, crypt(?, gen_salt('bf')), ?)";

    try (Connection conn = Conexion.conectar();
         PreparedStatement stmt = conn.prepareStatement(sql)) {

        stmt.setString(1, usuario.getNombreUsuario());
        stmt.setString(2, usuario.getPassword()); // 🔑 Encriptamos con pgcrypto
        stmt.setString(3, usuario.getRol());

        int rowsInserted = stmt.executeUpdate();
        return rowsInserted > 0;

    } catch (SQLException e) {
        System.err.println("❌ Error al insertar usuario: " + e.getMessage());
        return false;
    }
}
}
