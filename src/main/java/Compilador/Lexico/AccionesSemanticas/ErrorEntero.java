package Compilador.Lexico.AccionesSemanticas;

import Compilador.Lexico.AnalizadorLexico;
import Compilador.Sintactico.Parser;

public class ErrorEntero extends AccionSemantica {
    public ErrorEntero(AnalizadorLexico lexico) {
        super(lexico);
    }

    @Override
    public void accion(char simbolo) {
        System.out.println("Error en la linea " + lexico.linea + ": Entero mal escrito.");
        lexico.yylval = null;
        lexico.error = true;
        lexico.token = Parser.CTE_INT;
        lexico.indice--;
        buffer = "";
    }
}
