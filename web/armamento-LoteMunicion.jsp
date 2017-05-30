<%-- 
    Document   : armamento-LoteMunicion
    Created on : May 30, 2017, 2:44:45 PM
    Author     : Gisel
--%>

<%@page import="java.util.ArrayList"%>
<%@page import="Manejadores.ManejadorCodigos"%>
<%@page import="Classes.Municion"%>
<%@page import="Manejadores.ManejadorArmamento"%>
<%@page import="Classes.LoteMunicion"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<%@ include file="header.jsp" %>

<% 
    if(u.isAdmin() || u.isS4()){

%>
<% 


    LoteMunicion c = null;
    String mostrar="";
    ManejadorCodigos mc= new ManejadorCodigos();
    ManejadorArmamento mp= new ManejadorArmamento();
    if(request.getParameter("id")!=null){ //si es alta o si es ver
        int id= Integer.valueOf(request.getParameter("id"));
        c= mp.getMunicionLote(id);
    }
%>

<h1 align="center"><u><% if (c!=null){out.print("Modificar");}else{out.print("Agregar al lote: "+request.getParameter("lote"));}%></u></h1>
    <table  width='70%' style="font-size: 130%; text-align: left" >
        <tr>
            <td >
                <form method="post" name="formulario" id="formulario" action="ArmamentoLoteMunicion?id=<%if (c!=null){out.print(c.getId());}else{out.print("-1");} %>&lote=<%= request.getParameter("lote") %>">
                <table>
                    <tr>
                        <td>
                            <p><b>Municion:</b></p>
                        </td>
                        <td>
                            <%
                            ArrayList<Municion> ap = mc.getMuniciones();
                                String mostrarNombre="";
                                
                            if (c==null){
                                out.print("<select name=\"municion\" form=\"formulario\">");
                                
                                for(Municion p: ap ){
                                    out.print("<option value='"+p.getId()+"'>"+p.getId() +"</option>");
                                }
                               
                                out.print("</select>");
                            } 
                            else{
                                
                                out.print("<input value='"+c.getMunicion().getId()+"' readonly='readonly' name='municion'");
                            }
                            
                                    %>
                        </td>
                    </tr>
                    
                    <tr>
                        <td><b>Cantidad:</b> </td>
                        <td>
                            <p><input type="number" <% if (c!=null){out.print("value="+c.getCantidad());}%> name="cantidad" /></p>
                        </td>
                    </tr>
                    <tr>
                        <td><b>Cantidad munici&oacute;n viva:</b> </td>
                        <td>
                            <p><input type="number" <% if (c!=null){out.print("value="+c.getCantMunicionViva());}%> name="viva" /></p>
                        </td>
                    </tr>
                    <tr>
                        <td><b>Cantidad vainas:</b> </td>
                        <td>
                            <p><input type="number" <% if (c!=null){out.print("value="+c.getVainas());}%> name="vainas" /></p>
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
