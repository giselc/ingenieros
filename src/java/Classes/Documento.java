/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Classes;

import java.sql.Blob;
import java.sql.Date;

/**
 *
 * @author Gisel
 */

public class Documento {
    private int id;
    private tipoDocumento tipo;
    private Blob imagen;
    private Date fechaVencimiento;

    
    
    public Documento(int id, tipoDocumento tipo, Blob imagen, Date fechaVencimiento) {
        this.id = id;
        this.tipo = tipo;
        this.imagen = imagen;
        this.fechaVencimiento = fechaVencimiento;
    }
    
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public tipoDocumento getTipo() {
        return tipo;
    }

    public void setTipo(tipoDocumento tipo) {
        this.tipo = tipo;
    }

    public Blob getImagen() {
        return imagen;
    }

    public void setImagen(Blob imagen) {
        this.imagen = imagen;
    }

    public Date getFechaVencimiento() {
        return fechaVencimiento;
    }

    public void setFechaVencimiento(Date fechaVencimiento) {
        this.fechaVencimiento = fechaVencimiento;
    }
    
    
    
}
