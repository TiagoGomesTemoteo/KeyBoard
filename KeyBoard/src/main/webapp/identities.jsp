<%@page import="com.mycompany.keyboard.model.domain.Cliente"%>
<html>
    <body> 
        <%
             Cliente entities_cliente = (Cliente) request.getAttribute("cliente");
        %>
        <input type="hidden" name="endereco_id" value=
                <%
                    if (entities_cliente != null)
                        out.print("'" + entities_cliente.getEnderecos().get(0).getId() + "'");
                %> 
                >
        <input type="hidden" name="cliente_id" value=
                <%
                    if (entities_cliente != null)
                        out.print("'" + entities_cliente.getId() + "'");
                 %> 
                >
        <input type="hidden" name="telefone_id" value=
                 <%
                    if (entities_cliente != null)
                        out.print("'" + entities_cliente.getTelefone().getId() + "'");
                %> 
                >
        <input type="hidden" name="cartao_id" value=
                <%
                    if (entities_cliente != null && !entities_cliente.getCartoesDeCredito().isEmpty())
                        out.print("'" + entities_cliente.getCartoesDeCredito().get(0).getId() + "'");
                %> 
                >
    </body>
</html>
