package gt.edu.umg.sistema.estudiantes.vista;

import gt.edu.umg.sistema.estudiantes.ventas.vista.FrmClientesVentas;
import gt.edu.umg.sistema.estudiantes.ventas.vista.FrmEmpleadosVentas;
import gt.edu.umg.sistema.estudiantes.ventas.vista.FrmFacturaVentas;
import gt.edu.umg.sistema.estudiantes.ventas.vista.FrmProductosVentas;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;

public class FrmMenuPrincipal extends javax.swing.JFrame {

    public FrmMenuPrincipal() {
        initComponents();
    }

    @SuppressWarnings("unchecked")
    private void initComponents() {

        lblTitulo = new javax.swing.JLabel();
        btnEstudiantes = new javax.swing.JButton();
        btnClientes = new javax.swing.JButton();
        btnEmpleados = new javax.swing.JButton();
        btnProductos = new javax.swing.JButton();
        btnFacturacion = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Menu Principal - Sistema de Ventas");

        setJMenuBar(crearMenu());

        lblTitulo.setFont(new java.awt.Font("Segoe UI", 1, 22));
        lblTitulo.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblTitulo.setText("Seleccione un modulo");

        btnEstudiantes.setText("Ir a Estudiantes");
        btnEstudiantes.addActionListener(this::btnEstudiantesActionPerformed);

        btnClientes.setText("Clientes");
        btnClientes.addActionListener(evt -> new FrmClientesVentas().setVisible(true));

        btnEmpleados.setText("Empleados");
        btnEmpleados.addActionListener(evt -> new FrmEmpleadosVentas().setVisible(true));

        btnProductos.setText("Productos");
        btnProductos.addActionListener(evt -> new FrmProductosVentas().setVisible(true));

        btnFacturacion.setText("Facturacion UML");
        btnFacturacion.addActionListener(this::btnFacturacionActionPerformed);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(layout.createSequentialGroup()
                                .addGap(36, 36, 36)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(lblTitulo, javax.swing.GroupLayout.DEFAULT_SIZE, 328, Short.MAX_VALUE)
                                        .addComponent(btnEstudiantes, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(btnClientes, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(btnEmpleados, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(btnProductos, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(btnFacturacion, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                .addGap(36, 36, 36))
        );
        layout.setVerticalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(layout.createSequentialGroup()
                                .addGap(36, 36, 36)
                                .addComponent(lblTitulo)
                                .addGap(32, 32, 32)
                                .addComponent(btnEstudiantes, javax.swing.GroupLayout.PREFERRED_SIZE, 48, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(12, 12, 12)
                                .addComponent(btnClientes, javax.swing.GroupLayout.PREFERRED_SIZE, 48, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(12, 12, 12)
                                .addComponent(btnEmpleados, javax.swing.GroupLayout.PREFERRED_SIZE, 48, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(12, 12, 12)
                                .addComponent(btnProductos, javax.swing.GroupLayout.PREFERRED_SIZE, 48, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(btnFacturacion, javax.swing.GroupLayout.PREFERRED_SIZE, 48, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addContainerGap(44, Short.MAX_VALUE))
        );

        pack();
        setLocationRelativeTo(null);
    }

    private void btnEstudiantesActionPerformed(java.awt.event.ActionEvent evt) {
        FrmEstudiante ventana = new FrmEstudiante();
        ventana.setLocationRelativeTo(null);
        ventana.setVisible(true);
    }

    private void btnFacturacionActionPerformed(java.awt.event.ActionEvent evt) {
        try {
            FrmFacturaVentas ventana = new FrmFacturaVentas();
            ventana.setLocationRelativeTo(null);
            ventana.setVisible(true);
        } catch (Throwable ex) {
            ex.printStackTrace();
            javax.swing.JOptionPane.showMessageDialog(this,
                    "No se pudo abrir facturación.\n" + ex.getClass().getName() + ": " + ex.getMessage(),
                    "Error",
                    javax.swing.JOptionPane.ERROR_MESSAGE);
        }
    }

    private JMenuBar crearMenu() {
        JMenuBar menuBar = new JMenuBar();
        JMenu menuModulos = new JMenu("Modulos");

        JMenuItem itemEstudiantes = new JMenuItem("Estudiantes");
        itemEstudiantes.addActionListener(this::btnEstudiantesActionPerformed);

        JMenuItem itemClientes = new JMenuItem("Clientes");
        itemClientes.addActionListener(evt -> new FrmClientesVentas().setVisible(true));

        JMenuItem itemEmpleados = new JMenuItem("Empleados");
        itemEmpleados.addActionListener(evt -> new FrmEmpleadosVentas().setVisible(true));

        JMenuItem itemProductos = new JMenuItem("Productos");
        itemProductos.addActionListener(evt -> new FrmProductosVentas().setVisible(true));

        JMenuItem itemFacturas = new JMenuItem("Facturacion UML");
        itemFacturas.addActionListener(this::btnFacturacionActionPerformed);

        menuModulos.add(itemEstudiantes);
        menuModulos.add(itemClientes);
        menuModulos.add(itemEmpleados);
        menuModulos.add(itemProductos);
        menuModulos.add(itemFacturas);
        menuBar.add(menuModulos);
        return menuBar;
    }

    // Variables declaration - do not modify
    private javax.swing.JButton btnEstudiantes;
    private javax.swing.JButton btnClientes;
    private javax.swing.JButton btnEmpleados;
    private javax.swing.JButton btnFacturacion;
    private javax.swing.JButton btnProductos;
    private javax.swing.JLabel lblTitulo;
    // End of variables declaration
}
