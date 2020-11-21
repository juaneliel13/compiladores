package Compilador.CodigoIntermedio;

import Compilador.Lexico.Tipos;

public class Menor extends Comparador {

    public Menor(ConTipo izquierdo, ConTipo derecho) {
        super(izquierdo, derecho);

    }

    @Override
    protected String getSalto() {
        if (getTipo() == Tipos.INTEGER)
            return "JGE";
        return "JAE";
    }
}
