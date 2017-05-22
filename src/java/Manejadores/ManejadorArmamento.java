/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Manejadores;

import Classes.Armamento;
import Classes.ConexionBD;
import Classes.ISArmamento;
import Classes.ObservacionArmamento;
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
public class ManejadorArmamento {
    private Connection connection;

    public ManejadorArmamento() {
        connection = ConexionBD.GetConnection();
    }
    public void CerrarConexionManejador(){
        try {
            connection.close();
        } catch (SQLException ex) {
            Logger.getLogger(ManejadorClases.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    public ArrayList<ISArmamento> getInformacionSumariasArmamento(int idArmamento){
        ArrayList<ISArmamento> al= new ArrayList();
        try {
            Statement s= connection.createStatement();
            String sql="Select * from ISArmamento where idArmamento="+idArmamento;
            ResultSet rs= s.executeQuery(sql);
            ISArmamento g;
            ManejadorPersonal mp= new ManejadorPersonal();
            while (rs.next()){
                g= new ISArmamento(rs.getInt("id"),rs.getInt("idArmamento"),rs.getDate("fecha"),rs.getString("informacionSumaria"),mp.getPersonalBasico(rs.getInt("idOficialArmamento")));
                al.add(g);
            }
            mp.CerrarConexionManejador();
        } catch (SQLException ex) {
            Logger.getLogger(ManejadorCodigos.class.getName()).log(Level.SEVERE, null, ex);
        }
        return al;
    }
    public ISArmamento getInformacionSumariaArmamento(int id){
        ISArmamento g=null;
        try {
            Statement s= connection.createStatement();
            String sql="Select * from destinoArmamento where id="+id;
            ResultSet rs= s.executeQuery(sql);
            ManejadorPersonal mp= new ManejadorPersonal();
            if (rs.next()){
                g= new ISArmamento(rs.getInt("id"),rs.getInt("idArmamento"),rs.getDate("fecha"),rs.getString("informacionSumaria"),mp.getPersonalBasico(rs.getInt("idOficialArmamento")));
            }
            mp.CerrarConexionManejador();
        } catch (SQLException ex) {
            Logger.getLogger(ManejadorCodigos.class.getName()).log(Level.SEVERE, null, ex);
        }
        return g;
    }
    public int agregarISArmamento(int idArmamento, String fecha, String informacionSumaria, int idOficialArmamento){
        int clave = -1;
        try {
            Statement s= connection.createStatement();
            String sql="insert into ISarmamento(idArmamento, fecha, informacionSumaria,idOficialArmamento) values("+idArmamento+",'"+fecha+"','"+informacionSumaria+"',"+idOficialArmamento+")";
            int result= s.executeUpdate(sql, Statement.RETURN_GENERATED_KEYS);
            if(result>0){
                ResultSet rs=s.getGeneratedKeys(); //obtengo las ultimas llaves generadas
                while(rs.next()){ 
                    clave=rs.getInt(1);
                }
            }
        } catch (SQLException ex) {
            Logger.getLogger(ManejadorCodigos.class.getName()).log(Level.SEVERE, null, ex);
        }
        return clave;
    }
    public boolean eliminarISArmamento(int id){
        try {
            
            String sql="delete from ISarmamento where id="+id; 
            Statement s= connection.createStatement();
             int i= s.executeUpdate(sql);
            return (i>0);
            
        } catch (SQLException ex) {
            Logger.getLogger(ManejadorCodigos.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }
    
    public ArrayList<Armamento> getArmamentos(){
        ArrayList<Armamento> al= new ArrayList();
        try {
            Statement s= connection.createStatement();
            String sql="Select * from Armamento";
            ResultSet rs= s.executeQuery(sql);
            Armamento g;
            ManejadorPersonal mp= new ManejadorPersonal();
            ManejadorCodigos mc = new ManejadorCodigos();
            while (rs.next()){
                g= new Armamento(rs.getInt("numero"),mc.getModeloArmamento(rs.getInt("idModeloArmamento")),rs.getDate("fechaAlta"),rs.getDate("fechaBaja"),mc.getDestino(rs.getInt("idDestino")),mc.getMunicion(rs.getString("calibre")),mp.getPersonalBasico(rs.getInt("idEntregado")));
                al.add(g);
            }
            mp.CerrarConexionManejador();
            mc.CerrarConexionManejador();
        } catch (SQLException ex) {
            Logger.getLogger(ManejadorCodigos.class.getName()).log(Level.SEVERE, null, ex);
        }
        return al;
    }
    public Armamento getArmamento(int numero){
        Armamento g=null;
        try {
            Statement s= connection.createStatement();
            String sql="Select * from Armamento where numero="+numero;
            ResultSet rs= s.executeQuery(sql);
            ManejadorPersonal mp= new ManejadorPersonal();
            ManejadorCodigos mc = new ManejadorCodigos();
            if (rs.next()){
                g= new Armamento(rs.getInt("numero"),mc.getModeloArmamento(rs.getInt("idModeloArmamento")),rs.getDate("fechaAlta"),rs.getDate("fechaBaja"),mc.getDestino(rs.getInt("idDestino")),mc.getMunicion(rs.getString("calibre")),mp.getPersonalBasico(rs.getInt("idEntregado")));
            }
            mc.CerrarConexionManejador();
            mp.CerrarConexionManejador();
        } catch (SQLException ex) {
            Logger.getLogger(ManejadorCodigos.class.getName()).log(Level.SEVERE, null, ex);
        }
        return g;
    }
    public boolean agregarArmamento(int numero, int idModeloArmamento, String fechaAlta, String fechaBaja, int idDestino, int calibre, int idEntregado){
        try {
            Statement s= connection.createStatement();
            String sql="insert into armamento(numero, idModeloArmamento, fechaAlta, fechaBaja, idDestino, calibre,idEntregado) values("+numero+","+idModeloArmamento+",'"+fechaAlta+"','"+fechaBaja+"',"+idDestino+","+calibre+","+idEntregado+")";
            int i= s.executeUpdate(sql);
            if (i>0){
                return true;
            }            
        } catch (SQLException ex) {
            Logger.getLogger(ManejadorCodigos.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }
    public boolean eliminarArmamento(int numero){
        try {
            
            String sql="delete from armamento where numero="+numero; //trigger creado para eliminar las observaciones e infromacion sumaria
            Statement s= connection.createStatement();
             int i= s.executeUpdate(sql);
            return (i>0);
            
        } catch (SQLException ex) {
            Logger.getLogger(ManejadorCodigos.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }
    public boolean modificarArmamento(int numero, int idModeloArmamento, String fechaAlta, String fechaBaja, int idDestino, int calibre, int idEntregado){
        try {
            Statement s= connection.createStatement();
            String sql="update grado set idModeloArmamento="+idModeloArmamento+",fechaAlta='"+fechaAlta+"',fechaBaja='"+fechaBaja+"',idDestino="+idDestino+",calibre="+calibre+",idEntregado="+idEntregado+" where numero="+numero;
            int i= s.executeUpdate(sql);
            return (i>0);
        } catch (SQLException ex) {
            Logger.getLogger(ManejadorCodigos.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }
    
    public ArrayList<ObservacionArmamento> getObservacionesArmamento(int idArmamento){
        ArrayList<ObservacionArmamento> al= new ArrayList();
        try {
            Statement s= connection.createStatement();
            String sql="Select * from ObservacionArmamento where idArmamento="+idArmamento;
            ResultSet rs= s.executeQuery(sql);
            ObservacionArmamento g;
            ManejadorPersonal mp= new ManejadorPersonal();
            while (rs.next()){
                g= new ObservacionArmamento(rs.getInt("if"),rs.getDate("fecha"),rs.getString("observaciones"),mp.getPersonalBasico(rs.getInt("idEscribiento")),rs.getInt("idArmamento"));
                al.add(g);
            }
            mp.CerrarConexionManejador();
        } catch (SQLException ex) {
            Logger.getLogger(ManejadorCodigos.class.getName()).log(Level.SEVERE, null, ex);
        }
        return al;
    }
    public ObservacionArmamento getObservacionArmamento(int id){
        ObservacionArmamento g=null;
        try {
            Statement s= connection.createStatement();
            String sql="Select * from ObservacionArmamento where id="+id;
            ResultSet rs= s.executeQuery(sql);
            ManejadorPersonal mp= new ManejadorPersonal();
            if (rs.next()){
                g= new ObservacionArmamento(rs.getInt("if"),rs.getDate("fecha"),rs.getString("observaciones"),mp.getPersonalBasico(rs.getInt("idEscribiento")),rs.getInt("idArmamento"));
            }
            mp.CerrarConexionManejador();
        } catch (SQLException ex) {
            Logger.getLogger(ManejadorCodigos.class.getName()).log(Level.SEVERE, null, ex);
        }
        return g;
    }
    public boolean agregarObservacionArmamento(int idArmamento,String fecha,String observaciones, int idEscribiente){
        try {
            Statement s= connection.createStatement();
            String sql="insert into ObservacionArmamento(idArmamento, fecha, observaciones, idEscribiente) values("+idArmamento+",'"+fecha+"','"+observaciones+"',"+idEscribiente+")";
            int i= s.executeUpdate(sql);
            if (i>0){
                return true;
            }            
        } catch (SQLException ex) {
            Logger.getLogger(ManejadorCodigos.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }
    public boolean eliminarObservacionArmamento(int id){
        try {
            
            String sql="delete from ObservacionArmamento where id="+id; 
            Statement s= connection.createStatement();
             int i= s.executeUpdate(sql);
            return (i>0);
            
        } catch (SQLException ex) {
            Logger.getLogger(ManejadorCodigos.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }
    public boolean modificarObservacionArmamento(int id, int idArmamento,String fecha,String observaciones, int idEscribiente){
        try {
            Statement s= connection.createStatement();
            String sql="update grado set idArmamento="+idArmamento+",fecha='"+fecha+"',observaciones='"+observaciones+"',idEscribiente="+idEscribiente+" where id="+id;
            int i= s.executeUpdate(sql);
            return (i>0);
        } catch (SQLException ex) {
            Logger.getLogger(ManejadorCodigos.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }

}
