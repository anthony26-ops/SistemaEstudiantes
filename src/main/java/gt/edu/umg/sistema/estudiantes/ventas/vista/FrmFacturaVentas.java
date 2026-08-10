package gt.edu.umg.sistema.estudiantes.ventas.vista;

import gt.edu.umg.sistema.estudiantes.ventas.controlador.ClienteController;
import gt.edu.umg.sistema.estudiantes.ventas.controlador.ProductoController;
import gt.edu.umg.sistema.estudiantes.ventas.controlador.VentaFacturaController;
import gt.edu.umg.sistema.estudiantes.ventas.modelo.Cliente;
import gt.edu.umg.sistema.estudiantes.ventas.modelo.DetalleFactura;
import gt.edu.umg.sistema.estudiantes.ventas.modelo.Factura;
import gt.edu.umg.sistema.estudiantes.ventas.modelo.Producto;
import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

public class FrmFacturaVentas extends JFrame {

    private final VentaFacturaController facturaController = new VentaFacturaController();
    private final ClienteController clienteController = new ClienteController();
    private final ProductoController productoController = new ProductoController();

    private final JTextField txtIdFactura = new JTextField();
    private final JTextField txtFecha = new JTextField(LocalDate.now().toString());
    private final JComboBox<Cliente> cmbClientes = new JComboBox<>();
    private final JComboBox<Producto> cmbProductos = new JComboBox<>();
    private final JTextField txtCantidad = new JTextField();
    private final JTextField txtPrecioUnitario = new JTextField();
    private final JTextField txtTotal = new JTextField();
    private final JTable tablaDetalles = new JTable();
    private final JTable tablaFacturas = new JTable();
    private final List<DetalleFactura> detallesActuales = new ArrayList<>();

    public FrmFacturaVentas() {
        setTitle("Facturacion UML - Sistema de Ventas");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(980, 650);
        setLocationRelativeTo(null);
        initComponents();
        cargarDatosRelacionados();
        refrescarFacturas();
    }

    private void initComponents() {
        JPanel superior = new JPanel(new GridLayout(5, 4, 8, 8));
        superior.add(new JLabel("ID Factura"));
        superior.add(txtIdFactura);
        superior.add(new JLabel("Fecha"));
        superior.add(txtFecha);
        superior.add(new JLabel("Cliente"));
        superior.add(cmbClientes);
        superior.add(new JLabel("Producto"));
        superior.add(cmbProductos);
        superior.add(new JLabel("Cantidad"));
        superior.add(txtCantidad);
        superior.add(new JLabel("Precio Unitario"));
        superior.add(txtPrecioUnitario);
        superior.add(new JLabel("Total"));
        txtTotal.setEditable(false);
        superior.add(txtTotal);

        JButton btnAgregar = new JButton("Agregar Detalle");
        JButton btnEliminar = new JButton("Eliminar Detalle");
        JButton btnGuardar = new JButton("Guardar Factura");
        JButton btnNueva = new JButton("Nueva Factura");

        btnAgregar.addActionListener(evt -> agregarDetalle());
        btnEliminar.addActionListener(evt -> eliminarDetalle());
        btnGuardar.addActionListener(evt -> guardarFactura());
        btnNueva.addActionListener(evt -> limpiarFactura());
        cmbProductos.addActionListener(evt -> actualizarPrecioProducto());

        superior.add(btnAgregar);
        superior.add(btnEliminar);
        superior.add(btnGuardar);
        superior.add(btnNueva);

        add(superior, BorderLayout.NORTH);

        JSplitPane splitPane = new JSplitPane(JSplitPane.VERTICAL_SPLIT, new JScrollPane(tablaDetalles), new JScrollPane(tablaFacturas));
        splitPane.setResizeWeight(0.5);
        add(splitPane, BorderLayout.CENTER);
        refrescarDetalles();
    }

