package com.excusas;

public class ConsultaFamiliar implements AccionModerada {

    @Override
    public void ejecutar(Excusa excusa, String emailOrigen, EmailSender emailSender) {
        emailSender.enviarEmail(excusa.getEmpleado().getEmail(), emailOrigen,
                "consulta familiar", "¿Está todo bien?");
    }
}