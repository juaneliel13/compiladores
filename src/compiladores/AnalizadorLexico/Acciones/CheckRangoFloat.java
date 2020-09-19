package compiladores.AnalizadorLexico.Acciones;

import compiladores.AnalizadorLexico.AnalizadorLexico;

import java.util.HashMap;

public class CheckRangoFloat extends AccionSemantica {
    public CheckRangoFloat(AnalizadorLexico lexico) {
        super(lexico);
    }

    @Override
    public void accion(char simbolo) {
        //leer el valor y checkear rango
        float flotante;
        boolean error=false;
        if(buffer.contains("f")){
            buffer = buffer.replace('f','e');
        }
        try{
            flotante = Float.parseFloat(buffer);
        } catch (Exception e) {
            error=true;
            System.out.println(buffer);
            flotante= (float) 0.0;//error
        }

        if(flotante > 3.40282347e+38f || (flotante < 1.17549435e-38f && flotante != 0.0f) || error) {
            System.out.println("Error en la linea " + lexico.linea + ": Float fuera de rango");
            error = true;
            flotante = (float) 0.0; //error
        }
        if(flotante != 0.0f && !error)
            System.out.println("Se encontro el float " + buffer.replace('e','f'));
        if(!lexico.tablaDeSimbolos.containsKey(String.valueOf(flotante))) {
            HashMap<String, Object> aux = new HashMap<String, Object>();
            aux.put("Tipo","FLOAT");
            lexico.tablaDeSimbolos.put(String.valueOf(flotante),aux);
        }
        //leer el valor y checkear rango
        lexico.token = 12;
        lexico.indice--;
        buffer = "";
    }
}
