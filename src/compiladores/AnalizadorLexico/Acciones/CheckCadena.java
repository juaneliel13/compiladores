package compiladores.AnalizadorLexico.Acciones;

import compiladores.AnalizadorLexico.AnalizadorLexico;

public class CheckCadena extends AccionSemantica {
    public CheckCadena(AnalizadorLexico lexico) {
        super(lexico);
    }

    @Override
    public void accion(char simbolo) {
        //acomodar la wea
        lexico.token = 13;
        buffer = "";
    }
}
