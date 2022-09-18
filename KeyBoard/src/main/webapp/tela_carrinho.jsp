<%-- 
    Document   : tela_carrinho
    Created on : 18 de set. de 2022, 13:28:04
    Author     : Tiago
--%>

<%@page import="com.mycompany.keyboard.model.domain.Carrinho"%>
<%@page import="com.mycompany.keyboard.model.domain.Item"%>
<%@page import="com.mycompany.keyboard.model.domain.Teclado"%>
<%@page import="java.util.List"%>
<%@page import="com.mycompany.keyboard.util.Resultado"%>
<%@page import="com.mycompany.keyboard.model.domain.EntidadeDominio"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Carrinho</title>
    </head>
    <body>
        <%
            Resultado resultado = (Resultado) session.getAttribute("resultado");
        %>  
            CARRINHO
            
            <p>TECLADOS:
            <table border="1px">
                <tr>
                    <td>
                        ID:
                    </td>
                    <td>
                        Marca:
                    </td>
                    <td>
                        Modelo:
                    </td>
                    <td>
                        Visualizar:
                    </td>
                    <td>                        
                    </td>
                </tr>
                <%
                    if (resultado != null) {
                        List<EntidadeDominio> entidades = resultado.getEntidades();
                        StringBuilder sbRegistro = new StringBuilder();
                        StringBuilder sbLink = new StringBuilder();

                        if (entidades != null) {
                            Carrinho carrinho = (Carrinho)entidades.get(0);
                            
                            for (Item item : carrinho.getItens()){
                            
                                sbRegistro.setLength(0);
                                sbLink.setLength(0);
                                                                
                                sbLink.append("<a href=teclado?");
                                sbLink.append("teclado_id=");
                                sbLink.append(item.getTeclado().getId());
                                sbLink.append("&");
                                sbLink.append("operacao=");
                                sbLink.append("VISUALIZAR");
                                
                                sbRegistro.append(
                                        "<tr>"
                                        + "<td>"
                                        + item.getTeclado().getId()
                                        + "</td>"
                                        + "<td>"
                                        + item.getTeclado().getMarca()
                                        + "</td>"
                                        + "<td>"
                                        + item.getTeclado().getModelo()
                                        + "</td>"
                                        + "<td>"
                                        + sbLink.toString() + ">" + "Visualizar" + "</a>"
                                        + "</td>"
                                );

                                out.print(sbRegistro.toString());
                            }

                        }
                    }
                %>
            </table>
    </body>
</html>