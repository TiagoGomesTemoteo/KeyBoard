<%-- 
    Document   : lista_cliente
    Created on : 4 de set. de 2022, 19:32:52
    Author     : Tiago
--%>

<%@page import="com.mycompany.keyboard.model.domain.Cliente"%>
<%@page import="java.util.List"%>
<%@page import="com.mycompany.keyboard.model.domain.EntidadeDominio"%>
<%@page import="com.mycompany.keyboard.util.Resultado"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
    </head>
    <body>
        <%
            Resultado resultado = (Resultado) session.getAttribute("resultado");
        %>
        
            <a href="form_cliente.jsp"> Cadastrar</a>
            <table border="1px">
                <tr>
                    <td>
                        ID:
                    </td>
                    <td>
                        Data de cadastro:
                    </td>
                    <td>
                        Nome:
                    </td>
                    <td>
                        Gênero:
                    </td>
                    <td>
                        E-mail:
                    </td>
                    <td>
                        Rank:
                    </td>
                    <td>
                        Data de nascimento:
                    </td>
                    <td>
                        CPF:
                    </td>
                    <td>
                        Ativo:
                    </td>
                    <td>
                        Telefone:
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
                        StringBuilder sbRegistro = new StringBuilder();
                        StringBuilder sbLink = new StringBuilder();
                        StringBuilder sbAtivar = new StringBuilder();
                        String clienteAtivo;

                        if (entidades != null) {
                            for (int i = 0; i < entidades.size(); i++) {
                                Cliente cliente = (Cliente) entidades.get(i);
                                sbRegistro.setLength(0);
                                sbLink.setLength(0);
                                sbAtivar.setLength(0);

                                sbLink.append("<a href=cliente?");
                                sbLink.append("cliente_id=");
                                sbLink.append(cliente.getId());
                                sbLink.append("&");
                                sbLink.append("operacao=");
                                sbLink.append("VISUALIZAR");
                                
                                sbAtivar.append("<a href=cliente?");
                                sbAtivar.append("cliente_id=");
                                sbAtivar.append(cliente.getId());
                                sbAtivar.append("&");
                                sbAtivar.append("operacao=");
                                sbAtivar.append("DELETAR");
                                sbAtivar.append("&");
                                sbAtivar.append("ativo=");
                                sbAtivar.append(cliente.isAtivo());

                                if(cliente.isAtivo() == true){
                                    clienteAtivo = "Inativar";
                                }else{
                                    clienteAtivo = "Ativar";
                                }
                                
                                sbRegistro.append(
                                        "<tr>"
                                        + "<td>"
                                        + cliente.getId()
                                        + "</td>"
                                        + "<td>"
                                        + cliente.getDt_cadastro()
                                        + "</td>"
                                        + "<td>"
                                        + cliente.getNome()
                                        + "</td>"
                                        + "<td>"
                                        + cliente.getGenero().toString()
                                        + "</td>"
                                        + "<td>"
                                        + cliente.getEmail()
                                        + "</td>"
                                        + "<td>"
                                        + cliente.getRank()
                                        + "</td>"
                                        + "<td>"
                                        + cliente.getDtNascimento()
                                        + "</td>"
                                        + "<td>"
                                        + cliente.getCpf()
                                        + "</td>"
                                        + "<td>"
                                        + cliente.isAtivo()
                                        + "</td>"
                                        + "<td>"
                                        + "(" + cliente.getTelefone().getDdd() + ")"
                                        + cliente.getTelefone().getNumero()
                                        + "</td>"
                                        + "<td>"
                                        + sbLink.toString() + ">" + "Visualizar" + "</a>"
                                        + "</td>"
                                        + "<td>"
                                        + sbAtivar.toString() + ">" + clienteAtivo + "</a>"
                                        + "</td>"
                                        + "</tr>"
                                );

                                out.print(sbRegistro.toString());
                            }

                        }
                    }
                %>
            </table>
        </form>    
    </body>
</html>
