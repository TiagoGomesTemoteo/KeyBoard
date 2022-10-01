<%-- 
    Document   : tela_pagamento
    Created on : 19 de set. de 2022, 20:42:46
    Author     : Tiago
--%>

<%@page import="com.mycompany.keyboard.model.strategy.FunctionsUtilsPagamento"%>
<%@page import="com.mycompany.keyboard.util.Masks"%>
<%@page import="com.mycompany.keyboard.model.domain.CupomDeTroca"%>
<%@page import="com.mycompany.keyboard.model.domain.Carrinho"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
    </head>
    <body>
        
        <%
            Cliente cliente = null;
            Carrinho carrinho = null;
            
            if (session.getAttribute("cliente_info") != null){ 
                cliente = (Cliente) session.getAttribute("cliente_info");
            }
            
            if (session.getAttribute("cliente_carrinho") != null){ 
                carrinho = (Carrinho) session.getAttribute("cliente_carrinho");
            }
        %>
            
        <%@ include file="links_menu.jsp" %>
        
            <p>FORMA DE PAGAMENTO

            <p> VALOR TOTAL: <%
                if (carrinho != null && carrinho.getItens() != null && carrinho.getItens().size() != 0){
                    out.print(FunctionsUtilsPagamento.calcularValorTotal(carrinho.getItens()));
                }
            %>
            
            <p> Adicionar cartão:

            <form action="cartao" method="post">
                <%@ include file="form_cartao.jsp" %>
                <p> <input type="submit" name="operacao" value="ADICIONAR">
            </form>
        <form action="pedido" method="post">
            <p>CARTÃO DE CRÉDITO

            <p>Cartão 01:

            <input type="text" name="valor_cartao1" placeholder="Valor a ser pago">

            <select name="cartao1">
                <option value="">Selecione um cartão</option>
                <%
                    if(cliente != null && cliente.getCartoesDeCredito()!= null && cliente.getCartoesDeCredito().size() != 0){
                        for(CartaoDeCredito cartao_select : cliente.getCartoesDeCredito()){
                            out.print("<option value='" + cartao_select.getId() + "'>" +
                            cartao_select.getBandeira() + " - " + cartao_select.getNumero() + " - "
                            + cartao_select.getNomeImpressoNoCartao() 
                            + "</option>");
                        }
                    }
                %>

            </select>

            <p>Cartão 02:

            <input type="text" name="valor_cartao2" placeholder="Valor a ser pago">

            <select name="cartao2">
                <option value="">Selecione um cartão</option>
                <%
                    if(cliente != null && cliente.getCartoesDeCredito()!= null && cliente.getCartoesDeCredito().size() != 0){
                        for(CartaoDeCredito cartao_select : cliente.getCartoesDeCredito()){
                            out.print("<option value='" + cartao_select.getId() + "'>" +
                            cartao_select.getBandeira() + " - " + cartao_select.getNumero() + " - "
                            + cartao_select.getNomeImpressoNoCartao() 
                            + "</option>");
                        }
                    }

                %>
            </select>

            <p>CUPONS DE TROCA
                <%
                    if(cliente != null && cliente.getCuponsDeTroca()!= null && cliente.getCuponsDeTroca().size() != 0){
                        for(CupomDeTroca cupom : cliente.getCuponsDeTroca()){
                            out.print("<p><input type='checkbox' name='cupom"+ cupom.getId() + "'>" +
                            cupom.getValor() + " - Validade: " + Masks.brazilianDate(cupom.getValidade()));
                        }
                    }
                %>

            <p> <input type="submit" name="operacao" value="PAGAR">    
        </form>    
    </body>
</html>
