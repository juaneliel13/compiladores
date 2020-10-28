package Compilador.Lexico.AccionesSemanticas;

import Compilador.Lexico.AnalizadorLexico;
import Compilador.Utilidad.Logger;

public class CheckPalabraReservada extends AccionSemantica {
    public CheckPalabraReservada(AnalizadorLexico lexico) {
        super(lexico);
    }

    @Override
    public void accion(char simbolo) {
        String buffer_aux=buffer.toString();
        if (lexico.palabrasReservadas.containsKey(buffer_aux)) {
            Logger.getInstance().addEvent(lexico.linea, "Se encontro la palabra reservada " + buffer_aux);
            lexico.token = lexico.palabrasReservadas.get(buffer_aux);
        } else {
            Logger.getInstance().addError(lexico.linea, "Palabra reservada \"" + buffer_aux + "\" no encontrada o mal escrita");
            lexico.estado = 0;// Vuelve al estado inicial

        }
        lexico.indice--;
        buffer.setLength(0);
        lexico.yylval = null;
    }
}
