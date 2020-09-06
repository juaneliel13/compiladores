package compiladores.AnalizadorLexico;

public class Tupla {
    private Integer estado;
    private AccionSemantica AS;



    public Tupla(Integer estado, AccionSemantica AS) {
        this.estado = estado;
        this.AS = AS;
    }

    public Integer getEstado() {
        return estado;
    }

    public AccionSemantica getAS() {
        return AS;
    }

    public void setEstado(Integer estado) {
        this.estado = estado;
    }

    public void setAS(AccionSemantica AS) {
        this.AS = AS;
    }
}
