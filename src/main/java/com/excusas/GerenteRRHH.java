package com.excusas;

public class GerenteRRHH extends Responsable {

    public GerenteRRHH(String nombre, String email, int legajo, EmailSender emailSender) {
        super(nombre, email, legajo, emailSender);
    }

    @Override
    protected boolean puedeProcesar(Excusa excusa) {
        return excusa.getMotivo() == Motivo.IRRELEVANTE;
    }

    @Override
    protected void procesar(Excusa excusa) {
        excusa.aceptar();
    }
}