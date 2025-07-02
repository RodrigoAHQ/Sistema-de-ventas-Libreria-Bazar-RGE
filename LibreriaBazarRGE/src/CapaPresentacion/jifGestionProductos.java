/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JInternalFrame.java to edit this template
 */
package CapaPresentacion;

import CapaDatos.CategoriaDAO;
import CapaDatos.ProductoDAO;
import CapaEntidad.Categoria;
import CapaEntidad.Producto;
import java.util.List;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author ASUS
 */
public class jifGestionProductos extends javax.swing.JInternalFrame {

    /**
     * Creates new form jifGestionProductos
     */
    public jifGestionProductos() {
        initComponents();
        listarProductos();         // Carga los datos de productos en la tabla
        cargarCategorias();        // Carga las categorías en el JComboBox
        jtfStock.setEditable(false);
        jtfStock.setFocusable(false);
        jtfStock.setBackground(new java.awt.Color(230, 230, 230)); 
        setClosable(true);
        
        jpProductos.add(jmiProducto);
        jtProductos.setComponentPopupMenu(jpProductos);

    }
    private void listarProductos() {
        try {
            ProductoDAO dao = new ProductoDAO();
            List<Producto> lista = dao.listarTodos();

            DefaultTableModel model = new DefaultTableModel();
            model.addColumn("ID");
            model.addColumn("Nombre");
            model.addColumn("Descripción");
            model.addColumn("Precio Compra");
            model.addColumn("Precio Venta");
            model.addColumn("Stock");
            model.addColumn("Fecha Vencimiento");
            model.addColumn("Categoría");

            for (Producto p : lista) {
                model.addRow(new Object[]{
                    p.getIdProducto(),
                    p.getNombre(),
                    p.getDescripcion(),
                    p.getPrecioCompra(),
                    p.getPrecioVenta(),
                    p.getStock(),
                    p.getFechaVencimiento(),
                    p.getNombreCat()
                });
            }

            jtProductos.setModel(model);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error al listar productos: " + e.getMessage());
        }
    }   
    
    private void cargarCategorias() {
        try {
            CategoriaDAO dao = new CategoriaDAO();
            List<Categoria> lista = dao.listarTodos();
            jcCategoria.removeAllItems();

            for (Categoria c : lista) {
                jcCategoria.addItem(c.getIdCategoria() + " - " + c.getNombreCat());
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error al cargar categorías: " + e.getMessage());
        }
    }
    
    private void eliminarProducto() {
        int fila = jtProductos.getSelectedRow();
        if (fila >= 0) {
            String idProducto = jtProductos.getValueAt(fila, 0).toString();  // ID oculto en la columna 0

            int confirm = JOptionPane.showConfirmDialog(this, "¿Seguro de eliminar?", "Confirmar", JOptionPane.YES_NO_OPTION);
            if (confirm == JOptionPane.YES_OPTION) {
                try {
                    ProductoDAO dao = new ProductoDAO();
                    dao.eliminar(idProducto);  // ← directamente como String, sin parsear
                    listarProductos();
                    JOptionPane.showMessageDialog(this, "Producto eliminado");
                } catch (Exception e) {
                    JOptionPane.showMessageDialog(this, "Error al eliminar: " + e.getMessage());
                }
            }
        } else {
            JOptionPane.showMessageDialog(this, "Selecciona un producto");
        }
    }
    
    private void actualizarProducto() {
        int fila = jtProductos.getSelectedRow();
        if (fila >= 0) {
            try {
                String idProducto = jtProductos.getValueAt(fila, 0).toString();
                String nombre = jtfNombre.getText().trim();
                String descripcion = jtfDesc.getText().trim();
                double precioCompra = Double.parseDouble(jtfPrecioCompra.getText().trim());
                double precioVenta = Double.parseDouble(jtfPrecioVenta.getText().trim());
                int stock = Integer.parseInt(jtfStock.getText().trim());
                String fechaStr = jtfFechaVen.getText().trim();
                java.time.LocalDate fecha = null;
                if (!fechaStr.isEmpty()) {
                    fecha = java.time.LocalDate.parse(fechaStr); // Formato: yyyy-MM-dd
                }

                // Obtener ID y nombre de categoría desde JComboBox
                String categoriaCombo = (String) jcCategoria.getSelectedItem();
                String idCategoria = categoriaCombo.split(" - ")[0];
                String nombreCat = categoriaCombo.split(" - ")[1];

                Producto p = new Producto(
                    idCategoria, nombreCat,
                    idProducto, nombre, descripcion,
                    precioVenta, precioCompra,
                    stock, fecha
                );

                ProductoDAO dao = new ProductoDAO();
                dao.actualizar(p);

                JOptionPane.showMessageDialog(this, "✅ Producto actualizado");
                listarProductos();
            } catch (Exception e) {
                JOptionPane.showMessageDialog(this, "❌ Error al actualizar: " + e.getMessage());
            }
        } else {
            JOptionPane.showMessageDialog(this, "Selecciona un producto");
        }
    }
        
    private void seleccionarProductoDesdeTabla() {
        int fila = jtProductos.getSelectedRow();
        if (fila >= 0) {
            jtfNombre.setText(jtProductos.getValueAt(fila, 1).toString());
            jtfDesc.setText(jtProductos.getValueAt(fila, 2).toString());
            jtfPrecioCompra.setText(jtProductos.getValueAt(fila, 3).toString());
            jtfPrecioVenta.setText(jtProductos.getValueAt(fila, 4).toString());
            jtfStock.setText(jtProductos.getValueAt(fila, 5).toString());

            Object fechaObj = jtProductos.getValueAt(fila, 6);
            jtfFechaVen.setText(fechaObj != null ? fechaObj.toString() : "");

            String nombreCat = jtProductos.getValueAt(fila, 7).toString();

            // Seleccionar el item correspondiente del combo
            for (int i = 0; i < jcCategoria.getItemCount(); i++) {
                String item = jcCategoria.getItemAt(i);
                if (item.endsWith(" - " + nombreCat) || item.contains(" - " + nombreCat)) {
                    jcCategoria.setSelectedIndex(i);
                    break;
                }
            }
        } else {
            JOptionPane.showMessageDialog(this, "Selecciona un producto de la tabla.");
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

        jmiProducto = new javax.swing.JMenuItem();
        jpProductos = new javax.swing.JPopupMenu();
        jScrollPane1 = new javax.swing.JScrollPane();
        jtProductos = new javax.swing.JTable();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jtfPrecioVenta = new javax.swing.JTextField();
        jtfDesc = new javax.swing.JTextField();
        jtfNombre = new javax.swing.JTextField();
        jcCategoria = new javax.swing.JComboBox<>();
        jbActualizar = new javax.swing.JButton();
        jbEliminar = new javax.swing.JButton();
        jtfPrecioCompra = new javax.swing.JTextField();
        jtfFechaVen = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        jtfStock = new javax.swing.JTextField();

        jmiProducto.setText("Seleccionar");
        jmiProducto.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jmiProductoActionPerformed(evt);
            }
        });

