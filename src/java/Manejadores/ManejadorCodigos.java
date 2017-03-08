/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Manejadores;

import Classes.ConexionBD;
import Classes.Especialidad;
import Classes.Grado;
import Classes.TipoDocumento;
import Classes.TipoFamiliar;
import Classes.TipoSancion;
import Classes.UnidadMilitar;
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
    
    public ArrayList<Grado> getGrados(){
        ArrayList<Grado> al= new ArrayList<Grado>();
        try {
            Statement s= connection.createStatement();
            String sql="Select * from grado";
            ResultSet rs= s.executeQuery(sql);
            Grado g;
            while (rs.next()){
                g= new Grado(rs.getInt("id"), rs.getString("descripcion"));
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
                g= new Grado(rs.getInt("id"), rs.getString("descripcion"));
            }
            
        } catch (SQLException ex) {
            Logger.getLogger(ManejadorCodigos.class.getName()).log(Level.SEVERE, null, ex);
        }
        return g;
    }
    public Grado agregarGrado(String desc){
        Grado g=null;
        try {
            Statement s= connection.createStatement();
            String sql="insert into grado(descripcion) values('"+desc+"')";
            int i= s.executeUpdate(sql);
            if (i>0){
                ResultSet rs = s.getGeneratedKeys();
                if(rs.next()){
                        int id = (int)rs.getLong(1);
                        g= new Grado(id, desc);
                }
            }            
        } catch (SQLException ex) {
            Logger.getLogger(ManejadorCodigos.class.getName()).log(Level.SEVERE, null, ex);
        }
        return g;
    }
    public boolean modificarGrado(Grado g){
        try {
            Statement s= connection.createStatement();
            String sql="update grado set descripcion='"+g.getDescripcion()+"' where id="+g.getId();
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
            String sql="delete from grado where id="+id;
            int i= s.executeUpdate(sql);
            return (i>0);
        } catch (SQLException ex) {
            Logger.getLogger(ManejadorCodigos.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }
    
    
    public ArrayList<Especialidad> getEspecialidades(){
        ArrayList<Especialidad> al= new ArrayList<Especialidad>();
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
    public Especialidad agregarEspecialidad(String desc){
        Especialidad g=null;
        try {
            Statement s= connection.createStatement();
            String sql="insert into especialidades(descripcion) values('"+desc+"')";
            int i= s.executeUpdate(sql);
            if (i>0){
                ResultSet rs = s.getGeneratedKeys();
                if(rs.next()){
                        int id = (int)rs.getLong(1);
                        g= new Especialidad(id, desc);
                }
            }            
        } catch (SQLException ex) {
            Logger.getLogger(ManejadorCodigos.class.getName()).log(Level.SEVERE, null, ex);
        }
        return g;
    }
    public boolean modificarEspecialidad(Especialidad g){
        try {
            Statement s= connection.createStatement();
            String sql="update especialidades set descripcion='"+g.getDescripcion()+"' where id="+g.getId();
            int i= s.executeUpdate(sql);
            return (i>0);
        } catch (SQLException ex) {
            Logger.getLogger(ManejadorCodigos.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }
    public boolean eliminarEspecialidad(int id){
        try {
            Statement s= connection.createStatement();
            String sql="delete from especialidades where id="+id;
            int i= s.executeUpdate(sql);
            return (i>0);
        } catch (SQLException ex) {
            Logger.getLogger(ManejadorCodigos.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }
     
    public ArrayList<TipoDocumento> getTiposDocumentos(){
        ArrayList<TipoDocumento> al= new ArrayList<TipoDocumento>();
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
    public TipoDocumento agregarTipoDocumento(String desc){
        TipoDocumento g=null;
        try {
            Statement s= connection.createStatement();
            String sql="insert into tipodocumento(descripcion) values('"+desc+"')";
            int i= s.executeUpdate(sql);
            if (i>0){
                ResultSet rs = s.getGeneratedKeys();
                if(rs.next()){
                        int id = (int)rs.getLong(1);
                        g= new TipoDocumento(id, desc);
                }
            }            
        } catch (SQLException ex) {
            Logger.getLogger(ManejadorCodigos.class.getName()).log(Level.SEVERE, null, ex);
        }
        return g;
    }
    public boolean modificarTipoDocumento(TipoDocumento g){
        try {
            Statement s= connection.createStatement();
            String sql="update TipoDocumento set descripcion='"+g.getDescripcion()+"' where id="+g.getId();
            int i= s.executeUpdate(sql);
            return (i>0);
        } catch (SQLException ex) {
            Logger.getLogger(ManejadorCodigos.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }
    public boolean eliminarTipoDocumento(int id){
        try {
            Statement s= connection.createStatement();
            String sql="delete from TipoDocumento where id="+id;
            int i= s.executeUpdate(sql);
            return (i>0);
        } catch (SQLException ex) {
            Logger.getLogger(ManejadorCodigos.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }
    
    public ArrayList<TipoFamiliar> getTiposFamiliares(){
        ArrayList<TipoFamiliar> al= new ArrayList<TipoFamiliar>();
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
    public TipoFamiliar agregarTipoFamiliar(String desc){
        TipoFamiliar g=null;
        try {
            Statement s= connection.createStatement();
            String sql="insert into TipoFamiliar(descripcion) values('"+desc+"')";
            int i= s.executeUpdate(sql);
            if (i>0){
                ResultSet rs = s.getGeneratedKeys();
                if(rs.next()){
                        int id = (int)rs.getLong(1);
                        g= new TipoFamiliar(id, desc);
                }
            }            
        } catch (SQLException ex) {
            Logger.getLogger(ManejadorCodigos.class.getName()).log(Level.SEVERE, null, ex);
        }
        return g;
    }
    public boolean modificarTipoFamiliar(TipoFamiliar g){
        try {
            Statement s= connection.createStatement();
            String sql="update TipoFamiliar set descripcion='"+g.getDescripcion()+"' where id="+g.getId();
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
            String sql="delete from TipoFamiliar where id="+id;
            int i= s.executeUpdate(sql);
            return (i>0);
        } catch (SQLException ex) {
            Logger.getLogger(ManejadorCodigos.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }
     
    public ArrayList<TipoSancion> getTiposSanciones(){
        ArrayList<TipoSancion> al= new ArrayList<TipoSancion>();
        try {
            Statement s= connection.createStatement();
            String sql="Select * from tipofamiliar";
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
            String sql="Select * from tipofamiliar where id="+id;
            ResultSet rs= s.executeQuery(sql);
            
            if (rs.next()){
                ts= new TipoSancion(rs.getInt("id"), rs.getString("descripcion"));
            }
            
        } catch (SQLException ex) {
            Logger.getLogger(ManejadorCodigos.class.getName()).log(Level.SEVERE, null, ex);
        }
        return ts;
    }
    public TipoSancion agregarTipoSancion(String desc){
        TipoSancion g=null;
        try {
            Statement s= connection.createStatement();
            String sql="insert into TipoSancion(descripcion) values('"+desc+"')";
            int i= s.executeUpdate(sql);
            if (i>0){
                ResultSet rs = s.getGeneratedKeys();
                if(rs.next()){
                        int id = (int)rs.getLong(1);
                        g= new TipoSancion(id, desc);
                }
            }            
        } catch (SQLException ex) {
            Logger.getLogger(ManejadorCodigos.class.getName()).log(Level.SEVERE, null, ex);
        }
        return g;
    }
    public boolean modificarTipoSancion(TipoSancion g){
        try {
            Statement s= connection.createStatement();
            String sql="update TipoSancion set descripcion='"+g.getDescripcion()+"' where id="+g.getId();
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
            String sql="delete from TipoSancion where id="+id;
            int i= s.executeUpdate(sql);
            return (i>0);
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
    public UnidadMilitar agregarUnidadMilitar(String correo, String nombre, String telefono){
        UnidadMilitar g=null;
        try {
            Statement s= connection.createStatement();
            String sql="insert into unidadesmilitares(correo, nombre, telefono) values('"+correo+"','"+nombre+"','"+telefono+"')";
            int i= s.executeUpdate(sql);
            if (i>0){
                ResultSet rs = s.getGeneratedKeys();
                if(rs.next()){
                        int id = (int)rs.getLong(1);
                        g= new UnidadMilitar(id, nombre,telefono,correo);
                }
            }            
        } catch (SQLException ex) {
            Logger.getLogger(ManejadorCodigos.class.getName()).log(Level.SEVERE, null, ex);
        }
        return g;
    }
    public boolean modificarUnidadMilitar(UnidadMilitar g){
        try {
            Statement s= connection.createStatement();
            String sql="update UnidadesMilitares set nombre='"+g.getNombre()+"',correo='"+g.getCorreo()+"',telefono='"+g.getTelefono()+"' where id="+g.getId();
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
            String sql="delete from UnidadesMilitares where id="+id;
            int i= s.executeUpdate(sql);
            return (i>0);
        } catch (SQLException ex) {
            Logger.getLogger(ManejadorCodigos.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }
}
