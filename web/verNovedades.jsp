<%-- 
    Document   : verNovedades
    Created on : Apr 28, 2017, 8:11:44 AM
    Author     : Gisel
--%>

<%@ include file="header.jsp" %>

<script src="js/jquery-1.9.1.min.js"></script>
<%
    if(u!=null){
%>
<script>
    function novedades(fecha,admin){
         xmlhttp=new XMLHttpRequest();
         xmlhttp.onreadystatechange = function() {
             if (xmlhttp.readyState==4 && xmlhttp.status==200) {
                 var obj = jQuery.parseJSON( xmlhttp.responseText );
                 var listado = obj.novedad;
                 var datos="";
                 var j = 0;
                 var color;
                 if(listado.length==0){
                     datos+="<tr style='background-color:#ffcc66'><td>No hay archivos cargados</td></tr>"
                 
                 }
                 for (var i=0; i<listado.length;i++) {
                    if(i==0){
                        datos+="<tr style='background-color:#ffcc66'><td>Archivo</td><td></td>";
                        if(admin){
                            datos+="<td></td>";
                        }
                        datos+="</tr>";
                    }
                    if ((j%2)==0){
                           color=" #ccccff";
                       }
                       else{
                           color=" #ffff99";
                       }
                       j++;
                    datos+= "<tr style='background-color:"+color+"'><td>"+listado[i].nombre+"</td>";
                    datos+="<td style='width:10%'><a target='_blank' href='Novedades/"+listado[i].nombre+"' ><img width='100%' src='images/ver_1.png' /> </a></td>";
                    if(admin){     
                        datos+="<td style='width:10%'><a target='_blank' href='Novedades?elim="+listado[i].nombre+"' ><img width='100%' src='images/eliminar.png' /> </a></td>";
                    } 
                    datos+="</tr>";
                }
                 document.getElementById("lista").innerHTML=datos;
             };
         };
         xmlhttp.open("POST","Novedades?ver="+fecha.value);
         xmlhttp.send();
     }
</script>

<form id="novedades" method="post" action="Novedades?ver=1">
    <table style="text-align: left" align="center">
        <tr>
            <td>
            <p id="mensaje" style="color: #990000"><% if(session.getAttribute("mensaje")!=null){out.print("<img src='images/icono-informacion.png' width='10%' /> &nbsp;&nbsp;"+session.getAttribute("mensaje"));}%></p>
            <%
                session.setAttribute("mensaje",null);
            %>
            </td>
        </tr>
        <tr>
            <td colspan="2">
                <h2 style="padding: 0px">
                    Ver Novedades
                </h2>
            </td>
        </tr>
        <tr>
            <td style="width: 30%">
                <img src="images/verNovedades.png" width="90%"/>
            </td>
            <td style="width: 70%">
                <table>
                    <tr>
                        <td>
                            <b>Seleccione la fecha:</b>
                        </td>
                        <td>
                            <input type="date" name="fecha" onchange="novedades(this,<%=u.isAdmin()%>)" value=""/>
                        </td>
                    </tr>
                </table>
            </td>
        </tr>
    </table>
    <table id="lista" style="width: 50%">
        
    </table>
</form>
<%
    }
%>
<%@ include file="footer.jsp" %>
