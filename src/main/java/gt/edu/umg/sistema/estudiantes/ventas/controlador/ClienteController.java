package gt.edu.umg.sistema.estudiantes.ventas.controlador;

import gt.edu.umg.sistema.estudiantes.ventas.dao.ClienteDAO;
import gt.edu.umg.sistema.estudiantes.ventas.dao.ClienteDAOImpl;
import gt.edu.umg.sistema.estudiantes.ventas.modelo.Cliente;
import java.util.List;

public class ClienteController {

    private final ClienteDAO dao = new ClienteDAOImpl();

    public void guardar(Cliente cliente) {
        dao.guardar(cliente);
    }

    public List<Cliente> listar() {
        return dao.listar();
    }
}
