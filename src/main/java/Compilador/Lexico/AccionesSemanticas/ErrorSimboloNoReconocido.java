package Compilador.Lexico.AccionesSemanticas;

import Compilador.Lexico.AnalizadorLexico;
import Compilador.Utilidad.Logger;

public class ErrorSimboloNoReconocido extends AccionSemantica {
    public ErrorSimboloNoReconocido(AnalizadorLexico lexico) {
        super(lexico);
    }

    @Override
    public void accion(char simbolo) {
        Logger.getInstance().addError(lexico.linea, "Se encontro un simbolo desconocido '" + simbolo + "'");
        lexico.error = true;
    }
}
