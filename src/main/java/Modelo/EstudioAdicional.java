
package Modelo;

import java.sql.Date;


public class EstudioAdicional {
    
    private int id;
    private int egresadoId; // FK hacia egresados
    private String institucion;
    private String tipoEstudio;
    private Date fechaInicio;
    private Date fechaFin;
    private String descripcion;

    // ✅ Constructor vacío
    public EstudioAdicional() {}

    // ✅ Constructor completo
    public EstudioAdicional(int id, int egresadoId, String institucion, String tipoEstudio, Date fechaInicio, Date fechaFin, String descripcion) {
        this.id = id;
        this.egresadoId = egresadoId;
        this.institucion = institucion;
        this.tipoEstudio = tipoEstudio;
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

    public String getInstitucion() {
        return institucion;
    }

    public void setInstitucion(String institucion) {
        this.institucion = institucion;
    }

    public String getTipoEstudio() {
        return tipoEstudio;
    }

    public void setTipoEstudio(String tipoEstudio) {
        this.tipoEstudio = tipoEstudio;
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

