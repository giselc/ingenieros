<%-- 
    Document   : consultaMedica.jsp
    Created on : Apr 4, 2017, 7:26:40 PM
    Author     : Gisel
--%>

<%@page import="Classes.Personal"%>
<%@page import="java.util.ArrayList"%>
<%@page import="Manejadores.ManejadorPersonal"%>
<%@page import="Classes.ConsultaMedica"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<%@ include file="header.jsp" %>

<% 
    if(u.isAdmin() || u.isS1()){

%>
<% 


    ConsultaMedica c = null;
    String mostrar="";
    
    ManejadorPersonal mp= new ManejadorPersonal();
    if(request.getParameter("id")!=null){ //si es alta o si es ver
        int id= Integer.valueOf(request.getParameter("id"));
        c= mp.getConsultaMedica(id);
    }
%>

    <%
        if(request.getParameter("ci")!=null){
            %>
            <p align="left"><a href='personal.jsp?id=<%=request.getParameter("ci")%>'><img src="images/atras.png" width="15%"/></a></p>
            <%
            
        }
        else{
            %>
            <p align="left"><a href='s1-historiaClinica.jsp'><img src="images/atras.png" width="15%"/></a></p>
            <%
        }
    %>
    <h1 align="center"><u><% if (c!=null){out.print("Ver");}else{out.print("Alta");}%></u></h1>
    <table  width='70%' style="font-size: 130%; text-align: left" >
        <tr>
            <td valign='top' width='40%'>
                <img width="80%" src="images/consultaMedica.png" />
            </td>
            <td width='10%'>
                
            </td>
            <td width='50%'>
                <form method="post" name="formulario" id="formulario" action="ConsultaMedica?id=<%if (c!=null){out.print(c.getId());}else{out.print("-1");} %>">
                <table>
                    <tr>
                        <td>
                            <p><b>Fecha Inicio:</b></p>
                        </td>
                        <td>
                            <% 
                                java.util.Date hoy = new java.util.Date(); 
                                String m="";
                                if(hoy.getMonth()+1 <= 9 ){
                                    m= "0"+(hoy.getMonth()+1);
                                }
                                String d="";
                                if(hoy.getDate() <= 9 ){
                                    d= "0"+(hoy.getDate());
                                }
                                String fecha= (hoy.getYear()+1900) +"-"+ m +"-"+d;
                              %>
                              <input type=date name="fechaInicio" <%if( c!=null){out.print("value="+c.getFechaInicio());}else{out.print("value="+fecha);} %> required="required"/>
                        </td>
                    </tr>
                    <tr>
                        <td>
                            <p><b>Fecha Fin:</b></p>
                        </td>
                        <td>
                            <input type=date name="fechaFin" <%if( c!=null){out.print("value="+c.getFechaFin());}else{out.print("value="+fecha);} %> required="required"/>
                        </td>
                    </tr>
                    <tr>
                        <td>
                            <p><b>Paciente:</b></p>
                        </td>
                        <td>
                            <%
                            ArrayList<Personal> ap = mp.getListaPersonalBasico();
                                String mostrarNombre="";
                                
                            if (request.getParameter("ci")==null && request.getParameter("id")==null){
                                out.print("<select name=\"idPersonal\" form=\"formulario\">");
                                
                                for(Personal p: ap ){
                                    out.print("<option value='"+p.getCi()+"'>"+mp.obtenerNombreCompleto(p) +"</option>");
                                }
                               
                                out.print("</select>");
                            } 
                            else{
                                Personal p=null;
                                if(request.getParameter("ci")!=null){
                                    p= mp.getPersonalBasico(Integer.valueOf(request.getParameter("ci")));
                                }
                                else{
                                    p=c.getIdPersonal();
                                }
                                out.print(mp.obtenerNombreCompleto(p));
                                out.print("<input value='"+p.getCi()+"' hidden='hidden' name='idPersonal'");
                            }
                            
                                    %>
                        </td>
                    </tr>
                    
                    <tr>
                        <td style="vertical-align: top"><b>Diagn&oacute;stico:</b> </td>
                        <td>
                            <textarea rows="4" cols="50" name="diagnostico" form="formulario"><% if(c!=null){out.print(c.getDiagnostico());} %></textarea>
                        </td>
                    </tr>
                    <tr>
                        <td>
                            <p><b>M&eacute;dico:</b></p>
                        </td>
                        <td>
                        <%
                            out.print("<select name=\"idMedico\" form=\"formulario\">");
                                
                            for(Personal p: ap ){
                                out.print("<option value='"+p.getCi()+"'>"+ mp.obtenerNombreCompleto(p) +"</option>");
                            }

                            out.print("</select>");
                           
                            
                        %>
                        </td>
                    </tr>
                    <tr>
                        <td style="vertical-align: top"><b>Tratamiento:</b> </td>
                        <td>
                            <textarea rows="4" cols="50" name="tratamiento" form="formulario"><% if(c!=null){out.print(c.getTratamiento());} %></textarea>
                        </td>
                    </tr>
                </table>
                            <%
                            if(!u.isAdmin() && request.getParameter("id")!=null && Integer.valueOf(request.getParameter("id"))!=-1){
                                out.print("Las consultas no se pueden modificar.");
                            }
                            else{
                            
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
