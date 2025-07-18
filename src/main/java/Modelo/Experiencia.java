
package Modelo;

import java.sql.Date;

public class Experiencia {
    
      private int id;
    private int egresadoId; // FK hacia egresados
    private String empresa;
    private String cargo;
    private Date fechaInicio;
    private Date fechaFin;
    private String descripcion;

    // ✅ Constructor vacío
    public Experiencia() {}

    // ✅ Constructor completo
    public Experiencia(int id, int egresadoId, String empresa, String cargo, Date fechaInicio, Date fechaFin, String descripcion) {
        this.id = id;
        this.egresadoId = egresadoId;
        this.empresa = empresa;
        this.cargo = cargo;
        this.fechaInicio = fechaInicio;
        this.fechaFin = fechaFin;
        this.descripcion = descripcion;
    }

    // ⚙️ Getters y Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getEgresadoId() {
        return egresadoId;
    }

    public void setEgresadoId(int egresadoId) {
        this.egresadoId = egresadoId;
    }

    public String getEmpresa() {
        return empresa;
    }

    public void setEmpresa(String empresa) {
        this.empresa = empresa;
    }

    public String getCargo() {
        return cargo;
    }

    public void setCargo(String cargo) {
        this.cargo = cargo;
    }

    public Date getFechaInicio() {
        return fechaInicio;
    }

    public void setFechaInicio(Date fechaInicio) {
        this.fechaInicio = fechaInicio;
    }

    public Date getFechaFin() {
        return fechaFin;
    }

    public void setFechaFin(Date fechaFin) {
        this.fechaFin = fechaFin;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }
}

