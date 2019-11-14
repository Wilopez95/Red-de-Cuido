package FactoryUsuario;

public class Horario {
    private String Dia;
    private String HoraI;
    private String HoraF;

    public Horario(String dia, String horai,String horaf){
        this.Dia=dia;
        this.HoraI=horai;
        this.HoraF=horaf;
    }

    public String getDia() {
        return Dia;
    }

    public void setDia(String dia) {
        Dia = dia;
    }

    public String getHoraI() {
        return HoraI;
    }

    public void setHoraI(String horaI) {
        HoraI = horaI;
    }

    public String getHoraF() {
        return HoraF;
    }

    public void setHoraF(String horaF) {
        HoraF = horaF;
    }
}
