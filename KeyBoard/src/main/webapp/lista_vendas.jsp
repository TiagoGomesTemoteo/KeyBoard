<%-- 
    Document   : lista_cliente
    Created on : 4 de set. de 2022, 19:32:52
    Author     : Tiago
--%>

<%@page import="com.mycompany.keyboard.model.domain.enums.Estatus"%>
<%@page import="com.mycompany.keyboard.model.domain.Troca"%>
<%@page import="com.mycompany.keyboard.model.domain.Item"%>
<%@page import="com.mycompany.keyboard.util.ParameterParser"%>
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
        <link href="css/css_lista_vendas.css" rel="stylesheet">
        <title>JSP Page</title>
        <script>
            var numero;

            function less(id) {
              getValue(id);  
              if (numero != 0) {
                numero--;
              }
              setValue(numero, id);
            }

            function more(id) {
              getValue(id);
              numero++;
              setValue(numero, id);
            }
            
            function getValue(id){
                numero = document.getElementById('qtd_troca'+id).value;
            }

            function setValue(value, id) {
              document.getElementById('qtd_troca'+id).value = value;
            }

        </script>
    </head>
    <body>
        <%
            if(session.getAttribute("usuario") == null ) {
                response.sendRedirect("tela_cadastrar_cliente.jsp");
            } else {
                Cliente cliente = (Cliente) session.getAttribute("usuario");
                
                if (cliente.getNivel_acesso() != 2) {
                    response.sendRedirect("tela_cadastrar_cliente.jsp");
                }
            }
            
            Resultado resultado = (Resultado) session.getAttribute("resultado");
        %>
        
        <%@ include file="links_menu.jsp" %>
        <br>
        <br>
        <div class="title_meus_pedidos">
            <img class="icon_title_meus_pedidos" src="icons/aumentar.png"/>
            <span class="title_pages"> VENDAS</span>
        </div>
        
        <br>
        <div class="filtrado_por" >
            Filtrado por: 
            <select onchange="location.href=this.value">
                <option> Selecione...</option>
                <option value="pedido?operacao=CONSULTAR""> Pedidos</option>  
                <option value="troca?operacao=CONSULTAR""> Pedidos de Troca</option>  
            </select>
        </div>        
      
        <%
            if (resultado != null) {                
                List<EntidadeDominio> entidades = resultado.getEntidades();                
                
                if (entidades != null && entidades.size() > 0) {   
                    String sbOperacoesAdm;
                    StringBuilder sbProdutosTroca = new StringBuilder();
                    StringBuilder sbTroca = new StringBuilder();  
                    int indice = 0;
                    
                    if(entidades.get(0) instanceof Pedido) {
                        StringBuilder sbRegistro = new StringBuilder();
                        StringBuilder sbProdutos = new StringBuilder();                                             
                                               
                        for (int i = 0; i < entidades.size(); i++) {
                            Pedido pedido = (Pedido) entidades.get(i);
                            sbRegistro.setLength(0);
                            sbProdutos.setLength(0);
                            sbTroca.setLength(0);
                            sbProdutosTroca.setLength(0);
                            sbOperacoesAdm = "";
                            
                            switch (pedido.getEstatus().getEstatus()) {
                                case 12: 
                                    sbOperacoesAdm = ""
                                    + "<div class='operacoesADM operacoesAdmPedido'>"
                                        + "<a href='pedido?operacao=ALTERAR&pedido_id="+pedido.getId()+"&estatus="+Estatus.EM_TRANSPORTE+"'>Entregar itens ao cliente</a>"
                                    + "</div>";
                                break;
                                case 9:
                                    sbOperacoesAdm = ""
                                    + "<div class='operacoesADM operacoesAdmPedido'>"
                                    + "<a href='pedido?operacao=ALTERAR&pedido_id="+pedido.getId()+"&estatus="+Estatus.ENTREGUE+"'>Pedido entregue</a>"
                                    + "</div>";
                                break;
                            }

                            for (Item item: pedido.getItens()) {                            
                                indice++;
                                
                                sbProdutos.append(
                                "<div class='produto_pedido'>"
                                    +"<img class='img_produto_pedido' src='img/teclado"+item.getTeclado().getId()+".png'>"
                                    +"<span class='descricao_produto_pedido'> "+Masks.buildDescricaoTeclado(item.getTeclado())+"</span>"
                                    +"<div class='qtd_produto_pedido'>"
                                        +"<center>"
                                            +"Quant:"
                                            +"<br>" + item.getQuantidade()
                                        +"</center>"    
                                    +"</div>"
                                +"</div>"
                                +"<br>"
                                );                                   
                                
                            }

                            sbRegistro.append(                                              
                            "<div class='box_pedido'>"
                                +"<div class='dados_pedido'>"
                                    +"<div class='info_title_pedido'>"
                                        +"Número de Pedido"
                                        +"<p class='info_pedido'>" + pedido.getId()
                                    +"</div>"
                                    +"<div class='info_title_pedido'>"
                                        +"Status"
                                        +"<p class='info_pedido color_green'>" + Masks.buildDescricaoStatus(pedido.getEstatus())
                                    +"</div>"
                                    +"<div class='info_title_pedido'>"
                                        +"Data"
                                        +"<p class='info_pedido'>" + Masks.brazilianDate(pedido.getDt_cadastro())
                                    +"</div>"
                                    +"<div class='info_title_pedido'>"
                                        +"Pagamento"
                                        +"<p class='info_pedido'>" + Masks.buildTextFormaPagamento(pedido.getPagamento())
                                    +"</div>"
                                +"</div>"
                                +"<div class='linha_divisoria_pedidos_cliente'></div>"
                                +"<div class='endereco_pedido'>"
                                    +"<p><span class='title_endereco_itens_pedido'>ENDEREÇO</span>"
                                    +"<div class='info_endereco_pedido'>"
                                        + Masks.buildDescricaoEndereco(pedido.getEndereco())
                                    +"</div>"
                                +"</div>"
                                +"<div class='linha_divisoria_pedidos_cliente'></div>"
                                +"<p><div class='itens_pedido'>"
                                    +"<span class='title_endereco_itens_pedido'>ITENS</span>"                
                                    +"<p>"
                                    +sbProdutos.toString()
                                +"</div>"
                                + sbOperacoesAdm
                                +"<div class='space'></div>"
                            +"</div>"
                            );

                            out.print(sbRegistro.toString());                             
                            
                        }
                    } else if (entidades.get(0) instanceof Troca) {
                                
                        StringBuilder sbPedidosTroca = new StringBuilder();
                        StringBuilder sbItensTroca = new StringBuilder();

                        for (int i = 0; i < entidades.size(); i++) {
                            Troca troca = (Troca) entidades.get(i);
                            
                            sbTroca.setLength(0);
                            sbProdutosTroca.setLength(0);                           
                            sbItensTroca.setLength(0);
                            sbPedidosTroca.setLength(0);
                            sbOperacoesAdm = null;
                            
                            if (troca.getEstatus().getEstatus() == 15) {
                                sbOperacoesAdm = ""
                                + "<div class='operacoesADM operacoesADMTroca'>"
                                + "<a href='troca?operacao=ALTERAR&troca_id="+troca.getId()+"&estatus="+Estatus.TROCA_AUTORIZADA+"'>Aprovar Troca</a>"
                                + "</div>";  
                            }
                            
                            for (Item item: troca.getProdutos()) {                            

                                sbItensTroca.append(
                                "<div class='produto_pedido'>"
                                    +"<img class='img_produto_pedido' src='img/teclado"+item.getTeclado().getId()+".png'>"
                                    +"<span class='descricao_produto_pedido'> "+Masks.buildDescricaoTeclado(item.getTeclado())+"</span>"
                                    +"<div class='qtd_produto_pedido'>"
                                        +"<center>"
                                            +"Quant:"
                                            +"<br>" + item.getQuantidade()
                                        +"</center>"    
                                    +"</div>"
                                +"</div>"
                                +"<br>"
                                );
                                
                                sbProdutosTroca.append(
                                "<div class='produto_pedido'>"
                                        +"<input type='checkbox' name='item_troca' value='T"+item.getTeclado().getId()+"'>"
                                        +"<img class='img_produto_pedido' src='img/teclado"+item.getTeclado().getId()+".png'>"
                                        +"<span class='descricao_produto_pedido'>"+Masks.buildDescricaoTeclado(item.getTeclado())+"</span>"
                                        +"<div class='qtd_produto_pedido'>"
                                            +"<center>"
                                                +"Quant:"
                                                +"<button type='button' id='menos' onclick='less("+indice+")'>-</button>"
                                                +"<input class='qtd_troca' type='text' name='item_troca' value='0' id='qtd_troca"+indice+"'>"
                                                +"<button type='button' id='mais' onclick='more("+indice+")'>+</button>"
                                            +"</center>"
                                        +"</div>"
                                    +"</div>"
                                );
                            }

                            sbPedidosTroca.append(                                              
                            "<div class='box_pedido'>"
                                +"<div class='dados_pedido'>"
                                    +"<div class='info_title_pedido'>"
                                        +"Número de pedido de Troca"
                                        +"<p class='info_pedido'>" + troca.getId()
                                    +"</div>"
                                    +"<div class='info_title_pedido'>"
                                        +"Status"
                                        +"<p class='info_pedido color_green'>" + Masks.buildDescricaoStatus(troca.getEstatus())
                                    +"</div>"
                                    +"<div class='info_title_pedido'>"
                                        +"Data"
                                        +"<p class='info_pedido'>" + Masks.brazilianDate(troca.getDt_cadastro())
                                    +"</div>"
                                    +"<div class='info_title_pedido'>"
                                        +"Pagamento"
                                        +"<p class='info_pedido'>" + Masks.buildTextFormaPagamento(troca.getPedidoOrigem().getPagamento())
                                    +"</div>"
                                +"</div>"
                                +"<div class='linha_divisoria_pedidos_cliente'></div>"
                                +"<div class='endereco_pedido'>"
                                    +"<p><span class='title_endereco_itens_pedido'>ENDEREÇO</span>"
                                    +"<div class='info_endereco_pedido'>"
                                        + Masks.buildDescricaoEndereco(troca.getPedidoOrigem().getEndereco())
                                    +"</div>"
                                +"</div>"
                                +"<div class='linha_divisoria_pedidos_cliente'></div>"
                                +"<p><div class='itens_pedido'>"
                                    +"<span class='title_endereco_itens_pedido'>ITENS</span>"                
                                    +"<p>"
                                    +sbItensTroca.toString()
                                +"</div>"
                            +"</div>"
                            );

                            out.print(sbPedidosTroca.toString());
                            
                            if (troca.getEstatus() == Estatus.EM_TROCA) {
                                sbTroca.append(
                                "<form action='troca' method='post'>"
                                    +"<input type='hidden' name='pedido_origem_id' value='"+troca.getId()+"'>"
                                    +"<div class='box_pedido_trocar'>"
                                        +"<div>"
                                            +"<img class='icon_itens_troca' src='icons/caixa-de-devolucao.png'>"
                                            +"<span class='title_itens_troca'>ITENS PARA ESTOQUE</span>"
                                            +"<p class='frase_itens_troca'> Selecione os itens que vão voltar para o estoque."    
                                        +"</div>"
                                        +"<p>"
                                        + sbProdutosTroca.toString()
                                        + "<input class='button_white btn_enviar_estoque' type='submit' name='operacao' value='ENVIAR_ITENS_PARA_ESTOQUE'>"
                                        + "<p>" 
                                        + sbOperacoesAdm 
                                        +"<div class='space'></div>"
                                    +"</div>"                                    
                                +"</form>"
                                ); 

                                out.print(sbTroca.toString());
                            }
                        }
                    }                          
                }                 
            }
        %>         
    </body>
</html>
