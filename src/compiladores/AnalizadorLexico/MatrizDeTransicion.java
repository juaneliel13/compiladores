package compiladores.AnalizadorLexico;



public class MatrizDeTransicion {

    private Tupla matriz[][] = new Tupla [12][];


    public Integer convertir(char simbolo){

        switch (simbolo){

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
            case 'i':
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

            case 'f':
                return 2;

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
                return 3;

            case '+':
                return 4;

            case '-':
                return 5;

            case '*':
                return 6;

            case '/':
                return 7;

            case '%':
                return 8;

            case '!':
                return 9;

            case '<':
                return 10;

            case '>':
                return 11;

            case '=':
                return 12;

            case '_':
                return 13;

            case '{':
                return 14;

            case '}':
                return 15;

            case '(':
                return 16;

            case ')':
                return 17;

            case ',':
                return 18;

            case ';':
                return 19;

            case '\n':
                return 20;

            default:
                return -1;
        }


    }

}
