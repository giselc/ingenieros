<%-- 
    Document   : personal
    Created on : Mar 17, 2017, 9:22:39 PM
    Author     : Gisel
--%>

<%@page import="Classes.UnidadMilitar"%>
<%@page import="Classes.Tipo"%>
<%@page import="Classes.Grado"%>
<%@page import="java.util.ArrayList"%>
<%@page import="java.util.ArrayList"%>
<%@page import="Manejadores.ManejadorCodigos"%>
<%@page import="java.util.Base64"%>
<%@page import="java.sql.Blob"%>
<%@page import="Classes.Personal"%>
<%@page import="Manejadores.ManejadorPersonal"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>

<script type="text/javascript">
        function PreviewImage() {
            var oFReader = new FileReader();
            oFReader.readAsDataURL(document.getElementById("uploadImage").files[0]);
            oFReader.onload = function (oFREvent) {
                document.getElementById("uploadPreview").src = oFREvent.target.result;
            };   
        };
        function canvasimage(input,canv) {
            var canvas= document.getElementById(canv);
            var ctx = canvas.getContext('2d');
            var img = new Image;
            var oFReader = new FileReader();
            oFReader.readAsDataURL(document.getElementById(input).files[0]);
            oFReader.onload = function (oFREvent) {
                img.src = oFREvent.target.result;
            };
            
            img.onload = function() {
                var MAX_WIDTH = 800;
                var MAX_HEIGHT = 600;
                var width = img.width;
                var height = img.height;
                if (width > height) {
                  if (width > MAX_WIDTH) {
                    height *= MAX_WIDTH / width;
                    width = MAX_WIDTH;
                  }
                } else {
                  if (height > MAX_HEIGHT) {
                    width *= MAX_HEIGHT / height;
                    height = MAX_HEIGHT;
                  }
                }
                canvas.width = width;
                canvas.height = height;
                ctx.drawImage(img, 0, 0, width, height);
            };

        }
        function finalizar(f){
            if(f.elements["ci"].value.length == 8 ){
                var input = document.createElement('input');input.type = 'hidden';input.name = 'foto2';
                if(document.getElementById('uploadImage').value == ""){
                    input.value = "";
                }
                else{
                    document.getElementById('uploadImage').value = "";
                    var canvas= document.getElementById('canvas6');
                    var dataURL = canvas.toDataURL();
                    dataURL = dataURL.split(',');
                    input.value = dataURL[1];
                }
                f.appendChild(input);
                f.submit();
                return true;
            }
            else{
                alert("Cédula incorrecta.");
                return false;
            }
            
        }
        function showExpMision(b){
            if(b.checked){
                document.getElementById('lugarExpMision').style.display = '';
            }
            else{
                document.getElementById('lugarExpMision').style.display = 'none';
            }
        }
        function showCarneSalud(b){
            if(b.checked){
                document.getElementById('vtoCarneSalud').style.display = '';
            }
            else{
                document.getElementById('vtoCarneSalud').style.display = 'none';
            }
        }
        function showLicenciaConducir(b){
            if(b.checked){
                document.getElementById('nroLicCond').style.display = '';
                document.getElementById('catLicCond').style.display = '';
                document.getElementById('vtoLicCond').style.display = '';
            }
            else{
                document.getElementById('nroLicCond').style.display = 'none';
                document.getElementById('catLicCond').style.display = 'none';
                document.getElementById('vtoLicCond').style.display = 'none';
            }
        }
</script>
<% 
    Personal p=null;    
    if(request.getParameter("id")!=null){
        int ci = Integer.valueOf(request.getParameter("id"));
        ManejadorPersonal mp = new ManejadorPersonal();
        p= mp.getPersonalBasico(ci);
        mp.CerrarConexionManejador();
    }
