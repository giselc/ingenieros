/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Manejadores;

import Classes.Armamento;
import Classes.ConexionBD;
import Classes.ISArmamento;
import Classes.Lote;
import Classes.LoteMunicion;
import Classes.ObservacionArmamento;
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
            String sql="Select * from ISArmamento where idArmamento="+idArmamento+" order by fecha desc";
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
            String sql="Select * from ISArmamento where id="+id;
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
            String sql="Select * from Armamento order by numero asc";
            ResultSet rs= s.executeQuery(sql);
            Armamento g;
            ManejadorPersonal mp= new ManejadorPersonal();
            ManejadorCodigos mc = new ManejadorCodigos();
            while (rs.next()){
                g= new Armamento(rs.getInt("numero"),mc.getModeloArmamento(rs.getInt("idModelo")),rs.getDate("fechaAlta"),rs.getDate("fechaBaja"),mc.getDestino(rs.getInt("idDestino")),mc.getMunicion(rs.getString("calibre")),mp.getPersonalBasico(rs.getInt("idEntregado")));
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
                g= new Armamento(rs.getInt("numero"),mc.getModeloArmamento(rs.getInt("idModelo")),rs.getDate("fechaAlta"),rs.getDate("fechaBaja"),mc.getDestino(rs.getInt("idDestino")),mc.getMunicion(rs.getString("calibre")),mp.getPersonalBasico(rs.getInt("idEntregado")));
            }
            mc.CerrarConexionManejador();
            mp.CerrarConexionManejador();
        } catch (SQLException ex) {
            Logger.getLogger(ManejadorCodigos.class.getName()).log(Level.SEVERE, null, ex);
        }
        return g;
    }
    public boolean agregarArmamento(int numero, int idModeloArmamento, String fechaAlta, String fechaBaja, int idDestino, String calibre, int idEntregado){
        try {
            Statement s= connection.createStatement();
            if(fechaAlta.equals("")){
                fechaAlta="NULL";
            }
            else{
                fechaAlta="'"+fechaAlta+"'";
            }
            if(fechaBaja.equals("")){
                fechaBaja="NULL";
            }
            else{
                fechaBaja="'"+fechaBaja+"'";
            }
            String sql="insert into armamento(numero, idModelo, fechaAlta, fechaBaja, idDestino, calibre,idEntregado) values("+numero+","+idModeloArmamento+","+fechaAlta+","+fechaBaja+","+idDestino+",'"+calibre+"',"+idEntregado+")";
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
    public boolean modificarArmamento(int numero, int idModeloArmamento, String fechaAlta, String fechaBaja, int idDestino, String calibre, int idEntregado){
        try {
            Statement s= connection.createStatement();
            String sql="update grado set idModelo="+idModeloArmamento+",fechaAlta='"+fechaAlta+"',fechaBaja='"+fechaBaja+"',idDestino="+idDestino+",calibre='"+calibre+"',idEntregado="+idEntregado+" where numero="+numero;
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
            String sql="Select * from ObservacionArmamento where idArmamento="+idArmamento+" order by fecha desc";
            ResultSet rs= s.executeQuery(sql);
            ObservacionArmamento g;
            ManejadorPersonal mp= new ManejadorPersonal();
            while (rs.next()){
                g= new ObservacionArmamento(rs.getInt("id"),rs.getDate("fecha"),rs.getString("observaciones"),mp.getPersonalBasico(rs.getInt("idEscribiente")),rs.getInt("idArmamento"));
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
                g= new ObservacionArmamento(rs.getInt("id"),rs.getDate("fecha"),rs.getString("observaciones"),mp.getPersonalBasico(rs.getInt("idEscribiente")),rs.getInt("idArmamento"));
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
            String sql="update observacionArmamento set idArmamento="+idArmamento+",fecha='"+fecha+"',observaciones='"+observaciones+"',idEscribiente="+idEscribiente+" where id="+id;
            int i= s.executeUpdate(sql);
            return (i>0);
        } catch (SQLException ex) {
            Logger.getLogger(ManejadorCodigos.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }

    public ArrayList<Lote> getLotes(){
        ArrayList<Lote> al= new ArrayList();
        try {
            Statement s= connection.createStatement();
            String sql="Select * from lote order by id asc";
            ResultSet rs= s.executeQuery(sql);
            Lote g;
            ManejadorPersonal mp= new ManejadorPersonal();
            while (rs.next()){
                g= new Lote(rs.getString("id"),rs.getDate("fechaVencimiento"));
                al.add(g);
            }
            mp.CerrarConexionManejador();
        } catch (SQLException ex) {
            Logger.getLogger(ManejadorCodigos.class.getName()).log(Level.SEVERE, null, ex);
        }
        return al;
    }
    public Lote getLote(String id){
        Lote g=null;
        try {
            Statement s= connection.createStatement();
            String sql="Select * from lote where id='"+id+"'";
            ResultSet rs= s.executeQuery(sql);
            if (rs.next()){
                g= new Lote(rs.getString("id"),rs.getDate("fechaVencimiento"));
            }
        } catch (SQLException ex) {
            Logger.getLogger(ManejadorCodigos.class.getName()).log(Level.SEVERE, null, ex);
        }
        return g;
    }
    public boolean agregarLote(String id,String fechaVencimiento){
        try {
            Statement s= connection.createStatement();
            String sql="insert into lote(id, fechavencimiento) values('"+id+"','"+fechaVencimiento+"')";
            int i= s.executeUpdate(sql);
            if (i>0){
                return true;
            }            
        } catch (SQLException ex) {
            Logger.getLogger(ManejadorCodigos.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }
    public boolean eliminarLote(String id){
        try {
            
            String sql="delete from lote where id='"+id+"'"; //TRIGGER QUE ELIMINA LOS LOTE-MUNICION DE ESTE LOTE
            Statement s= connection.createStatement();
             int i= s.executeUpdate(sql);
            return (i>0);
            
        } catch (SQLException ex) {
            Logger.getLogger(ManejadorCodigos.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }
    
    public ArrayList<LoteMunicion> getMunicionesLote(String idLote){
        ArrayList<LoteMunicion> al= new ArrayList();
        try {
            Statement s= connection.createStatement();
            String sql="Select * from loteMunicion where idLote='"+idLote+"' order by idMunicion asc";
            ResultSet rs= s.executeQuery(sql);
            LoteMunicion g;
            ManejadorCodigos mc= new ManejadorCodigos();
            while (rs.next()){
                g= new LoteMunicion(rs.getInt("id"),idLote,mc.getMunicion(rs.getString("idMunicion")),rs.getInt("cantidad"),rs.getInt("cantMunicionViva"),rs.getInt("vainas"));
                al.add(g);
            }
            mc.CerrarConexionManejador();
        } catch (SQLException ex) {
            Logger.getLogger(ManejadorCodigos.class.getName()).log(Level.SEVERE, null, ex);
        }
        return al;
    }
    public LoteMunicion getMunicionLote(int id){
        LoteMunicion g=null;
        try {
            Statement s= connection.createStatement();
            String sql="Select * from lotemunicion where id="+id;
            ResultSet rs= s.executeQuery(sql);
            ManejadorCodigos mc= new ManejadorCodigos();
            if (rs.next()){
                g= new LoteMunicion(rs.getInt("id"),rs.getString("idLote"),mc.getMunicion(rs.getString("idMunicion")),rs.getInt("cantidad"),rs.getInt("cantMunicionViva"),rs.getInt("vainas"));
            }
            mc.CerrarConexionManejador();
        } catch (SQLException ex) {
            Logger.getLogger(ManejadorCodigos.class.getName()).log(Level.SEVERE, null, ex);
        }
        return g;
    }
    public boolean agregarLoteMunicion(String idLote, String idMunicion, int cantidad, int vivas, int vainas){
        try {
            Statement s= connection.createStatement();
            String sql="insert into lotemunicion(idLote, idMunicion, cantidad, cantMunicionViva, vainas) values('"+idLote+"','"+idMunicion+"',"+cantidad+","+vivas+","+vainas+")";
            int i= s.executeUpdate(sql);
            if (i>0){
                return true;
            }            
        } catch (SQLException ex) {
            Logger.getLogger(ManejadorCodigos.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }
    public boolean eliminarLoteMunicion(int id){
        try {
            
            String sql="delete from lotemunicion where id="+id; 
            Statement s= connection.createStatement();
             int i= s.executeUpdate(sql);
            return (i>0);
            
        } catch (SQLException ex) {
            Logger.getLogger(ManejadorCodigos.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }
    public boolean modificarLoteMunicion(int id, int cantidad, int vivas, int vainas){
        try {
            Statement s= connection.createStatement();
            String sql="update lotemunicion set cantidad="+cantidad+",cantMunicionViva="+vivas+",vainas="+vainas+" where id="+id;
            int i= s.executeUpdate(sql);
            return (i>0);
        } catch (SQLException ex) {
            Logger.getLogger(ManejadorCodigos.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }
    
    public void imprimirArmamentos(PrintWriter out){
        ArrayList<Armamento> aa= this.getArmamentos();
        String datos="<h1 align='center'>Armamentos del sistema</h1><table cellspacing=0px border=1px  style=\"width: 100%;\" align='center'>" +
"                <tr style='background-color:#ffcc66'>" +
"                            <td style='width: 10%' align='center'><h3 style='margin:2%;'>Numero</h3></td>" +
"                            <td style='width: 20%' align='center'><h3 style='margin:2%;'>Modelo</h3></td>" +
"                            <td style='width: 10%' align='center'><h3 style='margin:2%;'>Fecha Alta</h3></td>" +
"                            <td style='width: 10%' align='center'><h3 style='margin:2%;'>Fecha Baja</h3></td>" +
"                            <td style='width: 10%' align='center'><h3 style='margin:2%;'>Destino</h3></td>" +
"                            <td style='width: 10%' align='center'><h3 style='margin:2%;'>Calibre</h3></td>" +
"                            <td style='width: 10%' align='center'><h3 style='margin:2%;'>Entregado</h3></td>" +
"                       </tr>" ;
                int i=0;
                String color;
                for (Armamento u1: aa){
                        if ((i%2)==0){
                            color=" #ccccff";
                        }
                        else{
                            color=" #ffff99";
                        }
                        i++;
datos+="                    <tr style='background-color:"+color+"'>" +
"                    <td style='width: 10%' align='center'>"+u1.getNumero()+"</td>" +
"                    <td style='width: 20%' align='center'>"+u1.getModelo().getDescripcion()+"</td>" ;
                    if(u1.getFechaAlta()!=null){
datos+="                        <td style='width: 10%' align='center'>"+u1.getFechaAlta()+"</td>" ;
                    }
                    else{
datos+="                        <td style='width: 10%' align='center'></td>" ;
                    }
                   if(u1.getFechaBaja()!=null){
datos+="                        <td style='width: 10%' align='center'>"+u1.getFechaBaja()+"</td>" ;
                    }
                    else{
datos+="                        <td style='width: 10%' align='center'></td>" ;
                    }
datos+="                    <td style='width: 10%' align='center'>"+u1.getDestino().getDescripcion()+"</td>" +
"                    <td style='width: 10%' align='center'>"+u1.getCalibre().getId()+"</td>" ;
                    if(u1.getEntregado()!=null){
datos+="                        <td style='width: 10%' align='center'>"+ManejadorPersonal.obtenerNombreCompleto(u1.getEntregado())+"</td>" ;
                    }
                    else{
datos+="                        <td style='width: 10%' align='center'>NO ENTREGADO</td>" ;
                    }
datos+="            </tr>" ;
                }
datos+="    </table>";
     out.print(datos);
    }
   public void imprimirArmamento(int idArmamento, boolean ficha, boolean historial,PrintWriter out){
       String datos="<h1 align='center'> ARMAMENTO: "+idArmamento+"</h1>";
       if(ficha){
           Armamento a = this.getArmamento(idArmamento);
           datos+="<h3 align='center'></h3><table cellspacing=0px border=1px width='50%' align='center' style=\"text-align: left\">\n" +
"        <tr>\n" +
"            <td width='30%'>\n" +
"                <p><b>N&uacute;mero:</b></p>\n" +
"            </td>\n" +
"            <td width='70%'>\n" +
                   a.getNumero()+
"            </td>\n" +
"        </tr>\n" +
"        <tr>\n" +
"            <td>\n" +
"                <p><b>Modelo:</b></p>\n" +
"            </td>\n" +
"            <td>\n" +
                   a.getModelo().getDescripcion()+
"            </td>\n" +
"        </tr>\n" +
"        <tr>\n" +
"            <td>\n" +
"                <p><b>Fecha Alta:</b></p>\n" +
"            </td>\n" +
"            <td>\n" ;
           if(a.getFechaAlta()!=null){
               datos+=a.getFechaAlta();
           }
datos+="            </td>\n" +
"        </tr>\n" +
"        <tr>\n" +
"            <td>\n" +
"                <p><b>Fecha Baja:</b></p>\n" +
"            </td>\n" +
"            <td>\n" ;
           if(a.getFechaBaja()!=null){
               datos+=a.getFechaBaja();
           }
datos+="            </td>\n" +
"        </tr>\n" +
"        <tr>\n" +
"            <td>\n" +
"                <p><b> Destino:</b></p>\n" +
"            </td>\n" +
"            <td>\n" +
        a.getDestino().getDescripcion()+
"            </td>\n" +
"        </tr>\n" +
"        <tr>\n" +
"            <td>\n" +
"                <p><b> Calibre:</b></p>\n" +
"            </td>\n" +
"            <td>\n" +
        a.getCalibre().getId()+
"            </td>\n" +
"        </tr>\n" +
"        <tr>\n" +
"            <td>\n" +
"                <p><b> Personal entregado:</b></p>\n" +
"            </td>\n" +
"            <td>\n" ;
        if(a.getEntregado()!=null){
            datos+= ManejadorPersonal.obtenerNombreCompleto(a.getEntregado());
        }
datos+="            </td>\n" +
"        </tr>\n" +
"    </table>";
datos+="<h1 style='page-break-after:always' > </h1>";
       }
       if(historial){
           ArrayList<ObservacionArmamento> ao = this.getObservacionesArmamento(idArmamento);
           datos+="<h3 align='center'>HISTORIAL</h3><table style=\"width: 100%;\" align='center'>\n" +
"                <tr style='background-color:#ffcc66'>" +
"                            <td style='width: 10%' align='center'><h3 style='margin:2%;'>Fecha</h3></td>" +
"                            <td style='width: 20%' align='center'><h3 style='margin:2%;'>Observaciones</h3></td>" +
"                            <td style='width: 10%' align='center'><h3 style='margin:2%;'>Escribiente</h3></td>" +
"                       </tr>" ;
                int i=0;
                String color;
                for (ObservacionArmamento u1: ao){
                        if ((i%2)==0){
                            color=" #ccccff";
                        }
                        else{
                            color=" #ffff99";
                        }
                        i++;
datos+="                    <tr style='background-color:"+color+"'>" +
"                   <td style='width: 10%' align='center'>"+u1.getFecha()+"</td>" +
"                    <td style='width: 20%' align='center'>"+u1.getObservaciones()+"</td>" ;
                    if(u1.getEscribiente()!=null){
datos+="                        <td style='width: 10%' align='center'>"+ManejadorPersonal.obtenerNombreCompleto(u1.getEscribiente())+"</td>";
                    }
                    else{
datos+="                       <td style='width: 10%' align='center'></td>";
                    }
datos+="            </tr>" ;
                };
datos+="    </table>";
       }
       out.print(datos);
   }

    public void imprimirLotes(PrintWriter out) {
        ArrayList<Lote> al= this.getLotes();
        
        String datos = "<h1>Lotes ingresados al sistema:</h1><table cellspacing=0px border=1px style=\"width: 100%;\" align='center'>" +
"                <tr style='background-color:#ffcc66'>" +
"                            <td style='width: 30%' align='center'><h3 style='margin:2%;'>Id</h3></td>" +
"                            <td style='width: 20%' align='center'><h3 style='margin:2%;'>Fecha</h3></td>" +
"                </tr>" ;
                int i=0;
                String color;
                for (Lote u1: al){
                        if ((i%2)==0){
                            color=" #ccccff";
                        }
                        else{
                            color=" #ffff99";
                        }
                        i++;

datos+="         <tr style='background-color:"+color+"'>" +
"                    <td style='width: 30%' align='center'>"+u1.getId()+"</td>" +
"                    <td style='width: 20%' align='center'>"+u1.getFechaVencimiento()+"</td>" +
"                </tr>";
                }
datos+="    </table>";
out.print(datos);
    }

    public void imprimirLote(String id, PrintWriter out) {
        Lote v= this.getLote(id);
        ArrayList<LoteMunicion> lote = this.getMunicionesLote(id);
        String datos="<h1 align=\"center\">Lote: "+ v.getId() +"/ Vencimiento: "+ v.getFechaVencimiento() +"</h1>\n" +
"<h3>Municiones:</h3>"+
"    <table cellspacing=0px border=1px style=\"width: 100%;\" align='center'>" +
"                <tr style='background-color:#ffcc66'>" +
"                            <td style='width: 10%' align='center'><h3 style='margin:2%;'>Munición</h3></td>" +
"                            <td style='width: 20%' align='center'><h3 style='margin:2%;'>Cantidad</h3></td>" +
"                            <td style='width: 10%' align='center'><h3 style='margin:2%;'>Vivas</h3></td>" +
"                            <td style='width: 10%' align='center'><h3 style='margin:2%;'>Vainas</h3></td>" +
"                       </tr>" ;
                int i=0;
                String color;
                for (LoteMunicion u1: lote){
                        if ((i%2)==0){
                            color=" #ccccff";
                        }
                        else{
                            color=" #ffff99";
                        }
                        i++;

datos+="                    <tr style='background-color:"+color+"'>" +
"                    <td style='width: 10%' align='center'>"+u1.getMunicion().getId()+"</td>" +
"                    <td style='width: 20%' align='center'>"+u1.getCantidad()+"</td>" +
"                   <td style='width: 10%' align='center'>"+u1.getCantMunicionViva()+"</td>" +
"                    <td style='width: 10%' align='center'>"+u1.getVainas()+"</td>" +
"                    </tr>" ;
                }
datos+="    </table>";
        out.print(datos);
    }
    
}
