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
                                
				color:#fff;
				text-decoration:none;
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
                                
			}
			
			.nav2 li ul li ul {
				right:-15%;
				top:0px;
                                
			}
                        .nav2 li ul li ul li{
				width:150px;
			}
			
    </style> 
<ul  class="nav2" style="text-align: center; width: 100%;">
        <li style="width: 25%">
            <a href="s1.jsp"><img src="images/s1.png" width="80%" /></a>
        </li>
        <li style="width: 25%">
             <a href="s4.jsp"><img src="images/s4.png" width="80%" /></a>
        </li>
        <li style="width: 25%">
            <a href="escribiente.jsp"><img src="images/escribiente.png" width="80%" /></a>
        </li>
        <li style="width: 25%">
            <a href="usuarios.jsp"><img src="images/usuarios.png" width="80%" /></a>
        </li>
</ul>

<%@ include file="footer.jsp" %>