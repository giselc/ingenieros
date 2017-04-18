<%-- 
    Document   : pasajeHistorial
    Created on : Apr 7, 2017, 9:40:17 AM
    Author     : Gisel
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<%@ include file="header.jsp" %>
<% 
    if(u.isAdmin()){
%>
<% 
    if(request.getParameter("ci")!=null){
        int ci = Integer.valueOf(request.getParameter("ci"));
    
%>
<h1 align="center"><u>Pasaje de datos al historial:</u></h1>
<h3 align="center"><u>Ingrese el per&iacute;odo de desplegado del personal:</u></h3>
<form method="post" name="formulario" id="formulario" action="Personal?elim=<%=ci%>" >
    <p>
        <input type="text" value="S" name="historial" hidden="hidden" />
    </p>
    <p>
        <b>C.I.:</b>
        <input type="text" value="<%=ci%>" name="ci" readonly="readonly" />
    </p>
    <p>
        <b>Fecha de arribo:</b>
        <input type="date" name="arribo"/>
    </p>
    <p>
        <b>Fecha de regreso:</b>
        <input type="date" name="regreso"/>
    </p>
    <p>
        <b>Observaciones:</b>
    </p>
    <p>
        <textarea rows="4" cols="50" name="observaciones" form="formulario"></textarea>
    </p>
    <p align='right'><input type="submit"  value="Aceptar" /></p>
</form>       

<% 
    }
}
else{
     response.sendRedirect("index2.jsp");
}
    

%>
<%@ include file="footer.jsp" %>