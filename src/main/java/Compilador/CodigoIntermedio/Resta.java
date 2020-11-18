package Compilador.CodigoIntermedio;

import Compilador.CodigoAssembler.AdministradorDeRegistros;
import Compilador.Lexico.Tipos;

public class Resta extends Operador {
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
    public Resta(ConTipo izquierdo, ConTipo derecho) {
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

    @Override
    public void generarCodigo() {
        izquierdo.generarCodigo();
        derecho.generarCodigo();
        ConTipo izq = (ConTipo) izquierdo;
        ConTipo der = (ConTipo) derecho;
        if(this.getTipo() == Tipos.INTEGER){
            if(izquierdo.esHoja()) {
                reg = AdministradorDeRegistros.get16bits(this);
                codigo.append("MOV ");
                codigo.append(reg);
                codigo.append(",");
                codigo.append(izq.getRef());
                codigo.append("\n");
            }
            else{
                reg = izq.reg;
                AdministradorDeRegistros.nombre.put(reg,this);
            }
            codigo.append("SUB ");
            codigo.append(reg.toString());
            codigo.append(",");
            codigo.append(der.getRef());
            codigo.append("\n");

            if(!derecho.esHoja())
                der.reg.liberar();
        } else {
            codigo.append("FLD ");
            codigo.append(izq.getRef());
            codigo.append("\n");
            codigo.append("FSUB ");
            codigo.append(der.getRef());
            codigo.append("\n");

            String aux = crearAuxiliar();
            codigo.append("FST ");
            codigo.append(aux);
            codigo.append("\n");
            var_aux = aux;
            //generacion de codigo para resta flotante
        }
    }
}
