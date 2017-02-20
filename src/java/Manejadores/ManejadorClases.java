/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Manejadores;

import Classes.ConexionBD;
import Classes.Usuario;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
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
    
    //Retorna un Usuario con los parametros indicados persistiendolo en la base de datos si creador es Admin.
    //retorna null si creador no es admin o se produjo algun error en la escritura de la base de datos.
    //PRECONDICIONES: - existeUsuario(usuario)==false
    public Usuario crarUsuario(Usuario creador, String usuario, String nombreMostrar, String contrasena, boolean admin, boolean s1, boolean s4){
        Usuario u=null;
        if (creador.isAdmin()){
            try {
                String sql= "insert into usuarios (usuario, nombreMostrar, contrasena,admin, s1, s4) values(?,?,MD5(?),?,?,?),";
                PreparedStatement s= connection.prepareStatement(sql);
                s.setString(1, usuario);
                s.setString(2, nombreMostrar);
                s.setString(3, contrasena);
                s.setBoolean(4, admin);
                s.setBoolean(5, s1);
                s.setBoolean(6, s4);
                int result = s.executeUpdate();
                int id=0;
                if(result>0){
                    ResultSet rs = s.getGeneratedKeys();
                    if(rs.next()){
                        id = (int)rs.getLong(1);
                    }
                }
                u= new Usuario(id, usuario, nombreMostrar, contrasena, admin, s1, s4);
                
            } catch (SQLException ex) {
                Logger.getLogger(ManejadorClases.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return u;
    }
    
    //retorna false si creador no es admin o se produjo algun error en la escritura de la base de datos.
    //atributo usuario no es modificable.
    //atributo contrasena modificable con la funcion cambiarContrasena
    public boolean ModificarUsuario(Usuario creador, int id, String nombreMostrar, boolean admin, boolean s1, boolean s4){
        if (creador.isAdmin()){
            try {
                String sql= "Update usuarios set nombreMostrar=?, admin=?, s1=?, s2=? where id=?";
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
    
    //retorna false si creador no es admin o se produjo algun error en la escritura de la base de datos.
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
    
    //retorna false si creador no es admin o no es su contrasena o se produjo algun error en la escritura de la base de datos.
    public boolean cambiarContrasena(Usuario creador, int id, String contrasenaNueva){
        if(creador.isAdmin() || creador.getId()== id){
            try {
                String sql= "Update usuarios set contrasena=MD5('"+contrasenaNueva+"') where id="+id;
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
    
    
    
}
