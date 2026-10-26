package com.excusas;

import java.util.Map;

public class SupervisoraDeArea extends Responsable {

    private final Map<Motivo, AccionModerada> acciones = Map.of(
            Motivo.PERDIDA_SUMINISTRO, new ConsultaEdesur(),
            Motivo.CUIDADO_FAMILIAR, new ConsultaFamiliar());

    public SupervisoraDeArea(String nombre, String email, int legajo, EmailSender emailSender) {
        super(nombre, email, legajo, emailSender);
    }

    @Override
    protected boolean puedeProcesar(Excusa excusa) {
        return acciones.containsKey(excusa.getMotivo());
    }

    @Override
    protected void procesar(Excusa excusa) {
        excusa.aceptar();
        acciones.get(excusa.getMotivo()).ejecutar(excusa, getEmail(), emailSender);
    }
}