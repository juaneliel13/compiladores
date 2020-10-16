package Compilador.CodigoIntermedio;

public abstract class Nodo {

    public String tipo;
    public Nodo izquierdo, derecho;

    public Nodo(String tipo, Nodo izquierdo, Nodo derecho) {
        this.tipo = tipo;
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