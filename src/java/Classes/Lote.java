/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Classes;

import java.sql.Date;

/**
 *
 * @author Gisel
 */
public class Lote {
    private String id;
    private Date fechaVencimiento;

    public Lote(String id, Date fechaVencimiento) {
        this.id = id;
        this.fechaVencimiento = fechaVencimiento;
    }

    public String getId() {
        return id;
    }

    public Date getFechaVencimiento() {
        return fechaVencimiento;
    }
    
    
}
