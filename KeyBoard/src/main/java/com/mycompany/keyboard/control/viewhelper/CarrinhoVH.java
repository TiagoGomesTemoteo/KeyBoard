/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.keyboard.control.viewhelper;

import com.mycompany.keyboard.model.domain.Carrinho;
import com.mycompany.keyboard.model.domain.EntidadeDominio;
import com.mycompany.keyboard.model.domain.Item;
import com.mycompany.keyboard.util.ClienteInSession;
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
public class CarrinhoVH implements IViewHelper{

    @Override
    public EntidadeDominio getEntidade(HttpServletRequest request) {
        
        Carrinho carrinho = new Carrinho();
        Item item = new Item();
        
        String operacao = request.getParameter("operacao");
        
        if (operacao.equals("SALVAR") || operacao.equals("ADICIONAR")) {
            item.setNewInTheCar(true);
            item.getTeclado().setId(ParameterParser.toInt(request.getParameter("teclado_id")));
            item.setQuantidade(ParameterParser.toInt(request.getParameter("qtd_add_carrinho"+item.getTeclado().getId())));
            carrinho.getItens().add(item);
            carrinho.getCliente().setId(1);
            
        } else if (operacao.equals("CONSULTAR")) {
            carrinho.getCliente().setId(1);
        }

        return carrinho;
        
    }

    @Override
    public void setView(Resultado resultado, HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        
        String operacao = request.getParameter("operacao");
        
        ClienteInSession.Atualizar(request);
        ClienteInSession.getAllItensCar(request);
        
        if (operacao.equals("SALVAR")) {
            if(resultado.getMsg() != null){
                request.setAttribute("resultado", resultado);
                request.getRequestDispatcher("lista_teclado.jsp").forward(request, response); 
            }
            
            response.sendRedirect("/KeyBoard/cliente?operacao=CONSULTAR");
        }
 
        else if(resultado.getMsg() == null && operacao.equals("CONSULTAR") || operacao.equals("ADICIONAR")) {
            request.getSession().setAttribute("resultado", resultado);
            request.getRequestDispatcher("tela_carrinho.jsp").forward(request, response);
    
        }
    }       
}

