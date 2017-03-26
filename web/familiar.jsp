<%-- 
    Document   : familiar
    Created on : Mar 26, 2017, 1:03:42 PM
    Author     : Gisel
--%>

<%@page import="Classes.Tipo"%>
<%@page import="java.util.ArrayList"%>
<%@page import="Classes.TipoFamiliar"%>
<%@page import="Manejadores.ManejadorCodigos"%>
<%@page import="Manejadores.ManejadorPersonal"%>
<%@page import="Classes.Familiar"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<%@ include file="header.jsp" %>
<script src="js/jquery-1.9.1.min.js"></script>
<script src="js/jquery-ui.js"></script>
<script>
        function showDescDiscapacidad(b){
            if(b.checked){
                document.getElementById('descDiscapacidad').style.display = '';
            }
            else{
                document.getElementById('descDiscapacidad').style.display = 'none';
            }
        }
</script>
<script>
    function buscarFamiliar(inputCi){
        // alert(serialize(f));
        
         xmlhttp=new XMLHttpRequest();
         xmlhttp.onreadystatechange = function() {
             if (xmlhttp.readyState==4 && xmlhttp.status==200) {
                 var obj = jQuery.parseJSON( xmlhttp.responseText );
                 var listado = obj.familiar;
                 if(listado.length == 0){
                    document.getElementById("nombres").value= "";
                    document.getElementById("apellidos").value= "";
                    document.getElementById("domicilio").value= "";
                    document.getElementById("celular").value= "";
                    document.getElementById("telefono").value= "";
                    document.getElementById("edad").value= 0;
                    document.getElementById("ocupacion").value= "";
                    document.getElementById("discapacidad").checked="";
                    document.getElementById('descDiscapacidad').style.display = 'none';

                 }
                 for (var i=0; i<listado.length;i++) {
                    document.getElementById("nombres").value= listado[i].nombres;
                    document.getElementById("apellidos").value= listado[i].apellidos;
                    document.getElementById("domicilio").value= listado[i].domicilio;
                    document.getElementById("celular").value= listado[i].celular;
                    document.getElementById("telefono").value= listado[i].telefono;
                    document.getElementById("edad").value= listado[i].edad;
                    document.getElementById("ocupacion").value= listado[i].ocupacion;
                    if(listado[i].discapacidad){
                        document.getElementById("discapacidad").checked="checked";
                        document.getElementById('descDiscapacidad').style.display = '';
                        document.getElementById("descDiscapacidad1").value= listado[i].descDiscapacidad;
                    }
                    else{
                        document.getElementById("discapacidad").checked="";
                        document.getElementById('descDiscapacidad').style.display = 'none';
                    }

                 }
             };
         };
         xmlhttp.open("POST","Familiar?json="+inputCi.value);
         xmlhttp.send();
     }
</script>

