package Compilador.CodigoIntermedio;

public class Distinto extends Comparador {

    public Distinto(ConTipo izquierdo, ConTipo derecho) {
        super(izquierdo, derecho);
    }

    @Override
    protected String getSalto() {
        return "JE";
    }

}
