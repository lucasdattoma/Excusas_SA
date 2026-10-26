package com.excusas;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;

class CoberturaExtraTest {

    private EmailSender emailSender;
    private Empleado juan;
    private Recepcionista recepcionista;
    private SupervisoraDeArea supervisora;
    private GerenteRRHH gerente;
    private CEO ceo;

    @BeforeEach
    void setUp() {
        AdministradorDeProntuarios.getInstance().reiniciar();
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
    void supervisoraConsultaAEdesurPorPerdidaDeSuministro() {
        Excusa excusa = new Excusa(juan, Motivo.PERDIDA_SUMINISTRO);

        recepcionista.revisarExcusa(excusa);

        assertTrue(excusa.estaAceptada());
        verify(emailSender).enviarEmail(
                org.mockito.ArgumentMatchers.eq("EDESUR@mailfake.com.ar"),
                org.mockito.ArgumentMatchers.eq("sofia@excusas.com"),
                anyString(), anyString());
    }

    @Test
    void supervisoraPreguntaAlEmpleadoPorCuidadoFamiliar() {
        Excusa excusa = new Excusa(juan, Motivo.CUIDADO_FAMILIAR);

        recepcionista.revisarExcusa(excusa);

        assertTrue(excusa.estaAceptada());
        verify(emailSender).enviarEmail(
                org.mockito.ArgumentMatchers.eq("juan@mail.com"),
                org.mockito.ArgumentMatchers.eq("sofia@excusas.com"),
                anyString(), anyString());
    }

    @Test
    void gerenteNoEnviaMailNiGeneraProntuario() {
        Excusa excusa = new Excusa(juan, Motivo.IRRELEVANTE);

        recepcionista.revisarExcusa(excusa);

        assertTrue(excusa.estaAceptada());
        verify(emailSender, never()).enviarEmail(anyString(), anyString(), anyString(), anyString());
        assertEquals(0, AdministradorDeProntuarios.getInstance().getProntuarios().size());
    }

    @Test
    void flujoCompletoDeExcusaInverosimilGeneraProntuarioYNotificaADireccion() {
        ObserverDireccion miembro = mock(ObserverDireccion.class);
        AdministradorDeProntuarios.getInstance().suscribir(miembro);
        Excusa excusa = new Excusa(juan, Motivo.INVEROSIMIL);

        recepcionista.revisarExcusa(excusa);

        assertTrue(excusa.estaAceptada());
        assertEquals(1, AdministradorDeProntuarios.getInstance().getProntuarios().size());
        verify(miembro).actualizar(any(Prontuario.class));
    }

    @Test
    void excusaRechazadaNoGeneraProntuario() {
        gerente.setSiguiente(null);
        Excusa excusa = new Excusa(juan, Motivo.INVEROSIMIL);

        String respuesta = recepcionista.revisarExcusaConRespuesta(excusa);

        assertFalse(excusa.estaAceptada());
        assertEquals(Responsable.RECHAZO, respuesta);
        assertEquals(0, AdministradorDeProntuarios.getInstance().getProntuarios().size());
    }

    @Test
    void responsableVagoDerivaYElSiguienteLaProcesa() {
        recepcionista.setModo(new ModoVago());
        Excusa excusa = new Excusa(juan, Motivo.QUEDARSE_DORMIDO);

        String respuesta = recepcionista.revisarExcusaConRespuesta(excusa);

        // Nadie más en la cadena maneja "quedarse dormido": se rechaza
        assertEquals(Responsable.RECHAZO, respuesta);
        assertFalse(excusa.estaAceptada());
    }
}