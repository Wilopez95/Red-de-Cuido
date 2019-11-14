package Controller;

import Acceso.*;
import Datos.DataBaseManager;
import FactoryUsuario.*;
import Objetos.Contrato;
import Objetos.DTO;
import Objetos.Sesion;
import Vista.Home_Cliente;
import Vista.Home_Employed;
import Vista.LoginView;
import Vista.RegisterView;

import java.sql.SQLException;
import java.util.ArrayList;

public class MainController {

    private static MainController singleton;
    private Acceso acceso;
    private DataBaseManager DBM;
    private Sesion sesion;
    private LoginView loginwiev;
    private Home_Employed home_employed;
    private DTO dto;
    private Perfil perfil;
    public ArrayList<Empleado> ListaEmpleados = new ArrayList<Empleado>();
    public ArrayList<Cliente> ListaClientes = new ArrayList<Cliente>();
    public ArrayList<String> ListaServicios = new ArrayList<String>();
    public ArrayList<String> ListaCentros = new ArrayList<String>();
    public ArrayList<Contrato> Contratos = new ArrayList<Contrato>();


    UsuarioFactory factory;

    private MainController() throws SQLException {
        this.acceso = Acceso.getInstance();
        this.DBM = DataBaseManager.getInstance();
        sesion = Sesion.getInstance();
        factory = new UsuarioFactory();
        dto=new DTO();


    }

    private static synchronized void createinstance() throws SQLException {
        if(singleton == null){
            singleton = new MainController();
        }
    }

    public static MainController getInstance() throws SQLException {
        if(singleton == null){
            createinstance();
        }
        return singleton;
    }

    public Sesion getSesion() {
        return sesion;
    }

    public void test() throws SQLException {

        DBM.RegistrarCategoriaBD("Nueva Categoria",100,500);
        DBM.RegistrarPuestoBD("Nuevo Puesto",10000);
        DBM.RegistrarEmpleadosBD(1,1,1,"TestEmployed");


    }

    public void viewLoginInit() throws SQLException {
        loginwiev=new LoginView();
    }

    public void viewLoginEnd(){
        loginwiev.CloseWindows();
    }

    public void HomeClientInit() throws SQLException {
        Home_Cliente home_cliente= new Home_Cliente();
    }

    private void HomeEmployedInit(int tipo) throws SQLException {
        home_employed=new Home_Employed(tipo);
    }

    private void getCentros() throws SQLException {
        this.ListaCentros =  DBM.getCentrosBD();
    }

    private void getServicios() throws SQLException {
        this.ListaServicios = DBM.getServiciosBD();
    }

    /*
    * Optiene la lista de clientes que no han sido validados*/
    private void getClientes() throws SQLException {
        this.ListaClientes = DBM.getClientesBD(dto).getListaClientes();
    }

    /*
    * Optiene la lista de todos los empleados.*/
    private void getEmpleados() throws SQLException {
        this.ListaEmpleados = DBM.getEmpleadosBD(dto).getListaEmpleados();
    }

    private void getCuidadores() throws SQLException {
        this.ListaEmpleados = DBM.getCuidadoresBD(dto).getListaEmpleados();
    }

    public void getContratos() throws SQLException {
        this.Contratos = DBM.getContratosBD(dto).getListaContratos();
    }

    public void getHistorial(String email) throws SQLException {
        this.Contratos = DBM.getHistorialBD(dto,email).getListaContratos();
    }

    public void EnableClient(String mail) throws SQLException {
        DBM.EnableClienteBD(mail);
    }

    public void CalificarCliente(int value, String comment , int ID) throws SQLException {
        DBM.Calificar_ClienteBD(value,comment,ID);
    }

    public void CalificarEmpleado(int value, String comment , int ID) throws SQLException {
        DBM.Calificar_EmpleadoBD(value,comment,ID);
    }

    public void RegistrarContrato(int ID,String mail,String service,String idate,String fdate,int hours) throws SQLException {
        DBM.RegistrarContratoBD(ID,mail,service,idate,fdate,hours);
    }



    public void Login(String mail,String pass) throws SQLException {
        int val=acceso.LoginMaster(mail,pass);
        if(val==0){
            sesionCliente(mail,pass);
        }else{
            sesionEmpleado(mail,pass);
        }
    }


    public void viewRegister() throws SQLException {
        perfil = new Perfil();
        viewLoginEnd();
        RegisterView registerView = new RegisterView();
    }

    public boolean registrarCliente(Perfil perfil) throws SQLException {
        System.out.println("Registrando... "+perfil.getNombre());
        this.perfil = perfil;
        System.out.println(perfil.getCantEnfermedades() +"  #Enfermedades");
        return DBM.RegistrarCliente(perfil);
}



    public void sesionCliente(String email, String pass)throws SQLException{
        System.out.println("Inicio Sesion un clinte");
        this.dto = DBM.Login_Client(email,pass,dto);
        if(this.dto.isErrorFlag()) {
            System.out.println("Error en inicio de sesion");
            this.dto.setErrorFlag(false);
            loginwiev.showError();
        }else{
            Usuario Cliente = factory.crearUsuarioC(this.dto.getNombre(),this.dto.getCorreo(),this.dto.getCalificacion());
           sesion.IniciarSesion((Usuario) Cliente);
           System.out.println("Se se creo el cliente: "+Cliente.getNombre());
           viewLoginEnd();

            getHistorial(this.dto.getCorreo());
            getClientes();
            getCuidadores();
            getCentros();
            getServicios();

           HomeClientInit();

        }

    }

    public void showNotificatioAcc(){
        loginwiev.showAccNotification();
    }

    public void sesionEmpleado(String email, String pass) throws SQLException {
        System.out.println("Inicio Sesion un empleado");
        this.dto = DBM.Login_Employed(email,pass,dto);
        if(this.dto.isErrorFlag()){
            System.out.println("Error en inicio de sesion");
            this.dto.setErrorFlag(false);
            loginwiev.showError();

        }else{
            getContratos();
            getClientes();
            getEmpleados();
            getCentros();
            getServicios();
            Usuario Admin = factory.crearUsuarioA(this.dto.getNombre(),this.dto.getCorreo(),this.dto.getTipo());
            sesion.IniciarSesion((Usuario) Admin);
            System.out.println("Se se creo el cliente: "+Admin.getNombre());
            viewLoginEnd();
            HomeEmployedInit(sesion.getTipoEmpleado());
            System.out.println("Tipo empleado: "+sesion.getTipoEmpleado());
        }

        setDTO();
    }

    private void setDTO(){
        this.dto.setNombre(null);
        this.dto.setCorreo(null);
        this.dto.setErrorFlag(false);

    }





}
