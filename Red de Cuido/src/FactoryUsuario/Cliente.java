package FactoryUsuario;

import Acceso.MapeoEnfermedades;

import java.util.ArrayList;

public class Cliente  extends  Usuario{
    private String Correo;
    private int Calificacion;
    private ArrayList<MapeoEnfermedades> ListaEnfermedades = new ArrayList<MapeoEnfermedades>();
    public Cliente(String nombre, String correo, int calificacion){
        super(nombre);
        this.Correo=correo;
        this.Calificacion=calificacion;

    }

    public String getCorreo() {
        return Correo;
    }

    public void setCorreo(String correo) {
        Correo = correo;
    }

    public int getCalificacion() {
        return Calificacion;
    }

    public void setCalificacion(int calificacion) {
        Calificacion = calificacion;
    }

    public void addEnfermedad(String Enfermedad, String Tramiento){
        MapeoEnfermedades mapeoEnfermedades =  new MapeoEnfermedades(Enfermedad,Tramiento);
        ListaEnfermedades.add(mapeoEnfermedades);
    }

    public ArrayList<MapeoEnfermedades> getListaEnfermedades() {
        return ListaEnfermedades;
    }

}
