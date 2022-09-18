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
        <%@ include file="links_menu.jsp" %>
        
        <p>ALTERAR SENHA
        <%@ include file="form_alterar_senha.jsp" %>
        
        DADOS BÁSICOS
        <form action="cliente" method="post"> 
            <%@ include file="form_cliente.jsp" %>
            <p> <input type="submit" name="operacao" value="ALTERAR">
        </form>
            
        CARTÕES
        <form action="cartao" method="post">
            <%@ include file="form_cartao.jsp" %>
            <p> <input type="submit" name="operacao" value="SALVAR">
        </form>
            
        ENDEREÇOS
        <form action="endereco" method="post">
            <%@ include file="form_endereco.jsp" %>
            <p> <input type="submit" name="operacao" value="SALVAR">
        </form>
    </body>
</html>
