package Compilador.CodigoIntermedio;

public class If extends Nodo {

    public If(Nodo izquierdo, Nodo derecho) {
        super(izquierdo, derecho);
    }

    @Override
    public String generarCodigo() {
        //Este codigo deberia de llamar a izquierda luego a derecha
        //y generar un BF a una nueva etiqueta (si hay else seria a
        //este agregaria la etiqueta luego del else, si no hay else
        //se agregaria la etiqueta luego del codigo del then)
        return null;
    }
}
