/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package servlet;

import Manejadores.ManejadorArmamento;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.http.Part;

/**
 *
 * @author Gisel
 */
@MultipartConfig
public class ISArmamento extends HttpServlet {

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
            ManejadorArmamento mv = new ManejadorArmamento();
            int idArmamento = Integer.valueOf(request.getParameter("armamento"));
            if(request.getParameter("id")!= null){ //alta o modficacion
                Part filePart = request.getPart("informacionSumaria");
                int id= Integer.valueOf(request.getParameter("id"));
                String fecha = request.getParameter("fecha");
                int idOficialArmamento= Integer.valueOf(request.getParameter("idOficialArmamento"));
                
                if(id==-1){ //alta
                    int clave = mv.agregarISArmamento(idArmamento, fecha, this.getFileName(filePart), idOficialArmamento);
                    if(clave!=-1){
                        String s=" Error al subir el archivo.";
                        if(this.subirArchivo(filePart, clave, idArmamento)){
                            s="";
                        }
                        sesion.setAttribute("mensaje", "Información sumaria agregada correctamente."+s);
                    }
                    else{
                        sesion.setAttribute("mensaje", "ERROR al agregar la información sumaria.");
                    }
                    
                }
            }
            else{
                if(request.getParameter("elim")!= null){
                    int id=Integer.valueOf(request.getParameter("elim"));
                    Classes.ISArmamento o = mv.getInformacionSumariaArmamento(id);
                    if(this.eliminarArchivo(o.getInformacionSumaria(), id, idArmamento)){
                        if(mv.eliminarISArmamento(id)){
                            sesion.setAttribute("mensaje", "Información sumaria eliminada correctamente.");
                        }
                        else{
                            sesion.setAttribute("mensaje", "ERROR al eliminar la información sumaria.");
                        }
                    }
                    else{
                        sesion.setAttribute("mensaje", "ERROR al eliminar la información sumaria.");
                    }
                }
            }
            mv.CerrarConexionManejador();
            response.sendRedirect("armamento.jsp?id="+idArmamento);
        }
    }
    private boolean eliminarArchivo(String nombre, int idIS, int idArmamento){
        if(!nombre.equals("")){
            File f = new File(getServletContext().getRealPath("/")+"/ISArmamento/"+idArmamento+"-"+idIS+nombre.substring(nombre.lastIndexOf(".")));
            if(f.exists()){
                return f.delete();
                
            }
            return true;
        }
        return true;
    }
    private boolean subirArchivo(Part part, int idIS, int idArmamento){
        try{
            FileOutputStream output = null;
            String name = this.getFileName(part);
            if(name!=null && !name.equals("")){
                String extension = name.substring(name.lastIndexOf("."));
                InputStream input = part.getInputStream();
                try {
                    System.out.print(getServletContext().getRealPath("/"));
                    output = new FileOutputStream(getServletContext().getRealPath("/")+"/ISArmamento/"+idArmamento+"-"+idIS+extension);
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
