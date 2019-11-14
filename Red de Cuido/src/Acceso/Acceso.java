package Acceso;

public class Acceso {

    private static Acceso singleton;
    private Perfil perfil;

    private Acceso(){

    }

    private static synchronized void createinstance(){
        if(singleton == null){
            singleton = new Acceso();
        }
    }

    public static Acceso getInstance(){
        if(singleton == null){
            createinstance();
        }
        return singleton;
    }

    private int Login_Cliente(String email, String pass){
        System.out.println("Login Cliente");
        return 0;
    }

    private int Login_Employed(String email, String pass){
        System.out.println("Login Empleado");
        return 1;
    }

    public int LoginMaster(String email, String pass){
        String[] arrOfStr = email.toLowerCase().split("@", 2);
        if(arrOfStr[1].equals("cuido.com")){
            return Login_Employed(email,pass);
        }else {
            return Login_Cliente(email,pass);
        }
    }

    public void Register(Perfil perfil){

    }






}
