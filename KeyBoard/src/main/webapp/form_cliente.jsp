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
        <%
            Cliente cliente = (Cliente) request.getAttribute("cliente");
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
    </body>
</html>
