package Compilador.CodigoAssembler;

import java.util.List;

public class AdministradorDeRegistros {

    static List<Registro> r_32bits
    static {
        RegistroCompuesto EAX = new RegistroCompuesto("EAX",null);
        RegistroCompuesto AX = new RegistroCompuesto("AX",EAX);
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
    }

}
