package com.example.projectepis;

public class Eventos {
    private String nombre, ubicacion, duracion, fecha, horaComienzo, horaFinal, descripcion;

    public Eventos(){}
    public Eventos(String nombre, String ubicacion, String duracion, String fecha, String horaComienzo, String horaFinal, String descripcion) {
        this.nombre = nombre;
        this.ubicacion = ubicacion;
        this.duracion = duracion;
        this.fecha = fecha;
        this.horaComienzo = horaComienzo;
        this.horaFinal = horaFinal;
        this.descripcion = descripcion;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getUbicacion() {
        return ubicacion;
    }

    public void setUbicacion(String ubicacion) {
        this.ubicacion = ubicacion;
    }

    public String getDuracion() {
        return duracion;
    }

    public void setDuracion(String duracion) {
        this.duracion = duracion;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public String getHoraComienzo() {
        return horaComienzo;
    }

    public void setHoraComienzo(String horaComienzo) {
        this.horaComienzo = horaComienzo;
    }

    public String getHoraFinal() {
        return horaFinal;
    }

    public void setHoraFinal(String horaFinal) {
        this.horaFinal = horaFinal;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }
}
