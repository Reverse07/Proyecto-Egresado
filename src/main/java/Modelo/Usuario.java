
package Modelo;


public class Usuario {
   private int id;
    private String nombreUsuario;
    private String password; // Cambiado a "password" por convención
    private String rol;

    // --- Constructores ---
    public Usuario() {
        // Constructor vacío
    }

    public Usuario(int id, String nombreUsuario, String password, String rol) {
        this.id = id;
        this.nombreUsuario = nombreUsuario;
        this.password = password;
        this.rol = rol;
    }

    public Usuario(String nombreUsuario, String password, String rol) {
        this.nombreUsuario = nombreUsuario;
        this.password = password;
        this.rol = rol;
    }

    // --- Getters y Setters ---
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNombreUsuario() {
        return nombreUsuario;
    }

    public void setNombreUsuario(String nombreUsuario) {
        this.nombreUsuario = nombreUsuario;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRol() {
        return rol;
    }

    public void setRol(String rol) {
        this.rol = rol;
    }

    @Override
    public String toString() {
        return "Usuario{" +
                "id=" + id +
                ", nombreUsuario='" + nombreUsuario + '\'' +
                ", rol='" + rol + '\'' + // Nunca mostrar contraseña
                '}';
    }
}