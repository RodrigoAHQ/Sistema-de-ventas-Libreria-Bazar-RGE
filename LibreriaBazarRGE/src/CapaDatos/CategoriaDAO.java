/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package CapaDatos;


import CapaEntidad.Categoria;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.sql.SQLException;


/**
 *
 * @author ASUS
 */
public class CategoriaDAO {
        public List<Categoria> listarTodos() {
            List<Categoria> lista = new ArrayList<>();
            String sql = "SELECT idCategoria, nombre FROM categoria";

            try (Connection con = Conexion.conectar();
                 PreparedStatement stmt = con.prepareStatement(sql);
                 ResultSet rs = stmt.executeQuery()) {

                while (rs.next()) {
                    String idCategoria = String.valueOf(rs.getInt("idCategoria"));
                    String nombre = rs.getString("nombre");

                    Categoria c = new Categoria(idCategoria, nombre) {
                        @Override
                        public String getCategoria() {
                            return getNombreCat();  // Usar método concreto
                        }
                    };

                    lista.add(c);
                }

            } catch (SQLException e) {
                System.out.println("❌ Error al listar categorías: " + e.getMessage());
            }

            return lista;
        }
        
    public void insertar(String nombreCategoria) throws Exception {
        String sql = "INSERT INTO categoria (nombre) VALUES (?)";

        try (Connection cn = Conexion.conectar();
             PreparedStatement ps = cn.prepareStatement(sql)) {

            ps.setString(1, nombreCategoria);
            ps.executeUpdate();
        }
    }
    
    public void actualizar(Categoria categoria) throws Exception {
        String sql = "UPDATE categoria SET nombre = ? WHERE idCategoria = ?";
        try (Connection cn = Conexion.conectar();
             PreparedStatement ps = cn.prepareStatement(sql)) {
            ps.setString(1, categoria.getNombreCat());
            ps.setInt(2, Integer.parseInt(categoria.getIdCategoria()));
            ps.executeUpdate();
        }
    }
    
    public void eliminar(String idCategoria) throws Exception {
        String sql = "DELETE FROM categoria WHERE idCategoria = ?";
        try (Connection cn = Conexion.conectar();
             PreparedStatement ps = cn.prepareStatement(sql)) {
            ps.setInt(1, Integer.parseInt(idCategoria));
            ps.executeUpdate();
        }
    }


}

