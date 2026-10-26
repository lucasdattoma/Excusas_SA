package com.excusas;

import java.util.Random;

public class ModoVago implements ModoDesempeno {

    @Override
    public String revisar(Responsable responsable, Excusa excusa) {
        return responsable.derivar(excusa);
    }

    @Override
    public ModoDesempeno alNuevoDia(Random azar) {
        if (azar.nextDouble() < 0.5) {
            return new ModoProductivo();
        }
        return new ModoNormal();
    }
}