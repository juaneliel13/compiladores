package Compilador.CodigoIntermedio;

import Compilador.Lexico.AnalizadorLexico;

import java.util.Stack;

public abstract class Nodo {

    public static StringBuilder codigo = new StringBuilder(200);

    public static Stack<String> etiquetas = new Stack<>();

    public static int cont_et=0;

    public Nodo izquierdo = null, derecho = null;

    public static AnalizadorLexico lex;

    public Nodo(Nodo izquierdo, Nodo derecho) {
        this.izquierdo = izquierdo;
        this.derecho = derecho;
    }

    public static void setLexico(AnalizadorLexico lex) {
        Nodo.lex = lex;
    }

    public Nodo() {

    }

    public boolean esHoja() {
        return ((izquierdo == null) && (derecho == null));
    }

    public abstract void generarCodigo();

    public String imprimision() {
        return auxiliarito(this, "");
    }

    private String auxiliarito(Nodo nodo, String tabs) {
        String el_retorno = "";
        el_retorno += tabs + nodo.toString() + '\n';
        if (nodo.izquierdo != null)
            el_retorno += auxiliarito(nodo.izquierdo, tabs + '\t');
        /*else
            el_retorno += tabs + "\tnull\n";*/
        if (nodo.derecho != null)
            el_retorno += auxiliarito(nodo.derecho, tabs + '\t');
        /*else
            el_retorno += tabs + "\tnull\n";*/
        return el_retorno;
    }

    public static String crearEtiqueta(){
        etiquetas.push("etiqueta_"+cont_et++);
        return etiquetas.peek();
    }

    public static String getEtiqueta(){
        return etiquetas.pop();
    }

    public String toString() {
        return this.getClass().getSimpleName();
    }
}