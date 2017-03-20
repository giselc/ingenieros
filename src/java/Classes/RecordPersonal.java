/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Classes;

import java.sql.Blob;
import java.sql.Date;

/**
 *
 * @author Gisel
 */
public class RecordPersonal {
    public Grado grado;
    public int gradoInt;
    
    public String nombre;
    public String apellido;
    public int ci;
    public String fechaNac;
    public String vtoCI;
    public String pasaporte;
    public String vtoPas;
    public String cc;
    public int ccNro;
    public Boolean expMision;
    public String lugarExpMision;
    public Boolean licenciaConducir;
    public int nroLicCond;
    public String catLicCond;
    public String vtoLicCond;
    public Boolean carneSalud;
    public String vtoCarneSalud;
    
    public UnidadMilitar unidadMilitar;
    public int unidadMilitarId;
    
    public Apoderado apoderado;
    public String foto;
    public Blob fotoBlob;
}
