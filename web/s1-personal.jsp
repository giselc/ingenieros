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
<script src="js/jquery-1.9.1.min.js"></script>
<script src="js/jquery-ui.js"></script>
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
    function confirmar(f,um,ci){
        var s1="¿Desea pasar los datos al historial?";
        var r=confirm(s1);
        if (r==true)
        {
            f.action = "pasajeHistorial.jsp?ci="+ci
            return true;
        }
        else{
            var s2= "¿Desea eliminar los datos del sistema?";
            var r1=confirm(s2);
            if (r1==true){
                var input = document.createElement('input');input.type = 'hidden';input.name = 'historial';
                input.value = "N";
                f.appendChild(input);
                f.submit();
                return true;
            }
            else{
                return false;
            }
        }
    };
</script>


<div id='dialog2' style="display:none" title="Imprimir personal">
    <form method="post" target="_blank" onsubmit="return listar(this)" name="formListar" action='Listar?tipo=todoElPersonal'>
        <p>
            <b>ID:</b>
            <input type="checkbox" id="idONU" name="idONU" checked="checked"/>
        </p>
        <p>
            <b>Nombre Completo:</b>
            <input type="checkbox" id="nombreCompleto" name="nombreCompleto" checked="checked"/>
        </p>
        <p>
            <b>CI</b>
            <input type="checkbox" id="ci" name="ci" checked="checked"/>
            <b>Vto.:</b>
            <input type="checkbox" name="ciVto"/>
        </p>
        <p>
            <b>Fecha de Nacimiento:</b>
            <input type="checkbox" name="fechaNac"/>
        </p>
        <p>
            <b>Pasaporte:</b>
            <input type="checkbox" name="pasaporte"/>
            <b>Vto.:</b>
            <input type="checkbox" name="vtoPas"/>
        </p>
         <p>
            <b>Credencial:</b>
            <input type="checkbox" name="credencial" />
        </p>
        <p>
            <b>Misiones anteriores:</b>
            <input type="checkbox" name="misiones"/>
        </p>
        <p>
            <b>Licencia de Conducir:</b>
            <input type="checkbox" name="licencia" />
            <b>Vto.:</b>
            <input type="checkbox" name="vtoLic"/>
        </p>
        <p>
            <b>Carné de Salud:</b>
            <input type="checkbox" name="carneSalud" />
            <b>Vto.:</b>
            <input type="checkbox" name="vtoCarne" />
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
                            out.print("<td style='width: 10%' align='center'><h3 style='margin:2%;'>ID</h3></td>");
                            out.print("<td style='width: 10%' align='center'><h3 style='margin:2%;'>Grado</h3></td>");
                            out.print("<td style='width: 20%' align='center'><h3 style='margin:2%;'>Nombres</h3></td>");
                            out.print("<td style='width: 30%' align='center'><h3 style='margin:2%;'>Apellidos</h3></td>");
                            out.print("<td style='width: 15%' align='center'><h3 style='margin:2%;'>CI</h3></td>");
                            out.print("<td style='width: 10%' align='center'></td>");
                            if(u.isAdmin()){
                                out.print("<td style='width: 10%' align='center'></td>");
                            }
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
                    out.print("<td style='width: 10%' align='center'>"+u1.getIdONU()+"</td>");
                    out.print("<td style='width: 10%' align='center'>"+u1.getGrado().getAbreviacion()+"</td>"); 
                    out.print("<td style='width: 20%' align='center'>"+u1.getNombre()+"</td>");
                    out.print("<td style='width: 30%' align='center'>"+u1.getApellido()+"</td>");
                    out.print("<td style='width: 15%' align='center'>"+u1.getCi()+"</td>");
                    out.print("<td style='width: 10%' align='center'><a href='personal.jsp?id="+String.valueOf(u1.getCi())+"'><img src='images/ver.png' width='25%' /></a></td>");
                    if(u.isAdmin()){
                        out.print("<td style='width: 10%' align='center'><form method='post' onsubmit=\"return confirmar(this,'"+u1.getNombre()+"','"+u1.getCi()+"')\" action='Personal?elim="+u1.getCi()+"'><input type='image' width='25%' title='Eliminar personal' src='images/eliminar.png' alt='Submit Form' /> </form></td>");
                    }
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