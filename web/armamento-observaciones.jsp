<%-- 
    Document   : armamento-observacion
    Created on : May 22, 2017, 7:22:00 PM
    Author     : Gisel
--%>

<%@page import="Manejadores.ManejadorPersonal"%>
<%@page import="Classes.ObservacionArmamento"%>
<%@page import="Classes.Armamento"%>
<%@page import="java.util.ArrayList"%>
<%@page import="Manejadores.ManejadorArmamento"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<script>
    function confirmar1(f){
        var s="¿Seguro que desea eliminar la observación: ";
        var r=confirm(s);
        if (r==true)
        {
            f.submit();
            return true;
        }
        else{
            return false;
        }
    };
</script>
    <table style="float: right">
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
            <td style="width: 15%"><a href="armamento-observacion.jsp?armamento=<%= request.getParameter("id") %>" title="Agregar"><img width="30%" src='images/agregarLista.png' /></a> </td>
        </tr>
    </table>
    <table style="width: 100%;" align='center'>
            <%
                
                ArrayList<ObservacionArmamento> au = new ArrayList();
                if(request.getParameter("id")!=null){
                    ManejadorArmamento ma = new ManejadorArmamento();
                    au=ma.getObservacionesArmamento(Integer.valueOf(request.getParameter("id")));
                    ma.CerrarConexionManejador();
                }
                out.print("<tr style='background-color:#ffcc66'>");
                            out.print("<td style='width: 10%' align='center'><h3 style='margin:2%;'>Fecha</h3></td>");
                            out.print("<td style='width: 20%' align='center'><h3 style='margin:2%;'>Observaciones</h3></td>");
                            out.print("<td style='width: 10%' align='center'><h3 style='margin:2%;'>Escribiente</h3></td>");
                            out.print("<td style='width: 10%' align='center'></td>");
                            out.print("<td style='width: 10%' align='center'></td>");
                       out.print("</tr>" );
                int i=0;
                String color;
                for (ObservacionArmamento u1: au){
                        if ((i%2)==0){
                            color=" #ccccff";
                        }
                        else{
                            color=" #ffff99";
                        }
                        i++;

                    out.print("<tr style='background-color:"+color+"'>");
                    out.print("<td style='width: 10%' align='center'>"+u1.getFecha()+"</td>");
                    out.print("<td style='width: 20%' align='center'>"+u1.getObservaciones()+"</td>");
                    if(u1.getEscribiente()!=null){
                        out.print("<td style='width: 10%' align='center'>"+ManejadorPersonal.obtenerNombreCompleto(u1.getEscribiente())+"</td>");
                    }
                    else{
                       out.print("<td style='width: 10%' align='center'></td>"); 
                    }
                    out.print("<td style='width: 10%' align='center'><a href='armamento-observacion.jsp?armamento="+ request.getParameter("id")+"&id="+u1.getId()+"'><img src='images/ver.png' width='25%' /></a></td>");
                    out.print("<td style='width: 10%' align='center'><form method='post' onsubmit=\"return confirmar1(this)\" action='ObservacionArmamento?armamento="+ request.getParameter("id") +"&elim="+u1.getId()+"'><input type='image' width='25%' title='Eliminar' src='images/eliminar.png' alt='Submit Form' /> </form></td>");
                    out.print("</tr>");
                }
            %> 
                
    </table>