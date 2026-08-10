package gt.edu.umg.sistema.estudiantes.ventas.controlador;

import gt.edu.umg.sistema.estudiantes.ventas.dao.EmpleadoDAO;
import gt.edu.umg.sistema.estudiantes.ventas.dao.EmpleadoDAOImpl;
import gt.edu.umg.sistema.estudiantes.ventas.modelo.Empleado;
import java.util.List;

public class EmpleadoController {

    private final EmpleadoDAO dao = new EmpleadoDAOImpl();

    public void guardar(Empleado empleado) {
        dao.guardar(empleado);
    }

    public List<Empleado> listar() {
        return dao.listar();
    }
}
