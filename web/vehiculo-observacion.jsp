<%-- 
    Document   : vehiculo-observacion
    Created on : May 19, 2017, 4:14:03 PM
    Author     : Gisel
--%>

<%@page import="Classes.Tipo"%>
<%@page import="Manejadores.ManejadorCodigos"%>
<%@page import="Manejadores.ManejadorPersonal"%>
<%@page import="Classes.Personal"%>
<%@page import="java.util.ArrayList"%>
<%@page import="Manejadores.ManejadorVehiculo"%>
<%@page import="Classes.ObservacionVehiculo"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<%@ include file="header.jsp" %>
<script src="js/jquery-1.9.1.min.js"></script>
<%
    if(u.isAdmin()||u.isS4()){
        ObservacionVehiculo u1=null;
        if(request.getParameter("id")!=null){
            int id = Integer.valueOf(request.getParameter("id"));
            ManejadorVehiculo mv = new ManejadorVehiculo();
            u1= mv.getObservacionVehiculo(id);
            mv.CerrarConexionManejador();
        }
%>

<script>
    function observacionSubmit(form){
        //var file = document.getElementById("file").files[0];
        //document.getElementById("nombre").value = file.name;
        document.getElementById("enviando").style.display = "block";
        form.submit();
        //document.getElementById("enviando").style.display = "none";
        return true;

    }
    function showMantenimiento(b){
            if(b.checked){
                document.getElementById('tipomantenimiento').style.display = '';
                document.getElementById('hrkmMarcados').style.display = '';
                document.getElementById('escribiente').style.display = '';
                document.getElementById('operario').style.display = '';
            }
            else{
                document.getElementById('tipomantenimiento').style.display = 'none';
                document.getElementById('hrkmMarcados').style.display = 'none';
                document.getElementById('escribiente').style.display = 'none';
                document.getElementById('operario').style.display = 'none';
            }
        }
</script>
 <table  width='70%' style="font-size: 130%" >
        <tr>
            <td colspan="2">
                <p align="left"><a href="vehiculo.jsp?id=<% if(u1==null){out.print(request.getParameter("vehiculo"));} else{ out.print(u1.getIdVehiculo());}%>"><img src="images/atras.png" width="100%"/></a></p>
            </td>
        </tr>
        <tr>
            <td valign='top' width='40%'>
                <img src="images/documento1.png" width="80%" />
            </td>
            <td width='10%'>
                
            </td>
            <td width='50%'>
                <form id="observacion" enctype="multipart/form-data" method="post" action="VehiculoObservacion?vehiculo=<%=request.getParameter("vehiculo")%>&id=<%=request.getParameter("id")%>" onsubmit=" return observacionSubmit(this);">
                    <table>
                        <tr>
                            <td>
                                <b>Informaci&oacute;n sumaria:</b>
                            </td>
                            <td>
                                <input type="file" name="informacionSumaria" id="file" /> 
                                <%
                                if(u1!=null){
                                    out.print("<a href='ISVehiculos/"+u1.getId()+u1.getInfromacionSumaria().substring(u1.getInfromacionSumaria().indexOf("."))+"'>"+u1.getInfromacionSumaria()+"</p>");
                                }
                                %>
                            </td>
                        </tr>
                        <tr>
                            <td>
                                <div id="enviando"  style="position: fixed; top:0; left:0; width:100%; height: 100%;background: url('images/cargando (1).gif') center center no-repeat; background-size: 20%; display: none">
                                </div>
                                <b>Fecha:</b>
                            </td>
                            <td>
                                <input type="date" name="fecha" required="required" <% if(u1!=null){out.print("value='"+u1.getFecha()+"'");} %>/>
                            </td>
                        </tr>
                         
                        <tr>
                            <td>
                                <p><b>Hs o Km realizados</b></p>
                            </td>
                            <td>
                                <p align="center"><input type="number" <% if (u1!=null){out.print("value='"+u1.getHorasKilometrosRealizados()+"'");}%> name="horasKilometrosRealizados" /></p> 
                            </td>
                        </tr>
                        <tr>
                            <td style="vertical-align: top"><b>Observaciones:</b> </td>
                            <td>
                                <textarea rows="4" cols="50" name="observaciones" form="observacion"><% if(u1!=null){out.print(u1.getObservacion());} %></textarea>
                            </td>
                        </tr>
                        
                        <tr>
                            <td>
                                <p><b>Mantenimiento</b></p>
                            </td>
                            <td>
                                <input type=checkbox onclick="showMantenimiento(this);" name="mantenimiento" <% if(u1!=null && u1.isMantenimiento()){out.print("checked='checked'");} %>/>
                            </td>
                        </tr>
                        <tr id="tipomantenimiento" <% if ((u1!=null && !u1.isMantenimiento())||(u1==null)){out.print("style='display: none'");}%>>
                            <td>
                                <p style="color: #333333"><b>Tipo de Mantenimiento:</b></p>
                            </td>
                            <td>
                                <select name="idTipoMantenimiento" form="observacion">
                                    <%
                                    ManejadorCodigos mc= new ManejadorCodigos();
                                    ArrayList<Tipo> ag = mc.getTiposMantenimientoVehiculo();
                                    mc.CerrarConexionManejador();
                                    for(Tipo g: ag ){
                                        String s="";
                                        if(u1!=null && u1.getTipoMantenimiento()!=null && u1.getTipoMantenimiento().getId()==g.getId()){
                                            s="selected";
                                        }
                                        out.print("<option " + s +" value='"+String.valueOf(g.getId()) +"'>"+ g.getDescripcion() +"</option>");
                                    }
                                    %>
                                </select>
                            </td>
                        </tr>
                        <tr id="hrkmMarcados" <% if ((u1!=null && !u1.isMantenimiento())||(u1==null)){out.print("style='display: none'");}%>>
                            <td>
                                <p style="color: #333333"><b>Hs o Km marcados</b></p>
                            </td>
                            <td>
                                <p align="center"><input type="number" <% if (u1!=null){out.print("value='"+u1.getHorasKilometrosMarcados()+"'");}%> name="horasKilometrosMarcados" /></p> 
                            </td>
                        </tr>
                        
                        <tr id="operario" <% if ((u1!=null && !u1.isMantenimiento())||(u1==null)){out.print("style='display: none'");}%>>
                            <td><p style="color: #333333"><b>Operario:</b></p> </td>
                            <td>
                                <%
                                    ManejadorPersonal mp = new ManejadorPersonal();
                                    ArrayList<Personal> ap = mp.getListaPersonalBasico();
                                    out.print("<select name=\"operario\" form=\"observacion\">");
                                    for(Personal p: ap ){
                                        out.print("<option value='"+p.getCi()+"'");
                                        if(u1!=null && u1.getIdOperario()!=null && u1.getIdOperario().getCi()==p.getCi()){
                                            out.print(" selected ");
                                        }
                                        out.print(">"+ mp.obtenerNombreCompleto(p) +"</option>");
                                    } 
                                    out.print("</select>");
                                %>

                            </td>
                        </tr>
                        <tr id="escribiente" <% if ((u1!=null && !u1.isMantenimiento())||(u1==null)){out.print("style='display: none'");}%>>
                            <td><p style="color: #333333"><b>Escribiente:</b></p> </td>
                            <td>
                                <%
                                    out.print("<select name=\"escribiente\" form=\"observacion\">");
                                    for(Personal p: ap ){
                                        out.print("<option value='"+p.getCi()+"'");
                                        if(u1!=null && u1.getIdEscribiente()!=null && u1.getIdEscribiente().getCi()==p.getCi()){
                                            out.print(" selected ");
                                        }
                                        out.print(">"+ mp.obtenerNombreCompleto(p) +"</option>");
                                    } 
                                    out.print("</select>");
                                    mp.CerrarConexionManejador();
                                %>

                            </td>
                        </tr>
                    </table>
                    <input type="submit" value="Aceptar" />
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
