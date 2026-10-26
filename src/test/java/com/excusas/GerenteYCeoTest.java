package com.excusas;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

class GerenteYCeoTest {

    private EmailSender emailSender;
    private Empleado juan;
    private Recepcionista recepcionista;
    private SupervisoraDeArea supervisora;
    private GerenteRRHH gerente;
    private CEO ceo;

    @BeforeEach
    void setUp() {
        emailSender = mock(EmailSender.class);
        juan = new Empleado("Juan", "juan@mail.com", 100);
        recepcionista = new Recepcionista("Ana", "ana@excusas.com", 1, emailSender);
        supervisora = new SupervisoraDeArea("Sofia", "sofia@excusas.com", 2, emailSender);
        gerente = new GerenteRRHH("Gonzalo", "gonzalo@excusas.com", 3, emailSender);
        ceo = new CEO("Carla", "carla@excusas.com", 4, emailSender);

        recepcionista.setSiguiente(supervisora);
        supervisora.setSiguiente(gerente);
        gerente.setSiguiente(ceo);
    }

    @Test
    void gerenteAceptaExcusaIrrelevante() {
        Excusa excusa = new Excusa(juan, Motivo.IRRELEVANTE);

        recepcionista.revisarExcusa(excusa);

        assertTrue(excusa.estaAceptada());
    }

    @Test
    void ceoAceptaExcusaInverosimilYResponderAprobadoPorCreatividad() {
        Excusa excusa = new Excusa(juan, Motivo.INVEROSIMIL);

        recepcionista.revisarExcusa(excusa);

        assertTrue(excusa.estaAceptada());
        verify(emailSender).enviarEmail(
                "juan@mail.com", "carla@excusas.com", "Respuesta", "Aprobado por creatividad");
    }

    @Test
    void siLaCadenaNoTerminaEnCeoYNadiePuedeSeRechaza() {
        gerente.setSiguiente(null);
        Excusa excusa = new Excusa(juan, Motivo.INVEROSIMIL);

        String respuesta = recepcionista.revisarExcusaConRespuesta(excusa);

        assertFalse(excusa.estaAceptada());
        assertEquals(Responsable.RECHAZO, respuesta);
    }
}