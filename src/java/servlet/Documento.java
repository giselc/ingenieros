/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package servlet;

import Manejadores.ManejadorPersonal;
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
public class Documento extends HttpServlet {

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
        response.setContentType("text/html;charset=UTF-8");
        HttpSession sesion = request.getSession();
        try (PrintWriter out = response.getWriter()) {
            /* TODO output your page here. You may use following sample code. */
            ManejadorPersonal mp = new ManejadorPersonal();
            int ci= Integer.valueOf(request.getParameter("ci"));
            if(request.getParameter("id")!= null){ //alta o modficacion
                int id= Integer.valueOf(request.getParameter("id"));
                Part documento = request.getPart("documento");
                int tipoDocumento= Integer.valueOf(request.getParameter("tipoDocumento"));
                if(id==-1){ //alta
                    int clave = mp.crearDocumento(tipoDocumento, ci, this.getFileName(documento));
                    if(clave!=-1){
                        String s=" Error al subir el archivo.";
                        if(this.subirArchivo(documento, clave, ci)){
                            s="";
                        }
                        sesion.setAttribute("mensaje", "Documento agregado correctamente."+s);
                    }
                    else{
                        sesion.setAttribute("mensaje", "ERROR al agregar el documento.");
                    }
                    mp.CerrarConexionManejador();
                    response.sendRedirect("personal.jsp?id="+request.getParameter("ci"));
                    
                }
                else{ //modificacion
                    this.modificarArchivo(documento, id, ci);
                    if(mp.modificarDocumento(id, tipoDocumento,this.getFileName(documento))){
                       sesion.setAttribute("mensaje", "Documento modificado correctamente.");
                    }
                    else{
                        sesion.setAttribute("mensaje", "ERROR al modificar el documento.");
                    }
                    mp.CerrarConexionManejador();
                    response.sendRedirect("personal.jsp?id="+request.getParameter("ci"));
                }
            }
            else{
                if(request.getParameter("elim")!= null){
                    int id=Integer.valueOf(request.getParameter("elim"));
                    Classes.Documento d= mp.getDocumento(id);
                    this.eliminarArchivo(d.getNombre(), ci, id);
                    if(mp.eliminarDocumento(id)){
                        sesion.setAttribute("mensaje", "Documento eliminado correctamente.");
                    }
                    else{
                        sesion.setAttribute("mensaje", "ERROR al eliminar el documento.");
                    }
                    mp.CerrarConexionManejador();
                    response.sendRedirect("personal.jsp?id="+ci);
                }
            }
        }
    }
    private boolean modificarArchivo(Part part, int idDocumento, int ciPersonal){
        String name = this.getFileName(part);
        boolean b=true;
        if(name!=null && !name.equals("") ){
            ManejadorPersonal v = new ManejadorPersonal();
            Classes.Documento o = v.getDocumento(idDocumento);
            if(o.getNombre().equals("")){
                b=this.subirArchivo(part, idDocumento,ciPersonal);
            }
            else{
                String archivoViejo = ciPersonal+"-"+idDocumento+o.getNombre().substring(o.getNombre().lastIndexOf("."));
                String archivoNuevo = ciPersonal+"-"+idDocumento+name.substring(name.lastIndexOf("."));
                if(archivoNuevo.equals(archivoViejo)){
                    b=this.subirArchivo(part, idDocumento,ciPersonal);
                }
                else{
                    b=this.eliminarArchivo(archivoViejo, ciPersonal,idDocumento);
                    b= b && this.subirArchivo(part, idDocumento,ciPersonal);
                }
            }
        }
        return b;
    }
    private boolean eliminarArchivo(String nombre, int ciPersonal, int idDocumento){
        if(!nombre.equals("")){
            File f = new File(getServletContext().getRealPath("/")+"/Documentos/"+ciPersonal+"-"+idDocumento+nombre.substring(nombre.lastIndexOf(".")));
            if(f.exists()){
                return f.delete();
                
            }
            return true;
        }
        return true;
    }
    private boolean subirArchivo(Part part, int idDocumento, int ciPersonal){
        try{
            FileOutputStream output = null;
            String name = this.getFileName(part);
            if(name!=null && !name.equals("")){
                String extension = name.substring(name.lastIndexOf("."));
                InputStream input = part.getInputStream();
                try {
                    System.out.print(getServletContext().getRealPath("/"));
                    output = new FileOutputStream(getServletContext().getRealPath("/")+"/Documentos/"+ciPersonal+"-"+idDocumento+extension);
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
