package Compilador.Lexico.AccionesSemanticas;

import Compilador.Lexico.AnalizadorLexico;
import Compilador.Sintactico.Parser;
import Compilador.Utilidad.Logger;

import java.util.HashMap;

public class AltaIdentificador extends AccionSemantica {
    public AltaIdentificador(AnalizadorLexico lexico) {
        super(lexico);
    }

    @Override
    public void accion(char simbolo) {
        //se carga en algun lado
        if (buffer.length() > 20) {
            Logger.getInstance().addWarning(lexico.linea,"Indentificador \"" + buffer + "\" truncado");
            buffer = buffer.substring(0, 20);
        }
        Logger.getInstance().addEvent(lexico.linea,"Se encontro el identificador \"" + buffer + "\"");
        if (!lexico.tablaDeSimbolos.containsKey(buffer)) {
            HashMap<String, Object> aux = new HashMap<String, Object>();
            aux.put("Tipo", "IDENTIFICADOR");
            lexico.tablaDeSimbolos.put(buffer, aux);
        }

        lexico.token = Parser.ID;
        lexico.yylval = buffer;
        lexico.indice--;
        buffer = "";
    }
}
