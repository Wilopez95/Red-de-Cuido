package Acceso;

public class MapeoEnfermedades {

    private String Enfermedad;
    private  String Tratamiento;

    public MapeoEnfermedades(String enfermedad, String tratamiento){
        this.Enfermedad=enfermedad;
        this.Tratamiento=tratamiento;
    }

    public String getEnfermedad() {
        return Enfermedad;
    }

    public void setEnfermedad(String enfermedad) {
        Enfermedad = enfermedad;
    }

    public String getTratamiento() {
        return Tratamiento;
    }

    public void setTratamiento(String tratamiento) {
        Tratamiento = tratamiento;
    }
}
