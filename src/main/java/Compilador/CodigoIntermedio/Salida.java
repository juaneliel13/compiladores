package Compilador.CodigoIntermedio;

import Compilador.Lexico.Tipos;

public class Salida extends Nodo {
    public static boolean hay_salida=false;
    public static boolean integer=false;
    String ref;
    String tipo_print;
    public Salida(String ref){
        this.ref=ref;
        hay_salida=true;
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
        Tipos tipo = (Tipos) lex.tablaDeSimbolos.get(ref).get("Tipo");
        if(tipo==Tipos.INTEGER) {
            codigo.append("MOV AX, _" );
            codigo.append(ref);
            codigo.append("\nCWDE\nMOV aux_salida, EAX\n");
        }

        codigo.append("invoke printf, cfm$(\"%");
        codigo.append(tipo_print);
        codigo.append("\\n\"),");
        if(tipo==Tipos.STRING) {
            codigo.append("OFFSET _" + ref.replaceAll("'", "").replaceAll(" ","_"));
        }else if(tipo==Tipos.INTEGER) {
            integer=true;
            codigo.append("aux_salida");
        }else {
            codigo.append("_" + ref.replace(".", "_"));
        }
        codigo.append("\n");


    }
}
