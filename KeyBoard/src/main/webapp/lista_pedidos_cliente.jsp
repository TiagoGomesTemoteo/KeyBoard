<%-- 
    Document   : lista_cliente
    Created on : 4 de set. de 2022, 19:32:52
    Author     : Tiago
--%>

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
                    Status:
                </td>
                <td>
                    Data:
                </td>
                <td>
                    Pagamento:
                </td>
                
            </tr>
            <%
                if (resultado != null) {
                    List<EntidadeDominio> entidades = resultado.getEntidades();
                    StringBuilder sbRegistro = new StringBuilder();
                    
        
                    if (entidades != null) {
                        for (int i = 0; i < entidades.size(); i++) {
                            Pedido pedido = (Pedido) entidades.get(i);
                            sbRegistro.setLength(0);

                            sbRegistro.append(
                                    "<tr>"
                                    + "<td>"
                                    + pedido.getId()
                                    + "</td>"
                                    + "<td>"
                                    + pedido.getEstatus().toString()
                                    + "</td>"
                                    + "<td>"
                                    + Masks.brazilianDate(pedido.getDt_cadastro())
                                    + "</td>"
                                    + "<td>"
                                    + Masks.buildTextFormaPagamento(pedido.getPagamento())
                                    + "</td>"
                                    + "<td>"
                                    + "</tr>"
                            );

                            out.print(sbRegistro.toString());
                        }

                    }
                }
            %>
        </table>   
    </body>
</html>
