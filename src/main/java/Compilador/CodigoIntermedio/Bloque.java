package Compilador.CodigoIntermedio;

import com.sun.org.apache.bcel.internal.generic.RETURN;

public class Bloque extends Nodo {

    public Bloque(Nodo izquierdo, Nodo derecho) {
        super(izquierdo, derecho);
    }

    @Override
    public void generarCodigo() {
        return;
    }

}
