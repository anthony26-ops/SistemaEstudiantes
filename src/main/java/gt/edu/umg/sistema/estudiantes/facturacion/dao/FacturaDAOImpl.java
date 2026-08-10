package gt.edu.umg.sistema.estudiantes.facturacion.dao;

import gt.edu.umg.sistema.estudiantes.facturacion.modelo.Factura;
import java.util.ArrayList;
import java.util.List;

public class FacturaDAOImpl implements FacturaDAO {

    private final List<Factura> facturas = new ArrayList<>();

    @Override
    public void guardar(Factura factura) {
        facturas.add(factura);
    }

    @Override
    public List<Factura> listar() {
        return facturas;
    }
}
