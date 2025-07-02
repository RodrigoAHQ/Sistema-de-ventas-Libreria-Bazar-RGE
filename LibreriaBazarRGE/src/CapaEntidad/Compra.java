/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package CapaEntidad;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author ASUS
 */
public class Compra {
    private String idBoleta;
    private LocalDate fecha;
    private double total;
    private List<DetalleCompra> detalles;

    public Compra(String idBoleta, LocalDate fecha, double total) {
        this.idBoleta = idBoleta;
        this.fecha = fecha;
        this.total = total;
        this.detalles = new ArrayList<>();
    }

    public String getIdBoleta() {
        return idBoleta;
    }

    public LocalDate getFecha() {
        return fecha;
    }

    public double getTotal() {
        return total;
    }

    public List<DetalleCompra> getDetalles() {
        return detalles;
    }

    public void agregarDetalle(DetalleCompra detalle) {
        detalles.add(detalle);
    }
}

