package com.excusas;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

class RecepcionistaTest {

    private EmailSender emailSender;
    private Empleado juan;
    private Recepcionista recepcionista;

    @BeforeEach
    void setUp() {
        emailSender = mock(EmailSender.class);
        juan = new Empleado("Juan", "juan@mail.com", 100);
        recepcionista = new Recepcionista("Ana", "ana@excusas.com", 1, emailSender);
    }

    @Test
    void aceptaExcusaDeQuedarseDormidoYEnviaMailAlEmpleado() {
        Excusa excusa = new Excusa(juan, Motivo.QUEDARSE_DORMIDO);

        recepcionista.revisarExcusa(excusa);

        assertTrue(excusa.estaAceptada());
        verify(emailSender).enviarEmail(
                "juan@mail.com", "ana@excusas.com", "motivo demora", "la licencia fue aceptada");
    }

    @Test
    void aceptaExcusaDePerderElTransporte() {
        Excusa excusa = new Excusa(juan, Motivo.PERDI_TRANSPORTE);

        recepcionista.revisarExcusa(excusa);

        assertTrue(excusa.estaAceptada());
    }
}