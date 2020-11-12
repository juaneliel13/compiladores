package Compilador.CodigoIntermedio;

public class CuerpoIf extends Nodo {

    public CuerpoIf(Nodo izquierdo, Nodo derecho) {
        super(izquierdo, derecho);
    }

    @Override
    public void generarCodigo() {
        izquierdo.generarCodigo();
        if (derecho != null) {
            String etiqueta = crearEtiqueta();
            codigo.append("JMP ");
            codigo.append(etiqueta);
            codigo.append("\n");
            codigo.append(desapilar());
            codigo.append(":\n");
            apilar(etiqueta);
            derecho.generarCodigo();
        }
        codigo.append(desapilar());
        codigo.append(":\n");


    }
}
