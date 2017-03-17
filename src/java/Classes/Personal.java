/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Classes;

import java.sql.Date;
import java.util.ArrayList;

/**
 *
 * @author Gisel
 */

//PERSONAL DE HISTORIAL TIENE NULL DOCUMENTOS.
public class Personal {
    private Grado grado;
    private String nombre;
    private String apellido;
    private int ci;
    private Date fechaNac;
    private Date vtoCI;
    private String pasaporte;
    private Date vtoPas;
    private String cc;
    private int ccNro;
    private Boolean expMision;
    private String lugarExpMision;
    private Boolean licenciaConducir;
    private int nroLicCond;
    private String catLicCond;
    private Date vtoLicCond;
    private Boolean carneSalud;
    private Date vtoCarneSalud;
    
    private ArrayList<Sancion> sanciones;
    private UnidadMilitar unidadPerteneciente;
    private ArrayList<Documento> documentos;
    private Apoderado apoderado;
    private ArrayList<Familiar> familiares;
    private ArrayList<Especialidad> especialidades;

    public Personal(Grado grado, String nombre, String apellido, int ci, Date FechaNac,Date vtoCI, String pasaporte, Date vtoPas, String cc, int ccNro, Boolean expMision, String lugarExpMision, Boolean licenciaConducir, int nroLicCond, String catLicCond, Date vtoLicCond, Boolean carneSalud, Date vtoCarneSalud, UnidadMilitar unidadPerteneciente, Apoderado apoderado) {
        this.grado = grado;
        this.nombre = nombre;
        this.apellido = apellido;
        this.ci = ci;
        this.fechaNac = FechaNac;
        this.vtoCI = vtoCI;
        this.pasaporte = pasaporte;
        this.vtoPas = vtoPas;
        this.cc = cc;
        this.ccNro = ccNro;
        this.expMision = expMision;
        this.lugarExpMision = lugarExpMision;
        this.licenciaConducir = licenciaConducir;
        this.nroLicCond = nroLicCond;
        this.catLicCond = catLicCond;
        this.vtoLicCond = vtoLicCond;
        this.carneSalud = carneSalud;
        this.vtoCarneSalud = vtoCarneSalud;
        this.sanciones = new ArrayList<>();
        this.unidadPerteneciente = unidadPerteneciente;
        this.documentos = new ArrayList<>();
        this.apoderado = apoderado;
        this.familiares = new ArrayList<>();
        this.especialidades = new ArrayList<>();
    }
    
    public ArrayList<Sancion> getSanciones() {
        return sanciones;
    }

    public UnidadMilitar getUnidadPerteneciente() {
        return unidadPerteneciente;
    }

    public ArrayList<Documento> getDocumentos() {
        return documentos;
    }

    public Apoderado getApoderado() {
        return apoderado;
    }

    public ArrayList<Familiar> getFamiliares() {
        return familiares;
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

    public Date getVtoCI() {
        return vtoCI;
    }

    public String getPasaporte() {
        return pasaporte;
    }

    public Date getVtoPas() {
        return vtoPas;
    }

    public String getCc() {
        return cc;
    }

    public int getCcNro() {
        return ccNro;
    }

    public ArrayList<Especialidad> getEspecialidad() {
        return especialidades;
    }

    public Boolean getExpMision() {
        return expMision;
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

    public String getCatLicCond() {
        return catLicCond;
    }

    public Date getVtoLicCond() {
        return vtoLicCond;
    }

    public Boolean getCarneSalud() {
        return carneSalud;
    }

    public Date getVtoCarneSalud() {
        return vtoCarneSalud;
    }
    
    
}
