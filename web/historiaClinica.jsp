<%-- 
    Document   : historiaClinica
    Created on : Apr 4, 2017, 5:38:29 PM
    Author     : Gisel
--%>

<%@page import="java.util.HashMap"%>
<%@page import="Classes.ConsultaMedica"%>
<%@page import="java.util.ArrayList"%>
<%@page import="Manejadores.ManejadorPersonal"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<%
    if(request.getParameter("id")!=null){
        int ci=Integer.valueOf(request.getParameter("id"));
        ManejadorPersonal mp = new ManejadorPersonal();
        
%>

    <div id='dialog3' style="display:none" title="Imprimir sanciones">
        <form method="post" id="formulario2" target="_blank" onsubmit="return listar(this)" name="formListar" action='Listar?tipo=historiaClinica&ci=<%=ci%>'>
            <p>
                <b>Desde:</b>
                <input type="date" name="fechaDesde"/>
            </p>
            <p>
                <b>Hasta:</b>
                <input type="date" name="fechaHasta" <%out.print("value='"+fecha+"'");%> required="required"/>
            </p>
            <input type="submit" value="Imprimir"/>
        </form>
    </div>
    <table style="float: right">
        <tr>
            <td>
            <p id="mensaje" style="color: #990000"><% if(session.getAttribute("mensaje")!=null){out.print("<img src='images/icono-informacion.png' width='3%' /> &nbsp;&nbsp;"+session.getAttribute("mensaje"));}%></p>
            <%
                session.setAttribute("mensaje",null);
            %>
            </td>
        </tr>
        <tr>
            <td style="width: 55%"><h3 style="float: left; font-family: sans-serif">Historia Cl&iacute;nica:</h3></td>
            <td style="width: 15%"><a href="consultaMedica.jsp?ci=<%=ci%>" title="Agregar"><img width="30%" src='images/agregarLista.png' /></a> </td>
            <td style="width: 15%"><img src="images/imprimir.png" width="30%" onclick="abrir_dialog(dialog3)" /> </td>
        </tr>
    </table>
    
            <h2>Consultas activas:</h2>
    <table style="width: 100%;">
            <%
                HashMap<Integer,ArrayList<ConsultaMedica>> hm= mp.getHistoriaClinica(ci);
                ArrayList<ConsultaMedica> a1 = hm.get(1);
                ArrayList<ConsultaMedica> a2 = hm.get(2);
                mp.CerrarConexionManejador();
                        
                out.print("<tr style='background-color:#ffcc66'>");
                            out.print("<td style='width: 10%' align='center'><h3 style='margin:2%;'>Paciente</h3></td>");
                            out.print("<td style='width: 10%' align='center'><h3 style='margin:2%;'>Fecha Inicio</h3></td>");
                            out.print("<td style='width: 10%' align='center'><h3 style='margin:2%;'>Fecha Fin</h3></td>");
                            out.print("<td style='width: 20%' align='center'><h3 style='margin:2%;'>Diagnóstico</h3></td>");
                            out.print("<td style='width: 10%' align='center'><h3 style='margin:2%;'>Médico</h3></td>");
                            out.print("<td style='width: 20%' align='center'><h3 style='margin:2%;'>Tratamiento</h3></td>");
                            out.print("<td style='width: 10%' align='center'></td>");
                            if(u.isAdmin()){
                                out.print("<td style='width: 10%' align='center'></td>");
                            }
                       out.print("</tr>" );
                int i=0;
                String color;
                boolean imprimirSalto=true;
                for (ConsultaMedica c: a1){
                    if ((i%2)==0){
                        color=" #ccccff";
                    }
                    else{
                        color=" #ffff99";
                    }
                    i++;
                    out.print("<tr style='background-color:"+color+"'>");
                    out.print("<td style='width: 10%' align='center'>"+mp.obtenerNombreCompleto(c.getIdPersonal())+"</td>");
                    out.print("<td style='width: 10%' align='center'>"+c.getFechaInicio()+"</td>"); 
                    out.print("<td style='width: 10%' align='center'>"+c.getFechaFin()+"</td>");
                    out.print("<td style='width: 20%' align='center'>"+c.getDiagnostico()+"</td>");
                    out.print("<td style='width: 10%' align='center'>"+mp.obtenerNombreCompleto(c.getIdMedico())+"</td>");
                    out.print("<td style='width: 20%' align='center'>"+c.getTratamiento()+"</td>");
                    out.print("<td style='width: 10%' align='center'><a href='consultaMedica.jsp?id="+String.valueOf(c.getId())+"' ><img title='Ver' src='images/ver.png' width='25%' /></a></td>");
                    if(u.isAdmin()){
                        out.print("<td style='width: 10%' align='center'><form method='post' onsubmit=\"return confirmar(this,'')\" action='ConsultaMedica?elim="+c.getId()+"'><input type='image' width='25%' title='Eliminar' src='images/eliminar.png' alt='Submit Form' /> </form></td>");
                    }
                    out.print("</tr>");
                }
                out.print("</table>");
                out.print("<h2>Historial de Consultas:</h2>");
                out.print("<table style=\"width: 100%;\">");
                out.print("<tr style='background-color:#ffcc66'>");
                    out.print("<td style='width: 10%' align='center'><h3 style='margin:2%;'>Paciente</h3></td>");
                    out.print("<td style='width: 10%' align='center'><h3 style='margin:2%;'>Fecha Inicio</h3></td>");
                    out.print("<td style='width: 10%' align='center'><h3 style='margin:2%;'>Fecha Fin</h3></td>");
                    out.print("<td style='width: 20%' align='center'><h3 style='margin:2%;'>Diagnóstico</h3></td>");
                    out.print("<td style='width: 10%' align='center'><h3 style='margin:2%;'>Médico</h3></td>");
                    out.print("<td style='width: 20%' align='center'><h3 style='margin:2%;'>Tratamiento</h3></td>");
                    out.print("<td style='width: 10%' align='center'></td>");
                    if(u.isAdmin()){
                        out.print("<td style='width: 10%' align='center'></td>");
                    }
               out.print("</tr>" );
               for (ConsultaMedica c: a2){
                    if ((i%2)==0){
                        color=" #ccccff";
                    }
                    else{
                        color=" #ffff99";
                    }
                    i++;
                    out.print("<tr style='background-color:"+color+"'>");
                    out.print("<td style='width: 10%' align='center'>"+mp.obtenerNombreCompleto(c.getIdPersonal())+"</td>");
                    out.print("<td style='width: 10%' align='center'>"+c.getFechaInicio()+"</td>"); 
                    out.print("<td style='width: 10%' align='center'>"+c.getFechaFin()+"</td>");
                    out.print("<td style='width: 20%' align='center'>"+c.getDiagnostico()+"</td>");
                    out.print("<td style='width: 10%' align='center'>"+mp.obtenerNombreCompleto(c.getIdMedico())+"</td>");
                    out.print("<td style='width: 20%' align='center'>"+c.getTratamiento()+"</td>");
                    out.print("<td style='width: 10%' align='center'><a href='consultaMedica.jsp?id="+String.valueOf(c.getId())+"' ><img title='Ver' src='images/ver.png' width='25%' /></a></td>");
                    if(u.isAdmin()){
                        out.print("<td style='width: 10%' align='center'><form method='post' onsubmit=\"return confirmar(this,'')\" action='ConsultaMedica?elim="+c.getId()+"'><input type='image' width='25%' title='Eliminar' src='images/eliminar.png' alt='Submit Form' /> </form></td>");
                    }
                    out.print("</tr>");
                }
                 mp.CerrarConexionManejador();
            %>  
                
    </table>
<%
    }
%>