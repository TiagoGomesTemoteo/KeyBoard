<%@page import="com.mycompany.keyboard.util.Resultado"%>
<%@page import="com.mycompany.keyboard.util.Masks"%>
<%@page import="com.mycompany.keyboard.model.domain.Cliente"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <title>Cadastro/Login</title>  
        <link href="css/css_tela_cadastrar_cliente.css" rel="stylesheet">
        <link href="css/css_form_quero_cadastrar.css" rel="stylesheet">
        <link href="css/css_form_endereco.css" rel="stylesheet">
        <link href="css/css_form_cliente.css" rel="stylesheet">
    </head>
 
    <body>
        <%@ include file="links_menu.jsp" %>
        <p>
        <div class="title_identificacao">
            <img class="icon_title_identificacao" src="icons/green-user.png"/>
            <span class="title_pages"> IDENTIFICAÇÃO</span>
        </div>    
        
        <div class="form_login">
            <%@ include file="form_login.jsp" %>
        </div>
        
        <div class="form_cadastrar">
            <p class="form_title_page_login_cadastro">QUERO ME CADASTRAR
            <form action="cliente" method="post">

                <%@ include file="form_cliente.jsp" %>

                <p> <input class="senha" type="password" name="senha" placeholder="Crie sua senha*">

                <p> <input class="senha" type="password" name="confirmar_senha" placeholder="Confirme a senha*">

                <p> Endereço: <%@ include file="form_endereco.jsp" %>

                <p> <center><input class="button_white btn_form_cadastrar" type="submit" name="operacao" value="SALVAR"></center>

            </form> 
        </div>
              
        <%
            Resultado messageError = (Resultado)request.getAttribute("messageError");
            
            if(messageError != null && messageError.getMsg() != null) {
                out.println("<script>messageError('"+messageError.getMsg()+"')</script>");
            }
            
        %>
        
    </body>
</html>
