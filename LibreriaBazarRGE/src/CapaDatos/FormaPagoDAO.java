/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package CapaDatos;


import java.util.List;
import CapaEntidad.FormaPago;
import java.sql.*;
import java.util.*;

/**
 *
 * @author ASUS
 */
public class FormaPagoDAO {
    
        public List<FormaPago> listarTodas() throws SQLException {
            List<FormaPago> lista = new ArrayList<>();
            String sql = "SELECT idFormaPago, descripcion FROM formaPago";

            try (Connection cn = Conexion.conectar();
                 PreparedStatement ps = cn.prepareStatement(sql);
                 ResultSet rs = ps.executeQuery()) {

                while (rs.next()) {
                    int id = rs.getInt("idFormaPago");
                    String desc = rs.getString("descripcion");

                    lista.add(new FormaPago(id, desc));
                }
            }

            return lista;
        }
    
}
