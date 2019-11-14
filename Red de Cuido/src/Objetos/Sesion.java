package Objetos;

import FactoryUsuario.Administrador;
import FactoryUsuario.Usuario;

public class Sesion {
    private static Sesion singleton;
    private Usuario usuario;

    private Sesion(){

    }

    private static synchronized void createinstance(){
        if(singleton == null){
            singleton = new Sesion();
        }
    }

    public static Sesion getInstance(){
        if(singleton == null){
            createinstance();
        }
        return singleton;
    }

    private void setUsuario(Usuario user){
        this.usuario = (Usuario) user;
    }

    public void IniciarSesion(Usuario usuario){
        setUsuario(usuario);
    }

    public void CerrarSesion(){

    }

    public int getTipoEmpleado(){
        Administrador adm = (Administrador)usuario;
        return adm.getTipo();
    }

    public Usuario getUsuario() {
        return usuario;
    }
}
