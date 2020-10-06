package Compilador.Lexico.AccionesSemanticas;

import Compilador.Lexico.AnalizadorLexico;
import Compilador.Sintactico.Parser;

import java.util.HashMap;

public class CheckCadena extends AccionSemantica {
    public CheckCadena(AnalizadorLexico lexico) {
        super(lexico);
    }

    @Override
    public void accion(char simbolo) {
        //acomodar la wea
        buffer += "'";
        if (simbolo == '\r' || simbolo == '\n') {
            if (simbolo == '\r')
                lexico.indice++;
            System.out.println("Error en la linea " + lexico.linea + ": salto de linea en la cadena.");
            lexico.error = true;
        } else {
            System.out.println("Se encontro la cadena " + buffer + " en la linea " + lexico.linea + ".");
        }

        if (!lexico.tablaDeSimbolos.containsKey(buffer)) {
            HashMap<String, Object> aux = new HashMap<String, Object>();
            aux.put("Tipo", "STRING");
            lexico.tablaDeSimbolos.put(buffer, aux);
        }
        lexico.yylval = buffer;
        lexico.token = Parser.CADENA;
        buffer = "";
    }
}
