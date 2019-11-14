package FactoryUsuario;

public class UsuarioFactory implements IUsuarioFactoryMethod {



    @Override
    public Usuario crearUsuarioE(String Name,String Centro,int Calificacion , int ID,String Tipo , String Puesto) {
        return new Empleado(Name,Centro,Calificacion,ID,Tipo,Puesto);
    }

    @Override
    public Usuario crearUsuarioC(String Name,String Mail,int Grade) {
        return new Cliente(Name,Mail,Grade);
    }

    @Override
    public Usuario crearUsuarioA( String Name,String Correo, int Tipo) {
        return new Administrador(Name,Correo,Tipo);
    }
}
