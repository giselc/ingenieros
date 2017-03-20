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
<% 
    Personal p=null;    
    if(request.getParameter("id")!=null){
        int ci = Integer.valueOf(request.getParameter("id"));
        ManejadorPersonal mp = new ManejadorPersonal();
        p= mp.getPersonalBasico(ci);
    }
%>
    <script>
         $(document).ready(function() {
                 $("#content div").hide();
                 $("#tabs li:first").attr("id","current");
                 $("#content div:first").fadeIn();
                 $("#loader").fadeOut();
             $('#tabs a').click(function(e) {
                 e.preventDefault();
                 $("#content div").hide();
                 $("#tabs li").attr("id","");
                 $(this).parent().attr("id","current");
                 $('#' + $(this).attr('title')).fadeIn();
             });
         })();
     </script>
     <ul id="tabs">
        <li><a href="#" title="Datos-Personales">Datos Personales</a></li>
        <li <% if (request.getParameter("id")==null){ out.print("hidden='hidden'");} %>><a href="#" title="Sanciones">Sanciones</a></li>
     </ul>
    <div id="content">
         <div id="Datos-Personales"><%@include file="datosBasicos.jsp" %></div>
         <div id="Sanciones"><%@include file="sanciones.jsp" %></div>
     </div>
     <a href="s1-personal.jsp">Atr&aacute;s</a>
<% 
    }
    else{
         response.sendRedirect("");
    }

%>
<%@ include file="footer.jsp" %>
