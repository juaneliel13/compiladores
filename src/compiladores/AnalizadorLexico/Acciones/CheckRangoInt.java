package compiladores.AnalizadorLexico.Acciones;

import compiladores.AnalizadorLexico.AnalizadorLexico;

public class CheckRangoInt extends AccionSemantica {
    public CheckRangoInt(AnalizadorLexico lexico) {
        super(lexico);
    }

    @Override
    public void accion(char simbolo) {
        //leer el valor y checkear rango
        lexico.token = 11;
        buffer = "";
    }
}
