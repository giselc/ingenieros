/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package servlet;

import Manejadores.ManejadorCodigos;
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
public class Tipo extends HttpServlet {

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
            ManejadorCodigos mc = new ManejadorCodigos();
            String codigo = request.getParameter("codigo");
            boolean ok=false;
            if(request.getParameter("id")!= null){ //alta o modficacion
                int id= Integer.valueOf(request.getParameter("id"));
                String descripcion = request.getParameter("descripcion");
                
                if(id==-1){ //alta
                    if(codigo.equals("Familiares")){
                        ok=mc.agregarTipoFamiliar(descripcion);
                    }
                    else{
                        if(codigo.equals("Grados")){
                            String abreviacion = request.getParameter("abreviacion");
                            ok= mc.agregarGrado(descripcion,abreviacion);
                        }
                        else{
                            if(codigo.equals("Documentos")){
                                ok= mc.agregarTipoDocumento(descripcion);
                            }
                            else{
                                if(codigo.equals("Sanciones")){
                                    ok= mc.agregarTipoSancion(descripcion);
                                } 
                                else{
                                    if(codigo.equals("MantenimientoVehiculo")){
                                        ok= mc.agregarTipoMantenimientoVehiculo(descripcion);
                                    } 
                                    else{
                                        if(codigo.equals("ModelosArmamentos")){
                                            ok= mc.agregarModeloArmamento(descripcion);
                                        } 
                                        else{
                                            if(codigo.equals("DestinosArmamentos")){
                                                ok= mc.agregarDestino(descripcion);
                                            } 
                                            else{
                                                ok=mc.agregarEspecialidad(descripcion);
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                    if(ok){
                        sesion.setAttribute("mensaje", "Creado sastifactoriamente.");
                    }
                    else{
                        sesion.setAttribute("mensaje", "ERROR al crear.");
                    }
                    
                    
                }
                else{ //modificacion
                    if(codigo.equals("Familiares")){
                        ok=mc.modificarTipoFamiliar(id, descripcion);
                    }
                    else{
                        if(codigo.equals("Grados")){
                            String abreviacion = request.getParameter("abreviacion");
                            ok= mc.modificarGrado(id, descripcion,abreviacion);
                        }
                        else{
                            if(codigo.equals("Documentos")){
                                ok= mc.modificarTipoDocumento(id, descripcion);
                            }
                            else{
                                if(codigo.equals("Sanciones")){
                                    ok= mc.modificarTipoSancion(id, descripcion);
                                } 
                                else{
                                    if(codigo.equals("MantenimientoVehiculo")){
                                        ok= mc.modificarTipoMantenimientoVehiculo(id, descripcion);
                                    } 
                                    else{
                                        if(codigo.equals("ModelosArmamentos")){
                                            ok= mc.modificarModeloArmamento(id, descripcion);
                                        } 
                                        else{
                                            if(codigo.equals("DestinosArmamentos")){
                                                ok= mc.modificarDestino(id, descripcion);
                                            } 
                                            else{
                                                ok=mc.modificarEspecialidad(id, descripcion);
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                    if(ok){
                        sesion.setAttribute("mensaje", "Modificado sastifactoriamente.");
                    }
                    else{
                        sesion.setAttribute("mensaje", "ERROR al modificar.");
                    }
                   
                }
                
            }
            else{
                if(request.getParameter("elim")!= null){
                    int id= Integer.valueOf(request.getParameter("elim"));
                    if(codigo.equals("Familiares")){
                        ok=mc.eliminarTipoFamiliar(id);
                    }
                    else{
                        if(codigo.equals("Grados")){
                            ok= mc.eliminarGrado(id);
                        }
                        else{
                            if(codigo.equals("Documentos")){
                                ok= mc.eliminarTipoDocumento(id);
                            }
                            else{
                                if(codigo.equals("Sanciones")){
                                    ok= mc.eliminarTipoSancion(id);
                                } 
                                else{
                                    if(codigo.equals("MantenimientoVehiculo")){
                                        ok= mc.eliminarTipoMantenimientoVehiculo(id);
                                    } 
                                    else{
                                        if(codigo.equals("ModelosArmamentos")){
                                            ok= mc.eliminarModeloArmamento(id);
                                        } 
                                        else{
                                            if(codigo.equals("DestinosArmamentos")){
                                                ok= mc.eliminarDestino(id);
                                            } 
                                            else{
                                                ok=mc.eliminarEspecialidad(id);
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                    if(ok){
                        sesion.setAttribute("mensaje", "Eliminado sastifactoriamente.");
                    }
                    else{
                        sesion.setAttribute("mensaje", "ERROR al eliminar debido a que existe algún vinculo con el mismo.");
                    }
                    
                }
            }
            mc.CerrarConexionManejador();
            response.sendRedirect("tipos.jsp?codigo="+codigo);
                    
            
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
