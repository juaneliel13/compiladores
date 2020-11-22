package Compilador.CodigoIntermedio;

import Compilador.CodigoAssembler.AdministradorDeRegistros;
import Compilador.CodigoAssembler.Registro;
import Compilador.Lexico.Tipos;

public abstract class Comparador extends Operador {

    public static boolean comp_float=false;

    public Comparador(Nodo izquierdo, Nodo derecho) {
        super(izquierdo, derecho);
    }

    @Override
    public void generarCodigo() {
        izquierdo.generarCodigo();
        derecho.generarCodigo();
        ConTipo izq = (ConTipo) izquierdo;
        ConTipo der = (ConTipo) derecho;

        //Genero el codigo de la comparacion de los numeros
        if (izq.getTipo() == Tipos.INTEGER) {
            comparadorInteger(izq, der);
        } else {
            //Al solo tener dos tipos y solo llamarlo a generar codigo
            //cuando no hay errores en este caso seria un Float
            comparadorFloat(izq, der);
        }

        //Creo la etiqueta y agrego el codigo para saltar
        String etiqueta = crearEtiqueta();
        apilar(etiqueta);
        codigo.append(getSalto());
        codigo.append(" ");
        codigo.append(etiqueta);
        codigo.append("\n");
    }


    /**
     * Metodo templado para obtener la instruccion de salto segun el tipo de Comparador concreto
     *
     * @return devuelve la instruccion de salto.
     */
    protected abstract String getSalto();


    /**
     * Genera el codigo para comparar dos Integer.
     *
     * @param izq Nodo izquierdo de la comparacion
     * @param der Nodo derecho de la comparacion
     */
    private void comparadorInteger(ConTipo izq, ConTipo der) {
        String reg1, reg2;
        reg2 = der.getRef();
        if (izq.esHoja() && der.esHoja()) {
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

        //genero la comparacion
        codigo.append("CMP ");
        codigo.append(reg1);
        codigo.append(",");
        codigo.append(reg2);
        codigo.append("\n");

        //libero el registro
        reg.liberar();
    }


    /**
     * Genera el codigo para comparar dos Floats.
     *
     * @param izq Nodo izquierdo de la comparacion
     * @param der Nodo derecho de la comparacion
     */
    private void comparadorFloat(ConTipo izq, ConTipo der) {
        //si no existe mem2bytes
        //la creo
        codigo.append("FLD ");// op1
        codigo.append(izq.getRef());
        codigo.append("\nFCOMP ");//op2
        codigo.append(der.getRef());
        codigo.append("\nFSTSW mem2bytes\nMOV AX, mem2bytes\nSAHF\n");
        comp_float=true;
    }

}
