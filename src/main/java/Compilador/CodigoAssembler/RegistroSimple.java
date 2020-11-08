package Compilador.CodigoAssembler;

public class RegistroSimple extends Registro{

    public RegistroSimple(String nombre, Registro padre) {
        super(nombre,padre);
    }

    @Override
    public boolean estaLibre() {
        return libre;
    }

    @Override
    public void ocupar() {
        libre=false;
        this.notifyObservers();
    }

    @Override
    public void liberar(){
        libre = true;
        AdministradorDeRegistros.add8bits(this);
        this.notifyObservers();
    }



}
