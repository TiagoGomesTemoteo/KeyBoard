<%@page import="javax.swing.JOptionPane"%>
<%@page import="com.mycompany.keyboard.model.domain.Teclado"%>
<%@page import="java.util.List"%>
<%@page import="com.mycompany.keyboard.model.domain.EntidadeDominio"%>
<%@page import="com.mycompany.keyboard.util.Resultado"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
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
        <table border="1px">
            <tr>
                <td>
                    ID:
                </td>
                <td>
                    Marca:
                </td>
                <td>
                    Modelo:
                </td>
                <td>
                    Preço:
                </td>
                <td>
                    Visualizar:
                </td>
                <td>                        
                </td>
            </tr>
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
                            
                            sbRegistro.append(
                                    "<tr>"
                                    + "<td>"
                                    + teclado.getId()
                                    + "</td>"
                                    + "<td>"
                                    + teclado.getMarca()
                                    + "</td>"
                                    + "<td>"
                                    + teclado.getModelo()
                                    + "</td>"
                                    + "<td>"
                                    + teclado.getValor_venda()
                                    + "</td>"
                                    + "<td>"
                                    + sbLink.toString() + ">" + "Visualizar" + "</a>"
                                    + "</td>"
                                    + "<td>"
                                        + "<form action='carrinho' method='post'>"
                                            + "<button type='button' id='menos' onclick='less("+id+")'>-</button>"
                                            + "<input type='text' name='qtd_add_carrinho"+id+"' value='0' id='qtd_add_carrinho"+teclado.getId()+"'>" 
                                            + "<button type='button' id='mais' onclick='more("+id+")'>+</button>"                              
                                            + "<input type='hidden' name='teclado_id' value='" + id + "'>"
                                            + "<input type='submit' name='operacao' value='ADICIONAR'>" 
                                        + "</form>"
                                    + "</td>"

                            );

                            out.print(sbRegistro.toString());
                        }

                    }
                }
            %>
        </table>   
    </body>
</html>
