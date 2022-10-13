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
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Detalhes do Teclado</title>
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
                numero = document.getElementById('qtd_add_carrinho' + id).value;
            }

            function setValue(value, id) {
              document.getElementById('qtd_add_carrinho' + id).value = value;
            }

            const a = document.querySelector("link_add_carrinho");
            
            a.href = a.href.toString()+document.getElementById();
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
            
                    StringBuilder sbRegistro = new StringBuilder(0);
                    int id = teclado.getId();
                    
                    sbRegistro.append(
                              "<div class='caixa_produto'>"
                                + "<div class='descricao_produto_caixa_produto'>" + Masks.buildDescricaoTeclado(teclado) + "</div>"
                                + "<img src='img/teclado.png' class='imagem_produto_caixa_produto' />"
                                + "<div class='valor_produto_caixa_produto'> R$ "+teclado.getValor_venda()+"</div>" 
                                + "<div class='div_quantidade_add_produto_caixa_produto'>"
                                    + "Quant:"
                                        + "<form action='carrinho' method='post'>"
                                        + "<div class='div_quantidade_add_produto_caixa_produto_nivel_2'>"                             
                                            + "<button type='button' id='menos' onclick='less("+id+")'>-</button>"
                                            + "<input type='text' name='qtd_add_carrinho"+id+"' value='0' id='qtd_add_carrinho"+teclado.getId()+"' class='input_quantidade_add_produto_caixa_produto'>" 
                                            + "<button type='button' id='mais' onclick='more("+id+")'>+</button>"                              
                                            + "<input type='hidden' name='teclado_id' value='" + id + "'>"
                                        + "</div>"
                                + "</div>"        
                                + "<input type='submit' name='operacao' value='ADICIONAR' class='adicionar_produto_caixa_produto'>" 
                                        + "</form>"                                          
                            + "</div>"
                            + "<p>"
                            );
                            
                            

                            out.print(sbRegistro.toString());
                }                
        %> 
        
        <p>INFORMAÇÕES DO PRODUTO
        <%
            if (teclado != null) { 
                StringBuilder info_produto = new StringBuilder(0);
              
                info_produto.append(
                    "<p>Marca: " + teclado.getMarca()
                    + "<p>Modelo: " + teclado.getModelo()
                    + "<p>Quantidade de Teclas: " + teclado.getQtd_teclas()
                    + "<p>Polifonia máxima: " + teclado.getPolifonia_max()
                    + "<p>Peso: " + teclado.getPeso()
                    + "<p>Dimensões(altura, largura, comprimento): " + teclado.getAltura() + " x " + teclado.getLargura() + " x " + teclado.getComprimento()
                    + "<p>Cor: " + teclado.getCor()
                    + "<p>Voltagem: " + teclado.getVoltagem()
                );
                
                out.print(info_produto.toString());
            }
        %>

        
    </body>
</html>
