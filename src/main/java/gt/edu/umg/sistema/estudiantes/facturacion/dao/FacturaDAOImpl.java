package gt.edu.umg.sistema.estudiantes.facturacion.dao;

import gt.edu.umg.sistema.estudiantes.bd.ConexionMySQL;
import gt.edu.umg.sistema.estudiantes.facturacion.modelo.DetalleFactura;
import gt.edu.umg.sistema.estudiantes.facturacion.modelo.Factura;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class FacturaDAOImpl implements FacturaDAO {

    @Override
    public void guardar(Factura factura) {
        String buscarFactura = "SELECT id FROM facturas WHERE numero_factura = ?";
        String insertarFactura = "INSERT INTO facturas (numero_factura, fecha_factura, nombre_cliente, nit_cliente, direccion_cliente, total) "
                + "VALUES (?, ?, ?, ?, ?, ?)";
        String actualizarFactura = "UPDATE facturas SET fecha_factura = ?, nombre_cliente = ?, nit_cliente = ?, direccion_cliente = ?, total = ? "
                + "WHERE id = ?";
        String eliminarDetalles = "DELETE FROM factura_detalles WHERE factura_id = ?";
        String insertarDetalle = "INSERT INTO factura_detalles (factura_id, producto, cantidad, precio, subtotal) "
                + "VALUES (?, ?, ?, ?, ?)";

        Connection conn = null;

        try {
            conn = ConexionMySQL.getConnection();
            conn.setAutoCommit(false);

            Long facturaId = null;
            try (PreparedStatement buscarPs = conn.prepareStatement(buscarFactura)) {
                buscarPs.setString(1, factura.getNumeroFactura());
                try (ResultSet rs = buscarPs.executeQuery()) {
                    if (rs.next()) {
                        facturaId = rs.getLong("id");
                    }
                }
            }

            LocalDate fecha = LocalDate.parse(factura.getFechaFactura());

            if (facturaId == null) {
                try (PreparedStatement ps = conn.prepareStatement(insertarFactura, Statement.RETURN_GENERATED_KEYS)) {
                    ps.setString(1, factura.getNumeroFactura());
                    ps.setDate(2, Date.valueOf(fecha));
                    ps.setString(3, factura.getNombreCliente());
                    ps.setString(4, factura.getNitCliente());
                    ps.setString(5, factura.getDireccionCliente());
                    ps.setDouble(6, factura.getTotal());
                    ps.executeUpdate();

                    try (ResultSet keys = ps.getGeneratedKeys()) {
                        if (keys.next()) {
                            facturaId = keys.getLong(1);
                        }
                    }
                }
            } else {
                try (PreparedStatement ps = conn.prepareStatement(actualizarFactura)) {
                    ps.setDate(1, Date.valueOf(fecha));
                    ps.setString(2, factura.getNombreCliente());
                    ps.setString(3, factura.getNitCliente());
                    ps.setString(4, factura.getDireccionCliente());
                    ps.setDouble(5, factura.getTotal());
                    ps.setLong(6, facturaId);
                    ps.executeUpdate();
                }

                try (PreparedStatement ps = conn.prepareStatement(eliminarDetalles)) {
                    ps.setLong(1, facturaId);
                    ps.executeUpdate();
                }
            }

            try (PreparedStatement detallePs = conn.prepareStatement(insertarDetalle)) {
                for (DetalleFactura detalle : factura.getDetalles()) {
                    detallePs.setLong(1, facturaId);
                    detallePs.setString(2, detalle.getProducto());
                    detallePs.setInt(3, detalle.getCantidad());
                    detallePs.setDouble(4, detalle.getPrecio());
                    detallePs.setDouble(5, detalle.getSubtotal());
                    detallePs.addBatch();
                }
                detallePs.executeBatch();
            }

            conn.commit();
            factura.setId(facturaId);
        } catch (SQLException | RuntimeException ex) {
            if (conn != null) {
                try {
                    conn.rollback();
                } catch (SQLException rollbackEx) {
                    throw new RuntimeException("No se pudo revertir la transaccion de factura.", rollbackEx);
                }
            }
            throw new RuntimeException("No se pudo guardar la factura.", ex);
        } finally {
            if (conn != null) {
                try {
                    conn.setAutoCommit(true);
                    conn.close();
                } catch (SQLException ignored) {
                    // Se ignora al cerrar.
                }
            }
        }
    }

    @Override
    public List<Factura> listar() {
        List<Factura> facturas = new ArrayList<>();
        String listarFacturas = "SELECT id, numero_factura, fecha_factura, nombre_cliente, nit_cliente, direccion_cliente, total "
                + "FROM facturas ORDER BY id DESC";
        String listarDetalles = "SELECT producto, cantidad, precio, subtotal FROM factura_detalles WHERE factura_id = ? ORDER BY id";

        try (Connection conn = ConexionMySQL.getConnection();
             PreparedStatement ps = conn.prepareStatement(listarFacturas);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                Factura factura = new Factura();
                long facturaId = rs.getLong("id");
                factura.setId(facturaId);
                factura.setNumeroFactura(rs.getString("numero_factura"));
                factura.setFechaFactura(rs.getDate("fecha_factura").toString());
                factura.setNombreCliente(rs.getString("nombre_cliente"));
                factura.setNitCliente(rs.getString("nit_cliente"));
                factura.setDireccionCliente(rs.getString("direccion_cliente"));

                try (PreparedStatement detallePs = conn.prepareStatement(listarDetalles)) {
                    detallePs.setLong(1, facturaId);
                    try (ResultSet detalleRs = detallePs.executeQuery()) {
                        while (detalleRs.next()) {
                            DetalleFactura detalle = new DetalleFactura();
                            detalle.setProducto(detalleRs.getString("producto"));
                            detalle.setCantidad(detalleRs.getInt("cantidad"));
                            detalle.setPrecio(detalleRs.getDouble("precio"));
                            detalle.setSubtotal(detalleRs.getDouble("subtotal"));
                            factura.agregarDetalle(detalle);
                        }
                    }
                }

                facturas.add(factura);
            }
        } catch (SQLException ex) {
            throw new RuntimeException("No se pudieron listar las facturas.", ex);
        }

        return facturas;
    }
}
