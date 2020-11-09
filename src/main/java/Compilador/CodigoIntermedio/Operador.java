package Compilador.CodigoIntermedio;

public abstract class Operador extends ConTipo {


    public Operador(Nodo izquierdo, Nodo derecho) {
        super(izquierdo, derecho);
    }

    @Override
    public void generarCodigo() {
    }

    public abstract void updateTipo();

}
