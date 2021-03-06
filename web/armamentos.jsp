<%-- 
    Document   : armamentos
    Created on : May 22, 2017, 12:32:31 AM
    Author     : Gisel
--%>

<%@page import="Manejadores.ManejadorPersonal"%>
<%@page import="java.util.ArrayList"%>
<%@page import="Classes.Armamento"%>
<%@page import="Manejadores.ManejadorArmamento"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<%@ include file="header.jsp" %>
<% 
    if(u.isAdmin()||u.isS4()){

%>

<script>
    function listar(form) {//Funcion creada para no perder la sesion luego del submit
        form.submit();
        return false;
    };
    function confirmar(f){
        var s="¿Seguro que desea eliminar el armamento: ";
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
<form method="post" target="_blank" onsubmit="return listar(this)" name="formListar" action='Listar?tipo=armamentos'>
    
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
            <td style="width: 55%"><h3 style="float: left; font-family: sans-serif">Armamentos del sistema:</h3></td>
            <td style="width: 15%"><a href="index2.jsp"><img src="images/atras.png" width="100%"/></a></td>
            <td style="width: 15%"><a href="armamento.jsp" title="Agregar Vehículo"><img width="30%" src='images/agregarLista.png' /></a> </td>
            <td style="width: 15%"><input type="image" width="30%" title="Imprimir vehiculos"src="images/imprimir.png" alt="Submit Form" /></td>
        </tr>
    </table>
</form>    
    <table style="width: 100%;" align='center'>
            <%
                ManejadorArmamento  mc = new ManejadorArmamento();
                ArrayList<Armamento> au = mc.getArmamentos();
                mc.CerrarConexionManejador();
                out.print("<tr style='background-color:#ffcc66'>");
                            out.print("<td style='width: 10%' align='center'><h3 style='margin:2%;'>Numero</h3></td>");
                            out.print("<td style='width: 20%' align='center'><h3 style='margin:2%;'>Modelo</h3></td>");
                            out.print("<td style='width: 10%' align='center'><h3 style='margin:2%;'>Fecha Alta</h3></td>");
                            out.print("<td style='width: 10%' align='center'><h3 style='margin:2%;'>Fecha Baja</h3></td>");
                            out.print("<td style='width: 10%' align='center'><h3 style='margin:2%;'>Destino</h3></td>");
                            out.print("<td style='width: 10%' align='center'><h3 style='margin:2%;'>Calibre</h3></td>");
                            out.print("<td style='width: 10%' align='center'><h3 style='margin:2%;'>Entregado</h3></td>");
                            out.print("<td style='width: 10%' align='center'></td>");
                            out.print("<td style='width: 10%' align='center'></td>");
                       out.print("</tr>" );
                int i=0;
                String color;
                for (Armamento u1: au){
                        if ((i%2)==0){
                            color=" #ccccff";
                        }
                        else{
                            color=" #ffff99";
                        }
                        i++;

                    out.print("<tr style='background-color:"+color+"'>");
                    out.print("<td style='width: 10%' align='center'>"+u1.getNumero()+"</td>");
                    out.print("<td style='width: 20%' align='center'>"+u1.getModelo().getDescripcion()+"</td>");
                    if(u1.getFechaAlta()!=null){
                        out.print("<td style='width: 10%' align='center'>"+u1.getFechaAlta()+"</td>");
                    }
                    else{
                        out.print("<td style='width: 10%' align='center'></td>"); 
                    }
                    if(u1.getFechaBaja()!=null){
                        out.print("<td style='width: 10%' align='center'>"+u1.getFechaBaja()+"</td>"); 
                    }
                    else{
                        out.print("<td style='width: 10%' align='center'></td>"); 
                    }
                    
                    out.print("<td style='width: 10%' align='center'>"+u1.getDestino().getDescripcion()+"</td>"); 
                    out.print("<td style='width: 10%' align='center'>"+u1.getCalibre().getId()+"</td>"); 
                    if(u1.getEntregado()!=null){
                        out.print("<td style='width: 10%' align='center'>"+ManejadorPersonal.obtenerNombreCompleto(u1.getEntregado())+"</td>"); 
                    }
                    else{
                        out.print("<td style='width: 10%' align='center'>NO ENTREGADO</td>"); 
                    }
                    out.print("<td style='width: 10%' align='center'><a href='armamento.jsp?id="+String.valueOf(u1.getNumero())+"'><img src='images/ver.png' width='25%' /></a></td>");
                    out.print("<td style='width: 10%' align='center'><form method='post' onsubmit=\"return confirmar(this)\" action='Armamento?elim="+u1.getNumero()+"'><input type='image' width='25%' title='Eliminar Vehiculo' src='images/eliminar.png' alt='Submit Form' /> </form></td>");
                    out.print("</tr>");
                }
            %> 
                
    </table>
        
<% 
    }
    else{
         response.sendRedirect("");
    }

%>
<%@ include file="footer.jsp" %>