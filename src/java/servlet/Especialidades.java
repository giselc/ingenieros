/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package servlet;

import Manejadores.ManejadorPersonal;
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author Gisel
 */
public class Especialidades extends HttpServlet {

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
            int ci=Integer.valueOf(request.getParameter("ci"));
            if(request.getParameter("elim")== null){ //alta
                int id= Integer.valueOf(request.getParameter("idEspecialidad"));
                if(mp.agregarEspecialidad(id, ci)){
                    sesion.setAttribute("mensaje", "Especialidad agregada correctamente.");
                }
                else{
                    sesion.setAttribute("mensaje", "ERROR al agregar la especialidad.");
                }
                mp.CerrarConexionManejador();
                response.sendRedirect("personal.jsp?id="+ci);
            }
            else{
                if(request.getParameter("elim")!= null){
                    int id=Integer.valueOf(request.getParameter("elim"));
                    if(mp.eliminarEspecialidad(id, ci)){
                        sesion.setAttribute("mensaje", "Sancion eliminada correctamente.");
                    }
                    else{
                        sesion.setAttribute("mensaje", "ERROR al eliminar la sancion.");
                    }
                    if(request.getParameter("ci")!=null){
                        mp.CerrarConexionManejador();
                        response.sendRedirect("personal.jsp?id="+request.getParameter("ci"));
                    }
                    else{
                        mp.CerrarConexionManejador();
                        response.sendRedirect("s1-sanciones.jsp");
                    }
                }
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
