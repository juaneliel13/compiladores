package Compilador.CodigoIntermedio;

import Compilador.CodigoAssembler.AdministradorDeRegistros;
import Compilador.CodigoAssembler.Registro;
import Compilador.Lexico.Tipos;

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

        if(izq.getTipo() == Tipos.INTEGER) {
            if (izquierdo.esHoja() && derecho.esHoja()) {
                this.reg = AdministradorDeRegistros.get16bits(this);
                codigo.append("MOV ");
                codigo.append(reg);
                codigo.append(",");
                codigo.append(izq.getRef());
                codigo.append("\n");
                reg1 = this.reg.toString();

            } else {
                reg1 = izq.getRef();
            }
            codigo.append("CMP ");
            codigo.append(reg1);
            codigo.append(",");
            codigo.append(reg2);
            codigo.append("\n");
            reg.liberar();
        }
        else if(izq.getTipo() == Tipos.FLOAT) //esto por ahi es al pedo ya que no tenemos otros tipos o si hay error no
                                              //se llama a generar codigo
        {
            //si no existe mem2bytes
                //la creo
            codigo.append("FLD ");// op1
            codigo.append(izq.getRef());
            codigo.append("\nFCOM ");//op2
            codigo.append(der.getRef());
            codigo.append("\nFSTSW mem2bytes\nMOV AX,mem2bytes\nSAHF\n");
        }
        String etiqueta = crearEtiqueta();

        codigo.append(getSalto());
        codigo.append(" ");
        codigo.append(etiqueta);
        codigo.append("\n");

        apilar(etiqueta);
    }

    protected abstract String getSalto();
}
