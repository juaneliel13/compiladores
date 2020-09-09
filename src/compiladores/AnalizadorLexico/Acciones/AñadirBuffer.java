package compiladores.AnalizadorLexico.Acciones;

import compiladores.AnalizadorLexico.AnalizadorLexico;

public class AñadirBuffer extends AccionSemantica {
    public AñadirBuffer(AnalizadorLexico lexico) {
        super(lexico);
    }

    @Override
    public void accion(char simbolo) {
        buffer = buffer + simbolo;
    }
}
