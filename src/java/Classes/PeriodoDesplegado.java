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
public class PeriodoDesplegado {
    private int id;
    private Personal personal;
    private Date fechaDesde;
    private Date fechaHasta;
    private String observaciones;

    public PeriodoDesplegado(int id, Personal personal, Date fechaDesde, Date fechaHasta, String observaciones) {
        this.id = id;
        this.personal = personal;
        this.fechaDesde = fechaDesde;
        this.fechaHasta = fechaHasta;
        this.observaciones = observaciones;
    }

    public int getId() {
        return id;
    }

    public Personal getPersonal() {
        return personal;
    }

    public Date getFechaDesde() {
        return fechaDesde;
    }

    public Date getFechaHasta() {
        return fechaHasta;
    }

    public String getObservaciones() {
        return observaciones;
    }
    
    
}
