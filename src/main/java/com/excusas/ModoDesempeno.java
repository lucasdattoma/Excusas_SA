package com.excusas;

import java.util.Random;

public interface ModoDesempeno {
    String revisar(Responsable responsable, Excusa excusa);

    default ModoDesempeno alProcesar(int totalProcesadas) {
        if (totalProcesadas < 5) {
            return new ModoProductivo();
        }
        if (totalProcesadas <= 10) {
            return new ModoNormal();
        }
        return new ModoVago();
    }

    default ModoDesempeno alTomarCafe() {
        return new ModoProductivo();
    }

    default ModoDesempeno alTomarBreak(int minutos) {
        if (minutos >= 10 && minutos <= 15) {
            return new ModoNormal();
        }
        return this;
    }

    default ModoDesempeno alNuevoDia(Random azar) {
        return this;
    }
}