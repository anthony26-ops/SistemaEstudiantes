package gt.edu.umg.sistema.estudiantes.ventas.controlador;

import gt.edu.umg.sistema.estudiantes.ventas.dao.FacturaDAO;
import gt.edu.umg.sistema.estudiantes.ventas.dao.FacturaDAOImpl;
import gt.edu.umg.sistema.estudiantes.ventas.modelo.Factura;
import java.util.List;

public class VentaFacturaController {

    private final FacturaDAO dao = new FacturaDAOImpl();

    public void guardar(Factura factura) {
        dao.guardar(factura);
    }

    public List<Factura> listar() {
        return dao.listar();
    }
}
