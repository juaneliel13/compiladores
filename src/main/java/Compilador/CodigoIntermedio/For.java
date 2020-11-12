package Compilador.CodigoIntermedio;

public class For extends Nodo {

    public For(Nodo izquierdo, Nodo derecho) {
        super(izquierdo, derecho);
    }

    @Override
    public void generarCodigo() {
        String etiqueta_for = crearEtiqueta();
        codigo.append(etiqueta_for);
        codigo.append(":\n");
        izquierdo.generarCodigo();
        derecho.generarCodigo();
        codigo.append("JMP ");
        codigo.append(etiqueta_for);
        codigo.append("\n");
        codigo.append(desapilar());
        codigo.append(":\n");




    }
}
