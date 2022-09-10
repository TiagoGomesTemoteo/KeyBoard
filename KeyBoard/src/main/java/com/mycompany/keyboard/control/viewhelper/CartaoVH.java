/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.keyboard.control.viewhelper;

import com.mycompany.keyboard.model.domain.CartaoDeCredito;
import com.mycompany.keyboard.model.domain.EntidadeDominio;
import com.mycompany.keyboard.model.domain.enums.BandeiraCartao;
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
public class CartaoVH implements IViewHelper {

    @Override
    public EntidadeDominio getEntidade(HttpServletRequest request) {
        CartaoDeCredito cartao = new CartaoDeCredito();

        String operacao = request.getParameter("operacao");

        cartao.setNumero(ParameterParser.toInt(request.getParameter("cartao_numero")));
        cartao.setNomeImpressoNoCartao(request.getParameter("nomeImpressoNoCartao"));
        
        if (!request.getParameter("bandeira").equals("")){
            cartao.setBandeira(BandeiraCartao.valueOf(request.getParameter("bandeira")));
        }
        
        cartao.setCodSeguranca(ParameterParser.toInt(request.getParameter("codSeguranca")));
        cartao.setPreferencial(ParameterParser.toBoolean(request.getParameter("preferencial")));
        
        if(operacao.equals("ALTERAR") || operacao.equals("DELETAR")){
            cartao.setId(ParameterParser.toInt(request.getParameter("cartao_id")));
        }
        
        return cartao;
    }

    @Override
    public void setView(Resultado resultado, HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
