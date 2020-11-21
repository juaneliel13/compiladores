package Compilador.CodigoIntermedio;

import Compilador.CodigoAssembler.Registro;
import Compilador.Lexico.Tipos;

public abstract class ConTipo extends Nodo {

    static Tipos[][] compatibilidad = new Tipos[3][3];

    static {
        for(int i=0;i<compatibilidad.length;i++)
            for(int j=0;j<compatibilidad[0].length;j++){
                if(i==j)
                    compatibilidad[i][j]=Tipos.valueOf(i);
                else
                    compatibilidad[i][j]=null;
            }
    }

    private Tipos tipo;

    public Registro reg = null;

    public String var_aux = null;

    public ConTipo(Nodo izquierdo, Nodo derecho) {
        super(izquierdo, derecho);
    }

    public ConTipo() {
        super();
    }

    public Tipos getTipo() {
        return tipo;
    }

    public void setTipo(Tipos tipo) {
        this.tipo = tipo;
    }

    @Override
    public void generarCodigo() {
    }

    public String getRef() {
        if(reg!=null)
            return reg.toString();
        else
            return var_aux;
    }

}
