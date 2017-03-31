<%-- 
    Document   : usuarios
    Created on : Mar 2, 2017, 9:15:15 PM
    Author     : Gisel
--%>

<%@page import="Classes.Tipo"%>
<%@page import="Classes.RecordSancionados"%>
<%@page import="Manejadores.ManejadorCodigos"%>
<%@page import="java.util.Map"%>
<%@page import="java.util.HashMap"%>
<%@page import="Classes.Sancion"%>
<%@page import="java.util.ArrayList"%>
<%@page import="Manejadores.ManejadorPersonal"%>
<!DOCTYPE html>
<script src="js/jquery-1.9.1.min.js"></script>
<script src="js/jquery-ui.js"></script>
<%@ include file="header.jsp" %>
<script>
    function listar(form) {//Funcion creada para no perder la sesion luego del submit
        form.submit();
        return false;
    };
    function confirmar(f,um){
        var s="¿Seguro que desea eliminar la sancion: ";
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
<% 
    if(u.isAdmin() || u.isS1()){
        java.util.Date hoy = new java.util.Date(); 
        String m="";
        if(hoy.getMonth()+1 <= 9 ){
            m= "0"+(hoy.getMonth()+1);
        }
        String d="";
        if(hoy.getDate() <= 9 ){
            d= "0"+(hoy.getDate());
        }
        String fecha= (hoy.getYear()+1900) +"-"+ m +"-"+hoy.getDate();
%>

    <div id='dialog1' style="display:none" title="Imprimir sanciones">
        <form method="post" id="formulario2" target="_blank" onsubmit="return listar(this)" name="formListar" action='Listar?tipo=sanciones'>
            <p>
                <b>Desde:</b>
                <input type="date" name="fechaDesde"/>
            </p>
            <p>
                <b>Hasta:</b>
                <input type="date" name="fechaHasta" <%out.print("value='"+fecha+"'");%> required="required"/>
            </p>
            <p>
                <b>Tipo de Sanci&oacute;n:</b>
                <select name="tipoSancion" form="formulario2">
                    <%
                    ManejadorCodigos mc= new ManejadorCodigos();
                    ArrayList<Tipo> at = mc.getTiposSanciones();
                    out.print("<option selected value='TODOS'>TODOS</option>");
                    for(Tipo t: at ){
                        out.print("<option value='"+t.getId()+"'>"+ t.getDescripcion() +"</option>");
                    }
                    %>
                </select>
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
            <td style="width: 55%"><h3 style="float: left; font-family: sans-serif">Sanciones:</h3></td>
            <td style="width: 15%"><a href="index2.jsp"><img src="images/atras.png" width="100%"/></a></td>
            <td style="width: 15%"><a href="sancion.jsp?" title="Agregar"><img width="30%" src='images/agregarLista.png' /></a> </td>
            <td style="width: 15%"><img src="images/imprimir.png" width="30%" onclick="abrir_dialog(dialog1)" /> </td>
        </tr>
    </table>
    <table style="width: 100%;">
            <%
                ManejadorPersonal mp = new ManejadorPersonal();
                ArrayList<Sancion> a = mp.getSanciones(-1);
                out.print("<tr style='background-color:#ffcc66'>");
                            out.print("<td style='width: 10%' align='center'><h3 style='margin:2%;'>Fecha</h3></td>");
                            out.print("<td style='width: 10%' align='center'><h3 style='margin:2%;'>A</h3></td>");
                            out.print("<td style='width: 10%' align='center'><h3 style='margin:2%;'>Tipo Sancion</h3></td>");
                            out.print("<td style='width: 35%' align='center'><h3 style='margin:2%;'>Parte</h3></td>");
                            out.print("<td style='width: 5%' align='center'><h3 style='margin:2%;'>Dias</h3></td>");
                            out.print("<td style='width: 10%' align='center'><h3 style='margin:2%;'>Orden</h3></td>");
                            out.print("<td style='width: 10%' align='center'></td>");
                            out.print("<td style='width: 10%' align='center'></td>");
                       out.print("</tr>" );
                int i=0;
                String color;
                for (Sancion s: a){
                        if ((i%2)==0){
                            color=" #ccccff";
                        }
                        else{
                            color=" #ffff99";
                        }
                        i++;

                    out.print("<tr style='background-color:"+color+"'>");
                    out.print("<td style='width: 10%' align='center'>"+s.getFecha()+"</td>"); 
                    out.print("<td style='width: 10%' align='center'>"+mp.obtenerNombreCompleto(s.getA())+"</td>");
                    out.print("<td style='width: 10%' align='center'>"+s.getTipo().getDescripcion()+"</td>");
                    out.print("<td style='width: 35%' align='center'>"+s.getParte()+"</td>");
                    out.print("<td style='width: 5%' align='center'>"+s.getDias()+"</td>");
                    out.print("<td style='width: 10%' align='center'>"+mp.obtenerNombreCompleto(s.getOrden())+"</td>");
                    out.print("<td style='width: 10%' align='center'><a href='sancion.jsp?id="+String.valueOf(s.getId())+"' ><img title='Ver' src='images/ver.png' width='25%' /></a></td>");
                    out.print("<td style='width: 10%' align='center'><form method='post' onsubmit=\"return confirmar(this,'')\" action='Sancion?elim="+s.getId()+"'><input type='image' width='25%' title='Eliminar' src='images/eliminar.png' alt='Submit Form' /> </form></td>");
                    out.print("</tr>");
                }
                mp.CerrarConexionManejador();
                mc.CerrarConexionManejador();
            %> 
                
    </table>
<% 
    }
    else{
         response.sendRedirect("");
    }

%>
<%@ include file="footer.jsp" %>