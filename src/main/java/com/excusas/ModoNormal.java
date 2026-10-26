package com.excusas;

public class ModoNormal implements ModoDesempeno {

    @Override
    public String revisar(Responsable responsable, Excusa excusa) {
        if (responsable.puedeProcesar(excusa)) {
            responsable.procesar(excusa);
            responsable.registrarProcesada();
            return "Excusa aceptada";
        }
        return responsable.derivar(excusa);
    }
}