package gt.edu.umg.sistema.estudiantes.ventas.dao;

import gt.edu.umg.sistema.estudiantes.ventas.modelo.Empleado;
import java.util.List;

public interface EmpleadoDAO {

    void guardar(Empleado empleado);

    List<Empleado> listar();
}
