package Compilador.CodigoAssembler;

import java.util.HashSet;
import java.util.Observable;
import java.util.Observer;
import java.util.Set;

public class RegistroCompuesto extends Registro implements Observer {

    Set<Registro> hijos;

    public RegistroCompuesto(String nombre, Registro padre) {
        super(nombre, padre);
        this.hijos = new HashSet<>();

    }

    @Override
    public boolean estaLibre() {
        for (Registro hijo : hijos) {
            if (!hijo.estaLibre())
                return false;
        }
        return true;
    }

    @Override
    public void ocupar() {

        for (Registro hijo : hijos) {
            hijo.ocupar();
        }
        this.notifyObservers();
    }

    @Override
    public void liberar() {
        for (Registro hijo : hijos) {
            hijo.liberar();
        }
        this.notifyObservers();

    }

    public void addHijos(Registro...registros){
        for(Registro r:registros)
            hijos.add(r);
    }

    @Override
    public void update(Observable o, Object arg) {
        for (Registro hijo : hijos) {
            if (!hijo.estaLibre()) {
                this.libre = false;
                return;
            }
        }
        this.libre = true;
    }
}
