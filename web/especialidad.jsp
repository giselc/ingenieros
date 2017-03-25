<%-- 
    Document   : especialidad
    Created on : Mar 21, 2017, 11:21:11 PM
    Author     : Gisel
--%>

<%@page import="java.util.ArrayList"%>
<%@page import="Manejadores.ManejadorCodigos"%>
<%@page import="Classes.Tipo"%>
<%@page import="Classes.Tipo"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<%@ include file="header.jsp" %>

<% 
    if(u.isAdmin() || u.isS1()){
        Manejadores.ManejadorCodigos mc= new ManejadorCodigos();
%>
<h1 align="center"><u>Agregar Especialidad</u></h1>
    <p align="left"><a href="personal.jsp?id=<%=request.getParameter("ci")%>"><img src="images/atras.png" width="15%"/></a></p>
     
    <table  width='70%' style="font-size: 130%; text-align: left" >
        <tr>
            <td valign='top' width='40%'>
                <img width="80%" src="images/especialidad.png" />
            </td>
            <td width='10%'>
                
            </td>
            <td width='50%'>
                <form method="post" name="formulario" id="formulario" action="Especialidades?ci=<%=request.getParameter("ci")%>">
                    <table>
                        <tr>
                            <td>
                                <%
                                ArrayList<Tipo> ap = mc.getEspecialidades();
                                out.print("<select name=\"idEspecialidad\" form=\"formulario\">");

                                    for(Tipo p: ap ){
                                        out.print("<option value='"+p.getId()+"'>"+ p.getDescripcion() +"</option>");
                                    }

                                out.print("</select>");
                                %>
                            </td>
                        </tr>
                    </table>  
                    <p align='right'><input type="submit"  value="Aceptar" /></p>
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