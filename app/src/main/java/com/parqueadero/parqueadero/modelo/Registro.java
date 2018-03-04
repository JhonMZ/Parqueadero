package com.parqueadero.parqueadero.modelo;

import com.orm.SugarRecord;

import java.util.Date;

/**
 * Created by JhonMZ on 28/01/2018.
 */

public class Registro extends SugarRecord {

    private String placa;
    private String identificacion;
    private String nombre;
    private String telefono;
    private String observacion;
    private String fechaIngreso;
    private String fechaSalida;
    private double total;

    public Registro() {
        this.placa = "";
        this.identificacion = "";
        this.nombre = "";
        this.telefono = "";
        this.observacion = "";
        this.fechaIngreso = new Date().toString();
        this.fechaSalida = new Date().toString();
        this.total = 0;
    }

    public Registro(String placa, String identificacion, String nombre, String telefono, String observacion, String fechaIngreso, String fechaSalida, double total) {
        this.placa = placa;
        this.identificacion = identificacion;
        this.nombre = nombre;
        this.telefono = telefono;
        this.observacion = observacion;
        this.fechaIngreso = fechaIngreso;
        this.fechaSalida = fechaSalida;
        this.total = total;
    }

    public String getPlaca() {
        return placa;
    }

    public void setPlaca(String placa) {
        this.placa = placa;
    }

    public String getIdentificacion() {
        return identificacion;
    }

    public void setIdentificacion(String identificacion) {
        this.identificacion = identificacion;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getObservacion() {
        return observacion;
    }

    public void setObservacion(String observacion) {
        this.observacion = observacion;
    }

    public String getFechaIngreso() {
        return fechaIngreso;
    }

    public void setFechaIngreso(String fechaIngreso) {
        this.fechaIngreso = fechaIngreso;
    }

    public String getFechaSalida() {
        return fechaSalida;
    }

    public void setFechaSalida(String fechaSalida) {
        this.fechaSalida = fechaSalida;
    }

    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
    }

}
