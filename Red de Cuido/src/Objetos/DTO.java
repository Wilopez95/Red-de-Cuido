package Objetos;

import FactoryUsuario.*;

import java.util.ArrayList;


public class DTO {

    private String Nombre;
    private String Correo;
    private int Tipo;
    private int Calificacion;
    private boolean ErrorFlag;
    private Cliente cliente;
    private Empleado empleado;

    UsuarioFactory factory;

    public ArrayList<Cliente> ListaClientes = new ArrayList<Cliente>();
    public ArrayList<Empleado> ListaEmpleados = new ArrayList<Empleado>();
    public ArrayList<Contrato> ListaContratos = new ArrayList<Contrato>();

    public DTO()
    {
        factory = new UsuarioFactory();
        ErrorFlag = false;
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

    public int getTipo() {
        return Tipo;
    }

    public void setTipo(int tipo) {
        this.Tipo = tipo;
    }

    public boolean isErrorFlag() {
        return ErrorFlag;
    }

    public void setErrorFlag(boolean errorFlag) {
        this.ErrorFlag = errorFlag;
    }

    public int getCalificacion() {
        return Calificacion;
    }

    public void setCalificacion(int calificacion) {
        Calificacion = calificacion;
    }

    public void addCliente(String name , String mail,int grade){
        Usuario Cliente = factory.crearUsuarioC(name,mail,grade);
        ListaClientes.add((Cliente) Cliente);
    }

    public void addDesease(int index , String Enfermedad ,String Tratamiento){
        ListaClientes.get(index).addEnfermedad(Enfermedad,Tratamiento);
    }

    public ArrayList<Cliente> getListaClientes(){
        return this.ListaClientes;
    }

    public void addEmployed(String name,String center , int grade , int ID , String type, String position){
        Usuario Empleado = factory.crearUsuarioE(name,center,grade,ID,type,position);
        ListaEmpleados.add((Empleado) Empleado);
    }

    public void addHorario(int index ,String day , String horai, String horaf){
        Horario horario =  new Horario(day,horai,horaf);
        ListaEmpleados.get(index).addHorario(horario);
    }
    public ArrayList<Empleado> getListaEmpleados(){
        return this.ListaEmpleados;
    }

    public void addContrato(int ID,String Cliente,String Empleado ,String Servico , String FechaI , String FechaF,int Horas,int CalEmp,String ConEmp,int CalCli,String ComCli,int Total){
        Contrato contrato = new Contrato(ID,Cliente,Empleado,Servico,FechaI,FechaF,Horas,CalEmp,ConEmp,CalCli,ComCli,Total);
        ListaContratos.add(contrato);
    }
    public ArrayList<Contrato> getListaContratos(){
        return this.ListaContratos;
    }



    //Usuario Empleado = factory.crearUsuarioE("nombre","centro",5,1);
    //Usuario Cliente = factory.crearUsuarioC("nombre","correo",5);
}
