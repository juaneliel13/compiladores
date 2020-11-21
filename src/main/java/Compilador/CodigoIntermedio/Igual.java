package Compilador.CodigoIntermedio;

public class Igual extends Comparador {

    public Igual(ConTipo izquierdo, ConTipo derecho) {
        super(izquierdo, derecho);
    }

    @Override
    protected String getSalto() {
        return "JNE";
    }
}
