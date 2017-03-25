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
<script>
        function buscarApoderado(inputCi){
        // alert(serialize(f));
        
         xmlhttp=new XMLHttpRequest();
         xmlhttp.onreadystatechange = function() {
             if (xmlhttp.readyState==4 && xmlhttp.status==200) {
                 var obj = jQuery.parseJSON( xmlhttp.responseText );
                 var listado = obj.apoderado;
                 for (var i=0; i<listado.length;i++) {
                     document.getElementById("nombres").value= listado[i].nombre;
                     document.getElementById("apellidos").value= listado[i].apellidos;
                     document.getElementById("domicilio").value= listado[i].domicilio;
                     document.getElementById("celular").value= listado[i].celular;
                     document.getElementById("telefono").value= listado[i].telefono;

                 }
             };
         };
         xmlhttp.open("POST","Apoderado?json="+inputCi.value);
         xmlhttp.send();
     }
     function confirmarDesvinculacion(apoderadoCI, personalCI){
         var r= confirm("¿Está seguro de desvincular el apoderado? Si no hay personal vinculado al mismo sus datos se eliminarán.")
         if(r==true){
             location.href="Apoderado?elim="+apoderadoCI+"&idPersonal="+personalCI;
         }
    }
    </script>
<% 
    Apoderado apoderado=null;    
    if(request.getParameter("id")!=null){
        int ci = Integer.valueOf(request.getParameter("id"));
        ManejadorPersonal mp = new ManejadorPersonal();
        apoderado= mp.getApoderado(ci);
        if(apoderado!=null){
            %>
            <p align="left"><a onclick="confirmarDesvinculacion(<%=apoderado.getCi() %>,<%=ci %>);"><img src="images/desvincular.png" width="5%"/></a></p>
            <%
        }
        mp.CerrarConexionManejador();
    }
%>
<h1 align="center"><u><% if (apoderado!=null){out.print("Apoderado: "+apoderado.getNombre()+" "+apoderado.getApellido());}else{out.print("Apoderado");}%></u></h1>


<form method="post" name="formulario1" id="formulario1" onsubmit="return finalizar(this);" action="Apoderado?id=<%if (apoderado!=null){out.print(apoderado.getCi());}else{out.print("-1");} %>&idPersonal=<%=request.getParameter("id")%>" >
    
    <table>
        <tr>
            <td>
                <p><b>V&iacute;nculo:</b></p>
            </td>
            <td>
                <select name="idVinculo" form="formulario1">
                    <%
                    mc= new ManejadorCodigos();
                    ag = mc.getTiposFamiliares();
                    mc.CerrarConexionManejador();
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
                <p><b>C.I.:</b></p>
            </td>
            <td>
                <input type=number name="ci" onblur="buscarApoderado(this);"<%if( apoderado!=null){ out.print("value='"+Integer.valueOf(apoderado.getCi())+"'"); out.print("readonly=\"readonly\"");} %> required="required" />
            </td>
        </tr>
        <tr>
            <td>
                <p><b>Nombres:</b></p>
            </td>
            <td>
                <p align="center"><input type="text" id="nombres" value="<% if (apoderado!=null){out.print(apoderado.getNombre());}%>" required='required' name="nombres" /></p>
            </td>
        </tr>
        <tr>
            <td>
                <p><b>Apellidos:</b></p>
            </td>
            <td>
                <p align="center"><input type="text" id="apellidos" value="<% if (apoderado!=null){out.print(apoderado.getApellido());}%>" required='required' name="apellidos" /></p>
            </td>
        </tr>
        
        <tr>
            <td>
                <p><b>Domicilio:</b></p>
            </td>
            <td>
                <p align="center"><input type="text" id="domicilio" value="<% if (apoderado!=null){out.print(apoderado.getDomicilio());}%>" required='required' name="domicilio" /></p>
            </td>
        </tr>
        <tr>
            <td>
                <p><b>Tel&eacute;fono:</b></p>
            </td>
            <td>
                <p align="center"><input type="text" id="telefono" value="<% if (apoderado!=null){out.print(apoderado.getTelefono());}%>" required='required' name="telefono" /></p>
            </td>
        </tr>
        <tr>
            <td>
                <p><b>Celular:</b></p>
            </td>
            <td>
                <p align="center"><input type="text" id="celular" value="<% if (apoderado!=null){out.print(apoderado.getCelular());}%>" required='required' name="celular" /></p>
            </td>
        </tr>
    </table>
    <p align='right'><input type="submit"  value="Aceptar" /></p>
</form>       
