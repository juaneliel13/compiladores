package Compilador.Lexico.AccionesSemanticas;

import Compilador.Lexico.AnalizadorLexico;
import Compilador.Sintactico.Parser;
import Compilador.Utilidad.Logger;

import java.util.HashMap;

public class CheckRangoFloat extends AccionSemantica {
    public CheckRangoFloat(AnalizadorLexico lexico) {
        super(lexico);
    }

    @Override
    public void accion(char simbolo) {
        //leer el valor y checkear rango
        float flotante = 0.0f;
        boolean error = false;
        String buffer_aux = this.buffer.toString();
        if (buffer_aux.contains("f")) {
            buffer_aux = buffer_aux.replace('f', 'e');
        }
        try {
            flotante = Float.parseFloat(buffer_aux);
        } catch (Exception e) {
            error = true;
        }
        if (flotante > 3.40282347e+38f || (flotante < 1.17549435e-38f && flotante != 0.0f) || error) {
            //SE CHECKEA QUE NO SEA UN "." SOLO YA QUE EL AUTOMATA ADMITE EL "." SOLO
            if (!buffer_aux.equals(".")) {
                Logger.getInstance().addError(lexico.linea, "Float fuera de rango");

            } else {
                Logger.getInstance().addError(lexico.linea, "Simbolo inesperado '.'");
                lexico.estado = 0;
                lexico.indice--;
                return;
            }
            lexico.error = true;
            lexico.yylval = null;
        } else {
            Logger.getInstance().addEvent(lexico.linea, "Se encontro el float " + buffer_aux.replace('e', 'f'));
            if (!lexico.tablaDeSimbolos.containsKey(String.valueOf(flotante))) {
                HashMap<String, Object> aux = new HashMap<String, Object>();
                aux.put("Tipo", "FLOAT");
                aux.put("Contador", 1);
                lexico.tablaDeSimbolos.put(String.valueOf(flotante), aux);
            } else {
                int aux = (int) lexico.tablaDeSimbolos.get(String.valueOf(flotante)).get("Contador");
                aux++;
                lexico.tablaDeSimbolos.get(String.valueOf(flotante)).put("Contador", aux);
            }
            lexico.yylval = String.valueOf(flotante);
        }
        lexico.token = Parser.CTE_FLOAT;
        lexico.indice--;
        this.buffer.setLength(0);
    }
}
