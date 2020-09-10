package compiladores.AnalizadorLexico.Acciones;

import compiladores.AnalizadorLexico.AnalizadorLexico;

public class AltaTablaSimbolos extends AccionSemantica {
    public AltaTablaSimbolos(AnalizadorLexico lexico) {
        super(lexico);
    }

    @Override
    public void accion(char simbolo) {
        //se carga en algun lado
        lexico.token = 10;
        lexico.indice--;
        buffer = "";
    }
}
