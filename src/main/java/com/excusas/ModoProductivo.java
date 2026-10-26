package com.excusas;

public class ModoProductivo implements ModoDesempeno {

    @Override
    public String revisar(Responsable responsable, Excusa excusa) {
        if (responsable.puedeProcesar(excusa)) {
            responsable.procesar(excusa);
            responsable.registrarProcesada();
            return "Excusa aceptada";
        }
        responsable.avisarAlCto();
        return responsable.derivar(excusa);
    }
}