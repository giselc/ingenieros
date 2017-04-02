<%-- 
    Document   : personal
    Created on : Mar 19, 2017, 7:45:50 PM
    Author     : Gisel
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<%@ include file="header.jsp" %>
<script src="js/jquery-1.9.1.min.js"></script>
<script src="js/jquery-ui.js"></script>
<% 
    if(u.isAdmin()|| u.isS1()){

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
    <div id='dialog2' style="display:none" title="Imprimir ficha personal">
       <form method="post" target="_blank" onsubmit="return listar(this)" name="formListar" action='Listar?tipo=personal&ci=<%=request.getParameter("id")%>'>
           <p>
               <b>Datos Basicos:</b>
               <input type="checkbox" name="datosBasicos" checked="checked"/>
           </p>
           <p>
               <b>Familiares:</b>
               <input type="checkbox" name="familiares" checked="checked"/>
           </p>
           <p>
               <b>Apoderado:</b>
               <input type="checkbox" name="apoderado" checked="checked"/>
           </p>
           <p>
               <b>Especialidades:</b>
               <input type="checkbox" name="especialidades" checked="checked"/>
           </p>
           <input type="submit" value="Imprimir"/>
       </form>
    </div>
           <table style="width: 100%">
               <tr>
                   <td>
                       <p align="left"><a href="s1-personal.jsp"><img src="images/atras.png" width="20%"/></a></p>
                   </td>
                   <td>
                       <img src="images/imprimir.png" width="20%" onclick="abrir_dialog(dialog2)" />
                   </td>
               </tr>
           </table>
     
     
     <ul id="tabs">
        <li><a href="#" title="Datos-Personales">Datos Personales</a></li>
        <li <% if (request.getParameter("id")==null){ out.print("hidden='hidden'");} %>><a href="#" title="Familiares">Familiares</a></li>
        <li <% if (request.getParameter("id")==null){ out.print("hidden='hidden'");} %>><a href="#" title="Apoderado">Apoderado</a></li>
        <li <% if (request.getParameter("id")==null){ out.print("hidden='hidden'");} %>><a href="#" title="Especialidades">Especialidades</a></li>
        <li <% if (request.getParameter("id")==null){ out.print("hidden='hidden'");} %>><a href="#" title="Documentos">Documentos</a></li>
        <li <% if (request.getParameter("id")==null){ out.print("hidden='hidden'");} %>><a href="#" title="Sanciones">Sanciones</a></li>
    </ul>
    <div id="content">
         <div id="Datos-Personales"><%@include file="datosBasicos.jsp" %></div>
         <div id="Familiares"><%@include file="familiares.jsp" %></div>
         <div id="Documentos"><%@include file="documentos.jsp" %></div>
         <div id="Apoderado"><%@include file="apoderado.jsp" %></div>
         <div id="Especialidades"><%@include file="especialidades.jsp" %></div>
         <div id="Sanciones"><%@include file="sanciones.jsp" %></div>
     </div>
<% 
    }
    else{
         response.sendRedirect("");
    }
%>
<%@ include file="footer.jsp" %>
