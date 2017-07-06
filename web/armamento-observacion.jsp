<%-- 
    Document   : armamento-observacion
    Created on : May 25, 2017, 10:14:48 AM
    Author     : Gisel
--%>

<%@page import="java.util.ArrayList"%>
<%@page import="Classes.Personal"%>
<%@page import="Manejadores.ManejadorPersonal"%>
<%@page import="Manejadores.ManejadorArmamento"%>
<%@page import="Classes.ObservacionArmamento"%>
<%@ include file="header.jsp" %>
<% 
    if(u.isAdmin()|| u.isS4()){

%>
<% 
    ObservacionArmamento v=null;    
    int numero= Integer.valueOf(request.getParameter("armamento"));
    if(request.getParameter("id")!=null){
        int id = Integer.valueOf(request.getParameter("id"));
        ManejadorArmamento mv = new ManejadorArmamento();
        v= mv.getObservacionArmamento(id);
        mv.CerrarConexionManejador();
    }
%>
<p align="left"><a href="armamento.jsp?id=<%= request.getParameter("armamento") %>"><img src="images/atras.png" width="15%"/></a></p> 
<h1 align="center"><u><% if (v!=null){out.print("Editar Observación armamento");}else{out.print("Agregar observación armamento");}%></u></h1>
<form method="post" name="formulario" id="formulario" action="ObservacionArmamento?id=<%if (v!=null){out.print(v.getId());}else{out.print("-1");} %>&armamento=<%= request.getParameter("armamento") %>" >
    <table  width='70%' align='center' style="text-align: left">
        
        <tr>
            <td>
                <p><b>Fecha:</b></p>
            </td>
            <td>
                <input type="date" <% if (v!=null){out.print("value='"+v.getFecha()+"'");}%> name="fecha" />
            </td>
        </tr>
        <tr>
            <td style="vertical-align: top"><b>Observaciones:</b> </td>
            <td>
                <textarea rows="4" cols="50" name="observaciones" form="formulario"><% if(v!=null){out.print(v.getObservaciones());} %></textarea>
            </td>
        </tr>
        <tr>
            <td>
                <p><b> Escribiente:</b></p>
            </td>
            <td>
                <select name="idEscribiente" form="formulario">
                    <%
                    ManejadorPersonal mp= new ManejadorPersonal();
                    ArrayList<Personal> ap = mp.getListaPersonalBasico();
                    String selected = "";
                    if(v==null || v.getEscribiente()==null){
                        selected="selected";
                    }
                    out.print("<option " + selected +" value='-1'>NO ESCRIBIENTE</option>");
                    for(Personal m: ap ){
                        selected ="";
                        if(v!=null && v.getEscribiente()!=null && m.getCi()==v.getEscribiente().getCi()){
                            selected="selected";
                        }
                        out.print("<option " + selected +" value='"+m.getCi()+"'>"+ mp.obtenerNombreCompleto(m) +"</option>");
                    }
                    mp.CerrarConexionManejador();
                    %>
                </select>
            </td>
        </tr>
    </table>
    <p align='right'><input type="submit"  value="Aceptar" /></p>
</form>    

<% 
    }
    else{
         response.sendRedirect("");
    }
%>
<%@ include file="footer.jsp" %>