package Compilador.CodigoIntermedio;

public class Bloque extends Nodo {

    public Bloque(Nodo izquierdo, Nodo derecho) {
        super(izquierdo, derecho);
    }

    @Override
    public String generarCodigo() {
        if (derecho != null)
            return izquierdo.generarCodigo() + derecho + generarCodigo();
        return izquierdo.generarCodigo();
    }

}
