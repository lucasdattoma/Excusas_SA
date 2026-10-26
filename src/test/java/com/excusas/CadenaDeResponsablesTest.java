package com.excusas;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;

class CadenaDeResponsablesTest {

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
    void recepcionistaDerivaALaSupervisoraCuandoNoPuedeProcesar() {
        Excusa excusa = new Excusa(juan, Motivo.PERDIDA_SUMINISTRO);

        recepcionista.revisarExcusa(excusa);

        assertTrue(excusa.estaAceptada());
    }

    @Test
    void siNadieLaPuedeProcesarSeRechazaConMensaje() {
        Excusa excusa = new Excusa(juan, Motivo.INVEROSIMIL);

        String respuesta = recepcionista.revisarExcusaConRespuesta(excusa);

        assertFalse(excusa.estaAceptada());
        assertEquals("Excusa rechazada: necesitamos pruebas contundentes", respuesta);
    }
}