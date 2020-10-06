package Compilador.Lexico.AccionesSemanticas;

import Compilador.Lexico.AnalizadorLexico;
import Compilador.Sintactico.Parser;

import java.util.HashMap;

public class CheckRangoInt extends AccionSemantica {
    public CheckRangoInt(AnalizadorLexico lexico) {
        super(lexico);
    }

    @Override
    public void accion(char simbolo) {
        int entero = (int) Math.pow(2, 15);
        boolean error = false;
        try {
            entero = Integer.parseInt(buffer.split("_")[0]);
        } catch (NumberFormatException e) {
            error = true;
        }
        if (error || entero > (int) Math.pow(2, 15)) {
            System.out.println("Error en la linea " + lexico.linea + ": Entero fuera de rango.");
            lexico.yylval = null;
            lexico.error = true;
        } else {
            System.out.println("Se encontro el entero " + buffer + "i" + ".");
            //Agregamos o actualizamos segun corresponda
            if (!lexico.tablaDeSimbolos.containsKey(String.valueOf(entero))) {
                HashMap<String, Object> aux = new HashMap<String, Object>();
                aux.put("Tipo", "INT");
                aux.put("Contador", 1);
                lexico.tablaDeSimbolos.put(String.valueOf(entero), aux);
            } else {
                int aux = (int) lexico.tablaDeSimbolos.get(String.valueOf(entero)).get("Contador");
                aux++;
                lexico.tablaDeSimbolos.get(String.valueOf(entero)).put("Contador", aux);
            }
            lexico.yylval = String.valueOf(entero);
        }
        lexico.token = Parser.CTE_INT;
        buffer = "";
    }
}
