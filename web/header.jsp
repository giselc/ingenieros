<%--
    Document   : postulantes
    Created on : Mar 18, 2016, 11:57:12 AM
    Author     : Gisel
--%>

<%@page import="Classes.Usuario"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <link rel="stylesheet" href="css/ingenieros.css" type="text/css"/>
        <link rel="stylesheet" href="css/jquery-ui.css" type="text/css"/>
        <link rel="stylesheet" href="css/tabs.css" type="text/css"/>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <style type="text/css">
                        
			ul, ol {
				list-style:none;
                               
			}
			.nav > li {
				float:left;
			}
			.nav li a {
                                
				background-color:rgba(158,158,158,0.8);
				color:#fff;
				text-decoration:none;
				padding:10px 12px;
				display:block;
			}
			
			.nav li a:hover {
				background-color:#434343;
			}
			
			.nav li ul {
				display:none;
				position:absolute;
                                z-index: 5;
                                margin-left: -40px;
				min-width:140px;
			}
			
			.nav li:hover > ul {
				display:block;
			}
			
			.nav li ul li {
				position:relative;
			}
			
			.nav li ul li ul {
				right:-140px;
				top:0px;
			}
			
		</style>
        <title>Compa&ntilde;&iacute;a de Ingenieros - URUGUAY</title>
    </head>
    <body>
    
        <table style="width: 100%;min-height: 1600px;">
            <tr>
                <td class="fondo">
                    
                    <% 
                    HttpSession sesion= request.getSession();
                    if (sesion.getAttribute("usuario")!=null){
                    %>
                    <table style="width: 100%">
                        <tr>
                            <td style="width: 20%; vertical-align: top" >
                                <p style="color: #ffffff; margin: 0px"><%
                                    Usuario u= (Usuario)sesion.getAttribute("usuario");
                                    out.print("Bienvenido ");out.print(u.getNombreMostrar());
                                    %></p>
                                <form action="Logout" method="POST" style="font-size: 100%">
                                    <ul class="nav" style="padding: 0px;margin: 0px">
                                        <li><a <% if(u.isAdmin()){out.print("href='index2.jsp'");}else{out.print("href=''");} %>><table><tr><td align="center"><img src="images/home.png" width="80%" /></td><td>Inicio</td></tr></table> </a></li>
                                        <li><a > <table><tr><td align="center"><img src="images/menu.png" width="80%" /></td><td>Cuenta</td></tr></table> </a>
                                            <ul>
                                                        <li><a href="changePass.jsp?id=<%= u.getId() %>">Cambiar contrase&ntilde;a </a></li>
                                                        <li><a align="center"><input type="submit" value="SALIR"/></a></li>
                                                </ul>
                                        </li>
                                    </ul>
                                </form>
                            </td>
                            <td style="width: 60%; vertical-align: top">
                                <p align="center" ><img src="images/LOGO.png" title="INICIO" <% //if(u.isAdmin()){out.print("onclick=location.href='/postulantes.jsp'");}else{out.print("onclick=location.href='/listar.jsp'");} %> style="height: 15%; width: 100%"/></p>
                            </td>
                            
                            <td style="width: 20%">
                                
                            </td>
                        </tr>
                    </table>
                    <table style="width: 100%" >
                        <tr>
                            <td style="width: 5%">
                            </td>
                            <td>
                                </td>
                        </tr>
                        <tr>
                            <td style="width: 5%">
                            </td>
                            <td class="conteinermenu" >