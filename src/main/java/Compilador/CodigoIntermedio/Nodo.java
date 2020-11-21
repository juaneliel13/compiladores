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

    /**
     * Retorna si el nodo es Hoja
     * @return boolean
     */
    public boolean esHoja() {
        return ((izquierdo == null) && (derecho == null));
    }

    /**
     * Genera el codigo almacenandolo en el StringBuilder codigo
     */
    public abstract void generarCodigo();

    /**
     * Genera un String con la estructura representada en forma de arbol de directorio
     * @return String con la estructura
     */
    public String imprimir() {
        return imprimirRecursivo(this, "");
    }

    /**
     * Metodo auxiliar para imprimir de manera recursiva los nodos
     * @param nodo es el nodo que se quiere imprimir
     * @param tabs son la identacion que se tenia de antes (como profundidad)
     * @return
     */
    private String imprimirRecursivo(Nodo nodo, String tabs) {
        String el_retorno = "";
        el_retorno += tabs + nodo.toString() + '\n';
        if (nodo.izquierdo != null)
            el_retorno += imprimirRecursivo(nodo.izquierdo, tabs + '\t');
        if (nodo.derecho != null)
            el_retorno += imprimirRecursivo(nodo.derecho, tabs + '\t');
        return el_retorno;
    }

    /**
     * Crea una nueva etiqueta
     * @return la etiqueta
     */
    public static String crearEtiqueta() {
        return "etiqueta_" + (cont_et++);
    }

    /**
     * Crea y agrega a la tabla de simbolos una nueva variable auxiliar
     * @return la referencia de la variable auxiliar
     */
    public static String crearAuxiliar() {
        String aux="@aux_" + (cont_aux++);
        HashMap<String,Object> map_aux=new HashMap<>();
        map_aux.put("Uso","auxiliar");
        map_aux.put("Tipo", Tipos.FLOAT);
        lex.tablaDeSimbolos.put(aux,map_aux);
        return aux;
    }

    /**
     * Desapila una etiqueta
     * @return la etiqueta que estaba en el tope
     */
    public static String desapilar() {
        return etiquetas.pop();
    }

    /**
     * Apila la etiqueta.
     * @param etiqueta
     */
    public static void apilar(String etiqueta) {
        etiquetas.push(etiqueta);
    }

    public String toString() {
        return this.getClass().getSimpleName();
    }
}