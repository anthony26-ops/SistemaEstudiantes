package gt.edu.umg.sistema.estudiantes.facturacion.modelo;

/**
 * Representa una línea de producto dentro de la factura.
 */
public class DetalleFactura {

    private String producto;
    private int cantidad;
    private double precio;
    private double subtotal;

    public DetalleFactura() {
    }

    public DetalleFactura(String producto, int cantidad, double precio) {
        this.producto = producto;
        this.cantidad = cantidad;
        this.precio = precio;
        this.subtotal = cantidad * precio;
    }

    public String getProducto() {
        return producto;
    }

    public void setProducto(String producto) {
        this.producto = producto;
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
        actualizarSubtotal();
    }

    public double getPrecio() {
        return precio;
    }

    public void setPrecio(double precio) {
        this.precio = precio;
        actualizarSubtotal();
    }

    public double getSubtotal() {
        return subtotal;
    }

    public void setSubtotal(double subtotal) {
        this.subtotal = subtotal;
    }

    private void actualizarSubtotal() {
        this.subtotal = this.cantidad * this.precio;
    }
}
