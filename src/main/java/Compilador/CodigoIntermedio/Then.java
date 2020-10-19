package Compilador.CodigoIntermedio;

public class Then extends Nodo {

    public Then(Nodo nodo) {
        super(nodo,null);
    }

    @Override
    public String generarCodigo() {
        //
        return izquierdo.generarCodigo();
    }
}
