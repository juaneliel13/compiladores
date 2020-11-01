package Compilador.CodigoIntermedio;

public abstract class Operador extends ConTipo {


    public Operador(Nodo izquierdo, Nodo derecho) {
        super(izquierdo, derecho);
    }

    @Override
    public String generarCodigo() {
        return null;
    }

    public abstract void updateTipo();
    public abstract String metodoAbstracto();
}
