/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.keyboard.control.viewhelper;

import com.mycompany.keyboard.model.domain.Endereco;
import com.mycompany.keyboard.model.domain.EntidadeDominio;
import com.mycompany.keyboard.util.ParameterParser;
import com.mycompany.keyboard.util.Resultado;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author Tiago
 */
public class EnderecoVH implements IViewHelper {

    @Override
    public EntidadeDominio getEntidade(HttpServletRequest request) {
        Endereco endereco = new Endereco();

        String operacao = request.getParameter("operacao");

        endereco.setCep(ParameterParser.toInt(request.getParameter("cep")));
        endereco.setTipoResidencia(request.getParameter("tipoResidencia"));
        endereco.setTipoLogradouro(request.getParameter("tipoLogradouro"));
        endereco.setNumero(request.getParameter("numero"));
        endereco.setObservacoes(request.getParameter("observacoes"));
        endereco.setLogradouro(request.getParameter("logradouro"));
        endereco.setIdentificacao(request.getParameter("identificacao"));
        endereco.setBairro(request.getParameter("bairro"));
        endereco.setCidade(request.getParameter("cidade"));
        endereco.setEstado(request.getParameter("estado"));
        endereco.setPais(request.getParameter("pais"));
        
        if(operacao.equals("ALTERAR") || operacao.equals("DELETAR")){
            endereco.setId(ParameterParser.toInt(request.getParameter("endereco_id")));
        }
        return endereco;
    }

    @Override
    public void setView(Resultado resultado, HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
