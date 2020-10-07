package Compilador;

import Compilador.Lexico.AnalizadorLexico;
import Compilador.Sintactico.Parser;
import Compilador.Utilidad.Logger;

import java.nio.file.Files;
import java.nio.file.Paths;


public class App {
    public static void main(String[] args) {
        String fileName;
        fileName = args[0];
        String data = "";
        Logger logger= Logger.getInstance();
        Logger.setFilename(fileName);
        try {
            data = new String(Files.readAllBytes(Paths.get(fileName)));
        } catch (Exception e) {
            e.printStackTrace();
        }
        AnalizadorLexico lexico = new AnalizadorLexico(data);
        /*for(int i=-1; i!=0;i=lexico.getToken()){

        }*/
        Parser parser = new Parser(lexico);
        System.out.println("RETURN DEL YYPARSE: " + parser.yyparse_publico());
        System.out.println(lexico.tablaDeSimbolos.toString());
        logger.dumpErrors();
        logger.dumpEvents();
        logger.dumpWarnings();
    }
}
