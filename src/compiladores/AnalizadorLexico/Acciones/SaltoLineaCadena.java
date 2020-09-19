package compiladores.AnalizadorLexico.Acciones;

import compiladores.AnalizadorLexico.AnalizadorLexico;

public class SaltoLineaCadena extends AccionSemantica {
    public SaltoLineaCadena(AnalizadorLexico lexico) {
        super(lexico);
    }

    @Override
    public void accion(char simbolo) {
        buffer = buffer + simbolo;
        if (simbolo == '\n')
            lexico.linea++;
    }
}
