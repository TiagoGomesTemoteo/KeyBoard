<%-- 
    Document   : lista_cliente
    Created on : 4 de set. de 2022, 19:32:52
    Author     : Tiago
--%>

<%@page import="com.mycompany.keyboard.model.domain.enums.Estatus"%>
<%@page import="com.mycompany.keyboard.model.domain.CartaoDeCredito"%>
<%@page import="com.mycompany.keyboard.model.domain.enums.BandeiraCartao"%>
<%@page import="com.mycompany.keyboard.model.domain.Pedido"%>
<%@page import="com.mycompany.keyboard.util.Masks"%>
<%@page import="java.text.SimpleDateFormat"%>
<%@page import="com.mycompany.keyboard.model.domain.Cliente"%>
<%@page import="java.util.List"%>
<%@page import="com.mycompany.keyboard.model.domain.EntidadeDominio"%>
<%@page import="com.mycompany.keyboard.util.Resultado"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
    </head>
    <body>
        <%
            Resultado resultado = (Resultado) session.getAttribute("resultado");
        %>
        
        <%@ include file="links_menu.jsp" %>
        <table border="1px">
            <tr>
                <td>
                    Número de Pedido:
                </td>
                <td>
                    Data:
                </td>
                <td>
                    Pagamento:
                </td>
                <td>
                    Status:
                </td>
                
            </tr>
            <%
                if (resultado != null) {
                    List<EntidadeDominio> entidades = resultado.getEntidades();
                    StringBuilder sbRegistro = new StringBuilder();
                    StringBuilder sbLinkAtualizaStatus = new StringBuilder();
        
                    if (entidades != null) {
                        for (int i = 0; i < entidades.size(); i++) {
                            Pedido pedido = (Pedido) entidades.get(i);
                            sbRegistro.setLength(0);
                            sbLinkAtualizaStatus.setLength(0);
                            
                            sbLinkAtualizaStatus.append("pedido?");
                            sbLinkAtualizaStatus.append("pedido_id=");
                            sbLinkAtualizaStatus.append(pedido.getId());
                            sbLinkAtualizaStatus.append("&");
                            sbLinkAtualizaStatus.append("operacao=");
                            sbLinkAtualizaStatus.append("ALTERAR");
                            sbLinkAtualizaStatus.append("&");
                            sbLinkAtualizaStatus.append("estatus="); 
                            

                                                       
                            sbRegistro.append(
                                    "<tr>"
                                    + "<td>"
                                    + pedido.getId()
                                    + "</td>"
                                    + "<td>"
                                    + Masks.brazilianDate(pedido.getDt_cadastro())
                                    + "</td>"
                                    + "<td>"
                                    + Masks.buildTextFormaPagamento(pedido.getPagamento())
                                    + "</td>"                                    
                            );

                            out.print(sbRegistro.toString());
                            
                            out.print("<td>");
                            out.print("<select name='bandeira' onchange='location.href=this.value'>");
                
                            for (Estatus estatus : Estatus.values()) {
                                    out.print("<option value='" + sbLinkAtualizaStatus.toString() + estatus.toString() + "'");
                                    if (pedido.getEstatus().toString().equals(estatus.toString())){out.print(" selected");}
                                    out.print(">" + estatus.toString()+"</option>");
         
                            }
                            
                            out.print("</select>");
                            out.print("</td>");

                    }
                }
            }    
            %>
        </table>   
    </body>
</html>
