/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package servlet;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.json.Json;
import javax.json.JsonArrayBuilder;
import javax.json.JsonObjectBuilder;
import javax.servlet.ServletContext;
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
public class Novedades extends HttpServlet {

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
            if(request.getParameter("subir")!= null){
                Collection<Part> fileParts = request.getParts();
                //Part filePart = request.getPart("novedad"); // Obtiene el archivo
               // String fileName = request.getParameter("nombre"); // MSIE fix.

                //InputStream fileContent = filePart.getInputStream(); //Lo transforma en InputStream
                String fecha = request.getParameter("fecha");
                String fecha1=fecha.replace("-", "");
                FileOutputStream output = null;
                for (Part part : fileParts) {
                    if(this.getFileName(part)!=null){
                        InputStream input = part.getInputStream();
                        try {
                            System.out.print(getServletContext().getRealPath("/"));
                            output = new FileOutputStream(getServletContext().getRealPath("/")+"/Novedades/"+fecha1+"-"+this.getFileName(part));
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
                }
               response.sendRedirect("verNovedades.jsp?fecha="+fecha);
            }
            else{
                if(request.getParameter("ver")!= null && request.getParameter("ver")!="1"){
                    String fecha = request.getParameter("ver");
                    fecha=fecha.replace("-", "");
                    
                    File dir = new File(getServletContext().getRealPath("/")+"/Novedades");
                    String[] ficheros = dir.list();
                    JsonObjectBuilder json = Json.createObjectBuilder(); 
                    JsonArrayBuilder jab= Json.createArrayBuilder();
                    
                    if (ficheros != null){
                        for (int x=0;x<ficheros.length;x++)
                            if(ficheros[x].startsWith(fecha)){
                                jab.add(Json.createObjectBuilder()
                                      .add("nombre", ficheros[x])
                                  );
                            }
                    }
                    json.add("novedad", jab);
                    out.print(json.build());
                }
                else{
                    if(request.getParameter("elim")!= null){
                        if(eliminarArchivo(request.getParameter("elim"))){
                            sesion.setAttribute("mensaje", "Novedad eliminada correctamente.");
                        }
                        else{
                            sesion.setAttribute("mensaje", "ERROR al eliminar la novedad.");
                        }
                        response.sendRedirect("verNovedades.jsp");
                    }
                }
            }
        }
    }
    private boolean eliminarArchivo(String nombre){
        if(!nombre.equals("")){
            File f = new File(getServletContext().getRealPath("/")+"/Novedades/"+nombre);
            if(f.exists()){
                return f.delete();
                
            }
            return true;
        }
        return true;
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
