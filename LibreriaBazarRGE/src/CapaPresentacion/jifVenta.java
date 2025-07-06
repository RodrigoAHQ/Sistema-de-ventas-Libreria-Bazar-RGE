/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JInternalFrame.java to edit this template
 */
package CapaPresentacion;

import CapaDatos.ProductoDAO;
import CapaDatos.VentaDAO;
import CapaEntidad.DetalleVenta;
import CapaEntidad.Producto;
import CapaEntidad.Venta;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author ASUS
 */
public class jifVenta extends javax.swing.JInternalFrame {

    /**
     * Creates new form jifVenta
     */
    private List<DetalleVenta> listaDetalles = new ArrayList<>();

    public jifVenta() {
        initComponents();
        cargarProductosEnComboBox();
        configurarTablaVenta();
        cargarFormasDePago();
        setClosable(true);
    }
    
    private void cargarProductosEnComboBox() {
        try {
            ProductoDAO dao = new ProductoDAO();
            List<Producto> productos = dao.listarTodos();
            jcSeleccionarProducto.removeAllItems();

            for (Producto p : productos) {
                jcSeleccionarProducto.addItem(p.getIdProducto() + " - " + p.getNombre());
            }

        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error al cargar productos: " + e.getMessage());
        }
    }
    
    private void configurarTablaVenta() {
        DefaultTableModel modeloVenta = new DefaultTableModel();
        modeloVenta.addColumn("Producto");
        modeloVenta.addColumn("Descripción");
        modeloVenta.addColumn("Precio Unitario");
        modeloVenta.addColumn("Cantidad");
        modeloVenta.addColumn("Subtotal");
        jtVenta.setModel(modeloVenta);

        // Bloquear edición del campo total
        jtfTotalPagar.setEditable(false);
        jtfTotalPagar.setFocusable(false);
        jtfTotalPagar.setBackground(new java.awt.Color(230,230,230));
    }
    
    private void agregarProductoAVenta() {
        try {
            String seleccion = (String) jcSeleccionarProducto.getSelectedItem();
            if (seleccion == null || seleccion.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Selecciona un producto.");
                return;
            }

            String id = seleccion.split(" - ")[0];

            ProductoDAO dao = new ProductoDAO();
            Producto p = dao.obtenerPorId(id);

            // Validar cantidad
            String cantidadTexto = jtfCantidad.getText().trim();
            if (cantidadTexto.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Ingresa la cantidad a vender.");
                return;
            }

            int cantidad;
            try {
                cantidad = Integer.parseInt(cantidadTexto);
            } catch (NumberFormatException nfe) {
                JOptionPane.showMessageDialog(this, "La cantidad debe ser un número entero válido.");
                return;
            }

            if (cantidad <= 0) {
                JOptionPane.showMessageDialog(this, "La cantidad debe ser mayor a 0.");
                return;
            }

            // Verificar si ya existe en la tabla
            DefaultTableModel modelo = (DefaultTableModel) jtVenta.getModel();
            boolean encontrado = false;
            for (int i = 0; i < modelo.getRowCount(); i++) {
                String nombreTabla = modelo.getValueAt(i, 0).toString();
                if (nombreTabla.equalsIgnoreCase(p.getNombre())) {
                    // Ya existe, sumar cantidades y subtotal
                    int cantidadActual = Integer.parseInt(modelo.getValueAt(i, 3).toString());
                    int nuevaCantidad = cantidadActual + cantidad;
                    if (nuevaCantidad > p.getStock()) {
                        JOptionPane.showMessageDialog(this, "Stock insuficiente. Stock disponible: " + p.getStock());
                        return;
                    }
                    modelo.setValueAt(nuevaCantidad, i, 3); // cantidad
                    modelo.setValueAt(nuevaCantidad * p.getPrecioVenta(), i, 4); // subtotal

                    // Actualizar en listaDetalles
                    for (DetalleVenta d : listaDetalles) {
                        if (d.getIdProducto().equals(p.getIdProducto())) {
                            d.setCantidad(nuevaCantidad);
                            break;
                        }
                    }

                    encontrado = true;
                    break;
                }
            }

            if (!encontrado) {
                if (cantidad > p.getStock()) {
                    JOptionPane.showMessageDialog(this, "Stock insuficiente. Stock disponible: " + p.getStock());
                    return;
                }

                double subtotal = p.getPrecioVenta() * cantidad;
                modelo.addRow(new Object[]{
                    p.getNombre(),
                    p.getDescripcion(),
                    p.getPrecioVenta(),
                    cantidad,
                    subtotal
                });

                DetalleVenta detalle = new DetalleVenta();
                detalle.setIdProducto(p.getIdProducto());
                detalle.setCantidad(cantidad);
                detalle.setPrecioUnitario(p.getPrecioVenta());
                detalle.setTipoItem(jcTipoItem.getSelectedItem().toString()); // 👈 asignar tipoItem aquí
                listaDetalles.add(detalle);

            }

            actualizarTotal();
            jtfCantidad.setText("");

        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error al agregar producto: " + e.getMessage());
        }
}

    


    
    private void actualizarTotal() {
        double total = 0.0;
        DefaultTableModel modelo = (DefaultTableModel) jtVenta.getModel();
        for (int i = 0; i < modelo.getRowCount(); i++) {
            total += Double.parseDouble(modelo.getValueAt(i, 4).toString());
        }
        jtfTotalPagar.setText(String.format("%.2f", total));
    }
    