%>
<h1 align="center"><u><% if (p!=null){out.print("Personal: "+p.getNombre()+" "+p.getApellido());}else{out.print("Agregar personal");}%></u></h1>
<%
String foto="";
if(p==null){
    foto=  "images/silueta.jpg";
}
else{
    if(p.getFoto()==null){
        foto=  "images/silueta.jpg";
    }
    else{
        Blob b= p.getFoto();
        if (b!=null && (int)b.length()!= 0){
            byte[] by=b.getBytes(1,(int)b.length());
            String imgDataBase64=new String(Base64.getEncoder().encode(by));
            foto = "data:image/jpg;base64,"+imgDataBase64;

        }
    }
}

%>
<form method="post" name="formulario" id="formulario" onsubmit="return finalizar(this);" action="Personal?id=<%if (p!=null){out.print(p.getCi());}else{out.print("-1");} %>" >
    <table  width='70%' align='center' style="text-align: left">
        <tr>
            <td valign='top' width='40%'>
                <table>
                    <tr>
                        <td>
                            <p align="center"><label for="uploadImage" ><img src="<%= foto %>" id="uploadPreview" style="width: 90%" onclick=""/></label></p>
                        </td>
                    </tr>
                    <tr>
                        <td><input hidden="hidden" type="file" name="foto" id="uploadImage" onchange="PreviewImage(); canvasimage('uploadImage','canvas6');" accept="image/*"/></td>
                    </tr>
                    <tr>    
                        <td><canvas id="canvas6" hidden="hidden" > </canvas></td>
                    </tr>
                </table>
            </td>
            <td width='10%'>
                
            </td>
            <td width='50%'>
                <table>
                    <tr>
                        <td>
                            <p><b>C.I.:</b></p>
                        </td>
                        <td>
                            <input type=number name="ci" <%if( p!=null){ out.print("value='"+Integer.valueOf(p.getCi())+"'"); out.print("readonly=\"readonly\"");} %> required="required" />
                        </td>
                    </tr>
                    <tr>
                        <td>
                            <p><b>Grado:</b></p>
                        </td>
                        <td>
                            <select name="grado" form="formulario">
                                <%
                                ManejadorCodigos mc= new ManejadorCodigos();
                                ArrayList<Tipo> ag = mc.getGrados();
                                for(Tipo g: ag ){
                                    String s="";
                                    if(p!=null && p.getGrado().getId()==g.getId()){
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
                            <p><b>Nombre:</b></p>
                        </td>
                        <td>
                            <p align="center"><input type="text" value="<% if (p!=null){out.print(p.getNombre());}%>" required='required' name="nombre" /></p>
                        </td>
                    </tr>
                    <tr>
                        <td>
                            <p><b>Apellido:</b></p>
                        </td>
                        <td>
                            <p align="center"><input type="text" value="<% if (p!=null){out.print(p.getApellido());}%>" required='required' name="apellido" /></p>
                        </td>
                    </tr>
                    <tr>
                        <td>
                            <p><b>Unidad:</b></p>
                        </td>
                        <td>
                            <select name="unidadMilitar" form="formulario">
                                <%
                                ArrayList<UnidadMilitar> au = mc.getUnidadesMilitares();
                                for(UnidadMilitar um: au ){
                                    String s="";
                                    if(p!=null && p.getUnidadPerteneciente().getId()== um.getId()){
                                        s="selected";
                                    }
                                    out.print("<option " + s +" value='"+String.valueOf(um.getId()) +"'>"+ um.getNombre() +"</option>");
                                }
                                mc.CerrarConexionManejador();
                                %>
                            </select>
                        </td>
                    </tr>
                </table>
            </td>
        </tr>
    </table>  
    <table style="padding-left: 15%; text-align: left">
        
        <tr>
            <td>
                <p><b>Fecha de Nacimiento:</b></p>
            </td>
            <td>
                <input type=date name="fechaNac" <%if( p!=null){out.print("value="+p.getFechaNac());} %> />
            </td>
        </tr>
        <tr>
            <td>
                <p><b>Vencimiento C.I.:</b></p>
            </td>
            <td>
                <input type=date name="vtoCi" <%if( p!=null){out.print("value="+p.getVtoCI());} %> />
            </td>
        </tr>
        <tr>
            <td>
                <p><b>Pasaporte:</b></p>
            </td>
            <td>
                <input type=text name="pasaporte" <%if( p!=null){out.print("value='"+p.getPasaporte()+"'");} %> />
            </td>
        </tr>
        <tr>
            <td>
                <p><b>Vencimiento pasaporte:</b></p>
            </td>
            <td>
                <input type=date name="vtoPas" <%if( p!=null){out.print("value="+p.getVtoPas());} %> />
            </td>
        </tr>
        <tr>
            <td>
                <p><b>C.C.:</b></p>
            </td>
            <td>
                <input type=text name="cc" <%if( p!=null){out.print("value='"+p.getCc()+"'");} %> />
            </td>
        </tr>
        <tr>
            <td>
                <p><b>C.C. Nro.:</b></p>
            </td>
            <td>
                <input type=number name="ccNro" <%if( p!=null){out.print("value='"+p.getCcNro()+"'");} %> />
            </td>
        </tr>
        <tr>
            <td>
                <p><b>Licencia de Conducir</b></p>
            </td>
            <td>
                <input type=checkbox onclick="showLicenciaConducir(this);" name="licenciaConducir" <% if(p!=null && p.getLicenciaConducir()){out.print("checked='checked'");} %>/>
            </td>
        </tr>
        <tr id="nroLicCond" <% if ((p!=null && !p.getLicenciaConducir())||(p==null)){out.print("style='display: none'");}%>>
            <td>
                <p style="color: #333333"><b>Numero Licencia:</b></p>
            </td>
            <td>
                <input type=number  name="nroLicCond" <%if( p!=null){out.print("value='"+p.getNroLicCond()+"'");} %> />
            </td>
        </tr>
        <tr id="catLicCond" <% if ((p!=null && !p.getLicenciaConducir())||(p==null)){out.print("style='display: none'");}%>>
            <td>
                <p  style="color: #333333"><b>Categoria Licencia:</b></p>
            </td>
            <td>
                <input type=text  name="catLicCond" <%if( p!=null){out.print("value='"+p.getCatLicCond()+"'");} %> />
            </td>
        </tr>
        <tr id="vtoLicCond" <% if ((p!=null && !p.getLicenciaConducir())||(p==null)){out.print("style='display: none'");}%>>
            <td>
                <p  style="color: #333333"><b>Vencimiento Licencia:</b></p>
            </td>
            <td>
                <input type=date  name="vtoLicCond" <%if( p!=null){out.print("value='"+p.getVtoLicCond()+"'");} %> />
            </td>
        </tr>
        <tr>
            <td>
                <p><b>Carn&eacute; de Salud</b></p>
            </td>
            <td>
                <input type=checkbox onclick="showCarneSalud(this);" name="carneSalud" <% if(p!=null && p.getCarneSalud()){out.print("checked='checked'");} %>/>
            </td>
        </tr>
        <tr id="vtoCarneSalud" <% if ((p!=null && !p.getCarneSalud())||(p==null)){out.print("style='display: none'");}%>>
            <td>
                <p  style="color: #333333"><b>Vencimiento carn&eacute;:</b></p>
            </td>
            <td>
                <input type=date  name="vtoCarneSalud" <%if( p!=null){out.print("value="+p.getVtoCarneSalud());} %> />
            </td>
        </tr>
        <tr>
            <td>
                <p><b>Experiencia Misi&oacute;n:</b></p>
            </td>
            <td>
                <input type=checkbox onclick="showExpMision(this);" name="expMision" <% if(p!=null && p.getExpMision()){out.print("checked='checked'");} %>/>
            </td>
        </tr>
        <tr id="lugarExpMision" <% if ((p!=null && !p.getExpMision())||(p==null)){out.print("style='display: none'");}%>>
            <td>
                <p  style="color: #333333"><b>Misiones anteriores:</b></p>
            </td>
            <td>
                <textarea rows="4" cols="50" name="lugarExpMision" form="formulario"><%if( p!=null){out.print(p.getLugarExpMision());} %></textarea>
            </td>
        </tr>
    </table>
    <p align='right'><input type="submit"  value="Aceptar" /></p>
</form>       




