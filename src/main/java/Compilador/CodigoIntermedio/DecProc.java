package Compilador.CodigoIntermedio;

public class DecProc extends Nodo{

    String ref;
    public DecProc(Nodo izquierdo, Nodo derecho, String ref) {
        super(izquierdo, derecho);
        this.ref=ref;
    }

    @Override
    public void generarCodigo() {
    }
}
