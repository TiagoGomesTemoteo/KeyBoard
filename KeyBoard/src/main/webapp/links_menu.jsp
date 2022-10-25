<%@page import="com.mycompany.keyboard.model.domain.Cliente"%>
<head>
    <link href="css/css_padroes.css" rel="stylesheet">
    <link href="css/css_menu.css" rel="stylesheet">    
</head>
<div class="barra_grande_menu">
    <%
        String carrinho_menu = "";
        String minhaContaBarra = "";
        String loginLogout = "";
        Cliente cliente_menu = null;
        
        if(session.getAttribute("usuario") != null) {   
            cliente_menu = (Cliente) session.getAttribute("usuario");
            carrinho_menu = "<a href='carrinho?operacao=CONSULTAR'><img class='icon_carrinho_compra_preto' src='icons/carrinho-de-compras-preto.png'/></a>";
            minhaContaBarra = "<a href='pedido?operacao=CONSULTAR&cliente_id="+((Cliente) session.getAttribute("usuario")).getId()+"' class='link_login_cadastro'>MINHA CONTA</a>";
            loginLogout = "<span class='text_faca_login'><b>Olá, "+cliente_menu.getNome()+"</b><br>"+minhaContaBarra+" | <a href='usuario?operacao=SAIR' class='link_login_cadastro'>SAIR</a></span>";
        
        } else {
            loginLogout = "<span class='text_faca_login'> Faça <b><a href='tela_cadastrar_cliente.jsp' class='link_login_cadastro'>Login</a></b> ou<br> crie seu <b><a href='tela_cadastrar_cliente.jsp' class='link_login_cadastro'>Cadastro</a></b></span>";
            carrinho_menu = "<a href='tela_cadastrar_cliente.jsp'><img class='icon_carrinho_compra_preto' src='icons/carrinho-de-compras-preto.png'/></a>";
        }
    %>
    <img class="logo" src="img/logo.png"/>
    <input class="barra_pesquisa" type="text" placeholder="  Busque aqui">
    <img class="icon_login_cadastro" src="icons/profile-user.png"/>        
    <% 
        out.print(loginLogout);
        out.print(carrinho_menu);     
    %>
       
    <img class="icon_notification" src="icons/notificacao.png"/>
</div>
<div class="opcoes_menu">
    <%
        String admListaClientes = "";
        String admListaTeclado = "";
        String admCadastroTeclado = "";
        String estoque = "";
        String vendas = "";
        String minhaConta = "";
        
        if(session.getAttribute("usuario") != null) {
        
            minhaConta = "<a href='pedido?operacao=CONSULTAR&cliente_id="+((Cliente) session.getAttribute("usuario")).getId()+"'>Minha Conta</a>";
            if (((Cliente) session.getAttribute("usuario")).getNivel_acesso() == 2) {
                admListaClientes = "<a href='cliente?operacao=CONSULTAR'>Lista de Cliente</a>";
                admListaTeclado = "Lista de Teclado";
                admCadastroTeclado = "Cadastro de Teclado";
                estoque = "Estoque";
                vendas = "<a href='pedido?operacao=CONSULTAR'>Vendas</a>";
            }
        }
            
        
    %>
    <span class="text_spans_menu span_menu_home"><a href="index.jsp">Home</a></span>
    <span class="text_spans_menu span_menu_teclados"><a href="teclado?operacao=CONSULTAR">Teclados</a></span>
    <span class="text_spans_menu span_menu_minha_conta"><% out.print(minhaConta);%></span>
    <span class="text_spans_menu span_menu_lista_cliente"><% out.print(admListaClientes);%></span>
    <span class="text_spans_menu span_menu_lista_teclado"><% out.print(admListaTeclado);%></span>
    <span class="text_spans_menu span_menu_cadastro_teclado"><% out.print(admCadastroTeclado);%></span>
    <span class="text_spans_menu span_menu_estoque"><% out.print(estoque);%></span>
    <span class="text_spans_menu span_menu_vendas"><% out.print(vendas);%></span>
</div>    

<%@ include file="function_utils.jsp" %>
