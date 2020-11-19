package Compilador.CodigoIntermedio;

import Compilador.Lexico.Tipos;

public class Salida extends Nodo {
    String ref;
    String tipo_print;
    public Salida(String ref){
        this.ref=ref;

        Tipos tipo = (Tipos) lex.tablaDeSimbolos.get(ref).get("Tipo");
        switch (tipo){
            case INTEGER:
                tipo_print="hd";
                break;
            case FLOAT:
                tipo_print="f";
                break;
            case STRING:
                tipo_print="s";
                break;
        }
    }
    @Override
    public void generarCodigo() {

        codigo.append("invoke printf, cfm$(\"%");
        codigo.append(tipo_print);
        codigo.append("\\n\"),");
        if(lex.tablaDeSimbolos.get(ref).get("Tipo")==Tipos.STRING)
            codigo.append("OFFSET _"+ref.replace("'",""));
        else
            codigo.append("_"+ref.replace(".","_"));
        codigo.append("\n");


    }
}
