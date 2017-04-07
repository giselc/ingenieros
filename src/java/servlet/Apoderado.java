/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package servlet;

import Manejadores.ManejadorCodigos;
import Manejadores.ManejadorPersonal;
import java.io.IOException;
import java.io.PrintWriter;
import javax.json.Json;
import javax.json.JsonArrayBuilder;
import javax.json.JsonObjectBuilder;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author Gisel
 */
public class Apoderado extends HttpServlet {

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
            ManejadorPersonal mp = new ManejadorPersonal();
            if(request.getParameter("json")==null){
                int idPersonal= Integer.valueOf(request.getParameter("idPersonal"));
                /* TODO output your page here. You may use following sample code. */
                if(request.getParameter("id")!= null){ //alta o modficacion
                    int ci = Integer.valueOf(request.getParameter("ci"));
                    int idVinculo = Integer.valueOf(request.getParameter("idVinculo"));
                    String nombres = request.getParameter("nombres");
                    String apellidos = request.getParameter("apellidos");
                    String domicilio = request.getParameter("domicilio");
                    String telefono = request.getParameter("telefono");
                    String celular = request.getParameter("celular");
                    int id= Integer.valueOf(request.getParameter("id"));
                    ManejadorCodigos mc = new ManejadorCodigos();
                    if(id==-1){ //alta
                        if(mp.crearApoderado(idPersonal,ci,nombres,apellidos,idVinculo,domicilio,celular,telefono)){
                            sesion.setAttribute("mensaje", "Apoderado agregado correctamente.");
                        }
                        else{
                            sesion.setAttribute("mensaje", "ERROR al agregar el apoderado.");
                        }
                        mp.CerrarConexionManejador();
                        mc.CerrarConexionManejador();
                        response.sendRedirect("personal.jsp?id="+idPersonal);

                    }
                    else{ //modificacion
                        if(mp.modificarApoderado(idPersonal, new Classes.Apoderado(ci, nombres, apellidos, mc.getTipoFamiliar(idVinculo), domicilio, celular, telefono))){
                           sesion.setAttribute("mensaje", "Apoderado modificado correctamente.");
                        }
                        else{
                            sesion.setAttribute("mensaje", "ERROR al modificar el apoderado.");
                        }
                        mp.CerrarConexionManejador();
                        mc.CerrarConexionManejador();
                        response.sendRedirect("personal.jsp?id="+idPersonal);
                    }
                }
                else{
                    if(request.getParameter("elim")!= null){
                        int id=Integer.valueOf(request.getParameter("elim"));
                        if(mp.desvincularApoderado(idPersonal, id)){
                                sesion.setAttribute("mensaje", "Apoderado desvinculado correctamente.");
                        }
                        else{
                            sesion.setAttribute("mensaje", "ERROR al eliminar el documento.");
                        }
                        mp.CerrarConexionManejador();
                        response.sendRedirect("personal.jsp?id="+idPersonal);
                    }
                }
            }
            else{
               int ci = Integer.valueOf(request.getParameter("json"));
               Classes.Apoderado a=mp.getApoderado2(ci);
               JsonObjectBuilder json = Json.createObjectBuilder(); 

               if(a==null){
                   json.add("apoderado", Json.createArrayBuilder().build());
                }
                else{
                    JsonArrayBuilder jab= Json.createArrayBuilder();
                    jab.add(Json.createObjectBuilder()
                        .add("ci", a.getCi())
                        .add("nombre", a.getNombre())
                        .add("apellidos", a.getApellido())
                        .add("domicilio", a.getDomicilio())
                        .add("celular", a.getCelular())
                        .add("telefono", a.getTelefono())
                    );
                    json.add("apoderado", jab);
                }
               mp.CerrarConexionManejador();
                out.print(json.build());
            }
        }
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
