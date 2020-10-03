package compiladores.AnalizadorLexico.Acciones;

import compiladores.AnalizadorLexico.AnalizadorLexico;

public class ErrorSimboloNoReconocido extends AccionSemantica {
    public ErrorSimboloNoReconocido(AnalizadorLexico lexico) {
        super(lexico);
    }

    @Override
    public void accion(char simbolo) {

    }
}
