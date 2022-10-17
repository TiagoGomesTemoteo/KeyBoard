<%@page import="com.mycompany.keyboard.util.Masks"%>
<%@page import="javax.swing.JOptionPane"%>
<%@page import="com.mycompany.keyboard.model.domain.Teclado"%>
<%@page import="java.util.List"%>
<%@page import="com.mycompany.keyboard.model.domain.EntidadeDominio"%>
<%@page import="com.mycompany.keyboard.util.Resultado"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <link href="css/css_caixa_produto_tela_lista_teclado.css" rel="stylesheet">
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
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
            Resultado resultado = (Resultado) session.getAttribute("resultado");
        %>
        
        <%@ include file="links_menu.jsp" %>
        
        <p>TECLADOS:
        
            <%
                if (resultado != null) {
                    List<EntidadeDominio> entidades = resultado.getEntidades();
                    StringBuilder sbRegistro = new StringBuilder(0);
                    StringBuilder sbLink = new StringBuilder(0);
                    
                    if (entidades != null) {
                        for (int i = 0; i < entidades.size(); i++) {
                            Teclado teclado = (Teclado) entidades.get(i);
                            int id = teclado.getId();
                            
                            sbRegistro.setLength(0);
                            sbLink.setLength(0);

                            sbLink.append("<a href=teclado?");
                            sbLink.append("teclado_id=");
                            sbLink.append(id);
                            sbLink.append("&");
                            sbLink.append("operacao=");
                            sbLink.append("VISUALIZAR");
                            sbLink.append(">");
                            
                            sbRegistro.append(
                              "<div class='caixa_produto'>"
                                + "<img src='img/teclado.png' class='imagem_produto_caixa_produto' />"
                                + "<div class='descricao_produto_caixa_produto'>" + sbLink.toString() + Masks.buildDescricaoTeclado(teclado) + "</a></div>"
                                + "<div class='valor_produto_caixa_produto'>"+Masks.buildDinheiro(teclado.getValor_venda())+"</div>" 
                                + "<div class='div_quantidade_add_produto_caixa_produto'>"
                                    + "<center>Quant:</center>"
                                    + "<center>"
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

                    }
                }
            %>         
    </body>
</html>
