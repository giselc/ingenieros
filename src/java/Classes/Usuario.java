/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Classes;

/**
 *
 * @author Gisel
 */
public class Usuario {
    private int id;
    private String nombre;
    private String nombreMostrar;
    private boolean admin;
    private boolean s1;
    private boolean s4;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getNombreMostrar() {
        return nombreMostrar;
    }

    public void setNombreMostrar(String nombreMostrar) {
        this.nombreMostrar = nombreMostrar;
    }

    public boolean isAdmin() {
        return admin;
    }

    public void setAdmin(boolean admin) {
        this.admin = admin;
    }

    public boolean isS1() {
        return s1;
    }

    public void setS1(boolean s1) {
        this.s1 = s1;
    }

    public boolean isS4() {
        return s4;
    }

    public void setS4(boolean s4) {
        this.s4 = s4;
    }
    
    
}
