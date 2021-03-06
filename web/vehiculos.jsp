<%-- 
    Document   : vehiculos
    Created on : May 16, 2017, 6:57:05 PM
    Author     : Gisel
--%>

<%@page import="Classes.Vehiculo"%>
<%@page import="Manejadores.ManejadorVehiculo"%>
<%@page import="java.util.ArrayList"%>
<%@page import="Manejadores.ManejadorClases"%>
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
        var s="¿Seguro que desea eliminar el vehículo: ";
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
<form method="post" target="_blank" onsubmit="return listar(this)" name="formListar" action='Listar?tipo=vehiculos'>
    
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
            <td style="width: 55%"><h3 style="float: left; font-family: sans-serif">Veh&iacute;culos del sistema:</h3></td>
            <td style="width: 15%"><a href="index2.jsp"><img src="images/atras.png" width="100%"/></a></td>
            <td style="width: 15%"><a href="vehiculo.jsp" title="Agregar Vehículo"><img width="30%" src='images/agregarLista.png' /></a> </td>
            <td style="width: 15%"><input type="image" width="30%" title="Imprimir vehiculos"src="images/imprimir.png" alt="Submit Form" /></td>
        </tr>
    </table>
</form>    
    <table style="width: 100%;" align='center'>
            <%
                ManejadorVehiculo  mc = new ManejadorVehiculo();
                ArrayList<Vehiculo> au = mc.getVehiculos();
                mc.CerrarConexionManejador();
                out.print("<tr style='background-color:#ffcc66'>");
                            out.print("<td style='width: 20%' align='center'><h3 style='margin:2%;'>Matricula ONU</h3></td>");
                            out.print("<td style='width: 20%' align='center'><h3 style='margin:2%;'>Matricula UY</h3></td>");
                            out.print("<td style='width: 10%' align='center'><h3 style='margin:2%;'>Marca</h3></td>");
                            out.print("<td style='width: 10%' align='center'><h3 style='margin:2%;'>Modelo</h3></td>");
                            out.print("<td style='width: 10%' align='center'><h3 style='margin:2%;'>Motor</h3></td>");
                            out.print("<td style='width: 10%' align='center'><h3 style='margin:2%;'>Chasis</h3></td>");
                            out.print("<td style='width: 10%' align='center'></td>");
                            out.print("<td style='width: 10%' align='center'></td>");
                       out.print("</tr>" );
                int i=0;
                String color;
                for (Vehiculo u1: au){
                        if ((i%2)==0){
                            color=" #ccccff";
                        }
                        else{
                            color=" #ffff99";
                        }
                        i++;

                    out.print("<tr style='background-color:"+color+"'>");
                    out.print("<td style='width: 20%' align='center'>"+u1.getMatriculaONU()+"</td>");
                    out.print("<td style='width: 20%' align='center'>"+u1.getMatriculaUY()+"</td>");
                    out.print("<td style='width: 10%' align='center'>"+u1.getMarca()+"</td>");
                    out.print("<td style='width: 10%' align='center'>"+u1.getModelo()+"</td>"); 
                    out.print("<td style='width: 10%' align='center'>"+u1.getNroMotor()+"</td>"); 
                    out.print("<td style='width: 10%' align='center'>"+u1.getNroChasis()+"</td>"); 
                    out.print("<td style='width: 10%' align='center'><a href='vehiculo.jsp?id="+String.valueOf(u1.getMatriculaUY())+"'><img src='images/ver.png' width='25%' /></a></td>");
                    out.print("<td style='width: 10%' align='center'><form method='post' onsubmit=\"return confirmar(this)\" action='Vehiculo?elim="+u1.getMatriculaUY()+"'><input type='image' width='25%' title='Eliminar Vehiculo' src='images/eliminar.png' alt='Submit Form' /> </form></td>");
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