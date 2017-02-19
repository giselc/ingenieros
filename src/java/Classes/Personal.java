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
public class Personal {
    private Grado grado;
    private String nombre;
    private String apellido;
    private int ci;
    private Date vencimientoCI;
    private String pasaporte;
    private Date vencimientoPas;
    private String cc;
    private int ccNro;
    private Especialidad especialidad;
    private Boolean experienciaMision;
    private String lugarExpMision;
    private Boolean licenciaConducir;
    private int nroLicCond;
    private String categoriaLicCond;
    private Date vencimientoLicCond;
    private Boolean carneSalud;
    private Date vencimientoCarneSalud;

    public Personal(Grado grado, String nombre, String apellido, int ci, Date vencimientoCI, String pasaporte, Date vencimientoPas, String cc, int ccNro, Especialidad especialidad, Boolean experienciaMision, String lugarExpMision, Boolean licenciaConducir, int nroLicCond, String categoriaLicCond, Date vencimientoLicCond, Boolean carneSalud, Date vencimientoCarneSalud) {
        this.grado = grado;
        this.nombre = nombre;
        this.apellido = apellido;
        this.ci = ci;
        this.vencimientoCI = vencimientoCI;
        this.pasaporte = pasaporte;
        this.vencimientoPas = vencimientoPas;
        this.cc = cc;
        this.ccNro = ccNro;
        this.especialidad = especialidad;
        this.experienciaMision = experienciaMision;
        this.lugarExpMision = lugarExpMision;
        this.licenciaConducir = licenciaConducir;
        this.nroLicCond = nroLicCond;
        this.categoriaLicCond = categoriaLicCond;
        this.vencimientoLicCond = vencimientoLicCond;
        this.carneSalud = carneSalud;
        this.vencimientoCarneSalud = vencimientoCarneSalud;
    }

    public Grado getGrado() {
        return grado;
    }

    public String getNombre() {
        return nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public int getCi() {
        return ci;
    }

    public Date getVencimientoCI() {
        return vencimientoCI;
    }

    public String getPasaporte() {
        return pasaporte;
    }

    public Date getVencimientoPas() {
        return vencimientoPas;
    }

    public String getCc() {
        return cc;
    }

    public int getCcNro() {
        return ccNro;
    }

    public Especialidad getEspecialidad() {
        return especialidad;
    }

    public Boolean getExperienciaMision() {
        return experienciaMision;
    }

    public String getLugarExpMision() {
        return lugarExpMision;
    }

    public Boolean getLicenciaConducir() {
        return licenciaConducir;
    }

    public int getNroLicCond() {
        return nroLicCond;
    }

    public String getCategoriaLicCond() {
        return categoriaLicCond;
    }

    public Date getVencimientoLicCond() {
        return vencimientoLicCond;
    }

    public Boolean getCarneSalud() {
        return carneSalud;
    }

    public Date getVencimientoCarneSalud() {
        return vencimientoCarneSalud;
    }
    
    
}
