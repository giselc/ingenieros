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
public class ObservacionVehiculo {
    int id;
    String idVehiculo; //matricula UY
    String infromacionSumaria; //String de nombre de archivo de la informacion sumaria idObservacion+NombreArchivo
    Date fecha;
    int horasKilometrosRealizados;
    String observacion;
    boolean mantenimiento;
    TipoMantenimientoVehiculo tipoMantenimiento;
    int horasKilometrosMarcados;
    Personal idOperario;
    Personal idEscribiente;

    public ObservacionVehiculo(int id, String idVehiculo, String infromacionSumaria, Date fecha, int horasKilometrosRealizados, String observacion, boolean mantenimiento, TipoMantenimientoVehiculo tipoMantenimiento, int horasKilometrosMarcados, Personal idOperario, Personal idEscribiente) {
        this.id = id;
        this.idVehiculo = idVehiculo;
        this.infromacionSumaria = infromacionSumaria;
        this.fecha = fecha;
        this.horasKilometrosRealizados = horasKilometrosRealizados;
        this.observacion = observacion;
        this.mantenimiento = mantenimiento;
        this.tipoMantenimiento = tipoMantenimiento;
        this.horasKilometrosMarcados = horasKilometrosMarcados;
        this.idOperario = idOperario;
        this.idEscribiente = idEscribiente;
    }

    

    public int getId() {
        return id;
    }

    public String getIdVehiculo() {
        return idVehiculo;
    }

    public String getInfromacionSumaria() {
        return infromacionSumaria;
    }

    public Date getFecha() {
        return fecha;
    }

    public int getHorasKilometrosRealizados() {
        return horasKilometrosRealizados;
    }

    public boolean isMantenimiento() {
        return mantenimiento;
    }

    public int getHorasKilometrosMarcados() {
        return horasKilometrosMarcados;
    }


    public String getObservacion() {
        return observacion;
    }

    public Personal getIdOperario() {
        return idOperario;
    }

    public Personal getIdEscribiente() {
        return idEscribiente;
    }

    public TipoMantenimientoVehiculo getTipoMantenimiento() {
        return tipoMantenimiento;
    }
    
    
    
    
    
    
}
