package com.excusas;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Random;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.mockito.Mockito.mock;

class TransicionesTest {

    private EmailSender emailSender;
    private Empleado juan;
    private Recepcionista recepcionista;

    @BeforeEach
    void setUp() {
        emailSender = mock(EmailSender.class);
        juan = new Empleado("Juan", "juan@mail.com", 100);
        recepcionista = new Recepcionista("Ana", "ana@excusas.com", 1, emailSender);
    }

    private void procesar(int cantidad) {
        for (int i = 0; i < cantidad; i++) {
            recepcionista.revisarExcusa(new Excusa(juan, Motivo.QUEDARSE_DORMIDO));
        }
    }

    @Test
    void conMenosDeCincoExcusasProcesadasEstaProductivo() {
        procesar(3);
        assertTrue(recepcionista.getModo() instanceof ModoProductivo);
    }

    @Test
    void entreCincoYDiezExcusasEstaNormal() {
        procesar(7);
        assertTrue(recepcionista.getModo() instanceof ModoNormal);
    }

    @Test
    void conOnceOMasExcusasEstaVago() {
        procesar(11);
        assertTrue(recepcionista.getModo() instanceof ModoVago);
    }

    @Test
    void tomarCafeLoVuelveProductivo() {
        recepcionista.setModo(new ModoVago());
        recepcionista.tomarCafe();
        assertTrue(recepcionista.getModo() instanceof ModoProductivo);
    }

    @Test
    void breakDeDozeMinutosLoVuelveNormal() {
        recepcionista.setModo(new ModoVago());
        recepcionista.tomarBreak(12);
        assertTrue(recepcionista.getModo() instanceof ModoNormal);
    }

    @Test
    void breakMuyCortoOMuyLargoNoTieneEfecto() {
        recepcionista.setModo(new ModoVago());
        recepcionista.tomarBreak(5);
        assertTrue(recepcionista.getModo() instanceof ModoVago);
        recepcionista.tomarBreak(20);
        assertTrue(recepcionista.getModo() instanceof ModoVago);
    }

    @Test
    void alDiaSiguienteUnVagoPasaAProductivoSiElAzarEsMenorAlCincuenta() {
        recepcionista.setModo(new ModoVago());
        recepcionista.nuevoDia(azarFijo(0.1, 0.1));
        assertTrue(recepcionista.getModo() instanceof ModoProductivo);
    }

    @Test
    void alDiaSiguienteUnVagoPasaANormalSiElAzarEsMayorAlCincuenta() {
        recepcionista.setModo(new ModoVago());
        recepcionista.nuevoDia(azarFijo(0.9, 0.9));
        assertTrue(recepcionista.getModo() instanceof ModoNormal);
    }

    @Test
    void alDiaSiguienteUnNoVagoNoCambia() {
        recepcionista.setModo(new ModoNormal());
        recepcionista.nuevoDia(azarFijo(0.1, 0.1));
        assertFalse(recepcionista.getModo() instanceof ModoProductivo);
    }

    private Random azarFijo(double primero, double segundo) {
        return new Random() {
            private final double[] valores = {primero, segundo};
            private int i = 0;
            @Override
            public double nextDouble() {
                return valores[Math.min(i++, valores.length - 1)];
            }
        };
    }
}