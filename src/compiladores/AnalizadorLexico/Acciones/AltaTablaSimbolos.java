package compiladores.AnalizadorLexico.Acciones;

import compiladores.AnalizadorLexico.AnalizadorLexico;
import compiladores.Parser;

import java.util.HashMap;

public class AltaTablaSimbolos extends AccionSemantica {
    public AltaTablaSimbolos(AnalizadorLexico lexico) {
        super(lexico);
    }

    @Override
    public void accion(char simbolo) {
        //se carga en algun lado
        if (buffer.length() > 20) {
            System.out.println("Warning en la linea " + lexico.linea + ": indentificador \"" + buffer + "\" truncado");
            buffer = buffer.substring(0, 20);
        }
        System.out.println("Se encontro el identificador \"" + buffer + "\" en la linea " + lexico.linea);
        if (!lexico.tablaDeSimbolos.containsKey(buffer)) {
            HashMap<String, Object> aux = new HashMap<String, Object>();
            aux.put("Tipo", "IDENTIFICADOR");
            lexico.tablaDeSimbolos.put(buffer, aux);
        }

        lexico.token = Parser.ID;
        lexico.yylval.sval = buffer;
        lexico.indice--;
        buffer = "";
    }
}
