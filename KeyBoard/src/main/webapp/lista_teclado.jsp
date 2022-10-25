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
            Resultado resultado = (Resultado) session.getAttribute("teclados");

            Cliente cliente_logado = null;
            
            if(session.getAttribute("usuario") != null ) {
                cliente_logado = (Cliente) session.getAttribute("usuario");   
            }
            
        %>
        
        <%@ include file="links_menu.jsp" %>
        
        <p>TECLADOS:
        
            <%
                if (resultado != null) {
                    List<EntidadeDominio> entidades = resultado.getEntidades();
                    StringBuilder sbRegistro = new StringBuilder(0);
                    StringBuilder sbLink = new StringBuilder(0);
                    StringBuilder sbButtonAdd = new StringBuilder(0);
                    
                    if (entidades != null) {
                        for (int i = 0; i < entidades.size(); i++) {
                            Teclado teclado = (Teclado) entidades.get(i);
                            int id = teclado.getId();
                            
                            sbRegistro.setLength(0);
                            sbLink.setLength(0);
                            sbButtonAdd.setLength(0);

                            sbLink.append("<a href=teclado?");
                            sbLink.append("teclado_id=");
                            sbLink.append(id);
                            sbLink.append("&");
                            sbLink.append("operacao=");
                            sbLink.append("VISUALIZAR");
                            sbLink.append(">");
                            
                            if (teclado.getQtd_disponivel() == 0) {
                                sbButtonAdd.append("<div class='adicionar_produto_caixa_produto_sem_estoque'>SEM ESTOQUE</div>");
                            
                            } else if (cliente_logado != null){
                                sbButtonAdd.append("<input type='submit' name='operacao' value='ADICIONAR' class='adicionar_produto_caixa_produto'>");                            
                            
                            } else {
                                sbButtonAdd.append("<div class='adicionar_produto_caixa_produto centralizar'><a href='tela_cadastrar_cliente.jsp'>ADICIONAR</a></div>");
                            }
                            
                            
                            sbRegistro.append(
                              "<div class='caixa_produto'>"
                                + "<img src='img/teclado"+teclado.getId()+".png' class='imagem_produto_caixa_produto' />"
                                + "<div class='descricao_produto_caixa_produto'>" + sbLink.toString() + Masks.buildDescricaoTeclado(teclado) + "</a></div>"
                                + "<div class='valor_produto_caixa_produto'>"+Masks.buildDinheiro(teclado.getValor_venda())+"</div>" 
                                + "<div class='div_quantidade_add_produto_caixa_produto'>"
                                    + "<center>Quant:</center>"
                                    + "<center>"
                                        + "<form action='carrinho' method='post'>"
                                        + "<div class='div_quantidade_add_produto_caixa_produto_nivel_2'>"                             
                                            + "<button type='button' id='menos' onclick='less("+id+")'>-</button>"
                                            + "<input type='text' name='qtd_add_carrinho"+id+"' value='0' id='qtd_add_carrinho"+id+"' class='input_quantidade_add_produto_caixa_produto'>" 
                                            + "<button type='button' id='mais' onclick='more("+id+")'>+</button>"                              
                                            + "<input type='hidden' name='teclado_id' value='" + id + "'>"
                                            + "<input type='hidden' name='qtd_disp_teclado"+id+"' id='qtd_disp_teclado"+id+"' value='" + teclado.getQtd_disponivel() + "'>"
                                        + "</div>"
                                + "</div>"        
                                + sbButtonAdd.toString()
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
