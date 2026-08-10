package gt.edu.umg.sistema.estudiantes.ventas.modelo;

public class Cliente extends Persona {

    private String direccion;

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    @Override
    public String mostrarInformacion() {
        return super.mostrarInformacion() + ", Direccion: " + direccion;
    }

    @Override
    public String toString() {
        return getNombre() + " (" + getNit() + ")";
    }
}
