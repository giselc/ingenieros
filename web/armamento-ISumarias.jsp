<%-- 
    Document   : armamento-IS
    Created on : May 22, 2017, 7:22:47 PM
    Author     : Gisel
--%>

<%@page import="Manejadores.ManejadorArmamento"%>
<%@page import="java.util.ArrayList"%>
<%@page import="Classes.ISArmamento"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<script>
    function confirmar2(f){
        var s="¿Seguro que desea eliminar la información sumaria: ";
        var r=confirm(s);
        if (r==true)
        {
            f.submit();
            return true;
        }
        else{
            return false;
        }
    };
</script>
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
            <td style="width: 55%"><h3 style="float: left; font-family: sans-serif">Informaci&oacute;n sumarias:</h3></td>
            <td style="width: 15%"><a href="armamento-ISumaria.jsp?armamento=<%= request.getParameter("id") %>" title="Agregar"><img width="30%" src='images/agregarLista.png' /></a> </td>
        </tr>
    </table>
    <table style="width: 100%;" align='center'>
            <%
                
                ArrayList<ISArmamento> ais = new ArrayList();
                if(request.getParameter("id")!=null){
                    ManejadorArmamento ma = new ManejadorArmamento();
                    ais=ma.getInformacionSumariasArmamento(Integer.valueOf(request.getParameter("id")));
                    ma.CerrarConexionManejador();
                }
                out.print("<tr style='background-color:#ffcc66'>");
                            out.print("<td style='width: 10%' align='center'><h3 style='margin:2%;'>Fecha</h3></td>");
                            out.print("<td style='width: 20%' align='center'><h3 style='margin:2%;'>Archivo</h3></td>");
                            out.print("<td style='width: 10%' align='center'><h3 style='margin:2%;'>Oficial Armamento</h3></td>");
                            out.print("<td style='width: 10%' align='center'></td>");
                       out.print("</tr>" );
                i=0;
                for (ISArmamento u1: ais){
                        if ((i%2)==0){
                            color=" #ccccff";
                        }
                        else{
                            color=" #ffff99";
                        }
                        i++;

                    out.print("<tr style='background-color:"+color+"'>");
                    out.print("<td style='width: 10%' align='center'>"+u1.getFecha()+"</td>");
                    out.print("<td style='width: 20%' align='center'><a href='ISArmamento/"+request.getParameter("id")+"-"+u1.getId()+u1.getInformacionSumaria().substring(u1.getInformacionSumaria().indexOf("."))+"'>"+u1.getInformacionSumaria()+"</a></td>");
                    out.print("<td style='width: 10%' align='center'>"+u1.getOficialArmamento().getGrado().getAbreviacion()+" "+u1.getOficialArmamento().getNombre()+" "+u1.getOficialArmamento().getApellido()+"</td>");
                    out.print("<td style='width: 10%' align='center'><form method='post' onsubmit=\"return confirmar2(this)\" action='ISArmamento?elim="+u1.getId()+"&armamento="+request.getParameter("id")+"'><input type='image' width='25%' title='Eliminar' src='images/eliminar.png' alt='Submit Form' /> </form></td>");
                    out.print("</tr>");
                }
            %> 
                
    </table>
