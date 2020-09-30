package compiladores;

public class Logger {

    static Logger instance=null;



    private Logger(){

    }

    public Logger getInstance(){
        if(instance == null)
            instance = new Logger();
        return instance;
    }

    public void error(String msg,int line) {

    }

    public void warning(String msg,int line) {

    }




}
