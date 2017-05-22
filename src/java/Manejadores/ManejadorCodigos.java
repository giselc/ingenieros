/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Manejadores;

import Classes.AlertaVehiculo;
import Classes.ConexionBD;
import Classes.Destino;
import Classes.Especialidad;
import Classes.Grado;
import Classes.ModeloArmamento;
import Classes.Municion;
import Classes.Tipo;
import Classes.TipoDocumento;
import Classes.TipoFamiliar;
import Classes.TipoMantenimientoVehiculo;
import Classes.TipoSancion;
import Classes.UnidadMilitar;
import Classes.Usuario;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Gisel
 */

//ESTE MANEJADOR INTERACTUA CON LAS CLASES CON DEPENDENCIA DIRECTA CON LA BASE DE DATOS
public class ManejadorCodigos {
    private Connection connection;

    public ManejadorCodigos() {
        connection = ConexionBD.GetConnection();
    }
    public void CerrarConexionManejador(){
        try {
            connection.close();
        } catch (SQLException ex) {
            Logger.getLogger(ManejadorClases.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    public ArrayList<Tipo> getGrados(){
        ArrayList<Tipo> al= new ArrayList<Tipo>();
        try {
            Statement s= connection.createStatement();
            String sql="Select * from grado";
            ResultSet rs= s.executeQuery(sql);
            Grado g;
            while (rs.next()){
                g= new Grado(rs.getInt("id"), rs.getString("descripcion"), rs.getString("abreviacion"));
                al.add(g);
            }
            
        } catch (SQLException ex) {
            Logger.getLogger(ManejadorCodigos.class.getName()).log(Level.SEVERE, null, ex);
        }
        return al;
    }
    public Grado getGrado(int id){
        Grado g=null;
        try {
            
            Statement s= connection.createStatement();
            String sql="Select * from grado where id="+id;
            ResultSet rs= s.executeQuery(sql);
            
            if (rs.next()){
                g= new Grado(rs.getInt("id"), rs.getString("descripcion"), rs.getString("abreviacion"));
            }
            
        } catch (SQLException ex) {
            Logger.getLogger(ManejadorCodigos.class.getName()).log(Level.SEVERE, null, ex);
        }
        return g;
    }
    public boolean agregarGrado(String desc, String abr){
        try {
            Statement s= connection.createStatement();
            String sql="insert into grado(descripcion,abreviacion) values('"+desc+"','"+abr+"')";
            int i= s.executeUpdate(sql);
            if (i>0){
                return true;
            }            
        } catch (SQLException ex) {
            Logger.getLogger(ManejadorCodigos.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }
    public boolean modificarGrado(int id, String desc, String abr){
        try {
            Statement s= connection.createStatement();
            String sql="update grado set descripcion='"+desc+"',abreviacion='"+abr+"' where id="+id;
            int i= s.executeUpdate(sql);
            return (i>0);
        } catch (SQLException ex) {
            Logger.getLogger(ManejadorCodigos.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }
    public boolean eliminarGrado(int id){
        try {
            Statement s= connection.createStatement();
            String sql="select * from personal where grado="+id;
            ResultSet rs= s.executeQuery(sql);
            if(rs.next()){
                return false;
            }
            else{
                sql="select * from `personal-historial` where grado="+id;
                rs= s.executeQuery(sql);
                if(rs.next()){
                    return false;
                }
                else{
                    sql="delete from grado where id="+id;
                    int i= s.executeUpdate(sql);
                    return (i>0);
                }
            }
        } catch (SQLException ex) {
            Logger.getLogger(ManejadorCodigos.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }

    
    public ArrayList<Tipo> getEspecialidades(){
        ArrayList<Tipo> al= new ArrayList<Tipo>();
        try {
            Statement s= connection.createStatement();
            String sql="Select * from especialidades";
            ResultSet rs= s.executeQuery(sql);
            Especialidad g;
            while (rs.next()){
                g= new Especialidad(rs.getInt("id"), rs.getString("descripcion"));
                al.add(g);
            }
            
        } catch (SQLException ex) {
            Logger.getLogger(ManejadorCodigos.class.getName()).log(Level.SEVERE, null, ex);
        }
        return al;
    }
    public Especialidad getEspecialidad(int id){
        Especialidad g=null;
        try {
            Statement s= connection.createStatement();
            String sql="Select * from especialidades where id="+id;
            ResultSet rs= s.executeQuery(sql);
            
            if (rs.next()){
                g= new Especialidad(rs.getInt("id"), rs.getString("descripcion"));
            }
            
        } catch (SQLException ex) {
            Logger.getLogger(ManejadorCodigos.class.getName()).log(Level.SEVERE, null, ex);
        }
        return g;
    }
    public boolean agregarEspecialidad(String desc){
        try {
            Statement s= connection.createStatement();
            String sql="insert into especialidades(descripcion) values('"+desc+"')";
            int i= s.executeUpdate(sql);
            if (i>0){
                return true;
            }            
        } catch (SQLException ex) {
            Logger.getLogger(ManejadorCodigos.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }
    public boolean modificarEspecialidad(int id, String desc){
        try {
            Statement s= connection.createStatement();
            String sql="update especialidades set descripcion='"+desc+"' where id="+id;
            int i= s.executeUpdate(sql);
            return (i>0);
        } catch (SQLException ex) {
            Logger.getLogger(ManejadorCodigos.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }
    public boolean eliminarEspecialidad(int id){
        try {
            Statement s = connection.createStatement();
            String sql="select * from `personal-especialidad` where idEspecialidad="+id;
            ResultSet rs= s.executeQuery(sql);
            if(rs.next()){
                return false;
            }
            else{
                sql="delete from especialidades where id="+id;
                int i= s.executeUpdate(sql);
                return (i>0);
            }
        } catch (SQLException ex) {
            Logger.getLogger(ManejadorCodigos.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }
    
    public TipoMantenimientoVehiculo getTipoMantenimientoVehiculo(int id){
        TipoMantenimientoVehiculo g=null;
        try {
            Statement s= connection.createStatement();
            String sql="Select * from tipomantenimientovehiculo where id="+id;
            ResultSet rs= s.executeQuery(sql);
            
            if (rs.next()){
                g= new TipoMantenimientoVehiculo(rs.getInt("id"), rs.getString("descripcion"));
            }
            
        } catch (SQLException ex) {
            Logger.getLogger(ManejadorCodigos.class.getName()).log(Level.SEVERE, null, ex);
        }
        return g;
    }
    public boolean agregarTipoMantenimientoVehiculo(String desc){
        try {
            Statement s= connection.createStatement();
            String sql="insert into TipoMantenimientoVehiculo(descripcion) values('"+desc+"')";
            int i= s.executeUpdate(sql);
            if (i>0){
                return true;
            }            
        } catch (SQLException ex) {
            Logger.getLogger(ManejadorCodigos.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }
    public boolean modificarTipoMantenimientoVehiculo(int id, String desc){
        try {
            Statement s= connection.createStatement();
            String sql="update TipoMantenimientoVehiculo set descripcion='"+desc+"' where id="+id;
            int i= s.executeUpdate(sql);
            return (i>0);
        } catch (SQLException ex) {
            Logger.getLogger(ManejadorCodigos.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }
    public boolean eliminarTipoMantenimientoVehiculo(int id){
        try {
            Statement s = connection.createStatement();
            String sql="select * from alertasVehiculo where idTipoMantenimiento="+id;
            ResultSet rs= s.executeQuery(sql);
            if(rs.next()){
                return false;
            }
            else{
                sql="delete from TipoMantenimientoVehiculo where id="+id;
                int i= s.executeUpdate(sql);
                return (i>0);
            }
        } catch (SQLException ex) {
            Logger.getLogger(ManejadorCodigos.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }
    public ArrayList<Tipo> getTiposMantenimientoVehiculo(){
        ArrayList<Tipo> al= new ArrayList<Tipo>();
        try {
            Statement s= connection.createStatement();
            String sql="Select * from TipoMantenimientoVehiculo";
            ResultSet rs= s.executeQuery(sql);
            TipoMantenimientoVehiculo td;
            while (rs.next()){
                td= new TipoMantenimientoVehiculo(rs.getInt("id"), rs.getString("descripcion"));
                al.add(td);
            }
            
        } catch (SQLException ex) {
            Logger.getLogger(ManejadorCodigos.class.getName()).log(Level.SEVERE, null, ex);
        }
        return al;
    }
  
    public ArrayList<Tipo> getTiposDocumentos(){
        ArrayList<Tipo> al= new ArrayList<Tipo>();
        try {
            Statement s= connection.createStatement();
            String sql="Select * from tipodocumento";
            ResultSet rs= s.executeQuery(sql);
            TipoDocumento td;
            while (rs.next()){
                td= new TipoDocumento(rs.getInt("id"), rs.getString("descripcion"));
                al.add(td);
            }
            
        } catch (SQLException ex) {
            Logger.getLogger(ManejadorCodigos.class.getName()).log(Level.SEVERE, null, ex);
        }
        return al;
    }
    public TipoDocumento getTipoDocumento(int id){
        TipoDocumento td=null;
        try {
            Statement s= connection.createStatement();
            String sql="Select * from tipodocumento where id="+id;
            ResultSet rs= s.executeQuery(sql);
            
            if (rs.next()){
                td= new TipoDocumento(rs.getInt("id"), rs.getString("descripcion"));
            }
            
        } catch (SQLException ex) {
            Logger.getLogger(ManejadorCodigos.class.getName()).log(Level.SEVERE, null, ex);
        }
        return td;
    }
    public boolean agregarTipoDocumento(String desc){
        try {
            Statement s= connection.createStatement();
            String sql="insert into tipodocumento(descripcion) values('"+desc+"')";
            int i= s.executeUpdate(sql);
            if (i>0){
                return true;
            }            
        } catch (SQLException ex) {
            Logger.getLogger(ManejadorCodigos.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }
    public boolean modificarTipoDocumento(int id, String desc){
        try {
            Statement s= connection.createStatement();
            String sql="update TipoDocumento set descripcion='"+desc+"' where id="+id;
            int i= s.executeUpdate(sql);
            return (i>0);
        } catch (SQLException ex) {
            Logger.getLogger(ManejadorCodigos.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }
    public boolean eliminarTipoDocumento(int id){
        try {
            Statement s = connection.createStatement();
            String sql="select * from documentos where idTipoDocumento="+id;
            ResultSet rs= s.executeQuery(sql);
            if(rs.next()){
                return false;
            }
            else{
                sql="delete from TipoDocumento where id="+id;
                int i= s.executeUpdate(sql);
                return (i>0);
            }
        } catch (SQLException ex) {
            Logger.getLogger(ManejadorCodigos.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }
    
    public ArrayList<Tipo> getTiposFamiliares(){
        ArrayList<Tipo> al= new ArrayList<Tipo>();
        try {
            Statement s= connection.createStatement();
            String sql="Select * from tipofamiliar";
            ResultSet rs= s.executeQuery(sql);
            TipoFamiliar tf;
            while (rs.next()){
                tf= new TipoFamiliar(rs.getInt("id"), rs.getString("descripcion"));
                al.add(tf);
            }
            
        } catch (SQLException ex) {
            Logger.getLogger(ManejadorCodigos.class.getName()).log(Level.SEVERE, null, ex);
        }
        return al;
    }
    public TipoFamiliar getTipoFamiliar(int id){
        TipoFamiliar tf=null;
        try {
            Statement s= connection.createStatement();
            String sql="Select * from tipofamiliar where id="+id;
            ResultSet rs= s.executeQuery(sql);
            
            if (rs.next()){
                tf= new TipoFamiliar(rs.getInt("id"), rs.getString("descripcion"));
            }
            
        } catch (SQLException ex) {
            Logger.getLogger(ManejadorCodigos.class.getName()).log(Level.SEVERE, null, ex);
        }
        return tf;
    }
    public boolean agregarTipoFamiliar(String desc){
        try {
            Statement s= connection.createStatement();
            String sql="insert into TipoFamiliar(descripcion) values('"+desc+"')";
            int i= s.executeUpdate(sql);
            if (i>0){
                return true;
            }            
        } catch (SQLException ex) {
            Logger.getLogger(ManejadorCodigos.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }
    public boolean modificarTipoFamiliar(int id, String desc){
        try {
            Statement s= connection.createStatement();
            String sql="update TipoFamiliar set descripcion='"+desc+"' where id="+id;
            int i= s.executeUpdate(sql);
            return (i>0);
        } catch (SQLException ex) {
            Logger.getLogger(ManejadorCodigos.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }
    public boolean eliminarTipoFamiliar(int id){
        try {
            Statement s= connection.createStatement();
            String sql="select * from personal where idVinculo="+id;
            ResultSet rs= s.executeQuery(sql);
            if(rs.next()){
                return false;
            }
            else{
                sql="select * from `personal-familiar` where idVinculo="+id;
                rs= s.executeQuery(sql);
                if(rs.next()){
                    return false;
                }
                else{
                    sql="delete from TipoFamiliar where id="+id;
                    int i= s.executeUpdate(sql);
                    return (i>0);
                }
            }
        } catch (SQLException ex) {
            Logger.getLogger(ManejadorCodigos.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }
     
    public ArrayList<Tipo> getTiposSanciones(){
        ArrayList<Tipo> al= new ArrayList<Tipo>();
        try {
            Statement s= connection.createStatement();
            String sql="Select * from tipoSancion";
            ResultSet rs= s.executeQuery(sql);
            TipoSancion ts;
            while (rs.next()){
                ts= new TipoSancion(rs.getInt("id"), rs.getString("descripcion"));
                al.add(ts);
            }
            
        } catch (SQLException ex) {
            Logger.getLogger(ManejadorCodigos.class.getName()).log(Level.SEVERE, null, ex);
        }
        return al;
    }
    public TipoSancion getTipoSancion(int id){
        TipoSancion ts=null;
        try {
            Statement s= connection.createStatement();
            String sql="Select * from tipoSancion where id="+id;
            ResultSet rs= s.executeQuery(sql);
            
            if (rs.next()){
                ts= new TipoSancion(rs.getInt("id"), rs.getString("descripcion"));
            }
            
        } catch (SQLException ex) {
            Logger.getLogger(ManejadorCodigos.class.getName()).log(Level.SEVERE, null, ex);
        }
        return ts;
    }
    public boolean agregarTipoSancion(String desc){
        TipoSancion g=null;
        try {
            Statement s= connection.createStatement();
            String sql="insert into TipoSancion(descripcion) values('"+desc+"')";
            int i= s.executeUpdate(sql);
            if (i>0){
                return true;
            }            
        } catch (SQLException ex) {
            Logger.getLogger(ManejadorCodigos.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }
    public boolean modificarTipoSancion(int id, String desc){
        try {
            Statement s= connection.createStatement();
            String sql="update TipoSancion set descripcion='"+desc+"' where id="+id;
            int i= s.executeUpdate(sql);
            return (i>0);
        } catch (SQLException ex) {
            Logger.getLogger(ManejadorCodigos.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }
    public boolean eliminarTipoSancion(int id){
        try {
            Statement s= connection.createStatement();
            String sql="select * from sanciones where idTipoSancion="+id;
            ResultSet rs= s.executeQuery(sql);
            if(rs.next()){
                return false;
            }
            else{
                sql="select * from `sanciones-historial` where idTipoSancion="+id;
                rs= s.executeQuery(sql);
                if(rs.next()){
                    return false;
                }
                else{
                    sql="delete from TipoSancion where id="+id;
                    int i= s.executeUpdate(sql);
                    return (i>0);
                }
            }
        } catch (SQLException ex) {
            Logger.getLogger(ManejadorCodigos.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }
     
    public ArrayList<UnidadMilitar> getUnidadesMilitares(){
        ArrayList<UnidadMilitar> al= new ArrayList<UnidadMilitar>();
        try {
            Statement s= connection.createStatement();
            String sql="Select * from unidadesmilitares";
            ResultSet rs= s.executeQuery(sql);
            UnidadMilitar um;
            while (rs.next()){
                um= new UnidadMilitar(rs.getInt("id"), rs.getString("nombre"),rs.getString("telefono"),rs.getString("correo"));
                al.add(um);
            }
            
        } catch (SQLException ex) {
            Logger.getLogger(ManejadorCodigos.class.getName()).log(Level.SEVERE, null, ex);
        }
        return al;
    }
    public UnidadMilitar getUnidadMilitar(int id){
        UnidadMilitar um=null;
        try {
            Statement s= connection.createStatement();
            String sql="Select * from unidadesmilitares where id="+id;
            ResultSet rs= s.executeQuery(sql);
            
            if (rs.next()){
                um= new UnidadMilitar(rs.getInt("id"), rs.getString("nombre"),rs.getString("telefono"),rs.getString("correo"));
            }
            
        } catch (SQLException ex) {
            Logger.getLogger(ManejadorCodigos.class.getName()).log(Level.SEVERE, null, ex);
        }
        return um;
    }
    public boolean agregarUnidadMilitar(String correo, String nombre, String telefono){
        try {
            Statement s= connection.createStatement();
            String sql="insert into unidadesmilitares(correo, nombre, telefono) values('"+correo+"','"+nombre+"','"+telefono+"')";
            int i= s.executeUpdate(sql);
            if (i>0){
                return true;
            }            
        } catch (SQLException ex) {
            Logger.getLogger(ManejadorCodigos.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }
    public boolean modificarUnidadMilitar(int id, String nombre, String telefono, String correo){
        try {
            Statement s= connection.createStatement();
            String sql="update UnidadesMilitares set nombre='"+nombre+"',correo='"+correo+"',telefono='"+telefono+"' where id="+id;
            int i= s.executeUpdate(sql);
            return (i>0);
        } catch (SQLException ex) {
            Logger.getLogger(ManejadorCodigos.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }
    public boolean eliminarUnidadMilitar(int id){
        try {
            Statement s= connection.createStatement();
            String sql="select * from personal where unidadMilitar="+id;
            ResultSet rs= s.executeQuery(sql);
            if(rs.next()){
                return false;
            }
            else{
                sql="select * from `personal-historial` where unidadMilitar="+id;
                rs= s.executeQuery(sql);
                if(rs.next()){
                    return false;
                }
                else{
                    sql="delete from UnidadesMilitares where id="+id;
                    int i= s.executeUpdate(sql);
                    return (i>0);
                }
            }
        } catch (SQLException ex) {
            Logger.getLogger(ManejadorCodigos.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }
    public void imprimirUnidadesMilitares(PrintWriter out){
        out.print("<h1 align='center'>UNIDADES MILITARES DEL SISTEMA</h1>");
        out.print("<table style=\"width: 100%;\">");
        ArrayList<UnidadMilitar> au = this.getUnidadesMilitares();
        out.print("<tr style='background-color:#ffcc66'>");
                out.print("<td style='width: 30%' align='center'><h3 style='margin:2%;'>Nombre</h3></td>");
                out.print("<td style='width: 20%' align='center'><h3 style='margin:2%;'>Telefono</h3></td>");
                out.print("<td style='width: 30%' align='center'><h3 style='margin:2%;'>Correo</h3></td>");
        out.print("</tr></table>  <table style=\"width: 100%;\">" );
        int i=0;
        String color;
        for (UnidadMilitar u1: au){
            if ((i%2)==0){
                color=" #ccccff";
            }
            else{
                color=" #ffff99";
            }
            i++;

            out.print("<tr style='background-color:"+color+"'>");
                out.print("<td style='width: 30%' align='center'>"+u1.getNombre()+"</td>");
                out.print("<td style='width: 20%' align='center'>"+u1.getTelefono()+"</td>");
                out.print("<td style='width: 30%' align='center'>"+u1.getCorreo()+"</td>");
            out.print("</tr>");
        }
        out.print("</table>");
    }
    
    public void imprmirTipo(PrintWriter out, String tipo){
        ArrayList<Tipo> au = null;
        switch(tipo){
            case "Especialidades":  au = this.getEspecialidades(); out.print("<h1 align='center'>ESPECIALIDADES DEL SISTEMA</h1>");break;
            case "Grados":  au = this.getGrados(); out.print("<h1 align='center'>GRADOS DEL SISTEMA</h1>");break;
            case "Documentos":  au = this.getTiposDocumentos(); out.print("<h1 align='center'>DOCUMENTOS DEL SISTEMA</h1>");break;
            case "Familiares":  au = this.getTiposFamiliares(); out.print("<h1 align='center'>FAMILIARES DEL SISTEMA</h1>");break;
            case "Sanciones":  au = this.getTiposSanciones(); out.print("<h1 align='center'>SANCIONES DEL SISTEMA</h1>");break;
        }
        
        out.print("<table style=\"width: 100%;\">");
        
        out.print("<tr style='background-color:#ffcc66' align='center'>");
                if(tipo.equals("Grados")){
                    out.print("<td style='width: 30%' align='center'><h3 style='margin:2%;'>Abreviacion</h3></td>");
                }
                out.print("<td style='width: 20%' align='center'><h3 style='margin:2%;'>Descripcion</h3></td>");
        out.print("</tr>" );
        int i=0;
        String color;
        for (Tipo u1: au){
            if ((i%2)==0){
                color=" #ccccff";
            }
            else{
                color=" #ffff99";
            }
            i++;

            out.print("<tr style='background-color:"+color+"'>");
                if(tipo.equals("Grados")){
                    out.print("<td style='width: 30%' align='center'>"+((Grado)u1).getAbreviacion()+"</td>");
                }
                out.print("<td style='width: 20%' align='center'>"+u1.getDescripcion()+"</td>");
            out.print("</tr>");
        }
        out.print("</table>");
    }
    
    public ArrayList<Municion> getMuniciones(){
        ArrayList<Municion> al= new ArrayList<Municion>();
        try {
            Statement s= connection.createStatement();
            String sql="Select * from municion";
            ResultSet rs= s.executeQuery(sql);
            Municion g;
            while (rs.next()){
                g= new Municion(rs.getString("calibre"));
                al.add(g);
            }
            
        } catch (SQLException ex) {
            Logger.getLogger(ManejadorCodigos.class.getName()).log(Level.SEVERE, null, ex);
        }
        return al;
    }
    public Municion getMunicion(String calibre){
        Municion g=null;
        try {
            
            Statement s= connection.createStatement();
            String sql="Select * from Municion where calibre='"+calibre+"'";
            ResultSet rs= s.executeQuery(sql);
            
            if (rs.next()){
                g= new Municion(rs.getString("calibre"));
            }
            
        } catch (SQLException ex) {
            Logger.getLogger(ManejadorCodigos.class.getName()).log(Level.SEVERE, null, ex);
        }
        return g;
    }
    public boolean agregarMunicion(String calibre){
        try {
            Statement s= connection.createStatement();
            String sql="insert into Municion(calibre) values('"+calibre+"')";
            int i= s.executeUpdate(sql);
            if (i>0){
                return true;
            }            
        } catch (SQLException ex) {
            Logger.getLogger(ManejadorCodigos.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }
    public boolean eliminarMunicion(String calibre){
        try {
            Statement s= connection.createStatement();
            String sql="select * from armamento where calibre='"+calibre+"'";
            ResultSet rs= s.executeQuery(sql);
            if(rs.next()){
                return false;
            }
            else{
                sql="select * from loteMunicion where idMunicion="+calibre;
                rs= s.executeQuery(sql);
                if(rs.next()){
                    return false;
                }
                else{
                    sql="delete from Municion where calibre="+calibre;
                    int i= s.executeUpdate(sql);
                    return (i>0);
                }
            }
        } catch (SQLException ex) {
            Logger.getLogger(ManejadorCodigos.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }
    
    public ArrayList<Tipo> getDestinos(){
        ArrayList<Tipo> al= new ArrayList<Tipo>();
        try {
            Statement s= connection.createStatement();
            String sql="Select * from destinoArmamento";
            ResultSet rs= s.executeQuery(sql);
            Destino g;
            while (rs.next()){
                g= new Destino(rs.getInt("id"),rs.getString("descripcion"));
                al.add(g);
            }
            
        } catch (SQLException ex) {
            Logger.getLogger(ManejadorCodigos.class.getName()).log(Level.SEVERE, null, ex);
        }
        return al;
    }
    public Destino getDestino(int id){
        Destino g=null;
        try {
            
            Statement s= connection.createStatement();
            String sql="Select * from destinoarmamento where id="+id;
            ResultSet rs= s.executeQuery(sql);
            
            if (rs.next()){
                g= new Destino(rs.getInt("id"),rs.getString("descripcion"));
            }
            
        } catch (SQLException ex) {
            Logger.getLogger(ManejadorCodigos.class.getName()).log(Level.SEVERE, null, ex);
        }
        return g;
    }
    public boolean agregarDestino(String descripcion){
        try {
            Statement s= connection.createStatement();
            String sql="insert into destinoarmamento(descripcion) values('"+descripcion+"')";
            int i= s.executeUpdate(sql);
            if (i>0){
                return true;
            }            
        } catch (SQLException ex) {
            Logger.getLogger(ManejadorCodigos.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }
    public boolean modificarDestino(int id, String desc){
        try {
            Statement s= connection.createStatement();
            String sql="update destinoarmamento set descripcion='"+desc+"' where id="+id;
            int i= s.executeUpdate(sql);
            return (i>0);
        } catch (SQLException ex) {
            Logger.getLogger(ManejadorCodigos.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }
    public boolean eliminarDestino(int id){
        try {
            String sql="delete from destinoarmamento where id="+id;
            Statement s= connection.createStatement();
             int i= s.executeUpdate(sql);
            return (i>0);
            
        } catch (SQLException ex) {
            Logger.getLogger(ManejadorCodigos.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }
    
    public ArrayList<Tipo> getModelosArmamento(){
        ArrayList<Tipo> al= new ArrayList<Tipo>();
        try {
            Statement s= connection.createStatement();
            String sql="Select * from modeloarmamento";
            ResultSet rs= s.executeQuery(sql);
            ModeloArmamento g;
            while (rs.next()){
                g= new ModeloArmamento(rs.getInt("id"),rs.getString("descripcion"));
                al.add(g);
            }
            
        } catch (SQLException ex) {
            Logger.getLogger(ManejadorCodigos.class.getName()).log(Level.SEVERE, null, ex);
        }
        return al;
    }
    public ModeloArmamento getModeloArmamento(int id){
        ModeloArmamento g=null;
        try {
            
            Statement s= connection.createStatement();
            String sql="Select * from ModeloArmamento where id="+id;
            ResultSet rs= s.executeQuery(sql);
            
            if (rs.next()){
                g= new ModeloArmamento(rs.getInt("id"),rs.getString("descripcion"));
            }
            
        } catch (SQLException ex) {
            Logger.getLogger(ManejadorCodigos.class.getName()).log(Level.SEVERE, null, ex);
        }
        return g;
    }
    public boolean agregarModeloArmamento(String descripcion){
        try {
            Statement s= connection.createStatement();
            String sql="insert into ModeloArmamento(descripcion) values('"+descripcion+"')";
            int i= s.executeUpdate(sql);
            if (i>0){
                return true;
            }            
        } catch (SQLException ex) {
            Logger.getLogger(ManejadorCodigos.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }
    public boolean modificarModeloArmamento(int id, String desc){
        try {
            Statement s= connection.createStatement();
            String sql="update ModeloArmamento set descripcion='"+desc+"' where id="+id;
            int i= s.executeUpdate(sql);
            return (i>0);
        } catch (SQLException ex) {
            Logger.getLogger(ManejadorCodigos.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }
    public boolean eliminarModeloArmamento(int id){
        try {
            String sql="delete from ModeloArmamento where id="+id;
            Statement s= connection.createStatement();
            int i= s.executeUpdate(sql);
            return (i>0);
            
        } catch (SQLException ex) {
            Logger.getLogger(ManejadorCodigos.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }
    
}
