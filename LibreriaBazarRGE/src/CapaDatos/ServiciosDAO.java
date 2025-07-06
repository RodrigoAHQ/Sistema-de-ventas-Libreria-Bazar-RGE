/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package CapaDatos;
import CapaEntidad.Servicio;
import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
/**
 *
 * @author ASUS
 */
public class ServiciosDAO {
        public void insertar(Servicio servicio) throws Exception {
        String sql = "INSERT INTO servicio (tipo, precio, fecha, idCategoria) VALUES (?, ?, ?, ?)";

        try (Connection cn = Conexion.conectar();
             PreparedStatement ps = cn.prepareStatement(sql)) {

            ps.setString(1, servicio.getNombreCat()); // tipo del servicio
            ps.setDouble(2, servicio.getPrecio());
            ps.setDate(3, Date.valueOf(servicio.getFecha()));
            ps.setInt(4, Integer.parseInt(servicio.getIdCategoria()));

            ps.executeUpdate();
        }
    }

    public List<Servicio> listarTodos() throws Exception {
        List<Servicio> lista = new ArrayList<>();
        String sql = "SELECT s.idServicio, s.tipo, s.precio, s.fecha, s.idCategoria, c.nombre " +
                     "FROM servicio s JOIN categoria c ON s.idCategoria = c.idCategoria";

        try (Connection cn = Conexion.conectar();
             PreparedStatement ps = cn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                String idServicio = String.valueOf(rs.getInt("idServicio"));
                String tipo = rs.getString("tipo");
                double precio = rs.getDouble("precio");
                LocalDate fecha = rs.getDate("fecha").toLocalDate();
                String idCategoria = String.valueOf(rs.getInt("idCategoria"));
                String nombreCategoria = rs.getString("nombre");

                Servicio s = new Servicio(idCategoria, nombreCategoria, idServicio, precio, fecha);
                lista.add(s);
            }
        }

        return lista;
    }

    public void actualizar(Servicio servicio) throws Exception {
        String sql = "UPDATE servicio SET tipo = ?, precio = ?, fecha = ?, idCategoria = ? WHERE idServicio = ?";

        try (Connection cn = Conexion.conectar();
             PreparedStatement ps = cn.prepareStatement(sql)) {

            ps.setString(1, servicio.getNombreCat());
            ps.setDouble(2, servicio.getPrecio());
            ps.setDate(3, Date.valueOf(servicio.getFecha()));
            ps.setInt(4, Integer.parseInt(servicio.getIdCategoria()));
            ps.setInt(5, Integer.parseInt(servicio.getIdServicio()));

            ps.executeUpdate();
        }
    }

    public void eliminar(String idServicio) throws Exception {
        String sql = "DELETE FROM servicio WHERE idServicio = ?";

        try (Connection cn = Conexion.conectar();
             PreparedStatement ps = cn.prepareStatement(sql)) {

            ps.setInt(1, Integer.parseInt(idServicio));
            ps.executeUpdate();
        }
    }

    public Servicio obtenerPorId(String idServicio) throws Exception {
        String sql = "SELECT s.idServicio, s.tipo, s.precio, s.fecha, s.idCategoria, c.nombre " +
                     "FROM servicio s JOIN categoria c ON s.idCategoria = c.idCategoria " +
                     "WHERE s.idServicio = ?";

        try (Connection cn = Conexion.conectar();
             PreparedStatement ps = cn.prepareStatement(sql)) {

            ps.setInt(1, Integer.parseInt(idServicio));

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    String tipo = rs.getString("tipo");
                    double precio = rs.getDouble("precio");
                    LocalDate fecha = rs.getDate("fecha").toLocalDate();
                    String idCategoria = String.valueOf(rs.getInt("idCategoria"));
                    String nombreCategoria = rs.getString("nombre");

                    return new Servicio(idCategoria, nombreCategoria, idServicio, precio, fecha);
                }
            }
        }

        return null; // no encontrado
    }
    
}
