
package Modelo;


public class Carrera {
    
    private int id;
    private String nombre;

    // ✅ Constructor vacío
    public Carrera() {}

    // ✅ Constructor completo
    public Carrera(int id, String nombre) {
        this.id = id;
        this.nombre = nombre;
    }

    // ⚙️ Getters y Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    // 📝 Método toString para mostrar el nombre en JComboBox, JTable, etc.
    @Override
    public String toString() {
        return nombre;
    }
}

