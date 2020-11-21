package Compilador.CodigoIntermedio;

import Compilador.Lexico.Tipos;

public class MayorIgual extends Comparador {

    public MayorIgual(ConTipo izquierdo, ConTipo derecho) {
        super(izquierdo, derecho);
    }

    @Override
    protected String getSalto() {
        if (getTipo() == Tipos.INTEGER)
            return "JNGE";
        return "JB";
    }
}
