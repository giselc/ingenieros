/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Classes;
/**
 *
 * @author Gisel
 */
public class AlertaVehiculo {
    int id;
    String idVehiculo;
    TipoMantenimientoVehiculo tipoMantenimiento;
    int horasKilometros;
    int horasKilometrosAlerta;

    public AlertaVehiculo(int id,String idVehiculo, TipoMantenimientoVehiculo tipoMantenimiento, int horasKilometros , int horasKilometrosAlerta) {
        this.id = id;
        this.idVehiculo = idVehiculo;
        this.tipoMantenimiento = tipoMantenimiento;
        this.horasKilometros = horasKilometros;
        this.horasKilometrosAlerta = horasKilometrosAlerta;
    }

    public int getId() {
        return id;
    }

    public String getIdVehiculo() {
        return idVehiculo;
    }
    
    public TipoMantenimientoVehiculo getTipoMantenimiento() {
        return tipoMantenimiento;
    }

    public int getHorasKilometros() {
        return horasKilometros;
    }

    public int getHorasKilometrosAlerta() {
        return horasKilometrosAlerta;
    }
    
    
}
