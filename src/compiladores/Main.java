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
        System.out.println(data);
        System.out.println("LOS TOKEN SON: ");
        AnalizadorLexico lexico = new AnalizadorLexico(data);
        for(int i = 0; i<12; i++){
            System.out.println(lexico.getToken());
        }
        System.out.println(lexico.tablaDeSimbolos.toString());
    }
}
