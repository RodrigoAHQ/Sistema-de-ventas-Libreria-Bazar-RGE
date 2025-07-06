/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package CapaEntidad;

/**
 *
 * @author ASUS
 */
public class FormaPago {
    private int idFormaPago;
    private String descripcion;

    public FormaPago(int idFormaPago, String descripcion) {
        this.idFormaPago = idFormaPago;
        this.descripcion = descripcion;
    }

    public int getIdFormaPago() {
        return idFormaPago;
    }

    public String getDescripcion() {
        return descripcion;
    }

    @Override
    public String toString() {
        return idFormaPago + " - " + descripcion;
    }
    
}
