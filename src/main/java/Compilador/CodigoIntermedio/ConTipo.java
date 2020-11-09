package Compilador.CodigoIntermedio;

import Compilador.CodigoAssembler.Registro;
import Compilador.Lexico.Tipos;

public abstract class ConTipo extends Nodo {

    private Tipos tipo;

    public Registro reg = null;

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
    public void generarCodigo() {
    }

    public String getRef() {
        return reg.toString();
    }

    public boolean esHoja() {
        return ((izquierdo == null) && (derecho == null));
    }

}
