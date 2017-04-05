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
public class ConsultaMedica extends HttpServlet {

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
            if(request.getParameter("id")!= null){ //alta o modficacion
                int id= Integer.valueOf(request.getParameter("id"));
                int idPersonal= Integer.valueOf(request.getParameter("idPersonal"));
                int idMedico= Integer.valueOf(request.getParameter("idMedico"));
                String diagnostico = request.getParameter("diagnostico");
                String tratamiento = request.getParameter("tratamiento");
                String fechaInicio = request.getParameter("fechaInicio");
                String fechaFin = request.getParameter("fechaFin");
                
                if(id==-1){ //alta
                    if(mp.agregarConsultaMedica(idPersonal, fechaInicio, fechaFin, diagnostico, idMedico, tratamiento)){
                        sesion.setAttribute("mensaje", "Consulta médica agregada correctamente.");
                    }
                    else{
                        sesion.setAttribute("mensaje", "ERROR al agregar la consulta médica.");
                    }
                    if(request.getParameter("ci")!=null){
                        mp.CerrarConexionManejador();
                        response.sendRedirect("personal.jsp?id="+request.getParameter("ci"));
                    }
                    else{
                        mp.CerrarConexionManejador();
                        response.sendRedirect("s1-historiaClinica.jsp");
                    }
                    
                }
                else{
                    if(mp.modificarConsultaMedica(id,idPersonal, fechaInicio, fechaFin, diagnostico, idMedico, tratamiento)){
                        sesion.setAttribute("mensaje", "Consulta médica modificada correctamente.");
                    }
                    else{
                        sesion.setAttribute("mensaje", "ERROR al modificar la consulta médica.");
                    }
                    if(request.getParameter("ci")!=null){
                        mp.CerrarConexionManejador();
                        response.sendRedirect("personal.jsp?id="+request.getParameter("ci"));
                    }
                    else{
                        mp.CerrarConexionManejador();
                        response.sendRedirect("s1-historiaClinica.jsp");
                    }
                }
            }
            else{
                if(request.getParameter("elim")!= null){
                    int id=Integer.valueOf(request.getParameter("elim"));
                    if(mp.eliminarConsultaMedica(id)){
                        sesion.setAttribute("mensaje", "Consulta médica eliminada correctamente.");
                    }
                    else{
                        sesion.setAttribute("mensaje", "ERROR al eliminar la consulta médica.");
                    }
                    if(request.getParameter("ci")!=null && Integer.valueOf(request.getParameter("ci"))!=-1){
                        mp.CerrarConexionManejador();
                        response.sendRedirect("personal.jsp?id="+request.getParameter("ci"));
                    }
                    else{
                        mp.CerrarConexionManejador();
                        response.sendRedirect("s1-historiaClinica.jsp");
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
