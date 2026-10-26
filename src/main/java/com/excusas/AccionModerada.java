package com.excusas;

public interface AccionModerada {
    void ejecutar(Excusa excusa, String emailOrigen, EmailSender emailSender);
}