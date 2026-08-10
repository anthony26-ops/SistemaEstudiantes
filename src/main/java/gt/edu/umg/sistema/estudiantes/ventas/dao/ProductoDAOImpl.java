package gt.edu.umg.sistema.estudiantes.ventas.dao;

import gt.edu.umg.sistema.estudiantes.bd.ConexionMySQL;
import gt.edu.umg.sistema.estudiantes.ventas.modelo.Producto;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ProductoDAOImpl implements ProductoDAO {

    @Override
    public void guardar(Producto producto) {
        String sql = "INSERT INTO ventas_productos (id_producto, nombre, precio, existencia) VALUES (?, ?, ?, ?) "
                + "ON DUPLICATE KEY UPDATE nombre = VALUES(nombre), precio = VALUES(precio), existencia = VALUES(existencia)";

        try (Connection conn = ConexionMySQL.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, producto.getIdProducto());
            ps.setString(2, producto.getNombre());
            ps.setDouble(3, producto.getPrecio());
            ps.setInt(4, producto.getExistencia());
            ps.executeUpdate();
        } catch (SQLException ex) {
            throw new RuntimeException("No se pudo guardar el producto.", ex);
        }
    }

    @Override
    public List<Producto> listar() {
        List<Producto> productos = new ArrayList<>();
        String sql = "SELECT id_producto, nombre, precio, existencia FROM ventas_productos ORDER BY id_producto";

        try (Connection conn = ConexionMySQL.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                productos.add(mapear(rs));
            }
        } catch (SQLException ex) {
            throw new RuntimeException("No se pudieron listar los productos.", ex);
        }

        return productos;
    }

    @Override
    public Producto buscarPorId(int idProducto) {
        String sql = "SELECT id_producto, nombre, precio, existencia FROM ventas_productos WHERE id_producto = ?";

        try (Connection conn = ConexionMySQL.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, idProducto);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return mapear(rs);
                }
            }
        } catch (SQLException ex) {
            throw new RuntimeException("No se pudo buscar el producto.", ex);
        }

        return null;
    }

    @Override
    public void actualizarExistencia(int idProducto, int nuevaExistencia, ConnectionTx connectionTx) {
        String sql = "UPDATE ventas_productos SET existencia = ? WHERE id_producto = ?";
        try (PreparedStatement ps = connectionTx.getConnection().prepareStatement(sql)) {
            ps.setInt(1, nuevaExistencia);
            ps.setInt(2, idProducto);
            ps.executeUpdate();
        } catch (SQLException ex) {
            throw new RuntimeException("No se pudo actualizar la existencia del producto.", ex);
        }
    }

    private Producto mapear(ResultSet rs) throws SQLException {
        Producto producto = new Producto();
        producto.setIdProducto(rs.getInt("id_producto"));
        producto.setNombre(rs.getString("nombre"));
        producto.setPrecio(rs.getDouble("precio"));
        producto.setExistencia(rs.getInt("existencia"));
        return producto;
    }
}
