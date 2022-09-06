<%@page import="com.mycompany.keyboard.model.domain.Cliente"%>
<!DOCTYPE html>
<html>
    <head>
        <title>Cadastro/Login</title>
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    </head>
    <body>
        <a href="cliente?operacao=CONSULTAR">Consultar clientes</a>
        <%
            Cliente cliente = (Cliente) request.getAttribute("cliente");
        %>
        <form action="cliente" method="post">
            <input type="hidden" name="endereco_id" value=
                   <%
                       if (cliente != null)
                           out.print("'" + cliente.getEnderecos().get(0).getId() + "'");
                   %> 
                   >
            <input type="hidden" name="cliente_id" value=
                   <%
                       if (cliente != null)
                           out.print("'" + cliente.getId() + "'");
                   %> 
                   >
            <input type="hidden" name="telefone_id" value=
                   <%
                       if (cliente != null)
                           out.print("'" + cliente.getTelefone().getId() + "'");
                   %> 
                   >
            
            <p><input type="text" name="nome" placeholder="Nome*" value=
                   <%
                       if (cliente != null)
                           out.print("'" + cliente.getNome() + "'");
                   %> 
                   >
            Data de nascimento: <input type="date" name="dtNascimento" value=
                                       <%
                                           if (cliente != null)
                                               out.print("'" + cliente.getDtNascimento() + "'");
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

            <p><input type="number" name="cep" placeholder="CEP*" value=
                   <%
                       if (cliente != null)
                           out.print("'" + cliente.getEnderecos().get(0).getCep() + "'");
                   %>
                   >
            Tipo de residência: <select name="tipoResidencia">
                <option value="">Selecione...           </option>
                <option value="casa" 
                        <%
                            if (cliente != null && cliente.getEnderecos().get(0).getTipoResidencia().equals("casa"))
                                out.print(" selected");
                        %>
                        >Casa               </option>
                <option value="escritorio"
                        <%
                            if (cliente != null && cliente.getEnderecos().get(0).getTipoResidencia().equals("escritorio"))
                                out.print(" selected");
                        %>        
                        >Escritório   </option>
            </select>
            <input type="text" name="numero" placeholder="Número*" value=
                   <%
                       if (cliente != null)
                           out.print("'" + cliente.getEnderecos().get(0).getNumero() + "'");
                   %>
                   >
            <p><input type="text" name="observacoes" placeholder="Observações" value=
                   <%
                       if (cliente != null)
                           out.print("'" + cliente.getEnderecos().get(0).getObservacoes() + "'");
                   %>
                   >
            Tipo de Logradouro: <select name="tipoLogradouro">
                <option value="">Selecione...           </option>
                <option value="rua"
                        <%
                            if (cliente != null && cliente.getEnderecos().get(0).getTipoLogradouro().equals("rua"))
                                out.print(" selected");
                        %>
                        >Rua                 </option>
                <option value="avenida"
                        <%
                            if (cliente != null && cliente.getEnderecos().get(0).getTipoLogradouro().equals("avenida"))
                                out.print(" selected");
                        %>        
                        >Avenida         </option>
            </select>
            <input type="text" name="logradouro" placeholder="Logradouro*" value=
                   <%
                       if (cliente != null)
                           out.print("'" + cliente.getEnderecos().get(0).getLogradouro() + "'");
                   %>
                   >
            <input type="text" name="identificacao" placeholder="Identificação*" value=
                   <%
                       if (cliente != null)
                           out.print("'" + cliente.getEnderecos().get(0).getIdentificacao() + "'");
                   %>
                   >

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
