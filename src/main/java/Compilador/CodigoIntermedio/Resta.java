package Compilador.CodigoIntermedio;

import Compilador.CodigoAssembler.AdministradorDeRegistros;
import Compilador.Lexico.Tipos;

public class Resta extends Operador {

    public Resta(ConTipo izquierdo, ConTipo derecho) {
        super(izquierdo, derecho);
    }

    @Override
    public void generarCodigo() {
        izquierdo.generarCodigo();
        derecho.generarCodigo();
        ConTipo izq = (ConTipo) izquierdo;
        ConTipo der = (ConTipo) derecho;
        if (this.getTipo() == Tipos.INTEGER) {
            if (izquierdo.esHoja()) {
                reg = AdministradorDeRegistros.get16bits(this);
                codigo.append("MOV ");
                codigo.append(reg);
                codigo.append(",");
                codigo.append(izq.getRef());
                codigo.append("\n");
            } else {
                reg = izq.reg;
                AdministradorDeRegistros.nombre.put(reg, this);
            }
            codigo.append("SUB ");
            codigo.append(reg.toString());
            codigo.append(",");
            codigo.append(der.getRef());
            codigo.append("\n");

            if (!derecho.esHoja())
                der.reg.liberar();
        } else {
            codigo.append("FLD ");
            codigo.append(izq.getRef());
            codigo.append("\n");
            codigo.append("FSUB ");
            codigo.append(der.getRef());
            codigo.append("\n");

            String aux = crearAuxiliar();
            codigo.append("FSTP ");
            codigo.append(aux);
            codigo.append("\n");
            var_aux = aux;
            //generacion de codigo para resta flotante
        }
    }
}
