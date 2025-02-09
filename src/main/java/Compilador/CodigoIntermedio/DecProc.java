package Compilador.CodigoIntermedio;

import Compilador.Lexico.Tipos;

import java.util.HashMap;

public class DecProc extends Nodo {

    public static StringBuilder procs = new StringBuilder();

    String ref;

    public DecProc(Nodo izquierdo, Nodo derecho, String ref) {
        super(izquierdo, derecho);
        this.ref = ref;
    }

    @Override
    public void generarCodigo() {

        //Se crea un nuevo StringBuilder para guardar el codigo de este PROC
        StringBuilder aux = new StringBuilder();
        StringBuilder save_nodo = Nodo.codigo;
        Nodo.codigo = aux;
        
        //Genero la etiqueta del procedimiento
        generarEtiquetaProc();

        //Genero el codigo del procedimiento
        izquierdo.generarCodigo();

        //Agrego el RET
        generarRet();

        //Agrego este procedimiento al procs
        procs.append(aux.toString());

        //Restauro el StringBuilder del nodo
        Nodo.codigo = save_nodo;
    }

    /**
     * Genera la etiqueta del procedimiento
     */
    private void generarEtiquetaProc() {
        codigo.append("_");
        codigo.append(ref);
        codigo.append(" PROC\n");
    }

    /**
     * Genera el RET
     */
    private void generarRet() {
        codigo.append("RET\n");
        codigo.append("_");
        codigo.append(ref);
        codigo.append(" ENDP\n");
    }

}