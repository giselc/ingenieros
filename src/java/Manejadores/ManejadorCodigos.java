/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Manejadores;

import Classes.ConexionBD;
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
            String sql="Select * from tipodocumento where id="+id;
            ResultSet rs= s.executeQuery(sql);
            
            if (rs.next()){
                g= new Grado(rs.getInt("id"), rs.getString("descripcion"));
            }
            
        } catch (SQLException ex) {
            Logger.getLogger(ManejadorCodigos.class.getName()).log(Level.SEVERE, null, ex);
        }
        return g;
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
    
}
