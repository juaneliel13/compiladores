package Compilador.CodigoIntermedio;

import Compilador.Lexico.Tipos;

public class MenorIgual extends Comparador {

    public MenorIgual(ConTipo izquierdo, ConTipo derecho) {
        super(izquierdo, derecho);
    }

    @Override
    protected String getSalto() {
        if (getTipo() == Tipos.INTEGER)
            return "JNLE";
        return "JA";
    }

}
