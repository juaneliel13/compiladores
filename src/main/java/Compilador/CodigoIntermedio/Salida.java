package Compilador.CodigoIntermedio;

import Compilador.Lexico.Tipos;

public class Salida extends Nodo {

    public static boolean hay_salida = false;
    public static boolean integer = false;
    String ref;
    String tipo_print;
    Tipos tipo;

    public Salida(String ref) {
        this.ref = ref;
        hay_salida = true;
        tipo = (Tipos) lex.tablaDeSimbolos.get(ref).get("Tipo");

        //Se setea el tipo_print que es el string formater del printf
        switch (tipo) {
            case INTEGER:
                tipo_print = "hd";
                break;
            case FLOAT:
                tipo_print = "f";
                break;
            case STRING:
                tipo_print = "s";
                break;
        }
    }

    @Override
    public void generarCodigo() {

        //Si es un INTEGER se mueve a un registro de 32 bits para poder
        //imprimirlo de forma correcta
        if (tipo == Tipos.INTEGER) {
            codigo.append("MOV AX, _");
            codigo.append(ref.replaceAll("-","N"));
            codigo.append("\nCWDE\nMOV aux_salida, EAX\n");
        }

        //Se genera el inoke al printf con su correspondiente string formater
        codigo.append("invoke printf, cfm$(\"%");
        codigo.append(tipo_print);
        codigo.append("\\n\"),");

        //Dependiendo del tipo de impresion se pasa el parametro correspondiente
        switch (tipo) {
            case INTEGER:
                integer = true;
                codigo.append("aux_salida");
                break;
            case FLOAT:
                codigo.append("_");
                codigo.append(ref.replace(".", "_").replaceAll("-","N"));
                break;
            case STRING:
                codigo.append("OFFSET _");
                codigo.append(ref.replaceAll("'", "").replaceAll(" ", "_"));
                break;
        }
        codigo.append("\n");
    }
}
