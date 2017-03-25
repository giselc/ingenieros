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
     <p align="left"><a href="s1-personal.jsp"><img src="images/atras.png" width="15%"/></a></p>
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