<% 
    if(u.isAdmin()){

%>
<% 
    Familiar u1=null;
    int ciPersonal = Integer.valueOf(request.getParameter("ciPersonal"));
    if(request.getParameter("ciFamiliar")!=null){
        int ciFamiliar = Integer.valueOf(request.getParameter("ciFamiliar"));
        ManejadorPersonal mp = new ManejadorPersonal();
        u1= mp.getFamiliar(ciPersonal, ciFamiliar);
        mp.CerrarConexionManejador();
        
    }
    if(ciPersonal!=-1){
            %>
            <p align="left"><a href="personal.jsp?id=<%=ciPersonal%>"><img src="images/atras.png" width="15%"/></a></p>
            <%
            
        }
%>
<h1 align="center"><u><% if (u1!=null){out.print("Familiar: "+u1.getNombre()+" "+u1.getApellido());}else{out.print("Agregar Familiar");}%></u></h1>


    <table  width='70%' style="font-size: 130%" >
        <tr>
            <td valign='top' width='40%'>
                <img src="images/familia.png" width="80%" />
            </td>
            <td width='10%'>
                
            </td>
            <td width='50%'>
                <form method="post" id="formulario1" action="Familiar?ciFamiliar=<%if (u1!=null){out.print(u1.getCi());}else{out.print("-1");} %>&ciPersonal=<%=ciPersonal%>" >
                <table>
                    <tr>
                        <td>
                            <p><b>V&iacute;nculo:</b></p>
                        </td>
                        <td>
                            <select name="idVinculo" form="formulario1">
                                <%
                                ManejadorCodigos mc= new ManejadorCodigos();
                                ArrayList<Tipo> ag = mc.getTiposFamiliares();
                                mc.CerrarConexionManejador();
                                for(Tipo g: ag ){
                                    String s="";
                                    if(u1!=null && u1.getTipo().getId()==g.getId()){
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
                            <p><b>CI:</b></p>
                        </td>
                        <td>
                            <p align="center"><input type="text" onblur="buscarFamiliar(this);" value="<% if (u1!=null){out.print(u1.getCi());}%>" <% if (u1!=null){out.print("readonly='readonly'");}else{out.print("required='required'");}%> name="ci" /></p>
                        </td>
                    </tr>
                    <tr>
                        <td>
                            <p><b>Nombres:</b></p>
                        </td>
                        <td>
                            <p align="center"><input id="nombres" type="text" value="<% if (u1!=null){out.print(u1.getNombre());}%>" required='required' name="nombres" /></p>
                        </td>
                    </tr>
                    <tr>
                        <td>
                            <p><b>Apellidos:</b></p>
                        </td>
                        <td>
                            <p align="center"><input id="apellidos" type="text" value="<% if (u1!=null){out.print(u1.getApellido());}%>" required='required' name="apellidos" /></p>
                        </td>
                    </tr>
                    <tr>
                        <td>
                            <p><b>Tel&eacute;fono</b></p>
                        </td>
                        <td>
                            <p><input type="text" id="telefono" value="<% if (u1!=null){out.print(u1.getTelefono());}%>" name="telefono"/></p>
                        </td>
                    </tr>
                    <tr>
                        <td>
                            <p><b>Celular</b></p>
                        </td>
                        <td>
                            <p><input type="text" id="celular" value="<% if (u1!=null){out.print(u1.getCelular());}%>" name="celular"/></p>
                        </td>
                    </tr>
                    <tr>
                        <td>
                            <p><b>Domicilio</b></p>
                        </td>
                        <td>
                            <p><input type="text" id="domicilio" value="<% if (u1!=null){out.print(u1.getDomicilio());}%>" name="domicilio" /></p>
                        </td>
                    </tr>
                    <tr>
                        <td>
                            <p><b>Edad</b></p>
                        </td>
                        <td>
                            <p><input type="number" id="edad" <% if (u1!=null){out.print("value="+u1.getEdad());}%> name="edad" /></p>
                        </td>
                    </tr>
                    <tr>
                        <td>
                            <p><b>Ocupaci&oacute;n</b></p>
                        </td>
                        <td>
                            <p><input type="text" id="ocupacion" value="<% if (u1!=null){out.print(u1.getOcupacion());}%>" name="ocupacion" /></p>
                        </td>
                    </tr>
                     <tr>
                        <td>
                            <p><b>Discapacidad</b></p>
                        </td>
                        <td>
                            <input type=checkbox id="discapacidad" onclick="showDescDiscapacidad(this);" name="discapacidad" <% if(u1!=null && u1.getDiscapacidad()){out.print("checked='checked'");} %>/>
                        </td>
                    </tr>
                    <tr id="descDiscapacidad" <% if ((u1!=null && !u1.getDiscapacidad())||(u1==null)){out.print("style='display: none'");}%>>
                        <td>
                            <p style="color: #333333"><b>Descripci&oacute;n discapacidad:</b></p>
                        </td>
                        <td>
                            <input type=text id="descDiscapacidad1" name="descDiscapacidad" <%if( u1!=null){out.print("value='"+u1.getDescDiscapacidad()+"'");} %> />
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

