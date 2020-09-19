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
                lexico.token = simbolo;
        } else {
            switch (buffer) {
                case "<":
                    if (simbolo == '=') {
                        lexico.token = 20;
                    } else {
                        lexico.token = '<';
                        lexico.indice--;
                    }
                    break;
                case ">":
                    if (simbolo == '=') {
                        lexico.token = 19;
                    } else {
                        lexico.token = '>';
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
                        lexico.token = '=';
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
