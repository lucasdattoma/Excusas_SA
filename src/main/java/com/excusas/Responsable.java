package com.excusas;

import java.util.Random;

public abstract class Responsable extends Empleado implements Handler {

    public static final String RECHAZO = "Excusa rechazada: necesitamos pruebas contundentes";
    private static final String EMAIL_CTO = "cto@excusas.com";

    protected final EmailSender emailSender;
    private Handler siguiente;
    private ModoDesempeno modo = new ModoNormal();
    private int excusasProcesadas = 0;

    protected Responsable(String nombre, String email, int legajo, EmailSender emailSender) {
        super(nombre, email, legajo);
        this.emailSender = emailSender;
    }

    public void setSiguiente(Handler siguiente) {
        this.siguiente = siguiente;
    }

    public void setModo(ModoDesempeno modo) {
        this.modo = modo;
    }

    public ModoDesempeno getModo() {
        return modo;
    }

    @Override
    public void revisarExcusa(Excusa unaExcusa) {
        revisarExcusaConRespuesta(unaExcusa);
    }

    @Override
    public String revisarExcusaConRespuesta(Excusa unaExcusa) {
        return modo.revisar(this, unaExcusa);
    }

    public void tomarCafe() {
        modo = modo.alTomarCafe();
    }

    public void tomarBreak(int minutos) {
        modo = modo.alTomarBreak(minutos);
    }

    public void nuevoDia(Random azar) {
        modo = modo.alNuevoDia(azar);
    }

    void registrarProcesada() {
        excusasProcesadas++;
        modo = modo.alProcesar(excusasProcesadas);
    }

    String derivar(Excusa excusa) {
        if (siguiente == null) {
            return RECHAZO;
        }
        return siguiente.revisarExcusaConRespuesta(excusa);
    }

    void avisarAlCto() {
        emailSender.enviarEmail(EMAIL_CTO, getEmail(), "Excusa derivada",
                "Derivé una excusa que no pude procesar");
    }

    protected abstract boolean puedeProcesar(Excusa excusa);

    protected abstract void procesar(Excusa excusa);
}