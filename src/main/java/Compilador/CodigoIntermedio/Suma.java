package Compilador.CodigoIntermedio;

import Compilador.CodigoAssembler.AdministradorDeRegistros;
import Compilador.Lexico.Tipos;

public class Suma extends Operador {

    public Suma(ConTipo izquierdo, ConTipo derecho) {
        super(izquierdo, derecho);
    }

    @Override
    public void generarCodigo() {
        izquierdo.generarCodigo();
        derecho.generarCodigo();
        ConTipo izq = (ConTipo) izquierdo;
        ConTipo der = (ConTipo) derecho;
        if (this.getTipo() == Tipos.INTEGER) {
            String reg1, reg2;
            if (izquierdo.esHoja() && derecho.esHoja()) {
                reg = AdministradorDeRegistros.get16bits(this);
                codigo.append("MOV ");
                codigo.append(reg);
                codigo.append(",");
                codigo.append(izq.getRef());
                codigo.append("\n");
                reg1 = reg.toString();
                reg2 = der.getRef();
            } else {
                if (izquierdo.esHoja()) {
                    reg = der.reg;
                    AdministradorDeRegistros.nombre.put(reg,this);
                    reg1 = der.getRef();
                    reg2 = izq.getRef();
                } else {
                    reg = izq.reg;
                    AdministradorDeRegistros.nombre.put(reg,this);
                    reg1 = izq.getRef();
                    reg2 = der.getRef();
                }
                if (!izquierdo.esHoja() && !derecho.esHoja()) {
                    der.reg.liberar();
                }

            }
            codigo.append("ADD ");
            codigo.append(reg1);
            codigo.append(",");
            codigo.append(reg2);
            codigo.append("\n");
        } else {
            codigo.append("FLD ");
            codigo.append(izq.getRef());
            codigo.append("\n");
            codigo.append("FADD ");
            codigo.append(der.getRef());
            codigo.append("\n");

            String aux = crearAuxiliar();
            codigo.append("FSTP ");
            codigo.append(aux);
            codigo.append("\n");
            var_aux = aux;
            //generacion de codigo para suma flotante
        }
    }


}
