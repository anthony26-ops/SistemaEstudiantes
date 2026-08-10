package gt.edu.umg.sistema.estudiantes.facturacion.modelo;

import java.util.ArrayList;
import java.util.List;

/**
 * Modelo simple de factura en memoria.
 */
public class Factura {

    private Long id;
    private String numeroFactura;
    private String fechaFactura;
    private String nombreCliente;
    private String nitCliente;
    private String direccionCliente;
    private final List<DetalleFactura> detalles = new ArrayList<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNumeroFactura() {
        return numeroFactura;
    }

    public void setNumeroFactura(String numeroFactura) {
        this.numeroFactura = numeroFactura;
    }

    public String getFechaFactura() {
        return fechaFactura;
    }

    public void setFechaFactura(String fechaFactura) {
        this.fechaFactura = fechaFactura;
    }

    public String getNombreCliente() {
        return nombreCliente;
    }

    public void setNombreCliente(String nombreCliente) {
        this.nombreCliente = nombreCliente;
    }

    public String getNitCliente() {
        return nitCliente;
    }

    public void setNitCliente(String nitCliente) {
        this.nitCliente = nitCliente;
    }

    public String getDireccionCliente() {
        return direccionCliente;
    }

    public void setDireccionCliente(String direccionCliente) {
        this.direccionCliente = direccionCliente;
    }

    public List<DetalleFactura> getDetalles() {
        return detalles;
    }

    public void agregarDetalle(DetalleFactura detalle) {
        this.detalles.add(detalle);
    }

    public double getTotal() {
        double total = 0.0;
        for (DetalleFactura detalle : detalles) {
            total += detalle.getSubtotal();
        }
        return total;
    }
}
