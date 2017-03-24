<%-- 
    Document   : especialidades
    Created on : Mar 21, 2017, 11:20:56 PM
    Author     : Gisel
--%>

<%@page import="Classes.Especialidad"%>
<%@page import="java.util.ArrayList"%>
<%@page import="Manejadores.ManejadorPersonal"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<%
    if(request.getParameter("id")!=null){
        int ci=Integer.valueOf(request.getParameter("id"));
        ManejadorPersonal mp = new ManejadorPersonal();
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
            <td style="width: 55%"><h3 style="float: left; font-family: sans-serif">Personal:</h3></td>
            <td style="width: 15%"><a href="Especialidad.jsp?ci=<%=ci%>" title="Agregar"><img width="30%" src='images/agregarLista.png' /></a> </td>
        </tr>
    </table>
            
               
    <table style="width: 50%;" align='center'>
            <%
               
                ArrayList<Especialidad> a = mp.getEspecialidadesListar(ci);
                out.print("<tr style='background-color:#ffcc66'>");
                            out.print("<td style='width: 10%' align='center'><h3 style='margin:2%;'>Especialidad</h3></td>");
                            out.print("<td style='width: 10%' align='center'></td>");
                       out.print("</tr>" );
                int i=0;
                String color;
                for (Especialidad s: a){
                        if ((i%2)==0){
                            color=" #ccccff";
                        }
                        else{
                            color=" #ffff99";
                        }
                        i++;

                    out.print("<tr style='background-color:"+color+"'>");
                    out.print("<td style='width: 10%' align='center'>"+s.getDescripcion()+"</td>");
                    out.print("<td style='width: 10%' align='center'><form method='post' onsubmit=\"return confirmar(this,'')\" action='Especialidad?elim="+s.getId()+"&ci="+ci+"'><input type='image' width='25%' title='Eliminar' src='images/eliminar.png' alt='Submit Form' /> </form></td>");
                    out.print("</tr>");
                }
            %> 
                
    </table>
<%
    }
%>