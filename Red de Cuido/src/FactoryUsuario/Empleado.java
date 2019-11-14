package FactoryUsuario;

import Acceso.MapeoEnfermedades;

import java.util.ArrayList;

public class Empleado extends Usuario {

    private String Centro;
    private int Calificacion;
    private String Tipo;
    private String Puesto;
    private int ID;
    private ArrayList<Horario> ListaHorarios = new ArrayList<Horario>();

    public Empleado(String nombre, String centro, int calificacion, int id , String tipo, String puesto){
        super(nombre);
        this.Centro=centro;
        this.Calificacion=calificacion;
        this.ID=id;
        this.Tipo = tipo;
        this.Puesto = puesto;


    }

    public String getCentro() {
        return Centro;
    }

    public void setCentro(String centro) {
        Centro = centro;
    }

    public int getCalificacion() {
        return Calificacion;
    }

    public void setCalificacion(int calificacion) {
        Calificacion = calificacion;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public void addHorario(Horario horario){
        ListaHorarios.add(horario);
    }

    public String getTipo() {
        return Tipo;
    }

    public void setTipo(String tipo) {
        Tipo = tipo;
    }

    public String getPuesto() {
        return Puesto;
    }

    public void setPuesto(String puesto) {
        Puesto = puesto;
    }

    public int getID() {
        return ID;
    }

    public ArrayList<Horario> getListaHorarios() {
        return ListaHorarios;
    }

    public void setListaHorarios(ArrayList<Horario> listaHorarios) {
        ListaHorarios = listaHorarios;
    }
}
