package Compilador.CodigoIntermedio;

import Compilador.Lexico.Tipos;

import java.util.List;

public class Llamada extends Nodo{

    String ref;
    List<String> parametros_reales;
    List<Parametro> parametros_formales;
    public Llamada(String ref, List<String> parametros_reales){
        this.ref=ref;
        this.parametros_reales=parametros_reales;
        parametros_formales= (List<Parametro>) lex.tablaDeSimbolos.get(ref).get("Parametros");
    }
    @Override
    public void generarCodigo() {
        codigo.append("MOV AX,_FLAG_");
        codigo.append(ref);
        codigo.append("\nCMP AX, 1\nJE _RECURSION\n");
        codigo.append("MOV _FLAG_");
        codigo.append(ref);
        codigo.append(", 1\n");
        if(parametros_reales!=null) {
            for (int i = 0; i < parametros_formales.size(); i++) {
                if (parametros_formales.get(i).tipo == Tipos.FLOAT)
                    codigo.append("MOV EAX,_");
                else
                    codigo.append("MOV AX,_");
                codigo.append(parametros_reales.get(i));
                codigo.append("\n");
                codigo.append("MOV _");
                codigo.append(parametros_formales.get(i).idRef);
                if (parametros_formales.get(i).tipo == Tipos.FLOAT)
                    codigo.append(",EAX");
                else
                    codigo.append(",AX");

                codigo.append("\n");
            }
        }
        codigo.append("CALL ");
        codigo.append("_"+ref);
        codigo.append("\n");
        codigo.append("MOV _FLAG_");
        codigo.append(ref);
        codigo.append(", 0\n");
        if(parametros_reales!=null) {
            for (int i = 0; i < parametros_formales.size(); i++) {
                if (parametros_formales.get(i).tipoPasaje == "VAR") {
                    if (parametros_formales.get(i).tipo == Tipos.FLOAT)
                        codigo.append("MOV EAX,_");
                    else
                        codigo.append("MOV AX,_");

                    codigo.append(parametros_formales.get(i).idRef);
                    codigo.append("\n");
                    codigo.append("MOV _");
                    codigo.append(parametros_reales.get(i));
                    if (parametros_formales.get(i).tipo == Tipos.FLOAT)
                        codigo.append(",EAX");
                    else
                        codigo.append(",AX");

                    codigo.append("\n");
                }
            }
        }

    }
}
