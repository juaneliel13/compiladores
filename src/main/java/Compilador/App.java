package Compilador;

import Compilador.Lexico.AnalizadorLexico;
import Compilador.Sintactico.Parser;
import Compilador.Utilidad.Logger;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Paths;

public class App {
    public static void main(String[] args) {
        boolean imprime = true;
        if (args.length != 0) {
            File file = new File(args[0]);
            String data = "";
            Logger logger = Logger.getInstance();
            Logger.setFilename(file);
            try {
                data = new String(Files.readAllBytes(Paths.get(file.getName())));
            } catch (Exception e) {
                e.printStackTrace();
            }
            AnalizadorLexico lexico = new AnalizadorLexico(data);
            /*for(int i=-1; i!=0;i=lexico.getToken()){
                System.out.println(i);
            }*/
            Parser parser = new Parser(lexico);
            System.out.println("RETURN DEL YYPARSE: " + parser.yyparse_publico());
            System.out.println(lexico.tablaDeSimbolos.toString());
//            System.out.println(parser.raiz.imprimision());
            if (imprime) {
                logger.dumpErrors();
                logger.dumpEvents();
                logger.dumpWarnings();
            }
        } else {
            System.out.println("Se esperaban argumentos :c ");
        }
    }
}
