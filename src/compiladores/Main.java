package compiladores;

import compiladores.AnalizadorLexico.AnalizadorLexico;

import java.nio.file.Files;
import java.nio.file.Paths;

public class Main {
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
        Parser parser = new Parser(lexico);
        System.out.println("RETURN DEL YYPARSE: " + parser.yyparse());
        System.out.println(lexico.tablaDeSimbolos.toString());
    }
}
