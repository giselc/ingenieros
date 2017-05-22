<%-- 
    Document   : municion
    Created on : May 22, 2017, 2:29:06 AM
    Author     : Gisel
--%>

<%@page import="Manejadores.ManejadorCodigos"%>
<%@page import="Classes.Municion"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<%@ include file="header.jsp" %>
<% 
    if(u.isAdmin()){

%>
<h1 align="center"><u>Agregar Munici&oacute;n</u></h1>


    <table  width='70%' style="font-size: 130%" >
        <tr>
            <td valign='top' width='40%'>
                <img src="images/municion.png" width="80%" />
            </td>
            <td width='10%'>
                
            </td>
            <td width='50%'>
                <form method="post" action="Municion?id=-1" >
                <table>
                    <tr>
                        <td>
                            <p><b>Calibre:</b></p>
                        </td>
                        <td>
                            <p align="center"><input type="text" required name="calibre" /></p>
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

