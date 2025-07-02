/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JInternalFrame.java to edit this template
 */
package CapaPresentacion;

import CapaDatos.CompraDAO;
import CapaDatos.ProductoDAO;
import CapaEntidad.Compra;
import CapaEntidad.DetalleCompra;
import CapaEntidad.Producto;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author ASUS
 */
public class jifCompra extends javax.swing.JInternalFrame {

    /**
     * Creates new form jifCompra
     */
    private List<DetalleCompra> listaDetalles = new ArrayList<>();
    private DefaultTableModel modeloTabla;
    public jifCompra() {
        initComponents();
        cargarProductosEnCombo();
        modeloTabla = new DefaultTableModel();
        modeloTabla.addColumn("Producto");
        modeloTabla.addColumn("Cantidad");
        modeloTabla.addColumn("Precio");
        modeloTabla.addColumn("Subtotal");
        jtDetalleCompra.setModel(modeloTabla);
        

        jtfTotal.setEditable(false);
    }
    
    public void agregarDetalleCompra() {
        try {
            String seleccion = (String) jcProductos.getSelectedItem();
            if (seleccion == null || seleccion.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Selecciona un producto.");
                return;
            }

            String[] partes = seleccion.split(" - ");
            String idProducto = partes[0];
            String nombreProducto = partes[1];

            // 🔽 Verificar si ya fue agregado (usa idProducto)
            for (int i = 0; i < modeloTabla.getRowCount(); i++) {
                String idEnTabla = (String) modeloTabla.getValueAt(i, 0);
                if (idEnTabla.equals(nombreProducto)) {
                    JOptionPane.showMessageDialog(this, "⚠️ El producto ya fue agregado.");
                    return;
                }
            }

            int cantidad = Integer.parseInt(jtfCantidad.getText().trim());
            double precio = Double.parseDouble(jtfPrecioCompra.getText().trim());
            double subtotal = cantidad * precio;

            // ✅ Mostrar nombre del producto en la tabla
            modeloTabla.addRow(new Object[]{nombreProducto, cantidad, precio, subtotal});

            // Guardar internamente con ID
            String idBoleta = jtfBoletaCompra.getText().trim();
            DetalleCompra detalle = new DetalleCompra(idBoleta, idProducto, cantidad, precio);
            listaDetalles.add(detalle);

            actualizarTotalCompra();

        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "❌ Error al agregar producto: " + e.getMessage());
        }
    }


    
    public void actualizarTotalCompra() {
        double total = 0.0;
        for (int i = 0; i < modeloTabla.getRowCount(); i++) {
            total += Double.parseDouble(modeloTabla.getValueAt(i, 3).toString());
        }
        jtfTotal.setText(String.format("%.2f", total));
    }
    
    public void registrarCompra() {
        try {
            if (listaDetalles.isEmpty()) {
                JOptionPane.showMessageDialog(this, "No hay productos agregados.");
                return;
            }

            String idBoleta = jtfBoletaCompra.getText().trim();
            java.util.Date fechaUtil = jDateChooser1.getDate();
            if (fechaUtil == null) {
                JOptionPane.showMessageDialog(this, "Selecciona una fecha.");
                return;
            }

            LocalDate fecha = fechaUtil.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
            double total = Double.parseDouble(jtfTotal.getText().trim());

            Compra compra = new Compra(idBoleta, fecha, total);
            for (DetalleCompra d : listaDetalles) {
                compra.agregarDetalle(d);
            }

            CompraDAO dao = new CompraDAO();
            dao.registrarCompra(compra);

            JOptionPane.showMessageDialog(this, "✅ Compra registrada con éxito.");
            limpiarFormularioCompra();

        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "❌ Error al registrar compra: " + e.getMessage());
        }
    }
    
    public void limpiarFormularioCompra() {
        jtfBoletaCompra.setText("");
        jDateChooser1.setDate(null);
        jtfCantidad.setText("");
        jtfPrecioCompra.setText("");
        jtfTotal.setText("");
        modeloTabla.setRowCount(0);
        listaDetalles.clear();
    }
    
    public void cargarProductosEnCombo() {
        try {
            ProductoDAO dao = new ProductoDAO();
            List<Producto> productos = dao.listarTodos();  // Asegúrate que exista este método

            jcProductos.removeAllItems();
            for (Producto p : productos) {
                jcProductos.addItem(p.getIdProducto() + " - " + p.getNombre());
            }

        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "❌ Error al cargar productos: " + e.getMessage());
        }
    }
    
    private void abrirFormularioProducto() {
        jifProducto nuevoProducto = new jifProducto(); // Asegúrate que existe este formulario
        getParent().add(nuevoProducto);
        nuevoProducto.setVisible(true);

        // Opcional: puedes agregar un InternalFrameListener para refrescar el combo al cerrarse
        nuevoProducto.addInternalFrameListener(new javax.swing.event.InternalFrameAdapter() {
            @Override
            public void internalFrameClosed(javax.swing.event.InternalFrameEvent e) {
                cargarProductosEnCombo(); // recarga los productos
            }
        });
    }


    
    





    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        jtfBoletaCompra = new javax.swing.JTextField();
        jDateChooser1 = new com.toedter.calendar.JDateChooser();
        jLabel2 = new javax.swing.JLabel();
        jtfTotal = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jcProductos = new javax.swing.JComboBox<>();
        jLabel5 = new javax.swing.JLabel();
        jtfCantidad = new javax.swing.JTextField();
        jtfPrecioCompra = new javax.swing.JTextField();
        jLabel6 = new javax.swing.JLabel();
        jbAgregar = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        jtDetalleCompra = new javax.swing.JTable();
        jbRegistrar = new javax.swing.JButton();
        jbNuevoProducto = new javax.swing.JButton();

        jLabel1.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel1.setText("Ingresa el numero de boleta de la compra");

        jtfBoletaCompra.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N

        jDateChooser1.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N

        jLabel2.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel2.setText("Fecha de compra");

        jtfTotal.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N

        jLabel3.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel3.setText("Total");

        jLabel4.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel4.setText("Producto");

        jcProductos.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N

        jLabel5.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel5.setText("Cantidad");

        jtfCantidad.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N

        jtfPrecioCompra.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N

        jLabel6.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel6.setText("Precio Compra");

        jbAgregar.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jbAgregar.setText("Agregar");
        jbAgregar.setFocusTraversalPolicyProvider(true);
        jbAgregar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbAgregarActionPerformed(evt);
            }
        });

        jtDetalleCompra.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        jScrollPane1.setViewportView(jtDetalleCompra);

        jbRegistrar.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jbRegistrar.setText("Registrar Venta");
        jbRegistrar.setFocusTraversalPolicyProvider(true);
        jbRegistrar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbRegistrarActionPerformed(evt);
            }
        });

        jbNuevoProducto.setText("NuevoProducto");
        jbNuevoProducto.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbNuevoProductoActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGap(17, 17, 17)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jtfBoletaCompra, javax.swing.GroupLayout.PREFERRED_SIZE, 121, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel2)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jDateChooser1, javax.swing.GroupLayout.PREFERRED_SIZE, 157, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                            .addComponent(jLabel3)
                            .addGap(18, 18, 18)
                            .addComponent(jtfTotal, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jbRegistrar))
                        .addGroup(layout.createSequentialGroup()
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addGroup(layout.createSequentialGroup()
                                    .addComponent(jLabel4)
                                    .addGap(18, 18, 18)
                                    .addComponent(jcProductos, javax.swing.GroupLayout.PREFERRED_SIZE, 157, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addComponent(jbNuevoProducto, javax.swing.GroupLayout.PREFERRED_SIZE, 128, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addGroup(layout.createSequentialGroup()
                                    .addGap(50, 50, 50)
                                    .addComponent(jLabel5)
                                    .addGap(43, 43, 43)
                                    .addComponent(jtfCantidad, javax.swing.GroupLayout.PREFERRED_SIZE, 86, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGroup(layout.createSequentialGroup()
                                    .addGap(254, 254, 254)
                                    .addComponent(jLabel6)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                        .addComponent(jbAgregar, javax.swing.GroupLayout.DEFAULT_SIZE, 86, Short.MAX_VALUE)
                                        .addComponent(jtfPrecioCompra)))))))
                .addContainerGap(22, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(25, 25, 25)
                        .addComponent(jLabel2))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(23, 23, 23)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel1)
                            .addComponent(jtfBoletaCompra, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jDateChooser1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4)
                    .addComponent(jLabel5)
                    .addComponent(jtfCantidad, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jtfPrecioCompra, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel6)
                    .addComponent(jcProductos, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jbAgregar)
                    .addComponent(jbNuevoProducto))
                .addGap(15, 15, 15)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 243, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jtfTotal, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel3)
                    .addComponent(jbRegistrar))
                .addContainerGap(18, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jbAgregarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbAgregarActionPerformed
        // TODO add your handling code here:
        agregarDetalleCompra();
    }//GEN-LAST:event_jbAgregarActionPerformed

    private void jbRegistrarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbRegistrarActionPerformed
        // TODO add your handling code here:
        registrarCompra();
    }//GEN-LAST:event_jbRegistrarActionPerformed

    private void jbNuevoProductoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbNuevoProductoActionPerformed
        // TODO add your handling code here:
        abrirFormularioProducto();
    }//GEN-LAST:event_jbNuevoProductoActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private com.toedter.calendar.JDateChooser jDateChooser1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JButton jbAgregar;
    private javax.swing.JButton jbNuevoProducto;
    private javax.swing.JButton jbRegistrar;
    private javax.swing.JComboBox<String> jcProductos;
    private javax.swing.JTable jtDetalleCompra;
    private javax.swing.JTextField jtfBoletaCompra;
    private javax.swing.JTextField jtfCantidad;
    private javax.swing.JTextField jtfPrecioCompra;
    private javax.swing.JTextField jtfTotal;
    // End of variables declaration//GEN-END:variables
}
