/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package CapaEntidad;

/**
 *
 * @author ASUS
 */

public class DetalleVenta {
    private int idVenta;
    private String idProducto;
    private int cantidad;
    private double precioUnitario;
    private String tipoItem;

    public DetalleVenta() {
        // Constructor vacío
    }

    public DetalleVenta(int idVenta, String idProducto, int cantidad, double precioUnitario) {
        this.idVenta = idVenta;
        this.idProducto = idProducto;
        this.cantidad = cantidad;
        this.precioUnitario = precioUnitario;
    }

    // Getters
    public int getIdVenta() {
        return idVenta;
    }

    public String getIdProducto() {
        return idProducto;
    }

    public int getCantidad() {
        return cantidad;
    }

    public double getPrecioUnitario() {
        return precioUnitario;
    }

    public String getTipoItem() {
        return tipoItem;
    }
    

    // Setters
    public void setIdVenta(int idVenta) {
        this.idVenta = idVenta;
    }

    public void setIdProducto(String idProducto) {
        this.idProducto = idProducto;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }

    public void setPrecioUnitario(double precioUnitario) {
        this.precioUnitario = precioUnitario;
    }

    public void setTipoItem(String tipoItem) {
        this.tipoItem = tipoItem;
    }
    
    
}

