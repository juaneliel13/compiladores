package Compilador.CodigoIntermedio;

import Compilador.CodigoAssembler.AdministradorDeRegistros;
import Compilador.CodigoAssembler.Registro;

public abstract class Comparador extends Operador {

    public Comparador(Nodo izquierdo, Nodo derecho) {
        super(izquierdo, derecho);
    }

    @Override
    public void generarCodigo() {
        izquierdo.generarCodigo();
        derecho.generarCodigo();
        ConTipo izq = (ConTipo) izquierdo;
        ConTipo der = (ConTipo) derecho;
        String reg1, reg2;
        reg2=der.getRef();
        if (izquierdo.esHoja() && derecho.esHoja()) {
            this.reg = AdministradorDeRegistros.get16bits(this);
            codigo.append("MOV ");
            codigo.append(reg);
            codigo.append(",");
            codigo.append(izq.getRef());
            codigo.append("\n");
            reg1=this.reg.toString();

        }
        else{
            reg1= izq.getRef();
        }

        codigo.append("CMP ");
        codigo.append(reg1);
        codigo.append(",");
        codigo.append(reg2);
        codigo.append("\n");

        reg.liberar();

        codigo.append(getSalto());
        codigo.append(" ");
        codigo.append(crearEtiqueta());
        codigo.append("\n");
    }

    protected abstract String getSalto();
}
