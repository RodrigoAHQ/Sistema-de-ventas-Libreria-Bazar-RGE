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
public class Servicio extends Categoria {
    private String idServicio;
    private double precio;
    private LocalDate fecha;

    public Servicio(String idCategoria, String nombreCat, String idServicio, double precio, LocalDate fecha) {
        super(idCategoria, nombreCat);
        this.idServicio = idServicio;
        this.precio=precio;
        this.fecha=fecha;
    }

    public String getIdServicio() {
        return idServicio;
    }

    public double getPrecio() {
        return precio;
    }

    public LocalDate getFecha() {
        return fecha;
    }
    
    
    
          
    @Override
    public String getCategoria() {
       return "Id Categoria: " + getIdCategoria() + "| Nombre Categoria: " + getNombreCat();
    }
    
}
