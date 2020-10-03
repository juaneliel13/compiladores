package compiladores.AnalizadorLexico.Acciones;

import compiladores.AnalizadorLexico.AnalizadorLexico;
import compiladores.Parser;

public class CheckOperador extends AccionSemantica {
    public CheckOperador(AnalizadorLexico lexico) {
        super(lexico);
    }

    @Override
    public void accion(char simbolo) {
        //Si el buffer es "" estamos en estado 0 sino en alguno de los operadores de mas de un caracter
        if (buffer.equals("")) {
            lexico.token = simbolo;
        } else {
            switch (buffer) {
                case "<":
                    if (simbolo == '=') {
                        lexico.token = Parser.MENOR_IGUAL;
                    } else {
                        lexico.token = '<';
                        lexico.indice--;
                    }
                    break;
                case ">":
                    if (simbolo == '=') {
                        lexico.token = Parser.MAYOR_IGUAL;
                    } else {
                        lexico.token = '>';
                        lexico.indice--;
                    }
                    break;
                case "!":
                    if (simbolo == '=') {
                        lexico.token = Parser.DISTINTO;
                    } else {
                        //TODO: hay que incluir el tema del error
                    }
                    break;
                case "=":
                    if (simbolo == '=') {
                        lexico.token = Parser.COMP;
                    } else {
                        lexico.token = '=';
                        lexico.indice--;
                    }

                    break;

                case "/":
                        lexico.token=buffer.charAt(0);
                        lexico.indice--;
                default:

                    break;
            }
        }
        System.out.println("Se encontro el operador \"" + buffer + simbolo + "\" en la linea " + lexico.linea);
        buffer = "";
        //lexico.yylval.sval=null;
    }
}
