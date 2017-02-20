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
public class Apoderado {
    private int ci;
    private String nombre;
    private String apellido;
    private TipoFamiliar vinculo;
    private String domicilio;
    private String celular;
    private String telefono;

    public Apoderado(int ci, String nombre, String apellido, TipoFamiliar vinculo, String domicilio, String celular, String telefono) {
        this.ci = ci;
        this.nombre = nombre;
        this.apellido = apellido;
        this.vinculo = vinculo;
        this.domicilio = domicilio;
        this.celular = celular;
        this.telefono = telefono;
    }


    public int getCi() {
        return ci;
    }

    public String getNombre() {
        return nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public TipoFamiliar getVinculo() {
        return vinculo;
    }

    public String getDomicilio() {
        return domicilio;
    }

    public String getCelular() {
        return celular;
    }

    public String getTelefono() {
        return telefono;
    }
    
    
}
