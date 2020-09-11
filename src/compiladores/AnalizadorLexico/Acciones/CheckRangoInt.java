package compiladores.AnalizadorLexico.Acciones;

import compiladores.AnalizadorLexico.AnalizadorLexico;

import javax.naming.spi.ObjectFactoryBuilder;
import java.util.HashMap;

public class CheckRangoInt extends AccionSemantica {
    public CheckRangoInt(AnalizadorLexico lexico) {
        super(lexico);
    }

    @Override
    public void accion(char simbolo) {
        int entero;
        boolean error=false;
        try {
            entero = Integer.parseInt(buffer.split("_")[0]);
        } catch (NumberFormatException e) {
            error=true;
            entero=(int)Math.pow(2,15)-1;//maximo permitido
        }
        if (!(entero>= Math.pow(-2,15) && entero<=(Math.pow(2,15)-1)) || error) {
            System.out.println("Error en la linea " +lexico.linea +": Entero fuera de rango");
            entero = (int) Math.pow(2, 15) - 1; //maximo permitido
        }
        //rango entre -2^15 y 2^15-1

        if(!lexico.tablaDeSimbolos.containsKey(String.valueOf(entero))) {
            HashMap<String, Object> aux = new HashMap<String, Object>();
            aux.put("Tipo","ENTERO");
            lexico.tablaDeSimbolos.put(String.valueOf(entero),aux);
        }
        //leer el valor y checkear rango
        lexico.token = 11;
        buffer = "";
    }
}
