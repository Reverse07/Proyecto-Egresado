
package Modelo;

import java.time.LocalDate;


public class Reporte {
    private String titulo;          // Ej: "Egresados por Carrera"
    private String categoria;       // Ej: Nombre de la carrera
    private int cantidad;           // Total de registros
    private double porcentaje;      // Porcentaje respecto al total

    // Para reportes filtrados por fechas (opcional)
    private LocalDate fechaInicio;  
    private LocalDate fechaFin;

    // --- Constructores ---
    public Reporte() {
    }

    public Reporte(String titulo, String categoria, int cantidad, double porcentaje) {
        this.titulo = titulo;
        this.categoria = categoria;
        this.cantidad = cantidad;
        this.porcentaje = porcentaje;
    }

    public Reporte(String titulo, String categoria, int cantidad, double porcentaje,
                   LocalDate fechaInicio, LocalDate fechaFin) {
        this(titulo, categoria, cantidad, porcentaje); // Reutiliza el constructor anterior
        this.fechaInicio = fechaInicio;
        this.fechaFin = fechaFin;
    }

    // --- Getters y Setters ---
    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getCategoria() {
        return categoria;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }

    public double getPorcentaje() {
        return porcentaje;
    }

    public void setPorcentaje(double porcentaje) {
        this.porcentaje = porcentaje;
    }

    public LocalDate getFechaInicio() {
        return fechaInicio;
    }

    public void setFechaInicio(LocalDate fechaInicio) {
        this.fechaInicio = fechaInicio;
    }

    public LocalDate getFechaFin() {
        return fechaFin;
    }

    public void setFechaFin(LocalDate fechaFin) {
        this.fechaFin = fechaFin;
    }

    @Override
    public String toString() {
        return "Reporte{" +
                "titulo='" + titulo + '\'' +
                ", categoria='" + categoria + '\'' +
                ", cantidad=" + cantidad +
                ", porcentaje=" + porcentaje +
                (fechaInicio != null ? ", fechaInicio=" + fechaInicio : "") +
                (fechaFin != null ? ", fechaFin=" + fechaFin : "") +
                '}';
    }
}