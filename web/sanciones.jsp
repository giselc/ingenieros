<%-- 
    Document   : sanciones
    Created on : Mar 19, 2017, 8:42:55 PM
    Author     : Gisel
--%>

<%@page import="Classes.Tipo"%>
<%@page import="Classes.RecordSancionados"%>
<%@page import="Manejadores.ManejadorCodigos"%>
<%@page import="java.util.Map"%>
<%@page import="java.util.HashMap"%>
<%@page import="Classes.Sancion"%>
<%@page import="java.util.ArrayList"%>
<%@page import="Manejadores.ManejadorPersonal"%>
<!DOCTYPE html>

<%
    java.util.Date hoy = new java.util.Date(); 
    String m=""+hoy.getMonth()+1;
    if(hoy.getMonth()+1 <= 9 ){
        m= "0"+(hoy.getMonth()+1);
    }
    String d=""+hoy.getDate();
    if(hoy.getDate() <= 9 ){
        d= "0"+(hoy.getDate());
    }
    String fecha= (hoy.getYear()+1900) +"-"+ m +"-"+d;
    if(request.getParameter("id")!=null){
        int ci=Integer.valueOf(request.getParameter("id"));
        ManejadorPersonal mp = new ManejadorPersonal();
        
%>

    <div id='dialog1' style="display:none" title="Imprimir sanciones">
        <form method="post" id="formListar" target="_blank" onsubmit="return listar(this)" name="formListar" action='Listar?tipo=sanciones&ci=<%=ci%>'>
            <p>
                <b>Desde:</b>
                <input type="date" name="fechaDesde"/>
            </p>
            <p>
                <b>Hasta:</b>
                <input type="date" name="fechaHasta" <%out.print("value='"+fecha+"'");%> required="required"/>
            </p>
            <p>
                <b>Tipo de Sanci&oacute;n:</b>
                <select name="tipoSancion" form="formListar">
                    <%
                    mc= new ManejadorCodigos();
                    ArrayList<Tipo> at = mc.getTiposSanciones();
                    out.print("<option selected value='TODOS'>TODOS</option>");
                    for(Tipo t: at ){
                        out.print("<option value='"+t.getId()+"'>"+ t.getDescripcion() +"</option>");
                    }
                    %>
                </select>
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
            <td style="width: 55%"><h3 style="float: left; font-family: sans-serif">Sanciones:</h3></td>
            <td style="width: 15%"><a href="sancion.jsp?ci=<%=ci%>" title="Agregar"><img width="30%" src='images/agregarLista.png' /></a> </td>
            <td style="width: 15%"><img src="images/imprimir.png" width="30%" onclick="abrir_dialog(dialog1)" /> </td>
        </tr>
    </table>
    
            <table  align='center'>
                <tr>
                    <td colspan="3" style="border: #000000 1px solid">
                        Resumen de d&iacute;as por cumplir por tipo de sanci&oacute;n
                    </td>
                </tr>
                <%
                    ArrayList<RecordSancionados> listaSanciones = mp.getListaDiasPortipoSancion(ci);
                    out.print("<tr>");
                            out.print("<td style=\"border: #000000 1px solid\" width='30%'>");
                                out.print("Tipo");
                            out.print("</td>");
                            out.print("<td style=\"border: #000000 1px solid\" width='30%'>");
                                 out.print("Días");
                            out.print("</td>");
                            out.print("<td style=\"border: #000000 1px solid\" width='30%'>");
                                 out.print("Fecha inicial");
                            out.print("</td>");
                        out.print("</tr>");
                    for (RecordSancionados entry : listaSanciones) {
                        out.print("<tr>");
                            out.print("<td style=\"border: #000000 1px solid\" width='30%'>");
                                out.print(entry.tipo.getDescripcion());
                            out.print("</td>");
                            out.print("<td style=\"border: #000000 1px solid\" width='30%'>");
                                 out.print(entry.dias);
                            out.print("</td>");
                            out.print("<td style=\"border: #000000 1px solid\" width='30%'>");
                                 out.print(entry.fecha);
                            out.print("</td>");
                        out.print("</tr>");
                    }
                %>
            </table>
    <table style="width: 100%;">
            <%
               
                ArrayList<Sancion> a = mp.getSanciones(ci);
                mp.CerrarConexionManejador();
                mc.CerrarConexionManejador();
                        
                out.print("<tr style='background-color:#ffcc66'>");
                            out.print("<td style='width: 10%' align='center'><h3 style='margin:2%;'>Fecha</h3></td>");
                            out.print("<td style='width: 15%' align='center'><h3 style='margin:2%;'>Tipo Sancion</h3></td>");
                            out.print("<td style='width: 35%' align='center'><h3 style='margin:2%;'>Parte</h3></td>");
                            out.print("<td style='width: 5%' align='center'><h3 style='margin:2%;'>Dias</h3></td>");
                            out.print("<td style='width: 15%' align='center'><h3 style='margin:2%;'>Orden</h3></td>");
                            out.print("<td style='width: 10%' align='center'></td>");
                            if(u.isAdmin()){
                                out.print("<td style='width: 10%' align='center'></td>");
                            }
                       out.print("</tr>" );
                int i=0;
                String color;
                for (Sancion s: a){
                        if ((i%2)==0){
                            color=" #ccccff";
                        }
                        else{
                            color=" #ffff99";
                        }
                        i++;

                    out.print("<tr style='background-color:"+color+"'>");
                    out.print("<td style='width: 10%' align='center'>"+s.getFecha()+"</td>"); 
                    out.print("<td style='width: 15%' align='center'>"+s.getTipo().getDescripcion()+"</td>");
                    out.print("<td style='width: 35%' align='center'>"+s.getParte()+"</td>");
                    out.print("<td style='width: 5%' align='center'>"+s.getDias()+"</td>");
                    out.print("<td style='width: 15%' align='center'>"+ManejadorPersonal.obtenerNombreCompleto(s.getOrden())+"</td>");
                    out.print("<td style='width: 10%' align='center'><a href='sancion.jsp?id="+String.valueOf(s.getId())+"' ><img title='Ver' src='images/ver.png' width='25%' /></a></td>");
                   if(u.isAdmin()){
                        out.print("<td style='width: 10%' align='center'><form method='post' onsubmit=\"return confirmar(this,'')\" action='Sancion?elim="+s.getId()+"&ci="+ci+"'><input type='image' width='25%' title='Eliminar' src='images/eliminar.png' alt='Submit Form' /> </form></td>");
                    }
                    out.print("</tr>");
                }
            %> 
                
    </table>
<%
    }
%>