package Compilador.CodigoIntermedio;

public abstract class Nodo {
    public Nodo izquierdo,derecho;

    public boolean esHoja(){
        return ((izquierdo==null)&&(derecho==null));
    }

    public abstract String generarCodigo();

}