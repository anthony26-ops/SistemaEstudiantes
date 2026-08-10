package gt.edu.umg.sistema.estudiantes.ventas.dao;

import gt.edu.umg.sistema.estudiantes.bd.ConexionMySQL;
import gt.edu.umg.sistema.estudiantes.ventas.modelo.Empleado;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class EmpleadoDAOImpl implements EmpleadoDAO {

    @Override
    public void guardar(Empleado empleado) {
        String sql = "INSERT INTO ventas_empleados (id, nombre, nit, telefono, codigo_empleado, puesto) VALUES (?, ?, ?, ?, ?, ?) "
                + "ON DUPLICATE KEY UPDATE nombre = VALUES(nombre), nit = VALUES(nit), telefono = VALUES(telefono), "
                + "codigo_empleado = VALUES(codigo_empleado), puesto = VALUES(puesto)";

        try (Connection conn = ConexionMySQL.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, empleado.getId());
            ps.setString(2, empleado.getNombre());
            ps.setString(3, empleado.getNit());
            ps.setString(4, empleado.getTelefono());
            ps.setString(5, empleado.getCodigoEmpleado());
            ps.setString(6, empleado.getPuesto());
            ps.executeUpdate();
        } catch (SQLException ex) {
            throw new RuntimeException("No se pudo guardar el empleado.", ex);
        }
    }

    @Override
    public List<Empleado> listar() {
        List<Empleado> empleados = new ArrayList<>();
        String sql = "SELECT id, nombre, nit, telefono, codigo_empleado, puesto FROM ventas_empleados ORDER BY id";

        try (Connection conn = ConexionMySQL.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                Empleado empleado = new Empleado();
                empleado.setId(rs.getInt("id"));
                empleado.setNombre(rs.getString("nombre"));
                empleado.setNit(rs.getString("nit"));
                empleado.setTelefono(rs.getString("telefono"));
                empleado.setCodigoEmpleado(rs.getString("codigo_empleado"));
                empleado.setPuesto(rs.getString("puesto"));
                empleados.add(empleado);
            }
        } catch (SQLException ex) {
            throw new RuntimeException("No se pudieron listar los empleados.", ex);
        }

        return empleados;
    }
}
