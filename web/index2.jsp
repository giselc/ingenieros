<%-- 
    Document   : index2
    Created on : Feb 6, 2017, 5:01:25 PM
    Author     : Gisel
--%>

<%@ include file="header.jsp" %>
<style type="text/css">
                        
			.nav2 > li {
				float:left;
			}
			.nav2 li a {
                                
				background-color:rgba(60,60,60,0.6);
				color:#fff;
				text-decoration:none;
				padding:10px 5px;
				display:block;
			}
			
			.nav2 li a:hover{
				background-color:#434343;
			}
			
			.nav2 li ul {
				display:none;
				position:absolute;
                                z-index: 5;
                                margin-left: -40px;
				width:15%;
                                 
			}
			
			.nav2 li:hover > ul {
				display:block;
			}
			
			.nav2 li ul li {
				position:relative;
                                width:250px;
			}
			
			.nav2 li ul li ul {
				right:-15%;
				top:0px;
                                
			}
                        .nav2 li ul li ul li{
				width:150px;
			}
			
    </style> 
    <p id="mensaje" style="color: #990000"><% if(session.getAttribute("mensaje")!=null){out.print("<img src='images/icono-informacion.png' width='3%' /> &nbsp;&nbsp;"+session.getAttribute("mensaje"));}%></p>
            <%
                session.setAttribute("mensaje",null);
            %>
<ul  class="nav2" style="text-align: center; width: 100%;padding-left: 0px;">
    <%
        if(u.isAdmin() || u.isS1()){
    %>
        <li style="width: 25%">
            <a><img src="images/button_s1.png" width="80%" /></a>
            <ul>
                <li>
                    <a href="s1-personal.jsp">Personal</a>
                </li>
                <li>
                    <a href="s1-sanciones.jsp">Sanciones</a>
                </li>
                <li>
                    <a href="s1-historiaClinica.jsp">Consultas M&eacute;dicas</a>
                </li>
            </ul>
        </li>
    <%
        }
        if(u.isAdmin() || u.isS4()){
    %>
        <li style="width: 25%">
            <a href="s4.jsp"><img src="images/button_s4.png" width="80%" /></a>
        </li>
    <%
        }
        if(u.isAdmin()){
    %>
        <li style="width: 25%">
            <a><img src="images/CONFIG.png" width="80%" /></a>
            <ul>
                <li>
                    <a href="usuarios.jsp">Usuarios</a>
                </li>
                <li>
                    <a  href="unidadesMilitares.jsp">Unidades Militares</a>
                </li>
                <li>
                    <a  href="tipos.jsp?codigo=Familiares">Familiares/V&iacute;nculos</a>
                </li>
                <li>
                    <a href="tipos.jsp?codigo=Grados">Grados</a>
                </li>
                <li>
                    <a href="tipos.jsp?codigo=Documentos">Documentos</a>
                </li>
                <li>
                    <a  href="tipos.jsp?codigo=Sanciones">Tipos de Sanciones</a>
                </li>
                <li>
                    <a href="tipos.jsp?codigo=Especialidades">Especialidades</a>
                </li>
                <li>
                    <a href="Backup">Respaldo</a>
                </li>
            </ul>
        </li>
        <%
        }
        %>
        <li style="width: 25%">
            <a><img src="images/button_novedades.png" width="80%" /></a>
            <ul>
                <li>
                    <a href="verNovedades.jsp">Ver Novedades</a>
                </li>
                <%
        
                    if(u.isAdmin()||u.isEscribiente()){
                %>
                <li>
                    <a  href="subirNovedades.jsp">Subir Novedades</a>
                </li>
                <%
        
                    }
                %>
            </ul>
        </li>
        <li style="width: 25%">
            
        </li>
</ul>
<%@ include file="footer.jsp" %>