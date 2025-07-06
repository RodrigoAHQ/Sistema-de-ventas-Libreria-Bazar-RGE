package CapaDatos;

import CapaEntidad.DetalleVenta;
import CapaEntidad.Producto;
import CapaEntidad.Venta;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class VentaDAO {

    public int registrarVenta(Venta venta) throws SQLException {
        String sql = "INSERT INTO venta (fecha, total, idFormaPago) VALUES (?, ?, ?)";
        try (Connection cn = Conexion.conectar();
             PreparedStatement ps = cn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            ps.setTimestamp(1, Timestamp.valueOf(venta.getFecha()));
            ps.setDouble(2, venta.getTotal());
            ps.setInt(3, venta.getIdFormaPago());

            ps.executeUpdate();

            try (ResultSet rs = ps.getGeneratedKeys()) {
                if (rs.next()) {
                    return rs.getInt(1);
                } else {
                    throw new SQLException("No se pudo obtener el ID de la venta generada.");
                }
            }
        }
    }

    public void registrarDetalleVenta(DetalleVenta detalle) throws SQLException {
        String sql = "INSERT INTO detalleVenta (idVenta, idItem, cantidad, precioUnitario, tipoItem) VALUES (?, ?, ?, ?, ?)";
        try (Connection cn = Conexion.conectar();
             PreparedStatement ps = cn.prepareStatement(sql)) {

            ps.setInt(1, detalle.getIdVenta());
            ps.setInt(2, Integer.parseInt(detalle.getIdProducto()));  // o getIdItem()
            ps.setInt(3, detalle.getCantidad());
            ps.setDouble(4, detalle.getPrecioUnitario());
            ps.setString(5, detalle.getTipoItem());

            ps.executeUpdate();
        }
    }

    public int registrarVentaConDetalle(Venta venta, List<DetalleVenta> detalles) throws Exception {
        int idVentaGenerado = -1;

        String sqlVenta = "INSERT INTO venta (fecha, total, idFormaPago) VALUES (?, ?, ?)";

        try (Connection conn = Conexion.conectar();
             PreparedStatement ps = conn.prepareStatement(sqlVenta, Statement.RETURN_GENERATED_KEYS)) {

            ps.setTimestamp(1, Timestamp.valueOf(venta.getFecha()));
            ps.setDouble(2, venta.getTotal());
            ps.setInt(3, venta.getIdFormaPago());

            ps.executeUpdate();

            ResultSet rs = ps.getGeneratedKeys();
            if (rs.next()) {
                idVentaGenerado = rs.getInt(1);
            }

            // Insertar detalles
            String sqlDetalle = "INSERT INTO detalleVenta (idVenta, idItem, cantidad, precioUnitario, tipoItem) VALUES (?, ?, ?, ?, ?)";
            try (PreparedStatement psDetalle = conn.prepareStatement(sqlDetalle)) {
                for (DetalleVenta d : detalles) {
                    psDetalle.setInt(1, idVentaGenerado);
                    psDetalle.setString(2, d.getIdProducto());
                    psDetalle.setInt(3, d.getCantidad());
                    psDetalle.setDouble(4, d.getPrecioUnitario());
                    psDetalle.setString(5, d.getTipoItem());
                    psDetalle.addBatch();
                }
                psDetalle.executeBatch();
            }

            // Descontar stock SOLO si es producto
            ProductoDAO productoDAO = new ProductoDAO();
            for (DetalleVenta d : detalles) {
                if ("Producto".equalsIgnoreCase(d.getTipoItem())) {
                    Producto p = productoDAO.obtenerPorId(d.getIdProducto());
                    int nuevoStock = p.getStock() - d.getCantidad();
                    productoDAO.actualizarStock(d.getIdProducto(), nuevoStock);
                }
            }
        }

        return idVentaGenerado;
    }

    public List<DetalleVenta> listarDetallesPorVenta(int idVenta) throws SQLException {
        List<DetalleVenta> lista = new ArrayList<>();
        String sql = "SELECT idItem, cantidad, precioUnitario, tipoItem FROM detalleVenta WHERE idVenta = ?";

        try (Connection cn = Conexion.conectar();
             PreparedStatement ps = cn.prepareStatement(sql)) {

            ps.setInt(1, idVenta);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    DetalleVenta d = new DetalleVenta(
                        idVenta,
                        rs.getString("idItem"),
                        rs.getInt("cantidad"),
                        rs.getDouble("precioUnitario")
                    );
                    d.setTipoItem(rs.getString("tipoItem"));
                    lista.add(d);
                }
            }
        }

        return lista;
    }

    public List<Venta> listarTodas() throws Exception {
        List<Venta> lista = new ArrayList<>();
        String sql = "SELECT idVenta, fecha, total, idFormaPago FROM venta";

        try (Connection cn = Conexion.conectar();
             PreparedStatement ps = cn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                int idVenta = rs.getInt("idVenta");
                java.sql.Timestamp timestamp = rs.getTimestamp("fecha");
                java.time.LocalDateTime fecha = timestamp.toLocalDateTime();
                double total = rs.getDouble("total");
                int idFormaPago = rs.getInt("idFormaPago");

                Venta v = new Venta(idVenta, fecha, total, idFormaPago);
                lista.add(v);
            }
        } catch (SQLException e) {
            throw new Exception("Error al listar ventas: " + e.getMessage(), e);
        }

        return lista;
    }
}
