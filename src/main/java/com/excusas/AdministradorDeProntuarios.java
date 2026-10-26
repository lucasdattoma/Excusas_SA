package com.excusas;

import java.util.ArrayList;
import java.util.List;

public class AdministradorDeProntuarios {

    private static AdministradorDeProntuarios instancia;

    private final List<Prontuario> prontuarios = new ArrayList<>();
    private final List<ObserverDireccion> observers = new ArrayList<>();

    private AdministradorDeProntuarios() {
    }

    public static AdministradorDeProntuarios getInstance() {
        if (instancia == null) {
            instancia = new AdministradorDeProntuarios();
        }
        return instancia;
    }

    public void registrar(Prontuario prontuario) {
        prontuarios.add(prontuario);
        notificar(prontuario);
    }

    public void suscribir(ObserverDireccion observer) {
        observers.add(observer);
    }

    public void desuscribir(ObserverDireccion observer) {
        observers.remove(observer);
    }

    private void notificar(Prontuario prontuario) {
        for (ObserverDireccion observer : observers) {
            observer.actualizar(prontuario);
        }
    }

    public List<Prontuario> getProntuarios() {
        return new ArrayList<>(prontuarios);
    }

    // Solo para tests: el Singleton comparte estado entre ejecuciones
    public void reiniciar() {
        prontuarios.clear();
        observers.clear();
    }
}