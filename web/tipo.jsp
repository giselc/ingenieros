<%-- 
    Document   : familiar
    Created on : Mar 8, 2017, 12:14:59 AM
    Author     : Gisel
--%>

<%@page import="Classes.Grado"%>
<%@page import="Manejadores.ManejadorCodigos"%>
<%@page import="Classes.Tipo"%>
<%@page import="Manejadores.ManejadorClases"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<%@ include file="header.jsp" %>

<% 
    if(u.isAdmin()){

%>
<% 


    Tipo t = null;
    String mostrar="";
    String codigo = request.getParameter("codigo");
        if(request.getParameter("id")!=null){ 
        int id = Integer.valueOf(request.getParameter("id"));
        ManejadorCodigos mc = new ManejadorCodigos();
        if(codigo.equals("Familiares")){
            t=mc.getTipoFamiliar(id);
            mostrar="Familiar";
        }
        else{
            if(codigo.equals("Grados")){
                t= mc.getGrado(id);
                mostrar="Grado";
            }
            else{
                if(codigo.equals("Documentos")){
                    t= mc.getTipoDocumento(id);
                    mostrar="Documento";
                }
                else{
                    if(codigo.equals("Sanciones")){
                        t= mc.getTipoSancion(id);
                        mostrar="Sanción";
                    } 
                    else{
                        if(codigo.equals("MantenimientoVehiculo")){
                            t= mc.getTipoMantenimientoVehiculo(id);
                            mostrar="Mantenimiento Vehículo";
                        } 
                        else{
                            t=mc.getEspecialidad(id);
                            mostrar="Especialidad";
                        }
                    }
                }
            }
        }
        mc.CerrarConexionManejador();
    }
%>
<h1 align="center"><u><% if (t!=null){out.print(mostrar+" "+ t.getDescripcion());}else{out.print("Alta "+mostrar);}%></u></h1>


    <table  width='70%' style="font-size: 130%" >
        <tr>
            <td valign='top' width='40%'>
                <img width="100%" src="images/configLogo.png" />
            </td>
            <td width='10%'>
                
            </td>
            <td width='50%'>
                <form method="post" action="Tipo?id=<%if (t!=null){out.print(t.getId());}else{out.print("-1");} %>&codigo=<%= codigo %>">
                <table>
                    <%
                    if(codigo.equals("Grados")){
                        Grado g= (Grado)t;
                    
                    %>
                    <tr>
                        <td>
                            <p><b>Abreviaci&oacute;n:</b></p>
                        </td>
                        <td>
                            <p align="center"><input type="text" value="<% if (t!=null){out.print(g.getAbreviacion());}%>"  required='required' name="abreviacion" /></p>
                        </td>
                    </tr>
                    <%
                    }
                    %>
                    <tr>
                        <td>
                            <p><b>Descripci&oacute;n:</b></p>
                        </td>
                        <td>
                            <p align="center"><input type="text" value="<% if (t!=null){out.print(t.getDescripcion());}%>"  required='required' name="descripcion" /></p>
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
