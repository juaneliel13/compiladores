package compiladores.AnalizadorLexico.Acciones;

import compiladores.AnalizadorLexico.AnalizadorLexico;

public class CheckPalabraReservada extends AccionSemantica {

    public CheckPalabraReservada(AnalizadorLexico lexico) {
        super(lexico);
    }

    @Override
    public void accion(char simbolo) {

        if (lexico.palabrasReservadas.containsKey(buffer)) {
            System.out.println("Se encontro la palabra reservada " + buffer + " en la linea " + lexico.linea);
            lexico.token = lexico.palabrasReservadas.get(buffer);
        } else {
            System.out.println("Error en la linea " +lexico.linea +": Palabra reservada \"" + buffer + "\" no encontrada o mal escrita");
            lexico.token = 900;//Palabra reservada no encontrada
        }
        lexico.indice--;
        buffer = "";
        lexico.yylval=null;
    }

}
