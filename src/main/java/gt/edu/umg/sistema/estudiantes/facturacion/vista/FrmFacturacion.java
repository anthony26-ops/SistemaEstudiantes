package gt.edu.umg.sistema.estudiantes.facturacion.vista;

import gt.edu.umg.sistema.estudiantes.facturacion.controlador.FacturaController;
import gt.edu.umg.sistema.estudiantes.facturacion.modelo.DetalleFactura;
import gt.edu.umg.sistema.estudiantes.facturacion.modelo.Factura;
import java.awt.event.ActionEvent;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.List;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

public class FrmFacturacion extends javax.swing.JFrame {

    private static final java.util.logging.Logger logger = java.util.logging.Logger.getLogger(FrmFacturacion.class.getName());

    private final FacturaController controller;

    public FrmFacturacion() {
        initComponents();
        controller = new FacturaController();
        txtFechaFactura.setText(LocalDate.now().toString());
        configurarTabla();
        actualizarTotal();
    }

    @SuppressWarnings("unchecked")
    private void initComponents() {

        lblTitulo = new javax.swing.JLabel();
        lblNumeroFactura = new javax.swing.JLabel();
        txtNumeroFactura = new javax.swing.JTextField();
        lblFechaFactura = new javax.swing.JLabel();
        txtFechaFactura = new javax.swing.JTextField();
        lblNombreCliente = new javax.swing.JLabel();
        txtNombreCliente = new javax.swing.JTextField();
        lblNitCliente = new javax.swing.JLabel();
        txtNitCliente = new javax.swing.JTextField();
        lblDireccionCliente = new javax.swing.JLabel();
        txtDireccionCliente = new javax.swing.JTextField();
        lblProducto = new javax.swing.JLabel();
        txtProducto = new javax.swing.JTextField();
        lblCantidad = new javax.swing.JLabel();
        txtCantidad = new javax.swing.JTextField();
        lblPrecio = new javax.swing.JLabel();
        txtPrecio = new javax.swing.JTextField();
        lblTotal = new javax.swing.JLabel();
        txtTotal = new javax.swing.JTextField();
        btnAgregar = new javax.swing.JButton();
        btnEliminar = new javax.swing.JButton();
        btnGuardar = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblDetalles = new javax.swing.JTable();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Formulario de Facturación");

        lblTitulo.setFont(new java.awt.Font("Segoe UI", 1, 20));
        lblTitulo.setText("Formulario de Facturación");

        lblNumeroFactura.setText("Número de factura");

        lblFechaFactura.setText("Fecha");

        lblNombreCliente.setText("Cliente");

        lblNitCliente.setText("NIT");

        lblDireccionCliente.setText("Dirección");

        lblProducto.setText("Producto");

        lblCantidad.setText("Cantidad");

        lblPrecio.setText("Precio");

        lblTotal.setText("Total de la venta");

        txtTotal.setEditable(false);

        btnAgregar.setText("Agregar");
        btnAgregar.addActionListener(this::btnAgregarActionPerformed);

        btnEliminar.setText("Eliminar");
        btnEliminar.addActionListener(this::btnEliminarActionPerformed);

        btnGuardar.setText("Guardar");
        btnGuardar.addActionListener(this::btnGuardarActionPerformed);

        tblDetalles.setModel(new javax.swing.table.DefaultTableModel(
                new Object[][]{},
                new String[]{"Producto", "Cantidad", "Precio", "Subtotal"}
        ) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        });
        jScrollPane1.setViewportView(tblDetalles);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(layout.createSequentialGroup()
                                .addGap(24, 24, 24)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(jScrollPane1)
                                        .addGroup(layout.createSequentialGroup()
                                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                        .addComponent(lblNumeroFactura)
                                                        .addComponent(lblFechaFactura)
                                                        .addComponent(lblNombreCliente)
                                                        .addComponent(lblNitCliente)
                                                        .addComponent(lblDireccionCliente)
                                                        .addComponent(lblProducto)
                                                        .addComponent(lblCantidad)
                                                        .addComponent(lblPrecio)
                                                        .addComponent(lblTotal))
                                                .addGap(18, 18, 18)
                                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                        .addComponent(txtNumeroFactura)
                                                        .addComponent(txtFechaFactura)
                                                        .addComponent(txtNombreCliente)
                                                        .addComponent(txtNitCliente)
                                                        .addComponent(txtDireccionCliente)
                                                        .addComponent(txtProducto)
                                                        .addComponent(txtCantidad, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                        .addComponent(txtPrecio, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                        .addComponent(txtTotal, javax.swing.GroupLayout.PREFERRED_SIZE, 180, javax.swing.GroupLayout.PREFERRED_SIZE))
                                                .addGap(24, 24, 24)
                                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                                        .addComponent(btnAgregar, javax.swing.GroupLayout.DEFAULT_SIZE, 110, Short.MAX_VALUE)
                                                        .addComponent(btnEliminar, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                                        .addComponent(btnGuardar, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                                        .addComponent(lblTitulo))
                                .addGap(24, 24, 24))
        );
        layout.setVerticalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(layout.createSequentialGroup()
                                .addGap(18, 18, 18)
                                .addComponent(lblTitulo)
                                .addGap(18, 18, 18)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(lblNumeroFactura)
                                        .addComponent(txtNumeroFactura, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(12, 12, 12)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(lblFechaFactura)
                                        .addComponent(txtFechaFactura, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(12, 12, 12)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(lblNombreCliente)
                                        .addComponent(txtNombreCliente, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(12, 12, 12)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(lblNitCliente)
                                        .addComponent(txtNitCliente, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(12, 12, 12)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(lblDireccionCliente)
                                        .addComponent(txtDireccionCliente, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(18, 18, 18)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(lblProducto)
                                        .addComponent(txtProducto, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(btnAgregar))
                                .addGap(12, 12, 12)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(lblCantidad)
                                        .addComponent(txtCantidad, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(btnEliminar))
                                .addGap(12, 12, 12)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(lblPrecio)
                                        .addComponent(txtPrecio, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(btnGuardar))
                                .addGap(12, 12, 12)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(lblTotal)
                                        .addComponent(txtTotal, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(18, 18, 18)
                                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 238, Short.MAX_VALUE)
                                .addGap(18, 18, 18))
        );

        pack();
    }

    private void configurarTabla() {
        tblDetalles.setModel(new javax.swing.table.DefaultTableModel(
                new Object[][]{},
                new String[]{"Producto", "Cantidad", "Precio", "Subtotal"}
        ) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        });
    }

    private void btnAgregarActionPerformed(ActionEvent evt) {
        String producto = txtProducto.getText().trim();
        if (producto.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Ingrese el nombre del producto.");
            return;
        }

        int cantidad;
        double precio;

        try {
            cantidad = Integer.parseInt(txtCantidad.getText().trim());
            precio = Double.parseDouble(txtPrecio.getText().trim().replace(',', '.'));
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Cantidad y precio deben ser numéricos.");
            return;
        }

        if (cantidad <= 0 || precio < 0) {
            JOptionPane.showMessageDialog(this, "Cantidad debe ser mayor a cero y precio no puede ser negativo.");
            return;
        }

        double subtotal = cantidad * precio;
        DefaultTableModel modelo = obtenerModeloTabla();
        modelo.addRow(new Object[]{
            producto,
            cantidad,
            String.format("%.2f", precio),
            String.format("%.2f", subtotal)
        });
        actualizarTotal();
        limpiarCamposProducto();
    }

    private void btnEliminarActionPerformed(ActionEvent evt) {
        int fila = tblDetalles.getSelectedRow();
        if (fila < 0) {
            JOptionPane.showMessageDialog(this, "Seleccione una fila para eliminar.");
            return;
        }

        DefaultTableModel modelo = obtenerModeloTabla();
        modelo.removeRow(fila);
        actualizarTotal();
    }

    private void btnGuardarActionPerformed(ActionEvent evt) {
        if (txtNumeroFactura.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Ingrese el número de factura.");
            return;
        }

        if (txtNombreCliente.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Ingrese la información del cliente.");
            return;
        }

        if (tblDetalles.getRowCount() == 0) {
            JOptionPane.showMessageDialog(this, "Agregue al menos un producto.");
            return;
        }

        if (!fechaValida(txtFechaFactura.getText().trim())) {
            JOptionPane.showMessageDialog(this, "La fecha debe tener el formato yyyy-MM-dd.");
            return;
        }

        Factura factura = new Factura();
        factura.setNumeroFactura(txtNumeroFactura.getText().trim());
        factura.setFechaFactura(txtFechaFactura.getText().trim());
        factura.setNombreCliente(txtNombreCliente.getText().trim());
        factura.setNitCliente(txtNitCliente.getText().trim());
        factura.setDireccionCliente(txtDireccionCliente.getText().trim());

        DefaultTableModel modelo = obtenerModeloTabla();
        for (int i = 0; i < modelo.getRowCount(); i++) {
            String producto = String.valueOf(modelo.getValueAt(i, 0));
            int cantidad = Integer.parseInt(String.valueOf(modelo.getValueAt(i, 1)));
            double precio = Double.parseDouble(String.valueOf(modelo.getValueAt(i, 2)).replace(',', '.'));
            factura.agregarDetalle(new DetalleFactura(producto, cantidad, precio));
        }

        controller.guardar(factura);

        JOptionPane.showMessageDialog(this, "Factura guardada con éxito. Total: " + String.format("%.2f", factura.getTotal()));
        limpiarFormulario();
    }

    private boolean fechaValida(String fecha) {
        try {
            LocalDate.parse(fecha);
            return true;
        } catch (DateTimeParseException ex) {
            logger.fine("Fecha inválida: " + fecha);
            return false;
        }
    }

    private DefaultTableModel obtenerModeloTabla() {
        return (DefaultTableModel) tblDetalles.getModel();
    }

    private void actualizarTotal() {
        DefaultTableModel modelo = obtenerModeloTabla();
        double total = 0.0;
        for (int i = 0; i < modelo.getRowCount(); i++) {
            Object valorSubtotal = modelo.getValueAt(i, 3);
            if (valorSubtotal != null) {
                total += Double.parseDouble(String.valueOf(valorSubtotal).replace(',', '.'));
            }
        }
        txtTotal.setText(String.format("%.2f", total));
    }

    private void limpiarCamposProducto() {
        txtProducto.setText("");
        txtCantidad.setText("");
        txtPrecio.setText("");
        txtProducto.requestFocus();
    }

    private void limpiarFormulario() {
        txtNumeroFactura.setText("");
        txtFechaFactura.setText(LocalDate.now().toString());
        txtNombreCliente.setText("");
        txtNitCliente.setText("");
        txtDireccionCliente.setText("");
        limpiarCamposProducto();
        obtenerModeloTabla().setRowCount(0);
        actualizarTotal();
    }

    public List<Factura> getFacturasGuardadas() {
        return controller.listar();
    }

    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(() -> new FrmFacturacion().setVisible(true));
    }

    // Variables declaration - do not modify
    private javax.swing.JButton btnAgregar;
    private javax.swing.JButton btnEliminar;
    private javax.swing.JButton btnGuardar;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel lblCantidad;
    private javax.swing.JLabel lblDireccionCliente;
    private javax.swing.JLabel lblFechaFactura;
    private javax.swing.JLabel lblNitCliente;
    private javax.swing.JLabel lblNombreCliente;
    private javax.swing.JLabel lblNumeroFactura;
    private javax.swing.JLabel lblPrecio;
    private javax.swing.JLabel lblProducto;
    private javax.swing.JLabel lblTitulo;
    private javax.swing.JLabel lblTotal;
    private javax.swing.JTable tblDetalles;
    private javax.swing.JTextField txtCantidad;
    private javax.swing.JTextField txtDireccionCliente;
    private javax.swing.JTextField txtFechaFactura;
    private javax.swing.JTextField txtNitCliente;
    private javax.swing.JTextField txtNombreCliente;
    private javax.swing.JTextField txtNumeroFactura;
    private javax.swing.JTextField txtPrecio;
    private javax.swing.JTextField txtProducto;
    private javax.swing.JTextField txtTotal;
    // End of variables declaration
}
