<%-- 
    Document   : escribiente
    Created on : Apr 18, 2017, 7:14:16 PM
    Author     : Gisel
--%>

<%@ include file="header.jsp" %>
<script src="js/jquery-1.9.1.min.js"></script>
<%
    if(u.isAdmin()){
%>

<script>
    function novedadesSubmit(form){
        //var file = document.getElementById("file").files[0];
        //document.getElementById("nombre").value = file.name;
        document.getElementById("enviando").style.display = "block";
        form.submit();
        //document.getElementById("enviando").style.display = "none";
        return true;

    }
    function novedadesSubmit1(){
        var file = document.getElementById("file").files[0];
        document.getElementById("nombre").value = file.name;
        var data = new FormData(document.getElementById("novedades")); //Creamos los datos a enviar con el formulario
        $.ajax({
            url: 'Novedades?subir=1', //URL destino
            data: data,
            processData: false, //Evitamos que JQuery procese los datos, daría error
            contentType: false, //No especificamos ningún tipo de dato
            type: 'POST'
        });

    }
</script>
<form id="novedades" enctype="multipart/form-data" method="post" action="Novedades?subir=1" onsubmit=" return novedadesSubmit(this);">
    
    <table style="text-align: left" align="center">
        <tr>
            <td style="width: 30%">
                <img src="images/nove.png" width="90%"/>
            </td>
            <td style="width: 70%">
                <table>
                    <tr>
                        <td>
                            <div id="enviando" style="display: none; z-index: 5; position: absolute">
                                <img src="images/cargando (1).gif" />
                            </div>
                            <b>Seleccione la fecha:</b>
                        </td>
                        <td>
                            <input type="date" name="fecha" required="required"/>
                        </td>
                    </tr>
                     <tr>
                        <td>
                            <b>Seleccione el archivo a subir:</b>
                        </td>
                        <td>
                            <input type="file" name="novedad" id="file" required="required" multiple="multiple"/> 
                        </td>
                    </tr>
                    
                </table>
            </td>
        </tr>
    </table>
    <input type="text" name="nombre" id="nombre" value="2" hidden="hidden"/> 
    <input type="submit" value="Subir" />
    <!-- <button onclick="novedadesSubmit()" > Subir </button> -->
</form>
<%
    }
%>
<%@ include file="footer.jsp" %>
