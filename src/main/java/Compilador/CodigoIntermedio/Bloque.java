package Compilador.CodigoIntermedio;

public class Bloque extends Nodo {

    public Bloque(Nodo izquierdo, Nodo derecho) {
        super(izquierdo, derecho);
    }

    @Override
    public void generarCodigo() {
        return;
    }

}
