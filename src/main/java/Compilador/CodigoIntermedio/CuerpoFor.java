package Compilador.CodigoIntermedio;

public class CuerpoFor extends Nodo {

    public CuerpoFor(Nodo izquierdo, Nodo derecho) {
        super(izquierdo, derecho);
    }

    @Override
    public void generarCodigo() {
        izquierdo.generarCodigo();
        derecho.generarCodigo();
    }
}
