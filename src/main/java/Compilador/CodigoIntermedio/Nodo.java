package Compilador.CodigoIntermedio;

import Compilador.Lexico.AnalizadorLexico;
import Compilador.Lexico.Tipos;

import java.util.HashMap;
import java.util.Stack;

public abstract class Nodo {

    public static StringBuilder codigo = new StringBuilder(200);

    public static Stack<String> etiquetas = new Stack<>();

    public static int cont_et = 0;

    public static int cont_aux = 0;

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

    public static String crearEtiqueta() {
        return "etiqueta_" + (cont_et++);
    }

    public static String crearAuxiliar() {
        String aux="@aux_" + (cont_aux++);
        HashMap<String,Object> map_aux=new HashMap<>();
        map_aux.put("Uso","auxiliar");
        map_aux.put("Tipo", Tipos.FLOAT);
        lex.tablaDeSimbolos.put(aux,map_aux);
        return aux;
    }

    public static String desapilar() {
        return etiquetas.pop();
    }

    public static void apilar(String etiqueta) {
        etiquetas.push(etiqueta);
    }

    public String toString() {
        return this.getClass().getSimpleName();
    }
}