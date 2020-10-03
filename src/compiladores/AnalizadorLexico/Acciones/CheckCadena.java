package compiladores.AnalizadorLexico.Acciones;

import compiladores.AnalizadorLexico.AnalizadorLexico;
import compiladores.Parser;

import java.util.HashMap;

public class CheckCadena extends AccionSemantica {

    public CheckCadena(AnalizadorLexico lexico) {
        super(lexico);
    }

    @Override
    public void accion(char simbolo) {
        //acomodar la wea
        buffer += "'";
        String cadenaSinSalto = buffer.replace("\r", "").replace("\n", "");

        if (!cadenaSinSalto.equals(buffer)) {
            buffer = cadenaSinSalto;
            System.out.println("Error en la linea " + lexico.linea + ": salto de linea en la cadena");
            lexico.error = true;
        } else {
            System.out.println("Se encontro la cadena " + buffer + " en la linea " + lexico.linea);
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
