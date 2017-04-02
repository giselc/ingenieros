<%-- 
    Document   : s1
    Created on : Mar 14, 2017, 8:39:29 PM
    Author     : Gisel
--%>

<%@page import="Classes.Personal"%>
<%@page import="java.util.ArrayList"%>
<%@page import="Manejadores.ManejadorPersonal"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<%@ include file="header.jsp" %>
<% 
    if(u.isAdmin() || u.isS1()){

%>

<script>
    function listar(form) {//Funcion creada para no perder la sesion luego del submit
        if(document.getElementById("nombreCompleto").checked || document.getElementById("ci").checked){
            form.submit();
            return false;
        }
        else{
            alert("Debe seleccionar nombre y/o cédula.")
        }
        return false;
    };
    function confirmar(f,um){
        var s="¿Seguro que desea eliminar: ";
        var s1= s.concat(um,"?");
        var r=confirm(s1);
        if (r==true)
        {
            s="¿Desea pasar los datos al historial?";
            r=confirm(s1);
            var input = document.createElement('input');input.type = 'hidden';input.name = 'historial';
            if (r==true)
            {
                input.value = "S";
            }
            else{
                input.value = "N";
            }
            f.appendChild(input);
            f.submit();
            return true;
        }
        else{
            return false;
        }
    };
</script>
<script>
        function abrir_dialog(dialog) {
          $( dialog ).dialog({
              modal: true
          });
        };
        function cerrar_dialog(dialog) {
          $( dialog ).dialog('close');
        };
         $(document).ready(function() {
                 $("#content div").hide();
                 $("#tabs li:first").attr("id","current");
                 $("#content div:first").fadeIn();
                 $("#loader").fadeOut();
             $('#tabs a').click(function(e) {
                 document.getElementById("mensaje").innerHTML="";
                 e.preventDefault();
                 $("#content div").hide();
                 $("#tabs li").attr("id","");
                 $(this).parent().attr("id","current");
                 $('#' + $(this).attr('title')).fadeIn();
             });
         })();
     </script>
<script src="js/jquery-1.9.1.min.js"></script>
<script src="js/jquery-ui.js"></script>
<div id='dialog2' style="display:none" title="Imprimir personal">
    <form method="post" target="_blank" onsubmit="return listar(this)" name="formListar" action='Listar?tipo=todoElPersonal'>
        <p>
            <b>Nombre Completo:</b>
            <input type="checkbox" id="nombreCompleto" name="nombreCompleto" checked="checked"/>
        </p>
        <p>
            <b>CI</b>
            <input type="checkbox" id="ci" name="ci" checked="checked"/>
            <b>Vto.:</b>
            <input type="checkbox" name="ciVto" checked="checked"/>
        </p>
        <p>
            <b>Fecha de Nacimiento:</b>
            <input type="checkbox" name="fechaNac" checked="checked"/>
        </p>
        <p>
            <b>Pasaporte:</b>
            <input type="checkbox" name="pasaporte" checked="checked"/>
            <b>Vto.:</b>
            <input type="checkbox" name="vtoPas" checked="checked"/>
        </p>
         <p>
            <b>Credencial:</b>
            <input type="checkbox" name="credencial" checked="checked"/>
        </p>
        <p>
            <b>Misiones anteriores:</b>
            <input type="checkbox" name="misiones" checked="checked"/>
        </p>
        <p>
            <b>Licencia de Conducir:</b>
            <input type="checkbox" name="licencia" checked="checked"/>
            <b>Vto.:</b>
            <input type="checkbox" name="vtoLic" checked="checked"/>
        </p>
        <p>
            <b>Carné de Salud:</b>
            <input type="checkbox" name="carneSalud" checked="checked"/>
            <b>Vto.:</b>
            <input type="checkbox" name="vtoCarne" checked="checked"/>
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
            <td style="width: 55%"><h3 style="float: left; font-family: sans-serif">Personal:</h3></td>
            <td style="width: 15%"><a href="index2.jsp"><img src="images/atras.png" width="100%"/></a></td>
            <td style="width: 15%"><a href="personal.jsp" title="Agregar personal"><img width="30%" src='images/agregarLista.png' /></a> </td>
            <td style="width: 15%"><img src="images/imprimir.png" width="30%" onclick="abrir_dialog(dialog2)" /></td>
        </tr>
    </table>
    <table style="width: 100%;">
            <%
                ManejadorPersonal  mc = new ManejadorPersonal();
                ArrayList<Personal> au = mc.getListaPersonalBasico();
                mc.CerrarConexionManejador();
                out.print("<tr style='background-color:#ffcc66'>");
                            out.print("<td style='width: 10%' align='center'><h3 style='margin:2%;'>Grado</h3></td>");
                            out.print("<td style='width: 25%' align='center'><h3 style='margin:2%;'>Nombres</h3></td>");
                            out.print("<td style='width: 35%' align='center'><h3 style='margin:2%;'>Apellidos</h3></td>");
                            out.print("<td style='width: 15%' align='center'><h3 style='margin:2%;'>CI</h3></td>");
                            out.print("<td style='width: 10%' align='center'></td>");
                            out.print("<td style='width: 10%' align='center'></td>");
                       out.print("</tr>" );
                int i=0;
                String color;
                for (Personal u1: au){
                        if ((i%2)==0){
                            color=" #ccccff";
                        }
                        else{
                            color=" #ffff99";
                        }
                        i++;

                    out.print("<tr style='background-color:"+color+"'>");
                    out.print("<td style='width: 10%' align='center'>"+u1.getGrado().getAbreviacion()+"</td>"); 
                    out.print("<td style='width: 25%' align='center'>"+u1.getNombre()+"</td>");
                    out.print("<td style='width: 30%' align='center'>"+u1.getApellido()+"</td>");
                    out.print("<td style='width: 15%' align='center'>"+u1.getCi()+"</td>");
                    out.print("<td style='width: 10%' align='center'><a href='personal.jsp?id="+String.valueOf(u1.getCi())+"'><img src='images/ver.png' width='25%' /></a></td>");
                    out.print("<td style='width: 10%' align='center'><form method='post' onsubmit=\"return confirmar(this,'"+u1.getNombre()+"')\" action='Personal?elim="+u1.getCi()+"'><input type='image' width='25%' title='Eliminar personal' src='images/eliminar.png' alt='Submit Form' /> </form></td>");
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