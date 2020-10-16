package Compilador.CodigoIntermedio;

public class Hoja extends Nodo {

    public String ref; //referencia a la tabla de simbolos

    public Hoja(String tipo, Nodo izquierdo, Nodo derecho, String ref) {
        super(tipo, izquierdo, derecho);
        this.ref = ref;
    }

    @Override
    public String generarCodigo() {

        return null;
    }
}
