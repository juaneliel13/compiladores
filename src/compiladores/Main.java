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
        AnalizadorLexico lexic = new AnalizadorLexico(data);
        for(int i = 0; i<12; i++){
            System.out.println(lexic.getToken());
        }
    }
}
