package Compilador.CodigoAssembler;

import java.util.Observable;
import java.util.Observer;

public abstract class Registro extends Observable {

    String nombre;
    Registro padre;
    boolean libre;

    public Registro(String nombre,Registro padre) {
        this.nombre = nombre;
        this.padre = padre;
        this.libre = true;
        if(padre!=null)
            this.addObserver((Observer) padre);
    }

    public abstract boolean estaLibre();

    public abstract void ocupar();

    public abstract void liberar();


    @Override
    public String toString() {
        return nombre;
    }


}
