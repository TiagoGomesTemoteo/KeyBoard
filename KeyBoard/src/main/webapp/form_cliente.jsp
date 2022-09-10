<%@page import="com.mycompany.keyboard.util.Resultado"%>
<%@page import="com.mycompany.keyboard.util.Masks"%>
<%@page import="com.mycompany.keyboard.model.domain.Cliente"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <title>Cadastro/Login</title>   
    </head>
    <body>
        <a href="cliente?operacao=CONSULTAR">Consultar clientes</a>
        <%
            Cliente cliente = (Cliente) request.getAttribute("cliente");
            Resultado resultado = (Resultado) request.getAttribute("resultado");
        %>

        <form action="cliente" method="post">
            <%
                if(resultado != null && resultado.getMsg() != null){
                    out.print("<label>" + resultado.getMsg() + "</label>");
                }
            %>
            
            <%@ include file="identities.jsp" %>
            
            <p><input type="text" name="nome" placeholder="Nome*" value=
                   <%
                       if (cliente != null)
                           out.print("'" + cliente.getNome() + "'");
                   %> 
                   >
            <input type="text" name="dtNascimento" placeholder="Data de nascimento*" value=
                                       <%
                                           if (cliente != null)
                                               out.print("'" + Masks.brazilianDate(cliente.getDtNascimento()) + "'");
                                       %> 
                                       >
            <p><input type="text" name="telefone" placeholder="Telefone*" value=
                   <%
                       if (cliente != null)
                           out.print("'" + cliente.getTelefone().getDdd() + cliente.getTelefone().getNumero() + "'");
                   %> 
                   >
            Gênero: <select name="genero">
                <option value="">Selecione...       </option>
                <option value="masculino"
                        <%
                            if (cliente != null && cliente.getGenero().toString().equals("MASCULINO"))
                                out.print(" selected");
                        %>        
                        >Masculino </option>
                <option value="feminino"
                        <%
                            if (cliente != null && cliente.getGenero().toString().equals("FEMININO"))
                                out.print(" selected");
                        %> 
                        >Feminino   </option>
            </select>
            <input type="text" name="cpf" placeholder="CPF*" value=
                   <%
                       if (cliente != null)
                           out.print("'" + cliente.getCpf() + "'");
                   %> 
                   >
            <p><input type="email" name="email" placeholder="E-mail*" value=
                   <%
                       if (cliente != null)
                           out.print("'" + cliente.getEmail() + "'");
                   %> 
                   >
            <input type="password" name="senha" placeholder="Crie sua senha*">
            <input type="password" name="confirmar_senha" placeholder="Confirme a senha*">
            
            <label ></label>
            
            <p>Endereço: <%@ include file="form_endereco.jsp" %>
            <p>Cartão: <%@ include file="form_cartao.jsp" %>
            
            <p><input type="submit" name="operacao" value=
                   <%
                       if (cliente != null) {
                           out.print("ALTERAR");
                       } else {
                           out.print("SALVAR");
                       }
                   %>
                   > 
        </form>        
    </body>
</html>
