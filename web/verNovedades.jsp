<%-- 
    Document   : verNovedades
    Created on : Apr 28, 2017, 8:11:44 AM
    Author     : Gisel
--%>

<%@ include file="header.jsp" %>
<%
    if(u.isAdmin()){
%>

<form id="novedades" method="post" action="Novedades?ver=1">
    <table style="text-align: left" align="center">
        <tr>
            <td style="width: 30%">
                <img src="images/nove.png" width="90%"/>
            </td>
            <td style="width: 70%">
                <table>
                    <tr>
                        <td>
                            <b>Seleccione la fecha:</b>
                        </td>
                        <td>
                             <input type="date" name="fecha" />
                        </td>
                    </tr>
                     <tr>
                        <td>
                            
                        </td>
                        <td>
                            <input type="submit" value="Ver"/>
                        </td>
                    </tr>
                    
                </table>
            </td>
        </tr>
    </table>
</form>
<%
    }
%>
<%@ include file="footer.jsp" %>
