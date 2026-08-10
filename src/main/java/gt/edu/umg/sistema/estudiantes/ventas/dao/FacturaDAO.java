package gt.edu.umg.sistema.estudiantes.ventas.dao;

import gt.edu.umg.sistema.estudiantes.ventas.modelo.Factura;
import java.util.List;

public interface FacturaDAO {

    void guardar(Factura factura);

    List<Factura> listar();
}