    private void registrarVenta() {
        if (listaDetalles.isEmpty()) {
            JOptionPane.showMessageDialog(this, "No hay productos en la venta.");
            return;
        }

        try {
            // Obtener ID de forma de pago
            String seleccionPago = (String) jcFormaPago.getSelectedItem();
            if (seleccionPago == null || seleccionPago.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Selecciona una forma de pago.");
                return;
            }

            int idFormaPago = Integer.parseInt(seleccionPago.split(" - ")[0]);

            // Calcular total
            double total = listaDetalles.stream()
                    .mapToDouble(d -> d.getPrecioUnitario() * d.getCantidad())
                    .sum();

            
            Venta venta = new Venta(java.time.LocalDateTime.now(), total, idFormaPago);

            // Registrar en la base de datos
            VentaDAO dao = new VentaDAO();
            dao.registrarVentaConDetalle(venta, listaDetalles);

            JOptionPane.showMessageDialog(this, "✅ Venta registrada correctamente.");
            limpiarFormularioVenta();

        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "❌ Error al registrar venta: " + e.getMessage());
        }
    }


    
    private void limpiarFormularioVenta() {
        jtfBuscarProducto.setText("");
        jtfCantidad.setText("");
        jtfTotalPagar.setText("");
        listaDetalles.clear();
        ((DefaultTableModel) jtVenta.getModel()).setRowCount(0); // limpia tabla
    }
    
    private void cargarFormasDePago() {
        try {
            CapaDatos.FormaPagoDAO dao = new CapaDatos.FormaPagoDAO();
            List<CapaEntidad.FormaPago> formas = dao.listarTodas();

            jcFormaPago.removeAllItems();
            for (CapaEntidad.FormaPago f : formas) {
                jcFormaPago.addItem(f.getIdFormaPago() + " - " + f.getDescripcion());
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "❌ Error al cargar formas de pago: " + e.getMessage());
        }
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
        jLabel2 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jtVenta = new javax.swing.JTable();
        jLabel3 = new javax.swing.JLabel();
        jtfBuscarProducto = new javax.swing.JTextField();
        jbBuscar = new javax.swing.JButton();
        jbAgregar = new javax.swing.JButton();
        jLabel4 = new javax.swing.JLabel();
        jtfTotalPagar = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        jcSeleccionarProducto = new javax.swing.JComboBox<>();
        jbRegistrar = new javax.swing.JButton();
        jLabel6 = new javax.swing.JLabel();
        jcFormaPago = new javax.swing.JComboBox<>();
        jLabel7 = new javax.swing.JLabel();
        jtfCantidad = new javax.swing.JTextField();
        jcTipoItem = new javax.swing.JComboBox<>();

        jLabel1.setFont(new java.awt.Font("Segoe UI Black", 1, 36)); // NOI18N
        jLabel1.setText("VENTA");

        jLabel2.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        jLabel2.setText("Buscar Producto");

        jtVenta.setModel(new javax.swing.table.DefaultTableModel(
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
        jScrollPane1.setViewportView(jtVenta);

        jLabel3.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        jLabel3.setText("Ingrese cantidad a vender");

        jbBuscar.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jbBuscar.setText("Buscar");
        jbBuscar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbBuscarActionPerformed(evt);
            }
        });

        jbAgregar.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jbAgregar.setText("Agregar");
        jbAgregar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbAgregarActionPerformed(evt);
            }
        });

        jLabel4.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        jLabel4.setText("Total a pagar");

        jLabel5.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        jLabel5.setText("Selecione Producto");

        jbRegistrar.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jbRegistrar.setText("Registrar venta");
        jbRegistrar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbRegistrarActionPerformed(evt);
            }
        });

        jLabel6.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        jLabel6.setText("Forma de pago");

        jcFormaPago.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N

        jLabel7.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        jLabel7.setText("Ingrese tipo de item");

        jcTipoItem.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Producto", "Servicio" }));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(296, 296, 296)
                        .addComponent(jLabel1))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(30, 30, 30)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(layout.createSequentialGroup()
                                        .addComponent(jLabel3)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(jtfCantidad, javax.swing.GroupLayout.PREFERRED_SIZE, 158, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGroup(layout.createSequentialGroup()
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                            .addGroup(layout.createSequentialGroup()
                                                .addComponent(jLabel5)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                .addComponent(jcSeleccionarProducto, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                            .addGroup(layout.createSequentialGroup()
                                                .addComponent(jLabel2)
                                                .addGap(18, 18, 18)
                                                .addComponent(jtfBuscarProducto, javax.swing.GroupLayout.PREFERRED_SIZE, 208, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                        .addGap(18, 18, 18)
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                            .addComponent(jbBuscar, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                            .addComponent(jbAgregar, javax.swing.GroupLayout.DEFAULT_SIZE, 93, Short.MAX_VALUE)))
                                    .addGroup(layout.createSequentialGroup()
                                        .addComponent(jLabel6)
                                        .addGap(18, 18, 18)
                                        .addComponent(jcFormaPago, javax.swing.GroupLayout.PREFERRED_SIZE, 111, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGroup(layout.createSequentialGroup()
                                        .addComponent(jLabel7)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(jcTipoItem, javax.swing.GroupLayout.PREFERRED_SIZE, 172, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                .addGap(221, 221, 221))
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jLabel4)
                                .addGap(30, 30, 30)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(jbRegistrar, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(jtfTotalPagar, javax.swing.GroupLayout.PREFERRED_SIZE, 158, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 691, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addContainerGap(29, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(jtfBuscarProducto, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jbBuscar))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel5)
                        .addComponent(jLabel3)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jcSeleccionarProducto)
                            .addComponent(jbAgregar))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jtfCantidad, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(12, 12, 12)))
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel7)
                    .addComponent(jcTipoItem, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel6)
                    .addComponent(jcFormaPago, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 277, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jtfTotalPagar, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel4))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jbRegistrar))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jbBuscarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbBuscarActionPerformed
        // TODO add your handling code here:
        String texto = jtfBuscarProducto.getText().trim().toLowerCase();
        boolean encontrado = false;

        for (int i = 0; i < jcSeleccionarProducto.getItemCount(); i++) {
            String item = jcSeleccionarProducto.getItemAt(i);
            String nombreProducto = item.split(" - ")[1].trim().toLowerCase();

            if (nombreProducto.equalsIgnoreCase(texto)) {
                jcSeleccionarProducto.setSelectedIndex(i);
                JOptionPane.showMessageDialog(this, "Producto encontrado y seleccionado.");
                encontrado = true;
                break;
            }
        }

        if (!encontrado) {
            JOptionPane.showMessageDialog(this, "Producto no encontrado.");
        }
    }//GEN-LAST:event_jbBuscarActionPerformed

    private void jbAgregarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbAgregarActionPerformed
        // TODO add your handling code here:
        agregarProductoAVenta();
    }//GEN-LAST:event_jbAgregarActionPerformed

    private void jbRegistrarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbRegistrarActionPerformed
        // TODO add your handling code here:
        registrarVenta();
    }//GEN-LAST:event_jbRegistrarActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JButton jbAgregar;
    private javax.swing.JButton jbBuscar;
    private javax.swing.JButton jbRegistrar;
    private javax.swing.JComboBox<String> jcFormaPago;
    private javax.swing.JComboBox<String> jcSeleccionarProducto;
    private javax.swing.JComboBox<String> jcTipoItem;
    private javax.swing.JTable jtVenta;
    private javax.swing.JTextField jtfBuscarProducto;
    private javax.swing.JTextField jtfCantidad;
    private javax.swing.JTextField jtfTotalPagar;
    // End of variables declaration//GEN-END:variables
}
