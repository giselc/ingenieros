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
    private Personal p;
    private TipoDocumento tipo;
    private Blob imagen;

    
    
    public Documento(int id, Personal p, TipoDocumento tipo, Blob imagen) {
        this.id = id;
        this.tipo = tipo;
        this.imagen = imagen;
        this.p = p;
    }

    public Personal getP() {
        return p;
    }

    public int getId() {
        return id;
    }

    public TipoDocumento getTipo() {
        return tipo;
    }

    public Blob getImagen() {
        return imagen;
    }

    
    
    
}
