package Compilador.CodigoIntermedio;

import Compilador.CodigoAssembler.AdministradorDeRegistros;
import Compilador.CodigoAssembler.Registro;
import Compilador.Lexico.Tipos;

public class Division extends Operador {
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

    public Division(ConTipo izquierdo, ConTipo derecho) {
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
            if(!AdministradorDeRegistros.AX.estaLibre()){
                ConTipo propietario = AdministradorDeRegistros.propietario(AdministradorDeRegistros.AX);
                Registro aux = AdministradorDeRegistros.get16bits(propietario);
                System.out.println(propietario);
                propietario.reg = aux;
                codigo.append("MOV ");
                codigo.append(aux);
                codigo.append(",");
                codigo.append(AdministradorDeRegistros.AX);
                codigo.append("\n");
            }
            this.reg = AdministradorDeRegistros.getAX(this);
            codigo.append("MOV ");
            codigo.append(reg);
            codigo.append(",");
            codigo.append(izq.getRef());
            codigo.append("\n");
            codigo.append("IDIV ");
            codigo.append(reg.toString());
            codigo.append(",");
            codigo.append(der.getRef());
            codigo.append("\n");
        } else {
            //generacion de codigo para resta flotante
        }
    }
}
