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
public class ObservacionArmamento {
    private  int id;
    private Date fecha;
    private String observaciones;
    private Personal escribiente;
    private int idArmamento;

    public ObservacionArmamento(int id, Date fecha, String observaciones, Personal escribiente, int idArmamento) {
        this.id = id;
        this.fecha = fecha;
        this.observaciones = observaciones;
        this.escribiente = escribiente;
        this.idArmamento = idArmamento;
    }

    public int getId() {
        return id;
    }

    public Date getFecha() {
        return fecha;
    }

    public String getObservaciones() {
        return observaciones;
    }

    public Personal getEscribiente() {
        return escribiente;
    }

    public int getIdArmamento() {
        return idArmamento;
    }
    
    
}
