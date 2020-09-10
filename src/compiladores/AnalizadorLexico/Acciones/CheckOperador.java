package compiladores.AnalizadorLexico.Acciones;

import compiladores.AnalizadorLexico.AnalizadorLexico;

public class CheckOperador extends AccionSemantica {
    public CheckOperador(AnalizadorLexico lexico) {
        super(lexico);
    }

    @Override
    public void accion(char simbolo) {
        //Si el buffer es "" estamos en estado 0 sino en alguno de los operadores de mas de un caracter
        if (buffer.equals("")) {
            switch (simbolo) {
                case '+':
                    lexico.token = 14;
                    break;
                case '-':
                    lexico.token = 15;
                    break;
                case '*':
                    lexico.token = 16;
                    break;
                case '/':
                    lexico.token = 17;
                    break;
                case '{':
                    lexico.token = 25;
                    break;
                case '}':
                    lexico.token = 26;
                    break;
                case '(':
                    lexico.token = 27;
                    break;
                case ')':
                    lexico.token = 28;
                    break;
                case ',':
                    lexico.token = 29;
                    break;
                case ';':
                    lexico.token = 30;
                    break;
                default:
                    break;
            }
        } else {
            switch (buffer) {
                case "<":
                    if (simbolo == '=') {
                        lexico.token = 20;
                    } else {
                        lexico.token = 22;
                        lexico.indice--;
                    }
                    break;
                case ">":
                    if (simbolo == '=') {
                        lexico.token = 19;
                    } else {
                        lexico.token = 21;
                        lexico.indice--;
                    }
                    break;
                case "!":
                    if (simbolo == '=') {
                        lexico.token = 24;
                    } else {
                        //error xd
                    }
                    break;
                case "=":
                    if (simbolo == '=') {
                        lexico.token = 23;
                    } else {
                        lexico.token = 18;
                        lexico.indice--;
                    }
                    break;
                default:

                    break;
            }
        }
        buffer = "";
    }
}
