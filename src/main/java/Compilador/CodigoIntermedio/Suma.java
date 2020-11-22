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
            sumaInteger(izq,der);
        } else {
            sumaFloat(izq,der);
        }
    }

    /**
     * Esta funcion genera el codigo assembler para la suma de izq + der de INTEGER.
     *
     * @param izq Nodo ConTipo que representa el primer operando
     * @param der Nodo ConTipo que representa el segundo operando
     */
    private void sumaInteger(ConTipo izq, ConTipo der) {
        String reg1, reg2;
        if (izquierdo.esHoja() && derecho.esHoja()) {
            moverHojaAReg(izq);
            reg1 = reg.toString();
            reg2 = der.getRef();
        } else {
            if (izquierdo.esHoja()) {
                reg = der.reg;
                reg1 = der.getRef();
                reg2 = izq.getRef();
            } else {
                reg = izq.reg;
                reg1 = izq.getRef();
                reg2 = der.getRef();
            }
            AdministradorDeRegistros.nombre.put(reg,this);
            if (!izquierdo.esHoja() && !derecho.esHoja()) {
                der.reg.liberar();
            }
        }
        codigo.append("ADD ");
        codigo.append(reg1);
        codigo.append(",");
        codigo.append(reg2);
        codigo.append("\n");
    }

    /**
     * Esta funcion genera el codigo assembler para la suma de izq + der de FLOAT.
     *
     * @param izq Nodo ConTipo que representa el primer operando
     * @param der Nodo ConTipo que representa el segundo operando
     */
    private void sumaFloat(ConTipo izq, ConTipo der) {

        //Se carga el primer operando a la pila
        codigo.append("FLD ");
        codigo.append(izq.getRef());
        codigo.append("\n");

        //Se efectua la suma con el segundo operando
        codigo.append("FADD ");
        codigo.append(der.getRef());
        codigo.append("\n");

        //Se crea una variable auxiliar y se almacena el resultado en ella
        String aux = crearAuxiliar();
        codigo.append("FSTP ");
        codigo.append(aux);
        codigo.append("\n");
        var_aux = aux;
    }
}
