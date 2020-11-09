package Compilador.CodigoIntermedio;

public class Bloque extends Nodo {

    public Bloque(Nodo izquierdo, Nodo derecho) {
        super(izquierdo, derecho);
    }

    @Override
    public void generarCodigo() {
        if(izquierdo != null)
            izquierdo.generarCodigo();
        if(derecho != null)
            derecho.generarCodigo();
    }

}
