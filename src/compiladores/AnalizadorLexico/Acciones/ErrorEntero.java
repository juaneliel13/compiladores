package compiladores.AnalizadorLexico.Acciones;

import compiladores.AnalizadorLexico.AnalizadorLexico;
import compiladores.Parser;

import java.util.HashMap;

public class ErrorEntero extends AccionSemantica{
    public ErrorEntero(AnalizadorLexico lexico) {
        super(lexico);
    }

    @Override
    public void accion(char simbolo) {
        int entero;
        boolean error = false;
        System.out.println("Error en la linea " + lexico.linea + ": Entero mal escrito");
        entero = Integer.parseInt(buffer);

        if (!(entero <= Math.pow(2, 15)) || error) {
            System.out.println("Error en la linea " + lexico.linea + ": Entero fuera de rango");
            entero = (int) Math.pow(2, 15) - 1; //maximo permitido
        }
        if (entero != (int) Math.pow(2, 15) - 1 && !error)
            System.out.println("Se encontro el entero " + buffer + "_i");
        if (!lexico.tablaDeSimbolos.containsKey(String.valueOf(entero))) {
            HashMap<String, Object> aux = new HashMap<String, Object>();
            aux.put("Tipo", "INT");
            aux.put("Contador", 1);
            lexico.tablaDeSimbolos.put(String.valueOf(entero), aux);
        } else {
            int aux = (int) lexico.tablaDeSimbolos.get(String.valueOf(entero)).get("Contador");
            aux++;
            lexico.tablaDeSimbolos.get(String.valueOf(entero)).put("Contador",aux);

        }
        //leer el valor y checkear rango
        lexico.yylval= String.valueOf(entero);
        lexico.token = Parser.CTE_INT;
        lexico.indice--;
        buffer = "";
    }
}
