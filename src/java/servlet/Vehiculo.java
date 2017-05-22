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
public class Vehiculo extends HttpServlet {

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
            ManejadorVehiculo mv = new ManejadorVehiculo();
            if(request.getParameter("id")!= null){ //alta o modficacion
                String matriculaUY= request.getParameter("matriculaUY");
                String matriculaONU= request.getParameter("matriculaONU");
                String marca= request.getParameter("marca");
                String modelo= request.getParameter("modelo");
                int nroMotor=0;
                if(!request.getParameter("nroMotor").equals("")){
                    nroMotor= Integer.valueOf(request.getParameter("nroMotor"));
                }
                int nroChasis=0;
                if(!request.getParameter("nroChasis").equals("")){
                    nroChasis= Integer.valueOf(request.getParameter("nroChasis"));
                }
                double consumo=0;
                if(!request.getParameter("consumo").equals("")){
                   consumo= Double.valueOf(request.getParameter("consumo"));
                }
                int tipo= Integer.valueOf(request.getParameter("tipo"));
                int horasKilometrosInicial=0;
                if(!request.getParameter("horasKilometrosInicial").equals("")){
                    horasKilometrosInicial= Integer.valueOf(request.getParameter("horasKilometrosInicial"));
                }
                int horasKilometrosTotal=0;
                if(!request.getParameter("horasKilometrosTotal").equals("")){
                    horasKilometrosTotal= Integer.valueOf(request.getParameter("horasKilometrosTotal"));
                }
                
                if(request.getParameter("id").equals("-1")){ //alta
                    if(mv.agregarVehiculo(matriculaUY, matriculaONU, marca, modelo, nroMotor, nroChasis, consumo, tipo, horasKilometrosInicial)){
                        sesion.setAttribute("mensaje", "Vehículo agregado correctamente.");
                    }
                    else{
                        sesion.setAttribute("mensaje", "ERROR al agregar el vehículo.");
                    }
                    mv.CerrarConexionManejador();
                    response.sendRedirect("vehiculo.jsp?id="+matriculaUY);
                    
                }
                else{//modificar
                    if(mv.modificarVehiculo(matriculaUY,marca, modelo, nroMotor, nroChasis, consumo, tipo, horasKilometrosInicial,horasKilometrosTotal)){
                        sesion.setAttribute("mensaje", "Vehículo modificado correctamente.");
                    }
                    else{
                        sesion.setAttribute("mensaje", "ERROR al modificar el vehículo.");
                    }
                    mv.CerrarConexionManejador();
                    response.sendRedirect("vehiculo.jsp?id="+matriculaUY);
                }
            }
            else{
                if(request.getParameter("elim")!= null){
                    String matriculaUY=request.getParameter("elim");
                    if(mv.eliminarVehiculo(matriculaUY)){
                        sesion.setAttribute("mensaje", "Vehículo eliminado correctamente.");
                    }
                    else{
                        sesion.setAttribute("mensaje", "ERROR al eliminar el vehículo.");
                    }
                    mv.CerrarConexionManejador();
                    response.sendRedirect("vehiculo.jsp?id="+matriculaUY);
                    
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
