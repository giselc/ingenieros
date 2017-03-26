<%-- 
    Document   : familiares
    Created on : Mar 24, 2017, 11:32:36 PM
    Author     : Gisel
--%>

<%@page import="java.util.ArrayList"%>
<%@page import="Classes.Familiar"%>
<%@page import="Manejadores.ManejadorPersonal"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<%
    if(request.getParameter("id")!=null){
%>
<script>
    function listar(form) {//Funcion creada para no perder la sesion luego del submit
        form.submit();
        return false;
    };
    function confirmar(f,um){
        var s="¿Seguro que desea eliminar el familiar: ";
        var s1= s.concat(um,"?");
        var r=confirm(s1);
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
            <td style="width: 55%"><h3 style="float: left; font-family: sans-serif">Familiares:</h3></td>
            <td style="width: 15%"><a href="index2.jsp"><img src="images/atras.png" width="100%"/></a></td>
            <td style="width: 15%"><a href="familiar.jsp?ciPersonal=<%=Integer.valueOf(request.getParameter("id"))%>" title="Agregar"><img width="30%" src='images/agregarLista.png' /></a> </td>
        </tr>
    </table>
    <table style="width: 100%;" align='center'>
            <%
                ManejadorPersonal mp = new ManejadorPersonal();
                ArrayList<Familiar> au1 = mp.getFamiliares(Integer.valueOf(request.getParameter("id")));
                mp.CerrarConexionManejador();
                out.print("<tr style='background-color:#ffcc66'>");
                            out.print("<td style='width: 10%' align='center'><h3 style='margin:2%;'>CI</h3></td>");
                            out.print("<td style='width: 20%' align='center'><h3 style='margin:2%;'>Nombre</h3></td>");
                            out.print("<td style='width: 30%' align='center'><h3 style='margin:2%;'>Apellido</h3></td>");
                            out.print("<td style='width: 20%' align='center'><h3 style='margin:2%;'>Vinculo</h3></td>");
                            out.print("<td style='width: 10%' align='center'></td>");
                            out.print("<td style='width: 10%' align='center'></td>");
                       out.print("</tr>" );
                int i=0;
                String color;
                for (Familiar u1: au1){
                        if ((i%2)==0){
                            color=" #ccccff";
                        }
                        else{
                            color=" #ffff99";
                        }
                        i++;

                    out.print("<tr style='background-color:"+color+"'>");
                    out.print("<td style='width: 10%' align='center'>"+u1.getCi()+"</td>");
                    out.print("<td style='width: 20%' align='center'>"+u1.getNombre()+"</td>");
                    out.print("<td style='width: 30%' align='center'>"+u1.getApellido()+"</td>"); 
                    out.print("<td style='width: 20%' align='center'>"+u1.getTipo().getDescripcion()+"</td>"); 
                    out.print("<td style='width: 10%' align='center'><a href='familiar.jsp?ciFamiliar="+String.valueOf(u1.getCi())+"&ciPersonal="+Integer.valueOf(request.getParameter("id"))+"'><img src='images/ver.png' width='25%' /></a></td>");
                    out.print("<td style='width: 10%' align='center'><form method='post' onsubmit=\"return confirmar(this,'"+u1.getNombre()+"')\" action='Familiar?elim="+u1.getCi()+"&ciPersonal="+Integer.valueOf(request.getParameter("id"))+"'><input type='image' width='25%' title='Eliminar' src='images/eliminar.png' alt='Submit Form' /> </form></td>");
                    out.print("</tr>");
                }
            %> 
                
    </table>
   <%
 }
%>     