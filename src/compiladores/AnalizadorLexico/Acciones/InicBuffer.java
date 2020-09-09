package compiladores.AnalizadorLexico.Acciones;

import compiladores.AnalizadorLexico.AnalizadorLexico;

public class InicBuffer extends AccionSemantica {

    public InicBuffer(AnalizadorLexico lexico) {
        super(lexico);
    }

    @Override
    public void accion(char simbolo) {
        buffer = "" + simbolo;
    }

}
