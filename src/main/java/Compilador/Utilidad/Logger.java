package Compilador.Utilidad;


import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class Logger {

    class SorterByFirst implements Comparator<Tupla<Integer, String>> {

        @Override
        public int compare(Tupla<Integer, String> t1, Tupla<Integer, String> t2) {
            return t1.getFirst() - t2.getFirst();
        }
    }

    static Logger instance;
    static String path;
    static String filename;

    List<Tupla<Integer, String>> errors;
    List<Tupla<Integer, String>> warnings;
    List<Tupla<Integer, String>> events;

    private Logger() {
        this.errors = new ArrayList<Tupla<Integer, String>>();
        this.warnings = new ArrayList<Tupla<Integer, String>>();
        this.events = new ArrayList<Tupla<Integer, String>>();
    }

    public static Logger getInstance() {
        if (instance == null)
            instance = new Logger();
        return instance;
    }

    public static void setFilename(String filename) {
        Logger.filename = filename.substring(filename.lastIndexOf(File.separator) + 1).split("\\.")[0];
        Logger.path = filename.substring(0, filename.lastIndexOf(File.separator) + 1);
    }

    public void addError(int line, String msg) {
        this.errors.add(new Tupla<>(line, msg));
    }

    public void addWarning(int line, String msg) {
        this.warnings.add(new Tupla<>(line, msg));
    }

    public void addEvent(int line, String msg) {
        this.events.add(new Tupla<>(line, msg));
    }

    public void dumpErrors() {
        errors.sort(new SorterByFirst());
        FileWriter myFile = null;
        try {
            myFile = new FileWriter(filename + "-errores.txt");
            for (Tupla t : errors) {
                myFile.write("Error en la linea " + t.getFirst() + ": " + t.getSecond() + ".\n");
            }
            myFile.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void dumpWarnings() {
        warnings.sort(new SorterByFirst());
        FileWriter myFile = null;
        try {
            myFile = new FileWriter(filename + "-warnings.txt");
            for (Tupla t : warnings) {
                myFile.write("Warning en la linea " + t.getFirst() + ": " + t.getSecond() + ".\n");
            }
            myFile.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void dumpEvents() {
        //events.sort(new SorterByFirst());
        FileWriter myFile = null;
        try {
            myFile = new FileWriter(filename + "-events.txt");
            for (Tupla t : events) {
                myFile.write("Evento en la linea " + t.getFirst() + ": " + t.getSecond() + ".\n");
            }
            myFile.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
