/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Manejadores;

import Classes.AlertaMantenimiento;
import Classes.AlertaVehiculo;
import Classes.ConexionBD;
import Classes.ObservacionVehiculo;
import Classes.RecordAlertasMantenimiento;
import Classes.Vehiculo;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.json.Json;
import javax.json.JsonArrayBuilder;
import javax.json.JsonObjectBuilder;
import static javax.management.Query.div;

/**
 *
 * @author Gisel
 */
public class ManejadorVehiculo {
    private Connection connection;

    public ManejadorVehiculo() {
        connection = ConexionBD.GetConnection();
    }
    public void CerrarConexionManejador(){
        try {
            connection.close();
        } catch (SQLException ex) {
            Logger.getLogger(ManejadorClases.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public AlertaVehiculo getAlertaVehiculo(int id){
        AlertaVehiculo u= null;
        try {
            String sql= "Select * from alertasVehiculo where id="+id;
            Statement s= connection.createStatement();
            ResultSet rs = s.executeQuery(sql);
            ManejadorCodigos mc = new ManejadorCodigos();
            if(rs.next()){
                u=new AlertaVehiculo(rs.getInt("id"), rs.getString("idVehiculo"), mc.getTipoMantenimientoVehiculo(rs.getInt("idTipoMantenimiento")),rs.getInt("horaskilometros"), rs.getInt("horasKilometrosAlerta"));
            }
            mc.CerrarConexionManejador();
        } catch (SQLException ex) {
            Logger.getLogger(ManejadorClases.class.getName()).log(Level.SEVERE, null, ex);
        }
        return u;
    }
    public ArrayList<AlertaVehiculo> getAlertasVehiculo(String matriculaUY){
        ArrayList<AlertaVehiculo> al= new ArrayList<>();
        try {
            String sql= "Select * from alertasVehiculo where idVehiculo='"+matriculaUY+"'";
            Statement s= connection.createStatement();
            ResultSet rs = s.executeQuery(sql);
            ManejadorCodigos mc = new ManejadorCodigos();
            AlertaVehiculo u;
            while(rs.next()){
                u=new AlertaVehiculo(rs.getInt("id"), rs.getString("idVehiculo"), mc.getTipoMantenimientoVehiculo(rs.getInt("idTipoMantenimiento")),rs.getInt("horaskilometros"),rs.getInt("horaskilometrosAlerta"));
                al.add(u);
            }
            mc.CerrarConexionManejador();
        } catch (SQLException ex) {
            Logger.getLogger(ManejadorClases.class.getName()).log(Level.SEVERE, null, ex);
        }
        return al;
    }
    public boolean agregarAlertaVehiculo(String idVehiculo, int idTipoMantenimiento, int horasKilometros, int horasKilometrosAlerta){
        try {
            //java.util.Date date = new java.util.Date();
            //java.sql.Date fecha= new Date( date.getTime());
            String sql="insert into alertasVehiculo(idVehiculo, idTipoMantenimiento, horasKilometros, horasKilometrosAlerta) values(?,?,?,?)";
            PreparedStatement s= connection.prepareStatement(sql);
            s.setString(1, idVehiculo);
            s.setInt(2, idTipoMantenimiento);
            s.setInt(3, horasKilometros);
            s.setInt(4, horasKilometrosAlerta);
            //s.setDate(4, fecha);
            int i= s.executeUpdate();
            if (i>0){
                return true;
            }            
        } catch (SQLException ex) {
            Logger.getLogger(ManejadorCodigos.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }
    public boolean eliminarAlertaVehiculo(int id){
        try {
            Statement s = connection.createStatement(); //TRIGGER creado en base de datos para eliminar dependecias.
            String sql="delete from alertasVehiculo where id="+id;
            int i = s.executeUpdate(sql);
            return i>0;
        } catch (SQLException ex) {
            Logger.getLogger(ManejadorCodigos.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }
    
    public ArrayList<AlertaMantenimiento> getAlertasMantenimiento(){
        ArrayList<AlertaMantenimiento> al= new ArrayList<>();
        try {
            String sql= "Select * from alertasMantenimiento";
            Statement s= connection.createStatement();
            ResultSet rs = s.executeQuery(sql);
            AlertaMantenimiento u;
            while(rs.next()){
                u=new AlertaMantenimiento(rs.getInt("id"), this.getAlertaVehiculo(rs.getInt("idAlertaVehiculo")),rs.getDate("fecha"), rs.getInt("horasKilometrosAlcanzados"));
                al.add(u);
            }
        } catch (SQLException ex) {
            Logger.getLogger(ManejadorClases.class.getName()).log(Level.SEVERE, null, ex);
        }
        return al;
    }
    public boolean agregarAlertaMantenimiento(int idAlertaVehiculo, int horasKilometrosAlcanzados){
        try {
            java.util.Date date = new java.util.Date();
            java.sql.Date fecha= new Date( date.getTime());
            String sql="insert into alertasMantenimiento(idAlertaVehiculo, fecha,horasKilometrosAlcanzados) values(?,?,?)";
            PreparedStatement s= connection.prepareStatement(sql);
            s.setInt(1, idAlertaVehiculo);
            s.setDate(2, fecha);
            s.setInt(3, horasKilometrosAlcanzados);
            int i= s.executeUpdate();
            if (i>0){
                return true;
            }            
        } catch (SQLException ex) {
            Logger.getLogger(ManejadorCodigos.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }
    public boolean eliminarAlertaMantenimiento(int id){
        try {
            Statement s = connection.createStatement();
            String sql="delete from alertasMantenimiento where id="+id;
            int i= s.executeUpdate(sql);
            return (i>0);
            
        } catch (SQLException ex) {
            Logger.getLogger(ManejadorCodigos.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }
    public boolean eliminarAlertasMantenimiento(int idAlertaVehiculo){
        
        try {
            Statement s = connection.createStatement();
            String sql="delete from alertasMantenimiento where idAlertaVehiculo="+idAlertaVehiculo;
            int i= s.executeUpdate(sql);
            return (i>0);
            
        } catch (SQLException ex) {
            Logger.getLogger(ManejadorCodigos.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    
    }
    public ArrayList<String> actualizarAlertasMantenimiento(String idVehiculo){
        ArrayList<AlertaVehiculo> alertas = this.getAlertasVehiculo(idVehiculo);
        ArrayList<String> as= new ArrayList<>();
        int suma = -1;
        for(AlertaVehiculo alerta: alertas){
            suma = this.obtenerSumaHrKmUltimoMantenimiento(idVehiculo, alerta.getTipoMantenimiento().getId());
            if(suma>0){
                if(suma>=alerta.getHorasKilometros()){ //generarAlertaMantenimiento
                    this.eliminarAlertasMantenimiento(alerta.getId());
                    this.agregarAlertaMantenimiento(alerta.getId(), alerta.getHorasKilometros()-suma);
                    as.add("Tipo Mantenimiento: "+alerta.getTipoMantenimiento().getDescripcion()+"- Kilometros Faltantes: "+(alerta.getHorasKilometros()-suma));
                
                }
                else{
                    if(suma+alerta.getHorasKilometrosAlerta()>=alerta.getHorasKilometros()){
                        this.eliminarAlertasMantenimiento(alerta.getId());
                        this.agregarAlertaMantenimiento(alerta.getId(), alerta.getHorasKilometros()-suma);
                        as.add("Tipo Mantenimiento: "+alerta.getTipoMantenimiento().getDescripcion()+"- Kilometros Faltantes: "+(alerta.getHorasKilometros()-suma));
                    }
                    else{
                        this.eliminarAlertasMantenimiento(alerta.getId());
                    }
                }
            }
            else{
                if(suma==0){
                    this.eliminarAlertasMantenimiento(alerta.getId());
                }
            }
        }
        return as;
    }
    
    public ArrayList<Vehiculo> getVehiculos(){
        ArrayList<Vehiculo> al= new ArrayList<>();
        try {
            String sql= "Select * from vehiculos order by matriculaUY asc";
            Statement s= connection.createStatement();
            ResultSet rs = s.executeQuery(sql);
            Vehiculo u;
            while(rs.next()){
                u=new Vehiculo(rs.getString("matriculaONU"), rs.getString("matriculaUY"),rs.getString("marca"), rs.getString("modelo"),rs.getInt("nroChasis"),rs.getInt("nroMotor"),rs.getDouble("consumo"),rs.getInt("tipo"),rs.getBoolean("horas"),rs.getInt("horasKilometrosInicial"),rs.getInt("horasKilometrosTotal"));
                al.add(u);
            }
        } catch (SQLException ex) {
            Logger.getLogger(ManejadorClases.class.getName()).log(Level.SEVERE, null, ex);
        }
        return al;
    }
    public Vehiculo getVehiculo(String matriculaUY){
        Vehiculo v= null;
        try {
            String sql= "Select * from vehiculos where matriculaUY='"+matriculaUY+"'";
            Statement s= connection.createStatement();
            ResultSet rs = s.executeQuery(sql);
            if(rs.next()){
                v=new Vehiculo(rs.getString("matriculaONU"), rs.getString("matriculaUY"),rs.getString("marca"), rs.getString("modelo"),rs.getInt("nroChasis"),rs.getInt("nroMotor"),rs.getDouble("consumo"),rs.getInt("tipo"),rs.getBoolean("horas"),rs.getInt("horasKilometrosInicial"),rs.getInt("horasKilometrosTotal"));
            }
        } catch (SQLException ex) {
            Logger.getLogger(ManejadorClases.class.getName()).log(Level.SEVERE, null, ex);
        }
        return v;
    }
    public boolean agregarVehiculo(String matriculaUY, String matriculaONU, String marca, String modelo, int nroMotor, int nroChasis, double consumo, int tipo, int horaskilometrosinicial){
        try {
            String sql= "insert into vehiculos (matriculaUY, matriculaONU, marca,modelo, nroMotor,nroChasis,consumo,tipo,horaskilometrosInicial,horaskilometrosTotal) values(?,?,?,?,?,?,?,?,?)";
            PreparedStatement s= connection.prepareStatement(sql);
            s.setString(1,matriculaUY);
            s.setString(2, matriculaONU);
            s.setString(3, marca);
            s.setString(4, modelo);
            s.setInt(5, nroMotor);
            s.setInt(6, nroChasis);
            s.setDouble(7,consumo);
            s.setInt(8, tipo);
            s.setInt(9, horaskilometrosinicial);
            s.setInt(10, horaskilometrosinicial);
            int result = s.executeUpdate();
            if(result>0){
                return true;
            }
        } catch (Exception ex) {
            Logger.getLogger(ManejadorClases.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }
    public boolean modificarVehiculo(String matriculaUY, String marca, String modelo, int nroMotor, int nroChasis, double consumo, int tipo, int horaskilometrosinicial, int horaskilometrosTotal){
        try {
                String sql= "update vehiculos set marca=?, modelo=?, nroMotor=?, nroChasis=?, consumo=? ,tipo=? ,horaskilometrosinicial=?,horaskilometrostotal=? where matriculaUY='"+matriculaUY+"'";
                PreparedStatement s= connection.prepareStatement(sql);
                s.setString(1, marca);
                s.setString(2, modelo);
                s.setInt(3, nroMotor);
                s.setInt(4, nroChasis);
                s.setDouble(5, consumo);
                s.setInt(6,tipo);
                s.setInt(7, horaskilometrosinicial);
                s.setInt(8, horaskilometrosTotal);
                int result = s.executeUpdate();
                if(result>0){
                    return true;
                }
            } catch (SQLException ex) {
                Logger.getLogger(ManejadorClases.class.getName()).log(Level.SEVERE, null, ex);
            }
        return false;
    }
    public boolean eliminarVehiculo(String matriculaUY){
        try {
            Statement s = connection.createStatement(); //TRIGGER creado en base de datos para eliminar dependecias.
            String sql="delete from Vehiculos where matriculaUY="+matriculaUY;
            int i = s.executeUpdate(sql);
            return i>0;
        } catch (SQLException ex) {
            Logger.getLogger(ManejadorCodigos.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }
    
    
    public ArrayList<ObservacionVehiculo> getVehiculoHistorial(String id){
        ArrayList<ObservacionVehiculo> al= new ArrayList<>();
        try {
            String sql= "Select * from historialvehiculos where idvehiculo='"+id+"' order by fecha desc, mantenimiento desc";
            Statement s= connection.createStatement();
            ResultSet rs = s.executeQuery(sql);
            ObservacionVehiculo u;
            ManejadorPersonal mp = new ManejadorPersonal();
            ManejadorCodigos mc = new ManejadorCodigos();
            while(rs.next()){
                if(rs.getBoolean("mantenimiento")){
                    u=new ObservacionVehiculo(rs.getInt("id"),rs.getString("idVehiculo"), rs.getString("informacionSumaria"),rs.getDate("fecha"), rs.getInt("horaskilometrosRealizados"),rs.getString("observaciones"),rs.getBoolean("mantenimiento"),mc.getTipoMantenimientoVehiculo(rs.getInt("idtipomantenimiento")),rs.getInt("horasKilometrosMarcados"),mp.getPersonalBasico(rs.getInt("idOperario")),mp.getPersonalBasico(rs.getInt("idEscribiente")));
                }
                else{
                    u=new ObservacionVehiculo(rs.getInt("id"),rs.getString("idVehiculo"), rs.getString("informacionSumaria"),rs.getDate("fecha"), rs.getInt("horaskilometrosRealizados"),rs.getString("observaciones"),rs.getBoolean("mantenimiento"),null,0,null,null);
                }
                al.add(u);
            }
            mc.CerrarConexionManejador();
            mp.CerrarConexionManejador();
        } catch (SQLException ex) {
            Logger.getLogger(ManejadorClases.class.getName()).log(Level.SEVERE, null, ex);
        }
        return al;
    }
    public ObservacionVehiculo getObservacionVehiculo(int id){
        ObservacionVehiculo u= null;
        try {
            String sql= "Select * from historialVehiculos where id="+id;
            Statement s= connection.createStatement();
            ResultSet rs = s.executeQuery(sql);
            ManejadorPersonal mp = new ManejadorPersonal();
            ManejadorCodigos mc = new ManejadorCodigos();
            if(rs.next()){
                if(rs.getBoolean("mantenimiento")){
                    u=new ObservacionVehiculo(rs.getInt("id"),rs.getString("idVehiculo"), rs.getString("informacionSumaria"),rs.getDate("fecha"), rs.getInt("horaskilometrosRealizados"),rs.getString("observaciones"),rs.getBoolean("mantenimiento"),mc.getTipoMantenimientoVehiculo(rs.getInt("idtipomantenimiento")),rs.getInt("horasKilometrosMarcados"),mp.getPersonalBasico(rs.getInt("idOperario")),mp.getPersonalBasico(rs.getInt("idEscribiente")));
                }
                else{
                    u=new ObservacionVehiculo(rs.getInt("id"),rs.getString("idVehiculo"), rs.getString("informacionSumaria"),rs.getDate("fecha"), rs.getInt("horaskilometrosRealizados"),rs.getString("observaciones"),rs.getBoolean("mantenimiento"),null,0,null,null);
                }
            }
            mc.CerrarConexionManejador();
            mp.CerrarConexionManejador();
        } catch (SQLException ex) {
            Logger.getLogger(ManejadorClases.class.getName()).log(Level.SEVERE, null, ex);
        }
        return u;
    }
    public RecordAlertasMantenimiento agregarObservacionVehiculo(String idVehiculo, String informacionSumaria, String fecha, int horaskilometrosRealizados, boolean mantenimiento, int idTipoMantenimiento, int horaskilometrosMarcados, String observaciones, int idOperario, int idEscribiente){
        try {
            //file guardado en servlet
            RecordAlertasMantenimiento ram= new RecordAlertasMantenimiento();
            String sql= "insert into historialVehiculos (idVehiculo, informacionSumaria, fecha,horasKilometrosRealizados, observaciones,idOperario,idEscribiente, mantenimiento, horasKilometrosMarcados, idTipoMantenimiento) values('"+idVehiculo+"','"+informacionSumaria+"','"+fecha+"',"+horaskilometrosRealizados+",'"+observaciones+"',"+idOperario+","+idEscribiente+","+mantenimiento+","+horaskilometrosMarcados+","+idTipoMantenimiento+")";
            Statement s= connection.createStatement();
            Vehiculo v = this.getVehiculo(idVehiculo);
            int result = s.executeUpdate(sql, Statement.RETURN_GENERATED_KEYS); //TRIGGER para actualzar horasKilometrosTotal en vehiculos
            if(result>0){
                ResultSet rs=s.getGeneratedKeys(); //obtengo las ultimas llaves generadas
                int clave = -1;
                while(rs.next()){ 
                    clave=rs.getInt(1);
                }
                ArrayList<String> as = this.actualizarAlertasMantenimiento(idVehiculo);
                ram.clave = clave;
                ram.alertasMantenimiento = as;
                return ram;
            }
        } catch (Exception ex) {
            Logger.getLogger(ManejadorClases.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
    public RecordAlertasMantenimiento modificarObservacionVehiculo(int id, String idVehiculo, String informacionSumaria, String fecha, int horaskilometrosRealizados, boolean mantenimiento, int idTipoMantenimiento, int horaskilometrosMarcados, String observaciones, int idOperario, int idEscribiente){
        try {
                String sql= "update historialVehiculos set informacionSumaria=?, fecha=?, horaskilometrosRealizados=?, mantenimiento=?, idTipoMantenimiento=? ,horaskilometrosMarcados=? ,observaciones=?,idOperario=?,idEscribiente=? where id="+id;
                PreparedStatement s= connection.prepareStatement(sql);
                s.setString(1, informacionSumaria);
                s.setString(2, fecha);
                s.setInt(3, horaskilometrosRealizados);
                s.setBoolean(4, mantenimiento);
                s.setInt(5, idTipoMantenimiento);
                s.setInt(6,horaskilometrosMarcados);
                s.setString(7, observaciones);
                s.setInt(8, idOperario);
                s.setInt(9, idEscribiente);
                int result = s.executeUpdate();
                if(result>0){
                    RecordAlertasMantenimiento ram= new RecordAlertasMantenimiento();
                    ram.alertasMantenimiento = this.actualizarAlertasMantenimiento(idVehiculo);
                    return ram;
                }
            } catch (SQLException ex) {
                Logger.getLogger(ManejadorClases.class.getName()).log(Level.SEVERE, null, ex);
            }
        return null;
    }
    private int obtenerSumaHrKmUltimoMantenimiento(String idVehiculo, int idTipoMantenimiento){
        
        String sql = "select * from historialVehiculos where idVehiculo='"+idVehiculo+"' and mantenimiento=1 and idTipoMantenimiento="+idTipoMantenimiento+" order by fecha desc";
        Statement s;
        try {
            s = connection.createStatement();
            ResultSet rs = s.executeQuery(sql);
            if(rs.next()){
                sql = "select sum(horasKilometrosRealizados) as sumas from historialVehiculos where idVehiculo='"+idVehiculo+"' and fecha>'"+rs.getString("fecha")+"'";
            }
            else{
                sql = "select sum(horasKilometrosRealizados) as sumas from historialVehiculos where idVehiculo='"+idVehiculo+"'";
            }
            rs = s.executeQuery(sql);
            if(rs.next()){
                return rs.getInt("sumas");
            }
        } catch (SQLException ex) {
            Logger.getLogger(ManejadorVehiculo.class.getName()).log(Level.SEVERE, null, ex);
        }
        return -1;
    }
    public RecordAlertasMantenimiento eliminarObservacionVehiculo(int id, String idVehiculo){
        RecordAlertasMantenimiento ram = null;
        try {
            Statement s = connection.createStatement();
            String sql="delete from historialVehiculos where id="+id; //trigger que modifica km alcanzados en vehiculos
            int i= s.executeUpdate(sql);
            if(i>0){
                ram= new RecordAlertasMantenimiento();
                ram.alertasMantenimiento = this.actualizarAlertasMantenimiento(idVehiculo);
            }
            return ram;
            
        } catch (SQLException ex) {
            Logger.getLogger(ManejadorCodigos.class.getName()).log(Level.SEVERE, null, ex);
        }
        return ram;
    }

    public void imprimirVehiculo(String idVehiculo, boolean historial, boolean ficha, PrintWriter out){
        
        Vehiculo v = this.getVehiculo(idVehiculo);
        String datos="<h1 align=\"center\"><u>Vehículo: ONU:"+v.getMatriculaONU()+"/UY:"+v.getMatriculaUY()+"</u></h1>" ;
        if(ficha){
            
datos+=" <h1>FICHA:</h1>   <table  width='70%' cellspacing=0px border=1px align='center' style=\"text-align: left\">\n" +
"        <tr>\n" +
"            <td>\n" +
"                <p><b>Matr&iacute;cula Uruguay:</b></p>\n" +
"            </td>\n" +
"            <td>\n" +
                v.getMatriculaUY()+
"            </td>\n" +
"        </tr>\n" +
"        <tr>\n" +
"            <td>\n" +
"                <p><b>Matr&iacute;cula ONU:</b></p>\n" +
"            </td>\n" +
"            <td>\n" +
                    v.getMatriculaONU()+
"            </td>\n" +
"        </tr>\n" +
"        <tr>\n" +
"            <td>\n" +
"                <p><b>Marca:</b></p>\n" +
"            </td>\n" +
"            <td>\n" +
                    v.getMarca()+
"            </td>\n" +
"        </tr>\n" +
"        <tr>\n" +
"            <td>\n" +
"                <p><b>Modelo:</b></p>\n" +
"            </td>\n" +
"            <td>\n" +
v.getModelo()+
"            </td>\n" +
"        </tr>\n" +
"        <tr>\n" +
"            <td>\n" +
"                <p><b> N&uacute;mero de chasis:</b></p>\n" +
"            </td>\n" +
"            <td>\n" +
v.getNroChasis()+
"            </td>\n" +
"        </tr>\n" +
"        <tr>\n" +
"            <td>\n" +
"                <p><b> N&uacute;mero de motor:</b></p>\n" +
"            </td>\n" +
"            <td>\n" +
v.getNroMotor()+
"            </td>\n" +
"        </tr>\n" +
"        <tr>\n" +
"            <td>\n" +
"                <p><b> Consumo:</b></p>\n" +
"            </td>\n" +
"            <td>\n" +
v.getConsumo()+
"            </td>\n" +
"        </tr>\n" +
"        <tr>\n" +
"            <td>\n" +
"                <p><b>Tipo de veh&iacute;culo:</b></p>\n" +
"            </td>\n" +
"            <td>\n" ;
            switch(v.getTipoVehiculo()){
                case 0: datos+= "Táctico-Administrativo"; break;
                case 1: datos+= "Maquinaria"; break;
                case 2: datos+= "Remolque"; break;
                case 3: datos+= "Grupo electrógeno"; break;
            }
datos+="     </td>\n" +
"        </tr>\n" +
"        <tr>\n" +
"            <td>\n" +
"                <p><b> Horas o kil&oacute;metros Inicial:</b></p>\n" +
"            </td>\n" +
"            <td>\n" +
v.getHoraskilometrosInicial()+
"            </td>\n" +
"        </tr>\n" +
"        <tr>\n" +
"            <td>\n" +
"                <p><b> Horas o kil&oacute;metros alcanzados:</b></p>\n" +
"            </td>\n" +
"            <td>\n" +
v.getHoraskilometrosTotal()+
"            </td>\n" +
"        </tr>\n" +
"    </table>\n" ;

        }
        datos+="<h1 style='page-break-after:always' > </h1>";
        if(historial){
           ArrayList<ObservacionVehiculo> ao = this.getVehiculoHistorial(idVehiculo); 
           datos+="<h1>HISTORIAL:</h1> <table cellspacing=0px border=1px style=\"width: 100%;\" align='center'>\n" +
        "<tr style='background-color:#ffcc66'>" +
"<td style='width: 15%' align='center'><h3 style='margin:2%;'>Fecha</h3></td>" +
"<td style='width: 60%' align='center'><h3 style='margin:2%;'>Observación</h3></td>" +
"<td style='width: 10%' align='center'><h3 style='margin:2%;'>Mant.</h3></td>" +
"<td style='width: 10%' align='center'><h3 style='margin:2%;'>Hr o km Realizados</h3></td>";
                int i=0;
                String color;
                for (ObservacionVehiculo s: ao){
                        if ((i%2)==0){
                            color=" #ccccff";
                        }
                        else{
                            color=" #ffff99";
                        }
                        i++;
datos+= "<tr style='background-color:" + color + "'>" +
"<td style='width: 15%' align='center'>"+s.getFecha()+"</td>" +
"<td style='width: 60%' align='center'>"+s.getObservacion()+"</td>" +
"<td style='width: 10%' align='center'>";
                            if(s.isMantenimiento()){
                                datos+=s.getTipoMantenimiento().getDescripcion();
                            }
datos+="</td>" +
"<td style='width: 10%' align='center'>"+s.getHorasKilometrosRealizados()+"</td>" +
"</tr>";
                }
datos+="    </table>";
        }
        out.print(datos);
    }
    public void imprimirVehiculos(PrintWriter out){
        ArrayList<Vehiculo> av= this.getVehiculos();
        String datos="<h1 align='center'>Vehículos del sistema:</h1><table style=\"width: 100%;\" cellspacing=0px border=1px align='center'>\n" +
"                <tr style='background-color:#ffcc66'>" +
"                            <td style='width: 20%' align='center'><h3 style='margin:2%;'>Matricula ONU</h3></td>" +
"                            <td style='width: 20%' align='center'><h3 style='margin:2%;'>Matricula UY</h3></td>" +
"                            <td style='width: 10%' align='center'><h3 style='margin:2%;'>Marca</h3></td>" +
"                            <td style='width: 10%' align='center'><h3 style='margin:2%;'>Modelo</h3></td>" +
"                            <td style='width: 10%' align='center'><h3 style='margin:2%;'>Motor</h3></td>" +
"                            <td style='width: 10%' align='center'><h3 style='margin:2%;'>Chasis</h3></td>" +
"               </tr>" ;
                int i=0;
                String color;
                for (Vehiculo u1: av){
                        if ((i%2)==0){
                            color=" #ccccff";
                        }
                        else{
                            color=" #ffff99";
                        }
                        i++;
datos+="             <tr style='background-color:"+color+"'>" +
    "                    <td style='width: 20%' align='center'>"+u1.getMatriculaONU()+"</td>" +
    "                    <td style='width: 20%' align='center'>"+u1.getMatriculaUY()+"</td>" +
    "                    <td style='width: 10%' align='center'>"+u1.getMarca()+"</td>" +
    "                    <td style='width: 10%' align='center'>"+u1.getModelo()+"</td>" +
    "                    <td style='width: 10%' align='center'>"+u1.getNroMotor()+"</td>" +
    "                    <td style='width: 10%' align='center'>"+u1.getNroChasis()+"</td>" +
"                    </tr>";
                }
datos+="    </table>";
out.print(datos);
    }
    public void imprimirAlertasMantenimiento(PrintWriter out){
        ArrayList<AlertaMantenimiento> am = this.getAlertasMantenimiento();
        String datos="<h1 align='center'>ALERTAS DE MANTENIMIENTO</h1><table style=\"width: 100%;\" cellspacing=0px border=1px align='center'>" +
"                <tr style='background-color:#ffcc66'>" +
"                            <td style='width: 20%' align='center'><h3 style='margin:2%;'>Fecha</h3></td>" +
"                            <td style='width: 20%' align='center'><h3 style='margin:2%;'>Vehiculo</h3></td>" +
"                            <td style='width: 30%' align='center'><h3 style='margin:2%;'>Tipo Mantenimiento</h3></td>" +
"                            <td style='width: 20%' align='center'><h3 style='margin:2%;'>Hr o Km faltantes</h3></td>" +
"               </tr>" ;
                int i=0;
                String color;
                for (AlertaMantenimiento u1: am){
                        if ((i%2)==0){
                            color=" #ccccff";
                        }
                        else{
                            color=" #ffff99";
                        }
                        i++;
    datos+="    <tr style='background-color:"+color+"'>" +
    "                    <td style='width: 20%' align='center'>"+u1.getFecha()+"</td>" +
    "                    <td style='width: 20%' align='center'>"+u1.getAlertaVehiculo().getIdVehiculo()+"</td>" +
    "                    <td style='width: 30%' align='center'>"+u1.getAlertaVehiculo().getTipoMantenimiento().getDescripcion()+"</td>" +
    "                    <td style='width: 20%' align='center'>"+u1.getHorasKilometrosAlcanzados()+"</td>" +
    "           </tr>" ;
                    }
    datos+="    </table>";
    out.print(datos);
    }
}
