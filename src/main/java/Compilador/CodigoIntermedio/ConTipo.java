package Compilador.CodigoIntermedio;

import Compilador.Lexico.Tipos;

public class ConTipo extends Nodo {

    private Tipos tipo;

    public boolean marca=false;

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
        return;
    }

    public boolean esHoja() {
        return ((izquierdo == null) && (derecho == null)) || this.marca;
    }

    public ConTipo recorrido() {

        if (this.esHoja())
            return null;
        if (izquierdo.esHoja() && derecho.esHoja()) {
            this.marca=true;
            return this;

        } else {
            ConTipo izq = (ConTipo)izquierdo;
            ConTipo aux = izq.recorrido();
            if (aux != null) {
                aux.marca = true;
                return aux;
            } else {
                ConTipo der = (ConTipo)derecho;
                aux = der.recorrido();
                if (aux != null) {
                    aux.marca = true;
                    return aux;
                }
                else {
                    this.marca=true;
                    return this;
                }
            }
        }
    }
}
