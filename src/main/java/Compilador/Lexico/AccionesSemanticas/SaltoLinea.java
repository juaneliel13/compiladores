package Compilador.Lexico.AccionesSemanticas;

import Compilador.Lexico.AnalizadorLexico;

public class SaltoLinea extends AccionSemantica {
    public SaltoLinea(AnalizadorLexico lexico) {
        super(lexico);
    }

    @Override
    public void accion(char simbolo) {
        if (simbolo == '\n')
            lexico.linea++;
    }
}
