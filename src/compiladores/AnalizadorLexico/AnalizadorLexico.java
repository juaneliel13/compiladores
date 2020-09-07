package compiladores.AnalizadorLexico;

public class AnalizadorLexico {

    String fuente;
    static int token;
    MatrizDeTransicion matrizDeTransicion;
    private MatrizDeTransicion automata[][]= new MatrizDeTransicion[17][26];

    public AnalizadorLexico(String fuente){
        this.fuente = fuente;
        this.matrizDeTransicion = new MatrizDeTransicion();
    }

    public Integer getNextToken() {
        while(!matrizDeTransicion.finalizo()) {
            matrizDeTransicion.avanzar();
        }
        return token;
    }

}
