/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Conexion;

/**
 *
 * @author Usuario
 */
public class Egresado {
    int  estudiante_id;
    String codigo_egresados;
    String nombre;
    String apellidos;
    String dni;
    String email_personal;
    String telefono;
    Date fecha_ingreso;
    Date fecha_egreso;
    int carrera_id;
    String empresa_actual;
    String puesto_actual;
    Date fecha_ingreso_emp; 
    int duracion_anios;
    int facultad_id;
    int curso_id;
    int creditos;
    int profesor_id;
    int ciclo;
    double numeric;
    String correo;
    String especialidad;
    int sede_id;

//Constructor vacio
    public Egresado(){
    }

//ToString
       @Override
    public String toString() {
        return "Consultas{" + "estudiante_id=" + estudiante_id + ", codigo_egresados=" + codigo_egresados + ", nombre=" + nombre + ", apellidos=" + apellidos + ", dni=" + dni + ", email_personal=" + email_personal + ", telefono=" + telefono + ", fecha_ingreso=" + fecha_ingreso + ", fecha_egreso=" + fecha_egreso + ", carrera_id=" + carrera_id + ", empresa_actual=" + empresa_actual + ", puesto_actual=" + puesto_actual + ", fecha_ingreso_emp=" + fecha_ingreso_emp + ", duracion_anios=" + duracion_anios + ", facultad_id=" + facultad_id + ", curso_id=" + curso_id + ", creditos=" + creditos + ", profesor_id=" + profesor_id + ", ciclo=" + ciclo + ", numeric=" + numeric + ", correo=" + correo + ", especialidad=" + especialidad + ", sede_id=" + sede_id + '}';
    }


//Contructores 

        public Consultas(int estudiante_id, String codigo_egresados, String nombre, String apellidos, String dni, String email_personal, String telefono, Date fecha_ingreso, Date fecha_egreso, int carrera_id, String empresa_actual, String puesto_actual, Date fecha_ingreso_emp, int duracion_anios, int facultad_id, int curso_id, int creditos, int profesor_id, int ciclo, double numeric, String correo, String especialidad, int sede_id) {
        this.estudiante_id = estudiante_id;
        this.codigo_egresados = codigo_egresados;
        this.nombre = nombre;
        this.apellidos = apellidos;
        this.dni = dni;
        this.email_personal = email_personal;
        this.telefono = telefono;
        this.fecha_ingreso = fecha_ingreso;
        this.fecha_egreso = fecha_egreso;
        this.carrera_id = carrera_id;
        this.empresa_actual = empresa_actual;
        this.puesto_actual = puesto_actual;
        this.fecha_ingreso_emp = fecha_ingreso_emp;
        this.duracion_anios = duracion_anios;
        this.facultad_id = facultad_id;
        this.curso_id = curso_id;
        this.creditos = creditos;
        this.profesor_id = profesor_id;
        this.ciclo = ciclo;
        this.numeric = numeric;
        this.correo = correo;
        this.especialidad = especialidad;
        this.sede_id = sede_id;
    }



    //getters y setters 
    public int getEstudiante_id() {
        return estudiante_id;
    }

    public void setEstudiante_id(int estudiante_id) {
        this.estudiante_id = estudiante_id;
    }

    public String getCodigo_egresados() {
        return codigo_egresados;
    }

    public void setCodigo_egresados(String codigo_egresados) {
        this.codigo_egresados = codigo_egresados;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
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

    public String getEmail_personal() {
        return email_personal;
    }

    public void setEmail_personal(String email_personal) {
        this.email_personal = email_personal;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public Date getFecha_ingreso() {
        return fecha_ingreso;
    }

    public void setFecha_ingreso(Date fecha_ingreso) {
        this.fecha_ingreso = fecha_ingreso;
    }

    public Date getFecha_egreso() {
        return fecha_egreso;
    }

    public void setFecha_egreso(Date fecha_egreso) {
        this.fecha_egreso = fecha_egreso;
    }

    public int getCarrera_id() {
        return carrera_id;
    }

    public void setCarrera_id(int carrera_id) {
        this.carrera_id = carrera_id;
    }

    public String getEmpresa_actual() {
        return empresa_actual;
    }

    public void setEmpresa_actual(String empresa_actual) {
        this.empresa_actual = empresa_actual;
    }

    public String getPuesto_actual() {
        return puesto_actual;
    }

    public void setPuesto_actual(String puesto_actual) {
        this.puesto_actual = puesto_actual;
    }

    public Date getFecha_ingreso_emp() {
        return fecha_ingreso_emp;
    }

    public void setFecha_ingreso_emp(Date fecha_ingreso_emp) {
        this.fecha_ingreso_emp = fecha_ingreso_emp;
    }

    public int getDuracion_anios() {
        return duracion_anios;
    }

    public void setDuracion_anios(int duracion_anios) {
        this.duracion_anios = duracion_anios;
    }

    public int getFacultad_id() {
        return facultad_id;
    }

    public void setFacultad_id(int facultad_id) {
        this.facultad_id = facultad_id;
    }

    public int getCurso_id() {
        return curso_id;
    }

    public void setCurso_id(int curso_id) {
        this.curso_id = curso_id;
    }

    public int getCreditos() {
        return creditos;
    }

    public void setCreditos(int creditos) {
        this.creditos = creditos;
    }

    public int getProfesor_id() {
        return profesor_id;
    }

    public void setProfesor_id(int profesor_id) {
        this.profesor_id = profesor_id;
    }

    public int getCiclo() {
        return ciclo;
    }

    public void setCiclo(int ciclo) {
        this.ciclo = ciclo;
    }

    public double getNumeric() {
        return numeric;
    }

    public void setNumeric(double numeric) {
        this.numeric = numeric;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public String getEspecialidad() {
        return especialidad;
    }

    public void setEspecialidad(String especialidad) {
        this.especialidad = especialidad;
    }

    public int getSede_id() {
        return sede_id;
    }

    public void setSede_id(int sede_id) {
        this.sede_id = sede_id;
    }


    
}
