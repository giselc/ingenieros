<%-- 
    Document   : armamento-ISumaria
    Created on : May 25, 2017, 6:54:03 PM
    Author     : Gisel
--%>

<%@page import="java.util.ArrayList"%>
<%@page import="Classes.Personal"%>
<%@page import="Manejadores.ManejadorPersonal"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<%@ include file="header.jsp" %>
<script src="js/jquery-1.9.1.min.js"></script>
<%
    if(u.isAdmin()||u.isEscribiente()){
%>

<script>
    function enviandoSubmit(form){
        //var file = document.getElementById("file").files[0];
        //document.getElementById("nombre").value = file.name;
        document.getElementById("enviando").style.display = "block";
        form.submit();
        //document.getElementById("enviando").style.display = "none";
        return true;

    }
</script>
<p align="left"><a href="armamento.jsp?id=<%= request.getParameter("armamento") %>"><img src="images/atras.png" width="15%"/></a></p> 
<form id="isArmamento" enctype="multipart/form-data" method="post" action="ISArmamento?id=-1&armamento=<%= request.getParameter("armamento") %>" onsubmit=" return enviandoSubmit(this);">
    
    <table style="text-align: left" align="center">
        <tr>
            <td colspan="2">
                <h2 style="padding: 0px">
                    Informaci&oacute;n Sumaria:
                </h2>
            </td>
        </tr>
        <tr>
            <td style="width: 70%">
                <table>
                    <tr>
                        <td>
                            <div id="enviando"  style="position: fixed; top:0; left:0; width:100%; height: 100%;background: url('images/cargando (1).gif') center center no-repeat; background-size: 20%; display: none">
                                </div>
                            <b>Seleccione la fecha:</b>
                        </td>
                        <td>
                            <input type="date" name="fecha" required="required"/>
                        </td>
                    </tr>
                     <tr>
                        <td>
                            <b>Seleccione el archivo a subir:</b>
                        </td>
                        <td>
                            <input type="file" name="informacionSumaria" id="file" required="required"/> 
                        </td>
                    </tr>
                    <tr id="escribiente" >
                            <td><p><b>Oficial de armamento:</b></p> </td>
                            <td>
                                <%
                                    ManejadorPersonal mp= new ManejadorPersonal();
                                    ArrayList<Personal> ap= mp.getListaPersonalBasico();
                                    out.print("<select name=\"idOficialArmamento\" form=\"isArmamento\">");
                                    for(Personal p: ap ){
                                        out.print("<option value='"+p.getCi()+"'>"+ mp.obtenerNombreCompleto(p) +"</option>");
                                    } 
                                    out.print("</select>");
                                    mp.CerrarConexionManejador();
                                %>

                            </td>
                        </tr>
                </table>
            </td>
        </tr>
    </table>
    <input type="submit" value="Subir" />
</form>
<%
    }
%>
<%@ include file="footer.jsp" %>

