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
public class Armamento {
    private int numero;
    private ModeloArmamento modelo;
    private Date fechaAlta;
    private Date fechaBaja;
    private Destino destino;
    private Municion calibre;
    private Personal entregado;

    public Armamento(int numero, ModeloArmamento modelo, Date fechaAlta, Date fechaBaja, Destino destino, Municion calibre, Personal entregado) {
        this.numero = numero;
        this.modelo = modelo;
        this.fechaAlta = fechaAlta;
        this.fechaBaja = fechaBaja;
        this.destino = destino;
        this.calibre = calibre;
        this.entregado = entregado;
    }

    public Personal getEntregado() {
        return entregado;
    }
    public int getNumero() {
        return numero;
    }

    public ModeloArmamento getModelo() {
        return modelo;
    }

    public Date getFechaAlta() {
        return fechaAlta;
    }

    public Date getFechaBaja() {
        return fechaBaja;
    }

    public Destino getDestino() {
        return destino;
    }

    public Municion getCalibre() {
        return calibre;
    }
    
    
    
}
