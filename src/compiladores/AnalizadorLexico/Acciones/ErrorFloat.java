package compiladores.AnalizadorLexico.Acciones;

import compiladores.AnalizadorLexico.AnalizadorLexico;
import compiladores.Parser;

public class ErrorFloat extends AccionSemantica {
    public ErrorFloat(AnalizadorLexico lexico) {
        super(lexico);
    }

    @Override
    public void accion(char simbolo) {
        //error de float se da: estado 5 cuando no viene +/- o estado 6 si no viene digito
        System.out.println("Error: Constante de tipo Float mal escrita");
        lexico.token = Parser.CTE_FLOAT;
        lexico.error = true;
    }
}
