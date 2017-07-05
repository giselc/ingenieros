<%-- 
    Document   : armamento-lote
    Created on : May 30, 2017, 11:17:28 AM
    Author     : Gisel
--%>

<%@page import="Classes.LoteMunicion"%>
<%@page import="java.util.ArrayList"%>
<%@page import="Manejadores.ManejadorArmamento"%>
<%@page import="Classes.Lote"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<%@ include file="header.jsp" %>
<% 
    if(u.isAdmin()|| u.isS4()){

%>

<script>
    function listar(form) {//Funcion creada para no perder la sesion luego del submit
        form.submit();
        return false;
    };
    function confirmar(f){
        var s="¿Seguro que desea eliminar la munición del lote? ";
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
<p align="left"><a href="armamento-lotes.jsp"><img src="images/atras.png" width="15%"/></a></p>
<% 
     if(request.getParameter("id")==null){
        
    
%>
<h1 align="center"><u>Agregar lote</u></h1>
<form method="post" name="formulario" id="formulario" action="ArmamentoLote?id=-1" >
    <table  width='70%' align='center' style="text-align: left">
        <tr>
            <td>
                <p><b>Identificador:</b></p>
            </td>
            <td>
                <input type=text name="idLote" required="required" />
            </td>
        </tr>
        <tr>
            <td>
                <p><b>Fecha de vencimiento:</b></p>
            </td>
            <td>
                <input type="date" name="fecha" required="required"/>
            </td>
        </tr>
    </table>
    <p align='right'><input type="submit"  value="Aceptar" /></p>
</form>    
<%
    }
    else{
        String id = request.getParameter("id");
        ManejadorArmamento mv = new ManejadorArmamento();
        Lote v= mv.getLote(id);
        
%>
<h1 align="center">Lote: <%= v.getId() %>/ Vencimiento: <%= v.getFechaVencimiento() %></h1>
<form method="post" target="_blank" onsubmit="return listar(this)" name="formListar" action='Listar?tipo=lote&id=<%= id %>'>
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
            <td style="width: 55%"><h3 style="float: left; font-family: sans-serif">Municiones:</h3></td>
            <td style="width: 15%"><a href="armamento-LoteMunicion.jsp?lote=<%= request.getParameter("id") %>" title="Agregar"><img width="30%" src='images/agregarLista.png' /></a> </td>
            <td style="width: 15%"><input type="image" width="30%" title="Imprimir"src="images/imprimir.png" alt="Submit Form" /></td>
        </tr>
    </table>
</form>  
    <table style="width: 100%;" align='center'>
            <%
                
                ArrayList<LoteMunicion> ais = new ArrayList();
                ais=mv.getMunicionesLote(id);
                mv.CerrarConexionManejador();
                out.print("<tr style='background-color:#ffcc66'>");
                            out.print("<td style='width: 10%' align='center'><h3 style='margin:2%;'>Munición</h3></td>");
                            out.print("<td style='width: 20%' align='center'><h3 style='margin:2%;'>Cantidad</h3></td>");
                            out.print("<td style='width: 10%' align='center'><h3 style='margin:2%;'>Vivas</h3></td>");
                            out.print("<td style='width: 10%' align='center'><h3 style='margin:2%;'>Vainas</h3></td>");
                            out.print("<td style='width: 10%' align='center'></td>");
                       out.print("</tr>" );
                int i=0;
                String color;
                for (LoteMunicion u1: ais){
                        if ((i%2)==0){
                            color=" #ccccff";
                        }
                        else{
                            color=" #ffff99";
                        }
                        i++;

                    out.print("<tr style='background-color:"+color+"'>");
                    out.print("<td style='width: 10%' align='center'>"+u1.getMunicion().getId()+"</td>");
                    out.print("<td style='width: 20%' align='center'>"+u1.getCantidad()+"</td>");
                    out.print("<td style='width: 10%' align='center'>"+u1.getCantMunicionViva()+"</td>");
                    out.print("<td style='width: 10%' align='center'>"+u1.getVainas()+"</td>");
                    out.print("<td style='width: 10%' align='center'><form method='post' onsubmit=\"return confirmar(this)\" action='ArmamentoLoteMunicion?elim="+u1.getId()+"&lote="+id+"'><input type='image' width='25%' title='Eliminar' src='images/eliminar.png' alt='Submit Form' /> </form></td>");
                    out.print("</tr>");
                }
            %> 
                
    </table>
<% 
    }
}
else{
     response.sendRedirect("");
}

%>
<%@ include file="footer.jsp" %>
