/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package servlet;

import Classes.RecordPersonal;
import Manejadores.ManejadorPersonal;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Date;
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
public class Personal extends HttpServlet {

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
        Classes.Usuario u = (Classes.Usuario) sesion.getAttribute("usuario");
        try (PrintWriter out = response.getWriter()) {
            /* TODO output your page here. You may use following sample code. */
            ManejadorPersonal mp = new ManejadorPersonal();
            if(request.getParameter("id")!= null){ //alta o modficacion
                int id = Integer.valueOf(request.getParameter("id"));
                RecordPersonal rp = new RecordPersonal();
                rp.foto = request.getParameter("foto2");
                rp.ci= Integer.valueOf(request.getParameter("ci"));
                rp.gradoInt= Integer.valueOf(request.getParameter("grado"));
                rp.nombre = request.getParameter("nombre");
                rp.apellido = request.getParameter("apellido");
                rp.fechaNac = request.getParameter("fechaNac");
                rp.vtoCI =request.getParameter("vtoCi");
                
                rp.pasaporte = request.getParameter("pasaporte");
                rp.vtoPas = request.getParameter("vtoPas");
                rp.cc = request.getParameter("cc");
                if (request.getParameter("ccNro").equals("")){
                     rp.ccNro = 0;
                }
                else{
                     rp.ccNro = Integer.parseInt(request.getParameter("ccNro"));
                }
                rp.licenciaConducir = false;
                if (request.getParameter("licenciaConducir")!=null){
                    rp.licenciaConducir = request.getParameter("licenciaConducir").equals("on");
                }
                 if (request.getParameter("nroLicCond").equals("")){
                     rp.nroLicCond = 0;
                }
                else{
                     rp.nroLicCond = Integer.parseInt(request.getParameter("nroLicCond"));
                }
                rp.catLicCond = request.getParameter("catLicCond");
                rp.vtoLicCond = request.getParameter("vtoLicCond");
                rp.carneSalud = false;
                if (request.getParameter("carneSalud")!=null){
                    rp.carneSalud = request.getParameter("carneSalud").equals("on");
                }
                rp.vtoCarneSalud = request.getParameter("vtoCarneSalud");
                rp.expMision = false;
                if (request.getParameter("expMision")!=null){
                    rp.expMision = request.getParameter("expMision").equals("on");
                }
                rp.lugarExpMision = request.getParameter("lugarExpMision");
                rp.unidadMilitarId= Integer.valueOf(request.getParameter("unidadMilitar"));
                if(id==-1){ //alta
                    if(mp.agregarPersonal(rp)){
                        sesion.setAttribute("mensaje", "Personal agregado sastifactoriamente.");
                    }
                    else{
                        sesion.setAttribute("mensaje", "ERROR al ingresar el personal sistema.");
                    }
                    mp.CerrarConexionManejador();
                    response.sendRedirect("s1-personal.jsp");
                }
                else{ //modificacion
                    if( mp.modificarPersonalDatosBasicos(rp)){
                       sesion.setAttribute("mensaje", "Personal modificado sastifactoriamente.");
                    }
                    else{
                        sesion.setAttribute("mensaje", "ERROR al modificar el personal.");
                    }
                    mp.CerrarConexionManejador();
                    response.sendRedirect("s1-personal.jsp");
                }
            }
            else{
                if(request.getParameter("elim")!=null){
                    if(request.getParameter("historial").equals("S")){
                        if(mp.eliminarPersonal(Integer.valueOf(request.getParameter("elim")),true, request.getParameter("arribo"), request.getParameter("regreso"),  request.getParameter("observaciones"))){
                            sesion.setAttribute("mensaje", "Personal guardado en historial sastifactoriamente.");
                        }else{
                            sesion.setAttribute("mensaje", "ERROR al guardar el personal en el historial.");
                        };
                    }
                    else{
                        if(mp.eliminarPersonal(Integer.valueOf(request.getParameter("elim")),false,"", "",  "")){
                            sesion.setAttribute("mensaje", "Personal eliminado sastifactoriamente.");
                        }else{
                            sesion.setAttribute("mensaje", "ERROR al eliminar el personal.");
                        };
                    }
                    mp.CerrarConexionManejador();
                    response.sendRedirect("s1-personal.jsp");
                }
                if(request.getParameter("json")!=null){
                    if(request.getParameter("json")!=""){
                        JsonObjectBuilder json = Json.createObjectBuilder(); 
                        JsonArrayBuilder jab= Json.createArrayBuilder();
                        if(mp.existePersonalHistorial(Integer.valueOf(request.getParameter("json")))){
                            jab.add(Json.createObjectBuilder()
                                .add("existeEnHistorial", "S")
                            );
                        }
                        else{
                            jab.add(Json.createObjectBuilder()
                                .add("existeEnHistorial", "N")
                            );
                        }
                        json.add("existe", jab);
                        mp.CerrarConexionManejador();
                        out.print(json.build());
                    }
                }
                else{
                    if(request.getParameter("existe")!=null){
                        int ci= Integer.valueOf(request.getParameter("existe"));
                        if(mp.existePersonalHistorial(ci)){
                            if(mp.recuperarDatosHistorial(ci)){
                                sesion.setAttribute("mensaje", "Personal recuperado correctamente.");
                                mp.CerrarConexionManejador();
                                response.sendRedirect("personal.jsp?id="+ci);
                            }else{
                                sesion.setAttribute("mensaje", "ERROR al recuperar el personal.");
                                mp.CerrarConexionManejador();
                                response.sendRedirect("personal.jsp");
                            };
                        }
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
