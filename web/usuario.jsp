<%-- 
    Document   : usuario
    Created on : Mar 3, 2017, 1:31:40 AM
    Author     : Gisel
--%>

<%@page import="Manejadores.ManejadorClases"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<%@ include file="header.jsp" %>
<script>
    function validarContrasena(f){
        if (f.elements["pass"].value != f.elements["pass1"].value){
            document.getElementById("textocontrasena").innerHTML = "No coinciden las contraseñas.";
            return false;
        }
        return validarPermisos(f);
    };
    function validarPermisos(f){
        if (document.getElementById("admin").checked || document.getElementById("s1").checked || document.getElementById("s4").checked || document.getElementById("escribiente").checked){
            return true;
        }
        document.getElementById("textocontrasena").innerHTML = "Debe darle un rol al usuario.";
        return false;
    }
</script>
<% 
    if(u.isAdmin()){

%>
<% 


    Usuario u1= null;
    if(request.getParameter("id")!=null){
        int id = Integer.valueOf(request.getParameter("id"));
        ManejadorClases mc = new ManejadorClases();
        u1= mc.getUsuario(u, id);
        mc.CerrarConexionManejador();
    }
%>
<h1 align="center"><u><% if (u1!=null){out.print("Usuario: "+u1.getUsuario());}else{out.print("Alta de usuario");}%></u></h1>


    <table  width='70%' style="font-size: 130%" >
        <tr>
            <td valign='top' width='40%'>
                <img src="images/usuario.png" />
            </td>
            <td width='10%'>
                
            </td>
            <td width='50%'>
                <form method="post" action="Usuario?id=<%if (u1!=null){out.print(u1.getId());}else{out.print("-1");} %>" <% if (u1==null){out.print("onsubmit='return validarContrasena(this);'");}else{out.print("onsubmit='return validarPermisos(this);'");} %>>
                <table>
                    <tr>
                        <td>
                            <p><b>Usuario:</b></p>
                        </td>
                        <td>
                            <p align="center"><input type="text" value="<% if (u1!=null){out.print(u1.getUsuario());}%>" <% if (u1!=null){out.print("readonly='readonly'");}else{out.print("required='required'");}%> name="usuario" /></p>
                        </td>
                    </tr>
                    <tr>
                        <td>
                            <p><b>Nombre para mostrar:</b></p>
                        </td>
                        <td>
                            <p><input type="text" value="<% if (u1!=null){out.print(u1.getNombreMostrar());}%>" name="nombreMostrar" required="required"/></p>
                        </td>
                    </tr>
                    <tr>
                        <td colspan="2">
                            <table width='100%' border='1'>
                                <tr>
                                    <td colspan="2">
                                        <p><b>ROL:</b></p>
                                    </td>
                                    
                                </tr>
                                <tr>
                                    <td>
                                        <b>Administrador:</b>
                                    </td>
                                    <td>
                                        <input  id="admin" type=checkbox name="admin" <% if(u1!=null && u1.isAdmin()){out.print("checked='checked'");} %>/>
                                    </td>
                                </tr>
                                <tr>
                                    <td>
                                        <b>S1:</b>
                                    </td>
                                    <td>
                                        <input type=checkbox  id="s1" name="s1" <% if(u1!=null && u1.isS1()){out.print("checked='checked'");} %>/>
                                    </td>
                                </tr>
                                <tr>
                                    <td>
                                        <b>S4:</b>
                                    </td>
                                    <td>
                                        <input type=checkbox id="s4" name="s4" <% if(u1!=null && u1.isS4()){out.print("checked='checked'");} %>/>
                                    </td>
                                </tr>
                                <tr>
                                    <td>
                                        <b>Escribiente:</b>
                                    </td>
                                    <td>
                                        <input type=checkbox id="escribiente" name="escribiente" <% if(u1!=null && u1.isEscribiente()){out.print("checked='checked'");} %>/>
                                    </td>
                                </tr>
                            </table>
                        </td>
                    </tr>
                    <tr <% if (u1!=null){out.print("hidden='hidden'");}%>>
                        <td>
                            <p><b>Contrase&ntilde;a:</b></p>
                        </td>
                        <td>
                            <p><input type=password name="pass" pattern=".{6,}" <% if(u1!=null && u1.isS4()){out.print("checked='checked'");} %>/></p>
                        </td>
                    </tr>
                    <tr <% if (u1!=null){out.print("hidden='hidden'");}%>>
                        <td>
                            <p><b>Repita la contrase&ntilde;a:</b></p>
                        </td>
                        <td>
                            <p><input type=password name="pass1" <% if(u1!=null && u1.isS4()){out.print("checked='checked'");} %>/></p>

                        </td>
                    </tr>
                    <tr <% if (u1!=null){out.print("hidden='hidden'");}%>>
                        <td colspan="2">
                            <h3 id="textocontrasena" style="color: red"></h3>
                        </td>
                    </tr>
                </table>
                        <p align='right'><input type="submit"  value="Aceptar" /></p>
                </form>
            </td>
        </tr>
    </table>  
            




<% 
    }
    else{
         response.sendRedirect("");
    }

%>
<%@ include file="footer.jsp" %>