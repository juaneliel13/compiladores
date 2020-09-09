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
        this.matrizDeTransicion = new MatrizDeTransicion(17,26);
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
                as.accion(simbolo);
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
        //FILA: 0
        matrizDeTransicion.agregarTransicion(0,0,2);
        matrizDeTransicion.agregarTransicion(0,1,1);
        matrizDeTransicion.agregarTransicion(0,2,1);
        matrizDeTransicion.agregarTransicion(0,3,1);
        matrizDeTransicion.agregarTransicion(0,4,15);
        matrizDeTransicion.agregarTransicion(0,5,Integer.MAX_VALUE);
        matrizDeTransicion.agregarTransicion(0,6,Integer.MAX_VALUE);
        matrizDeTransicion.agregarTransicion(0,7,Integer.MAX_VALUE);
        matrizDeTransicion.agregarTransicion(0,8,8);
        //matrizDeTransicion.agregarTransicion(0,9,);
        matrizDeTransicion.agregarTransicion(0,10,13);
        matrizDeTransicion.agregarTransicion(0,11,12);
        matrizDeTransicion.agregarTransicion(0,12,11);
        matrizDeTransicion.agregarTransicion(0,13,14);
        //matrizDeTransicion.agregarTransicion(0,14,);
        matrizDeTransicion.agregarTransicion(0,15,Integer.MAX_VALUE);
        matrizDeTransicion.agregarTransicion(0,16,Integer.MAX_VALUE);
        matrizDeTransicion.agregarTransicion(0,17,Integer.MAX_VALUE);
        matrizDeTransicion.agregarTransicion(0,18,Integer.MAX_VALUE);
        matrizDeTransicion.agregarTransicion(0,19,4);
        matrizDeTransicion.agregarTransicion(0,20,Integer.MAX_VALUE);
        matrizDeTransicion.agregarTransicion(0,21,Integer.MAX_VALUE);
        matrizDeTransicion.agregarTransicion(0,22,0);
        matrizDeTransicion.agregarTransicion(0,23,0);
        matrizDeTransicion.agregarTransicion(0,24,0);
        matrizDeTransicion.agregarTransicion(0,25,16);
        //FILA: 1
        matrizDeTransicion.agregarTransicion(1,0,1);
        matrizDeTransicion.agregarTransicion(1,1,1);
        matrizDeTransicion.agregarTransicion(1,2,1);
        matrizDeTransicion.agregarTransicion(1,3,1);
        matrizDeTransicion.agregarTransicion(1,4,Integer.MAX_VALUE);
        matrizDeTransicion.agregarTransicion(1,5,Integer.MAX_VALUE);
        matrizDeTransicion.agregarTransicion(1,6,Integer.MAX_VALUE);
        matrizDeTransicion.agregarTransicion(1,7,Integer.MAX_VALUE);
        matrizDeTransicion.agregarTransicion(1,8,Integer.MAX_VALUE);
        matrizDeTransicion.agregarTransicion(1,9,Integer.MAX_VALUE);
        matrizDeTransicion.agregarTransicion(1,10,Integer.MAX_VALUE);
        matrizDeTransicion.agregarTransicion(1,11,Integer.MAX_VALUE);
        matrizDeTransicion.agregarTransicion(1,12,Integer.MAX_VALUE);
        matrizDeTransicion.agregarTransicion(1,13,Integer.MAX_VALUE);
        matrizDeTransicion.agregarTransicion(1,14,1);
        matrizDeTransicion.agregarTransicion(1,15,Integer.MAX_VALUE);
        matrizDeTransicion.agregarTransicion(1,16,Integer.MAX_VALUE);
        matrizDeTransicion.agregarTransicion(1,17,Integer.MAX_VALUE);
        matrizDeTransicion.agregarTransicion(1,18,Integer.MAX_VALUE);
        matrizDeTransicion.agregarTransicion(1,19,Integer.MAX_VALUE);
        matrizDeTransicion.agregarTransicion(1,20,Integer.MAX_VALUE);
        matrizDeTransicion.agregarTransicion(1,21,Integer.MAX_VALUE);
        matrizDeTransicion.agregarTransicion(1,22,Integer.MAX_VALUE);
        matrizDeTransicion.agregarTransicion(1,23,Integer.MAX_VALUE);
        matrizDeTransicion.agregarTransicion(1,24,Integer.MAX_VALUE);
        matrizDeTransicion.agregarTransicion(1,25,);
        //FILA: 2
        matrizDeTransicion.agregarTransicion(2,0,2);
        //matrizDeTransicion.agregarTransicion(2,1,);
        //matrizDeTransicion.agregarTransicion(2,2,);
        //matrizDeTransicion.agregarTransicion(2,3,);
        //matrizDeTransicion.agregarTransicion(2,4,);
        //matrizDeTransicion.agregarTransicion(2,5,);
        //matrizDeTransicion.agregarTransicion(2,6,);
        //matrizDeTransicion.agregarTransicion(2,7,);
        //matrizDeTransicion.agregarTransicion(2,8,);
        //matrizDeTransicion.agregarTransicion(2,9,);
        //matrizDeTransicion.agregarTransicion(2,10,);
        //matrizDeTransicion.agregarTransicion(2,11,);
        //matrizDeTransicion.agregarTransicion(2,12,);
        //matrizDeTransicion.agregarTransicion(2,13,);
        matrizDeTransicion.agregarTransicion(2,14,3);
        //matrizDeTransicion.agregarTransicion(2,15,);
        //matrizDeTransicion.agregarTransicion(2,16,);
        //matrizDeTransicion.agregarTransicion(2,17,);
        //matrizDeTransicion.agregarTransicion(2,18,);
        matrizDeTransicion.agregarTransicion(2,19,4);
        //matrizDeTransicion.agregarTransicion(2,20,);
        //matrizDeTransicion.agregarTransicion(2,21,);
        //matrizDeTransicion.agregarTransicion(2,22,);
        //matrizDeTransicion.agregarTransicion(2,23,);
        //matrizDeTransicion.agregarTransicion(2,24,);
        //matrizDeTransicion.agregarTransicion(2,25,);
        //FILA: 3
        //matrizDeTransicion.agregarTransicion(3,0,);
        //matrizDeTransicion.agregarTransicion(3,1,);
        matrizDeTransicion.agregarTransicion(3,2,Integer.MAX_VALUE);
        //matrizDeTransicion.agregarTransicion(3,3,);
        //matrizDeTransicion.agregarTransicion(3,4,);
        //matrizDeTransicion.agregarTransicion(3,5,);
        //matrizDeTransicion.agregarTransicion(3,6,);
        //matrizDeTransicion.agregarTransicion(3,7,);
        //matrizDeTransicion.agregarTransicion(3,8,);
        //matrizDeTransicion.agregarTransicion(3,9,);
        //matrizDeTransicion.agregarTransicion(3,10,);
        //matrizDeTransicion.agregarTransicion(3,11,);
        //matrizDeTransicion.agregarTransicion(3,12,);
        //matrizDeTransicion.agregarTransicion(3,13,);
        //matrizDeTransicion.agregarTransicion(3,14,);
        //matrizDeTransicion.agregarTransicion(3,15,);
        //matrizDeTransicion.agregarTransicion(3,17,);
        //matrizDeTransicion.agregarTransicion(3,16,);
        //matrizDeTransicion.agregarTransicion(3,18,);
        //matrizDeTransicion.agregarTransicion(3,19,);
        //matrizDeTransicion.agregarTransicion(3,20,);
        //matrizDeTransicion.agregarTransicion(3,21,);
        //matrizDeTransicion.agregarTransicion(3,22,);
        //matrizDeTransicion.agregarTransicion(3,23,);
        //matrizDeTransicion.agregarTransicion(3,24,);
        //matrizDeTransicion.agregarTransicion(3,25,);
        //FILA: 4
        matrizDeTransicion.agregarTransicion(4,0,4);
        matrizDeTransicion.agregarTransicion(4,1,Integer.MAX_VALUE);
        matrizDeTransicion.agregarTransicion(4,2,Integer.MAX_VALUE);
        matrizDeTransicion.agregarTransicion(4,3,5);
        matrizDeTransicion.agregarTransicion(4,4,Integer.MAX_VALUE);
        matrizDeTransicion.agregarTransicion(4,5,Integer.MAX_VALUE);
        matrizDeTransicion.agregarTransicion(4,6,Integer.MAX_VALUE);
        matrizDeTransicion.agregarTransicion(4,7,Integer.MAX_VALUE);
        matrizDeTransicion.agregarTransicion(4,8,Integer.MAX_VALUE);
        matrizDeTransicion.agregarTransicion(4,9,Integer.MAX_VALUE);
        matrizDeTransicion.agregarTransicion(4,10,Integer.MAX_VALUE);
        matrizDeTransicion.agregarTransicion(4,11,Integer.MAX_VALUE);
        matrizDeTransicion.agregarTransicion(4,12,Integer.MAX_VALUE);
        matrizDeTransicion.agregarTransicion(4,13,Integer.MAX_VALUE);
        matrizDeTransicion.agregarTransicion(4,14,Integer.MAX_VALUE);
        matrizDeTransicion.agregarTransicion(4,15,Integer.MAX_VALUE);
        matrizDeTransicion.agregarTransicion(4,16,Integer.MAX_VALUE);
        matrizDeTransicion.agregarTransicion(4,17,Integer.MAX_VALUE);
        matrizDeTransicion.agregarTransicion(4,18,Integer.MAX_VALUE);
        matrizDeTransicion.agregarTransicion(4,19,Integer.MAX_VALUE);
        matrizDeTransicion.agregarTransicion(4,20,Integer.MAX_VALUE);
        matrizDeTransicion.agregarTransicion(4,21,Integer.MAX_VALUE);
        matrizDeTransicion.agregarTransicion(4,22,Integer.MAX_VALUE);
        matrizDeTransicion.agregarTransicion(4,23,Integer.MAX_VALUE);
        matrizDeTransicion.agregarTransicion(4,24,Integer.MAX_VALUE);
        matrizDeTransicion.agregarTransicion(4,25,Integer.MAX_VALUE);
        //FILA: 5
        //matrizDeTransicion.agregarTransicion(5,0,);
        //matrizDeTransicion.agregarTransicion(5,1,);
        //matrizDeTransicion.agregarTransicion(5,2,);
        //matrizDeTransicion.agregarTransicion(5,3,);
        //matrizDeTransicion.agregarTransicion(5,4,);
        matrizDeTransicion.agregarTransicion(5,5,6);
        matrizDeTransicion.agregarTransicion(5,6,6);
        //matrizDeTransicion.agregarTransicion(5,7,);
        //matrizDeTransicion.agregarTransicion(5,8,);
        //matrizDeTransicion.agregarTransicion(5,9,);
        //matrizDeTransicion.agregarTransicion(5,10,);
        //matrizDeTransicion.agregarTransicion(5,11,);
        //matrizDeTransicion.agregarTransicion(5,12,);
        //matrizDeTransicion.agregarTransicion(5,13,);
        //matrizDeTransicion.agregarTransicion(5,14,);
        //matrizDeTransicion.agregarTransicion(5,15,);
        //matrizDeTransicion.agregarTransicion(5,16,);
        //matrizDeTransicion.agregarTransicion(5,17,);
        //matrizDeTransicion.agregarTransicion(5,18,);
        //matrizDeTransicion.agregarTransicion(5,19,);
        //matrizDeTransicion.agregarTransicion(5,20,);
        //matrizDeTransicion.agregarTransicion(5,21,);
        //matrizDeTransicion.agregarTransicion(5,22,);
        //matrizDeTransicion.agregarTransicion(5,23,);
        //matrizDeTransicion.agregarTransicion(5,24,);
        //matrizDeTransicion.agregarTransicion(5,25,);
        //FILA: 6
        matrizDeTransicion.agregarTransicion(6,0,7);
        //matrizDeTransicion.agregarTransicion(6,1,);
        //matrizDeTransicion.agregarTransicion(6,2,);
        //matrizDeTransicion.agregarTransicion(6,3,);
        //matrizDeTransicion.agregarTransicion(6,4,);
        //matrizDeTransicion.agregarTransicion(6,5,);
        //matrizDeTransicion.agregarTransicion(6,6,);
        //matrizDeTransicion.agregarTransicion(6,7,);
        //matrizDeTransicion.agregarTransicion(6,8,);
        //matrizDeTransicion.agregarTransicion(6,9,);
        //matrizDeTransicion.agregarTransicion(6,10,);
        //matrizDeTransicion.agregarTransicion(6,11,);
        //matrizDeTransicion.agregarTransicion(6,12,);
        //matrizDeTransicion.agregarTransicion(6,13,);
        //matrizDeTransicion.agregarTransicion(6,14,);
        //matrizDeTransicion.agregarTransicion(6,15,);
        //matrizDeTransicion.agregarTransicion(6,16,);
        //matrizDeTransicion.agregarTransicion(6,17,);
        //matrizDeTransicion.agregarTransicion(6,18,);
        //matrizDeTransicion.agregarTransicion(6,19,);
        //matrizDeTransicion.agregarTransicion(6,20,);
        //matrizDeTransicion.agregarTransicion(6,21,);
        //matrizDeTransicion.agregarTransicion(6,22,);
        //matrizDeTransicion.agregarTransicion(6,23,);
        //matrizDeTransicion.agregarTransicion(6,24,);
        //matrizDeTransicion.agregarTransicion(6,25,);
        //FILA: 7
        matrizDeTransicion.agregarTransicion(7,0,7);
        matrizDeTransicion.agregarTransicion(7,1,Integer.MAX_VALUE);
        matrizDeTransicion.agregarTransicion(7,2,Integer.MAX_VALUE);
        matrizDeTransicion.agregarTransicion(7,3,Integer.MAX_VALUE);
        matrizDeTransicion.agregarTransicion(7,4,Integer.MAX_VALUE);
        matrizDeTransicion.agregarTransicion(7,5,Integer.MAX_VALUE);
        matrizDeTransicion.agregarTransicion(7,6,Integer.MAX_VALUE);
        matrizDeTransicion.agregarTransicion(7,7,Integer.MAX_VALUE);
        matrizDeTransicion.agregarTransicion(7,8,Integer.MAX_VALUE);
        matrizDeTransicion.agregarTransicion(7,9,Integer.MAX_VALUE);
        matrizDeTransicion.agregarTransicion(7,10,Integer.MAX_VALUE);
        matrizDeTransicion.agregarTransicion(7,11,Integer.MAX_VALUE);
        matrizDeTransicion.agregarTransicion(7,12,Integer.MAX_VALUE);
        matrizDeTransicion.agregarTransicion(7,13,Integer.MAX_VALUE);
        matrizDeTransicion.agregarTransicion(7,14,Integer.MAX_VALUE);
        matrizDeTransicion.agregarTransicion(7,15,Integer.MAX_VALUE);
        matrizDeTransicion.agregarTransicion(7,16,Integer.MAX_VALUE);
        matrizDeTransicion.agregarTransicion(7,17,Integer.MAX_VALUE);
        matrizDeTransicion.agregarTransicion(7,18,Integer.MAX_VALUE);
        matrizDeTransicion.agregarTransicion(7,19,Integer.MAX_VALUE);
        matrizDeTransicion.agregarTransicion(7,20,Integer.MAX_VALUE);
        matrizDeTransicion.agregarTransicion(7,21,Integer.MAX_VALUE);
        matrizDeTransicion.agregarTransicion(7,22,Integer.MAX_VALUE);
        matrizDeTransicion.agregarTransicion(7,23,Integer.MAX_VALUE);
        matrizDeTransicion.agregarTransicion(7,24,Integer.MAX_VALUE);
        matrizDeTransicion.agregarTransicion(7,25,Integer.MAX_VALUE);
        //FILA: 8
        matrizDeTransicion.agregarTransicion(8,0,Integer.MAX_VALUE);
        matrizDeTransicion.agregarTransicion(8,1,Integer.MAX_VALUE);
        matrizDeTransicion.agregarTransicion(8,2,Integer.MAX_VALUE);
        matrizDeTransicion.agregarTransicion(8,3,Integer.MAX_VALUE);
        matrizDeTransicion.agregarTransicion(8,4,Integer.MAX_VALUE);
        matrizDeTransicion.agregarTransicion(8,5,Integer.MAX_VALUE);
        matrizDeTransicion.agregarTransicion(8,6,Integer.MAX_VALUE);
        matrizDeTransicion.agregarTransicion(8,7,Integer.MAX_VALUE);
        matrizDeTransicion.agregarTransicion(8,8,Integer.MAX_VALUE);
        matrizDeTransicion.agregarTransicion(8,9,9);
        matrizDeTransicion.agregarTransicion(8,10,Integer.MAX_VALUE);
        matrizDeTransicion.agregarTransicion(8,11,Integer.MAX_VALUE);
        matrizDeTransicion.agregarTransicion(8,12,Integer.MAX_VALUE);
        matrizDeTransicion.agregarTransicion(8,13,Integer.MAX_VALUE);
        matrizDeTransicion.agregarTransicion(8,14,Integer.MAX_VALUE);
        matrizDeTransicion.agregarTransicion(8,15,Integer.MAX_VALUE);
        matrizDeTransicion.agregarTransicion(8,16,Integer.MAX_VALUE);
        matrizDeTransicion.agregarTransicion(8,17,Integer.MAX_VALUE);
        matrizDeTransicion.agregarTransicion(8,18,Integer.MAX_VALUE);
        matrizDeTransicion.agregarTransicion(8,19,Integer.MAX_VALUE);
        matrizDeTransicion.agregarTransicion(8,20,Integer.MAX_VALUE);
        matrizDeTransicion.agregarTransicion(8,21,Integer.MAX_VALUE);
        matrizDeTransicion.agregarTransicion(8,22,Integer.MAX_VALUE);
        matrizDeTransicion.agregarTransicion(8,23,Integer.MAX_VALUE);
        matrizDeTransicion.agregarTransicion(8,24,Integer.MAX_VALUE);
        matrizDeTransicion.agregarTransicion(8,25,Integer.MAX_VALUE);
        //FILA: 9
        matrizDeTransicion.agregarTransicion(9,0,9);
        matrizDeTransicion.agregarTransicion(9,1,9);
        matrizDeTransicion.agregarTransicion(9,2,9);
        matrizDeTransicion.agregarTransicion(9,3,9);
        matrizDeTransicion.agregarTransicion(9,4,9);
        matrizDeTransicion.agregarTransicion(9,5,9);
        matrizDeTransicion.agregarTransicion(9,6,9);
        matrizDeTransicion.agregarTransicion(9,7,9);
        matrizDeTransicion.agregarTransicion(9,8,9);
        matrizDeTransicion.agregarTransicion(9,9,10);
        matrizDeTransicion.agregarTransicion(9,10,9);
        matrizDeTransicion.agregarTransicion(9,11,9);
        matrizDeTransicion.agregarTransicion(9,12,9);
        matrizDeTransicion.agregarTransicion(9,13,9);
        matrizDeTransicion.agregarTransicion(9,14,9);
        matrizDeTransicion.agregarTransicion(9,15,9);
        matrizDeTransicion.agregarTransicion(9,16,9);
        matrizDeTransicion.agregarTransicion(9,17,9);
        matrizDeTransicion.agregarTransicion(9,18,9);
        matrizDeTransicion.agregarTransicion(9,19,9);
        matrizDeTransicion.agregarTransicion(9,20,9);
        matrizDeTransicion.agregarTransicion(9,21,9);
        matrizDeTransicion.agregarTransicion(9,22,9);
        matrizDeTransicion.agregarTransicion(9,23,9);
        matrizDeTransicion.agregarTransicion(9,24,9);
        matrizDeTransicion.agregarTransicion(9,25,9);
        //FILA: 10
        matrizDeTransicion.agregarTransicion(10,0,9);
        matrizDeTransicion.agregarTransicion(10,1,9);
        matrizDeTransicion.agregarTransicion(10,2,9);
        matrizDeTransicion.agregarTransicion(10,3,9);
        matrizDeTransicion.agregarTransicion(10,4,9);
        matrizDeTransicion.agregarTransicion(10,5,9);
        matrizDeTransicion.agregarTransicion(10,6,9);
        matrizDeTransicion.agregarTransicion(10,7,9);
        matrizDeTransicion.agregarTransicion(10,8,0);
        matrizDeTransicion.agregarTransicion(10,9,10);
        matrizDeTransicion.agregarTransicion(10,10,9);
        matrizDeTransicion.agregarTransicion(10,11,9);
        matrizDeTransicion.agregarTransicion(10,12,9);
        matrizDeTransicion.agregarTransicion(10,13,9);
        matrizDeTransicion.agregarTransicion(10,14,9);
        matrizDeTransicion.agregarTransicion(10,15,9);
        matrizDeTransicion.agregarTransicion(10,16,9);
        matrizDeTransicion.agregarTransicion(10,17,9);
        matrizDeTransicion.agregarTransicion(10,18,9);
        matrizDeTransicion.agregarTransicion(10,19,9);
        matrizDeTransicion.agregarTransicion(10,20,9);
        matrizDeTransicion.agregarTransicion(10,21,9);
        matrizDeTransicion.agregarTransicion(10,22,9);
        matrizDeTransicion.agregarTransicion(10,23,9);
        matrizDeTransicion.agregarTransicion(10,24,9);
        matrizDeTransicion.agregarTransicion(10,25,9);
        //FILA: 11
        matrizDeTransicion.agregarTransicion(11,0,Integer.MAX_VALUE);
        matrizDeTransicion.agregarTransicion(11,1,Integer.MAX_VALUE);
        matrizDeTransicion.agregarTransicion(11,2,Integer.MAX_VALUE);
        matrizDeTransicion.agregarTransicion(11,3,Integer.MAX_VALUE);
        matrizDeTransicion.agregarTransicion(11,4,Integer.MAX_VALUE);
        matrizDeTransicion.agregarTransicion(11,5,Integer.MAX_VALUE);
        matrizDeTransicion.agregarTransicion(11,6,Integer.MAX_VALUE);
        matrizDeTransicion.agregarTransicion(11,7,Integer.MAX_VALUE);
        matrizDeTransicion.agregarTransicion(11,8,Integer.MAX_VALUE);
        matrizDeTransicion.agregarTransicion(11,9,Integer.MAX_VALUE);
        matrizDeTransicion.agregarTransicion(11,10,Integer.MAX_VALUE);
        matrizDeTransicion.agregarTransicion(11,11,Integer.MAX_VALUE);
        matrizDeTransicion.agregarTransicion(11,12,Integer.MAX_VALUE);
        matrizDeTransicion.agregarTransicion(11,13,Integer.MAX_VALUE);
        matrizDeTransicion.agregarTransicion(11,14,Integer.MAX_VALUE);
        matrizDeTransicion.agregarTransicion(11,15,Integer.MAX_VALUE);
        matrizDeTransicion.agregarTransicion(11,16,Integer.MAX_VALUE);
        matrizDeTransicion.agregarTransicion(11,17,Integer.MAX_VALUE);
        matrizDeTransicion.agregarTransicion(11,18,Integer.MAX_VALUE);
        matrizDeTransicion.agregarTransicion(11,19,Integer.MAX_VALUE);
        matrizDeTransicion.agregarTransicion(11,20,Integer.MAX_VALUE);
        matrizDeTransicion.agregarTransicion(11,21,Integer.MAX_VALUE);
        matrizDeTransicion.agregarTransicion(11,22,Integer.MAX_VALUE);
        matrizDeTransicion.agregarTransicion(11,23,Integer.MAX_VALUE);
        matrizDeTransicion.agregarTransicion(11,24,Integer.MAX_VALUE);
        matrizDeTransicion.agregarTransicion(11,25,Integer.MAX_VALUE);
        //FILA: 12
        matrizDeTransicion.agregarTransicion(12,0,Integer.MAX_VALUE);
        matrizDeTransicion.agregarTransicion(12,1,Integer.MAX_VALUE);
        matrizDeTransicion.agregarTransicion(12,2,Integer.MAX_VALUE);
        matrizDeTransicion.agregarTransicion(12,3,Integer.MAX_VALUE);
        matrizDeTransicion.agregarTransicion(12,4,Integer.MAX_VALUE);
        matrizDeTransicion.agregarTransicion(12,5,Integer.MAX_VALUE);
        matrizDeTransicion.agregarTransicion(12,6,Integer.MAX_VALUE);
        matrizDeTransicion.agregarTransicion(12,7,Integer.MAX_VALUE);
        matrizDeTransicion.agregarTransicion(12,8,Integer.MAX_VALUE);
        matrizDeTransicion.agregarTransicion(12,9,Integer.MAX_VALUE);
        matrizDeTransicion.agregarTransicion(12,10,Integer.MAX_VALUE);
        matrizDeTransicion.agregarTransicion(12,11,Integer.MAX_VALUE);
        matrizDeTransicion.agregarTransicion(12,12,Integer.MAX_VALUE);
        matrizDeTransicion.agregarTransicion(12,13,Integer.MAX_VALUE);
        matrizDeTransicion.agregarTransicion(12,14,Integer.MAX_VALUE);
        matrizDeTransicion.agregarTransicion(12,15,Integer.MAX_VALUE);
        matrizDeTransicion.agregarTransicion(12,16,Integer.MAX_VALUE);
        matrizDeTransicion.agregarTransicion(12,17,Integer.MAX_VALUE);
        matrizDeTransicion.agregarTransicion(12,18,Integer.MAX_VALUE);
        matrizDeTransicion.agregarTransicion(12,19,Integer.MAX_VALUE);
        matrizDeTransicion.agregarTransicion(12,20,Integer.MAX_VALUE);
        matrizDeTransicion.agregarTransicion(12,21,Integer.MAX_VALUE);
        matrizDeTransicion.agregarTransicion(12,22,Integer.MAX_VALUE);
        matrizDeTransicion.agregarTransicion(12,23,Integer.MAX_VALUE);
        matrizDeTransicion.agregarTransicion(12,24,Integer.MAX_VALUE);
        matrizDeTransicion.agregarTransicion(12,25,Integer.MAX_VALUE);
        //FILA: 13
        //matrizDeTransicion.agregarTransicion(13,0,);
        //matrizDeTransicion.agregarTransicion(13,1,);
        //matrizDeTransicion.agregarTransicion(13,2,);
        //matrizDeTransicion.agregarTransicion(13,3,);
        //matrizDeTransicion.agregarTransicion(13,4,);
        //matrizDeTransicion.agregarTransicion(13,5,);
        //matrizDeTransicion.agregarTransicion(13,6,);
        //matrizDeTransicion.agregarTransicion(13,7,);
        //matrizDeTransicion.agregarTransicion(13,8,);
        //matrizDeTransicion.agregarTransicion(13,9,);
        //matrizDeTransicion.agregarTransicion(13,10,);
        //matrizDeTransicion.agregarTransicion(13,11,);
        //matrizDeTransicion.agregarTransicion(13,12,);
        matrizDeTransicion.agregarTransicion(13,13,Integer.MAX_VALUE);
        //matrizDeTransicion.agregarTransicion(13,14,);
        //matrizDeTransicion.agregarTransicion(13,15,);
        //matrizDeTransicion.agregarTransicion(13,16,);
        //matrizDeTransicion.agregarTransicion(13,17,);
        //matrizDeTransicion.agregarTransicion(13,18,);
        //matrizDeTransicion.agregarTransicion(13,19,);
        //matrizDeTransicion.agregarTransicion(13,20,);
        //matrizDeTransicion.agregarTransicion(13,21,);
        //matrizDeTransicion.agregarTransicion(13,22,);
        //matrizDeTransicion.agregarTransicion(13,23,);
        //matrizDeTransicion.agregarTransicion(13,24,);
        //matrizDeTransicion.agregarTransicion(13,25,);
        //FILA: 14
        matrizDeTransicion.agregarTransicion(14,0,Integer.MAX_VALUE);
        matrizDeTransicion.agregarTransicion(14,1,Integer.MAX_VALUE);
        matrizDeTransicion.agregarTransicion(14,2,Integer.MAX_VALUE);
        matrizDeTransicion.agregarTransicion(14,3,Integer.MAX_VALUE);
        matrizDeTransicion.agregarTransicion(14,4,Integer.MAX_VALUE);
        matrizDeTransicion.agregarTransicion(14,5,Integer.MAX_VALUE);
        matrizDeTransicion.agregarTransicion(14,6,Integer.MAX_VALUE);
        matrizDeTransicion.agregarTransicion(14,7,Integer.MAX_VALUE);
        matrizDeTransicion.agregarTransicion(14,8,Integer.MAX_VALUE);
        matrizDeTransicion.agregarTransicion(14,9,Integer.MAX_VALUE);
        matrizDeTransicion.agregarTransicion(14,10,Integer.MAX_VALUE);
        matrizDeTransicion.agregarTransicion(14,11,Integer.MAX_VALUE);
        matrizDeTransicion.agregarTransicion(14,12,Integer.MAX_VALUE);
        matrizDeTransicion.agregarTransicion(14,13,Integer.MAX_VALUE);
        matrizDeTransicion.agregarTransicion(14,14,Integer.MAX_VALUE);
        matrizDeTransicion.agregarTransicion(14,15,Integer.MAX_VALUE);
        matrizDeTransicion.agregarTransicion(14,16,Integer.MAX_VALUE);
        matrizDeTransicion.agregarTransicion(14,17,Integer.MAX_VALUE);
        matrizDeTransicion.agregarTransicion(14,18,Integer.MAX_VALUE);
        matrizDeTransicion.agregarTransicion(14,19,Integer.MAX_VALUE);
        matrizDeTransicion.agregarTransicion(14,20,Integer.MAX_VALUE);
        matrizDeTransicion.agregarTransicion(14,21,Integer.MAX_VALUE);
        matrizDeTransicion.agregarTransicion(14,22,Integer.MAX_VALUE);
        matrizDeTransicion.agregarTransicion(14,23,Integer.MAX_VALUE);
        matrizDeTransicion.agregarTransicion(14,24,Integer.MAX_VALUE);
        matrizDeTransicion.agregarTransicion(14,25,Integer.MAX_VALUE);
        //FILA: 15
        matrizDeTransicion.agregarTransicion(15,0,Integer.MAX_VALUE);
        matrizDeTransicion.agregarTransicion(15,1,Integer.MAX_VALUE);
        matrizDeTransicion.agregarTransicion(15,2,Integer.MAX_VALUE);
        matrizDeTransicion.agregarTransicion(15,3,Integer.MAX_VALUE;
        matrizDeTransicion.agregarTransicion(15,4,15);
        matrizDeTransicion.agregarTransicion(15,5,Integer.MAX_VALUE);
        matrizDeTransicion.agregarTransicion(15,6,Integer.MAX_VALUE);
        matrizDeTransicion.agregarTransicion(15,7,Integer.MAX_VALUE);
        matrizDeTransicion.agregarTransicion(15,8,Integer.MAX_VALUE);
        matrizDeTransicion.agregarTransicion(15,9,Integer.MAX_VALUE);
        matrizDeTransicion.agregarTransicion(15,10,Integer.MAX_VALUE);
        matrizDeTransicion.agregarTransicion(15,11,Integer.MAX_VALUE);
        matrizDeTransicion.agregarTransicion(15,12,Integer.MAX_VALUE);
        matrizDeTransicion.agregarTransicion(15,13,Integer.MAX_VALUE);
        matrizDeTransicion.agregarTransicion(15,14,Integer.MAX_VALUE);
        matrizDeTransicion.agregarTransicion(15,15,Integer.MAX_VALUE);
        matrizDeTransicion.agregarTransicion(15,16,Integer.MAX_VALUE);
        matrizDeTransicion.agregarTransicion(15,17,Integer.MAX_VALUE);
        matrizDeTransicion.agregarTransicion(15,18,Integer.MAX_VALUE);
        matrizDeTransicion.agregarTransicion(15,19,Integer.MAX_VALUE);
        matrizDeTransicion.agregarTransicion(15,20,Integer.MAX_VALUE);
        matrizDeTransicion.agregarTransicion(15,21,Integer.MAX_VALUE);
        matrizDeTransicion.agregarTransicion(15,22,Integer.MAX_VALUE);
        matrizDeTransicion.agregarTransicion(15,23,Integer.MAX_VALUE);
        matrizDeTransicion.agregarTransicion(15,24,Integer.MAX_VALUE;
        matrizDeTransicion.agregarTransicion(15,25,Integer.MAX_VALUE);
        //FILA: 16
        matrizDeTransicion.agregarTransicion(16,0,16);
        matrizDeTransicion.agregarTransicion(16,1,16);
        matrizDeTransicion.agregarTransicion(16,2,16);
        matrizDeTransicion.agregarTransicion(16,3,16);
        matrizDeTransicion.agregarTransicion(16,4,16);
        matrizDeTransicion.agregarTransicion(16,5,16);
        matrizDeTransicion.agregarTransicion(16,6,16);
        matrizDeTransicion.agregarTransicion(16,7,16);
        matrizDeTransicion.agregarTransicion(16,8,16);
        matrizDeTransicion.agregarTransicion(16,9,16);
        matrizDeTransicion.agregarTransicion(16,10,16);
        matrizDeTransicion.agregarTransicion(16,11,16);
        matrizDeTransicion.agregarTransicion(16,12,16);
        matrizDeTransicion.agregarTransicion(16,13,16);
        matrizDeTransicion.agregarTransicion(16,14,16);
        matrizDeTransicion.agregarTransicion(16,15,16);
        matrizDeTransicion.agregarTransicion(16,16,16);
        matrizDeTransicion.agregarTransicion(16,17,16);
        matrizDeTransicion.agregarTransicion(16,18,16);
        matrizDeTransicion.agregarTransicion(16,19,16);
        matrizDeTransicion.agregarTransicion(16,20,16);
        matrizDeTransicion.agregarTransicion(16,21,16);
        //matrizDeTransicion.agregarTransicion(16,22,);
        matrizDeTransicion.agregarTransicion(16,23,16);
        matrizDeTransicion.agregarTransicion(16,24,16);
        matrizDeTransicion.agregarTransicion(16,25,Integer.MAX_VALUE);
    }

}
