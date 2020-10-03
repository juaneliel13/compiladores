package compiladores.AnalizadorLexico.Acciones;

import compiladores.AnalizadorLexico.AnalizadorLexico;
import compiladores.Parser;

import java.util.HashMap;

public class ErrorEntero extends AccionSemantica {
    public ErrorEntero(AnalizadorLexico lexico) {
        super(lexico);
    }

    @Override
    public void accion(char simbolo) {
        System.out.println("Error en la linea " + lexico.linea + ": Entero mal escrito");
        lexico.yylval = null;
        lexico.error = true;
        lexico.token = Parser.CTE_INT;
        lexico.indice--;
        buffer = "";
    }
}
