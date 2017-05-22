/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package servlet;

import Manejadores.ManejadorArmamento;
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
public class Armamento extends HttpServlet {

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
            ManejadorArmamento mc = new ManejadorArmamento();
            if(request.getParameter("id")!= null){ //alta o modficacion
                int id= Integer.valueOf(request.getParameter("id"));
                String fechaAlta = request.getParameter("fechaAlta");
                String fechaBaja = request.getParameter("fechaBaja");
                int idDestino= Integer.valueOf(request.getParameter("idDestino"));
                int idModeloArmamento= Integer.valueOf(request.getParameter("idModeloArmamento"));
                int calibre= Integer.valueOf(request.getParameter("calibre"));
                int idEntregado= Integer.valueOf(request.getParameter("idEntregado"));
                if(id==-1){ //alta
                    if(mc.agregarArmamento(id, idModeloArmamento, fechaAlta, fechaBaja, idDestino, calibre, idEntregado)){
                        sesion.setAttribute("mensaje", "Armamento agregado correctamente.");
                    }
                    else{
                        sesion.setAttribute("mensaje", "ERROR al agregar el armamento.");
                    }
                   
                }
                else{ //modificacion
                    if(mc.modificarArmamento(id, idModeloArmamento, fechaAlta, fechaBaja, idDestino, calibre, idEntregado)){
                       sesion.setAttribute("mensaje", "Armamento modificado correctamente.");
                    }
                    else{
                        sesion.setAttribute("mensaje", "ERROR al modificar el armamento.");
                    }
                   
                }
            }
            else{
                if(request.getParameter("elim")!= null){
                    int id=Integer.valueOf(request.getParameter("elim"));
                    if(mc.eliminarArmamento(id)){
                        sesion.setAttribute("mensaje", "Armametno eliminado correctamente.");
                    }
                    else{
                        sesion.setAttribute("mensaje", "ERROR al eliminar el armamento.");
                    }
                }
            }
            mc.CerrarConexionManejador();
            response.sendRedirect("armamentos.jsp");
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
