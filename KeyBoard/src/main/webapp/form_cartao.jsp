<%@page import="com.mycompany.keyboard.model.domain.enums.BandeiraCartao"%>
<%@page import="com.mycompany.keyboard.model.domain.CartaoDeCredito"%>
<%@page import="com.mycompany.keyboard.model.domain.Cliente"%>
<html>
    <body>

        <%
            CartaoDeCredito cartao = null;

            if (request.getAttribute("cliente") != null && 
                !((Cliente) request.getAttribute("cliente")).getCartoesDeCredito().isEmpty()) {
                
                cartao = ((Cliente) request.getAttribute("cliente")).getCartoesDeCredito().get(0);
            }
        %>
        <p><input type="text" name="cartao_numero" placeholder="Número*" value=
                  <%
                      if (cartao != null) out.print("'" + cartao.getNumero() + "'");
                  %>
                  >
        <p>Bandeira: <select name="bandeira">
                <option value="">Selecione...</option>
                <%
                    for (BandeiraCartao bandeira : BandeiraCartao.values()) {
                        out.print("<option value='" + bandeira.toString() + "'");
                        if (cartao != null && cartao.getBandeira().toString().equals(bandeira.toString())){out.print(" selected");}
                        out.print(">"+bandeira.toString()+"</option>");
                    }
                %>
            </select>
        <p><input type="text" name="nomeImpressoNoCartao" placeholder="Nome impresso no cartão*" value=
                  <%
                      if (cartao != null) out.print("'" + cartao.getNomeImpressoNoCartao() + "'");
                  %>
                  >
        <p><input type="text" name="codSeguranca" placeholder="Código de Segurança" value=
                  <%
                      if (cartao != null) out.print("'" + cartao.getCodSeguranca() + "'");
                  %>
                  >
<!--        <p><input type="checkbox" name="preferencial"
                  
                    //if(cartao != null && cartao.isPreferencial()) out.print("checked");
                  
                  > Preferencial   -->
    </body>
</html>
