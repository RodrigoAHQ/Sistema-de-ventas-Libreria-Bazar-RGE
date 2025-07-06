package CapaDatos;

import CapaEntidad.Compra;
import CapaEntidad.DetalleCompra;
import java.sql.*;

public class CompraDAO {

public void registrarCompra(Compra compra) throws Exception {
    String sqlCompra = "INSERT INTO compra (idBoleta, fecha, total) VALUES (?, ?, ?)";

    try (Connection cn = Conexion.conectar()) {
        cn.setAutoCommit(false);

        try (PreparedStatement ps = cn.prepareStatement(sqlCompra)) {
            ps.setString(1, compra.getIdBoleta());
            ps.setDate(2, Date.valueOf(compra.getFecha()));
            ps.setDouble(3, compra.getTotal());
            ps.executeUpdate();
        }

        String sqlDetalle = "INSERT INTO detalleCompra (idBoleta, idProducto, cantidad, precioUnitario) VALUES (?, ?, ?, ?)";
        try (PreparedStatement psDetalle = cn.prepareStatement(sqlDetalle)) {
            for (DetalleCompra detalle : compra.getDetalles()) {
                psDetalle.setString(1, detalle.getIdBoleta());
                psDetalle.setInt(2, Integer.parseInt(detalle.getIdProducto()));
                psDetalle.setInt(3, detalle.getCantidad());
                psDetalle.setDouble(4, detalle.getPrecioUnitario());
                psDetalle.addBatch();
            }
            psDetalle.executeBatch();
        }

        // ACTUALIZAR STOCK
        ProductoDAO productoDAO = new ProductoDAO();
        for (DetalleCompra detalle : compra.getDetalles()) {
            String idProducto = detalle.getIdProducto();
            int cantidadComprada = detalle.getCantidad();

            // Utiliza la conexión existente para obtener stock actual
            int stockActual = obtenerStockActual(cn, idProducto);
            int nuevoStock = stockActual + cantidadComprada;

            productoDAO.actualizarStockConConexion(cn, idProducto, nuevoStock); // usa misma conexión
        }

        cn.commit();
    } catch (Exception e) {
        throw new Exception("Error al registrar la compra: " + e.getMessage(), e);
    }
}

    private int obtenerStockActual(Connection cn, String idProducto) throws SQLException {
        String sql = "SELECT stock FROM producto WHERE idProducto = ?";
        try (PreparedStatement ps = cn.prepareStatement(sql)) {
            ps.setString(1, idProducto);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt("stock");
                } else {
                    throw new SQLException("Producto no encontrado: " + idProducto);
                }
            }
        }
    }

}
