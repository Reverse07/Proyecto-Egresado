package Modelo;

import java.sql.Date;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Usuario
 */
public class Egresado {

     private int id;
    private String nombres;
    private String apellidos;
    private String dni;
    private String correo;
    private String telefono;
    private Date fechaNacimiento;
    private String sexo;
    private String direccion;
    private Carrera carrera; // Relación con la tabla carreras
    private Date fechaEgreso;
    private Usuario usuario; // Usuario asociado
    private List<Experiencia> experiencias; // Relación 1:N con experiencias
    private List<EstudioAdicional> estudiosAdicionales; // Relación 1:N con estudios adicionales

    public Egresado() {
    }

    public Egresado(int id, String nombres, String apellidos, String dni, String correo, String telefono, Date fechaNacimiento, String sexo, String direccion, Carrera carrera, Date fechaEgreso, Usuario usuario, List<Experiencia> experiencias, List<EstudioAdicional> estudiosAdicionales) {
        this.id = id;
        this.nombres = nombres;
        this.apellidos = apellidos;
        this.dni = dni;
        this.correo = correo;
        this.telefono = telefono;
        this.fechaNacimiento = fechaNacimiento;
        this.sexo = sexo;
        this.direccion = direccion;
        this.carrera = carrera;
        this.fechaEgreso = fechaEgreso;
        this.usuario = usuario;
        this.experiencias = experiencias;
        this.estudiosAdicionales = estudiosAdicionales;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNombres() {
        return nombres;
    }

    public void setNombres(String nombres) {
        this.nombres = nombres;
    }

    public String getApellidos() {
        return apellidos;
    }

    public void setApellidos(String apellidos) {
        this.apellidos = apellidos;
    }

    public String getDni() {
        return dni;
    }

    public void setDni(String dni) {
        this.dni = dni;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public Date getFechaNacimiento() {
        return fechaNacimiento;
    }

    public void setFechaNacimiento(Date fechaNacimiento) {
        this.fechaNacimiento = fechaNacimiento;
    }

    public String getSexo() {
        return sexo;
    }

    public void setSexo(String sexo) {
        this.sexo = sexo;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public Carrera getCarrera() {
        return carrera;
    }

    public void setCarrera(Carrera carrera) {
        this.carrera = carrera;
    }

    public Date getFechaEgreso() {
        return fechaEgreso;
    }

    public void setFechaEgreso(Date fechaEgreso) {
        this.fechaEgreso = fechaEgreso;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public List<Experiencia> getExperiencias() {
        return experiencias;
    }

    public void setExperiencias(List<Experiencia> experiencias) {
        this.experiencias = experiencias;
    }

    public List<EstudioAdicional> getEstudiosAdicionales() {
        return estudiosAdicionales;
    }

    public void setEstudiosAdicionales(List<EstudioAdicional> estudiosAdicionales) {
        this.estudiosAdicionales = estudiosAdicionales;
    }
}
