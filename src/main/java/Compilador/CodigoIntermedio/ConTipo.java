package Compilador.CodigoIntermedio;

import Compilador.Lexico.Tipos;

public class ConTipo extends Nodo {

    Tipos tipo;

    public ConTipo(Nodo izquierdo, Nodo derecho) {
        super(izquierdo, derecho);
    }

    public ConTipo() {
        super();
    }

    public Tipos getTipo() {
        return tipo;
    }

    public void setTipo(Tipos tipo) {
        this.tipo = tipo;
    }

    @Override
    public String generarCodigo() {
        return null;
    }
}
