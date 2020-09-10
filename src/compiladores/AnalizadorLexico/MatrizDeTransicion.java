package compiladores.AnalizadorLexico;

import compiladores.AnalizadorLexico.Acciones.AccionSemantica;

public class MatrizDeTransicion {

    private Tupla matriz[][]; //= new Tupla [17][26];

    public MatrizDeTransicion(int estados, int simbolos) {
        this.matriz = new Tupla[estados][simbolos];
    }

    public Integer convertir(char simbolo) {

        switch (simbolo) {

            case '0':
            case '1':
            case '2':
            case '3':
            case '4':
            case '5':
            case '6':
            case '7':
            case '8':
            case '9':
                return 0;

            case 'a':
            case 'b':
            case 'c':
            case 'd':
            case 'e':
            case 'g':
            case 'h':
            case 'j':
            case 'k':
            case 'l':
            case 'm':
            case 'n':
            case 'o':
            case 'p':
            case 'q':
            case 'r':
            case 's':
            case 't':
            case 'u':
            case 'v':
            case 'w':
            case 'x':
            case 'y':
            case 'z':
                return 1;

            case 'i':
                return 2;

            case 'f':
                return 3;

            case 'A':
            case 'B':
            case 'C':
            case 'D':
            case 'E':
            case 'F':
            case 'G':
            case 'H':
            case 'I':
            case 'J':
            case 'K':
            case 'L':
            case 'M':
            case 'N':
            case 'O':
            case 'P':
            case 'Q':
            case 'R':
            case 'S':
            case 'T':
            case 'U':
            case 'V':
            case 'W':
            case 'X':
            case 'Y':
            case 'Z':
                return 4;

            case '+':
                return 5;

            case '-':
                return 6;

            case '*':
                return 7;

            case '/':
                return 8;

            case '%':
                return 9;

            case '!':
                return 10;

            case '<':
                return 11;

            case '>':
                return 12;

            case '=':
                return 13;

            case '_':
                return 14;

            case '{':
                return 15;

            case '}':
                return 16;

            case '(':
                return 17;

            case ')':
                return 18;

            case '.':
                return 19;

            case ',':
                return 20;

            case ';':
                return 21;

            case '\n':
            case '\r':
                return 22;

            case '\t':
                return 23;

            case ' ':
                return 24;

            case '\'':
                return 25;

            default:
                return -1;
        }


    }

    public void agregarTransicion(int estado, int simbolo, int nuevoEstado, AccionSemantica as) {
        matriz[estado][simbolo] = new Tupla(nuevoEstado, as);
    }

    public void agregarTransicion(Integer estado, char simbolo, Integer nuevoEstado, AccionSemantica as) {
        matriz[estado][convertir(simbolo)] = new Tupla(nuevoEstado, as);
    }

    public Integer siguienteEstado(Integer estado, char simbolo) {
        return matriz[estado][convertir(simbolo)].getEstado();
    }

    public AccionSemantica accionSemantica(Integer estado, char simbolo) {
        return matriz[estado][convertir(simbolo)].getAS();
    }

}
