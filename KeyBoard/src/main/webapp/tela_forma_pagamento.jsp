<%-- 
    Document   : tela_pagamento
    Created on : 19 de set. de 2022, 20:42:46
    Author     : Tiago
--%>

<%@page import="com.mycompany.keyboard.model.domain.Pedido"%>
<%@page import="com.mycompany.keyboard.model.strategy.FunctionsUtilsPagamento"%>
<%@page import="com.mycompany.keyboard.util.Masks"%>
<%@page import="com.mycompany.keyboard.model.domain.CupomDeTroca"%>
<%@page import="com.mycompany.keyboard.model.domain.Carrinho"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link href="css/css_tela_forma_pagamento.css" rel="stylesheet">
        <title>JSP Page</title>
    </head>
    <body>
        
        <%
            Cliente cliente = null;
            Carrinho carrinho = null;
            Pedido pedido = null;
            
            if (session.getAttribute("cliente_info") != null) { 
                cliente = (Cliente) session.getAttribute("cliente_info");
            }
            
            if (session.getAttribute("cliente_carrinho") != null) { 
                carrinho = (Carrinho) session.getAttribute("cliente_carrinho");
            }
            
            if (session.getAttribute("pedido") != null) {
                pedido = (Pedido) session.getAttribute("pedido");
            }
            
        %>
            
        <%@ include file="links_menu.jsp" %>
        
        <div class="box_forma_pagamento">
            <div class="title_forma_pagamento">
                <img class="icon_forma_pagamento_title" src="icons/recibo.png">
                <span class="text_forma_pagamento_title">FORMA DE PAGAMENTO</span>
            </div>    
            <div class="box_provisorio_add_cartao">
                <p> Adicionar cartão:

                <form action="cartao" method="post">
                    <%@ include file="form_cartao.jsp" %>
                    <p> <input type="submit" name="operacao" value="ADICIONAR">
                </form>
            </div>
            <form action="pedido" method="post">
                
                <div class="box_cartao_credito">
                    <div>
                        <img class="icon_cartao_de_credito" src="icons/cartao-do-banco.png">
                        <span class="text_title_cartao_de_credito">CARTÃO DE CRÉDITO</span>
                    </div>  
                    
                    <div class="cartao_forma_pagamento">
                        <p>Cartão 01:

                        <br><br><input type="text" name="cartao" placeholder="Valor a ser pago">

                        <select name="cartao">
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
                    </div>
                    
                    <div class="cartao_forma_pagamento">        
                        <p>Cartão 02:

                        <br><br><input type="text" name="cartao" placeholder="Valor a ser pago">

                        <select name="cartao">
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
                    </div>    
                </div>
                
                <div class="box_cupom_troca">
                    <div>
                        <center>
                            <img class="icon_forma_pagamento_cupom"src="icons/cupom.png">
                            <span class="text_title_cupom">CUPOM DE TROCA</span>
                        </center>
                        
                    </div>
                    <div class="box_option_cupons">
                        <p><span>Caso deseje, você pode usar mais de um cupom na compra.</span>
                        <%
                            if(cliente != null && cliente.getCuponsDeTroca()!= null && cliente.getCuponsDeTroca().size() != 0){
                                for(CupomDeTroca cupom : cliente.getCuponsDeTroca()){
                                    out.print(""
                                    + "<div class='option_cupom'>"
                                        + "<input class='conteudo_option_cupom' type='checkbox' name='cupom' value='"+cupom.getId()+"'>" 
                                    + "<span>" + Masks.buildDinheiro(cupom.getValor()) + "</span><span>Validade: " + Masks.brazilianDate(cupom.getValidade()) + "</span>"
                                    + "</div>");
                                }
                            }
                        %>
                    </div>
                    
                                      
                </div>
                <p> <input class="button_green btn_pagar" type="submit" name="operacao" value="PAGAR"> 
                <div class="box_total_pagar">
                    
<!--                    <br><span class="text_box_total_pagar">TOTAL DA SUA COMPRA</span>
                        <p>
                        <span class="valor_box_total_pagar">
                        </span>-->
                Valor total dos pedidos: 
                <%
                    if (pedido != null){
                        out.print(Masks.buildDinheiro(pedido.getValor_total()));
                    }
                %>
                <br>Desconto promocional: 
                <%
                    if (pedido != null && pedido.getValor_total_com_desconto() > 0){
                        out.print(Masks.buildDinheiro(pedido.getValor_total() - pedido.getValor_total_com_desconto()));
                    }
                %>

                <br> Total:
                <%
                    if (pedido != null){
                        if (pedido.getValor_total_com_desconto() > 0) { 
                            out.print(Masks.buildDinheiro(pedido.getValor_total_com_desconto()));
                        } else {
                            out.print(Masks.buildDinheiro(pedido.getValor_total()));
                        }
                    }
                %>
                                              
                </div>
                <div class="button_white btn_voltar"><a href="carrinho?operacao=CONSULTAR">VOLTAR</a></div>
                
                <br><input type="text" name="cupom_promocional">  <input type="submit" name="operacao" value="APLICAR_CUPOM">
            </form>
            
        </div>    
    </body>
</html>
