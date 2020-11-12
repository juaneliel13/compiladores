package Compilador.CodigoAssembler;

import java.util.Observable;
import java.util.Observer;
import java.util.Vector;

public class RegistroCompuesto extends Registro implements Observer {

    Vector<Registro> hijos;

    public RegistroCompuesto(String nombre, Registro padre) {
        super(nombre, padre);
        this.hijos = new Vector<>();

    }

    @Override
    public void ocupar() {
        for (Registro hijo : hijos) {
            hijo.ocupar();
        }
        this.setChanged();
        this.notifyObservers();
    }

    @Override
    public void liberar() {
        for (Registro hijo : hijos) {
            hijo.liberar();
        }
        this.setChanged();
        this.notifyObservers();
        if (this.nombre.length() == 3)
            AdministradorDeRegistros.add32bits(this);
        else
            AdministradorDeRegistros.add16bits(this);
    }

    public void addHijos(Registro...registros){
        for(Registro r:registros)
            hijos.add(r);
    }

    @Override
    public void update(Observable o, Object arg) {
        boolean nLibre = true;
        for (Registro hijo : hijos) {
            if (!hijo.estaLibre()) {
                nLibre = false;
                break;
            }
        }
        if(nLibre != this.libre) {
            this.libre = nLibre;
            this.setChanged();
            if(this.libre) {
                if (this.nombre.length() == 3)
                    AdministradorDeRegistros.add32bits(this);
                else
                    AdministradorDeRegistros.add16bits(this);
            } else {
                if (this.nombre.length() == 3)
                    AdministradorDeRegistros.rm32bits(this);
                else
                    AdministradorDeRegistros.rm16bits(this);
            }
            this.notifyObservers();
        }
    }
}
