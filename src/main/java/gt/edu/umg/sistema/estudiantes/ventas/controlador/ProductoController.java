package gt.edu.umg.sistema.estudiantes.ventas.controlador;

import gt.edu.umg.sistema.estudiantes.ventas.dao.ProductoDAO;
import gt.edu.umg.sistema.estudiantes.ventas.dao.ProductoDAOImpl;
import gt.edu.umg.sistema.estudiantes.ventas.modelo.Producto;
import java.util.List;

public class ProductoController {

    private final ProductoDAO dao = new ProductoDAOImpl();

    public void guardar(Producto producto) {
        dao.guardar(producto);
    }

    public List<Producto> listar() {
        return dao.listar();
    }
}
