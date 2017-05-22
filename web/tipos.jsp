<%-- 
    Document   : familiares
    Created on : Mar 8, 2017, 12:14:48 AM
    Author     : Gisel
--%>

<%@page import="Classes.Tipo"%>
<%@page import="Classes.Especialidad"%>
<%@page import="Classes.Grado"%>
<%@page import="Classes.TipoSancion"%>
<%@page import="Classes.TipoFamiliar"%>
<%@page import="Classes.TipoDocumento"%>
<%@page import="java.util.ArrayList"%>
<%@page import="Manejadores.ManejadorCodigos"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<%@ include file="header.jsp" %>
<% 
    if(u.isAdmin()){
        String codigo = request.getParameter("codigo");
%>

<script>
    function listar(form) {//Funcion creada para no perder la sesion luego del submit
        form.submit();
        return false;
    };
    function confirmar(f,um){
        var s="¿Seguro que desea eliminar: ";
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
<form method="post" target="_blank" onsubmit="return listar(this)" name="formListar" action='Listar?tipo=tipos&codigo=<%= codigo %>'>
    
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
            <td style="width: 55%"><h3 style="float: left; font-family: sans-serif"><%= codigo %> del sistema:</h3></td>
            <td style="width: 15%"><a href="index2.jsp"><img src="images/atras.png" width="100%"/></a></td>
            <td style="width: 15%"><a href="tipo.jsp?codigo=<%= codigo%>" title="Agregar"><img width="30%" src='images/agregarLista.png' /></a> </td>
            <td style="width: 15%"><input type="image" width="30%" title="Imprimir"src="images/imprimir.png" alt="Submit Form" /></td>
        </tr>
    </table>
</form>    
    <table style="width: 60%;" align='center'>
            <%
                ManejadorCodigos  mc = new ManejadorCodigos();
                ArrayList<Tipo> at = null;
                
                if(codigo.equals("Familiares")){
                    at=mc.getTiposFamiliares();
                }
                else{
                    if(codigo.equals("Grados")){
                        at= mc.getGrados();
                    }
                    else{
                        if(codigo.equals("Documentos")){
                            at= mc.getTiposDocumentos();
                        }
                        else{
                            if(codigo.equals("Sanciones")){
                                at= mc.getTiposSanciones();
                            } 
                            else{
                                if(codigo.equals("MantenimientoVehiculo")){
                                    at= mc.getTiposMantenimientoVehiculo();
                                } 
                                else{
                                    if(codigo.equals("ModelosArmamentos")){
                                        at= mc.getModelosArmamento();
                                    } 
                                    else{
                                        if(codigo.equals("DestinosArmamentos")){
                                            at= mc.getDestinos();
                                        } 
                                        else{
                                            at=mc.getEspecialidades();
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
                mc.CerrarConexionManejador();
                out.print("<tr style='background-color:#ffcc66'>");
                            out.print("<td style='width: 60%' align='center'><h3 style='margin:2%;'>Descripcion</h3></td>");
                            out.print("<td style='width: 20%' align='center'></td>");
                            out.print("<td style='width: 20%' align='center'></td>");
                       out.print("</tr>" );
                int i=0;
                String color;
                for (Tipo t: at){
                        if ((i%2)==0){
                            color=" #ccccff";
                        }
                        else{
                            color=" #ffff99";
                        }
                        i++;

                    out.print("<tr style='background-color:"+color+"'>");
                    out.print("<td style='width: 60%' align='center'>"+t.getDescripcion()+"</td>");
                    out.print("<td style='width: 20%' align='center'><a href='tipo.jsp?id="+String.valueOf(t.getId())+"&codigo="+codigo+"'><img src='images/ver.png' width='25%' /></a></td>");
                    out.print("<td style='width: 20%' align='center'><form method='post' onsubmit=\"return confirmar(this,'"+t.getDescripcion()+"')\" action='Tipo?elim="+t.getId()+"&codigo="+codigo+"'><input type='image' width='25%' title='Eliminar' src='images/eliminar.png' alt='Submit Form' /> </form></td>");
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
