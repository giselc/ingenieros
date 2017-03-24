<%-- 
    Document   : documento
    Created on : Mar 20, 2017, 10:16:43 PM
    Author     : Gisel
--%>

<%@page import="java.util.ArrayList"%>
<%@page import="Classes.Tipo"%>
<%@page import="Manejadores.ManejadorCodigos"%>
<%@page import="java.util.Base64"%>
<%@page import="java.sql.Blob"%>
<%@page import="Manejadores.ManejadorPersonal"%>
<%@page import="Classes.Documento"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<%@ include file="header.jsp" %>
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
        function finalizar(f,m){
                var input = document.createElement('input');input.type = 'hidden';input.name = 'foto2';
                if((document.getElementById('uploadImage').value == "") && m=="N"){
                    alert("Debe agregar una imagen.");
                    return false;
                }
                else{
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
                }
                f.appendChild(input);
                f.submit();
                return true;            
        }
</script>
<% 
    if(u.isAdmin() || u.isS1()){

%>
<% 
    Documento d=null;    
    if(request.getParameter("id")!=null){
        int id = Integer.valueOf(request.getParameter("id"));
        ManejadorPersonal mp = new ManejadorPersonal();
        d= mp.getDocumento(id);
    }
%>
<h1 align="center"><u><% if (d!=null){out.print("Editar Documento");}else{out.print("Agregar Documento");}%></u></h1>
<%
String foto="";
if(d==null){
    foto=  "images/documento1.png";
}
else{
    if(d.getImagen()==null){
        foto=  "images/documento1.png";
    }
    else{
        Blob b= d.getImagen();
        if (b!=null && (int)b.length()!= 0){
            byte[] by=b.getBytes(1,(int)b.length());
            String imgDataBase64=new String(Base64.getEncoder().encode(by));
            foto = "data:image/jpg;base64,"+imgDataBase64;

        }
    }
}

%>
<form method="post" name="formulario" id="formulario" onsubmit="return finalizar(this,'<% if(request.getParameter("id")!=null){out.print("S");}else{out.print("N");}%>');" action="Documento?id=<%if (d!=null){out.print(d.getId());}else{out.print("-1");} %>" >
    <table  width='70%' align='center' style="text-align: left">
        <tr>
            <td valign='top' width='50%' style="text-align: center">
                <input type="text" hidden="hidden" value="<%=request.getParameter("ci")%>" name='ci'/>
                <p align="center"><label for="uploadImage" ><img src="<%= foto %>" id="uploadPreview" style="width: 50%" onclick="" /></label></p>
                <input hidden="hidden" type="file" name="foto" id="uploadImage" onchange="PreviewImage(); canvasimage('uploadImage','canvas6');" accept="image/*"/>
                <canvas id="canvas6" hidden="hidden" > </canvas>
            </td>
            <td width='50%'>
                <table>
                    <tr>
                        <td>
                            <p><b>Tipo Documento:</b></p>
                        </td>
                        <td>
                            <select name="tipoDocumento" form="formulario">
                                <%
                                ManejadorCodigos mc= new ManejadorCodigos();
                                ArrayList<Tipo> ag = mc.getTiposDocumentos();
                                for(Tipo g: ag ){
                                    String s="";
                                    if(d!=null && d.getTipo().getId()==g.getId()){
                                        s="selected";
                                    }
                                    out.print("<option " + s +" value='"+String.valueOf(g.getId()) +"'>"+ g.getDescripcion() +"</option>");
                                }
                                %>
                            </select>
                        </td>
                    </tr>
                </table>
            </td>
        </tr>
    </table>  
    <p align='right'><input type="submit"  value="Aceptar" /></p>
</form>       




<% 
    }
    else{
         response.sendRedirect("");
    }

%>
<%@ include file="footer.jsp" %>