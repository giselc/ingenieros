<%-- 
    Document   : vehiculo-ficha
    Created on : May 16, 2017, 8:59:27 PM
    Author     : Gisel
--%>

<%@page import="Manejadores.ManejadorVehiculo"%>
<%@page import="Classes.Vehiculo"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<% 
    Vehiculo v=null;    
    if(request.getParameter("id")!=null){
        String id = request.getParameter("id");
        ManejadorVehiculo mv = new ManejadorVehiculo();
        v= mv.getVehiculo(id);
        mv.CerrarConexionManejador();
    }
%>
<h1 align="center"><u><% if (v!=null){out.print("Vehículo: ONU:"+v.getMatriculaONU()+"/UY:"+v.getMatriculaUY());}else{out.print("Agregar vehículo");}%></u></h1>
<form method="post" name="formulario" id="formulario" action="Vehiculo?id=<%if (v!=null){out.print(v.getMatriculaUY());}else{out.print("-1");} %>" >
    <table  width='70%' align='center' style="text-align: left">
        <tr>
            <td>
                <p><b>Matr&iacute;cula Uruguay:</b></p>
            </td>
            <td>
                <input type=text name="matriculaUY" <%if( v!=null){ out.print("value='"+v.getMatriculaUY()+"'"); out.print("readonly=\"readonly\"");} %> required="required" />
            </td>
        </tr>
        <tr>
            <td>
                <p><b>Matr&iacute;cula ONU:</b></p>
            </td>
            <td>
                <input type=text name="matriculaONU" <%if( v!=null){ out.print("value='"+v.getMatriculaONU()+"'"); out.print("readonly=\"readonly\"");} %> required="required" />
            </td>
        </tr>
        <tr>
            <td>
                <p><b>Marca:</b></p>
            </td>
            <td>
                <input type="text" value="<% if (v!=null){out.print(v.getMarca());}%>" name="marca" />
            </td>
        </tr>
        <tr>
            <td>
                <p><b>Modelo:</b></p>
            </td>
            <td>
                <input type="text" value="<% if (v!=null){out.print(v.getModelo());}%>" name="modelo" />
            </td>
        </tr>
        <tr>
            <td>
                <p><b> N&uacute;mero de chasis:</b></p>
            </td>
            <td>
                <input type="number" <% if (v!=null){out.print("value='"+v.getNroChasis()+"'");}%> name="nroChasis" />
            </td>
        </tr>
        <tr>
            <td>
                <p><b> N&uacute;mero de motor:</b></p>
            </td>
            <td>
                <input type="number" <% if (v!=null){out.print("value='"+v.getNroMotor()+"'");}%> name="nroMotor" />
            </td>
        </tr>
        <tr>
            <td>
                <p><b> Consumo:</b></p>
            </td>
            <td>
                <input type="number" <% if (v!=null){out.print("value='"+v.getConsumo()+"'");}%> name="consumo" />
            </td>
        </tr>
        <tr>
            <td>
                <p><b>Tipo de veh&iacute;culo:</b></p>
            </td>
            <td>
                <select name="tipo" form="formulario">
                    <option <% if ((v!=null && v.getTipoVehiculo()==0)||(v==null)){out.print("selected='selected'");} %> value="0">T&aacute;ctico-Administrativo</option>
                    <option <% if (v!=null && v.getTipoVehiculo()==1){out.print("selected='selected'");} %> value="1">Maquinaria</option>
                    <option <% if (v!=null && v.getTipoVehiculo()==2){out.print("selected='selected'");} %> value="2">Remolque</option>
                    <option <% if (v!=null && v.getTipoVehiculo()==3){out.print("selected='selected'");} %> value="3">Grupo electr&oacute;geno</option>
                </select>
            </td>
        </tr>
        <tr>
            <td>
                <p><b> Horas o kil&oacute;metros Inicial:</b></p>
            </td>
            <td>
                <input type="number" <% if (v!=null){out.print("value='"+v.getHoraskilometrosInicial()+"'");}%> name="horasKilometrosInicial" />
            </td>
        </tr>
        <tr <%if(v==null){out.print("style=\"display: none\"");}%>>
            <td>
                <p><b> Horas o kil&oacute;metros alcanzados:</b></p>
            </td>
            <td>
                <input type="number" <% if (v!=null){out.print("value='"+v.getHoraskilometrosTotal()+"'");}%> name="horasKilometrosTotal" readonly="readonly" />
            </td>
        </tr>
    </table>
    <p align='right'><input type="submit"  value="Aceptar" /></p>
</form>    