package com.excusas;

public class CEO extends Responsable {

    public CEO(String nombre, String email, int legajo, EmailSender emailSender) {
        super(nombre, email, legajo, emailSender);
    }

    @Override
    protected boolean puedeProcesar(Excusa excusa) {
        return excusa.getMotivo() == Motivo.INVEROSIMIL;
    }

    @Override
    protected void procesar(Excusa excusa) {
        excusa.aceptar();
        emailSender.enviarEmail(
                excusa.getEmpleado().getEmail(), getEmail(), "Respuesta", "Aprobado por creatividad");
        AdministradorDeProntuarios.getInstance()
                .registrar(new Prontuario(excusa.getEmpleado(), excusa));
    }
}