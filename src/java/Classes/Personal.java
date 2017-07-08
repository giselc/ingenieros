/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Classes;

import java.sql.Blob;
import java.sql.Date;
import java.time.LocalDate;
import java.util.ArrayList;

/**
 *
 * @author Gisel
 */

//PERSONAL DE HISTORIAL TIENE NULL DOCUMENTOS.
public class Personal {
    private int idONU;
    private Blob foto;
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

    public Personal(RecordPersonal rp) {
        this.idONU = rp.idONU;
        this.foto = rp.fotoBlob;
        this.grado = rp.grado;
        this.nombre = rp.nombre;
        this.apellido = rp.apellido;
        this.ci = rp.ci;
        if(rp.fechaNac!=null && !rp.fechaNac.equals("")){
            this.fechaNac = Date.valueOf(rp.fechaNac);
        }
        else{
            this.fechaNac=null;
        }
        if(rp.vtoCI!=null && !rp.vtoCI.equals("")){
            this.vtoCI = Date.valueOf(rp.vtoCI);
        }
        else{
            this.vtoCI=null;
        }
        this.pasaporte = rp.pasaporte;
        if( rp.vtoPas!=null &&!rp.vtoPas.equals("")){
            this.vtoPas = Date.valueOf(rp.vtoPas);
        }
        else{
            this.vtoPas=null;
        }
        this.cc = rp.cc;
        this.ccNro = rp.ccNro;
        this.expMision = rp.expMision;
        this.lugarExpMision = rp.lugarExpMision;
        this.licenciaConducir = rp.licenciaConducir;
        this.nroLicCond = rp.nroLicCond;
        this.catLicCond = rp.catLicCond;
        if(rp.vtoLicCond!=null && !rp.vtoLicCond.equals("")){
            this.vtoLicCond = Date.valueOf(rp.vtoLicCond);
        }
        else{
            this.vtoLicCond=null;
        }
        this.carneSalud = rp.carneSalud;
        if(rp.vtoCarneSalud!=null && !rp.vtoCarneSalud.equals("")){
            this.vtoCarneSalud = Date.valueOf(rp.vtoCarneSalud);
        }
        else{
            this.vtoCarneSalud=null;
        }
        this.sanciones = new ArrayList<>();
        this.unidadPerteneciente = rp.unidadMilitar;
        this.documentos = new ArrayList<>();
        this.apoderado = rp.apoderado;
        this.familiares = new ArrayList<>();
        this.especialidades = new ArrayList<>();
    }

    public int getIdONU() {
        return idONU;
    }

    public Blob getFoto() {
        return foto;
    }

    public Date getFechaNac() {
        return fechaNac;
    }

    public ArrayList<Especialidad> getEspecialidades() {
        return especialidades;
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
