package Compilador.CodigoIntermedio;

import Compilador.CodigoAssembler.AdministradorDeRegistros;
import Compilador.Lexico.Tipos;

public class Suma extends Operador {
    static Tipos[][] compatibilidad = new Tipos[3][3];

    static {
        for (int i = 0; i < compatibilidad.length; i++)
            for (int j = 0; j < compatibilidad[0].length; j++) {
                if (i == j)
                    compatibilidad[i][j] = Tipos.valueOf(i);
                else
                    compatibilidad[i][j] = null;
            }
    }

    public Suma(ConTipo izquierdo, ConTipo derecho) {
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
            codigo.append("FST ");
            codigo.append(aux);
            codigo.append("\n");
            var_aux = aux;
            //generacion de codigo para suma flotante
        }
    }


}
