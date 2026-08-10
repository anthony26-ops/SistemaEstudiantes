package gt.edu.umg.sistema.estudiantes.ventas.vista;

import gt.edu.umg.sistema.estudiantes.ventas.controlador.EmpleadoController;
import gt.edu.umg.sistema.estudiantes.ventas.modelo.Empleado;
import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.util.List;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

public class FrmEmpleadosVentas extends JFrame {

    private final EmpleadoController controller = new EmpleadoController();
    private final JTextField txtId = new JTextField();
    private final JTextField txtNombre = new JTextField();
    private final JTextField txtNit = new JTextField();
    private final JTextField txtTelefono = new JTextField();
    private final JTextField txtCodigo = new JTextField();
    private final JTextField txtPuesto = new JTextField();
    private final JTable tabla = new JTable();

    public FrmEmpleadosVentas() {
        setTitle("Empleados - Sistema de Ventas");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(800, 520);
        setLocationRelativeTo(null);
        initComponents();
        refrescarTabla();
    }

    private void initComponents() {
        JPanel formulario = new JPanel(new GridLayout(7, 2, 8, 8));
        formulario.add(new JLabel("ID"));
        formulario.add(txtId);
        formulario.add(new JLabel("Nombre"));
        formulario.add(txtNombre);
        formulario.add(new JLabel("NIT"));
        formulario.add(txtNit);
        formulario.add(new JLabel("Telefono"));
        formulario.add(txtTelefono);
        formulario.add(new JLabel("Codigo Empleado"));
        formulario.add(txtCodigo);
        formulario.add(new JLabel("Puesto"));
        formulario.add(txtPuesto);

        JButton btnGuardar = new JButton("Guardar Empleado");
        JButton btnLimpiar = new JButton("Limpiar");
        btnGuardar.addActionListener(evt -> guardarEmpleado());
        btnLimpiar.addActionListener(evt -> limpiarCampos());
        formulario.add(btnGuardar);
        formulario.add(btnLimpiar);

        add(formulario, BorderLayout.NORTH);
        add(new JScrollPane(tabla), BorderLayout.CENTER);
    }

    private void guardarEmpleado() {
        try {
            Empleado empleado = new Empleado();
            empleado.setId(Integer.parseInt(txtId.getText().trim()));
            empleado.setNombre(txtNombre.getText().trim());
            empleado.setNit(txtNit.getText().trim());
            empleado.setTelefono(txtTelefono.getText().trim());
            empleado.setCodigoEmpleado(txtCodigo.getText().trim());
            empleado.setPuesto(txtPuesto.getText().trim());

            controller.guardar(empleado);
            JOptionPane.showMessageDialog(this, "Empleado guardado correctamente.");
            limpiarCampos();
            refrescarTabla();
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "No se pudo guardar el empleado: " + ex.getMessage());
        }
    }

    private void refrescarTabla() {
        DefaultTableModel modelo = new DefaultTableModel(
                new Object[]{"ID", "Nombre", "NIT", "Telefono", "Codigo", "Puesto"}, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        try {
            List<Empleado> empleados = controller.listar();
            for (Empleado empleado : empleados) {
                modelo.addRow(new Object[]{
                    empleado.getId(),
                    empleado.getNombre(),
                    empleado.getNit(),
                    empleado.getTelefono(),
                    empleado.getCodigoEmpleado(),
                    empleado.getPuesto()
                });
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "No se pudo cargar empleados: " + ex.getMessage());
        }

        tabla.setModel(modelo);
    }

    private void limpiarCampos() {
        txtId.setText("");
        txtNombre.setText("");
        txtNit.setText("");
        txtTelefono.setText("");
        txtCodigo.setText("");
        txtPuesto.setText("");
    }
}
