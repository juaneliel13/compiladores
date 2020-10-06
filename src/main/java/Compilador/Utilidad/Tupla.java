package Compilador.Utilidad;

public class Tupla<t1,t2> {
    private t1 first;
    private t2 second;

    public Tupla(t1 first, t2 second) {
        this.first = first;
        this.second = second;
    }

    public t1 getFirst() {
        return first;
    }

    public t2 getSecond() {
        return second;
    }

    public void setFirst(t1 first) {
        this.first = first;
    }

    public void setSecond(t2 second) {
        this.second = second;
    }
}
