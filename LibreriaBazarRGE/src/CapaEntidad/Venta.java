/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package CapaEntidad;

import java.time.LocalDateTime;

/**
 *
 * @author ASUS
 */
public class Venta {
    private int idVenta;              
    private LocalDateTime fecha;
    private double total;
    private int idFormaPago;

    // Constructor sin idVenta (cuando aún no se ha generado)
    public Venta(LocalDateTime fecha, double total, int idFormaPago) {
        this.fecha = fecha;
        this.total = total;
        this.idFormaPago = idFormaPago;
    }

    // Constructor con idVenta (por si necesitas obtenerlo luego)
    public Venta(int idVenta, LocalDateTime fecha, double total, int idFormaPago) {
        this.idVenta = idVenta;
        this.fecha = fecha;
        this.total = total;
        this.idFormaPago = idFormaPago;
    }

    // Getters y Setters
    public int getIdVenta() {
        return idVenta;
    }

    public void setIdVenta(int idVenta) {
        this.idVenta = idVenta;
    }

    public LocalDateTime getFecha() {
        return fecha;
    }

    public void setFecha(LocalDateTime fecha) {
        this.fecha = fecha;
    }

    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
    }

    public int getIdFormaPago() {
        return idFormaPago;
    }

    public void setIdFormaPago(int idFormaPago) {
        this.idFormaPago = idFormaPago;
    }
}
