package Compilador.CodigoIntermedio;

import Compilador.CodigoAssembler.AdministradorDeRegistros;
import Compilador.Lexico.Tipos;

public class Asignacion extends ConTipo {
    static Tipos[][] compatibilidad = new Tipos[3][3];

    static {
        // Se inicializa la matriz de compatibilidad
        // como no aceptamos conversiones ponemos todos null, salvo la diagonal
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
            asignacionInteger(izq,der);
        } else {
            asignacionFloat(izq,der);
        }
    }

    /**
     * Esta funcion genera el codigo assembler para la asignacion lValue = rValue de tipo INTEGER.
     * @param lValue nodo Hoja donde se guarda el resultado de la expresion.
     * @param rValue nodo ConTipo ( Hoja o Operador ) que tiene la expresion a guardar en la variable.
     */
    private void asignacionInteger(ConTipo lValue, ConTipo rValue){
        //Si es una Hoja el lado derecho hay que moverlo a un registro
        //ya que no se puede hacer un MOV de memoria a memoria.
        //sino se asigna el registro del rValue a this para luego liberarlo
        if (rValue.esHoja()) {
            this.reg = AdministradorDeRegistros.get16bits(this);
            codigo.append("MOV ");
            codigo.append(this.reg);
            codigo.append(",");
            codigo.append(rValue.getRef());
            codigo.append("\n");
        } else {
            this.reg = rValue.reg;
        }

        //se mueve la variable del rValue al lValue
        codigo.append("MOV ");
        codigo.append(lValue.getRef());
        codigo.append(",");
        codigo.append(this.reg);
        codigo.append("\n");

        //Se libera el registro utilizado
        this.reg.liberar();
    }

    /**
     * Esta funcion genera el codigo assembler para la asignacion lValue = rValue de tipo FLOAT.
     * @param lValue nodo Hoja donde se guarda el resultado de la expresion.
     * @param rValue nodo ConTipo ( Hoja o Operador ) que tiene la expresion a guardar en la variable.
     */
    private void asignacionFloat(ConTipo lValue, ConTipo rValue) {
        codigo.append("FLD ");
        codigo.append(rValue.getRef());
        codigo.append("\nFST ");
        codigo.append(lValue.getRef());
        codigo.append("\n");
    }

}