        jtProductos.setModel(new javax.swing.table.DefaultTableModel(
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
        jScrollPane1.setViewportView(jtProductos);

        jLabel1.setText("Nombre");

        jLabel2.setText("Descripcion");

        jLabel3.setText("Precio Compra");

        jLabel4.setText("Precio Venta");

        jLabel6.setText("Fecha Ven.");

        jLabel7.setText("Categoria");

        jbActualizar.setText("Actualizar");
        jbActualizar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbActualizarActionPerformed(evt);
            }
        });

        jbEliminar.setText("Eliminar");
        jbEliminar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbEliminarActionPerformed(evt);
            }
        });

        jLabel5.setText("Stock");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 755, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 15, Short.MAX_VALUE)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jbActualizar, javax.swing.GroupLayout.DEFAULT_SIZE, 110, Short.MAX_VALUE)
                            .addComponent(jbEliminar, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addGap(19, 19, 19))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jLabel1)
                                .addGap(24, 24, 24)
                                .addComponent(jtfNombre, javax.swing.GroupLayout.PREFERRED_SIZE, 109, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(jLabel3))
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jLabel2)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jtfDesc, javax.swing.GroupLayout.PREFERRED_SIZE, 193, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jtfPrecioCompra, javax.swing.GroupLayout.PREFERRED_SIZE, 86, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel4))
                        .addGap(2, 2, 2)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jLabel5)
                                .addGap(18, 18, 18)
                                .addComponent(jtfStock, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(jLabel7))
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jtfPrecioVenta, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jLabel6)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jtfFechaVen, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(18, 18, 18)
                        .addComponent(jcCategoria, javax.swing.GroupLayout.PREFERRED_SIZE, 132, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(41, 41, 41)
                        .addComponent(jbActualizar)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jbEliminar))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(32, 32, 32)
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 242, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(34, 34, 34)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel3)
                        .addComponent(jtfPrecioCompra, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel7)
                        .addComponent(jcCategoria, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel5)
                        .addComponent(jtfStock, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel1)
                        .addComponent(jtfNombre, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(jLabel6)
                    .addComponent(jtfDesc, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jtfFechaVen, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel4)
                    .addComponent(jtfPrecioVenta, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(26, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jbActualizarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbActualizarActionPerformed
        // TODO add your handling code here:
        actualizarProducto();
    }//GEN-LAST:event_jbActualizarActionPerformed

    private void jbEliminarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbEliminarActionPerformed
        // TODO add your handling code here:
        eliminarProducto();
    }//GEN-LAST:event_jbEliminarActionPerformed

    private void jmiProductoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jmiProductoActionPerformed
        // TODO add your handling code here:
        seleccionarProductoDesdeTabla();
    }//GEN-LAST:event_jmiProductoActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JButton jbActualizar;
    private javax.swing.JButton jbEliminar;
    private javax.swing.JComboBox<String> jcCategoria;
    private javax.swing.JMenuItem jmiProducto;
    private javax.swing.JPopupMenu jpProductos;
    private javax.swing.JTable jtProductos;
    private javax.swing.JTextField jtfDesc;
    private javax.swing.JTextField jtfFechaVen;
    private javax.swing.JTextField jtfNombre;
    private javax.swing.JTextField jtfPrecioCompra;
    private javax.swing.JTextField jtfPrecioVenta;
    private javax.swing.JTextField jtfStock;
    // End of variables declaration//GEN-END:variables
}
