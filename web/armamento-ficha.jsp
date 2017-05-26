<%-- 
    Document   : armamento-ficha
    Created on : May 22, 2017, 7:22:21 PM
    Author     : Gisel
--%>

<%@page import="Classes.Personal"%>
<%@page import="Manejadores.ManejadorPersonal"%>
<%@page import="Classes.Municion"%>
<%@page import="Manejadores.ManejadorCodigos"%>
<%@page import="java.util.ArrayList"%>
<%@page import="Classes.Tipo"%>
<%@page import="Manejadores.ManejadorArmamento"%>
<%@page import="Classes.Armamento"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<% 
    Armamento v=null;    
    if(request.getParameter("id")!=null){
        int id = Integer.valueOf(request.getParameter("id"));
        ManejadorArmamento mv = new ManejadorArmamento();
        v= mv.getArmamento(id);
        mv.CerrarConexionManejador();
    }
%>
<h1 align="center"><u><% if (v!=null){out.print("Armamento:"+ v.getNumero());}else{out.print("Agregar armamento");}%></u></h1>
<form method="post" name="formulario" id="formulario" action="Armamento?id=<%if (v!=null){out.print(v.getNumero());}else{out.print("-1");} %>" >
    <table  width='70%' align='center' style="text-align: left">
        <tr>
            <td>
                <p><b>N&uacute;mero:</b></p>
            </td>
            <td>
                <input type=text name="numero" <%if( v!=null){ out.print("value='"+v.getNumero()+"'"); out.print("readonly=\"readonly\"");} %> required="required" />
            </td>
        </tr>
        <tr>
            <td>
                <p><b>Modelo:</b></p>
            </td>
            <td>
                <select name="modelo" form="formulario">
                    <%
                    ManejadorCodigos mc = new ManejadorCodigos();
                    ArrayList<Tipo> at = mc.getModelosArmamento();

                    for(Tipo t: at ){
                        String selected ="";
                        if(v!=null && t.getId()==v.getModelo().getId()){
                            selected="selected";
                        }
                        out.print("<option " + selected +" value='"+t.getId()+"'>"+ t.getDescripcion() +"</option>");
                    }
                    %>
                </select>
            </td>
        </tr>
        <tr>
            <td>
                <p><b>Fecha Alta:</b></p>
            </td>
            <td>
                <input type="date" <% if (v!=null){out.print("value='"+v.getFechaAlta()+"'");}%> name="fechaAlta" />
            </td>
        </tr>
        <tr>
            <td>
                <p><b>Fecha Baja:</b></p>
            </td>
            <td>
                <input type="date" <% if (v!=null){out.print("value='"+v.getFechaAlta()+"'");}%> name="fechaBaja" />
            </td>
        </tr>
        <tr>
            <td>
                <p><b> Destino:</b></p>
            </td>
            <td>
                <select name="destino" form="formulario">
                    <%
                    at = mc.getDestinos();

                    for(Tipo t: at ){
                        String selected ="";
                        if(v!=null && t.getId()==v.getDestino().getId()){
                            selected="selected";
                        }
                        out.print("<option " + selected +" value='"+t.getId()+"'>"+ t.getDescripcion() +"</option>");
                    }
                    %>
                </select>
            </td>
        </tr>
        <tr>
            <td>
                <p><b> Calibre:</b></p>
            </td>
            <td>
                <select name="municion" form="formulario">
                    <%
                    ArrayList<Municion> am = mc.getMuniciones();

                    for(Municion m: am ){
                        String selected ="";
                        if(v!=null && m.getId()==v.getCalibre().getId()){
                            selected="selected";
                        }
                        out.print("<option " + selected +" value='"+m.getId()+"'>"+ m.getId() +"</option>");
                    }
                    mc.CerrarConexionManejador();
                    %>
                </select>
            </td>
        </tr>
        <tr>
            <td>
                <p><b> Personal entregado:</b></p>
            </td>
            <td>
                <select name="entregado" form="formulario">
                    <%
                    ManejadorPersonal mp= new ManejadorPersonal();
                    ArrayList<Personal> ap = mp.getListaPersonalBasico();
                    String selected = "";
                    if(v==null || v.getEntregado()==null){
                        selected="selected";
                    }
                    out.print("<option " + selected +" value='-1'>NO ENTREGADO</option>");
                    for(Personal m: ap ){
                        selected ="";
                        if(v!=null && v.getEntregado()!=null && m.getCi()==v.getEntregado().getCi()){
                            selected="selected";
                        }
                        out.print("<option " + selected +" value='"+m.getCi()+"'>"+ mp.obtenerNombreCompleto(m) +"</option>");
                    }
                    mp.CerrarConexionManejador();
                    %>
                </select>
            </td>
        </tr>
    </table>
    <p align='right'><input type="submit"  value="Aceptar" /></p>
</form>    
