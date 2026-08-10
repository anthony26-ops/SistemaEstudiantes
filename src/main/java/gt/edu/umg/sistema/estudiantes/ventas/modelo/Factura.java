package gt.edu.umg.sistema.estudiantes.ventas.modelo;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Factura {

    private int idFactura;
    private LocalDate fecha;
    private Cliente cliente;
    private final List<DetalleFactura> detalles = new ArrayList<>();

    public int getIdFactura() {
        return idFactura;
    }

    public void setIdFactura(int idFactura) {
        this.idFactura = idFactura;
    }

    public LocalDate getFecha() {
        return fecha;
    }

    public void setFecha(LocalDate fecha) {
        this.fecha = fecha;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    public List<DetalleFactura> getDetalles() {
        return detalles;
    }

    public void agregarDetalle(DetalleFactura detalle) {
        detalles.add(detalle);
    }

    public double calcularTotal() {
        double total = 0.0;
        for (DetalleFactura detalle : detalles) {
            total += detalle.calcularSubtotal();
        }
        return total;
    }
}
