package Compilador.CodigoIntermedio;

public class CuerpoIf extends Nodo {

    public CuerpoIf(Nodo izquierdo, Nodo derecho) {
        super(izquierdo, derecho);
    }

    @Override
    public void generarCodigo() {
        //Codigo assembler de un IF sin ELSE
        //      condicion
        //      JF <etiqueta_desapilada>
        //      ... (codigo THEN)           <------ izquierdo.generarCodigo();
        //      <etiqueta_desapilada>       <------ agregarEtiqueta();

        //Genero el codigo del THEN (que esta a la izquierda)
        izquierdo.generarCodigo();

        //Ahora si el derecho no es null hay ELSE
        if (derecho != null) {
            generarCodigoElse();
        }

        //Agrego la etiqueta del fin del if (haya o no ELSE)
        agregarEtiqueta();

    }

    /**
     * Genera el codigo si hay else.
     */
    private void generarCodigoElse() {
        //Creo la etiqueta y se agrega el salto incondicional
        //del then a esta etiqueta (seria la que iria al final)
        //Codigo assembler de un IF con ELSE
        //      condicion
        //      JF <etiqueta_desapilada>
        //      ... (codigo THEN)       <------- izquierdo.generarCodigo();
        //------------------------------------------------------------------------------+
        //      JMP <etiqueta>                                                          +
        //      <etiqueta_desapilada>                                                   +  este codigo es esta funcion
        //      ... (codigo ELSE)       <------- derecho.generarCodigo();               +
        //------------------------------------------------------------------------------+
        //      <etiqueta>              <------- esta se agrega por fuera de este metodo

        String etiqueta = crearEtiqueta();
        codigo.append("JMP ");
        codigo.append(etiqueta);
        codigo.append("\n");

        // Se agrega la etiqueta que se habia hecho en la codicion
        codigo.append(desapilar());
        codigo.append(":\n");
        apilar(etiqueta);

        // Se genera el codigo del ELSE
        derecho.generarCodigo();
    }

    /**
     * Agrega la ultima etiqueta de la pila.
     */
    private void agregarEtiqueta() {
        codigo.append(desapilar());
        codigo.append(":\n");
    }

}
