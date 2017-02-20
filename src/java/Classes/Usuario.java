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
    private String usuario;
    private String nombreMostrar;
    private boolean admin;
    private boolean s1;
    private boolean s4;

    public Usuario(int id, String usuario, String nombreMostrar, Boolean admin, Boolean s1, Boolean s4) {
        this.id = id;
        this.usuario = usuario;
        this.nombreMostrar = nombreMostrar;
        this.admin = admin;
        this.s1 = s1;
        this.s4 = s4;
    }

    
    public int getId() {
        return id;
    }

    public String getUsuario() {
        return usuario;
    }

    public String getNombreMostrar() {
        return nombreMostrar;
    }

    public boolean isAdmin() {
        return admin;
    }

    public boolean isS1() {
        return s1;
    }

    public boolean isS4() {
        return s4;
    }    
    
}
