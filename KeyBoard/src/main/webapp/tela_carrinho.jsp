<%-- 
    Document   : tela_carrinho
    Created on : 18 de set. de 2022, 13:28:04
    Author     : Tiago
--%>

<%@page import="com.mycompany.keyboard.util.Resultado"%>
<%@page import="com.mycompany.keyboard.model.strategy.FunctionsUtilsPagamento"%>
<%@page import="com.mycompany.keyboard.util.Masks"%>
<%@page import="com.mycompany.keyboard.model.domain.Endereco"%>
<%@page import="com.mycompany.keyboard.model.domain.Cliente"%>
<%@page import="com.mycompany.keyboard.model.domain.Carrinho"%>
<%@page import="com.mycompany.keyboard.model.domain.Item"%>
<%@page import="com.mycompany.keyboard.model.domain.Teclado"%>
<%@page import="java.util.List"%>
<%@page import="com.mycompany.keyboard.model.domain.EntidadeDominio"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link href="css/css_carrinho.css" rel="stylesheet">
        <title>Carrinho</title>
    </head>
    <body>
        <% 
            if(session.getAttribute("usuario") == null) {
                response.sendRedirect("tela_cadastrar_cliente.jsp");
            }
            
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
        <div class="box_provisorio">
            <form action="endereco" method="post">
                <p> Adicionar novo endereço: <%@ include file="form_endereco.jsp" %>
                <p> <input type="submit" name="operacao" value="ADICIONAR">
            </form>
        </div>
        <p>    
        <form action="pedido" method="post">
            <div class="box_selecione_endereco">
                <img class="icon_title_forms_carrinho" src="icons/localization_point.png">
                <span class="text_title_forms_carrinho">SELECIONE UM ENDEREÇO</span>
                <p><select class="select_endereco_carrinho" name="endereco_entrega">
                        <option value="0">Selecione...</option>
                        <%
                            if(cliente != null && cliente.getEnderecos() != null && cliente.getEnderecos().size() != 0){
                                for(Endereco endereco_select : cliente.getEnderecos()){
                                    out.print("<option value='" + endereco_select.getId() + "'>" +
                                    endereco_select.getCidade() + " - " + endereco_select.getBairro() + " - "
                                    + endereco_select.getLogradouro() + " - " + endereco_select.getNumero()
                                    + "</option>");
                                }
                            }
                        %>
                    </select>
            </div>
                    
            <div class="box_produto_frete">
                <img class="icon_title_forms_carrinho" src="icons/cesta.png">
                <span class="text_title_forms_carrinho">PRODUTO</span>
                
                <div class="linha_produto_frete"></div>
                    <%
                        if (carrinho != null) {
                            StringBuilder sbRegistro = new StringBuilder();
                            StringBuilder sbLink = new StringBuilder();
                            StringBuilder sbLinkAddOrRmOneProduto = new StringBuilder();
                            StringBuilder sbLinkRemoverProduto = new StringBuilder();

                            if (carrinho.getItens() != null) {                          

                                for (Item item : carrinho.getItens()){

                                    sbRegistro.setLength(0);
                                    sbLink.setLength(0);
                                    sbLinkAddOrRmOneProduto.setLength(0);
                                    sbLinkRemoverProduto.setLength(0);

                                    sbLinkAddOrRmOneProduto.append("<a href=carrinho?");
                                    sbLinkAddOrRmOneProduto.append("teclado_id=");
                                    sbLinkAddOrRmOneProduto.append(item.getTeclado().getId());
                                    sbLinkAddOrRmOneProduto.append("&");
                                    sbLinkAddOrRmOneProduto.append("operacao=");
                                    sbLinkAddOrRmOneProduto.append("ADICIONAR");
                                    sbLinkAddOrRmOneProduto.append("&");
                                    sbLinkAddOrRmOneProduto.append("qtd_add_carrinho"+item.getTeclado().getId()+"=");
                                    
                                    sbLinkRemoverProduto.append("<a href=carrinho?");
                                    sbLinkRemoverProduto.append("teclado_id=");
                                    sbLinkRemoverProduto.append(item.getTeclado().getId());
                                    sbLinkRemoverProduto.append("&");
                                    sbLinkRemoverProduto.append("operacao=");
                                    sbLinkRemoverProduto.append("DELETAR");
                                    sbLinkRemoverProduto.append(">");

                                    sbLink.append("<a href=teclado?");
                                    sbLink.append("teclado_id=");
                                    sbLink.append(item.getTeclado().getId());
                                    sbLink.append("&");
                                    sbLink.append("operacao=");
                                    sbLink.append("VISUALIZAR");

                                    
                                    sbRegistro.append(
                                    "<div class='produtos_carrinho'>"
                                        +"<img class='img_produto_carrinho' src='img/teclado"+item.getTeclado().getId()+".png'>"
                                        +"<div class='descricao_produto'>"+Masks.buildDescricaoTeclado(item.getTeclado())+"</div>"
                                        +"<div class='qtd_produto'>"
                                            +"<center>"
                                                +"Quant:"
                                                +"<br>"+sbLinkAddOrRmOneProduto+"-1>< </a>"+item.getQuantidade()+" " + sbLinkAddOrRmOneProduto + "1> ></a>"
                                                +"<br>"+sbLinkRemoverProduto+"Remover</a>"
                                            +"</center>"        
                                        +"</div>" 
                                        +"<span class='preco_produto'>"+Masks.itemValorTotal(item)+"</span>"
                                    +"</div>"
                                    +"<br>"
                                    );

                                    out.print(sbRegistro.toString());
                                }

                            }
                        }
                    %>
                                            
            </div>    
            
            <div class="resumo_carrinho">
                <div>
                    <img class="icon_title_forms_carrinho" src="icons/arquivo.png">
                    <span class="text_title_forms_carrinho">RESUMO</span>
                </div>
                <div class="valor_produtos_carrinho">
                    <span>Valor dos produtos:</span>
                    <span>
                        <%
                            if (carrinho != null && carrinho.getItens() != null && carrinho.getItens().size() != 0){
                                out.print(Masks.buildDinheiro(FunctionsUtilsPagamento.calcularValorTotal(carrinho.getItens())));
                            }
                        %>
                    </span>
                </div> 
                <p><center><input class="button_white btn_finalizar_carrinho" type="submit" name="operacao" value="FINALIZAR"></center>
            </div>    
        </form>
        <%
            Resultado messageError = null;
            
            if ((Resultado)request.getAttribute("messageError") != null) {
                messageError = (Resultado)request.getAttribute("messageError");
            }
            
            if(messageError != null && messageError.getMsg() != null) {
                out.println("<script>messageError('"+messageError.getMsg()+"')</script>");
            }
            
        %>
    </body>
</html>
