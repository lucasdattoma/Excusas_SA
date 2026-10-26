package com.excusas;

public class ConsultaEdesur implements AccionModerada {

    private static final String EMAIL_EDESUR = "EDESUR@mailfake.com.ar";

    @Override
    public void ejecutar(Excusa excusa, String emailOrigen, EmailSender emailSender) {
        emailSender.enviarEmail(EMAIL_EDESUR, emailOrigen, "consulta corte de luz",
                "¿Es verdad que se cortó la luz en todo el barrio?");
    }
}