package Compilador.CodigoIntermedio;

import Compilador.CodigoAssembler.AdministradorDeRegistros;
import Compilador.CodigoAssembler.Registro;

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

    /**
     * Libera el registro 'reg'. Para esto se pide un nuevo registro 'aux' donde guardarlo
     * y el nodo que tenia asociado 'reg' se le asocia 'aux'.
     *
     * @param reg Registro que se quiere liberar
     */
    protected void liberarReg(Registro reg) {
        ConTipo propietario = AdministradorDeRegistros.propietario(reg);
        Registro aux = AdministradorDeRegistros.get16bits(propietario);
        propietario.reg = aux;
        codigo.append("MOV ");
        codigo.append(aux);
        codigo.append(", ");
        codigo.append(reg);
        codigo.append("\n");
    }

    protected void moverHojaAReg(ConTipo izq) {
        reg = AdministradorDeRegistros.get16bits(this);
        codigo.append("MOV ");
        codigo.append(reg);
        codigo.append(", ");
        codigo.append(izq.getRef());
        codigo.append("\n");
    }

}
