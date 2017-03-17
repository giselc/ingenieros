/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Manejadores;

import Classes.ConexionBD;
import Classes.Personal;
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
public class ManejadorPersonal {
    private Connection connection;

    public ManejadorPersonal() {
        connection = ConexionBD.GetConnection();
    }
    //retorna una lista del personal de la tabla Personal de la BD.
    public ArrayList<Personal> getPersonalListar(){
        ArrayList<Personal> ap= new ArrayList<>();
        try {
            String sql= "Select * from personal";
            Statement s= connection.createStatement();
            ResultSet rs = s.executeQuery(sql);
            Personal p;
            ManejadorCodigos mc = new ManejadorCodigos();
            ManejadorClases mc1 = new ManejadorClases();
            while(rs.next()){
                p=new Personal(mc.getGrado(rs.getInt("grado")),rs.getString("nombre"),rs.getString("apellido"),rs.getInt("ci"),rs.getDate("FechaNac"),rs.getDate("vtoCi"),rs.getString("pasaporte"),rs.getDate("vtoPas"),rs.getString("cc"),rs.getInt("ccNro"),rs.getBoolean("expMision"),rs.getString("lugarExpMision"),rs.getBoolean("licenciaConducir"),rs.getInt("nroLicCond"),rs.getString("catLicCond"),rs.getDate("vtoLicCond"),rs.getBoolean("carneSalud"),rs.getDate("vtoCarneSalud"),mc.getUnidadMilitar(rs.getInt("unidadMilitar")),mc1.getApoderado(rs.getInt("ci")));
                ap.add(p);
            }
        } catch (SQLException ex) {
            Logger.getLogger(ManejadorClases.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return ap;
    }
    
    
}
