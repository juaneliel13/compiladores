package Compilador.Lexico.AccionesSemanticas;

import Compilador.Lexico.AnalizadorLexico;

public class AñadirBuffer extends AccionSemantica {
    public AñadirBuffer(AnalizadorLexico lexico) {
        super(lexico);
    }

    @Override
    public void accion(char simbolo) {
        buffer = buffer + simbolo;
    }
}
