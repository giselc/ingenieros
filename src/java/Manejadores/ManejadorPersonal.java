/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Manejadores;

import Classes.Apoderado;
import Classes.ConexionBD;
import Classes.Documento;
import Classes.Especialidad;
import Classes.Personal;
import Classes.RecordPersonal;
import Classes.Sancion;
import java.sql.Blob;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Time;
import static java.sql.Types.NULL;
import java.util.ArrayList;
import java.util.Base64;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Gisel
 */
public class ManejadorPersonal {
    private Connection connection;

    public ManejadorPersonal() {
        connection = ConexionBD.GetConnection();
    }
    public Personal getPersonalBasico(int ci){
        Personal p=null;
        try {
            String sql= "Select * from personal where ci="+ci;
            Statement s= connection.createStatement();
            ResultSet rs = s.executeQuery(sql);
            ManejadorCodigos mc = new ManejadorCodigos();
            RecordPersonal rp= new RecordPersonal();
            if(rs.next()){
                rp.fotoBlob=rs.getBlob("foto");
                rp.grado=mc.getGrado(rs.getInt("grado"));
                rp.nombre=rs.getString("nombres");
                rp.apellido=rs.getString("apellidos");
                rp.ci=rs.getInt("ci");
                rp.fechaNac=rs.getString("FechaNac");
                rp.vtoCI=rs.getString("vtoCi");
                rp.pasaporte=rs.getString("pasaporte");
                rp.vtoPas=rs.getString("vtoPas");
                rp.cc=rs.getString("cc");
                rp.ccNro=rs.getInt("ccNro");
                rp.expMision=rs.getBoolean("expMision");
                rp.lugarExpMision=rs.getString("lugarExpMision");
                rp.licenciaConducir=rs.getBoolean("licenciaConducir");
                rp.nroLicCond=rs.getInt("nroLicCond");
                rp.catLicCond=rs.getString("catLicCond");
                rp.vtoLicCond=rs.getString("vtoLicCond");
                rp.carneSalud=rs.getBoolean("carneSalud");
                rp.vtoCarneSalud=rs.getString("vtoCarneSalud");
                rp.unidadMilitar=mc.getUnidadMilitar(rs.getInt("unidadMilitar"));
                rp.apoderado=this.getApoderado(rs.getInt("ci"));
                p=new Personal(rp);
            }
            else{
                System.out.print("errorSQL"+sql);
            }
        } catch (SQLException ex) {
            Logger.getLogger(ManejadorClases.class.getName()).log(Level.SEVERE, null, ex);
        }
        return p;
    }
    //retorna una lista del personal de la tabla Personal de la BD.
    public ArrayList<Personal> getListaPersonalBasico(){
        ArrayList<Personal> ap= new ArrayList<>();
        try {
            String sql= "Select * from personal";
            Statement s= connection.createStatement();
            ResultSet rs = s.executeQuery(sql);
            Personal p;
            ManejadorCodigos mc = new ManejadorCodigos();
            RecordPersonal rp= new RecordPersonal();
            while(rs.next()){
                rp.foto=null; //null porque no es necesario
                rp.grado=mc.getGrado(rs.getInt("grado"));
                rp.nombre=rs.getString("nombres");
                rp.apellido=rs.getString("apellidos");
                rp.ci=rs.getInt("ci");
                rp.fechaNac=rs.getString("FechaNac");
                rp.vtoCI=rs.getString("vtoCi");
                rp.pasaporte=rs.getString("pasaporte");
                rp.vtoPas=rs.getString("vtoPas");
                rp.cc=rs.getString("cc");
                rp.ccNro=rs.getInt("ccNro");
                rp.expMision=rs.getBoolean("expMision");
                rp.lugarExpMision=rs.getString("lugarExpMision");
                rp.licenciaConducir=rs.getBoolean("licenciaConducir");
                rp.nroLicCond=rs.getInt("nroLicCond");
                rp.catLicCond=rs.getString("catLicCond");
                rp.vtoLicCond=rs.getString("vtoLicCond");
                rp.carneSalud=rs.getBoolean("carneSalud");
                rp.vtoCarneSalud=rs.getString("vtoCarneSalud");
                rp.unidadMilitar=mc.getUnidadMilitar(rs.getInt("unidadMilitar"));
                rp.apoderado=this.getApoderado(rs.getInt("ci"));
                p=new Personal(rp);
                ap.add(p);
            }
        } catch (SQLException ex) {
            Logger.getLogger(ManejadorClases.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return ap;
    }
    public boolean agregarPersonal(RecordPersonal rp){
        try {
            String a = "";String a1="";String b="";String b1 ="";String c="";String c1="";String d="";String d1 = "";String e="";String e1 = "";
            if(!rp.vtoLicCond.equals("")){
                a=",vtoLicCond";
                a1=",?";
            }
            if(!rp.vtoPas.equals("")){
                b=",vtoPas";
                b1=",?";
            }
            if(!rp.vtoCI.equals("")){
                c=",vtoCI";
                c1=",?";
            }
            if(!rp.fechaNac.equals("")){
                d=",fechaNac";
                d1=",?";
            }
            if(!rp.vtoCarneSalud.equals("")){
                e=",vtoCarneSalud";
                e1=",?";
            }
            String sql= "insert into personal (ci, nombres, apellidos,grado, pasaporte,cc,ccNro,expMision,lugarExpMision,licenciaConducir, nroLicCond, catLicCond,carneSalud,unidadMilitar,foto"+a+b+c+d+e+") values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?"+a1+b1+c1+d1+e1+")";
            PreparedStatement s= connection.prepareStatement(sql);
            s.setInt(1,rp.ci);
            s.setString(2, rp.nombre);
            s.setString(3, rp.apellido);
            s.setInt(4, rp.gradoInt);
            s.setString(5, rp.pasaporte);
            s.setString(6, rp.cc);
            s.setInt(7,rp.ccNro);
            s.setBoolean(8, rp.expMision);
            s.setString(9, rp.lugarExpMision);
            s.setBoolean(10, rp.licenciaConducir);
            s.setInt(11,rp.nroLicCond);
            s.setString(12, rp.catLicCond);
            s.setBoolean(13, rp.carneSalud);
            s.setInt(14,rp.unidadMilitarId);
            if(rp.foto==null || rp.foto.equals("")){
                s.setNull(15, NULL);
            }
            else{
                byte[] imageByte = Base64.getDecoder().decode(rp.foto);
                Blob blob = connection.createBlob();//Where connection is the connection to db object. 
                blob.setBytes(1, imageByte);
                s.setBlob(15, blob);
            }
            int i = 16;
            if(!rp.vtoLicCond.equals("")){
                s.setString(i++, rp.vtoLicCond);
            }
            if(!rp.vtoPas.equals("")){
                s.setString(i++, rp.vtoPas);
            }
            if(!rp.vtoCI.equals("")){
                s.setString(i++, rp.vtoCI);
            }

            if(!rp.fechaNac.equals("")){
                s.setString(i++, rp.fechaNac);
            }
            if(!rp.vtoCarneSalud.equals("")){
                s.setString(i++, rp.vtoCarneSalud);
            }
            System.out.print(s);
            int result = s.executeUpdate();
            if(result>0){
                return true;
            }
        } catch (Exception ex) {
            Logger.getLogger(ManejadorClases.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }
    public boolean modificarPersonalDatosBasicos(RecordPersonal rp){
        try {
                String strFoto="";
                if(!rp.foto.equals("")){
                     strFoto=",foto=?";
                }
                String a = "";String b="";String c="";String d="";String e="";
                if(!rp.vtoLicCond.equals("")){
                    a=",vtoLicCond=?";
                }
                if(!rp.vtoPas.equals("")){
                    b=",vtoPas=?";
                }
                if(!rp.vtoCI.equals("")){
                    c=",vtoCI=?";
                }
                if(!rp.fechaNac.equals("")){
                    d=",fechaNac=?";
                }
                if(!rp.vtoCarneSalud.equals("")){
                    e=",vtoCarneSalud=?";
                }
                String sql= "update personal set nombres=?, apellidos=?, grado=?, pasaporte=?, cc=? ,ccNro=? ,expMision=? ,lugarExpMision=? ,licenciaConducir=? , nroLicCond=? , catLicCond=? , carneSalud=? , unidadMilitar=?"+strFoto+a+b+c+d+e+" where ci="+rp.ci;
                PreparedStatement s= connection.prepareStatement(sql);
                s.setString(1, rp.nombre);
                s.setString(2, rp.apellido);
                s.setInt(3, rp.gradoInt);
                s.setString(4, rp.pasaporte);
                s.setString(5, rp.cc);
                s.setInt(6,rp.ccNro);
                s.setBoolean(7, rp.expMision);
                s.setString(8, rp.lugarExpMision);
                s.setBoolean(9, rp.licenciaConducir);
                s.setInt(10,rp.nroLicCond);
                s.setString(11, rp.catLicCond);
                s.setBoolean(12, rp.carneSalud);
                s.setInt(13,rp.unidadMilitarId);
                int i=14;
                if (!rp.foto.equals("")){
                    byte[] imageByte = Base64.getDecoder().decode(rp.foto);
                    Blob blob = connection.createBlob();//Where connection is the connection to db object. 
                    blob.setBytes(1, imageByte);
                    s.setBlob(i++, blob);
                }
                if(!rp.vtoLicCond.equals("")){
                    s.setString(i++, rp.vtoLicCond);
                }
                if(!rp.vtoPas.equals("")){
                   s.setString(i++, rp.vtoPas);
                }
                if(!rp.vtoCI.equals("")){
                    s.setString(i++, rp.vtoCI);
                }
                if(!rp.fechaNac.equals("")){
                    s.setString(i++, rp.fechaNac);
                }
                if(!rp.vtoCarneSalud.equals("")){
                    s.setString(i++, rp.vtoCarneSalud);
                }
                int result = s.executeUpdate();
                if(result>0){
                    return true;
                }
            } catch (SQLException ex) {
                Logger.getLogger(ManejadorClases.class.getName()).log(Level.SEVERE, null, ex);
            }
        return false;
    }
    
    //Sanciones
    public int diferenciasDiasDesdeHoy(Sancion s) {

        final int MILLSECS_PER_DAY = 24 * 60 * 60 * 1000;
        java.util.Date hoy = new java.util.Date();
        long deltaDays = ( hoy.getTime() - s.getFecha().getTime())/MILLSECS_PER_DAY;
        int diasCumplidos =0;
        if(hoy.getHours()>=18){
            diasCumplidos= (int)deltaDays + 1;
        }
        else{
            diasCumplidos= (int)deltaDays;
        }
        return diasCumplidos;
    }
    public HashMap<Integer,Integer> getListaDiasPortipoSancion(int idPersonal){
        HashMap<Integer,Integer> hm= new HashMap<>();
        try {
            String sql= "Select * from sanciones where vigente=1 and idPersonal="+idPersonal + " order by fecha asc, hora asc";
            String sql2= "Select sum(dias) as suma from sanciones where vigente=1 and idPersonal="+idPersonal;
            Statement s= connection.createStatement();
            Statement s2= connection.createStatement();
            ResultSet rs = s.executeQuery(sql);
            ResultSet rs2 = s2.executeQuery(sql2);
            Sancion sancion;
            int diasAcumplir=0;
            if(rs2.next()){
                diasAcumplir=rs2.getInt("suma");
            }
            System.out.print(diasAcumplir);
            ManejadorCodigos mc = new ManejadorCodigos();
            int diasCumplidos;
            int diasRestantes=0;
            while(rs.next()){
                sancion=new Sancion(rs.getInt("id"),mc.getTipoSancion(rs.getInt("idTipoSancion")),rs.getString("parte"),rs.getInt("dias"),rs.getDate("fecha"),rs.getTime("hora"),this.getPersonalBasico(rs.getInt("idorden")),this.getPersonalBasico(rs.getInt("idPersonal")),rs.getBoolean("vigente"));
                if(hm.get(sancion.getTipo().getId())==null){ //si es primero de su tipo
                    diasCumplidos = diferenciasDiasDesdeHoy(sancion);
                    System.out.print(diasCumplidos);
                    if(diasCumplidos - sancion.getDias() >=0){ //cumpli mas dias
                        this.modificarSancionVigente(false, sancion.getId());
                        hm.put(sancion.getTipo().getId(), diasAcumplir- diasCumplidos);
                    }
                    else{ //me quedan dias por cumplir
                        hm.put(sancion.getTipo().getId(), diasRestantes);
                    }
                    diasRestantes = diasCumplidos - sancion.getDias();
                    System.out.print(diasRestantes);
                }
                else{ // ya pase los calculos debo colocar vigente a lo que me resta
                    if(diasRestantes-sancion.getDias()>0){
                        this.modificarSancionVigente(false, sancion.getId());
                        diasRestantes -=sancion.getDias();
                    }
                    else{
                        diasRestantes -=sancion.getDias();
                    }
                     System.out.print(diasRestantes);
                }
            }
        } catch (SQLException ex) {
            Logger.getLogger(ManejadorClases.class.getName()).log(Level.SEVERE, null, ex);
        }
        return hm;
    }
    public boolean modificarSancionVigente(boolean vigente, int id){
        try{
             String sql= "update sanciones set vigente=? where id="+id;
                PreparedStatement s= connection.prepareStatement(sql);
                s.setBoolean(1, vigente);
                int result = s.executeUpdate();
                if(result>0){
                    return true;
                }
            } catch (SQLException ex) {
                Logger.getLogger(ManejadorClases.class.getName()).log(Level.SEVERE, null, ex);
            }
        return false;
    }
    public ArrayList<Sancion> getSanciones(int idPersonal){
        ArrayList<Sancion> as= new ArrayList<>();
        try {
            String sql= "Select * from sanciones where idPersonal="+idPersonal + " order by vigente desc, fecha desc, hora desc";
            Statement s= connection.createStatement();
            ResultSet rs = s.executeQuery(sql);
            Sancion sancion;
            ManejadorCodigos mc = new ManejadorCodigos();
            while(rs.next()){
                sancion=new Sancion(rs.getInt("id"),mc.getTipoSancion(rs.getInt("idTipoSancion")),rs.getString("parte"),rs.getInt("dias"),rs.getDate("fecha"),rs.getTime("hora"),this.getPersonalBasico(rs.getInt("idorden")),this.getPersonalBasico(rs.getInt("idPersonal")),rs.getBoolean("vigente"));
                as.add(sancion);
            }
        } catch (SQLException ex) {
            Logger.getLogger(ManejadorClases.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return as;
    }
    public Sancion getSancion(int id){
        try {
            String sql= "Select * from sanciones where id="+id;
            Statement s= connection.createStatement();
            ResultSet rs = s.executeQuery(sql);
            Sancion sancion;
            ManejadorCodigos mc = new ManejadorCodigos();
            while(rs.next()){
                sancion=new Sancion(rs.getInt("id"),mc.getTipoSancion(rs.getInt("idTipoSancion")),rs.getString("parte"),rs.getInt("dias"),rs.getDate("fecha"),rs.getTime("hora"),this.getPersonalBasico(rs.getInt("idorden")), this.getPersonalBasico(rs.getInt("idPersonal")),rs.getBoolean("vigente"));
                return sancion;
            }
        } catch (SQLException ex) {
            Logger.getLogger(ManejadorClases.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return null;
    }
    public boolean crearSancion(int ci, int tipoSancion, int orden, String parte, int dias, String fecha, String hora){
        String sql= "insert into sanciones (idPersonal, idOrden, parte,fecha, idTipoSancion,dias, hora) values(?,?,?,?,?,?,?)";
        try{
            PreparedStatement s= connection.prepareStatement(sql);
            s.setInt(1,ci);
            s.setInt(2, orden);
            s.setString(3,parte);
            s.setString(4, fecha);
            s.setInt(5, tipoSancion);
            s.setInt(6, dias);
            s.setString(7, hora);
            int result = s.executeUpdate();
            if(result>0){
                return true;
            }
        } catch (Exception ex) {
            Logger.getLogger(ManejadorClases.class.getName()).log(Level.SEVERE, null, ex);
        }

        return false;
    }
    public  boolean modificarSancion(int id, int ci, int tipoSancion, int orden, String parte, int dias, String fecha, String hora){
        try{
             String sql= "update sanciones set idPersonal=?, idTipoSancion=?, idorden=?, parte=?, dias=? ,fecha=?,hora=? where id="+id;
                PreparedStatement s= connection.prepareStatement(sql);
                s.setInt(1, ci);
                s.setInt(2, tipoSancion);
                s.setInt(3, orden);
                s.setString(4, parte);
                s.setInt(5, dias);
                s.setString(6,fecha);
                s.setString(7,hora);
                int result = s.executeUpdate();
                if(result>0){
                    return true;
                }
            } catch (SQLException ex) {
                Logger.getLogger(ManejadorClases.class.getName()).log(Level.SEVERE, null, ex);
            }
        return false;
    }
    public boolean eliminarSancion(int id){
        try {
            Statement s= connection.createStatement();
            String sql="delete from sanciones where id="+id;
            int i= s.executeUpdate(sql);
            return (i>0);
        } catch (SQLException ex) {
            Logger.getLogger(ManejadorCodigos.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }
    //documentos
    public ArrayList<Documento> getDocumentosListar(int idPersonal){ //sin la imagen
        ArrayList<Documento> as= new ArrayList<>();
        try {
            String sql= "Select * from documentos where idPersonal="+idPersonal;
            Statement s= connection.createStatement();
            ResultSet rs = s.executeQuery(sql);
            Documento documento;
            ManejadorCodigos mc = new ManejadorCodigos();
            Personal p = this.getPersonalBasico(idPersonal);
            while(rs.next()){
                documento=new Documento(rs.getInt("id"),p,mc.getTipoDocumento(rs.getInt("idTipoDocumento")),null);
                as.add(documento);
            }
        } catch (SQLException ex) {
            Logger.getLogger(ManejadorClases.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return as;
    }
    public Documento getDocumento(int id){
        Documento p=null;
        try {
            String sql= "Select * from documentos where id="+id;
            Statement s= connection.createStatement();
            ResultSet rs = s.executeQuery(sql);
            ManejadorCodigos mc = new ManejadorCodigos();
            if(rs.next()){
                p= new Documento(id, this.getPersonalBasico(rs.getInt("idPersonal")), mc.getTipoDocumento(rs.getInt("idTipoDocumento")), rs.getBlob("imagen"));
            }
        } catch (SQLException ex) {
            Logger.getLogger(ManejadorClases.class.getName()).log(Level.SEVERE, null, ex);
        }
        return p;
    }
    public boolean modificarDocumento(int id, int tipoDocumento, String foto){
        try {
            String strFoto="";
            if(!foto.equals("")){
                 strFoto=", imagen=?";
            }
            String sql= "update documentos set idTipoDocumento=?"+strFoto+" where id="+id;
            PreparedStatement s= connection.prepareStatement(sql);
            s.setInt(1,tipoDocumento);
            if(!foto.equals("")){
                byte[] imageByte = Base64.getDecoder().decode(foto);
                Blob blob = connection.createBlob();//Where connection is the connection to db object. 
                blob.setBytes(1, imageByte);
                s.setBlob(2, blob);
            }
           
            int result = s.executeUpdate();
            if(result>0){
                return true;
            }
        } catch (Exception ex) {
            Logger.getLogger(ManejadorClases.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }
    public boolean crearDocumento(int tipoDocumento, int idPersonal, String foto){
        try {
            String sql= "insert into documentos (idPersonal, idTipoDocumento, imagen) values(?,?,?)";
            PreparedStatement s= connection.prepareStatement(sql);
            s.setInt(1,idPersonal);
            s.setInt(2, tipoDocumento);
            byte[] imageByte = Base64.getDecoder().decode(foto);
            Blob blob = connection.createBlob();//Where connection is the connection to db object. 
            blob.setBytes(1, imageByte);
            s.setBlob(3, blob);
            int result = s.executeUpdate();
            if(result>0){
                return true;
            }
        } catch (Exception ex) {
            Logger.getLogger(ManejadorClases.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }
    public boolean eliminarDocumento(int id){
        try {
            Statement s= connection.createStatement();
            String sql="delete from documentos where id="+id;
            int i= s.executeUpdate(sql);
            return (i>0);
        } catch (SQLException ex) {
            Logger.getLogger(ManejadorCodigos.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }
    
    //especialidades
    public ArrayList<Especialidad> getEspecialidadesListar(int idPersonal){ //sin la imagen
        ArrayList<Especialidad> as= new ArrayList<>();
        try {
            String sql= "Select * from `personal-especialidad` where idPersonal="+idPersonal;
            Statement s= connection.createStatement();
            ResultSet rs = s.executeQuery(sql);
            Especialidad especialidad;
            ManejadorCodigos mc = new ManejadorCodigos();
            while(rs.next()){
                especialidad=mc.getEspecialidad(rs.getInt("idEspecialidad"));
                as.add(especialidad);
            }
        } catch (SQLException ex) {
            Logger.getLogger(ManejadorClases.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return as;
    }
    public boolean agregarEspecialidad(int idEspecialidad, int idPersonal){
        try {
            String sql= "insert into `personal-especialidad` (idPersonal, idEspecialidad) values ("+idPersonal+","+idEspecialidad+")";
            Statement s= connection.createStatement();
            int result = s.executeUpdate(sql);
            if(result>0){
                return true;
            }
        } catch (Exception ex) {
            Logger.getLogger(ManejadorClases.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }
    public boolean eliminarEspecialidad(int idEspecialidad, int idPersonal){
        try {
            String sql= "delete from `personal-especialidad` where idPersonal="+idPersonal+" and idEspecialidad="+idEspecialidad;
            Statement s= connection.createStatement();
            int result = s.executeUpdate(sql);
            if(result>0){
                return true;
            }
        } catch (Exception ex) {
            Logger.getLogger(ManejadorClases.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }
//APODERADOOO
    
    //retorna una Apoderado con los datos obtenidos y persiste en la base de datos la relacion apoderado-personal
    //retorna null si se produce algun error en la escritura de la base de datos.
    public boolean crearApoderado(int ciPersonal, int ci,String nombres, String apellidos,int idvinculo,String domicilio,String celular,String telefono){
        try {
            Statement s= connection.createStatement();
            String sql="Select * from apoderados where ci="+ci;
            ResultSet rs= s.executeQuery(sql);
            int i;
            if (!rs.next()){
                sql="insert into apoderados (ci, nombres, apellidos, domicilio, celular,telefono) values("+ci+",'"+nombres+"','"+apellidos+"','"+domicilio+"','"+celular+"','"+telefono+"')";
                i= s.executeUpdate(sql);
                
            }
            else{
                sql="update apoderados set nombres='"+nombres+"',apellidos='"+apellidos+"',domicilio='"+domicilio+"',celular='"+celular+"',telefono='"+telefono+"' where ci="+ci;
                i= s.executeUpdate(sql);
            }
            if(i>0){
                sql="update personal set idApoderado="+ci+", idVinculo="+idvinculo+" where ci="+ciPersonal;
                i = s.executeUpdate(sql);
                ManejadorCodigos mc= new ManejadorCodigos();
                return i>0;
            }
            
        } catch (SQLException ex) {
            Logger.getLogger(ManejadorCodigos.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }
    
    //retorna el apoderado que tiene la relacion apoderado-personal segun los parametros
    //retorna null si no hay relacion
    //retorna null si se produjo un error con la base de datos
    public Apoderado getApoderado(int ciPersonal){
        try {
            Statement s= connection.createStatement();
            String sql="Select apoderados.ci, apoderados.nombres, apoderados.apellidos, apoderados.domicilio,personal.idVinculo, apoderados.domicilio, apoderados.celular, apoderados.telefono from personal left join apoderados on personal.idApoderado=apoderados.ci where Personal.ci="+ciPersonal;
            ResultSet rs= s.executeQuery(sql);
            if (rs.next()){
                ManejadorCodigos mc= new ManejadorCodigos();
                if(rs.getInt("ci")==0){
                    return null;
                }
                return new Apoderado(rs.getInt("ci"), rs.getString("nombres"), rs.getString("apellidos"), mc.getTipoFamiliar(rs.getInt("idVinculo")), rs.getString("domicilio"), rs.getString("celular"), rs.getString("telefono"));
            }
        } catch (SQLException ex) {
            Logger.getLogger(ManejadorCodigos.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
    
    //modifica atributos de un apoderado y su vinculo con idPersonal
    public boolean modificarApoderado(int idPersonal, Apoderado apod){
        try {
            Statement s= connection.createStatement();
            String sql="Update personal-apoderado left join apoderados on idApoderado=ci set idVinculo="+ apod.getVinculo().getId() +", nombre="+ apod.getNombre() +", apellido="+ apod.getApellido() +", celular="+ apod.getCelular() +", domicilio="+ apod.getDomicilio() +", telefono="+ apod.getTelefono() +" where apoderados.ci="+apod.getCi() +" and Personal-apoderado.idPersonal="+idPersonal;
            int rs= s.executeUpdate(sql);
            return (rs>0);
        } catch (SQLException ex) {
            Logger.getLogger(ManejadorCodigos.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }
    
    //desvincula el apoderado con ci=idApod con el personal con ci=idPersonal
    //los datos del apoderado son eliminados de la base de datos si no hay ningun otro vinculo con idApod
    //retorna 0 si se produjo error con base de datos
    // 1 si se desvinculo y no se borro los datos
    // 2 si se desvinculo y se borro los datos 
    public int desvincularApoderado(int idPersonal, int idApod){
        try {
            Statement s= connection.createStatement();
            String sql="update form personal set idApoderado=-1, idVinculo=-1 where  ci="+idPersonal;
            int i= s.executeUpdate(sql);
            if (i>0){
                sql="select form personal where idApoderado="+idApod;
                ResultSet rs= s.executeQuery(sql);
                if(rs.next()){
                    return 1;
                }
                else{
                    sql="delete form apoderados where ci="+idApod;
                    i= s.executeUpdate(sql);
                    if(i>0){
                        return 2;
                    }
                    else{
                        return 0;
                    }
                }
            }
            else{
                return 0;
            }
        } catch (SQLException ex) {
            Logger.getLogger(ManejadorCodigos.class.getName()).log(Level.SEVERE, null, ex);
            return 0;
        }
    }
    
    
}
