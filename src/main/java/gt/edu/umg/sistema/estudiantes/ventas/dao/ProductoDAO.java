package gt.edu.umg.sistema.estudiantes.ventas.dao;

import gt.edu.umg.sistema.estudiantes.ventas.modelo.Producto;
import java.util.List;

public interface ProductoDAO {

    void guardar(Producto producto);

    List<Producto> listar();

    Producto buscarPorId(int idProducto);

    void actualizarExistencia(int idProducto, int nuevaExistencia, ConnectionTx connectionTx);

    interface ConnectionTx {
        java.sql.Connection getConnection();
    }
}
