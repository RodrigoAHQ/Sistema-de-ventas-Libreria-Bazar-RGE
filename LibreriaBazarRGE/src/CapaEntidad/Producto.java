/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package CapaEntidad;

import java.time.LocalDate;


/**
 *
 * @author ASUS
 */
public class Producto extends Categoria{
    private String idProducto;
    private String nombre;
    private String descripcion;
    private double precioCompra;
    private double precioVenta;
    private int stock;
    private LocalDate fechaVencimiento;  // java.sql.Date
    
    public Producto() {
        super("", "");
    }


    public Producto(String idCategoria, String nombreCat, String idProducto,
        String nombre, String descripcion,
        double precioVenta, double precioCompra,
        int stock, LocalDate fechaVencimiento) {
        super(idCategoria, nombreCat);
        this.idProducto = idProducto;
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.precioVenta = precioVenta;
        this.precioCompra = precioCompra;
        this.stock = stock;
        this.fechaVencimiento = fechaVencimiento;
    }

    public void setIdProducto(String idProducto) {
        this.idProducto = idProducto;
    }
       
    public String getIdProducto() {
        return idProducto;
    }

    public String getNombre() {
        return nombre;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public double getPrecioCompra() {
        return precioCompra;
    }

    public double getPrecioVenta() {
        return precioVenta;
    }

    public int getStock() {
        return stock;
    }

    public LocalDate getFechaVencimiento() {
        return fechaVencimiento;
    }

    @Override
    public String getCategoria() {
       return "Id Categoria: " + getIdCategoria() + "| Nombre Categoria: " + getNombreCat();
    }
    
}
