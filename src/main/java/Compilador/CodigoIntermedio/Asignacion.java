package Compilador.CodigoIntermedio;

import Compilador.CodigoAssembler.AdministradorDeRegistros;
import Compilador.Lexico.Tipos;

public class Asignacion extends ConTipo {
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

    public Asignacion(ConTipo izquierdo, ConTipo derecho) {
        super(izquierdo, derecho);
        if (izquierdo == null || izquierdo.getTipo() == null || derecho == null || derecho.getTipo() == null)
            setTipo(null);
        else {
            setTipo(compatibilidad[izquierdo.getTipo().getValue()][derecho.getTipo().getValue()]);
        }
    }


    @Override
    public void generarCodigo() {
        izquierdo.generarCodigo();
        derecho.generarCodigo();
        ConTipo izq = (ConTipo) izquierdo;
        ConTipo der = (ConTipo) derecho;
        if (getTipo() == Tipos.INTEGER) {
            if (der.esHoja()) {
                this.reg = AdministradorDeRegistros.get16bits(this);
                codigo.append("MOV ");
                codigo.append(this.reg);
                codigo.append(",");
                codigo.append(der.getRef());
                codigo.append("\n");
            } else {
                this.reg = der.reg;
            }
            codigo.append("MOV ");
            codigo.append(izq.getRef());
            codigo.append(",");
            codigo.append(this.reg);
            codigo.append("\n");
            this.reg.liberar();
        } else {
            codigo.append("FLD ");
            codigo.append(der.getRef());
            codigo.append("\n");
            codigo.append("FST ");
            codigo.append(izq.getRef());
            codigo.append("\n");
        }
    }


}
