<%-- 
    Document   : vehiculo-alerta
    Created on : May 19, 2017, 4:14:21 PM
    Author     : Gisel
--%>

<%@page import="Classes.Tipo"%>
<%@page import="java.util.ArrayList"%>
<%@page import="Manejadores.ManejadorCodigos"%>
<%@page import="Manejadores.ManejadorVehiculo"%>
<%@page import="Classes.AlertaVehiculo"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<%@ include file="header.jsp" %>
<% 
    if(u.isAdmin()){

%>
<% 
    AlertaVehiculo u1=null;
    if(request.getParameter("id")!=null){
        int id = Integer.valueOf(request.getParameter("id"));
        ManejadorVehiculo mv = new ManejadorVehiculo();
        u1= mv.getAlertaVehiculo(id);
        mv.CerrarConexionManejador();
    }
%>
<h1 align="center"><u><% if (u1!=null){out.print("Alerta para el vehículo: "+request.getParameter("vehiculo"));}else{out.print("Agregar alerta para el vehículo: "+request.getParameter("vehiculo"));}%></u></h1>


    <table  width='70%' style="font-size: 130%" >
        <tr>
            <td valign='top' width='40%'>
                <img src="images/alerta.png" width="80%" />
            </td>
            <td width='10%'>
                
            </td>
            <td width='50%'>
                <form method="post" id="formulario1" action="AlertaVehiculo?vehiculo=<%=request.getParameter("vehiculo")%>&id=<%=request.getParameter("id")%>" >
                <table>
                    <tr>
                        <td>
                            <p><b>Tipo Mantenimiento:</b></p>
                        </td>
                        <td colspan="2">
                            <%
                            if(u1==null){
                            %>
                                <select name="idTipoMantenimiento" form="formulario1" style="width: 100%">
                                    <%
                                    ManejadorCodigos mc= new ManejadorCodigos();
                                    ArrayList<Tipo> ag = mc.getTiposMantenimientoVehiculo();
                                    mc.CerrarConexionManejador();
                                    for(Tipo g: ag ){
                                        String s="";
                                        out.print("<option " + s +" value='"+String.valueOf(g.getId()) +"'>"+ g.getDescripcion() +"</option>");
                                    }
                                    %>
                                </select>
                            <%
                            }
                            else{
                                %>
                                <input type="text" value="<%= u1.getTipoMantenimiento().getDescripcion() %>" readonly="readonly"/>
                                <%
                            }
                            %>
                        </td>
                    </tr>
                    <tr>
                        <td>
                            <p><b>Alerta cada</b></p>
                        </td>
                        <td>
                            <p align="center"><input style="width: 100%" type="number" <% if (u1!=null){out.print("value='"+u1.getHorasKilometros()+"' readonly=\"readonly\"");}%> required='required' name="HorasKilometros" /></p> 
                        </td>
                        <td>
                            <p>Hs o Km.</p>
                        </td>
                    </tr>
                    <tr>
                        <td>
                            <p><b>Notificar</b></p>
                        </td>
                        <td>
                            <p align="center"><input style="width: 100%" type="number" <% if (u1!=null){out.print("value='"+u1.getHorasKilometrosAlerta()+"' readonly=\"readonly\"");}%> required='required' name="HorasKilometrosAlerta" /></p> 
                        </td>
                        <td>
                            <p>Hs o Km antes.</p>
                        </td>
                    </tr>
                </table>
                        <%
                        if(u1==null){
                        %>
                        <p align='right'><input type="submit"  value="Aceptar" /></p>
                        <%
                        }
                        %>
                </form>
            </td>
        </tr>
    </table>  
            




<% 
    }
    else{
         response.sendRedirect("");
    }

%>
<%@ include file="footer.jsp" %>
