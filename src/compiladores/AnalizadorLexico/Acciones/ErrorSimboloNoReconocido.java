package compiladores.AnalizadorLexico.Acciones;

import compiladores.AnalizadorLexico.AnalizadorLexico;

public class ErrorSimboloNoReconocido extends AccionSemantica {
    public ErrorSimboloNoReconocido(AnalizadorLexico lexico) {
        super(lexico);
    }

    @Override
    public void accion(char simbolo) {
        System.out.println("Se encontro un simbolo desconocido");
        //activar flag de error

    }
}
