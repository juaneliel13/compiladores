package Compilador;

import Compilador.Lexico.AnalizadorLexico;
import Compilador.Sintactico.Parser;

import java.nio.file.Files;
import java.nio.file.Paths;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main(String[] args) {
        String fileName;
        fileName = args[0];
        String data = "";
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
    }
}
