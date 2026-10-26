package com.excusas;

public class Excusa {

    private final Empleado empleado;
    private final Motivo motivo;
    private boolean aceptada;

    public Excusa(Empleado empleado, Motivo motivo) {
        this.empleado = empleado;
        this.motivo = motivo;
        this.aceptada = false;
    }

    public Empleado getEmpleado() { return empleado; }
    public Motivo getMotivo() { return motivo; }
    public boolean estaAceptada() { return aceptada; }
    public void aceptar() { this.aceptada = true; }
}