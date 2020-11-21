package Compilador.CodigoIntermedio;

import Compilador.Lexico.Tipos;

import java.util.List;

public class Llamada extends Nodo {

    String ref;
    List<String> parametros_reales;
    List<Parametro> parametros_formales;

    public Llamada(String ref, List<String> parametros_reales) {
        this.ref = ref;
        this.parametros_reales = parametros_reales;
        parametros_formales = (List<Parametro>) lex.tablaDeSimbolos.get(ref).get("Parametros");
    }

    @Override
    public void generarCodigo() {

        generarCheckeoRecursion();
        generarPasajeCopiaValor();
        generarCall();
        generarPasajeCopiaValorResultado();

    }

    /**
     * Genera el codigo que chekea que el flag _FLAG_<nombre_proc> no
     * tenga un 1 (si este tiene 1 quiere decir que se esta ejecutando)
     * luego del chekeo se setea en 1 el flag
     */
    private void generarCheckeoRecursion() {
        codigo.append("MOV AX, _FLAG_");
        codigo.append(ref);
        codigo.append("\nCMP AX, 1\nJE _RECURSION\nMOV _FLAG_");
        codigo.append(ref);
        codigo.append(", 1\n");
    }

    /**
     * Genera los movs de los parametros reales a los
     * parametros formales
     */
    private void generarPasajeCopiaValor() {
        if (parametros_reales != null) {
            for (int i = 0; i < parametros_formales.size(); i++) {
                if (parametros_formales.get(i).tipo == Tipos.FLOAT) {
                    codigo.append("MOV EAX, _");
                } else {
                    codigo.append("MOV AX, _");
                }
                codigo.append(parametros_reales.get(i));
                codigo.append("\nMOV _");
                codigo.append(parametros_formales.get(i).idRef);
                if (parametros_formales.get(i).tipo == Tipos.FLOAT) {
                    codigo.append(", EAX\n");
                } else {
                    codigo.append(", AX\n");
                }
            }
        }
    }

    /**
     * Genera el codigo que realiza el CALL del proc luego del
     * CALL se setea el flag en 0
     */
    private void generarCall() {
        codigo.append("CALL _");
        codigo.append(ref);
        codigo.append("\nMOV _FLAG_");
        codigo.append(ref);
        codigo.append(", 0\n");
    }

    /**
     * Genera el codigo para mover los parametros formales a los
     * parametros reales, solo de los parametros que tiene VAR
     */
    private void generarPasajeCopiaValorResultado() {
        if (parametros_reales != null) {
            for (int i = 0; i < parametros_formales.size(); i++) {
                if (parametros_formales.get(i).tipoPasaje == "VAR") {
                    if (parametros_formales.get(i).tipo == Tipos.FLOAT) {
                        codigo.append("MOV EAX, _");
                    } else {
                        codigo.append("MOV AX, _");
                    }
                    codigo.append(parametros_formales.get(i).idRef);
                    codigo.append("\nMOV _");
                    codigo.append(parametros_reales.get(i));
                    if (parametros_formales.get(i).tipo == Tipos.FLOAT) {
                        codigo.append(", EAX\n");
                    } else {
                        codigo.append(", AX\n");
                    }
                }
            }
        }
    }
}
