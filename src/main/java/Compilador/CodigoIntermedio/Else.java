package Compilador.CodigoIntermedio;

public class Else extends Nodo {

    public Else(Nodo nodo) {
        super(nodo, null);
    }

    @Override
    public void generarCodigo() {
        izquierdo.generarCodigo();
    }
}
