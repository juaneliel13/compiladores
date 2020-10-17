package Compilador.CodigoIntermedio;

public class Asignacion extends ConTipo {

    public Asignacion(Nodo izquierdo, Nodo derecho){
        super(izquierdo, derecho);
    }

    @Override
    public String generarCodigo() {
        return null;
    }
}
