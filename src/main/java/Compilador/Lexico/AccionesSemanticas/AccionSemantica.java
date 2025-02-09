package Compilador.Lexico.AccionesSemanticas;

import Compilador.Lexico.AnalizadorLexico;

public abstract class AccionSemantica {
    protected static StringBuilder buffer=new StringBuilder();;
    protected AnalizadorLexico lexico;

    public AccionSemantica(AnalizadorLexico lexico) {
        this.lexico = lexico;
    }

    public abstract void accion(char simbolo);
}
