package com.excusas;

public interface EmailSender {
    void enviarEmail(String unEmailDestino, String unEmailOrigen, String unAsunto, String unCuerpo);
}