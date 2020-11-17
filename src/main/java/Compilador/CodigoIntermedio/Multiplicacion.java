package Compilador.CodigoIntermedio;

import Compilador.CodigoAssembler.AdministradorDeRegistros;
import Compilador.CodigoAssembler.Registro;
import Compilador.Lexico.Tipos;

public class Multiplicacion extends Operador {
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

    public Multiplicacion(ConTipo izquierdo, ConTipo derecho) {
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
            if(!AdministradorDeRegistros.AX.estaLibre() && izq.reg!=AdministradorDeRegistros.AX){
                ConTipo propietario = AdministradorDeRegistros.propietario(AdministradorDeRegistros.AX);
                Registro aux = AdministradorDeRegistros.get16bits(propietario);
                propietario.reg = aux;
                codigo.append("MOV ");
                codigo.append(aux);
                codigo.append(",");
                codigo.append(AdministradorDeRegistros.AX);
                codigo.append("\n");

            }
            if(!AdministradorDeRegistros.DX.estaLibre()){
                ConTipo propietario = AdministradorDeRegistros.propietario(AdministradorDeRegistros.DX);
                Registro aux = AdministradorDeRegistros.get16bits(propietario);
                propietario.reg = aux;
                codigo.append("MOV ");
                codigo.append(aux);
                codigo.append(",");
                codigo.append(AdministradorDeRegistros.DX);
                codigo.append("\n");
            }
            this.reg = AdministradorDeRegistros.getAX(this);
            AdministradorDeRegistros.getDX(this);
            AdministradorDeRegistros.DX.liberar();
            if(izq.reg!=AdministradorDeRegistros.AX)
                codigo.append(templateEntero(izq.getRef(),der.getRef())+"\n");
            else
                codigo.append(templateEntero(der.getRef())+"\n");
            if(!der.esHoja())
                der.reg.liberar();
        } else {
            //generacion de codigo para multiplicacion flotante
        }
    }

    private String templateEntero(String reg1, String reg2){
        return "MOV AX,"+reg1+"\nIMUL AX,"+reg2;
    }

    private String templateEntero(String reg2){
        return "IMUL AX,"+reg2;
    }

    private String templateFloat(String reg1, String reg2){
        return "FLD " + reg1 + "\nFMUL " + reg2;
    }
}
