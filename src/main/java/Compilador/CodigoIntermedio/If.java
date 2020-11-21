package Compilador.CodigoIntermedio;

public class If extends Nodo {

    public If(Nodo izquierdo, Nodo derecho) {
        super(izquierdo, derecho);
    }

    @Override
    public void generarCodigo() {
        izquierdo.generarCodigo();
        derecho.generarCodigo();
    }
}
