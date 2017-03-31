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
import Classes.Personal;
import Classes.RecordPersonal;
import Classes.RecordRecalculo;
import Classes.RecordSancionados;
import Classes.Sancion;
import Classes.Tipo;
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
    public String obtenerNombreCompleto(Personal p){
        return (p.getGrado().getAbreviacion()+" "+p.getNombre()+" "+p.getApellido());
    }
    public void imprimirSanciones(int idPersonal,String fechaDesde, String fechaHasta, int tipoSancion, PrintWriter out){
        ArrayList<Sancion> as = this.getSancionesListar(idPersonal, fechaDesde, fechaHasta, tipoSancion);
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
        System.out.print(rr.dias + " " + rr.sancion.getFecha().toString());
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
                    System.out.println("Recalculando..");
                    RecordRecalculo rc = recalcularDias(s.getA().getCi(), s.getTipo().getId());
                    System.out.println("Fin recalculo..");
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
    public Apoderado getApoderado2(int ciApoderado){
        try {
            Statement s= connection.createStatement();
            String sql="Select * from apoderados where ci="+ciApoderado;
            ResultSet rs= s.executeQuery(sql);
            if (rs.next()){
                ManejadorCodigos mc= new ManejadorCodigos();
                return new Apoderado(rs.getInt("ci"), rs.getString("nombres"), rs.getString("apellidos"), null, rs.getString("domicilio"), rs.getString("celular"), rs.getString("telefono"));
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
            String sql="Update apoderados left join personal on apoderados.ci=personal.ci set idVinculo="+ apod.getVinculo().getId() +", apoderados.nombres='"+ apod.getNombre() +"', apoderados.apellidos='"+ apod.getApellido() +"', apoderados.celular='"+ apod.getCelular() +"', apoderados.domicilio='"+ apod.getDomicilio() +"', apoderados.telefono='"+ apod.getTelefono() +"' where apoderados.ci="+apod.getCi() +" and Personal.ci="+idPersonal;
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
            String sql="update personal set idApoderado=-1, idVinculo=-1 where  ci="+idPersonal;
            int i= s.executeUpdate(sql);
            if (i>0){
                sql="select * from personal where idApoderado="+idApod;
                ResultSet rs= s.executeQuery(sql);
                if(rs.next()){
                    return 1;
                }
                else{
                    sql="delete from apoderados where ci="+idApod;
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
    
    
    //familiares
    public ArrayList<Familiar> getFamiliares(int ciPersonal){
        ArrayList<Familiar> as= new ArrayList<>();
        try {
            String sql= "Select * from familiares left join `personal-familiar` on familiares.ci=idFamiliar where idPersonal="+ciPersonal;
            Statement s= connection.createStatement();
            ResultSet rs = s.executeQuery(sql);
            Familiar f;
            ManejadorCodigos mc = new ManejadorCodigos();
            while(rs.next()){
                f=new Familiar(rs.getInt("ci"),mc.getTipoFamiliar(rs.getInt("idVinculo")),rs.getString("nombres"),rs.getInt("edad"),rs.getString("apellidos"),rs.getString("domicilio"),rs.getString("ocupacion"),rs.getString("telefono"),rs.getString("celular"),rs.getBoolean("discapacidad"),rs.getString("descDiscapacidad"));
                as.add(f);
            }
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
                f=new Familiar(rs.getInt("ci"),mc.getTipoFamiliar(rs.getInt("idVinculo")),rs.getString("nombres"),rs.getInt("edad"),rs.getString("apellidos"),rs.getString("domicilio"),rs.getString("ocupacion"),rs.getString("telefono"),rs.getString("celular"),rs.getBoolean("discapacidad"),rs.getString("descDiscapacidad"));
            }
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
    public int desvincularFamiliar(int ciPersonal, int ciFamiliar){
        try {
            Statement s= connection.createStatement();
            String sql="delete from `personal-familiar` where idPersonal="+ciPersonal+" and idFamiliar="+ciFamiliar;
            int i= s.executeUpdate(sql);
            if (i>0){
                sql="select * from `personal-familiar` where idFamiliar="+ciFamiliar;
                ResultSet rs= s.executeQuery(sql);
                if(rs.next()){
                    return 1;
                }
                else{
                    sql="delete from familiares where ci="+ciFamiliar;
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
    public Familiar getFamiliar2(int ciFamiliar){
        Familiar f=null;
        try {
            String sql= "Select * from familiares where ci="+ciFamiliar;
            Statement s= connection.createStatement();
            ResultSet rs = s.executeQuery(sql);
            ManejadorCodigos mc = new ManejadorCodigos();
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
                i= s.executeUpdate(sql);
                
            }
            else{
                sql="update familiares set nombres='"+nombres+"',apellidos='"+apellidos+"',domicilio='"+domicilio+"',celular='"+celular+"',telefono='"+telefono+"',edad="+edad+",ocupacion='"+ocupacion+"',descDiscapacidad='"+descDiscapacidad+"',discapacidad='"+intDiscapacidad+"' where ci="+ciFamiliar;
                i= s.executeUpdate(sql);
            }
            if(i>0){
                    s= connection.createStatement();
                    sql="insert into `personal-familiar` (idPersonal, idFamiliar, idVinculo) values ("+ciPersonal+","+ciFamiliar+","+idvinculo+")";
                    i = s.executeUpdate(sql);
                    return i>0;
                }
        }
        catch(SQLException ex){
            return false;
        }

        return false;
    }

    public void imprimirPersonal(int ci, boolean basico, boolean familiar, boolean apoderado, boolean especialidades, PrintWriter out ){
        
    }
    public void imprimirDatosBasicos(int ci){
        
    }
}
