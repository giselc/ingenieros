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
    private TipoSancion tipo; //tipoSancion
    private String parte;
    private int dias;
    private Date fecha;

    public Sancion(int id, TipoSancion tipo, String parte, int dias, Date fecha) {
        this.id = id;
        this.tipo = tipo;
        this.parte = parte;
        this.dias = dias;
        this.fecha = fecha;
    }

    public int getId() {
        return id;
    }

    public TipoSancion getTipo() {
        return tipo;
    }

    public String getParte() {
        return parte;
    }

    public int getDias() {
        return dias;
    }

    public Date getFecha() {
        return fecha;
    }

    
    
    
}
