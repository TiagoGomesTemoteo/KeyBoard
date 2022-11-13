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
        <link href="css/css_lista_pedidos_cliente.css" rel="stylesheet">
        <title>JSP Page</title>
        <script>
            var numero;
            var qtd_disp_teclado;

            function less(id) {
              getValue(id);  
              if (numero != 0) {
                numero--;
              }
              setValue(numero, id);
            }

            function more(id) {
              getValue(id);
              if(numero < qtd_disp_teclado){
                    numero++;    
                    setValue(numero, id);
                } 
            }
            
            function getValue(id){
                numero = document.getElementById('qtd_troca'+id).value;
                qtd_disp_teclado = parseInt(document.getElementById('qtd_disp_teclado' + id).value);   
            }

            function setValue(value, id) {
              document.getElementById('qtd_troca'+id).value = value;
            }
            
            
            
        </script>
    </head>
    <body>
        <%
            
            Cliente cliente_logado = null;
            
            if(session.getAttribute("usuario") == null) {
                response.sendRedirect("tela_cadastrar_cliente.jsp");
            
            } else {
                cliente_logado = (Cliente) session.getAttribute("usuario");
            }
            
            Resultado resultado = (Resultado) session.getAttribute("resultado");
        %>
        
        <%@ include file="links_menu.jsp" %>
        <br>
        <br>
        <div class="title_meus_pedidos">
            <img class="icon_title_meus_pedidos" src="icons/cesta.png"/>
            <span class="title_pages"> MEUS PEDIDOS</span>
        </div>
        
        <br>
        <div class="filtrado_por" >
            Filtrado por: 
            <select onchange="location.href=this.value">
                <option> Selecione...</option>
                <option value="pedido?operacao=CONSULTAR&cliente_id=<%out.print(cliente_logado.getId());%>"> Pedidos</option>  
                <option value="troca?operacao=CONSULTAR&cliente_id=<%out.print(cliente_logado.getId());%>"> Pedidos de Troca</option>  
            </select>
        </div>
        <%
            if (resultado != null) {                
                List<EntidadeDominio> entidades = resultado.getEntidades();                
                
                if (entidades != null && entidades.size() > 0) {    
                    String sbOperacoesAdm;
                    
                    if(entidades.get(0) instanceof Pedido) {
                        StringBuilder sbRegistro = new StringBuilder();
                        StringBuilder sbProdutos = new StringBuilder();
                        StringBuilder sbTroca = new StringBuilder();
                        StringBuilder sbProdutosTroca = new StringBuilder();
                        int indice = 0;

                        for (int i = 0; i < entidades.size(); i++) {
                            Pedido pedido = (Pedido) entidades.get(i);
                            sbRegistro.setLength(0);
                            sbProdutos.setLength(0);
                            sbTroca.setLength(0);
                            sbProdutosTroca.setLength(0);                         
                            
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

                                sbProdutosTroca.append(
                                "<div class='produto_troca'>"
                                        +"<input type='checkbox' name='item_troca' value='T"+item.getTeclado().getId()+"'>"
                                        +"<img class='img_produto_pedido' src='img/teclado"+item.getTeclado().getId()+".png'>"
                                        +"<span class='descricao_produto_pedido'>"+Masks.buildDescricaoTeclado(item.getTeclado())+"</span>"
                                        +"<div class='qtd_produto_pedido'>"
                                            +"<center>"
                                                +"Quant:"
                                                +"<button type='button' id='menos' onclick='less("+indice+")'>-</button>"
                                                +"<input class='qtd_troca' type='text' name='item_troca' value='0' id='qtd_troca"+indice+"'>"
                                                +"<button type='button' id='mais' onclick='more("+indice+")'>+</button>"
                                                +"<input type='hidden' name='qtd_disp_teclado"+indice+"' id='qtd_disp_teclado"+indice+"' value='" +item.getQuantidade()+ "'>"
                                            +"</center>"
                                        +"</div>"
                                    +"</div>"
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
                                + "<div class='linha_divisoria_pedidos_cliente'></div>"
                                +"<div class='endereco_pedido'>"
                                    +"<p><span class='title_endereco_itens_pedido'>ENDEREÇO</span>"
                                    +"<div class='info_endereco_pedido'>"
                                        + Masks.buildDescricaoEndereco(pedido.getEndereco())
                                    +"</div>"
                                +"</div>"
                                + "<div class='linha_divisoria_pedidos_cliente'></div>"
                                +"<p><div class='itens_pedido'>"
                                    +"<span class='title_endereco_itens_pedido'>ITENS</span>"                
                                    +"<p>"
                                    +sbProdutos.toString()
                                +"</div>"
                            +"</div>"
                            );

                            out.print(sbRegistro.toString());

                            if (pedido.getEstatus() == Estatus.ENTREGUE) {
                                sbTroca.append(
                                "<form action='troca' method='post'>"
                                    +"<input type='hidden' name='pedido_origem_id' value='"+pedido.getId()+"'>"
                                    +"<div class='box_pedido_trocar'>"
                                        +"<div>"
                                            +"<img class='icon_itens_troca' src='icons/caixa-de-devolucao.png'>"
                                            +"<span class='title_itens_troca'>ITENS PARA TROCA</span>"
                                            +"<p class='frase_itens_troca'> Selecione a quantidade e os itens que deseja trocar."    
                                        +"</div>"
                                        +"<p>"
                                        + sbProdutosTroca.toString()
                                        + "<input class='btn_solicitar_troca button_green' type='submit' name='operacao' value='SOLICITAR_TROCA'>"
                                    +"</div>"
                                    +"<br><br>"
                                +"</form>"
                                ); 

                                out.print(sbTroca.toString());
                            }
                        }  
                        
                    } else if (entidades.get(0) instanceof Troca) {
                                
                        StringBuilder sbPedidosTroca = new StringBuilder();
                        StringBuilder sbItensTroca = new StringBuilder();
                        
                        for (int i = 0; i < entidades.size(); i++) {
                            Troca troca = (Troca) entidades.get(i);
                            
                            sbOperacoesAdm = "";
                            sbItensTroca.setLength(0);
                            sbPedidosTroca.setLength(0);
                            
                            if (troca.getEstatus().getEstatus() == 16) {
                                sbOperacoesAdm = "" 
                                +"<div class='operacoes'>"
                                    +"<a href='troca?operacao=ALTERAR&troca_id="+troca.getId()+"&estatus="+Estatus.TROCADO+"'>Itens recebidos</a>"
                                +"</div>";   
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
                            }

                            sbPedidosTroca.append(                                              
                            "<div class='box_pedido_trocar'>"
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
                                + sbOperacoesAdm
                                +"<div class='space'></div>"
                            +"</div>"
                            +"<br>"
                            );

                            out.print(sbPedidosTroca.toString());
                        }
                    }
                } 
            }
        %>         
    </body>
</html>
