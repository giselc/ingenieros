/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package servlet;

import Classes.RecordListarPersonal;
import Manejadores.ManejadorArmamento;
import Manejadores.ManejadorClases;
import Manejadores.ManejadorCodigos;
import Manejadores.ManejadorPersonal;
import Manejadores.ManejadorVehiculo;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author Gisel
 */
public class Listar extends HttpServlet {

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
            String tipo = request.getParameter("tipo");
            if(tipo.equals("usuarios")){
                ManejadorClases mc = new ManejadorClases();
                mc.imprimirUsuarios(out, u);
                mc.CerrarConexionManejador();
            }
            else{
                ManejadorCodigos mc = new ManejadorCodigos();
                if(tipo.equals("unidadesMilitares")){
                    mc.imprimirUnidadesMilitares(out);
                    mc.CerrarConexionManejador();
                }
                else{
                    if(tipo.equals("tipos")){
                        String codigos = request.getParameter("codigo");
                        mc.imprmirTipo(out, codigos);
                        mc.CerrarConexionManejador();
                    }
                    else{
                        if(tipo.equals("sanciones")){
                            int ci=-1;
                            if(request.getParameter("ci")!=null){
                                ci=Integer.valueOf(request.getParameter("ci"));
                            }
                            ManejadorPersonal mp = new ManejadorPersonal();
                            int tipoSancion= -1;
                            if(!request.getParameter("tipoSancion").equals("TODOS")){
                                tipoSancion = Integer.valueOf(request.getParameter("tipoSancion"));
                            }
                            mp.imprimirSanciones(ci, request.getParameter("fechaDesde"), request.getParameter("fechaHasta"), tipoSancion,out);
                            mp.CerrarConexionManejador();
                        }
                        if(tipo.equals("personal")){
                            int ci=Integer.valueOf(request.getParameter("ci"));
                            boolean basico=request.getParameter("datosBasicos")!=null;
                            boolean familiares=request.getParameter("familiares")!=null;
                            boolean apoderado=request.getParameter("apoderado")!=null;
                            boolean especialidades=request.getParameter("especialidades")!=null;
                            ManejadorPersonal mp = new ManejadorPersonal();
                            mp.imprimirPersonal(ci, basico, familiares, apoderado, especialidades, out);
                            mp.CerrarConexionManejador();
                            
                        }
                        else{
                            if(tipo.equals("todoElPersonal")){
                                RecordListarPersonal rl= new RecordListarPersonal();
                                rl.nombreCompleto = request.getParameter("nombreCompleto")!=null;
                                rl.carneSalud = request.getParameter("carneSalud")!=null;
                                rl.ciVto = request.getParameter("ciVto")!=null;
                                rl.ci = request.getParameter("ci")!=null;
                                rl.credencial = request.getParameter("credencial")!=null;
                                rl.fechaNac = request.getParameter("fechaNac")!=null;
                                rl.liciencia = request.getParameter("licencia")!=null;
                                rl.misiones = request.getParameter("misiones")!=null;
                                rl.pasaporte = request.getParameter("pasaporte")!=null;
                                rl.vtoCarne = request.getParameter("vtoCarne")!=null;
                                rl.vtoLic = request.getParameter("vtoLic")!=null;
                                rl.vtoPas = request.getParameter("vtoPas")!=null;
                                ManejadorPersonal mp = new ManejadorPersonal();
                                mp.imprimirTodoElPersonal(rl, out);
                                mp.CerrarConexionManejador();
                            }
                            else{
                                if(tipo.equals("historiaClinica")){
                                    int ci=-1;
                                    if(request.getParameter("ci")!=null){
                                        ci=Integer.valueOf(request.getParameter("ci"));
                                    }
                                    ManejadorPersonal mp = new ManejadorPersonal();
                                    mp.imprimirHistoriaClinica(ci, request.getParameter("fechaDesde"), request.getParameter("fechaHasta"),out);
                                    mp.CerrarConexionManejador();
                                }
                                else{
                                    if(tipo.equals("sancionados")){
                                        ManejadorPersonal mp = new ManejadorPersonal();
                                        mp.imprimirSancionados(out);
                                        mp.CerrarConexionManejador();
                                    }
                                    else{
                                        if(tipo.equals("vehiculo")){
                                            ManejadorVehiculo mv = new ManejadorVehiculo();
                                            mv.imprimirVehiculo(request.getParameter("id"), request.getParameter("historial")!=null, request.getParameter("ficha")!=null, out);
                                            mv.CerrarConexionManejador();
                                        }
                                        else{
                                            if(tipo.equals("vehiculos")){
                                                ManejadorVehiculo mv = new ManejadorVehiculo();
                                                mv.imprimirVehiculos(out);
                                                mv.CerrarConexionManejador();
                                            }
                                            else{
                                                if(tipo.equals("alertasMantenimiento")){
                                                    ManejadorVehiculo mv = new ManejadorVehiculo();
                                                    mv.imprimirAlertasMantenimiento(out);
                                                    mv.CerrarConexionManejador();
                                                }
                                                else{
                                                    if(tipo.equals("armamentos")){
                                                        ManejadorArmamento mv = new ManejadorArmamento();
                                                        mv.imprimirArmamentos(out);
                                                        mv.CerrarConexionManejador();
                                                    }
                                                    else{
                                                        if(tipo.equals("armamento")){
                                                            ManejadorArmamento mv = new ManejadorArmamento();
                                                            mv.imprimirArmamento(Integer.valueOf(request.getParameter("id")),request.getParameter("ficha")!=null,request.getParameter("historial")!=null,out);
                                                            mv.CerrarConexionManejador();
                                                        }
                                                    }
                                                }
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
                mc.CerrarConexionManejador();
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
