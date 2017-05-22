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
public class ISArmamento {
    private int id;
    private int idArmamento;
    private Date fecha;
    private String informacionSumaria;
    private Personal oficialArmamento;

    public ISArmamento(int id, int idArmamento, Date fecha, String informacionSumaria, Personal oficialArmamento) {
        this.id = id;
        this.idArmamento = idArmamento;
        this.fecha = fecha;
        this.informacionSumaria = informacionSumaria;
        this.oficialArmamento = oficialArmamento;
    }

    public int getId() {
        return id;
    }

    public int getIdArmamento() {
        return idArmamento;
    }

    public Date getFecha() {
        return fecha;
    }

    public String getInformacionSumaria() {
        return informacionSumaria;
    }

    public Personal getOficialArmamento() {
        return oficialArmamento;
    }
    
    
    
    
}
