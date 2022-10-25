<%-- 
    Document   : lista_cliente
    Created on : 4 de set. de 2022, 19:32:52
    Author     : Tiago
--%>

<%@page import="com.mycompany.keyboard.model.domain.Teclado"%>
<%@page import="com.mycompany.keyboard.model.domain.enums.Estatus"%>
<%@page import="com.mycompany.keyboard.model.domain.CartaoDeCredito"%>
<%@page import="com.mycompany.keyboard.model.domain.enums.BandeiraCartao"%>
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
        <link href="css/css_tela_detalhes_teclado.css" rel="stylesheet">
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Detalhes do Teclado</title>
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
                qtd_disp_teclado = parseInt(document.getElementById('qtd_disp_teclado' + id).value);        
                numero = document.getElementById('qtd_add_carrinho' + id).value;               
            }

            function setValue(value, id) {
                document.getElementById('qtd_add_carrinho' + id).value = value;
            }      
        </script>
    </head>
    <body>
        <%
            Teclado teclado = (Teclado) request.getAttribute("teclado");
        %>
        
        <%@ include file="links_menu.jsp" %>
        
        <p>
      
        <%
            if (teclado != null) {
                
                StringBuilder sbButtonAdd = new StringBuilder(0);
                StringBuilder sbRegistro = new StringBuilder(0);
                int id = teclado.getId();
                
                 if (teclado.getQtd_disponivel() == 0) {
                    sbButtonAdd.append("<div class='btn_comprar_detalhes_produto_sem_estoque'>SEM ESTOQUE</div>");

                } else {
                    sbButtonAdd.append("<input type='submit' name='operacao' value='ADICIONAR'  class='button_green btn_comprar_detalhes_produto'>");
                }

                sbRegistro.append(
                "<div class='box_detalhes_produto'>"
                   +"<div class='title_detalhe_produto'>" + Masks.buildDescricaoTeclado(teclado) + "</div>"    
                   +"<div>"
                        +"<img class='imagem_detalhe_teclado' src='img/teclado"+teclado.getId()+".png'>"
                    +"</div>"
                    +"<div class='text_vendido_por_detalhes_produto'>"
                        +"Vendido e entregue por KeyBoards | <span class='text_em_estoque'>Em estoque</span>"
                        +"<p><span class='valor_produto_detalhes'>"+Masks.buildDinheiro(teclado.getValor_venda())+"</span>"
                    +"</div>"
                    +"<form action='carrinho' method='post'>" 
                        +"<div class='add_quantidade'>"
                            +"<button type='button' id='menos' onclick='less("+id+")'>-</button>"
                            +"<input type='text' name='qtd_add_carrinho"+id+"' value='0' id='qtd_add_carrinho"+teclado.getId()+"' class='qtd_add_produto_detalhes'>"
                            +"<button type='button' id='mais' onclick='more("+id+")'>+</button>"
                            +"<input type='hidden' name='qtd_disp_teclado"+id+"' id='qtd_disp_teclado"+id+"' value='" + teclado.getQtd_disponivel() + "'>"
                            +"<input type='hidden' name='teclado_id' value='" + id + "'>"
                        +"</div>"
                        +sbButtonAdd.toString()
                    +"</form>" 
                +"</div>"
                );        

                out.print(sbRegistro.toString());
            }                
        %> 
        
        <%
            if (teclado != null) { 
                StringBuilder info_produto = new StringBuilder(0);
                
                info_produto.append(
                "<div class='box_info_produto'>"
                    +"<div>"
                        +"<img class='icon_info_produto' src='icons/arquivo.png'>"
                        +"<span class='text_info_produto'>INFORMAÇÕES DO PRODUTO</span>"
                    +"</div>"
                    +"<br>"
                    +"<div class='info_produto'>"
                        +"<p>Marca:                                  <span class='info_produto_dado'>"+teclado.getMarca()+"</span></p>"
                        +"<p>Modelo:                                 <span class='info_produto_dado'>"+teclado.getModelo()+"</span></p>"
                        +"<p>Quantidade de Teclas:                   <span class='info_produto_dado'>"+teclado.getQtd_teclas()+"</span></p>"
                        +"<p>Polifonia máxima:                       <span class='info_produto_dado'>"+teclado.getPolifonia_max()+"</span></p>"
                        +"<p>Peso:                                   <span class='info_produto_dado'>"+teclado.getPeso()+"</span></p>"
                        +"<p>Dimensões(altura, largura, comprimento):<span class='info_produto_dado'>"+teclado.getAltura() + " x " + teclado.getLargura() + " x " + teclado.getComprimento()+"</span></p>"
                        +"<p>Cor:                                    <span class='info_produto_dado'>"+teclado.getCor()+"</span></p>"
                        +"<p>Voltagem:                               <span class='info_produto_dado'>"+teclado.getVoltagem()+"</span></p>"
                    +"</div>"
                +"</div>"
                );
                
                out.print(info_produto.toString());
            }
        %>

        
    </body>
</html>
