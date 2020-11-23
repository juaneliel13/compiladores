package Compilador;

import Compilador.CodigoIntermedio.*;
import Compilador.Lexico.AnalizadorLexico;
import Compilador.Lexico.Tipos;
import Compilador.Sintactico.Parser;
import Compilador.Utilidad.Logger;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

public class App {

    public static String path,filename,masm_path;

    public static void main(String[] args) throws IOException {
        boolean imprime = true;
        if (args.length != 0) {
            File file = new File(args[0]);
            Properties prop = new Properties();
            try {
                InputStream in = new FileInputStream("conf.properties");
                prop.load(in);
                in.close();
                masm_path = prop.getProperty("masm_path");
            }
            catch(Exception e) {
                masm_path = "C:\\masm32\\bin\\";
            }
            String data = "";
            path = file.getAbsoluteFile().getParent() + File.separator;
            filename = file.getName().split("\\.")[0];
            Logger logger = Logger.getInstance();
            Logger.setFilename(path,filename);
            try {
                data = new String(Files.readAllBytes(Paths.get(file.getAbsolutePath())));
            } catch (Exception e) {
                e.printStackTrace();
            }
            AnalizadorLexico lexico = new AnalizadorLexico(data);
            Parser parser = new Parser(lexico);
            Nodo.setLexico(lexico);
            Logger.setParser(parser);
            int ret_parser=parser.yyparse_publico();
            if (imprime) {
                logger.dumpErrors();
                logger.dumpEvents();
                logger.dumpWarnings();
            }
            System.out.println(lexico.tablaDeSimbolos.toString());
            if (!parser.error && ret_parser!=1) {
                FileWriter myFile = new FileWriter(path + filename + "-arbol-sintactico.txt");
                myFile.write(parser.raiz.imprimir());
                myFile.close();
                parser.raiz.generarCodigo();
                generarCodigo(lexico);
            }
        } else {
            System.out.println("Se esperaban argumentos :c ");
        }
    }

    public static void generarCodigo(AnalizadorLexico lexico) throws IOException {
        FileWriter myFile = new FileWriter(path + filename + ".asm");
        myFile.write(".386\n.model flat, stdcall\n.stack 200h\noption casemap :none\ninclude \\masm32\\include\\masm32rt.inc\n");
        if(Salida.hay_salida || Llamada.hay_llamada || Division.hay_division)
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
            }else {
                if(uso=="procedimiento"){
                    String nombre_proc= entry.getKey();
                    myFile.write("NI_"+nombre_proc+ " DW " +lexico.tablaDeSimbolos.get(nombre_proc).get("NI") +"\n");
                    myFile.write("FLAG_"+nombre_proc+ " DW 0\n");
                    myFile.write("nombre_"+nombre_proc+ " DB " + "\'"+nombre_proc.substring(0,nombre_proc.indexOf("@")) +"\',0\n");
                }
            }
        }
        if(Salida.integer)
            myFile.write("aux_salida DD ?\n");
        if(Comparador.comp_float)
            myFile.write("mem2bytes DW ?\n");
        if(Division.hay_division)
            myFile.write("_cero DB 'Error: Division por cero',0\n");
        if(Llamada.hay_llamada) {
            myFile.write("_recursion DB 'Error: Llamado recursivo de',0\n");
            myFile.write("_invocacion DB 'Error: Se alcanzo el numero maximo de invocaciones permitidas para el procedimiento',0\n");
            myFile.write("nombre_proc DD ?\n");
        }
        myFile.write("\n");
        myFile.write(".CODE\n");
        myFile.write(DecProc.procs.toString());
        if(Division.hay_division)
            myFile.write("_CERO:\ninvoke printf, cfm$(\"%s\\n\"),OFFSET _cero\nJMP _END\n");
        if(Llamada.hay_llamada) {
            myFile.write("_RECURSION:\ninvoke printf, cfm$(\"%s %s\\n\"),OFFSET _recursion,nombre_proc\nJMP _END\n");
            myFile.write("_INVOCACION:\ninvoke printf, cfm$(\"%s %s\\n\"),OFFSET _invocacion,nombre_proc\nJMP _END\n");
        }
        myFile.write("START:\n");
        myFile.write(Nodo.codigo.toString());
        myFile.write("_END:\n");
        myFile.write("exit,0\n");
        myFile.write("END START\n");
        myFile.close();

        ProcessBuilder processBuilder = new ProcessBuilder();
        processBuilder.directory(new File(masm_path));
        processBuilder.command("cmd.exe", "/c" ,
                        "copy " + path + filename + ".asm " + filename + ".asm " +
                        "& ml /c /coff " + filename + ".asm " +
                        "& link /SUBSYSTEM:CONSOLE " + filename + ".obj " +
                        "& del /f " + filename + ".asm " +
                        "& move " + filename + ".obj " + path + filename + ".obj " +
                        "& move " + filename + ".exe " + path + filename + ".exe ");
        try {

            Process process = processBuilder.start();
            int exitCode = process.waitFor();
            System.out.println("\nBuild exited with error code : " + exitCode);

        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            System.out.println("No se pudo compilar el archivo " + filename + ".asm, no se encontro el MASM.");
        }
    }
}
