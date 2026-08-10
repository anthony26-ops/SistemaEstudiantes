package gt.edu.umg.sistema.estudiantes.ventas.modelo;

public class Producto {

    private int idProducto;
    private String nombre;
    private double precio;
    private int existencia;

    public int getIdProducto() {
        return idProducto;
    }

    public void setIdProducto(int idProducto) {
        this.idProducto = idProducto;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public double getPrecio() {
        return precio;
    }

    public void setPrecio(double precio) {
        this.precio = precio;
    }

    public int getExistencia() {
        return existencia;
    }

    public void setExistencia(int existencia) {
        this.existencia = existencia;
    }

    public boolean hayExistencia(int cantidad) {
        return existencia >= cantidad;
    }

    @Override
    public String toString() {
        return nombre + " - Q" + String.format("%.2f", precio);
    }
}
