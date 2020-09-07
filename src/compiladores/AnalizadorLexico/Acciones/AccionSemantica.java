package compiladores.AnalizadorLexico.Acciones;

import compiladores.AnalizadorLexico.AnalizadorLexico;

public abstract class AccionSemantica {
    protected static String buffer;
    protected AnalizadorLexico lexico;

    public AccionSemantica(AnalizadorLexico lexico) {
        this.lexico = lexico;
    }

    public abstract void accion();
}
