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
public class AlertaMantenimiento {
    int id;
    AlertaVehiculo alertaVehiculo;
    Date fecha;
    int horasKilometrosAlcanzados;

    public AlertaMantenimiento(int id, AlertaVehiculo alertaVehiculo, Date fecha,int horasKilometrosAlcanzados) {
        this.id = id;
        this.alertaVehiculo = alertaVehiculo;
        this.fecha = fecha;
        this.horasKilometrosAlcanzados = horasKilometrosAlcanzados;
    }

    public int getId() {
        return id;
    }

    public AlertaVehiculo getAlertaVehiculo() {
        return alertaVehiculo;
    }

    public Date getFecha() {
        return fecha;
    }

    public int getHorasKilometrosAlcanzados() {
        return horasKilometrosAlcanzados;
    }
    
    
    
}
