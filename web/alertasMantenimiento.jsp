<%-- 
    Document   : alertasMantenimiento
    Created on : May 15, 2017, 6:45:21 PM
    Author     : Gisel
--%>

<%@page import="Classes.AlertaMantenimiento"%>
<%@page import="Manejadores.ManejadorVehiculo"%>
<%@page import="Manejadores.ManejadorClases"%>
<%@page import="Classes.AlertaVehiculo"%>
<%@page import="Classes.UnidadMilitar"%>
<%@page import="java.util.ArrayList"%>
<%@page import="Manejadores.ManejadorCodigos"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<%@ include file="header.jsp" %>
<% 
    if(u.isAdmin() || u.isS4()){

%>

<script>
    function listar(form) {//Funcion creada para no perder la sesion luego del submit
        form.submit();
        return false;
    };
    function confirmar(f){
        var s="¿Seguro que desea eliminar la Alerta: ";
        var s1= s.concat(um,"?");
        var r=confirm(s1);
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
<form method="post" target="_blank" onsubmit="return listar(this)" name="formListar" action='Listar?tipo=alertasMantenimiento'>
    
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
            <td style="width: 55%"><h3 style="float: left; font-family: sans-serif">Alertas de mantenimiento de veh&iacute;culos:</h3></td>
            <td style="width: 15%"><a href="index2.jsp"><img src="images/atras.png" width="100%"/></a></td>
            <td style="width: 15%"><input type="image" width="30%" title="Imprimir alertas"src="images/imprimir.png" alt="Submit Form" /></td>
        </tr>
    </table>
</form>    
    <table style="width: 100%;" align='center'>
            <%
                ManejadorVehiculo  mc = new ManejadorVehiculo();
                ArrayList<AlertaMantenimiento> au = mc.getAlertasMantenimiento();
                mc.CerrarConexionManejador();
                out.print("<tr style='background-color:#ffcc66'>");
                            out.print("<td style='width: 20%' align='center'><h3 style='margin:2%;'>Fecha</h3></td>");
                            out.print("<td style='width: 20%' align='center'><h3 style='margin:2%;'>Vehiculo</h3></td>");
                            out.print("<td style='width: 30%' align='center'><h3 style='margin:2%;'>Tipo Mantenimiento</h3></td>");
                            out.print("<td style='width: 20%' align='center'><h3 style='margin:2%;'>Hr o Km faltantes</h3></td>");
                            out.print("<td style='width: 10%' align='center'></td>");
                       out.print("</tr>" );
                int i=0;
                String color;
                for (AlertaMantenimiento u1: au){
                        if ((i%2)==0){
                            color=" #ccccff";
                        }
                        else{
                            color=" #ffff99";
                        }
                        i++;

                    out.print("<tr style='background-color:"+color+"'>");
                    out.print("<td style='width: 20%' align='center'>"+u1.getFecha()+"</td>");
                    out.print("<td style='width: 20%' align='center'>"+u1.getAlertaVehiculo().getIdVehiculo()+"</td>");
                    out.print("<td style='width: 30%' align='center'>"+u1.getAlertaVehiculo().getTipoMantenimiento().getDescripcion()+"</td>"); 
                    out.print("<td style='width: 20%' align='center'>"+u1.getHorasKilometrosAlcanzados()+"</td>"); 
                    out.print("<td style='width: 10%' align='center'><form method='post' onsubmit=\"return confirmar(this)\" action='AlertaMantenimiento?elim="+u1.getId()+"'><input type='image' width='25%' title='Eliminar' src='images/eliminar.png' alt='Submit Form' /> </form></td>");
                    out.print("</tr>");
                }
            %> 
                
    </table>
        
<% 
    }
    else{
         response.sendRedirect("");
    }

%>
<%@ include file="footer.jsp" %>

