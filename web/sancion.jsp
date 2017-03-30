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
    int ci=-1;
    if(request.getParameter("id")!=null){ //si es alta o si es ver
        int id= Integer.valueOf(request.getParameter("id"));
        s= mp.getSancion(id);
        ci=s.getA().getCi();
    }
%>

    <%
        if(request.getParameter("ci")!=null){
            ci= Integer.valueOf(request.getParameter("ci"));
        }
        if(ci!=-1){
            %>
            <p align="left"><a href="personal.jsp?id=<%=ci%>"><img src="images/atras.png" width="15%"/></a></p>
            <%
            
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
                                String fecha= (hoy.getYear()+1900) +"-"+ m +"-"+hoy.getDate();
                              %>
                              <input type=date name="fecha" <%=fecha%> <%if( s!=null){out.print("value="+s.getFecha());}else{out.print("value="+fecha);} %> required="required"/>
                        </td>
                    </tr>
                    <tr>
                        <td>
                            <p><b>Para:</b></p>
                        </td>
                        <td>
                            <%
                            ArrayList<Personal> ap = mp.getListaPersonalBasico();
                                String mostrarNombre="";
                                
                            if (request.getParameter("ci")==null && request.getParameter("id")==null){
                                out.print("<select name=\"a\" form=\"formulario\">");
                                
                                for(Personal p: ap ){
                                    mostrarNombre=p.getGrado().getAbreviacion()+" "+p.getNombre()+" "+ p.getApellido();
                                    out.print("<option value='"+p.getCi()+"'>"+ mostrarNombre +"</option>");
                                }
                               
                                out.print("</select>");
                            } 
                            else{
                                Personal p=null;
                                if(request.getParameter("ci")!=null){
                                    p= mp.getPersonalBasico(Integer.valueOf(request.getParameter("ci")));
                                }
                                else{
                                    p=s.getA();
                                }
                                out.print(p.getGrado().getAbreviacion()+" "+p.getNombre()+" "+p.getApellido());
                                out.print("<input value='"+p.getCi()+"' hidden='hidden' name='a'");
                            }
                            
                                    %>
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
                                    mostrarNombre=p.getGrado().getAbreviacion()+" "+p.getNombre()+" "+ p.getApellido();
                                    String selected ="";
                                    if(s!=null && p.getCi()==s.getOrden().getCi()){
                                        selected="selected";
                                    }
                                    out.print("<option " + selected +" value='"+p.getCi()+"'>"+ mostrarNombre +"</option>");
                                }
                                mp.CerrarConexionManejador();
                                mc.CerrarConexionManejador();
                                %>
                            </select>
                        </td>
                    </tr>
                </table>
                            <%
                            if(request.getParameter("id")!=null && Integer.valueOf(request.getParameter("id"))!=-1){
                                out.print("Las sanciones no se pueden modificar.");
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
