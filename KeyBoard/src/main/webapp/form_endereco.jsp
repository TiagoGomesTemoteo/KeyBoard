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
        <p><input class="campo_cep" type="number" name="cep" placeholder="CEP*" value=
                   <%
                       if (endereco != null)
                           out.print("'" + endereco.getCep() + "'");
                   %>
                   >
            <select class="campo_tipo_residencia" name="tipoResidencia">
                <option value="">Tipo resid?ncia           </option>
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
                        >Escrit?rio   </option>
            </select>
            <input class="campo_numero" type="text" name="numero" placeholder="N?mero*" value=
                   <%
                       if (endereco != null)
                           out.print("'" + endereco.getNumero() + "'");
                   %>
                   >
            <p><input class="campo_observacao" type="text" name="observacoes" placeholder="Observa??es" value=
                   <%
                       if (endereco != null)
                           out.print("'" + endereco.getObservacoes() + "'");
                   %>
                   >
            <p><select name="tipoLogradouro">
                <option value="">Tipo logradouro</option>
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
            <p><input type="text" name="pais" placeholder="Pa?s*" value=
                   <%
                       if (endereco != null)
                           out.print("'" + endereco.getPais() + "'");
                   %>
                   >
            <p><input type="text" name="identificacao" placeholder="Identifica??o*" value=
                   <%
                       if (endereco != null)
                           out.print("'" + endereco.getIdentificacao() + "'");
                   %>
                   >
    </body>
</html>
