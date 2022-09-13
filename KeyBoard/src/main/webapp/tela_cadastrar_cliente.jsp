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
        
        <%@ include file="form_login.jsp" %>

        <p>QUERO ME CADASTRAR
        <form action="cliente" method="post">
            
            <%@ include file="form_cliente.jsp" %>
            
            <p> <input type="password" name="senha" placeholder="Crie sua senha*">
            
            <p> <input type="password" name="confirmar_senha" placeholder="Confirme a senha*">
            
            <p> Endereço: <%@ include file="form_endereco.jsp" %>
           
            <p> <input type="submit" name="operacao" value="SALVAR">
                    
        </form>        
    </body>
</html>
