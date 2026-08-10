package gt.edu.umg.sistema.estudiantes.ventas.vista;

import gt.edu.umg.sistema.estudiantes.ventas.controlador.ClienteController;
import gt.edu.umg.sistema.estudiantes.ventas.modelo.Cliente;
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

public class FrmClientesVentas extends JFrame {

    private final ClienteController controller = new ClienteController();
    private final JTextField txtId = new JTextField();
    private final JTextField txtNombre = new JTextField();
    private final JTextField txtNit = new JTextField();
    private final JTextField txtTelefono = new JTextField();
    private final JTextField txtDireccion = new JTextField();
    private final JTable tabla = new JTable();

    public FrmClientesVentas() {
        setTitle("Clientes - Sistema de Ventas");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(760, 520);
        setLocationRelativeTo(null);
        initComponents();
        refrescarTabla();
    }

    private void initComponents() {
        JPanel formulario = new JPanel(new GridLayout(6, 2, 8, 8));
        formulario.add(new JLabel("ID"));
        formulario.add(txtId);
        formulario.add(new JLabel("Nombre"));
        formulario.add(txtNombre);
        formulario.add(new JLabel("NIT"));
        formulario.add(txtNit);
        formulario.add(new JLabel("Telefono"));
        formulario.add(txtTelefono);
        formulario.add(new JLabel("Direccion"));
        formulario.add(txtDireccion);

        JButton btnGuardar = new JButton("Guardar Cliente");
        JButton btnLimpiar = new JButton("Limpiar");
        btnGuardar.addActionListener(evt -> guardarCliente());
        btnLimpiar.addActionListener(evt -> limpiarCampos());
        formulario.add(btnGuardar);
        formulario.add(btnLimpiar);

        add(formulario, BorderLayout.NORTH);
        add(new JScrollPane(tabla), BorderLayout.CENTER);
    }

    private void guardarCliente() {
        try {
            Cliente cliente = new Cliente();
            cliente.setId(Integer.parseInt(txtId.getText().trim()));
            cliente.setNombre(txtNombre.getText().trim());
            cliente.setNit(txtNit.getText().trim());
            cliente.setTelefono(txtTelefono.getText().trim());
            cliente.setDireccion(txtDireccion.getText().trim());

            controller.guardar(cliente);
            JOptionPane.showMessageDialog(this, "Cliente guardado correctamente.");
            limpiarCampos();
            refrescarTabla();
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "No se pudo guardar el cliente: " + ex.getMessage());
        }
    }

    private void refrescarTabla() {
        DefaultTableModel modelo = new DefaultTableModel(
                new Object[]{"ID", "Nombre", "NIT", "Telefono", "Direccion"}, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        try {
            List<Cliente> clientes = controller.listar();
            for (Cliente cliente : clientes) {
                modelo.addRow(new Object[]{
                    cliente.getId(),
                    cliente.getNombre(),
                    cliente.getNit(),
                    cliente.getTelefono(),
                    cliente.getDireccion()
                });
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "No se pudo cargar clientes: " + ex.getMessage());
        }

        tabla.setModel(modelo);
    }

    private void limpiarCampos() {
        txtId.setText("");
        txtNombre.setText("");
        txtNit.setText("");
        txtTelefono.setText("");
        txtDireccion.setText("");
    }
}
