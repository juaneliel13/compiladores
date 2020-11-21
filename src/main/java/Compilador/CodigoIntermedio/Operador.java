package Compilador.CodigoIntermedio;

public abstract class Operador extends ConTipo {

    public Operador(Nodo izquierdo, Nodo derecho) {
        super(izquierdo, derecho);
    }

    public void updateTipo() {
        ConTipo izq = (ConTipo) izquierdo;
        ConTipo der = (ConTipo) derecho;
        if (izquierdo == null || izq.getTipo() == null || derecho == null || der.getTipo() == null)
            setTipo(null);
        else {
            setTipo(compatibilidad[izq.getTipo().getValue()][der.getTipo().getValue()]);
        }
    }

}
