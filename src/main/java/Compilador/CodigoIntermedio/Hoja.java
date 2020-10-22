package Compilador.CodigoIntermedio;

public class Hoja extends ConTipo {

    public String ref; //referencia a la tabla de simbolos

    public Hoja(String ref) {
        super(null, null);
        this.ref = ref;
    }

    public Hoja() {
        super();
    }

    @Override
    public String generarCodigo() {

        return null;
    }
}
