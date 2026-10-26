package com.excusas;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.never;
import static org.mockito.ArgumentMatchers.any;

class ProntuariosTest {

    private EmailSender emailSender;
    private Empleado juan;
    private CEO ceo;

    @BeforeEach
    void setUp() {
        AdministradorDeProntuarios.getInstance().reiniciar();
        emailSender = mock(EmailSender.class);
        juan = new Empleado("Juan", "juan@mail.com", 100);
        ceo = new CEO("Carla", "carla@excusas.com", 4, emailSender);
    }

    @Test
    void getInstanceSiempreDevuelveLaMismaInstancia() {
        assertSame(AdministradorDeProntuarios.getInstance(),
                AdministradorDeProntuarios.getInstance());
    }

    @Test
    void cuandoElCeoAceptaUnaExcusaSeGuardaUnProntuario() {
        ceo.revisarExcusa(new Excusa(juan, Motivo.INVEROSIMIL));

        assertEquals(1, AdministradorDeProntuarios.getInstance().getProntuarios().size());
        Prontuario p = AdministradorDeProntuarios.getInstance().getProntuarios().get(0);
        assertEquals(100, p.getLegajo());
    }

    @Test
    void elEquipoDeDireccionSeEntieraDelNuevoProntuario() {
        ObserverDireccion miembro = mock(ObserverDireccion.class);
        AdministradorDeProntuarios.getInstance().suscribir(miembro);

        ceo.revisarExcusa(new Excusa(juan, Motivo.INVEROSIMIL));

        verify(miembro).actualizar(any(Prontuario.class));
    }

    @Test
    void unMiembroDesuscriptoNoRecibeAvisos() {
        ObserverDireccion miembro = mock(ObserverDireccion.class);
        AdministradorDeProntuarios.getInstance().suscribir(miembro);
        AdministradorDeProntuarios.getInstance().desuscribir(miembro);

        ceo.revisarExcusa(new Excusa(juan, Motivo.INVEROSIMIL));

        verify(miembro, never()).actualizar(any(Prontuario.class));
    }
}