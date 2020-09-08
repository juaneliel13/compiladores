package compiladores.AnalizadorLexico;

import compiladores.AnalizadorLexico.Acciones.AccionSemantica;

import java.util.ArrayList;
import java.util.List;

public class AnalizadorLexico {

    String fuente;
    public int token;
    public int estado;
    public int indice;
    MatrizDeTransicion matrizDeTransicion;
    public List<String> palabrasReservadas;

    public AnalizadorLexico(String fuente){
        this.fuente = fuente;
        this.matrizDeTransicion = new MatrizDeTransicion();
        cargarPalabrasReservadas();
    }

    public Integer getToken() {

        token = -1;
        estado = 0;
        char simbolo;
        AccionSemantica as;
        while(estado!=-1) {
            simbolo=fuente.charAt(indice);
            as=matrizDeTransicion.accionSemantica(estado,simbolo);
            if (as!=null)
                as.accion();
            estado=matrizDeTransicion.siguienteEstado(estado,simbolo);
            //matrizDeTransicion.avanzar();
        }
        return token;
    }

    private void cargarPalabrasReservadas(){
        palabrasReservadas=new ArrayList<String>();
        palabrasReservadas.add("IF");
        palabrasReservadas.add("THEN");
        palabrasReservadas.add("ELSE");
        palabrasReservadas.add("END_IF");
        palabrasReservadas.add("OUT");
        palabrasReservadas.add("FUNC");
        palabrasReservadas.add("RETURN");
        palabrasReservadas.add("INTEGER");
        palabrasReservadas.add("FLOAT");
        palabrasReservadas.add("FOR");
    }

    private void cargarMatrizDeTransicion(){
       // matrizDeTransicion.agregarTransicion(0,);

    }

}
