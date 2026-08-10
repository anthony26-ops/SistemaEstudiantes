package gt.edu.umg.sistema.estudiantes.ventas.dao;

import gt.edu.umg.sistema.estudiantes.bd.ConexionMySQL;
import gt.edu.umg.sistema.estudiantes.ventas.modelo.Cliente;
import gt.edu.umg.sistema.estudiantes.ventas.modelo.DetalleFactura;
import gt.edu.umg.sistema.estudiantes.ventas.modelo.Factura;
import gt.edu.umg.sistema.estudiantes.ventas.modelo.Producto;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class FacturaDAOImpl implements FacturaDAO {

    private final ProductoDAOImpl productoDAO = new ProductoDAOImpl();

    @Override
    public void guardar(Factura factura) {
        String insertarFactura = "INSERT INTO ventas_facturas (id_factura, fecha, cliente_id, total) VALUES (?, ?, ?, ?) "
                + "ON DUPLICATE KEY UPDATE fecha = VALUES(fecha), cliente_id = VALUES(cliente_id), total = VALUES(total)";
        String eliminarDetalles = "DELETE FROM ventas_factura_detalles WHERE factura_id = ?";
        String insertarDetalle = "INSERT INTO ventas_factura_detalles (factura_id, producto_id, cantidad, precio_unitario) VALUES (?, ?, ?, ?)";

        try (Connection conn = ConexionMySQL.getConnection()) {
            conn.setAutoCommit(false);
            try {
                try (PreparedStatement facturaPs = conn.prepareStatement(insertarFactura)) {
                    facturaPs.setInt(1, factura.getIdFactura());
                    facturaPs.setDate(2, Date.valueOf(factura.getFecha()));
                    facturaPs.setInt(3, factura.getCliente().getId());
                    facturaPs.setDouble(4, factura.calcularTotal());
                    facturaPs.executeUpdate();
                }

                try (PreparedStatement deletePs = conn.prepareStatement(eliminarDetalles)) {
                    deletePs.setInt(1, factura.getIdFactura());
                    deletePs.executeUpdate();
                }

                for (DetalleFactura detalle : factura.getDetalles()) {
                    Producto producto = productoDAO.buscarPorId(detalle.getProducto().getIdProducto());
                    if (producto == null) {
                        throw new RuntimeException("Producto no encontrado para la factura.");
                    }
                    if (!producto.hayExistencia(detalle.getCantidad())) {
                        throw new RuntimeException("No hay existencia suficiente para " + producto.getNombre());
                    }

                    try (PreparedStatement detallePs = conn.prepareStatement(insertarDetalle)) {
                        detallePs.setInt(1, factura.getIdFactura());
                        detallePs.setInt(2, detalle.getProducto().getIdProducto());
                        detallePs.setInt(3, detalle.getCantidad());
                        detallePs.setDouble(4, detalle.getPrecioUnitario());
                        detallePs.executeUpdate();
                    }

                    productoDAO.actualizarExistencia(
                            producto.getIdProducto(),
                            producto.getExistencia() - detalle.getCantidad(),
                            () -> conn
                    );
                }

                conn.commit();
            } catch (Exception ex) {
                conn.rollback();
                throw ex;
            } finally {
                conn.setAutoCommit(true);
            }
        } catch (SQLException ex) {
            throw new RuntimeException("No se pudo guardar la factura.", ex);
        }
    }

    @Override
    public List<Factura> listar() {
        List<Factura> facturas = new ArrayList<>();
        String facturasSql = "SELECT f.id_factura, f.fecha, c.id AS cliente_id, c.nombre, c.nit, c.telefono, c.direccion "
                + "FROM ventas_facturas f INNER JOIN ventas_clientes c ON c.id = f.cliente_id ORDER BY f.id_factura DESC";
        String detallesSql = "SELECT d.producto_id, d.cantidad, d.precio_unitario, p.nombre, p.precio, p.existencia "
                + "FROM ventas_factura_detalles d INNER JOIN ventas_productos p ON p.id_producto = d.producto_id "
                + "WHERE d.factura_id = ? ORDER BY d.id_detalle";

        try (Connection conn = ConexionMySQL.getConnection();
             PreparedStatement facturaPs = conn.prepareStatement(facturasSql);
             ResultSet facturaRs = facturaPs.executeQuery()) {
            while (facturaRs.next()) {
                Factura factura = new Factura();
                factura.setIdFactura(facturaRs.getInt("id_factura"));
                factura.setFecha(facturaRs.getDate("fecha").toLocalDate());

                Cliente cliente = new Cliente();
                cliente.setId(facturaRs.getInt("cliente_id"));
                cliente.setNombre(facturaRs.getString("nombre"));
                cliente.setNit(facturaRs.getString("nit"));
                cliente.setTelefono(facturaRs.getString("telefono"));
                cliente.setDireccion(facturaRs.getString("direccion"));
                factura.setCliente(cliente);

                try (PreparedStatement detallePs = conn.prepareStatement(detallesSql)) {
                    detallePs.setInt(1, factura.getIdFactura());
                    try (ResultSet detalleRs = detallePs.executeQuery()) {
                        while (detalleRs.next()) {
                            Producto producto = new Producto();
                            producto.setIdProducto(detalleRs.getInt("producto_id"));
                            producto.setNombre(detalleRs.getString("nombre"));
                            producto.setPrecio(detalleRs.getDouble("precio"));
                            producto.setExistencia(detalleRs.getInt("existencia"));

                            DetalleFactura detalle = new DetalleFactura();
                            detalle.setProducto(producto);
                            detalle.setCantidad(detalleRs.getInt("cantidad"));
                            detalle.setPrecioUnitario(detalleRs.getDouble("precio_unitario"));
                            factura.agregarDetalle(detalle);
                        }
                    }
                }

                facturas.add(factura);
            }
        } catch (SQLException ex) {
            throw new RuntimeException("No se pudieron listar las facturas de ventas.", ex);
        }

        return facturas;
    }
}
