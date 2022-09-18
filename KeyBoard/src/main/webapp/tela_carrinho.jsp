<%-- 
    Document   : tela_carrinho
    Created on : 18 de set. de 2022, 13:28:04
    Author     : Tiago
--%>

<%@page import="com.mycompany.keyboard.model.domain.Endereco"%>
<%@page import="com.mycompany.keyboard.model.domain.Cliente"%>
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
            Cliente cliente = null;
            
            if (session.getAttribute("cliente_info") != null){ 
                cliente = (Cliente) session.getAttribute("cliente_info");
            }
        %>  
            
        <%@ include file="links_menu.jsp" %>
        
        <p>CARRINHO
            
        <p>SELECIONE UM ENDEREÇO
            <select name="endereco">
                <option value="">Selecione...</option>
                <%
                    if(cliente != null && cliente.getEnderecos() != null && cliente.getEnderecos().size() != 0){
                        for(Endereco endereco : cliente.getEnderecos()){
                            out.print("<option value='" + endereco.getId() + "'>" +
                            endereco.getCidade() + " - " + endereco.getBairro() + " - "
                            + endereco.getLogradouro() + " - " + endereco.getNumero()
                            + "</option>");
                        }
                    }
                %>
                
            </select>

        <form action="endereco" method="post">
                <p> Adicionar: <%@ include file="form_endereco.jsp" %>
                <p> <input type="submit" name="operacao" value="ADICIONAR">
        </form>
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
                    Preço:
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
                                    + item.getTeclado().getValor_venda()
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
