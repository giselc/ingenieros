<%-- 
    Document   : index2
    Created on : Feb 6, 2017, 5:01:25 PM
    Author     : Gisel
--%>

<%@ include file="header.jsp" %>
<% 
    if(u.isAdmin()){

%>
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
<ul  class="nav2" style="text-align: center; width: 100%; padding: 0px">
        <li style="width: 25%">
            <a href="s1.jsp"><img src="images/S1.png" width="80%" /></a>
        </li>
        <li style="width: 25%">
            <a href="s4.jsp"><img src="images/S4.png" width="80%" /></a>
        </li>
        <li style="width: 25%">
            <a href="escribiente.jsp"><img src="images/ESCRIBIENTE.png" width="80%" /></a>
        </li>
        <li style="width: 25%">
            <a href="usuarios.jsp"><img src="images/CONFIG.png" width="80%" /></a>
            <ul>
                <li>
                    <a href="usuarios.jsp">Usuarios</a>
                </li>
                <li>
                    <a  href="unidadesMilitares.jsp">Unidades Militares</a>
                </li>
                <li>
                    <a href="grados.jsp">Grados</a>
                </li>
                <li>
                    <a href="tipoDocumento.jsp">Documentos</a>
                </li>
                <li>
                    <a  href="tipoFamiliar.jsp">Familiares/V&iacute;nculos</a>
                </li>
                <li>
                    <a  href="tipoSancion.jsp">Sanci&oacute;n</a>
                </li>
                <li>
                    <a href="especialidades.jsp">Especialidades</a>
                </li>
            </ul>
        </li>
</ul>
<% 
    }
    else{
         response.sendRedirect("");
    }

%>
<%@ include file="footer.jsp" %>