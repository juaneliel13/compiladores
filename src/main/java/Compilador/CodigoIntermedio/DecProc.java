package Compilador.CodigoIntermedio;

import Compilador.Lexico.Tipos;

import java.util.ArrayList;
import java.util.HashMap;

public class DecProc extends Nodo{

    public static StringBuilder procs = new StringBuilder();



    String ref;
    public DecProc(Nodo izquierdo, Nodo derecho, String ref) {
        super(izquierdo, derecho);
        this.ref=ref;
    }

    @Override
    public void generarCodigo() {
        StringBuilder aux = Nodo.codigo;
        HashMap<String, Object> h_aux = new HashMap<String, Object>();
        h_aux.put("Uso", "variable");
        h_aux.put("Tipo",Tipos.INTEGER);
        h_aux.put("Inic","0");
        lex.tablaDeSimbolos.put("FLAG_"+ref, h_aux);
        Nodo.codigo = procs;
        codigo.append("_"+ref);
        codigo.append(" PROC ");
        codigo.append("\n");
        izquierdo.generarCodigo();
        codigo.append("RET");
        codigo.append("\n");
        codigo.append("_"+ref);
        codigo.append(" ENDP");
        codigo.append("\n");
        Nodo.codigo = aux;
    }
}