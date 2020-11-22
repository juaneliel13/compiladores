package Compilador.CodigoIntermedio;

import Compilador.CodigoAssembler.AdministradorDeRegistros;
import Compilador.Lexico.Tipos;

public class Multiplicacion extends Operador {

    public Multiplicacion(ConTipo izquierdo, ConTipo derecho) {
        super(izquierdo, derecho);
    }

    @Override
    public void generarCodigo() {
        izquierdo.generarCodigo();
        derecho.generarCodigo();
        ConTipo izq = (ConTipo) izquierdo;
        ConTipo der = (ConTipo) derecho;
        if (this.getTipo() == Tipos.INTEGER) {
            multiplicacionInteger(izq, der);
        } else {
            multiplicacionFloat(izq, der);
        }
    }

    /**
     * Esta funcion genera el codigo assembler para la multiplicacion de izq * der de INTEGER.
     *
     * @param izq Nodo ConTipo que representa el primer operando
     * @param der Nodo ConTipo que representa el segundo operando
     */
    private void multiplicacionInteger(ConTipo izq, ConTipo der) {

        //Si estan ocupados los registros AX o DX se liberan
        if (!AdministradorDeRegistros.AX.estaLibre() && izq.reg != AdministradorDeRegistros.AX) {
            liberarReg(AdministradorDeRegistros.AX);
        }
        if (!AdministradorDeRegistros.DX.estaLibre()) {
            liberarReg(AdministradorDeRegistros.DX);
        }

        //Se ocupan los registros AX y DX
        this.reg = AdministradorDeRegistros.getAX(this);
        AdministradorDeRegistros.getDX(this);
        AdministradorDeRegistros.DX.liberar();


        if (izq.reg != AdministradorDeRegistros.AX) {
            //Quiere decir que izq es una hoja
            //se mueve el lado izq en AX
            codigo.append("MOV AX, ");
            codigo.append(izq.getRef());
            codigo.append("\nIMUL AX, ");
            codigo.append(der.getRef());
            codigo.append("\n");
        } else {
            //Se multiplica directamente (izq siempre va a ser AX)
            codigo.append("IMUL AX, ");
            codigo.append(der.getRef());
            codigo.append("\n");
        }
        if (!der.esHoja())
            der.reg.liberar();
    }

    /**
     * Esta funcion genera el codigo assembler para la multiplicacion de izq * der de FLOAT.
     *
     * @param izq Nodo ConTipo que representa el primer operando
     * @param der Nodo ConTipo que representa el segundo operando
     */
    private void multiplicacionFloat(ConTipo izq, ConTipo der) {

        //Se carga el primer operando a la pila
        codigo.append("FLD ");
        codigo.append(izq.getRef());

        //Se efectua la multiplicacion con el segundo operando
        codigo.append("\nFMUL ");
        codigo.append(der.getRef());

        //Se crea una varible auxiliar y se almacena el resultado en ella
        String aux = crearAuxiliar();
        codigo.append("\nFSTP ");
        codigo.append(aux);
        codigo.append("\n");
        var_aux = aux;
    }

}
