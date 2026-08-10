package gt.edu.umg.sistema.estudiantes.ventas.vista;

import gt.edu.umg.sistema.estudiantes.ventas.controlador.ProductoController;
import gt.edu.umg.sistema.estudiantes.ventas.modelo.Producto;
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

public class FrmProductosVentas extends JFrame {

    private final ProductoController controller = new ProductoController();
    private final JTextField txtId = new JTextField();
    private final JTextField txtNombre = new JTextField();
    private final JTextField txtPrecio = new JTextField();
    private final JTextField txtExistencia = new JTextField();
    private final JTable tabla = new JTable();

    public FrmProductosVentas() {
        setTitle("Productos - Sistema de Ventas");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(760, 500);
        setLocationRelativeTo(null);
        initComponents();
        refrescarTabla();
    }

    private void initComponents() {
        JPanel formulario = new JPanel(new GridLayout(5, 2, 8, 8));
        formulario.add(new JLabel("ID Producto"));
        formulario.add(txtId);
        formulario.add(new JLabel("Nombre"));
        formulario.add(txtNombre);
        formulario.add(new JLabel("Precio"));
        formulario.add(txtPrecio);
        formulario.add(new JLabel("Existencia"));
        formulario.add(txtExistencia);

        JButton btnGuardar = new JButton("Guardar Producto");
        JButton btnLimpiar = new JButton("Limpiar");
        btnGuardar.addActionListener(evt -> guardarProducto());
        btnLimpiar.addActionListener(evt -> limpiarCampos());
        formulario.add(btnGuardar);
        formulario.add(btnLimpiar);

        add(formulario, BorderLayout.NORTH);
        add(new JScrollPane(tabla), BorderLayout.CENTER);
    }

    private void guardarProducto() {
        try {
            Producto producto = new Producto();
            producto.setIdProducto(Integer.parseInt(txtId.getText().trim()));
            producto.setNombre(txtNombre.getText().trim());
            producto.setPrecio(Double.parseDouble(txtPrecio.getText().trim()));
            producto.setExistencia(Integer.parseInt(txtExistencia.getText().trim()));

            controller.guardar(producto);
            JOptionPane.showMessageDialog(this, "Producto guardado correctamente.");
            limpiarCampos();
            refrescarTabla();
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "No se pudo guardar el producto: " + ex.getMessage());
        }
    }

    private void refrescarTabla() {
        DefaultTableModel modelo = new DefaultTableModel(
                new Object[]{"ID", "Nombre", "Precio", "Existencia"}, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        try {
            List<Producto> productos = controller.listar();
            for (Producto producto : productos) {
                modelo.addRow(new Object[]{
                    producto.getIdProducto(),
                    producto.getNombre(),
                    producto.getPrecio(),
                    producto.getExistencia()
                });
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "No se pudo cargar productos: " + ex.getMessage());
        }

        tabla.setModel(modelo);
    }

    private void limpiarCampos() {
        txtId.setText("");
        txtNombre.setText("");
        txtPrecio.setText("");
        txtExistencia.setText("");
    }
}
