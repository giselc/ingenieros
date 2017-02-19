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
    private int id;
    private TipoFamiliar tipo; //tipoFamiliar
    private String nombre;
    private String apellido;
    private String ci; //NO SE IDENTIFICA POR CI PORQUE EL PERSONAL PUEDE DESCONOCER ESTE DATO
    private String domicilio;
    private String ocupacion;
    private String telefono;
    private String celular;
    private Boolean discapacidad; //Boolean con mayuscula porque permite valor NULL

    public Familiar(int id, TipoFamiliar tipo, String nombre, String apellido, String ci, String domicilio, String ocupacion, String telefono, String celular, Boolean discapacidad) {
        this.id = id;
        this.tipo = tipo;
        this.nombre = nombre;
        this.apellido = apellido;
        this.ci = ci;
        this.domicilio = domicilio;
        this.ocupacion = ocupacion;
        this.telefono = telefono;
        this.celular = celular;
        this.discapacidad = discapacidad;
    }
    
    public int getId() {
        return id;
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

    public String getCi() {
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
