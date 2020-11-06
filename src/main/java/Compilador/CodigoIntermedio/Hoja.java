package Compilador.CodigoIntermedio;

import Compilador.Lexico.AnalizadorLexico;
import Compilador.Lexico.Tipos;

public class Hoja extends ConTipo {

    public String ref; //referencia a la tabla de simbolos

    public Hoja(String ref) {
        super(null, null);
        this.ref = ref;
        System.out.println("referencia "+ref);
        System.out.println(lex.tablaDeSimbolos);
        setTipo((Tipos)lex.tablaDeSimbolos.get(ref).get("Tipo"));
    }

    public Hoja() {
        super();
    }

    @Override
    public String generarCodigo() {

        return null;
    }
}
