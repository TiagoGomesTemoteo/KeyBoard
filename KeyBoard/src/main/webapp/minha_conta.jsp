<!DOCTYPE html>
<html>
    <head>
        <title>Start Page</title>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link href="css/css_padroes.css" rel="stylesheet">
        <link href="css/css_menu.css" rel="stylesheet">            
    </head>
    <body> 
        <%@ include file="links_menu.jsp" %>  
        <%
            
            Cliente cliente_logado = null;
            
            if(session.getAttribute("usuario") == null) {
                response.sendRedirect("tela_cadastrar_cliente.jsp");
            
            } else {
                cliente_logado = (Cliente) session.getAttribute("usuario");
            }
            
            
        %>
    </body>
</html>
