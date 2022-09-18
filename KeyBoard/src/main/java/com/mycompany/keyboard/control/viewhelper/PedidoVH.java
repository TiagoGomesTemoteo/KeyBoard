/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.keyboard.control.viewhelper;

import com.mycompany.keyboard.model.domain.Carrinho;
import com.mycompany.keyboard.model.domain.Cliente;
import com.mycompany.keyboard.model.domain.EntidadeDominio;
import com.mycompany.keyboard.model.domain.Item;
import com.mycompany.keyboard.model.domain.Pedido;
import com.mycompany.keyboard.util.ClienteInSession;
import com.mycompany.keyboard.util.ParameterParser;
import com.mycompany.keyboard.util.Resultado;
import java.io.IOException;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


/**
 *
 * @author Tiago
 */
public class PedidoVH implements IViewHelper{

    @Override
    public EntidadeDominio getEntidade(HttpServletRequest request) {
        
        Pedido pedido = new Pedido();
        
        String operacao = request.getParameter("operacao");
        
        if (operacao.equals("FINALIZAR")) {
            
            pedido.setCliente((Cliente)request.getSession().getAttribute("cliente_info"));
            pedido.setItens(((Carrinho)request.getSession().getAttribute("cliente_carrinho")).getItens());
            pedido.getEndereco().setId(ParameterParser.toInt(request.getParameter("endereco_entrega")));
            request.setAttribute("pedido", pedido);
        } 

        return pedido;
        
    }

    @Override
    public void setView(Resultado resultado, HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        
        String operacao = request.getParameter("operacao");
        
        ClienteInSession.Atualizar(request);
        ClienteInSession.getAllItensCar(request);
        
        if(operacao.equals("FINALIZAR")){
                request.getRequestDispatcher("tela_forma_pagamento.jsp").forward(request, response);    
        }
//            
//        }else if(resultado.getMsg() == null && operacao.equals("VISUALIZAR")) {
//            request.setAttribute("cliente", resultado.getEntidades().get(0));
//            request.getRequestDispatcher("tela_editar_cliente.jsp").forward(request, response);
//             
        else if(resultado.getMsg() == null && operacao.equals("CONSULTAR")) {
            request.getSession().setAttribute("resultado",resultado);
            request.getRequestDispatcher("tela_carrinho.jsp").forward(request, response);
//            
//        }else if(resultado.getMsg() == null && operacao.equals("ALTERAR")) {
//            response.sendRedirect("/KeyBoard/cliente?operacao=CONSULTAR");
//            
//        }else if(resultado.getMsg() == null && operacao.equals("DELETAR")) {
//            response.sendRedirect("/KeyBoard/cliente?operacao=CONSULTAR");
//        }
    
        }
    }       
}

