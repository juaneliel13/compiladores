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
            restaInteger(izq,der);
        } else {
            restaFloat(izq,der);
        }
    }

    /**
     * Esta funcion genera el codigo assembler para la resta de izq - der de INTEGER.
     *
     * @param izq Nodo ConTipo que representa el primer operando
     * @param der Nodo ConTipo que representa el segundo operando
     */
    private void restaInteger(ConTipo izq, ConTipo der) {

        //Si el izquierdo es hoja se mueve a un registro
        //sino se utiliza el registro del lado izquierdo
        if (izquierdo.esHoja()) {
            moverHojaAReg(izq);
        } else {
            reg = izq.reg;
            AdministradorDeRegistros.nombre.put(reg, this);
        }

        //Se genera el codigo de la resta
        codigo.append("SUB ");
        codigo.append(reg.toString());
        codigo.append(", ");
        codigo.append(der.getRef());
        codigo.append("\n");

        //Si el derecho no es hoja se libera su registro
        if (!derecho.esHoja())
            der.reg.liberar();
    }

    /**
     * Esta funcion genera el codigo assembler para la resta de izq - der de FLOAT.
     *
     * @param izq Nodo ConTipo que representa el primer operando
     * @param der Nodo ConTipo que representa el segundo operando
     */
    private void restaFloat(ConTipo izq, ConTipo der) {
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
    }
}
