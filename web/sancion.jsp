<%-- 
    Document   : sancion
    Created on : Mar 19, 2017, 9:52:07 PM
    Author     : Gisel
--%>

<%@page import="java.text.SimpleDateFormat"%>
<%@page import="Classes.Tipo"%>
<%@page import="Manejadores.ManejadorCodigos"%>
<%@page import="Classes.Personal"%>
<%@page import="java.util.ArrayList"%>
<%@page import="Manejadores.ManejadorPersonal"%>
<%@page import="Classes.Sancion"%>
<%@ include file="header.jsp" %>

<% 
    if(u.isAdmin() || u.isS1()){

%>
<% 


    Sancion s = null;
    String mostrar="";
    
    ManejadorPersonal mp= new ManejadorPersonal();
    ManejadorCodigos mc= new ManejadorCodigos();
    if(request.getParameter("id")!=null){ //si es alta o si es ver
        int id= Integer.valueOf(request.getParameter("id"));
        s= mp.getSancion(id);
    }
%>
<h1 align="center"><u><% if (s!=null){out.print("Ver sanción");}else{out.print("Alta sanción");}%></u></h1>


    <table  width='70%' style="font-size: 130%; text-align: left" >
        <tr>
            <td valign='top' width='40%'>
                <img width="80%" src="images/sancion.png" />
            </td>
            <td width='10%'>
                
            </td>
            <td width='50%'>
                <form method="post" name="formulario" id="formulario" action="Sancion?id=<%if (s!=null){out.print(s.getId());}else{out.print("-1");} %>&ci=<%if (request.getParameter("ci")!=null){out.print(request.getParameter("ci"));}else{out.print("-1");} %>">
                <table>
                    <tr>
                        <td>
                            <p><b>Fecha:</b></p>
                        </td>
                        <td>
                            <input type=date name="fecha" <%if( s!=null){out.print("value="+s.getFecha());}else{out.print(new SimpleDateFormat("yyyy-MM-dd").toString());} %> required="required"/>
                        </td>
                    </tr>
                    <tr>
                        <td>
                            <p><b>Para:</b></p>
                        </td>
                        <td>
                            <select name="a" form="formulario" <%if (request.getParameter("ci")!=null){out.print("readonly");} %> required='required'>
                                <%
                                ArrayList<Personal> ap = mp.getListaPersonalBasico();
                                String mostrarNombre="";
                                
                                for(Personal p: ap ){
                                    mostrarNombre=p.getGrado().getDescripcion()+" "+p.getNombre()+" "+ p.getApellido();
                                    String selected ="";
                                    if(s!=null && p.getCi()==s.getA().getCi()){
                                        selected="selected";
                                    }
                                    out.print("<option " + selected +" value='"+p.getCi()+"'>"+ mostrarNombre +"</option>");
                                }
                                %>
                            </select>
                        </td>
                    </tr>
                    <tr>
                        <td>
                            <p><b>Tipo de Sancion:</b></p>
                        </td>
                        <td>
                            <select name="tipoSancion" form="formulario" <%if (request.getParameter("ci")!=null){out.print("readonly");} %> required='required'>
                                <%
                                ArrayList<Tipo> at = mc.getTiposSanciones();
                                
                                for(Tipo t: at ){
                                    String selected ="";
                                    if(s!=null && t.getId()==s.getTipo().getId()){
                                        selected="selected";
                                    }
                                    out.print("<option " + selected +" value='"+t.getId()+"'>"+ t.getDescripcion() +"</option>");
                                }
                                %>
                            </select>
                        </td>
                    </tr>
                    <tr>
                        <td style="vertical-align: top"><b>Parte:</b> </td>
                        <td>
                            <textarea rows="4" cols="50" name="parte" form="formulario"><% if(s!=null){out.print(s.getParte());} %></textarea>
                        </td>
                    </tr>
                    <tr>
                        <td>
                            <p><b>D&iacute;as:</b></p>
                        </td>
                        <td>
                            <p><input type="number" <% if (s!=null){out.print("value="+s.getDias());}%> required='required' name="dias" /></p>
                        </td>
                    </tr>
                    <tr>
                        <td>
                            <p><b>Orden:</b></p>
                        </td>
                        <td>
                            <select name="orden" form="formulario" <%if (request.getParameter("ci")!=null){out.print("readonly");} %> required='required'>
                                <%
                                for(Personal p: ap ){
                                    mostrarNombre=p.getGrado().getDescripcion()+" "+p.getNombre()+" "+ p.getApellido();
                                    String selected ="";
                                    if(s!=null && p.getCi()==s.getOrden().getCi()){
                                        selected="selected";
                                    }
                                    out.print("<option " + selected +" value='"+p.getCi()+"'>"+ mostrarNombre +"</option>");
                                }
                                %>
                            </select>
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