    private void cargarDatosRelacionados() {
        try {
            cmbClientes.removeAllItems();
            for (Cliente cliente : clienteController.listar()) {
                cmbClientes.addItem(cliente);
            }

            cmbProductos.removeAllItems();
            for (Producto producto : productoController.listar()) {
                cmbProductos.addItem(producto);
            }

            actualizarPrecioProducto();
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "No se pudieron cargar clientes/productos: " + ex.getMessage());
        }
    }

    private void actualizarPrecioProducto() {
        Producto producto = (Producto) cmbProductos.getSelectedItem();
        if (producto != null) {
            txtPrecioUnitario.setText(String.format("%.2f", producto.getPrecio()));
        }
    }

    private void agregarDetalle() {
        try {
            Producto producto = (Producto) cmbProductos.getSelectedItem();
            if (producto == null) {
                throw new IllegalArgumentException("Debe registrar productos antes de facturar.");
            }

            int cantidad = Integer.parseInt(txtCantidad.getText().trim());
            if (!producto.hayExistencia(cantidad)) {
                throw new IllegalArgumentException("No hay existencia suficiente para el producto seleccionado.");
            }

            DetalleFactura detalle = new DetalleFactura();
            detalle.setProducto(producto);
            detalle.setCantidad(cantidad);
            detalle.setPrecioUnitario(Double.parseDouble(txtPrecioUnitario.getText().trim()));
            detallesActuales.add(detalle);

            refrescarDetalles();
            txtCantidad.setText("");
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "No se pudo agregar el detalle: " + ex.getMessage());
        }
    }

    private void eliminarDetalle() {
        int fila = tablaDetalles.getSelectedRow();
        if (fila >= 0) {
            detallesActuales.remove(fila);
            refrescarDetalles();
        }
    }

    private void guardarFactura() {
        try {
            if (detallesActuales.isEmpty()) {
                throw new IllegalArgumentException("Agrega al menos un detalle a la factura.");
            }

            Cliente cliente = (Cliente) cmbClientes.getSelectedItem();
            if (cliente == null) {
                throw new IllegalArgumentException("Debe registrar clientes antes de facturar.");
            }

            Factura factura = new Factura();
            factura.setIdFactura(Integer.parseInt(txtIdFactura.getText().trim()));
            factura.setFecha(LocalDate.parse(txtFecha.getText().trim()));
            factura.setCliente(cliente);
            for (DetalleFactura detalle : detallesActuales) {
                factura.agregarDetalle(detalle);
            }

            facturaController.guardar(factura);
            JOptionPane.showMessageDialog(this, "Factura guardada correctamente.");
            limpiarFactura();
            cargarDatosRelacionados();
            refrescarFacturas();
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "No se pudo guardar la factura: " + ex.getMessage());
        }
    }

    private void refrescarDetalles() {
        DefaultTableModel modelo = new DefaultTableModel(
                new Object[]{"Producto", "Cantidad", "Precio Unitario", "Subtotal"}, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        double total = 0.0;
        for (DetalleFactura detalle : detallesActuales) {
            double subtotal = detalle.calcularSubtotal();
            total += subtotal;
            modelo.addRow(new Object[]{
                detalle.getProducto().getNombre(),
                detalle.getCantidad(),
                detalle.getPrecioUnitario(),
                subtotal
            });
        }

        tablaDetalles.setModel(modelo);
        txtTotal.setText(String.format("%.2f", total));
    }

    private void refrescarFacturas() {
        DefaultTableModel modelo = new DefaultTableModel(
                new Object[]{"ID Factura", "Fecha", "Cliente", "Detalles", "Total"}, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        try {
            List<Factura> facturas = facturaController.listar();
            for (Factura factura : facturas) {
                modelo.addRow(new Object[]{
                    factura.getIdFactura(),
                    factura.getFecha(),
                    factura.getCliente().getNombre(),
                    factura.getDetalles().size(),
                    factura.calcularTotal()
                });
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "No se pudieron cargar facturas: " + ex.getMessage());
        }

        tablaFacturas.setModel(modelo);
    }

    private void limpiarFactura() {
        txtIdFactura.setText("");
        txtFecha.setText(LocalDate.now().toString());
        txtCantidad.setText("");
        detallesActuales.clear();
        refrescarDetalles();
    }
}
