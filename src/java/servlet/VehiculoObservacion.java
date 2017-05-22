/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package servlet;

import Classes.ObservacionVehiculo;
import Classes.RecordAlertasMantenimiento;
import Manejadores.ManejadorVehiculo;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.Collection;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.http.Part;
import javax.xml.bind.ParseConversionEvent;

/**
 *
 * @author Gisel
 */
@MultipartConfig
public class VehiculoObservacion extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession sesion = request.getSession();
        try (PrintWriter out = response.getWriter()) {
            /* TODO output your page here. You may use following sample code. */
            ManejadorVehiculo mv = new ManejadorVehiculo();
            String idVehiculo = request.getParameter("vehiculo");
            if(request.getParameter("id")!= null){ //alta o modficacion
                Part filePart = request.getPart("informacionSumaria");
                int id= Integer.valueOf(request.getParameter("id"));
                String fecha = request.getParameter("fecha");
                int horasKilometrosRealizados= 0;
                if(!request.getParameter("horasKilometrosRealizados").equals("")){
                    horasKilometrosRealizados = Integer.valueOf(request.getParameter("horasKilometrosRealizados"));
                }
                int horasKilometrosMarcados= 0;
                if(!request.getParameter("horasKilometrosMarcados").equals("")){
                    horasKilometrosMarcados = Integer.valueOf(request.getParameter("horasKilometrosMarcados"));
                }
                int idtipoMantenimiento= -1;
                if(!request.getParameter("idTipoMantenimiento").equals("")){
                    idtipoMantenimiento = Integer.valueOf(request.getParameter("idTipoMantenimiento"));
                }
                boolean mantenimiento = false;
                if (request.getParameter("mantenimiento")!=null){
                    mantenimiento = request.getParameter("mantenimiento").equals("on");
                }
                String observaciones = request.getParameter("observaciones");
                int idOperario= Integer.valueOf(request.getParameter("operario"));
                int idEscribiente= Integer.valueOf(request.getParameter("escribiente"));
                
                if(id==-1){ //alta
                    RecordAlertasMantenimiento ram = mv.agregarObservacionVehiculo(idVehiculo,this.getFileName(filePart), fecha , horasKilometrosRealizados, mantenimiento, idtipoMantenimiento, horasKilometrosMarcados, observaciones, idOperario, idEscribiente);
                    if(ram!=null && ram.clave!=-1){
                        String s=" Error al cargar informacion sumaria.";
                        if(this.subirArchivo(filePart, ram.clave)){
                            s="";
                        }
                        System.out.print(ram.alertasMantenimiento);
                        sesion.setAttribute("alertasMantenimiento", ram.alertasMantenimiento);
                        sesion.setAttribute("mensaje", "Observación agregada correctamente."+s);
                    }
                    else{
                        sesion.setAttribute("alertasMantenimiento", null);
                        sesion.setAttribute("mensaje", "ERROR al agregar la observación.");
                    }
                    
                }
                else{
                    ObservacionVehiculo o = mv.getObservacionVehiculo(id);
                    System.out.print(o.getId());
                    if(this.modificarArchivo(filePart,id)){
                        RecordAlertasMantenimiento ram = mv.modificarObservacionVehiculo(id, idVehiculo, this.getFileName(filePart), fecha, horasKilometrosRealizados, mantenimiento, idtipoMantenimiento, horasKilometrosMarcados, observaciones, idOperario, idEscribiente);
                        if(ram!=null){
                            sesion.setAttribute("alertasMantenimiento", ram.alertasMantenimiento);
                            sesion.setAttribute("mensaje", "Observación modificada correctamente.");
                        }
                        else{
                            sesion.setAttribute("alertasMantenimiento", null);
                            sesion.setAttribute("mensaje", "ERROR al modificar la observación.");
                        } 
                    }
                    else{
                        sesion.setAttribute("alertasMantenimiento", null);
                        sesion.setAttribute("mensaje", "ERROR al modificar la observación.");
                    }
                }
            }
            else{
                if(request.getParameter("elim")!= null){
                    int id=Integer.valueOf(request.getParameter("elim"));
                    ObservacionVehiculo o = mv.getObservacionVehiculo(id);
                    if(this.eliminarArchivo(o.getInfromacionSumaria(), id)){
                        RecordAlertasMantenimiento ram= mv.eliminarObservacionVehiculo(id,o.getIdVehiculo());
                        if(ram!=null){
                            sesion.setAttribute("alertasMantenimiento", ram.alertasMantenimiento);
                            sesion.setAttribute("mensaje", "Observación eliminada correctamente.");
                        }
                        else{
                            sesion.setAttribute("alertasMantenimiento", null);
                            sesion.setAttribute("mensaje", "ERROR al eliminar la observación.");
                        }
                    }
                    else{
                        sesion.setAttribute("alertasMantenimiento", null);
                        sesion.setAttribute("mensaje", "ERROR al eliminar la observación.");
                    }
                }
            }
            mv.CerrarConexionManejador();
            response.sendRedirect("vehiculo.jsp?id="+idVehiculo);
        }
    }
    private boolean eliminarArchivo(String nombre, int idObservacion){
        if(!nombre.equals("")){
            File f = new File(getServletContext().getRealPath("/")+"/ISVehiculos/"+idObservacion+nombre.substring(nombre.lastIndexOf(".")));
            return f.delete();
        }
        return true;
    }
    private boolean modificarArchivo(Part part, int idObservcion){
        String name = this.getFileName(part);
        System.out.print(name+"dd");
        boolean b=true;
        if(name!=null && !name.equals("") ){
            System.out.print("dd2");
            ManejadorVehiculo v = new ManejadorVehiculo();
            ObservacionVehiculo o = v.getObservacionVehiculo(idObservcion);
            if(o.getInfromacionSumaria().equals("")){
                b=this.subirArchivo(part, idObservcion);
            }
            else{
                String archivoViejo = idObservcion+o.getInfromacionSumaria().substring(o.getInfromacionSumaria().lastIndexOf("."));
                String archivoNuevo = idObservcion+name.substring(name.lastIndexOf("."));
                if(archivoNuevo.equals(archivoViejo)){
                    b=this.subirArchivo(part, idObservcion);
                }
                else{
                    b=this.eliminarArchivo(archivoViejo, idObservcion);
                    b= b && this.subirArchivo(part, idObservcion);
                }
            }
        }
        return b;
    }
    private boolean subirArchivo(Part part, int idObservacion){
        try{
            FileOutputStream output = null;
            String name = this.getFileName(part);
            if(name!=null && !name.equals("")){
                String extension = name.substring(name.lastIndexOf("."));
                InputStream input = part.getInputStream();
                try {
                    System.out.print(getServletContext().getRealPath("/"));
                    output = new FileOutputStream(getServletContext().getRealPath("/")+"/ISVehiculos/"+idObservacion+extension);
                    int leido = 0;
                    leido = input.read();
                    while (leido != -1) {
                        output.write(leido);
                        leido = input.read();
                    }
                } catch (FileNotFoundException ex) {
                    Logger.getLogger(Novedades.class.getName()).log(Level.SEVERE, ex.getMessage());
                } catch (IOException ex) {
                    Logger.getLogger(Novedades.class.getName()).log(Level.SEVERE, ex.getMessage());
                } finally {
                    try {
                        output.flush();
                        output.close();
                        input.close();
                    } catch (IOException ex) {
                        Logger.getLogger(Novedades.class.getName()).log(Level.SEVERE, "Error cerrando flujo de salida", ex);
                    }
                }
            }
            return true;
        }
        catch(Exception e){
            
        }
        return false;
    }
    private String getFileName(Part part) {
		for (String cd : part.getHeader("content-disposition").split(";")) {
			if (cd.trim().startsWith("filename")) {
				return cd.substring(cd.indexOf('=') + 1).trim()
						.replace("\"", "");
			}
		}
		return null;
	}

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
