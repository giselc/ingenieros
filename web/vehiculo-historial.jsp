<%-- 
    Document   : vehiculo-historial
    Created on : May 16, 2017, 8:59:43 PM
    Author     : Gisel
--%>

<%@page import="java.util.ArrayList"%>
<%@page import="Classes.ObservacionVehiculo"%>
<%@page import="Manejadores.ManejadorVehiculo"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<%
    if(request.getParameter("id")!=null){
        String idVehiculo=request.getParameter("id");
        ManejadorVehiculo mv = new ManejadorVehiculo();
%>
<table style="float: right" >
        <tr>
            <td>
            <p id="mensaje" style="color: #990000"><% if(session.getAttribute("mensaje")!=null){out.print("<img src='images/icono-informacion.png' width='3%' /> &nbsp;&nbsp;"+session.getAttribute("mensaje"));}%></p>
            <%
                session.setAttribute("mensaje",null);
            %>
            </td>
        </tr>
        <tr>
            <td style="width: 55%"><h3 style="float: left; font-family: sans-serif">Historial:</h3></td>
            <td style="width: 15%"><a href="vehiculo-observacion.jsp?vehiculo=<%=idVehiculo%>&id=-1" title="Agregar"><img width="20%" src='images/agregarLista.png' /></a> </td>
        </tr>
    </table>
            
               
    <table style="width: 100%;" align='center'>
            <%
               
                ArrayList<ObservacionVehiculo> a = mv.getVehiculoHistorial(idVehiculo);
                mv.CerrarConexionManejador();
                out.print("<tr style='background-color:#ffcc66'>");
                            out.print("<td style='width: 15%' align='center'><h3 style='margin:2%;'>Fecha</h3></td>");
                            out.print("<td style='width: 45%' align='center'><h3 style='margin:2%;'>Observación</h3></td>");
                            out.print("<td style='width: 10%' align='center'><h3 style='margin:2%;'>Mant.</h3></td>");
                            out.print("<td style='width: 10%' align='center'><h3 style='margin:2%;'>Hr o km Realizados</h3></td>");
                            out.print("<td style='width: 10%' align='center'></td>");
                            if(u.isAdmin()){
                                out.print("<td style='width: 10%' align='center'></td>");
                            }
                       out.print("</tr>" );
                int i=0;
                String color;
                for (ObservacionVehiculo s: a){
                        if ((i%2)==0){
                            color=" #ccccff";
                        }
                        else{
                            color=" #ffff99";
                        }
                        i++;

                    out.print("<tr style='background-color:"+color+"'>");
                    out.print("<td style='width: 15%' align='center'>"+s.getFecha()+"</td>");
                    out.print("<td style='width: 45%' align='center'>"+s.getObservacion()+"</td>");
                    out.print("<td style='width: 10%' align='center'>");
                            if(s.isMantenimiento()){
                                out.print(s.getTipoMantenimiento().getDescripcion());
                            }
                        out.print("</td>");
                    out.print("<td style='width: 10%' align='center'>"+s.getHorasKilometrosRealizados()+"</td>");
                    out.print("<td style='width: 10%' align='center'><a href='vehiculo-observacion.jsp?id="+s.getId()+"&vehiculo="+idVehiculo+"' ><img title='Ver' src='images/ver.png' width='25%' /></a></td>");
                   if(u.isAdmin()){
                        out.print("<td style='width: 10%' align='center'><form method='post' onsubmit=\"return confirmar(this,'')\" action='VehiculoObservacion?elim="+s.getId()+"&vehiculo="+idVehiculo+"'><input type='image' width='25%' title='Eliminar' src='images/eliminar.png' alt='Submit Form' /> </form></td>");
                    }
                   out.print("</tr>");
                }
            %> 
                
    </table>
<%
    }
%>
