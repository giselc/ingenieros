<%-- 
    Document   : unidadMilitar
    Created on : Mar 7, 2017, 10:55:55 PM
    Author     : Gisel
--%>

<%@page import="Classes.UnidadMilitar"%>
<%@page import="Manejadores.ManejadorCodigos"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<%@ include file="header.jsp" %>
<% 
    if(u.isAdmin()){

%>
<% 
    UnidadMilitar u1=null;
    if(request.getParameter("id")!=null){
        int id = Integer.valueOf(request.getParameter("id"));
        ManejadorCodigos mc = new ManejadorCodigos();
        u1= mc.getUnidadMilitar(id);
        mc.CerrarConexionManejador();
    }
%>
<h1 align="center"><u><% if (u1!=null){out.print("Unidad Militar: "+u1.getNombre());}else{out.print("Agregar Unidad Militar");}%></u></h1>


    <table  width='70%' style="font-size: 130%" >
        <tr>
            <td valign='top' width='40%'>
                <img src="images/ejercito.png" width="80%" />
            </td>
            <td width='10%'>
                
            </td>
            <td width='50%'>
                <form method="post" action="UnidadMilitar?id=<%if (u1!=null){out.print(u1.getId());}else{out.print("-1");} %>" >
                <table>
                    <tr>
                        <td>
                            <p><b>Nombre:</b></p>
                        </td>
                        <td>
                            <p align="center"><input type="text" value="<% if (u1!=null){out.print(u1.getNombre());}%>" <% if (u1!=null){out.print("readonly='readonly'");}else{out.print("required='required'");}%> name="nombre" /></p>
                        </td>
                    </tr>
                    <tr>
                        <td>
                            <p><b>Tel&eacute;fono</b></p>
                        </td>
                        <td>
                            <p><input type="text" value="<% if (u1!=null){out.print(u1.getTelefono());}%>" name="telefono" required="required"/></p>
                        </td>
                    </tr>
                    <tr>
                        <td>
                            <p><b>Correo</b></p>
                        </td>
                        <td>
                            <p><input type="email" value="<% if (u1!=null){out.print(u1.getCorreo());}%>" name="correo" required="required"/></p>
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
