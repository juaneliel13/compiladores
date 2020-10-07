package Compilador.Lexico.AccionesSemanticas;

import Compilador.Lexico.AnalizadorLexico;
import Compilador.Sintactico.Parser;
import Compilador.Utilidad.Logger;

public class CheckOperador extends AccionSemantica {
    public CheckOperador(AnalizadorLexico lexico) {
        super(lexico);
    }

    @Override
    public void accion(char simbolo) {
        //Si el buffer es "" estamos en estado 0 sino en alguno de los operadores de mas de un caracter
        if (buffer.equals("")) {
            lexico.token = simbolo;
            buffer += simbolo;
        } else {
            switch (buffer) {
                case "<":
                    if (simbolo == '=') {
                        lexico.token = Parser.MENOR_IGUAL;
                        buffer += simbolo;
                    } else {
                        lexico.token = '<';
                        lexico.indice--;
                    }
                    break;
                case ">":
                    if (simbolo == '=') {
                        lexico.token = Parser.MAYOR_IGUAL;
                        buffer += simbolo;
                    } else {
                        lexico.token = '>';
                        lexico.indice--;
                    }
                    break;
                case "!":
                    lexico.token = Parser.DISTINTO;
                    if (simbolo == '=')
                        buffer += simbolo;
                    else {
                        lexico.indice--;
                        lexico.error = true;
                        Logger.getInstance().addError(lexico.linea,"Se esperaba \"!=\" y se encontró \"!\"");
                    }
                    break;
                case "=":
                    if (simbolo == '=') {
                        lexico.token = Parser.COMP;
                        buffer += simbolo;
                    } else {
                        lexico.token = '=';
                        lexico.indice--;
                    }

                    break;

                case "/":
                    lexico.token = buffer.charAt(0);
                    lexico.indice--;
                default:

                    break;
            }
        }
        Logger.getInstance().addEvent(lexico.linea,"Se encontro el operador \"" + buffer + "\"");
        buffer = "";
        lexico.yylval = null;
    }
}
