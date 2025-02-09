package Compilador.Lexico.AccionesSemanticas;

import Compilador.Lexico.AnalizadorLexico;
import Compilador.Lexico.Tipos;
import Compilador.Sintactico.Parser;
import Compilador.Utilidad.Logger;

import java.util.HashMap;

public class CheckRangoEntero extends AccionSemantica {
    public CheckRangoEntero(AnalizadorLexico lexico) {
        super(lexico);
    }

    @Override
    public void accion(char simbolo) {
        String buffer_aux = this.buffer.toString();
        int entero = (int) Math.pow(2, 15);
        boolean error = false;
        try {
            entero = Integer.parseInt(buffer_aux.split("_")[0]);
        } catch (NumberFormatException e) {
            error = true;
        }
        if (error || entero > (int) Math.pow(2, 15)) {
            Logger.getInstance().addError(lexico.linea, "Entero fuera de rango");
            lexico.yylval = null;
            lexico.error = true;
        } else {
            Logger.getInstance().addEvent(lexico.linea, "Se encontro el entero " + buffer_aux + "i");
            //Agregamos o actualizamos segun corresponda
            if (!lexico.tablaDeSimbolos.containsKey(String.valueOf(entero))) {
                HashMap<String, Object> aux = new HashMap<String, Object>();
                aux.put("Tipo", Tipos.INTEGER);
                aux.put("Contador", 1);
                aux.put("NI",0);
                lexico.tablaDeSimbolos.put(String.valueOf(entero), aux);
            } else {
                int aux = (int) lexico.tablaDeSimbolos.get(String.valueOf(entero)).get("Contador");
                aux++;
                lexico.tablaDeSimbolos.get(String.valueOf(entero)).put("Contador", aux);
            }
            lexico.yylval = String.valueOf(entero);
        }
        lexico.token = Parser.CTE_INT;
        this.buffer.setLength(0);
    }
}
