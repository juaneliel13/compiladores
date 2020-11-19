package Compilador.Lexico.AccionesSemanticas;

import Compilador.Lexico.AnalizadorLexico;
import Compilador.Sintactico.Parser;
import Compilador.Utilidad.Logger;

public class ErrorFloat extends AccionSemantica {
    public ErrorFloat(AnalizadorLexico lexico) {
        super(lexico);
    }

    @Override
    public void accion(char simbolo) {
        //error de float se da: estado 5 cuando no viene +/- o estado 6 si no viene digito
        Logger.getInstance().addError(lexico.linea, "Flotante mal escrito");
        lexico.token = Parser.CTE_FLOAT;
        lexico.error = true;
        lexico.indice--;
        buffer.setLength(0);
    }
}
