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
public class Familiar extends HttpServlet {

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
                int idPersonal= Integer.valueOf(request.getParameter("ciPersonal"));
                /* TODO output your page here. You may use following sample code. */
                if(request.getParameter("ciFamiliar")!= null){ //alta o modficacion
                    int ciFamiliar = Integer.valueOf(request.getParameter("ci"));
                    int idVinculo = Integer.valueOf(request.getParameter("idVinculo"));
                    String nombres = request.getParameter("nombres");
                    String apellidos = request.getParameter("apellidos");
                    String domicilio = request.getParameter("domicilio");
                    String telefono = request.getParameter("telefono");
                    String celular = request.getParameter("celular");
                    String ocupacion = request.getParameter("ocupacion");
                    int edad=0;
                    if(request.getParameter("edad")!=""){
                        edad = Integer.valueOf(request.getParameter("edad"));
                    }
                    boolean discapacidad = false;
                    String descDiscapacidad = "";
                    if (request.getParameter("discapacidad")!=null){
                        discapacidad = request.getParameter("discapacidad").equals("on");
                        descDiscapacidad = request.getParameter("descDiscapacidad");
                    }
                    ManejadorCodigos mc = new ManejadorCodigos();
                    if(Integer.valueOf(request.getParameter("ciFamiliar"))==-1){ //alta
                        if(mp.crearFamiliar(idPersonal, ciFamiliar, nombres, apellidos, idVinculo, domicilio, celular, telefono, discapacidad, edad, descDiscapacidad, ocupacion)){
                            sesion.setAttribute("mensaje", "Familiar agregado correctamente.");
                        }
                        else{
                            sesion.setAttribute("mensaje", "ERROR al agregar el familiar.");
                        }
                        mp.CerrarConexionManejador();
                        mc.CerrarConexionManejador();
                        response.sendRedirect("personal.jsp?id="+idPersonal);

                    }
                    else{ //modificacion
                        if(mp.modificarFamiliar(idPersonal, new Classes.Familiar(ciFamiliar, mc.getTipoFamiliar(idVinculo), nombres, edad, apellidos, domicilio, ocupacion, telefono, celular, discapacidad, descDiscapacidad))){
                           sesion.setAttribute("mensaje", "Familiar modificado correctamente.");
                        }
                        else{
                            sesion.setAttribute("mensaje", "ERROR al modificar el familiar.");
                        }
                        mp.CerrarConexionManejador();
                        mc.CerrarConexionManejador();
                        response.sendRedirect("personal.jsp?id="+idPersonal);
                    }
                }
                else{
                    if(request.getParameter("elim")!= null){
                        int ciFamiliar=Integer.valueOf(request.getParameter("elim"));
                        int resultado=mp.desvincularFamiliar(idPersonal, ciFamiliar);
                        if(resultado!=0){
                            if(resultado==1){
                                sesion.setAttribute("mensaje", "Familiar desvinculado correctamente.");
                            }
                            else{
                                sesion.setAttribute("mensaje", "Familiar eliminado correctamente.");
                            }

                        }
                        else{
                            sesion.setAttribute("mensaje", "ERROR al eliminar el familiar.");
                        }
                        mp.CerrarConexionManejador();
                        response.sendRedirect("personal.jsp?id="+idPersonal);
                    }
                }
            }
            else{
               int ci = Integer.valueOf(request.getParameter("json"));
               Classes.Familiar f=mp.getFamiliar2(ci);
               JsonObjectBuilder json = Json.createObjectBuilder(); 
               if(f==null){
                   json.add("familiar", Json.createArrayBuilder().build());
                }
                else{
                    JsonArrayBuilder jab= Json.createArrayBuilder();
                    jab.add(Json.createObjectBuilder()
                        .add("nombres", f.getNombre())
                        .add("apellidos", f.getApellido())
                        .add("domicilio", f.getDomicilio())
                        .add("celular", f.getCelular())
                        .add("telefono", f.getTelefono())
                        .add("edad", f.getEdad())
                        .add("ocupacion", f.getOcupacion())
                        .add("discapacidad", f.getDiscapacidad())
                        .add("descDiscapacidad", f.getDescDiscapacidad())
                    );
                    json.add("familiar", jab);
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
