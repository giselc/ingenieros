<%-- 
    Document   : apoderado
    Created on : Mar 21, 2017, 11:20:41 PM
    Author     : Gisel
--%>

<%@page import="java.util.ArrayList"%>
<%@page import="Classes.Tipo"%>
<%@page import="Manejadores.ManejadorCodigos"%>
<%@page import="Manejadores.ManejadorPersonal"%>
<%@page import="Classes.Apoderado"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<% 
    Apoderado apoderado=null;    
    if(request.getParameter("id")!=null){
        int ci = Integer.valueOf(request.getParameter("id"));
        ManejadorPersonal mp = new ManejadorPersonal();
        apoderado= mp.getApoderado(ci);
        if(apoderado!=null){
            %>
            <p align="left"><a href="Apoderado?elim=<%=apoderado.getCi() %>&idPersonal=<%=ci %>"><img src="images/desvincular.png" width="5%"/></a></p>
            <%
        }
    }
%>
<h1 align="center"><u><% if (apoderado!=null){out.print("Apoderado: "+apoderado.getNombre()+" "+apoderado.getApellido());}else{out.print("Apoderado");}%></u></h1>


<form method="post" name="formulario1" id="formulario1" onsubmit="return finalizar(this);" action="Apoderado?id=<%if (apoderado!=null){out.print(apoderado.getCi());}else{out.print("-1");} %>&idPersonal=<%=request.getParameter("id")%>" >
    
    <table>
        <tr>
            <td>
                <p><b>C.I.:</b></p>
            </td>
            <td>
                <input type=number name="ci" <%if( apoderado!=null){ out.print("value='"+Integer.valueOf(apoderado.getCi())+"'"); out.print("readonly=\"readonly\"");} %> required="required" />
            </td>
        </tr>
        <tr>
            <td>
                <p><b>Nombres:</b></p>
            </td>
            <td>
                <p align="center"><input type="text" value="<% if (apoderado!=null){out.print(apoderado.getNombre());}%>" required='required' name="nombres" /></p>
            </td>
        </tr>
        <tr>
            <td>
                <p><b>Apellidos:</b></p>
            </td>
            <td>
                <p align="center"><input type="text" value="<% if (apoderado!=null){out.print(apoderado.getApellido());}%>" required='required' name="apellidos" /></p>
            </td>
        </tr>
        <tr>
            <td>
                <p><b>V&iacute;nculo:</b></p>
            </td>
            <td>
                <select name="idVinculo" form="formulario1">
                    <%
                    ag = mc.getTiposFamiliares();
                    for(Tipo g: ag ){
                        String s="";
                        if(apoderado!=null && apoderado.getVinculo().getId()==g.getId()){
                            s="selected";
                        }
                        out.print("<option " + s +" value='"+String.valueOf(g.getId()) +"'>"+ g.getDescripcion() +"</option>");
                    }
                    %>
                </select>
            </td>
        </tr>
        <tr>
            <td>
                <p><b>Domicilio:</b></p>
            </td>
            <td>
                <p align="center"><input type="text" value="<% if (apoderado!=null){out.print(apoderado.getDomicilio());}%>" required='required' name="domicilio" /></p>
            </td>
        </tr>
        <tr>
            <td>
                <p><b>Tel&eacute;fono:</b></p>
            </td>
            <td>
                <p align="center"><input type="text" value="<% if (apoderado!=null){out.print(apoderado.getTelefono());}%>" required='required' name="telefono" /></p>
            </td>
        </tr>
        <tr>
            <td>
                <p><b>Celular:</b></p>
            </td>
            <td>
                <p align="center"><input type="text" value="<% if (apoderado!=null){out.print(apoderado.getCelular());}%>" required='required' name="celular" /></p>
            </td>
        </tr>
    </table>
    <p align='right'><input type="submit"  value="Aceptar" /></p>
</form>       
