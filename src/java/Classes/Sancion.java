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
public class Sancion {
    private int id;
    private tipoSancion tipo;
    private String parte;
    private int dias;
    private Date fecha;

    public Sancion(int id, tipoSancion tipo, String parte, int dias, Date fecha) {
        this.id = id;
        this.tipo = tipo;
        this.parte = parte;
        this.dias = dias;
        this.fecha = fecha;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public tipoSancion getTipo() {
        return tipo;
    }

    public void setTipo(tipoSancion tipo) {
        this.tipo = tipo;
    }

    public String getParte() {
        return parte;
    }

    public void setParte(String parte) {
        this.parte = parte;
    }

    public int getDias() {
        return dias;
    }

    public void setDias(int dias) {
        this.dias = dias;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }
    
    
    
}
