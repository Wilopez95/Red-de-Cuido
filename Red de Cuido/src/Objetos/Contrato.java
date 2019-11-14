package Objetos;

public class Contrato {

    private int ID;
    private String Cliente;
    private String Empleado;
    private String Servicio;
    private String Fecha_inicio;
    private String Fecha_final;
    private int horasdiarias;
    private int Calificacion_Empleado;
    private String Comentario_Empleado;
    private int Calificacion_Cliente;
    private String Comentario_Cliente;
    private int Precio;

    public Contrato(int ID, String cliente, String empleado, String servicio, String fecha_inicio, String fecha_final, int horasdiarias, int calificacion_Empleado, String comentario_Empleado, int calificacion_Cliente, String comentario_Cliente, int precio) {
        this.ID = ID;
        Cliente = cliente;
        Empleado = empleado;
        Servicio = servicio;
        Fecha_inicio = fecha_inicio;
        Fecha_final = fecha_final;
        this.horasdiarias = horasdiarias;
        Calificacion_Empleado = calificacion_Empleado;
        Comentario_Empleado = comentario_Empleado;
        Calificacion_Cliente = calificacion_Cliente;
        Comentario_Cliente = comentario_Cliente;
        Precio = precio;
    }

    public int getPrecio() {
        return Precio;
    }

    public String getCliente() {
        return Cliente;
    }

    public String getEmpleado() {
        return Empleado;
    }

    public String getServicio() {
        return Servicio;
    }

    public int getID() {
        return ID;
    }

    public String getFecha_inicio() {
        return Fecha_inicio;
    }

    public String getFecha_final() {
        return Fecha_final;
    }

    public int getHorasdiarias() {
        return horasdiarias;
    }

    public int getCalificacion_Empleado() {
        return Calificacion_Empleado;
    }

    public String getComentario_Empleado() {
        return Comentario_Empleado;
    }

    public int getCalificacion_Cliente() {
        return Calificacion_Cliente;
    }

    public String getComentario_Cliente() {
        return Comentario_Cliente;
    }

}