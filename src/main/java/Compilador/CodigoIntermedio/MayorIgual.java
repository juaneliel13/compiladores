package Compilador.CodigoIntermedio;

import Compilador.Lexico.Tipos;

public class MayorIgual extends Operador {
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

    public MayorIgual(ConTipo izquierdo, ConTipo derecho) {
        super(izquierdo, derecho);
    }

    public void updateTipo(){
        ConTipo izq = (ConTipo)izquierdo;
        ConTipo der = (ConTipo)derecho;
        if(izquierdo==null || izq.getTipo()==null || derecho==null || der.getTipo()==null)
            setTipo(null);
        else {
            setTipo(compatibilidad[izq.getTipo().getValue()][der.getTipo().getValue()]);
        }
    }

}
