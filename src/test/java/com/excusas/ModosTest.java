package com.excusas;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;

class ModosTest {

    private EmailSender emailSender;
    private Empleado juan;
    private Recepcionista recepcionista;
    private SupervisoraDeArea supervisora;

    @BeforeEach
    void setUp() {
        emailSender = mock(EmailSender.class);
        juan = new Empleado("Juan", "juan@mail.com", 100);
        recepcionista = new Recepcionista("Ana", "ana@excusas.com", 1, emailSender);
        supervisora = new SupervisoraDeArea("Sofia", "sofia@excusas.com", 2, emailSender);
        recepcionista.setSiguiente(supervisora);
    }

    @Test
    void modoNormalEsElPorDefectoYEvaluaLaExcusa() {
        Excusa excusa = new Excusa(juan, Motivo.QUEDARSE_DORMIDO);

        recepcionista.revisarExcusa(excusa);

        assertTrue(excusa.estaAceptada());
    }

    @Test
    void modoVagoNoEvaluaYDerivaAlSiguiente() {
        recepcionista.setModo(new ModoVago());
        Excusa excusa = new Excusa(juan, Motivo.QUEDARSE_DORMIDO);

        recepcionista.revisarExcusa(excusa);

        assertFalse(excusa.estaAceptada());
        verify(emailSender, never()).enviarEmail(
                "juan@mail.com", "ana@excusas.com", "motivo demora", "la licencia fue aceptada");
    }

    @Test
    void modoProductivoEvaluaSiPuedeYNoAvisaAlCto() {
        recepcionista.setModo(new ModoProductivo());
        Excusa excusa = new Excusa(juan, Motivo.QUEDARSE_DORMIDO);

        recepcionista.revisarExcusa(excusa);

        assertTrue(excusa.estaAceptada());
        verify(emailSender, never()).enviarEmail(
                org.mockito.ArgumentMatchers.eq("cto@excusas.com"), anyString(), anyString(), anyString());
    }

    @Test
    void modoProductivoAvisaAlCtoCuandoNoPuedeYDeriva() {
        recepcionista.setModo(new ModoProductivo());
        Excusa excusa = new Excusa(juan, Motivo.PERDIDA_SUMINISTRO);

        recepcionista.revisarExcusa(excusa);

        assertTrue(excusa.estaAceptada());
        verify(emailSender).enviarEmail(
                org.mockito.ArgumentMatchers.eq("cto@excusas.com"), anyString(), anyString(), anyString());
    }
}