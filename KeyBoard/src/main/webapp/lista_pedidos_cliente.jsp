<%-- 
    Document   : lista_cliente
    Created on : 4 de set. de 2022, 19:32:52
    Author     : Tiago
--%>

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
        <div class="filtrado_por">
            Filtrado por: 
            <select>
                <option></option>
            </select>
        </div>
      
        <%
            if (resultado != null) {
                List<EntidadeDominio> entidades = resultado.getEntidades();
                StringBuilder sbRegistro = new StringBuilder();
                StringBuilder sbProdutos = new StringBuilder();
                StringBuilder sbTroca = new StringBuilder();
                StringBuilder sbProdutosTroca = new StringBuilder();
                int indice = 0;
                
                if (entidades != null) {
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
                                +"<img class='img_produto_pedido' src='img/teclado.png'>"
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
                                    +"<img class='img_produto_pedido' src='img/teclado.png'>"
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

                        sbRegistro.append(                                              
                        "<div class='box_pedido'>"
                            +"<div class='dados_pedido'>"
                                +"<div class='info_pedido'>"
                                    +"Número de Pedido"
                                    +"<p>" + pedido.getId()
                                +"</div>"
                                +"<div class='info_pedido'>"
                                    +"Status"
                                    +"<p>" + pedido.getEstatus()
                                +"</div>"
                                +"<div class='info_pedido'>"
                                    +"Data"
                                    +"<p>" + Masks.brazilianDate(pedido.getDt_cadastro())
                                +"</div>"
                                +"<div class='info_pedido'>"
                                    +"Pagamento"
                                    +"<p>" + Masks.buildTextFormaPagamento(pedido.getPagamento())
                                +"</div>"
                            +"</div>"
                            +"<div class='endereco_pedido'>"
                                +"<span class='title_endereco_itens_pedido'>ENDEREÇO</span>"
                                +"<div class='info_endereco_pedido'>"
                                    + Masks.buildDescricaoEndereco(pedido.getEndereco())
                                +"</div>"
                            +"</div>"
                            +"<div class='itens_pedido'>"
                                +"<span class='title_endereco_itens_pedido'>ITENS</span>"                
                                +"<p>"
                                +sbProdutos.toString()
                            +"</div>"
                        +"</div>"
                        );
                        
                        out.print(sbRegistro.toString());
                        
                        
                        sbTroca.append(
                        "<form action='troca' method='post'>"
                            +"<input type='hidden' name='pedido_origem_id' value='"+pedido.getId()+"'>"
                            +"<div class='box_pedido_trocar'>"
                               +"ITENS"
                                +"<p> Selecione a quantidade e os itens que deseja trocar."
                                + sbProdutosTroca.toString()
                                + "<input type='submit' name='operacao' value='SOLICITAR_TROCA'>"
                            +"</div>"
                        +"</form>"
                        ); 
                        
                        out.print(sbTroca.toString());
                    }

                }
            }
        %>         
    </body>
</html>
