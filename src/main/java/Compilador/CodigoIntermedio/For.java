package Compilador.CodigoIntermedio;

public class For extends Nodo {

    public For(Nodo izquierdo, Nodo derecho) {
        super(izquierdo, derecho);
    }

    @Override
    public void generarCodigo() {

        //      <etiqueta_for>
        //------------------------------------------------------+ codigo generado por izquierdo.generarCodigo();
        //      condicion                                       + (que es un nodo Comparador)
        //      JF <etiqueta_desapilada>                        +
        //------------------------------------------------------+
        //      ... (bloque del for)
        //      JMP <etiqueta_for>
        //      <etiqueta_desapilada>

        //Se crea la etiqueta_for y se agrega
        String etiqueta_for = crearEtiqueta();
        codigo.append(etiqueta_for);
        codigo.append(":\n");

        //Se genera el codigo de la condicion (izquierdo) y luego el cuerpo del for (derecho)
        izquierdo.generarCodigo();
        derecho.generarCodigo();

        //Se agrega el salto incodicional a la condicion
        codigo.append("JMP ");
        codigo.append(etiqueta_for);
        codigo.append("\n");

        //Se agrega la etiqueta si no se cumple la condicon para salir del bucle
        codigo.append(desapilar());
        codigo.append(":\n");
    }


}
