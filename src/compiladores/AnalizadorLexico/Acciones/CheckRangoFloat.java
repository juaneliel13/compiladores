package compiladores.AnalizadorLexico.Acciones;

import compiladores.AnalizadorLexico.AnalizadorLexico;

public class CheckRangoFloat extends AccionSemantica {
    public CheckRangoFloat(AnalizadorLexico lexico) {
        super(lexico);
    }

    @Override
    public void accion(char simbolo) {
        //leer el valor y checkear rango
        lexico.token = 12;
        lexico.indice--;
        buffer = "";
    }
}
