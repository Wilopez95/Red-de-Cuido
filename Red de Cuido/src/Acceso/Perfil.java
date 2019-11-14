package Acceso;

import java.util.ArrayList;

public class Perfil {

    private String Nombre;
    private String Correo;
    private String Contraseña;
    private String Justificacion;
    private ArrayList<MapeoEnfermedades> Enfermedades = new ArrayList<MapeoEnfermedades>();


    public Perfil(){


    }



    public void addEnfermedad(MapeoEnfermedades mapeoEnfermedades){
        Enfermedades.add(mapeoEnfermedades);
    }

    public String getNombre() {
        return Nombre;
    }

    public void setNombre(String nombre) {
        Nombre = nombre;
    }

    public String getCorreo() {
        return Correo;
    }

    public void setCorreo(String correo) {
        Correo = correo;
    }

    public String getContraseña() {
        return Contraseña;
    }

    public void setContraseña(String contraseña) {
        Contraseña = contraseña;
    }

    public String getJustificacion() {
        return Justificacion;
    }

    public void setJustificacion(String justificacion) {
        Justificacion = justificacion;
    }

    public int getCantEnfermedades(){
        return Enfermedades.size();
    }

    public MapeoEnfermedades getEnfermedadById(int index){
        return Enfermedades.get(index);
    }


}
