<%-- 
    Document   : familiares
    Created on : Mar 24, 2017, 11:32:36 PM
    Author     : Gisel
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<%@ include file="header.jsp" %>
<% 
    if(u.isAdmin()){

%>

<script>
    function listar(form) {//Funcion creada para no perder la sesion luego del submit
        form.submit();
        return false;
    };
    function confirmar(f,um){
        var s="¿Seguro que desea eliminar la Unidad Militar: ";
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
<form method="post" target="_blank" onsubmit="return listar(this)" name="formListar" action='Listar?tipo=unidadesMilitares'>
    
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
            <td style="width: 55%"><h3 style="float: left; font-family: sans-serif">Unidades militares del sistema:</h3></td>
            <td style="width: 15%"><a href="index2.jsp"><img src="images/atras.png" width="100%"/></a></td>
            <td style="width: 15%"><a href="unidadMilitar.jsp" title="Agregar Unidad Militar"><img width="30%" src='images/agregarLista.png' /></a> </td>
            <td style="width: 15%"><input type="image" width="30%" title="Imprimir unidades militares"src="images/imprimir.png" alt="Submit Form" /></td>
        </tr>
    </table>
</form>    
    <table style="width: 100%;" align='center'>
            <%
                ManejadorCodigos  mc = new ManejadorCodigos();
                ArrayList<UnidadMilitar> au = mc.getUnidadesMilitares();
                mc.CerrarConexionManejador();
                out.print("<tr style='background-color:#ffcc66'>");
                            out.print("<td style='width: 30%' align='center'><h3 style='margin:2%;'>Nombre</h3></td>");
                            out.print("<td style='width: 20%' align='center'><h3 style='margin:2%;'>Telefono</h3></td>");
                            out.print("<td style='width: 30%' align='center'><h3 style='margin:2%;'>Correo</h3></td>");
                            out.print("<td style='width: 10%' align='center'></td>");
                            out.print("<td style='width: 10%' align='center'></td>");
                       out.print("</tr>" );
                int i=0;
                String color;
                for (UnidadMilitar u1: au){
                        if ((i%2)==0){
                            color=" #ccccff";
                        }
                        else{
                            color=" #ffff99";
                        }
                        i++;

                    out.print("<tr style='background-color:"+color+"'>");
                    out.print("<td style='width: 30%' align='center'>"+u1.getNombre()+"</td>");
                    out.print("<td style='width: 20%' align='center'>"+u1.getTelefono()+"</td>");
                    out.print("<td style='width: 30%' align='center'>"+u1.getCorreo()+"</td>"); 
                    out.print("<td style='width: 10%' align='center'><a href='unidadMilitar.jsp?id="+String.valueOf(u1.getId())+"'><img src='images/ver.png' width='25%' /></a></td>");
                    out.print("<td style='width: 10%' align='center'><form method='post' onsubmit=\"return confirmar(this,'"+u1.getNombre()+"')\" action='UnidadMilitar?elim="+u1.getId()+"'><input type='image' width='25%' title='Eliminar Unidad Militar' src='images/eliminar.png' alt='Submit Form' /> </form></td>");
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