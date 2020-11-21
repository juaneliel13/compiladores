package Compilador;

import Compilador.CodigoIntermedio.*;
import Compilador.Lexico.AnalizadorLexico;
import Compilador.Lexico.Tipos;
import Compilador.Sintactico.Parser;
import Compilador.Utilidad.Logger;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;

public class App {
    public static void main(String[] args) throws IOException {
        boolean imprime = true;
        if (args.length != 0) {
            File file = new File(args[0]);
            String data = "";
            Logger logger = Logger.getInstance();
            Logger.setFilename(file);
            try {
                data = new String(Files.readAllBytes(Paths.get(file.getAbsolutePath())));
            } catch (Exception e) {
                e.printStackTrace();
            }
            AnalizadorLexico lexico = new AnalizadorLexico(data);
            /*for(int i=-1; i!=0;i=lexico.getToken()){
                System.out.println(i);
            }*/
            Parser parser = new Parser(lexico);
            Nodo.setLexico(lexico);
            Logger.setParser(parser);
            System.out.println("RETURN DEL YYPARSE: " + parser.yyparse_publico());
            if (imprime) {
                logger.dumpErrors();
                logger.dumpEvents();
                logger.dumpWarnings();
            }
            System.out.println(lexico.tablaDeSimbolos.toString());
            if (!parser.error) {
                System.out.println(parser.raiz.imprimision());
                parser.raiz.generarCodigo();
                generarCodigo(args[0], lexico);
            }
            System.out.println(DecProc.procs);
            System.out.println(Nodo.codigo);


        } else {
            System.out.println("Se esperaban argumentos :c ");
        }
    }

    public static void generarCodigo(String direccion, AnalizadorLexico lexico) throws IOException {
        File file = new File(direccion);
        String filename = file.getName().split("\\.")[0];
        String path = file.getAbsoluteFile().getParent() + File.separator;
        FileWriter myFile = new FileWriter(path + filename + ".asm");
        myFile.write(".386\n.model flat, stdcall\n.stack 200h\noption casemap :none\ninclude \\masm32\\include\\masm32rt.inc\n");
        if(Salida.hay_salida)
            myFile.write("dll_dllcrt0 PROTO C\nprintf PROTO C :VARARG");
        myFile.write("\n\n.DATA\n");
        for (Map.Entry<String, HashMap<String, Object>> entry : lexico.tablaDeSimbolos.entrySet()) {
            Tipos tipo = (Tipos) entry.getValue().get("Tipo");
            String uso = (String) entry.getValue().get("Uso");
            if (tipo == Tipos.INTEGER) {
                if (uso == "variable") {
                    String inic = (String) entry.getValue().get("Inic");
                    myFile.write("_" + entry.getKey() + " DW " + inic + "\n");
                } else {
                    if(entry.getValue().get("NI")!=entry.getValue().get("Contador"))
                    myFile.write("_" + entry.getKey().replace("-", "N") + " DW " + entry.getKey() + "\n");
                }
            } else if (tipo == Tipos.FLOAT) {
                if (uso == "variable") {
                    String inic = (String) entry.getValue().get("Inic");
                    myFile.write("_" + entry.getKey() + " DQ " + inic + "\n");
                } else if (uso == "auxiliar") {
                    myFile.write(entry.getKey() + " DQ ?\n");
                } else {
                    myFile.write("_" + entry.getKey().replace(".", "_").replace("-", "N") + " DQ " + entry.getKey() + "\n");
                }
            } else if (tipo == Tipos.STRING) {
                myFile.write("_" + entry.getKey().replaceAll("\'", "").replaceAll(" ","_") + " DB " + entry.getKey() + ",0\n");
            }
        }
        if(Salida.integer)
            myFile.write("aux_salida DD ?\n");
        myFile.write("mem2bytes DW ?\n");
        myFile.write("_cero DB 'Error: Division por cero',0\n");
        myFile.write("_recursion DB 'Error: Recursion no permitida',0\n");
        myFile.write("\n");
        myFile.write(".CODE\n");
        myFile.write(DecProc.procs.toString());
        myFile.write("_CERO:\ninvoke printf, cfm$(\"%s\\n\"),OFFSET _cero\nJMP _END\n");
        myFile.write("_RECURSION:\ninvoke printf, cfm$(\"%s\\n\"),OFFSET _recursion\nJMP _END\n");
        myFile.write("START:\n");
        myFile.write(Nodo.codigo.toString());
        myFile.write("_END:\n");
        myFile.write("exit,0\n");
        myFile.write("END START\n");
        myFile.close();

    }
}
