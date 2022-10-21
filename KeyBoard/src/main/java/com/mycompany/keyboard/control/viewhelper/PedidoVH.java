/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.keyboard.control.viewhelper;

import com.mycompany.keyboard.model.domain.Carrinho;
import com.mycompany.keyboard.model.domain.CartaoDeCredito;
import com.mycompany.keyboard.model.domain.Cliente;
import com.mycompany.keyboard.model.domain.CupomDeTroca;
import com.mycompany.keyboard.model.domain.EntidadeDominio;
import com.mycompany.keyboard.model.domain.Pagamento;
import com.mycompany.keyboard.model.domain.Pedido;
import com.mycompany.keyboard.model.domain.enums.Estatus;
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
public class PedidoVH implements IViewHelper {

    @Override
    public EntidadeDominio getEntidade(HttpServletRequest request) {

        Pedido pedido = new Pedido();

        String operacao = request.getParameter("operacao");

        if (operacao.equals("FINALIZAR")) {

            pedido.setCliente((Cliente) request.getSession().getAttribute("cliente_info"));
            pedido.setItens(((Carrinho) request.getSession().getAttribute("cliente_carrinho")).getItens());
            pedido.getEndereco().setId(ParameterParser.toInt(request.getParameter("endereco_entrega")));
            pedido.setValor_total(ParameterParser.toDouble(request.getParameter("valor_total")));
            pedido.setEstatus(Estatus.EM_ANALISE);
            request.getSession().setAttribute("pedido", pedido);

        }
        
        if (operacao.equals("CONSULTAR")) {
            if (request.getParameter("cliente_id") != null ) 
                pedido.getCliente().setId(ParameterParser.toInt(request.getParameter("cliente_id")));
            else
                pedido.getCliente().setId(0);
        }
        
        if (operacao.equals("PAGAR")) {
            pedido = (Pedido) request.getSession().getAttribute("pedido");

            if(request.getParameterValues("cartao").length > 1) {
                getCartoesUsados(request, pedido);
            }

            if(request.getParameterValues("cupom").length > 0) {
                getCuponsUsados(request, pedido);
            }            
        }
        
        if (operacao.equals("ALTERAR")) {
            pedido.setId(ParameterParser.toInt(request.getParameter("pedido_id")));
            pedido.setEstatus(Estatus.pegaEstatusPorDescricao(request.getParameter("estatus")));
            System.out.println(pedido.getId());
        }

        return pedido;

    }

    @Override
    public void setView(Resultado resultado, HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        String operacao = request.getParameter("operacao");

        ClienteInSession.Atualizar(request);
        ClienteInSession.getAllItensCar(request);

        if (operacao.equals("FINALIZAR")) {
            request.getRequestDispatcher("tela_forma_pagamento.jsp").forward(request, response);
               
        } else if (operacao.equals("PAGAR")) {
            request.getSession().setAttribute("resultado", resultado);
            request.getRequestDispatcher("lista_pedidos_cliente.jsp").forward(request, response);
    
        } else if (operacao.equals("CONSULTAR")){
            request.getSession().setAttribute("resultado", resultado);
            
            if (request.getParameter("cliente_id") != null ) { 
                request.getRequestDispatcher("lista_pedidos_cliente.jsp").forward(request, response);

            } else {
                request.getRequestDispatcher("lista_vendas.jsp").forward(request, response);
            }   
            
        } else if (operacao.equals("ALTERAR")){
            request.getSession().setAttribute("resultado", ClienteInSession.getAllPedidos());
            request.getRequestDispatcher("lista_vendas.jsp").forward(request, response);
        }
        
        
    }

    public void getCartoesUsados(HttpServletRequest request, Pedido pedido) {

        String [] valoresAndCartoes = request.getParameterValues("cartao");
        
        double valor;
        CartaoDeCredito cartao;
        
        for (int i = 0; i<valoresAndCartoes.length; i+=2){
            valor = ParameterParser.toDouble(valoresAndCartoes[i]);
            cartao = getCartao(request, ParameterParser.toInt(valoresAndCartoes[i+1]));
            
            pedido.getPagamento().add(new Pagamento(valor, cartao));    
        }
    }   
    
    public void getCuponsUsados(HttpServletRequest request, Pedido pedido) {

        String [] valoresAndCartoes = request.getParameterValues("cupom");
        CupomDeTroca cupom;
        
        for (int j = 0; j<valoresAndCartoes.length; j++) {
            System.out.println(valoresAndCartoes[j]);
        }
        
        for (int i = 0; i<valoresAndCartoes.length; i++) {
            cupom = getCupom(request, ParameterParser.toInt(valoresAndCartoes[i]));
            pedido.getPagamento().add(new Pagamento(cupom.getValor(), cupom));
        }
    }
      
    public CartaoDeCredito getCartao (HttpServletRequest request, int idCartao) {
        Cliente cliente = (Cliente) request.getSession().getAttribute("cliente_info");

        for (CartaoDeCredito cartao : cliente.getCartoesDeCredito()) {
            if (cartao.getId() == idCartao) {
                return cartao;
            }
        }
        
        return null;
    }
     
    public CupomDeTroca getCupom(HttpServletRequest request, int idCupomUsado) {

        Cliente cliente = (Cliente) request.getSession().getAttribute("cliente_info");

        for (CupomDeTroca cupom : cliente.getCuponsDeTroca()) {
            if (cupom.getId() == idCupomUsado) {
                return cupom;
            }
        }

        return null;
    }
}
