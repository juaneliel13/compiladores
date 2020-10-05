package Compilador.Lexico.AccionesSemanticas;

import Compilador.Lexico.AnalizadorLexico;

public class ErrorSimboloNoReconocido extends AccionSemantica {
    public ErrorSimboloNoReconocido(AnalizadorLexico lexico) {
        super(lexico);
    }

    @Override
    public void accion(char simbolo) {
        System.out.println("Error: Se encontro un simbolo desconocido \'" + simbolo + "\'");
        lexico.error = true;
    }
}
