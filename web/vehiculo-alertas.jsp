<%-- 
    Document   : vehiculo-alertas
    Created on : May 19, 2017, 3:15:46 PM
    Author     : Gisel
--%>

<%@page import="Classes.AlertaVehiculo"%>
<%@page import="java.util.ArrayList"%>
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
            <td style="width: 55%"><h3 style="float: left; font-family: sans-serif">Configuraci&oacute;n de alertas:</h3></td>
            <td style="width: 15%"><a href="vehiculo-alerta.jsp?vehiculo=<%=idVehiculo%>&id=-1" title="Agregar"><img width="20%" src='images/agregarLista.png' /></a> </td>
        </tr>
    </table>
            
               
    <table style="width: 100%;" align='center'>
            <%
               
                ArrayList<AlertaVehiculo> a = mv.getAlertasVehiculo(idVehiculo);
                mv.CerrarConexionManejador();
                out.print("<tr style='background-color:#ffcc66'>");
                            out.print("<td style='width: 15%' align='center'><h3 style='margin:2%;'>Tipo Mantenimiento</h3></td>");
                            out.print("<td style='width: 50%' align='center'><h3 style='margin:2%;'>cada</h3></td>");
                            out.print("<td style='width: 15%' align='center'><h3 style='margin:2%;'>notificar faltando</h3></td>");
                            out.print("<td style='width: 10%' align='center'></td>");
                            if(u.isAdmin()){
                                out.print("<td style='width: 10%' align='center'></td>");
                            }
                       out.print("</tr>" );
                int i=0;
                String color;
                for (AlertaVehiculo s: a){
                        if ((i%2)==0){
                            color=" #ccccff";
                        }
                        else{
                            color=" #ffff99";
                        }
                        i++;

                    out.print("<tr style='background-color:"+color+"'>");
                    out.print("<td style='width: 15%' align='center'>"+s.getTipoMantenimiento().getDescripcion()+"</td>");
                    out.print("<td style='width: 50%' align='center'>"+s.getHorasKilometros()+"</td>");
                    out.print("<td style='width: 15%' align='center'>"+s.getHorasKilometrosAlerta()+"</td>");
                    out.print("<td style='width: 10%' align='center'><a href='vehiculo-alerta.jsp?id="+s.getId()+"' ><img title='Ver' src='images/ver.png' width='25%' /></a></td>");
                   if(u.isAdmin()){
                        out.print("<td style='width: 10%' align='center'><form method='post' onsubmit=\"return confirmar(this,'')\" action='AlertaVehiculo?elim="+s.getId()+"&vehiculo="+idVehiculo+"'><input type='image' width='25%' title='Eliminar' src='images/eliminar.png' alt='Submit Form' /> </form></td>");
                    }
                   out.print("</tr>");
                }
            %> 
                
    </table>
<%
    }
%>
