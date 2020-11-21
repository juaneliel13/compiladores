package Compilador.CodigoIntermedio;

import Compilador.CodigoAssembler.AdministradorDeRegistros;
import Compilador.Lexico.Tipos;

public class Division extends Operador {

    public Division(ConTipo izquierdo, ConTipo derecho) {
        super(izquierdo, derecho);
    }

    @Override
    public void generarCodigo() {
        izquierdo.generarCodigo();
        derecho.generarCodigo();
        ConTipo izq = (ConTipo) izquierdo;
        ConTipo der = (ConTipo) derecho;
        if (this.getTipo() == Tipos.INTEGER) {
            divisionInteger(izq, der);
        } else {
            divisionFloat(izq, der);
        }
    }

    /**
     * Esta funcion genera el codigo assembler para la division de izq / der de INTEGER.
     *
     * @param izq Nodo ConTipo que representa el dividendo
     * @param der Nodo ConTipo que representa el divisor
     */
    private void divisionInteger(ConTipo izq, ConTipo der) {

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

            //Se verifica que el divisor sea distinto de cero
            codigo.append("\nCWD\nCMP ");
            codigo.append(der.getRef());
            codigo.append(", 0\nJE _CERO\n");

            //Se efectua la division
            codigo.append("IDIV ");
            codigo.append(der.getRef());
            codigo.append("\n");
        } else {
            //Se verifica que el divisor sea distinto de cero y divide directamente (izq siempre va a ser AX)
            codigo.append("CWD\nCMP ");
            codigo.append(der.getRef());
            codigo.append(", 0\nJE _CERO\nIDIV ");
            codigo.append(der.getRef());
            codigo.append("\n");
        }

        //Se libera el registro de la expresion del lado derecho
        if (!der.esHoja())
            der.reg.liberar();
    }

    /**
     * Esta funcion genera el codigo assembler para la division de izq / der de FLOAT.
     *
     * @param izq Nodo ConTipo que representa el dividendo
     * @param der Nodo ConTipo que representa el divisor
     */
    private void divisionFloat(ConTipo izq, ConTipo der) {

        //Se carga el divisor a la pila
        codigo.append("FLD ");
        codigo.append(der.getRef());

        //Se verifica que el divisor no sea 0
        codigo.append("\nFLD _0_0\nFCOMP\nFSTSW mem2bytes\nMOV AX, mem2bytes\nSAHF\nJE _CERO\nFLD ");

        //Se carga el dividendo a la pila
        codigo.append(izq.getRef());

        //Se efectua la division
        codigo.append("\nFDIVR\n");

        //Se crea una variable auxiliar y se almacena el resultado en ella
        String aux = crearAuxiliar();
        codigo.append("FSTP ");
        codigo.append(aux);
        codigo.append("\n");
        var_aux = aux;
    }

}

