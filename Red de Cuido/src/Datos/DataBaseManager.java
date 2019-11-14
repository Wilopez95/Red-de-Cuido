package Datos;

import Acceso.*;
import Objetos.DTO;
import sun.misc.Perf;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.*;
import java.util.ArrayList;

public class DataBaseManager {
    private static DataBaseManager singleton;
    private Connection con;

    private DataBaseManager(){


    }

    private static synchronized void createinstance(){
        if(singleton == null){
            singleton = new DataBaseManager();
        }
    }

    public static DataBaseManager getInstance(){
        if(singleton == null){
            createinstance();
        }
        return singleton;
    }

    private void ConexionSQL(){
        try {
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
            String connectionURL ="jdbc:sqlserver://localhost\\WLOPEZSQL:1433;databaseName=test;user=Admin;password=admin;";
            con = DriverManager.getConnection(connectionURL);
            System.out.println("Conexion exitosa");
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }


    public DTO Login_Client(String emailparam , String passparam, DTO dto) throws SQLException {
        ConexionSQL();
        System.out.println("Obteniendo datos cliente");
        try {
            String SPsql = "EXEC Login_User ?,?";
            PreparedStatement st = con.prepareStatement(SPsql);
            st.setString(1,emailparam);
            st.setString(2,passparam);
            ResultSet rs = st.executeQuery();
            while(rs.next()) {
                String Nombre = rs.getString(1);
                String Correo = rs.getString(2);
                int Calificacion = rs.getInt(3);


                System.out.println(Nombre+" - "+Correo+" - "+Calificacion);
                dto.setNombre(Nombre);
                dto.setCorreo(Correo);
                dto.setCalificacion(Calificacion);
            }


        }catch(Exception e) {
            System.out.println(e);
            dto.setErrorFlag(true);

        }
        con.close();
        return dto;
    }


    public DTO Login_Employed(String emailparam , String passparam,DTO dto) throws SQLException {
        ConexionSQL();
        System.out.println( "Obteniendo datos Empleado");
        try{
            String SPsql = "EXEC Login_Admin ?,?";
            PreparedStatement st = con.prepareStatement(SPsql);
            st.setString(1,emailparam);
            st.setString(2,passparam);
            ResultSet rs = st.executeQuery();
            while(rs.next()) {
                //int ID = rs.getInt(1);
                String Correo = rs.getString(1);
                int Tipo = rs.getInt(2);
                String Centro = rs.getString(3);
                String Nombre = rs.getString(4);
                //System.out.println(Correo+" - "+Tipo+" - "+Centro+" - "+Nombre);
                dto.setNombre(Nombre);
                dto.setCorreo(Correo);
                dto.setTipo(Tipo);
            }
        }catch(Exception e) {
            System.out.println(e);
            dto.setErrorFlag(true);

        }
        con.close();
        return dto;
    }

    public boolean RegistrarCliente(Perfil perfil)throws SQLException {
        boolean flag ;
        ConexionSQL();
        System.out.println( "Registrando un nuevo Cliente");
        try {
            CallableStatement cs = con.prepareCall("{? = call Register_User (?,?,?,?)}");
            int i = 0;
            cs.registerOutParameter(++i, java.sql.Types.INTEGER);
            cs.setString(++i, perfil.getNombre());
            cs.setString(++i,perfil.getCorreo());
            cs.setString(++i,perfil.getContraseña());
            cs.setString(++i,perfil.getJustificacion());
            cs.execute();
            int groupId = cs.getInt(1);
            System.out.println("Respuesta: "+groupId);
           if(groupId == -1){
               flag = false;
           }else {
               //aca envio a guardar enfermedades
               if(perfil.getCantEnfermedades()>0){
                   registarEnfermedades(perfil);
               }
               flag = true;
           }
        }catch(Exception e) {
            System.out.println(e);
            flag = false;
        }
        con.close();
        return flag;
    }

    private void registarEnfermedades(Perfil perfil) throws SQLException {
        System.out.println("Vamos a registrar todas las enfermedaes");
        for (int i = 0 ; i<perfil.getCantEnfermedades();i++){
            System.out.println(perfil.getEnfermedadById(i).getEnfermedad() +" "+perfil.getEnfermedadById(i).getTratamiento());
            ConexionSQL();
            try {
                CallableStatement cs = con.prepareCall("{? = call Register_Disease (?,?,?)}");
                int j = 0;
                cs.registerOutParameter(++j, java.sql.Types.INTEGER);
                cs.setString(++j, perfil.getCorreo());
                cs.setString(++j,perfil.getEnfermedadById(i).getEnfermedad() );
                cs.setString(++j,perfil.getEnfermedadById(i).getTratamiento());
                cs.execute();
                int groupId = cs.getInt(1);

            }catch(Exception e) {
                System.out.println(e);
            }
            con.close();

        }

    }

    public ArrayList<String> getCentrosBD() throws SQLException {
        ArrayList<String> ListaCentros  = new  ArrayList<String>();
        System.out.println("Obteniendo lista de centros");
        ConexionSQL();
        try {
            String SPsql = "EXEC Get_Centros";
            PreparedStatement st = con.prepareStatement(SPsql);
            ResultSet rs = st.executeQuery();
            while(rs.next()) {
                //int ID = rs.getInt(1);
                String Nombre = rs.getString(1);
                String Ubicacion = rs.getString(2);

                ListaCentros.add(Nombre);
                //System.out.println(Nombre+" - "+Ubicacion);
            }
        }catch(Exception e) {
            System.out.println(e);


        }
        con.close();
        return ListaCentros;
    }

    public ArrayList<String> getServiciosBD() throws SQLException {
        ArrayList<String> ListaServicios  = new  ArrayList<String>();
        System.out.println("Obteniendo lista de servicios");
        ConexionSQL();
        try {
            String SPsql = "EXEC Get_Servicios";
            PreparedStatement st = con.prepareStatement(SPsql);
            ResultSet rs = st.executeQuery();
            while(rs.next()) {
                String Nombre = rs.getString(1);
                ListaServicios.add(Nombre);
                //System.out.println(Nombre);
            }
        }catch(Exception e) {
            System.out.println(e);
        }
        con.close();
        return ListaServicios;
    }

    public DTO getClientesBD(DTO dto) throws SQLException {
        System.out.println("Obteniendo Clientes");
        ConexionSQL();
        try {
            String SPsql = "EXEC Get_Clientes ";
            PreparedStatement st = con.prepareStatement(SPsql);
            ResultSet rs = st.executeQuery();
            int i=0;
            while(rs.next()) {
                String Nombre = rs.getString(1);
                String Correo = rs.getString(2);
                int Calificacion = rs.getInt(3);
                System.out.println(Nombre+" - "+Correo+" - "+Calificacion);
                dto.addCliente(Nombre,Correo,Calificacion);
                dto=getEnfermedadesBD(i,Correo,dto);
                i++;
            }
        }catch(Exception e) {
            System.out.println(e);

        }
        con.close();
        return  dto;
    }

    private DTO getEnfermedadesBD(int i,String User,DTO dto) throws SQLException {
        System.out.println("Obteniendo Enfermedades de clientes");
        ConexionSQL();
        try {

            String SPsql = "EXEC Get_Enfermedades ?";
            PreparedStatement st = con.prepareStatement(SPsql);
            st.setString(1,User);
            ResultSet rs = st.executeQuery();
            while(rs.next()) {
                String Enfermedad = rs.getString(1);
                String Tratamiento = rs.getString(2);

                if(Enfermedad == null){
                    System.out.println("No hay enfermedades registradas para este Cliente");
                }else {
                     dto.addDesease(i,Enfermedad,Tratamiento);
                    //System.out.println(Enfermedad+" - "+Tratamiento);
                }
            }
        }catch(Exception e) {

            System.out.println(e);

        }
        con.close();
        return dto;
    }
    public DTO getEmpleadosBD(DTO dto) throws SQLException {
        System.out.println("Obteniendo Empleados");
        ConexionSQL();
        try {
            String SPsql = "EXEC Get_Empleados ";
            PreparedStatement st = con.prepareStatement(SPsql);
            ResultSet rs = st.executeQuery();
            int i = 0;
            while(rs.next()) {
                String Centro = rs.getString(1);
                int ID = rs.getInt(2);
                String Nombre = rs.getString(3);
                int Calificacion = rs.getInt(4);
                String Puesto = rs.getString(5);
                String Tipo = rs.getString(6);
                dto.addEmployed(Nombre,Centro,Calificacion,ID,Tipo,Puesto);
                System.out.println(Centro+" - "+ID+" - "+Nombre+" - "+Calificacion+" - "+Puesto+" - "+Tipo);
                getHorariosBD(i,dto,ID);
                i++;
            }
        }catch(Exception e) {
            System.out.println(e);

        }
        con.close();
        return dto;
    }

    public DTO getCuidadoresBD(DTO dto) throws SQLException {
        System.out.println("Obteniendo Cuidadores");
        ConexionSQL();
        try {
            String SPsql = "EXEC Get_Cuidadores ";
            PreparedStatement st = con.prepareStatement(SPsql);
            ResultSet rs = st.executeQuery();
            int i = 0;
            while(rs.next()) {
                String Centro = rs.getString(1);
                int ID = rs.getInt(2);
                String Nombre = rs.getString(3);
                int Calificacion = rs.getInt(4);
                String Puesto = rs.getString(5);
                String Tipo = rs.getString(6);
                dto.addEmployed(Nombre,Centro,Calificacion,ID,Tipo,Puesto);
                System.out.println(Centro+" - "+ID+" - "+Nombre+" - "+Calificacion+" - "+Puesto+" - "+Tipo);
                getHorariosBD(i,dto,ID);
                i++;
            }
        }catch(Exception e) {
            System.out.println(e);

        }
        con.close();
        return dto;
    }

    public DTO getHorariosBD(int i,DTO dto, int ID) throws SQLException {
        System.out.println("Obteniendo Horarios de empleados");
        ConexionSQL();
        try {

            String SPsql = "EXEC Get_Horarios  ?";
            PreparedStatement st = con.prepareStatement(SPsql);
            st.setInt(1,ID);
            ResultSet rs = st.executeQuery();
            while(rs.next()) {
                String Dia = rs.getString(1);
                String HoraI = rs.getString(2);
                String HoraF = rs.getString(2);

                //System.out.println(Dia +" "+HoraI+" "+HoraF);

                if(Dia == null){
                    System.out.println("No hay Horarios registradas para este Empleado");
                }else {
                    dto.addHorario(i, Dia, HoraI, HoraF);
                }

            }
        }catch(Exception e) {

            System.out.println(e);

        }
        con.close();
        return dto;
    }

    public DTO getContratosBD(DTO dto) throws SQLException {
        System.out.println("Obteniendo Contratos");
        ConexionSQL();
        try {
            String SPsql = "EXEC Get_Contratos ";
            PreparedStatement st = con.prepareStatement(SPsql);
            ResultSet rs = st.executeQuery();
            while(rs.next()) {
                int ID = rs.getInt(1);
                String Cliente = rs.getString(2);
                String Empleado = rs.getString(3);
                String Servicio = rs.getString(4);
                String FechaI = rs.getString(5);
                String FechaF = rs.getString(6);
                int CalEmpleado = rs.getInt(7);
                String ComEmpleado = rs.getString(8);
                int CalCliente = rs.getInt(9);
                String ComCliente = rs.getString(10);
                int Horas = rs.getInt(11);
                int Total = rs.getInt(12);
                dto.addContrato(ID,Cliente,Empleado,Servicio,FechaI,FechaF,Horas,CalEmpleado,ComEmpleado,CalCliente,ComCliente,Total);
                System.out.println(Cliente+"/"+Empleado+"/"+Servicio+"/"+FechaI+"/"+FechaF+"/"+CalEmpleado+"/"+ComEmpleado+"/"+CalCliente+"/"+ComCliente+"/"+Horas+"/"+Total);
            }
        }catch(Exception e) {
            System.out.println(e);

        }
        con.close();
        return dto;
    }

    public DTO getHistorialBD(DTO dto,String email) throws SQLException {
        System.out.println("Obteniendo Historial");
        ConexionSQL();
        try {
            String SPsql = "EXEC Get_Historial ?";
            PreparedStatement st = con.prepareStatement(SPsql);
            st.setString(1,email);
            ResultSet rs = st.executeQuery();
            while(rs.next()) {
                int ID = rs.getInt(1);
                String Cliente = rs.getString(2);
                String Empleado = rs.getString(3);
                String Servicio = rs.getString(4);
                String FechaI = rs.getString(5);
                String FechaF = rs.getString(6);
                int CalEmpleado = rs.getInt(7);
                String ComEmpleado = rs.getString(8);
                int CalCliente = rs.getInt(9);
                String ComCliente = rs.getString(10);
                int Horas = rs.getInt(11);
                int Total = rs.getInt(12);
                dto.addContrato(ID,Cliente,Empleado,Servicio,FechaI,FechaF,Horas,CalEmpleado,ComEmpleado,CalCliente,ComCliente,Total);
                System.out.println(Cliente+"/"+Empleado+"/"+Servicio+"/"+FechaI+"/"+FechaF+"/"+CalEmpleado+"/"+ComEmpleado+"/"+CalCliente+"/"+ComCliente+"/"+Horas+"/"+Total);
            }
        }catch(Exception e) {
            System.out.println(e);

        }
        con.close();
        return dto;
    }


    public void EnableClienteBD(String email) throws SQLException {
        System.out.println("Habilitando un cliente");
        ConexionSQL();
        try {
            CallableStatement cs = con.prepareCall("{? = call Enable_Cliente (?)}");
            int i = 0;
            cs.registerOutParameter(++i, java.sql.Types.INTEGER);
            cs.setString(++i, email);
            cs.execute();
            int groupId = cs.getInt(1);
            //System.out.println("Respuesta: "+groupId);
        }catch(Exception e) {
            System.out.println(e);
        }
        con.close();

    }


    public void Calificar_ClienteBD(int value,String commetn,int ID) throws SQLException {
            System.out.println("Calificando Cliente");
            ConexionSQL();
            try {
                CallableStatement cs = con.prepareCall("{? = call Calificar_Empleado (?,?,?)}");
                int i = 0;
                cs.registerOutParameter(++i, java.sql.Types.INTEGER);
                cs.setInt(++i, value);
                cs.setString(++i, commetn);
                cs.setInt(++i,ID);
                cs.execute();
                int groupId = cs.getInt(1);
                System.out.println("Respuesta: "+groupId);
            }catch(Exception e) {
                System.out.println(e);
            }
            con.close();

    }

    public void Calificar_EmpleadoBD(int value,String commetn,int ID) throws SQLException {
        System.out.println("Calificando Cliente");
        ConexionSQL();
        try {
            CallableStatement cs = con.prepareCall("{? = call Calificar_Clientes (?,?,?)}");
            int i = 0;
            cs.registerOutParameter(++i, java.sql.Types.INTEGER);
            cs.setInt(++i, value);
            cs.setString(++i, commetn);
            cs.setInt(++i,ID);
            cs.execute();
            int groupId = cs.getInt(1);
            System.out.println("Respuesta: "+groupId);
        }catch(Exception e) {
            System.out.println(e);
        }
        con.close();

    }

    public void RegistrarContratoBD(int ID,String mail,String service,String idate,String fdate,int hours)throws SQLException {
        boolean flag ;
        ConexionSQL();
        System.out.println( "Registrando un nuevo Contrato");
        try {
            CallableStatement cs = con.prepareCall("{? = call Agregar_Contrato (?,?,?,?,?,?)}");
            int i = 0;
            cs.registerOutParameter(++i, java.sql.Types.INTEGER);
            cs.setInt(++i, ID);
            cs.setString(++i,mail);
            cs.setString(++i,service);
            cs.setString(++i,idate);
            cs.setString(++i,fdate);
            cs.setInt(++i,hours);
            cs.execute();
            int groupId = cs.getInt(1);
            System.out.println("Respuesta: "+groupId);
        }catch(Exception e) {
            System.out.println(e);
            flag = false;
        }
        con.close();
    }


    public void RegistrarCategoriaBD(String name,int PrecioCli , int PrecioCui)throws SQLException {
        boolean flag ;
        ConexionSQL();
        System.out.println( "Registrando una nueva Categoria");
        try {
            CallableStatement cs = con.prepareCall("{? = call RegistrarCategoria (?,?,?)}");
            int i = 0;
            cs.registerOutParameter(++i, java.sql.Types.INTEGER);
            cs.setString(++i,name);
            cs.setInt(++i,PrecioCli);
            cs.setInt(++i,PrecioCui);
            cs.execute();
            int groupId = cs.getInt(1);
            System.out.println("Respuesta: "+groupId);
        }catch(Exception e) {
            System.out.println(e);
            flag = false;
        }
        con.close();
    }


    public void RegistrarPuestoBD(String name,int Salario)throws SQLException {
        boolean flag ;
        ConexionSQL();
        System.out.println( "Registrando una nueva Categoria");
        try {
            CallableStatement cs = con.prepareCall("{? = call RegistrarPuesto (?,?)}");
            int i = 0;
            cs.registerOutParameter(++i, java.sql.Types.INTEGER);
            cs.setString(++i,name);
            cs.setInt(++i,Salario);
            cs.execute();
            int groupId = cs.getInt(1);
            System.out.println("Respuesta: "+groupId);
        }catch(Exception e) {
            System.out.println(e);
            flag = false;
        }
        con.close();
    }

    public void RegistrarEmpleadosBD(int centro,int puesto,int tipoemp, String nombre)throws SQLException {
        boolean flag ;
        ConexionSQL();
        System.out.println( "Registrando un nuevo empleado");
        try {
            CallableStatement cs = con.prepareCall("{? = call RegistrarEmpleado (?,?,?,?)}");
            int i = 0;
            cs.registerOutParameter(++i, java.sql.Types.INTEGER);
            cs.setInt(++i,centro);
            cs.setInt(++i,puesto);
            cs.setInt(++i,tipoemp);
            cs.setString(++i,nombre);
            cs.execute();
            int groupId = cs.getInt(1);
            System.out.println("Respuesta: "+groupId);
        }catch(Exception e) {
            System.out.println(e);
            flag = false;
        }
        con.close();
    }






}
