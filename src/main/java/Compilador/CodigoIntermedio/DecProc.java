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