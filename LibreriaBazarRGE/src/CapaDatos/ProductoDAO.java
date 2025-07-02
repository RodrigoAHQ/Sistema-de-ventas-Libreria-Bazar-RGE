package CapaDatos;

import CapaEntidad.Producto;
import java.math.BigDecimal;
import java.sql.*;
import java.time.LocalDate;
import java.util.*;

public class ProductoDAO {

    public void insertar(Producto p) throws SQLException {
        String sql = "INSERT INTO producto (idCategoria, nombre, descripcion, precioCompra, precioVenta, stock, fechaVencimiento) VALUES (?, ?, ?, ?, ?, ?, ?)";

        try (Connection conn = Conexion.conectar();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, p.getIdCategoria());
            stmt.setString(2, p.getNombre());
            stmt.setString(3, p.getDescripcion());
            stmt.setDouble(4, p.getPrecioCompra());  // ✅ CORREGIDO: antes estaba mal
            stmt.setDouble(5, p.getPrecioVenta());
            stmt.setInt(6, p.getStock());

            if (p.getFechaVencimiento() != null) {
                stmt.setDate(7, java.sql.Date.valueOf(p.getFechaVencimiento()));
            } else {
                stmt.setNull(7, java.sql.Types.DATE);
            }

            stmt.executeUpdate();
        }
    }
    
        public void actualizar(Producto p) throws Exception {
            String sql = "UPDATE producto SET nombre=?, descripcion=?, precioCompra=?, precioVenta=?, stock=?, fechaVencimiento=?, idCategoria=? WHERE idProducto=?";
            try (Connection cn = Conexion.conectar();
                 PreparedStatement ps = cn.prepareStatement(sql)) {

                ps.setString(1, p.getNombre());
                ps.setString(2, p.getDescripcion());
                ps.setBigDecimal(3, BigDecimal.valueOf(p.getPrecioCompra()));
                ps.setBigDecimal(4, BigDecimal.valueOf(p.getPrecioVenta()));
                ps.setInt(5, p.getStock());

                if (p.getFechaVencimiento() != null) {
                    ps.setDate(6, java.sql.Date.valueOf(p.getFechaVencimiento()));
                } else {
                    ps.setNull(6, java.sql.Types.DATE);
                }

                ps.setInt(7, Integer.parseInt(p.getIdCategoria()));
                ps.setInt(8, Integer.parseInt(p.getIdProducto()));

                ps.executeUpdate();
            }
    }
    
        public void eliminar(String idProducto) throws Exception {
            String sql = "DELETE FROM producto WHERE idProducto = ?";
            try (Connection cn = Conexion.conectar();
                 PreparedStatement ps = cn.prepareStatement(sql)) {
                ps.setString(1, idProducto); // ← usamos setString, no setInt
                ps.executeUpdate();
            }
        }

    public List<Producto> listarTodos() throws Exception {
        List<Producto> lista = new ArrayList<>();

        String sql = "SELECT p.*, c.nombre AS nombreCat FROM producto p " +
                     "JOIN categoria c ON p.idCategoria = c.idCategoria";


        try (Connection con = Conexion.conectar();
             PreparedStatement st = con.prepareStatement(sql);
             ResultSet rs = st.executeQuery()) {

            while (rs.next()) {
                String idCategoria = String.valueOf(rs.getInt("idCategoria"));
                String nombreCat = rs.getString("nombreCat");
                String idProducto = String.valueOf(rs.getInt("idProducto"));
                String nombre = rs.getString("nombre");
                String descripcion = rs.getString("descripcion");
                double precioCompra = rs.getDouble("precioCompra");
                double precioVenta = rs.getDouble("precioVenta");
                int stock = rs.getInt("stock");

                // Manejo de fecha de vencimiento (puede ser null)
                java.sql.Date fecha = rs.getDate("fechaVencimiento");
                LocalDate fechaVencimiento = null;
                if (fecha != null) {
                    fechaVencimiento = fecha.toLocalDate();
                }

                Producto p = new Producto(
                    idCategoria,
                    nombreCat,
                    idProducto,
                    nombre,
                    descripcion,
                    precioVenta,   // ✅ primero venta
                    precioCompra,  // ✅ luego compra
                    stock,
                    fechaVencimiento
                );


                lista.add(p);
            }
        }

        return lista;
    }
    
    public Producto obtenerPorId(String idProducto) throws Exception {
        String sql = "SELECT p.idProducto, p.nombre, p.descripcion, p.precioCompra, p.precioVenta, p.stock, p.fechaVencimiento, c.idCategoria, c.nombre AS nombreCat " +
                     "FROM producto p JOIN categoria c ON p.idCategoria = c.idCategoria " +
                     "WHERE p.idProducto = ?";

        try (Connection cn = Conexion.conectar();
             PreparedStatement ps = cn.prepareStatement(sql)) {

            ps.setInt(1, Integer.parseInt(idProducto));

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    String idProd = rs.getString("idProducto");
                    String nombre = rs.getString("nombre");
                    String descripcion = rs.getString("descripcion");
                    double precioCompra = rs.getDouble("precioCompra");
                    double precioVenta = rs.getDouble("precioVenta");
                    int stock = rs.getInt("stock");
                    java.sql.Date fechaSql = rs.getDate("fechaVencimiento");
                    java.time.LocalDate fechaVencimiento = fechaSql != null ? fechaSql.toLocalDate() : null;

                    String idCategoria = rs.getString("idCategoria");
                    String nombreCat = rs.getString("nombreCat");

                    return new Producto(idCategoria, nombreCat, idProd, nombre, descripcion,
                    precioVenta, precioCompra, stock, fechaVencimiento);

                } else {
                    return null;  // No encontrado
                }
            }

        } catch (SQLException e) {
            throw new Exception("Error al obtener producto por ID: " + e.getMessage(), e);
        }
    }
    
    public void actualizarStock(String idProducto, int nuevoStock) throws SQLException {
        Connection cn = Conexion.conectar();
        String sql = "UPDATE producto SET stock = ? WHERE idProducto = ?";
        PreparedStatement ps = cn.prepareStatement(sql);
        ps.setInt(1, nuevoStock);
        ps.setString(2, idProducto);
        ps.executeUpdate();
        ps.close();
        cn.close();
    }
    
    public void actualizarStockConConexion(Connection cn, String idProducto, int nuevoStock) throws SQLException {
    String sql = "UPDATE producto SET stock = ? WHERE idProducto = ?";
    try (PreparedStatement ps = cn.prepareStatement(sql)) {
        ps.setInt(1, nuevoStock);
        ps.setString(2, idProducto);
        ps.executeUpdate();
    }
}

    
    /*public void actualizarStockConSP(String idProducto, int nuevoStock) throws SQLException {
        Connection cn = Conexion.conectar();
        CallableStatement cs = cn.prepareCall("{call sp_actualizar_stock(?, ?)}");
        cs.setInt(1, Integer.parseInt(idProducto));
        cs.setInt(2, nuevoStock);
        cs.execute();
        cs.close();
        cn.close();

    }*/
    
    /*public void sumarStockConSP(String idProducto, int cantidadASumar) throws SQLException {
        Connection cn = Conexion.conectar();
        CallableStatement cs = cn.prepareCall("{call sp_sumar_stock(?, ?)}");
        cs.setInt(1, Integer.parseInt(idProducto));
        cs.setInt(2, cantidadASumar);
        cs.execute();
        cs.close();
        cn.close();
    }*/


}
