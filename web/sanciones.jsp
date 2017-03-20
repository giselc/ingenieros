<%-- 
    Document   : sanciones
    Created on : Mar 19, 2017, 8:42:55 PM
    Author     : Gisel
--%>

<%@page import="Classes.Sancion"%>
<%@page import="java.util.ArrayList"%>
<%@page import="Manejadores.ManejadorPersonal"%>
<!DOCTYPE html>
<%
 int ci=Integer.valueOf(request.getParameter("id"));
 ManejadorPersonal mp = new ManejadorPersonal();
%>
<form method="post" target="_blank" onsubmit="return listar(this)" name="formListar" action='Listar?tipo=sanciones&ci=<%=ci%>'>
    
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
            <td style="width: 55%"><h3 style="float: left; font-family: sans-serif">Personal:</h3></td>
            <td style="width: 15%"><a href="sancion.jsp?ci=<%=ci%>" title="Agregar personal"><img width="30%" src='images/agregarLista.png' /></a> </td>
            <td style="width: 15%"><input type="image" width="30%" title="Imprimir personal"src="images/imprimir.png" alt="Submit Form" /></td>
        </tr>
    </table>
</form>    
    <table style="width: 100%;">
            <%
               
                ArrayList<Sancion> a = mp.getSanciones(ci);
                out.print("<tr style='background-color:#ffcc66'>");
                            out.print("<td style='width: 10%' align='center'><h3 style='margin:2%;'>Fecha</h3></td>");
                            out.print("<td style='width: 10%' align='center'><h3 style='margin:2%;'>Tipo Sancion</h3></td>");
                            out.print("<td style='width: 30%' align='center'><h3 style='margin:2%;'>Parte</h3></td>");
                            out.print("<td style='width: 15%' align='center'><h3 style='margin:2%;'>Dias</h3></td>");
                            out.print("<td style='width: 15%' align='center'><h3 style='margin:2%;'>Orden</h3></td>");
                            out.print("<td style='width: 10%' align='center'></td>");
                            out.print("<td style='width: 10%' align='center'></td>");
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
                    out.print("<td style='width: 10%' align='center'>"+s.getTipo().getDescripcion()+"</td>");
                    out.print("<td style='width: 30%' align='center'>"+s.getParte()+"</td>");
                    out.print("<td style='width: 15%' align='center'>"+s.getDias()+"</td>");
                    out.print("<td style='width: 15%' align='center'>"+s.getOrden().getGrado().getDescripcion()+" "+s.getOrden().getNombre()+s.getOrden().getApellido()+"</td>");
                    out.print("<td style='width: 10%' align='center'><a href='sancion.jsp?id="+String.valueOf(s.getId())+"'><img src='images/ver.png' width='25%' /></a></td>");
                    out.print("<td style='width: 10%' align='center'><form method='post' onsubmit=\"return confirmar(this,'')\" action='Sancion?elim="+s.getId()+"'><input type='image' width='25%' title='Eliminar personal' src='images/eliminar.png' alt='Submit Form' /> </form></td>");
                    out.print("</tr>");
                }
            %> 
                
    </table>