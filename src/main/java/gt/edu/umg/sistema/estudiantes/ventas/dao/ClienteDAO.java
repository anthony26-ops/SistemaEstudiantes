package gt.edu.umg.sistema.estudiantes.ventas.dao;

import gt.edu.umg.sistema.estudiantes.ventas.modelo.Cliente;
import java.util.List;

public interface ClienteDAO {

    void guardar(Cliente cliente);

    List<Cliente> listar();

    Cliente buscarPorId(int id);
}
