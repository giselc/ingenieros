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
import Classes.Familiar;
import Classes.ConsultaMedica;
import Classes.PeriodoDesplegado;
import Classes.Personal;
import Classes.RecordListarPersonal;
import Classes.RecordPersonal;
import Classes.RecordRecalculo;
import Classes.RecordSancionados;
import Classes.Sancion;
import Classes.Tipo;
import Classes.TipoFamiliar;
import com.sun.faces.util.CollectionsUtils;
import java.io.PrintWriter;
import java.sql.Blob;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Time;
import static java.sql.Types.NULL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Base64;
import java.util.Calendar;
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
    public void CerrarConexionManejador(){
        try {
            connection.close();
        } catch (SQLException ex) {
            Logger.getLogger(ManejadorClases.class.getName()).log(Level.SEVERE, null, ex);
        }
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
                rp.idONU=rs.getInt("idONU");
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
                mc.CerrarConexionManejador();
            }
        } catch (SQLException ex) {
            Logger.getLogger(ManejadorClases.class.getName()).log(Level.SEVERE, null, ex);
        }
        return p;
    }
    public boolean existePersonalHistorial(int ci){
        Personal p=null;
        try {
            String sql= "Select * from `personal-historial` where ci="+ci;
            Statement s= connection.createStatement();
            ResultSet rs = s.executeQuery(sql);
            if(rs.next()){
                return true;
            }
        } catch (SQLException ex) {
            Logger.getLogger(ManejadorClases.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }
    public boolean recuperarDatosHistorial(int ci){
        try{
            Statement s = connection.createStatement();
            String sql= "insert into historiaClinica (idPersonal, fechaInicio, fechaFin, diagnostico, tratamiento, idMedico) select idPersonal, fechaInicio, fechaFin, diagnostico, tratamiento, idMedico from `historiaClinica-historial` where idPersonal="+ci;
            s.addBatch(sql);
            sql= "delete from `historiaClinica-historial` where idPersonal="+ci;
            s.addBatch(sql);
            sql= "insert into sanciones (idPersonal, idTipoSancion, idOrden, parte, fecha, hora, dias) select idPersonal, idTipoSancion, idOrden, parte, fecha, hora, dias from `sanciones-historial` where idPersonal="+ci;
            s.addBatch(sql);
            sql= "delete from `sanciones-historial` where idPersonal="+ci;
            s.addBatch(sql);
            sql= "insert into sancionados select * from `sancionados-historial` where ciPersonal="+ci;
            s.addBatch(sql);
            sql= "delete from `sancionados-historial` where ciPersonal="+ci;
            s.addBatch(sql);
            sql= "insert into personal select * from `personal-historial` where ci="+ci;
            s.addBatch(sql);
            sql= "delete from `personal-historial` where ci="+ci;
            s.addBatch(sql);
            s.executeBatch();
            return true;
        }
        catch(Exception e){
            return false;
        }
    }
    public boolean eliminarPersonal(int ci, boolean guardarHistorial, String fechaArribo, String fechaRegreso, String observaciones){
        try {
            Statement s = connection.createStatement();
            Statement s1 = connection.createStatement();
            String sql="";
            ResultSet rs = null;
            Apoderado a = this.getApoderado(ci);
            if(a!=null){
                sql="update personal set idApoderado=-1, idVinculo=-1 where  ci="+ci;
                s.addBatch(sql);
                sql= "select * from personal where ci<>"+ci+" and idApoderado="+a.getCi();
                rs = s1.executeQuery(sql);
                if(!rs.next()){
                    sql= "delete from apoderados where ci="+a.getCi();
                    s.addBatch(sql);
                }
            }
            ArrayList<Familiar> familiares = this.getFamiliares(ci);
            for(Familiar familiar: familiares){
                sql= "select * from `personal-familiar` where idPersonal<>"+ci+" and idFamiliar="+familiar.getCi();
                rs = s1.executeQuery(sql);
                if(!rs.next()){
                    sql= "delete from familiares where ci="+familiar.getCi();
                    s.addBatch(sql);
                }
            }
            sql= "delete from `personal-familiar` where idPersonal="+ci;
            s.addBatch(sql);
            sql= "delete from documentos where idPersonal="+ci;
            s.addBatch(sql);
            if(guardarHistorial){
                sql= "insert into `historiaClinica-historial` (idPersonal, fechaInicio, fechaFin, diagnostico, tratamiento, idMedico) select idPersonal, fechaInicio, fechaFin, diagnostico, tratamiento, idMedico from historiaClinica where idPersonal="+ci;
                s.addBatch(sql);
                sql= "insert into `sanciones-historial` (idPersonal, idTipoSancion, idOrden, parte, fecha, hora, dias) select idPersonal, idTipoSancion, idOrden, parte, fecha, hora, dias from sanciones where idPersonal="+ci;
                s.addBatch(sql);
                sql= "insert into `personal-historial` select * from personal where ci="+ci;
                s.addBatch(sql);
                sql= "insert into `sancionados-historial` select * from sancionados where ciPersonal="+ci;
                s.addBatch(sql);
                sql= "insert into periodosDesplegado (idPersonal,fechaInicio, fechaFin, observaciones) value ("+ci+",'"+fechaArribo+"','"+fechaRegreso+"','"+observaciones+"')";
                s.addBatch(sql);
                
            }
            sql= "delete from historiaClinica where idPersonal="+ci;
            s.addBatch(sql);
            sql= "delete from `personal-especialidad` where idPersonal="+ci;
            s.addBatch(sql);
            sql= "delete from sanciones where idPersonal="+ci;
            s.addBatch(sql);
            sql= "delete from sancionados where ciPersonal="+ci;
            s.addBatch(sql);
            sql= "delete from personal where ci="+ci;
            s.addBatch(sql);
            s.executeBatch();
            return true;
        } catch (SQLException ex) {
            Logger.getLogger(ManejadorPersonal.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
        
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
                rp.idONU=rs.getInt("idONU");
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
            mc.CerrarConexionManejador();
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
            String sql= "insert into personal (idONU,ci, nombres, apellidos,grado, pasaporte,cc,ccNro,expMision,lugarExpMision,licenciaConducir, nroLicCond, catLicCond,carneSalud,unidadMilitar,foto"+a+b+c+d+e+") values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?"+a1+b1+c1+d1+e1+")";
            PreparedStatement s= connection.prepareStatement(sql);
            int i = 1;
            s.setInt(i++,rp.idONU);
            s.setInt(i++,rp.ci);
            s.setString(i++, rp.nombre);
            s.setString(i++, rp.apellido);
            s.setInt(i++, rp.gradoInt);
            s.setString(i++, rp.pasaporte);
            s.setString(i++, rp.cc);
            s.setInt(i++,rp.ccNro);
            s.setBoolean(i++, rp.expMision);
            s.setString(i++, rp.lugarExpMision);
            s.setBoolean(i++, rp.licenciaConducir);
            s.setInt(i++,rp.nroLicCond);
            s.setString(i++, rp.catLicCond);
            s.setBoolean(i++, rp.carneSalud);
            s.setInt(i++,rp.unidadMilitarId);
            if(rp.foto==null || rp.foto.equals("")){
                s.setNull(i++, NULL);
            }
            else{
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
                String sql= "update personal set idONU=?, nombres=?, apellidos=?, grado=?, pasaporte=?, cc=? ,ccNro=? ,expMision=? ,lugarExpMision=? ,licenciaConducir=? , nroLicCond=? , catLicCond=? , carneSalud=? , unidadMilitar=?"+strFoto+a+b+c+d+e+" where ci="+rp.ci;
                PreparedStatement s= connection.prepareStatement(sql);
                int i=1;
                s.setInt(i++, rp.idONU);
                s.setString(i++, rp.nombre);
                s.setString(i++, rp.apellido);
                s.setInt(i++, rp.gradoInt);
                s.setString(i++, rp.pasaporte);
                s.setString(i++, rp.cc);
                s.setInt(i++,rp.ccNro);
                s.setBoolean(i++, rp.expMision);
                s.setString(i++, rp.lugarExpMision);
                s.setBoolean(i++, rp.licenciaConducir);
                s.setInt(i++,rp.nroLicCond);
                s.setString(i++, rp.catLicCond);
                s.setBoolean(i++, rp.carneSalud);
                s.setInt(i++,rp.unidadMilitarId);
                
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
    public ArrayList<PeriodoDesplegado> getPeriodosDesplegado(int idPersonal){
        ArrayList<PeriodoDesplegado> as= new ArrayList<>();
        try {
            String sql= "Select * from periodosDesplegado where idPersonal="+idPersonal + " order by fechaInicio asc";
            Statement s= connection.createStatement();
            ResultSet rs = s.executeQuery(sql);
            PeriodoDesplegado periodo;
            while(rs.next()){
                periodo=new PeriodoDesplegado(rs.getInt("id"),this.getPersonalBasico(rs.getInt("idPersonal")),rs.getDate("fechaInicio"),rs.getDate("fechaFin"),rs.getString("observaciones"));
                as.add(periodo);
            }
        } catch (SQLException ex) {
            Logger.getLogger(ManejadorClases.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return as;
    }
    //Sanciones
    public int diferenciasDiasDesdeHoy(Date s, Time h) {
        final int MILLSECS_PER_DAY = 24 * 60 * 60 * 1000;
        java.util.Date hoy = new java.util.Date();
        long deltaDays = ( hoy.getTime() - s.getTime())/MILLSECS_PER_DAY;
        int diasCumplidos =(int)deltaDays;
        //System.out.println(diasCumplidos+ " " +(diasCumplidos!=0 && (hoy.getHours())>18));
        if(diasCumplidos!=0 && (hoy.getHours())<=18){
            diasCumplidos--;
        }
        return diasCumplidos;
    }
    public int diferenciasDiasDesdeHoy2(Date s, Time h) {
        final int MILLSECS_PER_DAY = 24 * 60 * 60 * 1000;
        java.util.Date hoy = new java.util.Date();
        long deltaDays = ( hoy.getTime() - s.getTime())/MILLSECS_PER_DAY;
        int diasCumplidos =(int)deltaDays;
        //System.out.println(diasCumplidos+ " " +(diasCumplidos!=0 && (hoy.getHours())>18));
        return diasCumplidos;
    }
    public HashMap<Integer,ArrayList<RecordSancionados>> getListaSancionadosConDias(){
        HashMap<Integer,ArrayList<RecordSancionados>> hm= new HashMap<>();
        try {
            ResultSet rs = null;
            Statement s= connection.createStatement();
            int diasCumplidos =0;
            String sql="";
            RecordSancionados r=null;
            sql= "Select * from sancionados";
            rs = s.executeQuery(sql);
            ManejadorCodigos mc = new ManejadorCodigos();
            while (rs.next()){
                    diasCumplidos = diferenciasDiasDesdeHoy(rs.getDate("fechaInicial"), rs.getTime("horaInicial"));
                    if (diasCumplidos>=rs.getInt("cantTotalDias")){
                        sql="delete from sancionados where ciPersonal="+rs.getInt("ciPersonal") + " and idTipoSancion="+rs.getInt("idTipoSancion");
                        s.executeUpdate(sql);
                    }
                    else{
                        //System.out.print(rs.getInt("cantTotalDias")+" "+ diasCumplidos);
                        r =new RecordSancionados();
                        r.dias=rs.getInt("cantTotalDias")-diasCumplidos;
                        r.tipo = mc.getTipoSancion(rs.getInt("idTipoSancion"));
                        r.fecha = rs.getDate("fechaInicial");
                        if(hm.containsKey(rs.getInt("ciPersonal"))){
                            hm.get(rs.getInt("ciPersonal")).add(r);
                        }
                        else{
                            hm.put(rs.getInt("ciPersonal"), new ArrayList<>());
                            hm.get(rs.getInt("ciPersonal")).add(r);
                        }
                        
                    }
                
            }
            mc.CerrarConexionManejador();
        } catch (SQLException ex) {
            Logger.getLogger(ManejadorClases.class.getName()).log(Level.SEVERE, null, ex);
        }
        return hm;
    }
    public ArrayList<RecordSancionados> getListaDiasPortipoSancion(int idPersonal){
        ArrayList<RecordSancionados> as= new ArrayList<RecordSancionados>();
        try {
            ManejadorCodigos mc= new ManejadorCodigos();
            ArrayList<Tipo> tipoSancion = mc.getTiposSanciones();
            mc.CerrarConexionManejador();
            ResultSet rs = null;
            Statement s= connection.createStatement();
            int diasCumplidos =0;
            String sql="";
            RecordSancionados r=null;
            for (Tipo t: tipoSancion){
                sql= "Select * from sancionados where ciPersonal="+idPersonal + " and idTipoSancion="+t.getId();
                rs = s.executeQuery(sql);
                if(rs.next()){
                    diasCumplidos = diferenciasDiasDesdeHoy(rs.getDate("fechaInicial"), rs.getTime("horaInicial"));
                    if (diasCumplidos>=rs.getInt("cantTotalDias")){
                        sql="delete from sancionados where ciPersonal="+idPersonal + " and idTipoSancion="+t.getId();
                        s.executeUpdate(sql);
                    }
                    else{
                        //System.out.print(rs.getInt("cantTotalDias")+" "+ diasCumplidos);
                        r =new RecordSancionados();
                        r.dias=rs.getInt("cantTotalDias")-diasCumplidos;
                        r.tipo = t;
                        r.fecha = rs.getDate("fechaInicial");
                        as.add(r);
                    }
                }
            }
        } catch (SQLException ex) {
            Logger.getLogger(ManejadorClases.class.getName()).log(Level.SEVERE, null, ex);
        }
        return as;
    }
    public ArrayList<Sancion> getSanciones(int idPersonal){
        ArrayList<Sancion> as= new ArrayList<>();
        try {
            String id="";
            if(idPersonal!=-1){
                id= "where idPersonal="+idPersonal;
            }
            String sql= "Select * from sanciones "+id+" order by fecha desc, hora desc";
            Statement s= connection.createStatement();
            ResultSet rs = s.executeQuery(sql);
            Sancion sancion;
            ManejadorCodigos mc = new ManejadorCodigos();
            while(rs.next()){
                sancion=new Sancion(rs.getInt("id"),mc.getTipoSancion(rs.getInt("idTipoSancion")),rs.getString("parte"),rs.getInt("dias"),rs.getDate("fecha"),rs.getTime("hora"),this.getPersonalBasico(rs.getInt("idorden")),this.getPersonalBasico(rs.getInt("idPersonal")));
                as.add(sancion);
            }
            mc.CerrarConexionManejador();
        } catch (SQLException ex) {
            Logger.getLogger(ManejadorClases.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return as;
    }
    public ArrayList<Sancion> getSancionesListar(int idPersonal,String fechaDesde, String fechaHasta, int tipoSancion){
        ArrayList<Sancion> as= new ArrayList<>();
        try {
            String Titulo="Sanciones: ";
            String id="";
            if(idPersonal!=-1){
                id="idPersonal="+idPersonal+" and ";
            }
            String tipo="";
            if(tipoSancion!=-1){
                tipo=" idTipoSancion="+tipoSancion+" and ";
            }
            String fecha="";
            if(!fechaDesde.equals("")){
                fecha="fecha>= '"+fechaDesde + "' and ";
                Titulo += "Desde : "+fechaDesde+" ";
            }
             Titulo += "Hasta : "+fechaHasta;
            String sql=sql="Select * from sanciones where "+id+tipo+fecha+" fecha<= '"+fechaHasta + "'  order by fecha desc, hora desc";     
            Statement s= connection.createStatement();
            ResultSet rs = s.executeQuery(sql);
            Sancion sancion;
            ManejadorCodigos mc = new ManejadorCodigos();
            while(rs.next()){
                sancion=new Sancion(rs.getInt("id"),mc.getTipoSancion(rs.getInt("idTipoSancion")),rs.getString("parte"),rs.getInt("dias"),rs.getDate("fecha"),rs.getTime("hora"),this.getPersonalBasico(rs.getInt("idorden")),this.getPersonalBasico(rs.getInt("idPersonal")));
                as.add(sancion);
            }
        } catch (SQLException ex) {
            Logger.getLogger(ManejadorClases.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return as;
    }
    public static String obtenerNombreCompleto(Personal p){
        return (p.getGrado().getAbreviacion()+" "+p.getNombre()+" "+p.getApellido());
    }
    public void imprimirSanciones(int idPersonal,String fechaDesde, String fechaHasta, int tipoSancion, PrintWriter out){
        ArrayList<Sancion> as = this.getSancionesListar(idPersonal, fechaDesde, fechaHasta, tipoSancion);
        out.print("<h1 align='center'>Sanciones</h1>");
        if(!fechaDesde.equals("")){
            out.print("<h2 align='center'>Desde: "+fechaDesde+"</h2>");
        }
         out.print("<h2 align='center'>Hasta: "+fechaHasta+"</h2>");
        out.print("<table style=\"width: 100%;\">");
        out.print("<tr style='background-color:#ffcc66' align='center'>");
                out.print("<td style='width: 20%' align='center'><h3 style='margin:2%;'>A</h3></td>");
                out.print("<td style='width: 20%' align='center'><h3 style='margin:2%;'>Tipo sanción</h3></td>");
                out.print("<td style='width: 20%' align='center'><h3 style='margin:2%;'>Orden</h3></td>");
                out.print("<td style='width: 20%' align='center'><h3 style='margin:2%;'>Parte</h3></td>");
                out.print("<td style='width: 10%' align='center'><h3 style='margin:2%;'>Fecha</h3></td>");
                out.print("<td style='width: 10%' align='center'><h3 style='margin:2%;'>Días</h3></td>");
        out.print("</tr>" );
        int i=0;
        String color;
        for (Sancion s: as){
            if ((i%2)==0){
                color=" #ccccff";
            }
            else{
                color=" #ffff99";
            }
            i++;

            out.print("<tr style='background-color:"+color+"'>");
                out.print("<td style='width: 20%' align='center'><h3 style='margin:2%;'>"+obtenerNombreCompleto(s.getA())+"</h3></td>");
                out.print("<td style='width: 20%' align='center'><h3 style='margin:2%;'>"+s.getTipo().getDescripcion()+"</h3></td>");
                out.print("<td style='width: 20%' align='center'><h3 style='margin:2%;'>"+obtenerNombreCompleto(s.getOrden())+"</h3></td>");
                out.print("<td style='width: 20%' align='center'><h3 style='margin:2%;'>"+s.getParte()+"</h3></td>");
                out.print("<td style='width: 10%' align='center'><h3 style='margin:2%;'>"+s.getFecha()+"</h3></td>");
                out.print("<td style='width: 10%' align='center'><h3 style='margin:2%;'>"+s.getDias()+"</h3></td>");
            out.print("</tr>");
        }
        out.print("</table>");
    }
    public Sancion getSancion(int id){
        try {
            String sql= "Select * from sanciones where id="+id;
            Statement s= connection.createStatement();
            ResultSet rs = s.executeQuery(sql);
            Sancion sancion;
            ManejadorCodigos mc = new ManejadorCodigos();
            while(rs.next()){
                sancion=new Sancion(rs.getInt("id"),mc.getTipoSancion(rs.getInt("idTipoSancion")),rs.getString("parte"),rs.getInt("dias"),rs.getDate("fecha"),rs.getTime("hora"),this.getPersonalBasico(rs.getInt("idorden")), this.getPersonalBasico(rs.getInt("idPersonal")));
                return sancion;
            }
            mc.CerrarConexionManejador();
        } catch (SQLException ex) {
            Logger.getLogger(ManejadorClases.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return null;
    }
    public ArrayList<Sancion> getSancionesDelTipo(int idPersonal, int idTipoSancion){
        ArrayList<Sancion> as= new ArrayList<>();
        try {
            String sql= "Select * from sanciones where idPersonal="+idPersonal + " and idTipoSancion = "+idTipoSancion + " order by fecha asc, hora asc";
            Statement s= connection.createStatement();
            ResultSet rs = s.executeQuery(sql);
            Sancion sancion;
            ManejadorCodigos mc = new ManejadorCodigos();
            while(rs.next()){
                sancion=new Sancion(rs.getInt("id"),mc.getTipoSancion(rs.getInt("idTipoSancion")),rs.getString("parte"),rs.getInt("dias"),rs.getDate("fecha"),rs.getTime("hora"),this.getPersonalBasico(rs.getInt("idorden")),this.getPersonalBasico(rs.getInt("idPersonal")));
                as.add(sancion);
            }
            mc.CerrarConexionManejador();
        } catch (SQLException ex) {
            Logger.getLogger(ManejadorClases.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return as;
    }
    public Date sumarRestarDiasFecha(Date fecha, int dias){
      Calendar calendar = Calendar.getInstance();
      java.util.Date fecha1 = new java.util.Date(fecha.getTime());
      calendar.setTime(fecha1); // Configuramos la fecha que se recibe
      calendar.add(Calendar.DAY_OF_YEAR, dias);  // numero de días a añadir, o restar en caso de días<0
      return new Date(calendar.getTime().getTime()); // Devuelve el objeto Date con los nuevos días añadidos	
    }
    public RecordRecalculo recalcularDias(int ciPersonal, int idTipoSancion){
        ArrayList<Sancion> sanciones = this.getSancionesDelTipo(ciPersonal,idTipoSancion);
        int dias=0;
        Sancion sancionInicial = null;
        for(Sancion s : sanciones){
            if(sancionInicial == null){
                dias= s.getDias();
                sancionInicial = s;
            }
            else{
                if(sumarRestarDiasFecha(sancionInicial.getFecha(), dias).getTime()-s.getFecha().getTime()<=0){
                    dias= s.getDias();
                    sancionInicial = s;
                }
                else{
                    dias+=s.getDias();
                    
                }
            }
        }
        RecordRecalculo rr= new RecordRecalculo();
        rr.dias = dias;
        rr.sancion=sancionInicial;
        //System.out.print(rr.dias + " " + rr.sancion.getFecha().toString());
        return rr;
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
                sql= "Select * from sancionados where ciPersonal="+ci + " and idTipoSancion="+tipoSancion;
                ResultSet rs = null;
                Statement s1= connection.createStatement();
                rs= s1.executeQuery(sql);
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                java.util.Date fecha1= sdf.parse(fecha);
                java.sql.Date fecha2= new Date(fecha1.getTime());
                if(rs.next()){ //si ya estaba sancionado
                    int totalDias= rs.getInt("cantTotalDias")+dias;
                    if(((rs.getDate("fechaInicial").getTime()-fecha2.getTime())>0)){
                        //System.out.println("Recalculando..");
                        RecordRecalculo rc = recalcularDias(ci, tipoSancion);
                        //System.out.println("Fin recalculo..");
                        sql= "update sancionados set fechaInicial='"+rc.sancion.getFecha().toString()+"', cantTotalDias="+ rc.dias +" where ciPersonal="+ci+" and idTipoSancion="+tipoSancion;
                        s1.executeUpdate(sql);
                    }
                    else{
                        sql= "update sancionados set cantTotalDias="+totalDias+" where ciPersonal="+ci+" and idTipoSancion="+tipoSancion;
                        s1.executeUpdate(sql);
                    }
                }
                else{ //si no estaba sancionado
                    RecordRecalculo rc = recalcularDias(ci, tipoSancion);
                    sql= "insert into sancionados (ciPersonal,idTipoSancion,fechaInicial,cantTotalDias,horaInicial) values("+ci+","+tipoSancion+",'"+rc.sancion.getFecha()+"',"+rc.dias+",'"+hora+"')";
                    s1.executeUpdate(sql);
                }
                return true;
            }
        } catch (Exception ex) {
            Logger.getLogger(ManejadorClases.class.getName()).log(Level.SEVERE, null, ex);
        }

        return false;
    }
    public boolean eliminarSancion(int id){
        try {
            Sancion s = this.getSancion(id);
            String sql="delete from sanciones where id="+id; //si esta sancionado, me fijo la fecha y hora de la sancion eliminada, si es mayor resto dias, si igual me guardo la fecha y hora, la elimino y despues busco la sancion de fecha >= y resto dias, si menor la elimino solo
            Statement s1= connection.createStatement();
            int i= s1.executeUpdate(sql);
            
            
            sql= "Select * from sancionados where ciPersonal="+s.getA().getCi() + " and idTipoSancion="+s.getTipo().getId();
            ResultSet rs = null;
            
            rs= s1.executeQuery(sql);
            if(rs.next()){ // esta sacionado
                int cantTotalDias= rs.getInt("cantTotalDias");
                if(((rs.getDate("fechaInicial").getTime()-s.getFecha().getTime())<0)){ //fecha de sancionado menor a la que elimino
                 //   System.out.println("Recalculando..");
                    RecordRecalculo rc = recalcularDias(s.getA().getCi(), s.getTipo().getId());
                 //   System.out.println("Fin recalculo..");
                    sql= "update sancionados set fechaInicial='"+rc.sancion.getFecha().toString()+"', cantTotalDias="+ rc.dias +" where ciPersonal="+s.getA().getCi()+" and idTipoSancion="+s.getTipo().getId();
                    s1.executeUpdate(sql);
                }
                else{
                    if((rs.getDate("fechaInicial").getTime()-s.getFecha().getTime())==0){
                        sql= "Select * from sanciones where idPersonal="+s.getA().getCi() + " and fecha >='"+s.getFecha().toString()+"' and hora >='"+s.getHora().toString()+"' order by fecha asc, hora asc";
                        rs = s1.executeQuery(sql);
                        if(rs.next()){
                            sql= "update sancionados set fechaInicial='"+rs.getDate("fecha").toString()+"', horaInicial='"+rs.getTime("hora").toString()+"', cantTotalDias="+(cantTotalDias-s.getDias())+" where ciPersonal="+s.getA().getCi()+" and idTipoSancion="+s.getTipo().getId();
                            s1.executeUpdate(sql);
                        }   
                        else{
                            sql= "delete from sancionados where ciPersonal="+s.getA().getCi()+" and idTipoSancion="+s.getTipo().getId();
                            s1.executeUpdate(sql);
                        }
                    }
                }
                
            }
            return (i>0);
        } catch (SQLException ex) {
            Logger.getLogger(ManejadorCodigos.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }
    
    //historial clinico
    public ConsultaMedica getConsultaMedica(int id){
        try {
            String sql= "Select * from historiaclinica where id="+id;
            Statement s= connection.createStatement();
            ResultSet rs = s.executeQuery(sql);
            ConsultaMedica h;
            while(rs.next()){
                h=new ConsultaMedica(rs.getInt("id"),this.getPersonalBasico(rs.getInt("idPersonal")),rs.getDate("fechaInicio"),rs.getDate("fechaFin"),rs.getString("diagnostico"),this.getPersonalBasico(rs.getInt("idMedico")),rs.getString("tratamiento"));
                return h;
            }
        } catch (SQLException ex) {
            Logger.getLogger(ManejadorClases.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
    public HashMap<Integer,ArrayList<ConsultaMedica>> getHistoriaClinica(int idPersonal){
        HashMap<Integer,ArrayList<ConsultaMedica>> hm = new HashMap<>();
        ArrayList<ConsultaMedica> ah1= new ArrayList<>();
        ArrayList<ConsultaMedica> ah2= new ArrayList<>();
        try {
            String id="";
            if(idPersonal!=-1){
                id= "where idPersonal="+idPersonal;
            }
            String sql= "Select * from historiaclinica "+id+" order by fechaInicio desc";
            Statement s= connection.createStatement();
            ResultSet rs = s.executeQuery(sql);
            ConsultaMedica h;
            while(rs.next()){
                h=new ConsultaMedica(rs.getInt("id"),this.getPersonalBasico(rs.getInt("idPersonal")),rs.getDate("fechaInicio"),rs.getDate("fechaFin"),rs.getString("diagnostico"),this.getPersonalBasico(rs.getInt("idMedico")),rs.getString("tratamiento"));
                if(diferenciasDiasDesdeHoy2(rs.getDate("fechaInicio"), null)>=0 && diferenciasDiasDesdeHoy2(rs.getDate("fechaFin"), null)<=0){
                    ah1.add(h);
                }
                else{
                    ah2.add(h);
                }
                
            }
        } catch (SQLException ex) {
            Logger.getLogger(ManejadorClases.class.getName()).log(Level.SEVERE, null, ex);
        }
        hm.put(1, ah1);
        hm.put(2, ah2);
        return hm;
    }
    public HashMap<Integer,ArrayList<ConsultaMedica>> getHistoriaClinicaListar(int idPersonal,String fechaDesde, String fechaHasta){
        HashMap<Integer,ArrayList<ConsultaMedica>> hm= new HashMap<>();
        ArrayList<ConsultaMedica> ah1= new ArrayList<>();
        ArrayList<ConsultaMedica> ah2= new ArrayList<>();
        try {
            String id="";
            if(idPersonal!=-1){
                id="idPersonal="+idPersonal+" and ";
            }
            String fecha="";
            if(!fechaDesde.equals("")){
                fecha="fechaInicio>= '"+fechaDesde + "' and ";
            }
            String sql="Select * from historiaClinica where "+id+fecha+" fechaInicio<= '"+fechaHasta + "'  order by fechaInicio desc";     
            Statement s= connection.createStatement();
            ResultSet rs = s.executeQuery(sql);
            ConsultaMedica h;
            while(rs.next()){
                h=new ConsultaMedica(rs.getInt("id"),this.getPersonalBasico(rs.getInt("idPersonal")),rs.getDate("fechaInicio"),rs.getDate("fechaFin"),rs.getString("diagnostico"),this.getPersonalBasico(rs.getInt("idMedico")),rs.getString("tratamiento"));
                if(diferenciasDiasDesdeHoy2(rs.getDate("fechaInicio"), null)>=0 && diferenciasDiasDesdeHoy2(rs.getDate("fechaFin"), null)<=0){
                    ah1.add(h);
                }
                else{
                    ah2.add(h);
                }
                
            }
        } catch (SQLException ex) {
            Logger.getLogger(ManejadorClases.class.getName()).log(Level.SEVERE, null, ex);
        }
        hm.put(1, ah1);
        hm.put(2, ah2);
        return hm;
    }
    public boolean agregarConsultaMedica(int idPersonal, String fechaInicio, String fechaFin, String diagnostico, int idMedico, String tratamiento){
        try {
            String sql= "insert into historiaClinica (idPersonal, fechaInicio, fechaFin, diagnostico, tratamiento, idMedico) values(?,?,?,?,?,?)";
            PreparedStatement s= connection.prepareStatement(sql);
            s.setInt(1,idPersonal);
            s.setString(2, fechaInicio);
            s.setString(3, fechaFin);
            s.setString(4, diagnostico);
            s.setString(5, tratamiento);
            s.setInt(6,idMedico);
            int result = s.executeUpdate();
            if(result>0){
                return true;
            }
        } catch (Exception ex) {
            Logger.getLogger(ManejadorClases.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }
    public boolean eliminarConsultaMedica(int id){
        try {
            Statement s= connection.createStatement();
            String sql="delete from historiaClinica where id="+id;
            int i= s.executeUpdate(sql);
            return (i>0);
        } catch (SQLException ex) {
            Logger.getLogger(ManejadorCodigos.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }
    public boolean modificarConsultaMedica(int id, int idPersonal, String fechaInicio, String fechaFin, String diagnostico, int idMedico, String tratamiento){
        try {
            String sql= "update historiaClinica set idPersonal=?, fechaInicio=?, fechaFin=?, diagnostico=?, tratamiento=?, idMedico=? where id="+id;
            PreparedStatement s= connection.prepareStatement(sql);
            s.setInt(1,idPersonal);
            s.setString(2, fechaInicio);
            s.setString(3, fechaFin);
            s.setString(4, diagnostico);
            s.setString(5, tratamiento);
            s.setInt(6,idMedico);
            int result = s.executeUpdate();
            if(result>0){
                return true;
            }
        } catch (Exception ex) {
            Logger.getLogger(ManejadorClases.class.getName()).log(Level.SEVERE, null, ex);
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
                documento=new Documento(rs.getInt("id"),p,mc.getTipoDocumento(rs.getInt("idTipoDocumento")),rs.getString("nombre"));
                as.add(documento);
            }
            mc.CerrarConexionManejador();
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
                p= new Documento(id, this.getPersonalBasico(rs.getInt("idPersonal")), mc.getTipoDocumento(rs.getInt("idTipoDocumento")), rs.getString("nombre"));
            }
            mc.CerrarConexionManejador();
        } catch (SQLException ex) {
            Logger.getLogger(ManejadorClases.class.getName()).log(Level.SEVERE, null, ex);
        }
        return p;
    }
    public boolean modificarDocumento(int id, int tipoDocumento, String nombre){
        try {
            String sql= "update documentos set idTipoDocumento=?, nombre=? where id="+id;
            PreparedStatement s= connection.prepareStatement(sql);
            s.setInt(1,tipoDocumento);
            s.setString(2,nombre);
            int result = s.executeUpdate();
            if(result>0){
                return true;
            }
        } catch (Exception ex) {
            Logger.getLogger(ManejadorClases.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }
    public int crearDocumento(int tipoDocumento, int idPersonal, String nombre){
        int clave = -1;
        try {
            String sql= "insert into documentos (idPersonal, idTipoDocumento, nombre) values("+idPersonal+","+tipoDocumento+",'"+nombre+"')";
            Statement s= connection.createStatement();
            int result = s.executeUpdate(sql,Statement.RETURN_GENERATED_KEYS);
            if(result>0){
                ResultSet rs=s.getGeneratedKeys(); //obtengo las ultimas llaves generadas
                while(rs.next()){ 
                    clave=rs.getInt(1);
                }
            }
        } catch (Exception ex) {
            Logger.getLogger(ManejadorClases.class.getName()).log(Level.SEVERE, null, ex);
        }
        return clave;
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
            mc.CerrarConexionManejador();
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
    public boolean crearApoderado(int ciPersonal, int ci,String nombres, String apellidos,int idvinculo,String domicilio,String celular,String telefono){
        try {
            Statement s= connection.createStatement();
            String sql="Select * from apoderados where ci="+ci;
            ResultSet rs= s.executeQuery(sql);
            int i;
            if (!rs.next()){
                sql="insert into apoderados (ci, nombres, apellidos, domicilio, celular,telefono) values("+ci+",'"+nombres+"','"+apellidos+"','"+domicilio+"','"+celular+"','"+telefono+"')";
                s.addBatch(sql);
                
            }
            else{
                sql="update apoderados set nombres='"+nombres+"',apellidos='"+apellidos+"',domicilio='"+domicilio+"',celular='"+celular+"',telefono='"+telefono+"' where ci="+ci;
                s.addBatch(sql);
            }
            sql="update personal set idApoderado="+ci+", idVinculo="+idvinculo+" where ci="+ciPersonal;
            s.addBatch(sql);
            s.executeBatch();
            return true;
        } catch (SQLException ex) {
            Logger.getLogger(ManejadorCodigos.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
    }
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
                TipoFamiliar tf = mc.getTipoFamiliar(rs.getInt("idVinculo"));
                mc.CerrarConexionManejador();
                return new Apoderado(rs.getInt("ci"), rs.getString("nombres"), rs.getString("apellidos"), tf, rs.getString("domicilio"), rs.getString("celular"), rs.getString("telefono"));
            }
        } catch (SQLException ex) {
            Logger.getLogger(ManejadorCodigos.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
    public Apoderado getApoderado2(int ciApoderado){
        try {
            Statement s= connection.createStatement();
            String sql="Select * from apoderados where ci="+ciApoderado;
            ResultSet rs= s.executeQuery(sql);
            if (rs.next()){
                return new Apoderado(rs.getInt("ci"), rs.getString("nombres"), rs.getString("apellidos"), null, rs.getString("domicilio"), rs.getString("celular"), rs.getString("telefono"));
            }
        } catch (SQLException ex) {
            Logger.getLogger(ManejadorCodigos.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
    public boolean modificarApoderado(int idPersonal, Apoderado apod){
        try {
            Statement s= connection.createStatement();
            String sql="Update apoderados left join personal on apoderados.ci=personal.ci set idVinculo="+ apod.getVinculo().getId() +", apoderados.nombres='"+ apod.getNombre() +"', apoderados.apellidos='"+ apod.getApellido() +"', apoderados.celular='"+ apod.getCelular() +"', apoderados.domicilio='"+ apod.getDomicilio() +"', apoderados.telefono='"+ apod.getTelefono() +"' where apoderados.ci="+apod.getCi() +" and Personal.ci="+idPersonal;
            int rs= s.executeUpdate(sql);
            return (rs>0);
        } catch (SQLException ex) {
            Logger.getLogger(ManejadorCodigos.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }
    public boolean desvincularApoderado(int idPersonal, int idApod){
        try {
            Statement s= connection.createStatement();
            String sql="update personal set idApoderado=-1, idVinculo=-1 where  ci="+idPersonal;
            s.addBatch(sql);
            sql="select * from personal where ci<>"+idPersonal+" and idApoderado="+idApod;
            ResultSet rs= s.executeQuery(sql);
            if(!rs.next()){
                sql="delete from apoderados where ci="+idApod;
                s.addBatch(sql);
            }
            s.executeBatch();
            return true;
        } catch (SQLException ex) {
            Logger.getLogger(ManejadorCodigos.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
    }
    
    //FAMILIARES
    public ArrayList<Familiar> getFamiliares(int ciPersonal){
        ArrayList<Familiar> as= new ArrayList<>();
        try {
            String sql= "Select * from familiares left join `personal-familiar` on familiares.ci=idFamiliar where idPersonal="+ciPersonal;
            Statement s= connection.createStatement();
            ResultSet rs = s.executeQuery(sql);
            Familiar f;
            ManejadorCodigos mc = new ManejadorCodigos();
            while(rs.next()){
                TipoFamiliar tf = mc.getTipoFamiliar(rs.getInt("idVinculo"));
                mc.CerrarConexionManejador();
                f=new Familiar(rs.getInt("ci"),tf,rs.getString("nombres"),rs.getInt("edad"),rs.getString("apellidos"),rs.getString("domicilio"),rs.getString("ocupacion"),rs.getString("telefono"),rs.getString("celular"),rs.getBoolean("discapacidad"),rs.getString("descDiscapacidad"));
                as.add(f);
            }
            mc.CerrarConexionManejador();
        } catch (SQLException ex) {
            Logger.getLogger(ManejadorClases.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return as;
    }
    public Familiar getFamiliar(int ciPersonal, int ciFamiliar){
        Familiar f=null;
        try {
            String sql= "Select * from familiares left join `personal-familiar` on familiares.ci=idFamiliar where idPersonal="+ciPersonal+" and ci="+ciFamiliar;
            Statement s= connection.createStatement();
            ResultSet rs = s.executeQuery(sql);
            ManejadorCodigos mc = new ManejadorCodigos();
            if(rs.next()){
                TipoFamiliar tf = mc.getTipoFamiliar(rs.getInt("idVinculo"));
                mc.CerrarConexionManejador();
                f=new Familiar(rs.getInt("ci"),tf,rs.getString("nombres"),rs.getInt("edad"),rs.getString("apellidos"),rs.getString("domicilio"),rs.getString("ocupacion"),rs.getString("telefono"),rs.getString("celular"),rs.getBoolean("discapacidad"),rs.getString("descDiscapacidad"));
            }
            mc.CerrarConexionManejador();
        } catch (SQLException ex) {
            Logger.getLogger(ManejadorClases.class.getName()).log(Level.SEVERE, null, ex);
        }
        return f;
    }
    public boolean modificarFamiliar(int ciPersonal, Familiar familiar){
        try {
            Statement s= connection.createStatement();
            int disc=0;
            if(familiar.getDiscapacidad()){
                disc=1;
            }
            String sql="Update  familiares left join `personal-familiar` on idFamiliar=ci set idVinculo="+ familiar.getTipo().getId() +",discapacidad="+ disc +", nombres='"+ familiar.getNombre() +"', ocupacion='"+ familiar.getOcupacion() +"', edad="+ familiar.getEdad() +",apellidos='"+ familiar.getApellido() +"', celular='"+ familiar.getCelular() +"', domicilio='"+ familiar.getDomicilio() +"', telefono='"+ familiar.getTelefono() +"', descDiscapacidad='"+ familiar.getDescDiscapacidad() +"' where ci="+familiar.getCi() +" and idPersonal="+ciPersonal;
            int rs= s.executeUpdate(sql);
            return (rs>0);
        } catch (SQLException ex) {
            Logger.getLogger(ManejadorCodigos.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }
    public boolean desvincularFamiliar(int ciPersonal, int ciFamiliar){
        try {
            Statement s= connection.createStatement();
            String sql="delete from `personal-familiar` where idPersonal="+ciPersonal+" and idFamiliar="+ciFamiliar;
            s.addBatch(sql);
            sql="select * from `personal-familiar` where idFamiliar="+ciFamiliar+"and idPersonal<>"+ciPersonal;
            ResultSet rs= s.executeQuery(sql);
            if(!rs.next()){
                sql="delete from familiares where ci="+ciFamiliar;
                s.addBatch(sql);
            }
            s.executeBatch();
            return true;
        } catch (SQLException ex) {
            Logger.getLogger(ManejadorCodigos.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
    }
    public Familiar getFamiliar2(int ciFamiliar){
        Familiar f=null;
        try {
            String sql= "Select * from familiares where ci="+ciFamiliar;
            Statement s= connection.createStatement();
            ResultSet rs = s.executeQuery(sql);
            if(rs.next()){
                f=new Familiar(rs.getInt("ci"),null,rs.getString("nombres"),rs.getInt("edad"),rs.getString("apellidos"),rs.getString("domicilio"),rs.getString("ocupacion"),rs.getString("telefono"),rs.getString("celular"),rs.getBoolean("discapacidad"),rs.getString("descDiscapacidad"));
            }
        } catch (SQLException ex) {
            Logger.getLogger(ManejadorClases.class.getName()).log(Level.SEVERE, null, ex);
        }
        return f;
    }
    public boolean crearFamiliar(int ciPersonal, int ciFamiliar,String nombres, String apellidos,int idvinculo,String domicilio,String celular,String telefono,boolean discapacidad, int edad, String descDiscapacidad, String ocupacion){
        int i =0;
        try {
            Statement s= connection.createStatement();
            String sql="Select * from familiares where ci="+ciFamiliar;
            ResultSet rs= s.executeQuery(sql);
            int intDiscapacidad=0;
            if(discapacidad){
                intDiscapacidad=1;
            }
            if (!rs.next()){
                sql="insert into familiares (ci, nombres, apellidos, domicilio, celular,telefono, ocupacion, descDiscapacidad, edad, discapacidad) values("+ciFamiliar+",'"+nombres+"','"+apellidos+"','"+domicilio+"','"+celular+"','"+telefono+"','"+ocupacion+"','"+descDiscapacidad+"',"+edad+",'"+intDiscapacidad+"')";
                s.addBatch(sql);
                
            }
            else{
                sql="update familiares set nombres='"+nombres+"',apellidos='"+apellidos+"',domicilio='"+domicilio+"',celular='"+celular+"',telefono='"+telefono+"',edad="+edad+",ocupacion='"+ocupacion+"',descDiscapacidad='"+descDiscapacidad+"',discapacidad='"+intDiscapacidad+"' where ci="+ciFamiliar;
                s.addBatch(sql);
            }
            sql="insert into `personal-familiar` (idPersonal, idFamiliar, idVinculo) values ("+ciPersonal+","+ciFamiliar+","+idvinculo+")";
            s.addBatch(sql);
            s.executeBatch();
            return true;
        }
        catch(SQLException ex){
            return false;
        }
    }

    //IMPRESIONES
    public void imprimirPersonal(int ci, boolean basico, boolean familiar, boolean apoderado, boolean especialidades, PrintWriter out ){
        try{
            Personal p= this.getPersonalBasico(ci);
            String foto="images/silueta.jpg";
            if(p.getFoto()!=null){
                Blob b= p.getFoto();
                if (b!=null && (int)b.length()!= 0){
                    byte[] by=b.getBytes(1,(int)b.length());
                    String imgDataBase64=new String(Base64.getEncoder().encode(by));
                    foto = "data:image/jpg;base64,"+imgDataBase64;

                }
            }
            out.print("<h1 align='center'>FICHA PERSONAL</h1>");
            out.print("<p align=\"center\"><img src="+foto+" style=\"width: 25%\" /></p>\n");
            
        
            if(basico){
                imprimirDatosBasicos(p, out);
            }
            if(familiar){
                imprimirFamiliares(ci, out);
            }
            if(apoderado){
                imprimirApoderado(ci, out);
            }
            if(especialidades){
                imprimirEspecialidades(ci, out);
            }
        }
        catch(Exception e){
            
        }
    }
    public void imprimirDatosBasicos(Personal p, PrintWriter out) throws SQLException{
        String idONU = "";
        if(p.getIdONU()!= -1){
            idONU=String.valueOf(p.getIdONU());
        }
            String datos =    "<h3 align='center'>DATOS BASICOS</h3>"+
"               <table  width='50%' align='center' >\n" +
"                    <tr>\n" +
"                        <td>\n" +
"                            <b>ID:</b>\n" +
"                        </td>\n" +
"                        <td>\n" +
"                            "+idONU+"\n" +
"                        </td>\n" +
"                    </tr>\n" +                    
"                    <tr>\n" +
"                        <td>\n" +
"                            <b>C.I.:</b>\n" +
"                        </td>\n" +
"                        <td>\n" +
"                            "+ p.getCi()+"\n" +
"                        </td>\n" +
"                    </tr>\n" +
"                    <tr>\n" +
"                        <td>\n" +
"                            <b>Grado:</b>\n" +
"                        </td>\n" +
"                        <td>\n" +
"                            "+ p.getGrado().getDescripcion()+"\n" +
"                        </td>\n" +
"                    </tr>\n" +
"                    <tr>\n" +
"                        <td>\n" +
"                           <b>Nombre:</b>\n" +
"                        </td>\n" +
"                        <td>\n" +
"                            "+ p.getNombre()+"\n" +
"                        </td>\n" +
"                    </tr>\n" +
"                    <tr>\n" +
"                        <td>\n" +
"                            <b>Apellido:</b>\n" +
"                        </td>\n" +
"                        <td>\n" +
"                            "+ p.getApellido()+"\n" +
"                        </td>\n" +
"                    </tr>\n" +
"                    <tr>\n" +
"                        <td>\n" +
"                            <b>Unidad:</b>\n" +
"                        </td>\n" +
"                        <td>\n" +
"                            "+ p.getUnidadPerteneciente().getNombre()+"\n" +
"                        </td>\n" +
"                    </tr>\n" +
"        <tr>\n" +
"            <td>\n" +
"                <b>Fecha de Nacimiento:</b>\n" +
"            </td>\n" +
"            <td>\n" ;
                    if(p.getFechaNac()!=null){
                        datos+=p.getFechaNac();
                     };
                     datos+="</td>\n" +
"            </td>\n" +
"        </tr>\n" +
"        <tr>\n" +
"            <td>\n" +
"                <b>Vencimiento C.I.:</b>\n" +
"            </td>\n" +
"            <td>\n" ;
                    if(p.getVtoCI()!=null){
                        datos+=p.getVtoCI();
                     };
                     datos+="</td>\n" +
"            </td>\n" +
"        </tr>\n" +
"        <tr>\n" +
"            <td>\n" +
"                <b>Pasaporte:</b>\n" +
"            </td>\n" +
"            <td>\n" +
"                "+ p.getPasaporte()+"\n" +
"            </td>\n" +
"        </tr>\n" +
"        <tr>\n" +
"            <td>\n" +
"                <b>Vencimiento pasaporte:</b>\n" +
"            </td>\n" +
"            <td>\n" ;
                    if(p.getVtoPas()!=null){
                        datos+=p.getVtoPas();
                     };
                     datos+="</td>\n" +
"            </td>\n" +
"        </tr>\n" +
"        <tr>\n" +
"            <td>\n" +
"                <b>C.C.:</b>\n" +
"            </td>\n" +
"            <td>\n" +
"                "+ p.getCc()+"\n" +
"            </td>\n" +
"        </tr>\n" +
"        <tr>\n" +
"            <td>\n" +
"                <b>C.C. Nro.:</b>\n" +
"            </td>\n" +
"            <td>\n" +
"                "+ p.getCcNro()+"\n" +
"            </td>\n" +
"        </tr>\n" +
"        <tr>\n" ;
        if(p.getLicenciaConducir()){
            datos+="<td>\n" +
"                <b>Licencia de Conducir</b>\n" +
"            </td>\n" +
"            <td>\n" +
"                "+ p.getCatLicCond()+" "+p.getNroLicCond()+"\n" +
"            </td>\n" +
"        </tr>\n" +
"        <tr>\n" +
"            <td>\n" +
"                <b>Vencimiento Licencia:</b>\n" +
"            </td>\n" +
"            <td>\n" ;
                    if(p.getVtoLicCond()!=null){
                        datos+=p.getLicenciaConducir();
                     };
                     datos+="</td>\n" +
"        </tr>\n" ;
        }
        if(p.getCarneSalud()){
            datos+="<tr>\n" +
"            <td>\n" +
"                <b>Vencimiento carn&eacute;:</b>\n" +
"            </td>\n" +
"            <td>\n" ;
                    if(p.getVtoCarneSalud()!=null){
                        datos+=p.getVtoCarneSalud();
                     };
                     datos+="</td>\n" +
"            </td>\n" +
"        </tr>\n" ;          
        }

        if(p.getExpMision()){
            datos+="<tr>\n" +
"        <tr>\n" +
"            <td>\n" +
"                <b>Lugar experiencia misi&oacuote;n:</b>\n" +
"            </td>\n" +
"            <td>\n" +
"                 "+ p.getLugarExpMision()+"\n" +
"            </td>\n" +
"        </tr>\n";                    
        }

        datos+="</table> <h1 style='page-break-after:always' > </h1>";

        out.print(datos);
    }
    public void imprimirFamiliares(int ci, PrintWriter out){
        ArrayList<Familiar> af= this.getFamiliares(ci);
        String datos =    "<h3 align='center'>FAMILIARES</h3>";
        if(af.size()==0){
            datos +=    "<h4 align='center'>No hay datos ingresados en el sistema.</h4>";
        }
        for(Familiar f: af){
            datos+="<table  width='50%' align='center' >\n" +
"                    <tr>\n" +
"                        <td>\n" +
"                            <b>Vínculo:</b>\n" +
"                        </td>\n" +
"                        <td>\n" +
"                            "+ f.getTipo().getDescripcion()+"\n" +
"                        </td>\n" +
"                    </tr>\n" +
"                    <tr>\n" +
"                        <td>\n" +
"                            <b>C.I.:</b>\n" +
"                        </td>\n" +
"                        <td>\n" +
"                            "+ f.getCi()+"\n" +
"                        </td>\n" +
"                    </tr>\n" +
"                    <tr>\n" +
"                        <td>\n" +
"                           <b>Nombre:</b>\n" +
"                        </td>\n" +
"                        <td>\n" +
"                            "+ f.getNombre()+"\n" +
"                        </td>\n" +
"                    </tr>\n" +
"                    <tr>\n" +
"                        <td>\n" +
"                            <b>Apellido:</b>\n" +
"                        </td>\n" +
"                        <td>\n" +
"                            "+ f.getApellido()+"\n" +
"                        </td>\n" +
"                    </tr>\n" +

"                    <tr>\n" +
"                        <td>\n" +
"                            <b>Edad:</b>\n" +
"                        </td>\n" +
"                        <td>\n" +
"                            "+ f.getEdad()+"\n" +
"                        </td>\n" +
"                    </tr>\n" +    
"                    <tr>\n" +
"                        <td>\n" +
"                            <b>Domicilio:</b>\n" +
"                        </td>\n" +
"                        <td>\n" +
"                            "+ f.getDomicilio()+"\n" +
"                        </td>\n" +
"                    </tr>\n" +        

"                    <tr>\n" +
"                        <td>\n" +
"                            <b>Ocupacion:</b>\n" +
"                        </td>\n" +
"                        <td>\n" +
"                            "+ f.getOcupacion()+"\n" +
"                        </td>\n" +
"                    </tr>\n" +
"                    <tr>\n" +
"                        <td>\n" +
"                            <b>Teléfono:</b>\n" +
"                        </td>\n" +
"                        <td>\n" +
"                            "+ f.getTelefono()+"\n" +
"                        </td>\n" +
"                    </tr>\n" +

"                    <tr>\n" +
"                        <td>\n" +
"                            <b>Celular:</b>\n" +
"                        </td>\n" +
"                        <td>\n" +
"                            "+ f.getCelular()+"\n" +
"                        </td>\n" +
"                    </tr>\n" ;
            if(f.getDiscapacidad()){
                datos+="<tr><td>\n" +
"                <b>Discapacidad:</b>\n" +
"            </td>\n" +
"            <td>\n" +
"                "+ f.getDescDiscapacidad()+"\n" +
"            </td>\n" +
"        </tr>\n" ;
                datos+="</table> <h1 style='page-break-after:always' > </h1>";
            }
        }
        if(af.size()==0){
            datos+="</table> <h1 style='page-break-after:always' > </h1>";
        }
        out.print(datos);
    }
    public void imprimirApoderado(int ciPersonal, PrintWriter out){
        Apoderado a= this.getApoderado(ciPersonal);
        
        if(a!=null){
        String datos =    "<h3 align='center'>APODERADO</h3>"+
        "<table  width='50%' align='center' >\n" +
"        <tr>\n" +
"            <td>\n" +
"                <p><b>V&iacute;nculo:</b></p>\n" +
"            </td>\n" +
"            <td>\n" + a.getVinculo().getDescripcion()+
"            </td>\n" +
"        </tr>\n" +
"        <tr>\n" +
"            <td>\n" +
"                <p><b>C.I.:</b></p>\n" +
"            </td>\n" +
"            <td>\n" +
                a.getCi()+
"            </td>\n" +
"        </tr>\n" +
"        <tr>\n" +
"            <td>\n" +
"                <p><b>Nombres:</b></p>\n" +
"            </td>\n" +
"            <td>\n" +
                a.getNombre()+
"            </td>\n" +
"        </tr>\n" +
"        <tr>\n" +
"            <td>\n" +
"                <p><b>Apellidos:</b></p>\n" +
"            </td>\n" +
"            <td>\n" +
                a.getApellido()+
"            </td>\n" +
"        </tr>\n" +
"        \n" +
"        <tr>\n" +
"            <td>\n" +
"                <p><b>Domicilio:</b></p>\n" +
"            </td>\n" +
"            <td>\n" +
                a.getDomicilio()+
"            </td>\n" +
"        </tr>\n" +
"        <tr>\n" +
"            <td>\n" +
"                <p><b>Tel&eacute;fono:</b></p>\n" +
"            </td>\n" +
"            <td>\n" +
                a.getTelefono()+
"            </td>\n" +
"        </tr>\n" +
"        <tr>\n" +
"            <td>\n" +
"                <p><b>Celular:</b></p>\n" +
"            </td>\n" +
"            <td>\n" +
                a.getCelular()+
"            </td>\n" +
"        </tr>\n" +
"    </table>";
        out.print(datos);
        }
    }
    public void imprimirEspecialidades(int ciPersonal, PrintWriter out){
        ArrayList<Especialidad> ae= this.getEspecialidadesListar(ciPersonal);
        String datos =    "<h3 align='center'>ESPECIALIDADES</h3>";
        if(ae.size()==0){
            datos +=    "<h4 align='center'>No hay datos ingresados en el sistema.</h4>";
        }
        datos+="<table  width='50%' align='center' >\n" ;

        for(Especialidad e : ae){
datos+="                        <tr>\n" +
"                            <td>\n" +
                e.getDescripcion()+
"                            </td>\n" +
"                        </tr>\n";
        }
        datos+= "</table>  \n" ;
        out.print(datos);
    }
    public void imprimirTodoElPersonal(RecordListarPersonal rl,PrintWriter out){
        ArrayList<Personal> ap = this.getListaPersonalBasico();
        String datos="<h3 align='center'>PERSONAL DEL SISTEMA</h3>";
        datos+="<table style=\"width: 100%;\">"+
        "<tr style='background-color:#ffcc66'>";
        if(rl.idONU){
            datos+="<td align='center'><h3 style='margin:2%;'>ID</h3></td>";
        }
        if(rl.nombreCompleto){
            datos+="<td align='center'><h3 style='margin:2%;'>Nombre Completo</h3></td>";
        }
        if(rl.ci){
            datos+="<td align='center'><h3 style='margin:2%;'>CI</h3></td>";
        }
        if(rl.ciVto){
            datos+="<td align='center'><h3 style='margin:2%;'>Vto. CI</h3></td>";
        }
        if(rl.fechaNac){
            datos+="<td align='center'><h3 style='margin:2%;'>Fecha Nac.</h3></td>";
        }
        if(rl.pasaporte){
            datos+="<td align='center'><h3 style='margin:2%;'>Pasaporte</h3></td>";
        }
        if(rl.vtoPas){
            datos+="<td align='center'><h3 style='margin:2%;'>Vto. Pas</h3></td>";
        }
        if(rl.credencial){
            datos+="<td align='center'><h3 style='margin:2%;'>Credencial</h3></td>";
        }
        if(rl.liciencia){
            datos+="<td align='center'><h3 style='margin:2%;'>Lic. Conducir</h3></td>";
        }
        if(rl.vtoLic){
            datos+="<td align='center'><h3 style='margin:2%;'>Vto. Licencia</h3></td>";
        }
        if(rl.carneSalud){
            datos+="<td align='center'><h3 style='margin:2%;'>Carné de Salud</h3></td>";
        }
        if(rl.vtoCarne){
            datos+="<td align='center'><h3 style='margin:2%;'>Vto. Carné</h3></td>";
        }
        if(rl.misiones){
            datos+="<td align='center'><h3 style='margin:2%;'>Misiones Ant.</h3></td>";
        }
        datos+="</tr>";
        int i=0;
        String color;
        for (Personal p: ap){
            if ((i%2)==0){
                color=" #ccccff";
            }
            else{
                color=" #ffff99";
            }
            i++;

            datos+="<tr style='background-color:"+color+"'>";
            if(rl.idONU){
                datos+="<td align='center'><h3 style='margin:2%;'>"+p.getIdONU()+"</h3></td>";
            }
            if(rl.nombreCompleto){
                datos+="<td align='center'><h3 style='margin:2%;'>"+this.obtenerNombreCompleto(p)+"</h3></td>";
            }
            if(rl.ci){
                datos+="<td align='center'><h3 style='margin:2%;'>"+p.getCi()+"</h3></td>";
            }
            if(rl.ciVto){
                datos+="<td align='center'><h3 style='margin:2%;'>";
                if(p.getVtoCI()!=null){
                    datos+=p.getVtoCI();
                }
                datos+="</h3></td>";
            }
            if(rl.fechaNac){
                datos+="<td align='center'><h3 style='margin:2%;'>";
                if(p.getFechaNac()!=null){
                    datos+=p.getFechaNac();
                }
                datos+="</h3></td>";
            }
            if(rl.pasaporte){
                datos+="<td align='center'><h3 style='margin:2%;'>"+p.getPasaporte()+"</h3></td>";
            }
            if(rl.vtoPas){
                datos+="<td align='center'><h3 style='margin:2%;'>";
                if(p.getVtoPas()!=null){
                    datos+=p.getVtoPas();
                }
                datos+="</h3></td>";
            }
            if(rl.credencial){
                datos+="<td align='center'><h3 style='margin:2%;'>"+p.getCc()+" "+p.getCcNro()+"</h3></td>";
            }
            if(rl.liciencia){
                datos+="<td align='center'><h3 style='margin:2%;'>"+p.getNroLicCond()+"</h3></td>";
            }
            if(rl.vtoLic){
                datos+="<td align='center'><h3 style='margin:2%;'>";
                if(p.getVtoLicCond()!=null){
                    datos+=p.getVtoLicCond();
                }
                datos+="</h3></td>";
            }
            if(rl.carneSalud){
                datos+="<td align='center'><h3 style='margin:2%;'>";
                if(p.getCarneSalud()){
                    datos+="S";
                }
                else{
                    datos+="N";
                }
                datos+="</h3></td>";
            }
            if(rl.vtoCarne){
                datos+="<td align='center'><h3 style='margin:2%;'>";
                if(p.getVtoCarneSalud()!=null){
                    datos+=p.getVtoCarneSalud();
                }
                datos+="</h3></td>";
            }
            if(rl.misiones){
                datos+="<td align='center'><h3 style='margin:2%;'>"+p.getLugarExpMision()+"</h3></td>";
            }
            datos+="</tr>";
        }
        datos+="</table>";
        out.print(datos);
    }
    public void imprimirHistoriaClinica(int idPersonal,String fechaDesde, String fechaHasta,PrintWriter out){
        HashMap<Integer,ArrayList<ConsultaMedica>> hm= this.getHistoriaClinicaListar(idPersonal, fechaDesde, fechaHasta);
        ArrayList<ConsultaMedica> a1 = hm.get(1);
        ArrayList<ConsultaMedica> a2 = hm.get(2);
        String datos="<h1 align='center'> Historia Clínica <h1>";
        if(!fechaDesde.equals("")){
            datos+= "        <h2 align='center'>Fecha Desde: "+fechaDesde+"</h2>";
        }
        if(!fechaHasta.equals("")){
            datos+= "        <h2 align='center'>Fecha Hasta: "+fechaHasta+"</h2>";
        }
        datos+= "        <h2>Consultas activas:</h2>" +
"    <table style=\"width: 100%;\">" +
"       <tr style='background-color:#ffcc66'>" +
    "       <td style='width: 10%' align='center'><h3 style='margin:2%;'>Paciente</h3></td>" +
    "       <td style='width: 10%' align='center'><h3 style='margin:2%;'>Fecha Inicio</h3></td>" +
    "       <td style='width: 10%' align='center'><h3 style='margin:2%;'>Fecha Fin</h3></td>" +
    "       <td style='width: 20%' align='center'><h3 style='margin:2%;'>Diagnóstico</h3></td>" +
    "       <td style='width: 10%' align='center'><h3 style='margin:2%;'>Médico</h3></td>" +
    "       <td style='width: 20%' align='center'><h3 style='margin:2%;'>Tratamiento</h3></td>" +
"      </tr>" ;
                int i=0;
                String color;
                boolean imprimirSalto=true;
                for (ConsultaMedica c: a1){
                    if ((i%2)==0){
                        color="#ccccff";
                    }
                    else{
                        color="#ffff99";
                    }
                    i++;
datos+="<tr style='background-color:"+color+"'>" +
"                    <td style='width: 10%' align='center'>"+this.obtenerNombreCompleto(c.getIdPersonal())+"</td>" +
"                    <td style='width: 10%' align='center'>"+c.getFechaInicio()+"</td>" +
"                    <td style='width: 10%' align='center'>"+c.getFechaFin()+"</td>" +
"                    <td style='width: 20%' align='center'>"+c.getDiagnostico()+"</td>" +
"                    <td style='width: 10%' align='center'>"+this.obtenerNombreCompleto(c.getIdMedico())+"</td>" +
"                    <td style='width: 20%' align='center'>"+c.getTratamiento()+"</td>" +
"       </tr>" ;
                }
datos+="       </table>" +
"              <h2>Historial de Consultas:</h2>" +
"              <table style=\"width: 100%;\">" +
"                   <tr style='background-color:#ffcc66'>" +
"                        <td style='width: 10%' align='center'><h3 style='margin:2%;'>Paciente</h3></td>" +
    "                    <td style='width: 10%' align='center'><h3 style='margin:2%;'>Fecha Inicio</h3></td>" +
    "                    <td style='width: 10%' align='center'><h3 style='margin:2%;'>Fecha Fin</h3></td>" +
    "                    <td style='width: 20%' align='center'><h3 style='margin:2%;'>Diagnóstico</h3></td>" +
    "                    <td style='width: 10%' align='center'><h3 style='margin:2%;'>Médico</h3></td>" +
    "                    <td style='width: 20%' align='center'><h3 style='margin:2%;'>Tratamiento</h3></td>" +
"                   </tr>" ;

               for (ConsultaMedica c: a2){
                    if ((i%2)==0){
                        color="#ccccff";
                    }
                    else{
                        color="#ffff99";
                    }
                    i++;
datos+="        <tr style='background-color:"+color+"'>"+
"                    <td style='width: 10%' align='center'>"+this.obtenerNombreCompleto(c.getIdPersonal())+"</td>" +
"                    <td style='width: 10%' align='center'>"+c.getFechaInicio()+"</td>" +
"                    <td style='width: 10%' align='center'>"+c.getFechaFin()+"</td>" +
"                    <td style='width: 20%' align='center'>"+c.getDiagnostico()+"</td>" +
"                    <td style='width: 10%' align='center'>"+this.obtenerNombreCompleto(c.getIdMedico())+"</td>" +
"                    <td style='width: 20%' align='center'>"+c.getTratamiento()+"</td>" +
"               </tr>";
               }
datos+="    </table>";
    out.print(datos);
    }
    public void imprimirSancionados(PrintWriter out){
        HashMap<Integer,ArrayList<RecordSancionados>> hm= this.getListaSancionadosConDias();
        String datos="<h1 align='center'> Sancionados <h1>"+
"    <table style=\"width: 100%;\">" +
"       <tr style='background-color:#ffcc66'>" +
    "       <td style='width: 40%' align='center'><h3 style='margin:2%;'>Personal</h3></td>" +
    "       <td style='width: 30%' align='center'><h3 style='margin:2%;'>Tipo Sanción</h3></td>" +
    "       <td style='width: 10%' align='center'><h3 style='margin:2%;'>Días restantes</h3></td>" +
    "       <td style='width: 20%' align='center'><h3 style='margin:2%;'>Fecha Inicial</h3></td>" +
"      </tr>" ;
        int i=0;
        String color;
        Personal p;
        for (HashMap.Entry<Integer,ArrayList<RecordSancionados>> entry: hm.entrySet()){
            p= getPersonalBasico(entry.getKey());
            for(RecordSancionados r: entry.getValue()){
                if ((i%2)==0){
                    color="#ccccff";
                }
                else{
                    color="#ffff99";
                }
                i++;
                datos+="<tr style='background-color:"+color+"'>" +
"                    <td style='width: 40%' align='center'>"+this.obtenerNombreCompleto(p)+"</td>" +
"                    <td style='width: 30%' align='center'>"+r.tipo.getDescripcion()+"</td>" +
"                    <td style='width: 10%' align='center'>"+r.dias+"</td>" +
"                    <td style='width: 20%' align='center'>"+r.fecha+"</td>" +
"       </tr>" ;
            }
        }
        datos+="       </table>" ;
        out.print(datos);
    }
}
