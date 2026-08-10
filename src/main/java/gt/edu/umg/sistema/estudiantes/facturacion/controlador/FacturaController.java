package gt.edu.umg.sistema.estudiantes.facturacion.controlador;

import gt.edu.umg.sistema.estudiantes.facturacion.dao.FacturaDAOImpl;
import gt.edu.umg.sistema.estudiantes.facturacion.modelo.Factura;
import java.util.List;

public class FacturaController {

    private final FacturaDAOImpl dao;

    public FacturaController() {
        this.dao = new FacturaDAOImpl();
    }

    public void guardar(Factura factura) {
        dao.guardar(factura);
    }

    public List<Factura> listar() {
        return dao.listar();
    }
}
