package Compilador.CodigoIntermedio;

public abstract class Nodo {

    public Nodo izquierdo=null, derecho=null;

    public Nodo(Nodo izquierdo, Nodo derecho) {
        this.izquierdo = izquierdo;
        this.derecho = derecho;
    }

    public Nodo() {

    }

    public boolean esHoja() {
        return ((izquierdo == null) && (derecho == null));
    }


    public abstract String generarCodigo();

}