/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package CapaEntidad;

/**
 *
 * @author ASUS
 */
public class DetalleCompra {
    private int idDetalleCompra;
    private String idBoleta;
    private String idProducto;
    private int cantidad;
    private double precioUnitario;

    public DetalleCompra(String idBoleta, String idProducto, int cantidad, double precioUnitario) {
        this.idBoleta = idBoleta;
        this.idProducto = idProducto;
        this.cantidad = cantidad;
        this.precioUnitario = precioUnitario;
    }

    public int getIdDetalleCompra() {
        return idDetalleCompra;
    }

    public void setIdDetalleCompra(int idDetalleCompra) {
        this.idDetalleCompra = idDetalleCompra;
    }

    public String getIdBoleta() {
        return idBoleta;
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
}
