package Compilador.CodigoAssembler;

import Compilador.CodigoIntermedio.ConTipo;

import java.util.HashMap;
import java.util.Vector;

public class AdministradorDeRegistros {

    public static Vector<Registro> r_32bits;
    public static Vector<Registro> r_16bits;
    public static Vector<Registro> r_8bits;

    static HashMap<Registro,ConTipo> nombre;

    public static RegistroCompuesto AX;

    static {
        r_8bits = new Vector<>();
        r_16bits = new Vector<>();
        r_32bits = new Vector<>();
        nombre = new HashMap<>();

        RegistroCompuesto EAX = new RegistroCompuesto("EAX",null);
        AX = new RegistroCompuesto("AX",EAX);
        EAX.addHijos(AX);
        RegistroSimple AH = new RegistroSimple("AH",AX);
        RegistroSimple AL = new RegistroSimple("AL",AX);
        AX.addHijos(AH,AL);
        RegistroCompuesto EBX = new RegistroCompuesto("EBX",null);
        RegistroCompuesto BX = new RegistroCompuesto("BX",EBX);
        EAX.addHijos(BX);
        RegistroSimple BH = new RegistroSimple("BH",BX);
        RegistroSimple BL = new RegistroSimple("BL",BX);
        AX.addHijos(BH,BL);
        RegistroCompuesto ECX = new RegistroCompuesto("ECX",null);
        RegistroCompuesto CX = new RegistroCompuesto("CX",ECX);
        EAX.addHijos(CX);
        RegistroSimple CH = new RegistroSimple("CH",CX);
        RegistroSimple CL = new RegistroSimple("CL",CX);
        AX.addHijos(CH,CL);
        RegistroCompuesto EDX = new RegistroCompuesto("EDX",null);
        RegistroCompuesto DX = new RegistroCompuesto("DX",EDX);
        EAX.addHijos(DX);
        RegistroSimple DH = new RegistroSimple("DH",DX);
        RegistroSimple DL = new RegistroSimple("DL",DX);
        AX.addHijos(DH,DL);

        addRegs8bits(AH,AL,BH,BL,CH,CL,DH,DL);
        addRegs16bits(AX,BX,CX,DX);
        addRegs32bits(EAX,EBX,ECX,EDX);
    }

    public static void add8bits(Registro registro) {
        r_8bits.add(registro);
    }

    public static void add16bits(Registro registro) {
        r_16bits.add(registro);
    }

    public static void add32bits(Registro registro) {
        r_32bits.add(registro);
    }

    public static Registro get8bits(ConTipo nodo){
        Registro aux = r_8bits.remove(0);
        aux.ocupar();
        nombre.put(aux,nodo);
        return aux;
    }

    public static Registro get16bits(ConTipo nodo){
        Registro aux = r_16bits.remove(0);
        aux.ocupar();
        nombre.put(aux,nodo);
        return aux;
    }

    public static Registro get32bits(ConTipo nodo){
        Registro aux = r_32bits.remove(0);
        aux.ocupar();
        nombre.put(aux,nodo);
        return aux;
    }

    public static void rm8bits(Registro registro) {
        r_8bits.remove(registro);
        nombre.remove(registro);
    }

    public static void rm16bits(Registro registro) {
        r_16bits.remove(registro);
        nombre.remove(registro);
    }

    public static void rm32bits(Registro registro) {
        r_32bits.remove(registro);
        nombre.remove(registro);
    }

    private static void addRegs8bits(Registro ...registros){
        for(Registro r : registros)
            r_8bits.add(r);
    }

    private static void addRegs16bits(Registro ...registros){
        for(Registro r : registros)
            r_16bits.add(r);
    }

    private static void addRegs32bits(Registro ...registros){
        for(Registro r : registros)
            r_32bits.add(r);
    }

    public static ConTipo propietario(Registro registro) {
        return nombre.get(registro);
    }

    public static Registro getAX(ConTipo nodo){
        AX.ocupar();
        nombre.put(AX,nodo);
        return AX;
    }

}
