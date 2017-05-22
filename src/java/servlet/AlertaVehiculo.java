/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package servlet;

import Manejadores.ManejadorVehiculo;
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
public class AlertaVehiculo extends HttpServlet {

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
                int id= Integer.valueOf(request.getParameter("id"));
                int idTipoMantenimiento= Integer.valueOf(request.getParameter("idTipoMantenimiento"));
                int HorasKilometrosAlerta= Integer.valueOf(request.getParameter("HorasKilometrosAlerta"));
                int HorasKilometros= Integer.valueOf(request.getParameter("HorasKilometros"));
                if(id==-1){ //alta
                    if(mv.agregarAlertaVehiculo(idVehiculo, idTipoMantenimiento, HorasKilometros, HorasKilometrosAlerta)){
                        sesion.setAttribute("mensaje", "Configuración de alerta agregada correctamente.");
                    }
                    else{
                        sesion.setAttribute("mensaje", "ERROR al agregar la nueva configuración de alerta.");
                    }
                    
                }
                //no se puede modificar
            }
            else{
                if(request.getParameter("elim")!= null){
                    int id=Integer.valueOf(request.getParameter("elim"));
                    if(mv.eliminarAlertaVehiculo(id)){
                        sesion.setAttribute("mensaje", "Configuración de alerta eliminada correctamente.");
                    }
                    else{
                        sesion.setAttribute("mensaje", "ERROR al eliminar la configuración de alerta.");
                    }
                }
            }
            mv.CerrarConexionManejador();
            response.sendRedirect("vehiculo.jsp?id="+idVehiculo);
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
