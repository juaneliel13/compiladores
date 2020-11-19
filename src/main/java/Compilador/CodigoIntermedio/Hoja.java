package Compilador.CodigoIntermedio;

import Compilador.Lexico.Tipos;

public class Hoja extends ConTipo {

    public String ref; //referencia a la tabla de simbolos

    public Hoja(String ref) {
        super(null, null);
        this.ref = ref;
        setTipo((Tipos)lex.tablaDeSimbolos.get(ref).get("Tipo"));
    }

    public Hoja() {
        super();
    }

    @Override
    public void generarCodigo() {
    }

    @Override
    public String getRef() {
        if (lex.tablaDeSimbolos.get(ref).get("Tipo")!=Tipos.FLOAT)
            return "_"+ref;
        else
            return "_"+ref.replace(".","_");
    }

    @Override
    public String toString() {
        return "Hoja{" +
                "ref='" + ref + '\'' +
                '}';
    }
}
