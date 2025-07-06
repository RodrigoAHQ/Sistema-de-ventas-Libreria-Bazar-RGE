/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package CapaEntidad;

/**
 *
 * @author ASUS
 */
public abstract class Categoria {
    private String idCategoria;
    private String nombreCategoria;

    public Categoria(String idCategoria, String nombreCategoria) {
        this.idCategoria=idCategoria;
        this.nombreCategoria=nombreCategoria;
    }

    public String getIdCategoria() {
        return idCategoria;
    }

    public String getNombreCat() {
        return nombreCategoria;
    }
    
    public abstract String getCategoria();
}