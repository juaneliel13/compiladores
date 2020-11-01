package Compilador.Lexico;

import java.util.HashMap;
import java.util.Map;

public enum Tipos {
    FLOAT(0), INTEGER(1), STRING(2);

    private int num;

    private static Map<Integer, Tipos> map = new HashMap<Integer, Tipos>();

    static {
        for (Tipos tiposEnum : Tipos.values()) {
            map.put(tiposEnum.num, tiposEnum);
        }
    }

    private Tipos(final int num) { this.num = num; }

    public static Tipos valueOf(int num) {
        return map.get(num);
    }

    public int getValue(){
        return num;
    }
}
