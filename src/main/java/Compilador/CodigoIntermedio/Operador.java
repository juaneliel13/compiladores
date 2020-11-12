package Compilador.CodigoIntermedio;

public abstract class Operador extends ConTipo {


    public Operador(Nodo izquierdo, Nodo derecho) {
        super(izquierdo, derecho);
    }


    public abstract void updateTipo();

}
