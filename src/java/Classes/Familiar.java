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
public class Familiar {
    private TipoFamiliar tipo; //tipoFamiliar
    private String nombre;
    private String apellido;
    private int ci; 
    private int edad;
    private String domicilio;
    private String ocupacion;
    private String telefono;
    private String celular;
    private Boolean discapacidad; //Boolean con mayuscula porque permite valor NULL
    private String descDiscapacidad;

    public Familiar( int ci, TipoFamiliar tipo, String nombre, int edad,String apellido, String domicilio, String ocupacion, String telefono, String celular, Boolean discapacidad, String descDiscapacidad) {
        this.tipo = tipo;
        this.nombre = nombre;
        this.apellido = apellido;
        this.ci = ci;
        this.edad = edad;
        this.domicilio = domicilio;
        this.ocupacion = ocupacion;
        this.telefono = telefono;
        this.celular = celular;
        this.discapacidad = discapacidad;
        this.descDiscapacidad = descDiscapacidad;
    }

    public String getDescDiscapacidad() {
        return descDiscapacidad;
    }

    public int getEdad() {
        return edad;
    }
    

    public TipoFamiliar getTipo() {
        return tipo;
    }

    public String getNombre() {
        return nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public int getCi() {
        return ci;
    }

    public String getDomicilio() {
        return domicilio;
    }

    public String getOcupacion() {
        return ocupacion;
    }

    public String getTelefono() {
        return telefono;
    }

    public String getCelular() {
        return celular;
    }

    public Boolean getDiscapacidad() {
        return discapacidad;
    }
    
    
    
}
