/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Manejadores;

import Classes.Apoderado;
import Classes.ConexionBD;
import Classes.Usuario;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
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

//MANEJADOR DE CLASES EXCEPTO LA CLASE PERSONAL
public class ManejadorClases {
    private Connection connection;

    public ManejadorClases() {
        connection = ConexionBD.GetConnection();
    }
    
    //USUARIOOOO
    
    //funcion creada para asegurar que no existe otro usuario con usuario='usuario'
    public boolean existeUsuario(String usuario){
        try {
            Statement s= connection.createStatement();
            String sql="Select * from usuarios where usuario='"+usuario+"'";
            ResultSet rs= s.executeQuery(sql);
            if (rs.next()){
                return true;
            }
            
        } catch (SQLException ex) {
            Logger.getLogger(ManejadorCodigos.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }
    
    //Retorna un Usuario con los parametros indicados persistiendolo en la base de datos si 'creador' es Admin.
    //retorna null si creador no es admin o se produjo algun error en la escritura de la base de datos.
    //PRECONDICIONES: - existeUsuario(usuario)==false
    public boolean crarUsuario(Usuario creador, String usuario, String nombreMostrar, String contrasena, boolean admin, boolean s1, boolean s4){
        if (creador.isAdmin()){
            try {
                String sql= "insert into usuarios (usuario, nombreMostrar, contrasena,admin, s1, s4) values(?,?,MD5(?),?,?,?)";
                PreparedStatement s= connection.prepareStatement(sql);
                s.setString(1, usuario);
                s.setString(2, nombreMostrar);
                s.setString(3, contrasena);
                s.setBoolean(4, admin);
                s.setBoolean(5, s1);
                s.setBoolean(6, s4);
                int result = s.executeUpdate();
                if(result>0){
                    return true;
                }
            } catch (SQLException ex) {
                Logger.getLogger(ManejadorClases.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return false;
    }
    
    //retorna false si 'creador' no es admin o se produjo algun error en la escritura de la base de datos.
    //atributo usuario no es modificable.
    //atributo contrasena modificable con la funcion cambiarContrasena
    public boolean ModificarUsuario(Usuario creador, int id, String nombreMostrar, boolean admin, boolean s1, boolean s4){
        if (creador.isAdmin()){
            try {
                String sql= "Update usuarios set nombreMostrar=?, admin=?, s1=?, s4=? where id=?";
                PreparedStatement s= connection.prepareStatement(sql);
                s.setString(1, nombreMostrar);
                s.setBoolean(2, admin);
                s.setBoolean(3, s1);
                s.setBoolean(4, s4);
                s.setInt(5, id);
                int result = s.executeUpdate();
                if(result>0){
                    return true;
                }
            } catch (SQLException ex) {
                Logger.getLogger(ManejadorClases.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return false;
    }
    
    //retorna false si 'creador' no es admin o se produjo algun error en la escritura de la base de datos.
    public boolean eliminarUsuario(Usuario creador, int id){
        if (creador.isAdmin()){
            try {
                String sql= "Delete from usuarios where id="+id;
                Statement s= connection.createStatement();
                int result = s.executeUpdate(sql);
                if(result>0){
                    return true;
                }
            } catch (SQLException ex) {
                Logger.getLogger(ManejadorClases.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return false;
    }
    
    //retorna false si 'creador' no es admin(o no es contrasena propia) o su contrasena anterior es incorrecta o se produjo algun error en la escritura de la base de datos.
    public boolean cambiarContrasena(Usuario creador, int id, String contrasenaNueva, String contrasenaAnterior){
        if(creador.isAdmin() || creador.getId()== id){
            try {
                String sql= "Update usuarios set contrasena=MD5('"+contrasenaNueva+"') where id="+id + " and contrasena=MD5('"+contrasenaAnterior+"')";
                Statement s= connection.createStatement();
                int result = s.executeUpdate(sql);
                if(result>0){
                    return true;
                }
            } catch (SQLException ex) {
                Logger.getLogger(ManejadorClases.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return false;
    }
    
    //Retorna la lista de Usuarios del sistema si el usuario 'creador' es admin
    public ArrayList<Usuario> getUsuarios(Usuario creador){
        ArrayList<Usuario> al= new ArrayList<>();
        if (creador.isAdmin()){
            try {
                String sql= "Select * from usuarios";
                Statement s= connection.createStatement();
                ResultSet rs = s.executeQuery(sql);
                Usuario u;
                while(rs.next()){
                    u=new Usuario(rs.getInt("id"), rs.getString("usuario"), rs.getString("nombreMostrar"), rs.getBoolean("admin"), rs.getBoolean("s1"), rs.getBoolean("s4"));
                    al.add(u);
                }
            } catch (SQLException ex) {
                Logger.getLogger(ManejadorClases.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return al;
    }
    
    //retorna el usuario con id='id' si el usuario 'creador' es admin o es el mismo. 
    public Usuario getUsuario(Usuario creador, int id){
        Usuario u=null;
        if (creador.isAdmin() || creador.getId()==id){
            try {
                String sql= "Select * from usuarios where id="+id;
                Statement s= connection.createStatement();
                ResultSet rs = s.executeQuery(sql);
                while(rs.next()){
                    u=new Usuario(rs.getInt("id"), rs.getString("usuario"), rs.getString("nombreMostrar"), rs.getBoolean("admin"), rs.getBoolean("s1"), rs.getBoolean("s4"));
                }
            } catch (SQLException ex) {
                Logger.getLogger(ManejadorClases.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return u;
    }
    
    public Usuario getUsuario(String usuario, String pass){
       
        Usuario u= null;
        try {
                String sql= "Select * from usuarios where usuario='"+usuario+"' and contrasena=MD5('"+pass+"')";
                Statement s= connection.createStatement();
                ResultSet rs = s.executeQuery(sql);
                while(rs.next()){
                    u=new Usuario(rs.getInt("id"), rs.getString("usuario"), rs.getString("nombreMostrar"), rs.getBoolean("admin"), rs.getBoolean("s1"), rs.getBoolean("s4"));
                }
            } catch (SQLException ex) {
                Logger.getLogger(ManejadorClases.class.getName()).log(Level.SEVERE, null, ex);
            }
        
        return u;
    }
    public void imprimirUsuarios(PrintWriter out, Usuario creador){
        out.print("<h1 align='center'>USUARIOS DEL SISTEMA</h1>");
        out.print("<table style=\"width: 100%;\">");
        ArrayList<Usuario> au = this.getUsuarios(creador);
        out.print("<tr style='background-color:#ffcc66'>");
                out.print("<td style='width: 20%' align='center'><h3 style='margin:2%;'>Usuario</h3></td>");
                out.print("<td style='width: 20%' align='center'><h3 style='margin:2%;'>Nombre para mostrar</h3></td>");
                out.print("<td style='width: 10%' align='center'><h3 style='margin:2%;'>Admin</h3></td>");
                out.print("<td style='width: 10%' align='center'><h3 style='margin:2%;'>S1</h3></td>");
                out.print("<td style='width: 10%' align='center'><h3 style='margin:2%;'>S4</h3></td>");
        out.print("</tr></table>  <table style=\"width: 100%;\">" );
        int i=0;
        String color;
        for (Usuario u1: au){
            if ((i%2)==0){
                color=" #ccccff";
            }
            else{
                color=" #ffff99";
            }
            i++;

            out.print("<tr style='background-color:"+color+"'>");
                out.print("<td style='width: 20%' align='center'>"+u1.getUsuario()+"</td>");
                out.print("<td style='width: 20%' align='center'>"+u1.getNombreMostrar()+"</td>");
                out.print("<td style='width: 10%' align='center'>"+u1.isAdmin()+"</td>");
                out.print("<td style='width: 10%' align='center'>"+u1.isS1()+"</td>"); 
                out.print("<td style='width: 10%' align='center'>"+u1.isS4()+"</td>"); 
            out.print("</tr>");
        }
        out.print("</table>");
    }
    
    //APODERADOOO
    
    //retorna una Apoderado con los datos obtenidos y persiste en la base de datos la relacion apoderado-personal
    //retorna null si se produce algun error en la escritura de la base de datos.
    public Apoderado crearApoderado(int idPersonal, int ci,String nombres, String apellidos,int idvinculo,String domicilio,String celular,String telefono){
        try {
            Statement s= connection.createStatement();
            String sql="Select * from apoderados where ci="+ci;
            ResultSet rs= s.executeQuery(sql);
            if (rs.next()){
                sql="insert into apoderados (ci, nombres, apellidos, domicilio, celular,telefono) values("+ci+",'"+nombres+"','"+apellidos+"','"+domicilio+"','"+celular+"','"+telefono+"')";
                int i= s.executeUpdate(sql);
                if(i>0){
                    sql="insert into personal-apoderado (idPersonal, idApoderado, idVinculo) values("+idPersonal+","+ci+","+idvinculo+")";
                    s.executeUpdate(sql);
                    ManejadorCodigos mc= new ManejadorCodigos();
                    return (new Apoderado(ci, nombres, apellidos, mc.getTipoFamiliar(idvinculo) , domicilio, celular, telefono));
                }
            }
            else{
                sql="Select * from personal-apoderado where idPersonal="+idPersonal+" and idApoderado="+ci;
                rs= s.executeQuery(sql);
                if (rs.next()){
                    return(getApoderado(ci,idPersonal));
                }
                else{
                    sql="insert into personal-apoderado (idPersonal, idApoderado, idVinculo) values("+idPersonal+","+ci+","+idvinculo+")";
                    s.executeUpdate(sql);
                    ManejadorCodigos mc= new ManejadorCodigos();
                    return (new Apoderado(ci, nombres, apellidos, mc.getTipoFamiliar(idvinculo) , domicilio, celular, telefono));
                }
            }
            
        } catch (SQLException ex) {
            Logger.getLogger(ManejadorCodigos.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
    
    //retorna el apoderado que tiene la relacion apoderado-personal segun los parametros
    //retorna null si no hay relacion
    //retorna null si se produjo un error con la base de datos
    public Apoderado getApoderado(int ci, int idPersonal){
        try {
            Statement s= connection.createStatement();
            String sql="Select * from personal-apoderado left join apoderados on idApoderado=ci where idApoderado.ci="+ci +" and idPersonal.ci="+idPersonal;
            ResultSet rs= s.executeQuery(sql);
            if (rs.next()){
                ManejadorCodigos mc= new ManejadorCodigos();
                return new Apoderado(rs.getInt("ci"), rs.getString("nombre"), rs.getString("apellido"), mc.getTipoFamiliar(rs.getInt("vinculo")), rs.getString("domicilio"), rs.getString("celular"), rs.getString("telefono"));
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
            String sql="delete form personal-apoderado where idApoderado="+idApod +" and idPersonal="+idPersonal;
            int i= s.executeUpdate(sql);
            if (i>0){
                sql="select form personal-apoderado where idApoderado="+idApod;
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
