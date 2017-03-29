/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Classes;

import java.sql.Date;
import java.sql.Time;

/**
 *
 * @author Gisel
 */
public class Sancion {
    private int id;
    private Personal a;
    private TipoSancion tipo; //tipoSancion
    private String parte;
    private int dias;
    private Date fecha;
    private Personal orden;
    private Time hora;

    public Sancion(int id, TipoSancion tipo, String parte, int dias, Date fecha, Time hora,Personal orden, Personal a) {
        this.id = id;
        this.tipo = tipo;
        this.parte = parte;
        this.dias = dias;
        this.hora = hora;
        this.fecha = fecha;
        this.orden = orden;
        this.a = a;
    }


    public Time getHora() {
        return hora;
    }

    public Personal getA() {
        return a;
    }

    public Personal getOrden() {
        return orden;
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
