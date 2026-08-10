/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package gt.edu.umg.sistema.estudiantes.dao;

import gt.edu.umg.sistema.estudiantes.bd.ConexionMySQL;
import gt.edu.umg.sistema.estudiantes.modelo.Estudiante;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author maorozco
 */
public class EstudianteDAOImpl implements EstudianteDAO {

    @Override
    public void guardar(Estudiante estutidante) {
        String sql = "INSERT INTO estudiantes (id, nombres, apellidos, email, carnet) "
                + "VALUES (?, ?, ?, ?, ?) "
                + "ON DUPLICATE KEY UPDATE "
                + "nombres = VALUES(nombres), "
                + "apellidos = VALUES(apellidos), "
                + "email = VALUES(email), "
                + "carnet = VALUES(carnet)";

        try (Connection conn = ConexionMySQL.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, estutidante.getId());
            ps.setString(2, estutidante.getNombres());
            ps.setString(3, estutidante.getApellidos());
            ps.setString(4, estutidante.getEmail());
            ps.setString(5, estutidante.getCarnet());
            ps.executeUpdate();
        } catch (SQLException ex) {
            throw new RuntimeException("No se pudo guardar el estudiante.", ex);
        }
    }

    @Override
    public List<Estudiante> listar() {
        List<Estudiante> estudiantes = new ArrayList<>();
        String sql = "SELECT id, nombres, apellidos, email, carnet FROM estudiantes ORDER BY id";

        try (Connection conn = ConexionMySQL.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                Estudiante estudiante = new Estudiante();
                estudiante.setId(rs.getInt("id"));
                estudiante.setNombres(rs.getString("nombres"));
                estudiante.setApellidos(rs.getString("apellidos"));
                estudiante.setEmail(rs.getString("email"));
                estudiante.setCarnet(rs.getString("carnet"));
                estudiantes.add(estudiante);
            }
        } catch (SQLException ex) {
            throw new RuntimeException("No se pudo listar los estudiantes.", ex);
        }

        return estudiantes;
    }

    @Override
    public void actualizar(Estudiante estudiante) {
        guardar(estudiante);
    }

    @Override
    public void eliminar(int id) {
        String sql = "DELETE FROM estudiantes WHERE id = ?";

        try (Connection conn = ConexionMySQL.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            ps.executeUpdate();
        } catch (SQLException ex) {
            throw new RuntimeException("No se pudo eliminar el estudiante.", ex);
        }
    }
    
}
