package Compilador.CodigoIntermedio;

import Compilador.Lexico.Tipos;

public class Parametro {

    public String idRef;
    public Tipos tipo;
    public String tipoPasaje;

    public Parametro(String idRef, Tipos tipo, String tipoPasaje){
        this.idRef = idRef;
        this.tipo = tipo;
        this.tipoPasaje = tipoPasaje;
    }

    @Override
    public String toString(){
        return "{" + idRef + "," + tipo + "," + tipoPasaje + "}";
    }

}
