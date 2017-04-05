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
public class ConsultaMedica {
    private int id;
    private Personal idPersonal;
    private Date fechaInicio;
    private Date fechaFin;
    private String diagnostico;
    private Personal idMedico;
    private String tratamiento;

    public ConsultaMedica(int id, Personal idPersonal, Date fechaInicio, Date fechaFin, String diagnostico, Personal idMedico, String tratamiento) {
        this.id = id;
        this.idPersonal = idPersonal;
        this.fechaInicio = fechaInicio;
        this.fechaFin = fechaFin;
        this.diagnostico = diagnostico;
        this.idMedico = idMedico;
        this.tratamiento = tratamiento;
    }

    public int getId() {
        return id;
    }

    public Personal getIdPersonal() {
        return idPersonal;
    }

    public Date getFechaInicio() {
        return fechaInicio;
    }

    public Date getFechaFin() {
        return fechaFin;
    }

    public String getDiagnostico() {
        return diagnostico;
    }

    public Personal getIdMedico() {
        return idMedico;
    }

    public String getTratamiento() {
        return tratamiento;
    }
    
    
}
