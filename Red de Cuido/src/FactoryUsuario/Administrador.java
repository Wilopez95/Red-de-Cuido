package FactoryUsuario;

public class Administrador extends Usuario {
    private String Correo;
    private int Tipo;

    public Administrador(String nombre, String correo, int tipo){
        super(nombre);
        this.Correo = correo;
        this.Tipo = tipo;
    }

    public String getCorreo() {
        return Correo;
    }

    public void setCorreo(String correo) {
        Correo = correo;
    }

    public int getTipo() {
        return Tipo;
    }

    public void setTipo(int tipo) {
        Tipo = tipo;
    }
}

