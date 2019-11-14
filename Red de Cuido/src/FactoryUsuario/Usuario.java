package FactoryUsuario;

public class Usuario {
    private String Nombre;


    public Usuario(String nombre){
        this.Nombre = nombre;
    }

    public String getNombre() {
        return Nombre;
    }

    public void setNombre(String nombre) {
        Nombre = nombre;
    }
}
