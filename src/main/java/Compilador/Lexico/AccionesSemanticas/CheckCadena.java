package Compilador.Lexico.AccionesSemanticas;

import Compilador.Lexico.AnalizadorLexico;
import Compilador.Sintactico.Parser;
import Compilador.Utilidad.Logger;

import java.util.HashMap;

public class CheckCadena extends AccionSemantica {
    public CheckCadena(AnalizadorLexico lexico) {
        super(lexico);
    }

    @Override
    public void accion(char simbolo) {

        this.buffer.append("'");
        String buffer_aux = this.buffer.toString();
        if (simbolo == '\r' || simbolo == '\n') {
            if (simbolo == '\r')
                lexico.indice++;
            Logger.getInstance().addError(lexico.linea, "Salto de linea en la cadena");
            lexico.error = true;
        } else {
            Logger.getInstance().addEvent(lexico.linea, "Se encontro la cadena " + buffer_aux);
        }

        if (!lexico.tablaDeSimbolos.containsKey(buffer_aux)) {
            HashMap<String, Object> aux = new HashMap<String, Object>();
            aux.put("Tipo", "STRING");
            lexico.tablaDeSimbolos.put(buffer_aux, aux);
        }
        lexico.yylval = buffer_aux;
        lexico.token = Parser.CADENA;
        this.buffer.setLength(0);
    }
}
