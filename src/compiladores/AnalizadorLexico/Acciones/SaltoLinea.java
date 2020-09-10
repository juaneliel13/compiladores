package compiladores.AnalizadorLexico.Acciones;

import compiladores.AnalizadorLexico.AnalizadorLexico;

public class SaltoLinea extends AccionSemantica {
    public SaltoLinea(AnalizadorLexico lexico) {
        super(lexico);
    }

    @Override
    public void accion(char simbolo) {
        if (simbolo == '\n')
            lexico.linea++;
    }
}
