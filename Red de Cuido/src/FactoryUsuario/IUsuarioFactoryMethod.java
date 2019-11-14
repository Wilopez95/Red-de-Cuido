package FactoryUsuario;

public interface IUsuarioFactoryMethod {
    public Usuario crearUsuarioE(String Name,String Center,int Grade,int ID , String tipe , String puestation);
    public Usuario crearUsuarioC(String Name,String Mail,int Grade);
    public Usuario crearUsuarioA(String Name,String Mail,int Type);

}
