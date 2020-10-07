package Compilador.Lexico.AccionesSemanticas;

import Compilador.Lexico.AnalizadorLexico;
import Compilador.Utilidad.Logger;

public class CheckPalabraReservada extends AccionSemantica {
    public CheckPalabraReservada(AnalizadorLexico lexico) {
        super(lexico);
    }

    @Override
    public void accion(char simbolo) {

        if (lexico.palabrasReservadas.containsKey(buffer)) {
            Logger.getInstance().addEvent(lexico.linea,"Se encontro la palabra reservada " + buffer);
            lexico.token = lexico.palabrasReservadas.get(buffer);
        } else {
            Logger.getInstance().addError(lexico.linea,"Palabra reservada \"" + buffer + "\" no encontrada o mal escrita");
            lexico.token = 900;//Palabra reservada no encontrada

        }
        lexico.indice--;
        buffer = "";
        lexico.yylval = null;
    }
}
