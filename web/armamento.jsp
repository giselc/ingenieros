<%-- 
    Document   : armamento
    Created on : May 22, 2017, 7:21:14 PM
    Author     : Gisel
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<%@ include file="header.jsp" %>
<script src="js/jquery-1.9.1.min.js"></script>
<script src="js/jquery-ui.js"></script>
<% 
    if(u.isAdmin()|| u.isS4()){

%>

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
    <div id='dialog2' style="display:none" title="Imprimir Armamento">
       <form method="post" target="_blank" onsubmit="return listar(this)" name="formListar" action='Listar?tipo=Armamento&id=<%=request.getParameter("id")%>'>
           <p>
               <b>Ficha:</b>
               <input type="checkbox" name="ficha" checked="checked"/>
           </p>
           <p>
               <b>Observaciones:</b>
               <input type="checkbox" name="historial" checked="checked"/>
           </p>
           <input type="submit" value="Imprimir"/>
       </form>
    </div>
           <table style="width: 100%">
               <tr>
                   <td>
                       <p align="left"><a href="armamentos.jsp"><img src="images/atras.png" width="20%"/></a></p>
                   </td>
                   <td>
                       <img src="images/imprimir.png" width="20%" onclick="abrir_dialog(dialog2)" />
                   </td>
               </tr>
           </table>
     
    <p id="mensaje" style="color: #990000"><% if(session.getAttribute("mensaje")!=null){out.print("<img src='images/icono-informacion.png' width='3%' /> &nbsp;&nbsp;"+session.getAttribute("mensaje"));}%></p>
    <%
        session.setAttribute("mensaje",null);
    %>
     <ul id="tabs">
        <li><a href="#" title="Ficha">Ficha</a></li>
        <li <% if (request.getParameter("id")==null){ out.print("hidden='hidden'");} %>><a href="#" title="Observaciones">Historial</a></li>
        <li <% if (request.getParameter("id")==null){ out.print("hidden='hidden'");} %>><a href="#" title="IS">Informaci&oacute;n sumaria</a></li>
    </ul>
    <div id="content">
         <div id="Ficha"><%@include file="armamento-ficha.jsp" %></div>
         <div id="Observaciones"><%@include file="armamento-observaciones.jsp" %></div>
         <div id="IS"><%@include file="armamento-ISumarias.jsp" %></div>
    </div>
<% 
    }
    else{
         response.sendRedirect("");
    }
%>
<%@ include file="footer.jsp" %>

