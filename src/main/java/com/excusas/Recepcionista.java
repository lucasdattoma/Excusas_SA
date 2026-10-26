package com.excusas;

public class Recepcionista extends Responsable {

    public Recepcionista(String nombre, String email, int legajo, EmailSender emailSender) {
        super(nombre, email, legajo, emailSender);
    }

    @Override
    protected boolean puedeProcesar(Excusa excusa) {
        return excusa.getMotivo() == Motivo.QUEDARSE_DORMIDO
                || excusa.getMotivo() == Motivo.PERDI_TRANSPORTE;
    }

    @Override
    protected void procesar(Excusa excusa) {
        excusa.aceptar();
        emailSender.enviarEmail(
                excusa.getEmpleado().getEmail(), getEmail(), "motivo demora", "la licencia fue aceptada");
    }
}