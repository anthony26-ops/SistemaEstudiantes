package gt.edu.umg.sistema.estudiantes.ventas.dao;

import gt.edu.umg.sistema.estudiantes.bd.ConexionMySQL;
import gt.edu.umg.sistema.estudiantes.ventas.modelo.Cliente;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ClienteDAOImpl implements ClienteDAO {

    @Override
    public void guardar(Cliente cliente) {
        String sql = "INSERT INTO ventas_clientes (id, nombre, nit, telefono, direccion) VALUES (?, ?, ?, ?, ?) "
                + "ON DUPLICATE KEY UPDATE nombre = VALUES(nombre), nit = VALUES(nit), telefono = VALUES(telefono), direccion = VALUES(direccion)";

        try (Connection conn = ConexionMySQL.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, cliente.getId());
            ps.setString(2, cliente.getNombre());
            ps.setString(3, cliente.getNit());
            ps.setString(4, cliente.getTelefono());
            ps.setString(5, cliente.getDireccion());
            ps.executeUpdate();
        } catch (SQLException ex) {
            throw new RuntimeException("No se pudo guardar el cliente.", ex);
        }
    }

    @Override
    public List<Cliente> listar() {
        List<Cliente> clientes = new ArrayList<>();
        String sql = "SELECT id, nombre, nit, telefono, direccion FROM ventas_clientes ORDER BY id";

        try (Connection conn = ConexionMySQL.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                clientes.add(mapear(rs));
            }
        } catch (SQLException ex) {
            throw new RuntimeException("No se pudieron listar los clientes.", ex);
        }

        return clientes;
    }

    @Override
    public Cliente buscarPorId(int id) {
        String sql = "SELECT id, nombre, nit, telefono, direccion FROM ventas_clientes WHERE id = ?";

        try (Connection conn = ConexionMySQL.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return mapear(rs);
                }
            }
        } catch (SQLException ex) {
            throw new RuntimeException("No se pudo buscar el cliente.", ex);
        }

        return null;
    }

    private Cliente mapear(ResultSet rs) throws SQLException {
        Cliente cliente = new Cliente();
        cliente.setId(rs.getInt("id"));
        cliente.setNombre(rs.getString("nombre"));
        cliente.setNit(rs.getString("nit"));
        cliente.setTelefono(rs.getString("telefono"));
        cliente.setDireccion(rs.getString("direccion"));
        return cliente;
    }
}
