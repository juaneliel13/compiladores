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
        String buffer_aux = buffer.toString();
        if (buffer_aux.length() > 20) {
            Logger.getInstance().addWarning(lexico.linea, "Indentificador \"" + buffer_aux + "\" truncado");
            buffer_aux = buffer_aux.substring(0, 20);
        }
        Logger.getInstance().addEvent(lexico.linea, "Se encontro el identificador \"" + buffer_aux + "\"");
        if (!lexico.tablaDeSimbolos.containsKey(buffer_aux+Parser.ambito)) {
            HashMap<String, Object> aux = new HashMap<String, Object>();
            aux.put("Uso", "IDENTIFICADOR");
            lexico.tablaDeSimbolos.put(buffer_aux, aux);
        }

        lexico.token = Parser.ID;
        lexico.yylval = buffer_aux;
        lexico.indice--;
        buffer.setLength(0);
    }
}
