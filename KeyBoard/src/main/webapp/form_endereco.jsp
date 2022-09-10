<%@page import="com.mycompany.keyboard.model.domain.Endereco"%>
<%@page import="com.mycompany.keyboard.model.domain.Cliente"%>
<html>
    <body>
        
        <%
            Endereco endereco = null;
            
            if (request.getAttribute("cliente") != null){
                endereco = ((Cliente) request.getAttribute("cliente")).getEnderecos().get(0);
            }
        %>
        <p><input type="number" name="cep" placeholder="CEP*" value=
                   <%
                       if (endereco != null)
                           out.print("'" + endereco.getCep() + "'");
                   %>
                   >
            Tipo de residência: <select name="tipoResidencia">
                <option value="">Selecione...           </option>
                <option value="casa" 
                        <%
                            if (endereco != null && endereco.getTipoResidencia().equals("casa"))
                                out.print(" selected");
                        %>
                        >Casa               </option>
                <option value="escritorio"
                        <%
                            if (endereco != null && endereco.getTipoResidencia().equals("escritorio"))
                                out.print(" selected");
                        %>        
                        >Escritório   </option>
            </select>
            <input type="text" name="numero" placeholder="Número*" value=
                   <%
                       if (endereco != null)
                           out.print("'" + endereco.getNumero() + "'");
                   %>
                   >
            <p><input type="text" name="observacoes" placeholder="Observações" value=
                   <%
                       if (endereco != null)
                           out.print("'" + endereco.getObservacoes() + "'");
                   %>
                   >
            Tipo de Logradouro: <select name="tipoLogradouro">
                <option value="">Selecione...           </option>
                <option value="rua"
                        <%
                            if (endereco != null && endereco.getTipoLogradouro().equals("rua"))
                                out.print(" selected");
                        %>
                        >Rua                 </option>
                <option value="avenida"
                        <%
                            if (endereco != null && endereco.getTipoLogradouro().equals("avenida"))
                                out.print(" selected");
                        %>        
                        >Avenida         </option>
            </select>
            <input type="text" name="logradouro" placeholder="Logradouro*" value=
                   <%
                       if (endereco != null)
                           out.print("'" + endereco.getLogradouro() + "'");
                   %>
                   >
            
            <p><input type="text" name="bairro" placeholder="Bairro*" value=
                   <%
                       if (endereco != null)
                           out.print("'" + endereco.getBairro() + "'");
                   %>
                   >
            <input type="text" name="cidade" placeholder="Cidade*" value=
                   <%
                       if (endereco != null)
                           out.print("'" + endereco.getCidade() + "'");
                   %>
                   >
            <input type="text" name="estado" placeholder="Estado*" value=
                   <%
                       if (endereco != null)
                           out.print("'" + endereco.getEstado() + "'");
                   %>
                   >
            <p><input type="text" name="pais" placeholder="País*" value=
                   <%
                       if (endereco != null)
                           out.print("'" + endereco.getPais() + "'");
                   %>
                   >
            <p><input type="text" name="identificacao" placeholder="Identificação*" value=
                   <%
                       if (endereco != null)
                           out.print("'" + endereco.getIdentificacao() + "'");
                   %>
                   >
    </body>
</html>
