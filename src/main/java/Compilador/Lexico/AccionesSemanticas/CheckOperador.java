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
        String buffer_aux=buffer.toString();
        if (buffer_aux.equals("")) {
            lexico.token = simbolo;
            this.buffer.append(simbolo);
        } else {
            switch (buffer_aux) {
                case "<":
                    if (simbolo == '=') {
                        lexico.token = Parser.MENOR_IGUAL;
                        this.buffer.append(simbolo);
                    } else {
                        lexico.token = '<';
                        lexico.indice--;
                    }
                    break;
                case ">":
                    if (simbolo == '=') {
                        lexico.token = Parser.MAYOR_IGUAL;
                        this.buffer.append(simbolo);
                    } else {
                        lexico.token = '>';
                        lexico.indice--;
                    }
                    break;
                case "!":
                    lexico.token = Parser.DISTINTO;
                    if (simbolo == '=')
                        this.buffer.append(simbolo);
                    else {
                        lexico.indice--;
                        lexico.error = true;
                        Logger.getInstance().addError(lexico.linea, "Se esperaba \"!=\" y se encontró \"!\"");
                    }
                    break;
                case "=":
                    if (simbolo == '=') {
                        lexico.token = Parser.COMP;
                        this.buffer.append(simbolo);
                    } else {
                        lexico.token = '=';
                        lexico.indice--;
                    }

                    break;

                case "/":
                    lexico.token = this.buffer.charAt(0);
                    lexico.indice--;
                default:

                    break;
            }
        }
        buffer_aux=buffer.toString();
        Logger.getInstance().addEvent(lexico.linea, "Se encontro el operador \"" + this.buffer+ "\"");
        this.buffer.setLength(0);
        lexico.yylval = null;
    }
}
