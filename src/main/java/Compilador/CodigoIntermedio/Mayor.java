package Compilador.CodigoIntermedio;

import Compilador.Lexico.Tipos;

public class Mayor extends Comparador {

    public Mayor(ConTipo izquierdo, ConTipo derecho) {
        super(izquierdo, derecho);
    }

    @Override
    protected String getSalto() {
        if (getTipo() == Tipos.INTEGER)
            return "JLE";
        return "JBE";
    }
}
