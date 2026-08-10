package gt.edu.umg.sistema.estudiantes.ventas.modelo;

public class Empleado extends Persona {

    private String codigoEmpleado;
    private String puesto;

    public String getCodigoEmpleado() {
        return codigoEmpleado;
    }

    public void setCodigoEmpleado(String codigoEmpleado) {
        this.codigoEmpleado = codigoEmpleado;
    }

    public String getPuesto() {
        return puesto;
    }

    public void setPuesto(String puesto) {
        this.puesto = puesto;
    }

    @Override
    public String mostrarInformacion() {
        return super.mostrarInformacion() + ", Codigo: " + codigoEmpleado + ", Puesto: " + puesto;
    }

    @Override
    public String toString() {
        return getNombre() + " - " + codigoEmpleado;
    }
}
