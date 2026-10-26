package com.excusas;

public class Prontuario {

    private final Empleado empleado;
    private final Excusa excusa;

    public Prontuario(Empleado empleado, Excusa excusa) {
        this.empleado = empleado;
        this.excusa = excusa;
    }

    public Empleado getEmpleado() { return empleado; }
    public Excusa getExcusa() { return excusa; }
    public int getLegajo() { return empleado.getLegajo(); }
}