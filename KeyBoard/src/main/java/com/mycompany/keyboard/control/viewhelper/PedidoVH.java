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
import com.mycompany.keyboard.model.domain.CupomPromocional;
import com.mycompany.keyboard.model.domain.EntidadeDominio;
import com.mycompany.keyboard.model.domain.Pagamento;
import com.mycompany.keyboard.model.domain.Pedido;
import com.mycompany.keyboard.model.domain.enums.Estatus;
import com.mycompany.keyboard.model.strategy.FunctionsUtilsPagamento;
import com.mycompany.keyboard.model.strategy.Messages;
import com.mycompany.keyboard.util.ClienteInSession;
import com.mycompany.keyboard.util.ParameterParser;
import com.mycompany.keyboard.util.Resultado;
import java.io.IOException;
import java.util.ArrayList;
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
            pedido.setValor_total(FunctionsUtilsPagamento.calcularValorTotal((pedido.getItens())));
            pedido.setEstatus(Estatus.APROVADA);
            request.getSession().setAttribute("pedido", pedido);

        }
        
        if (operacao.equals("CONSULTAR")) {
            if (request.getParameter("cliente_id") != null ) {
                pedido.getCliente().setId(ParameterParser.toInt(request.getParameter("cliente_id")));
            } else {
                pedido.getCliente().setId(0);
            }    
        }
        
        if (operacao.equals("PAGAR")) {
            pedido = (Pedido) request.getSession().getAttribute("pedido");
            
            pedido.setPagamento(new ArrayList<Pagamento>());

            if (request.getParameterValues("cartao") != null) {
                if (request.getParameterValues("cartao").length > 0) getCartoesUsados(request, pedido);
            }
            
            if (request.getParameterValues("cupom") != null) {
                if(request.getParameterValues("cupom").length > 0) getCuponsUsados(request, pedido);
            }
            
            setCupomPromocional(request, pedido);
                                
        }
        
        if (operacao.equals("ALTERAR")) {
            pedido.setId(ParameterParser.toInt(request.getParameter("pedido_id")));
            pedido.setEstatus(Estatus.pegaEstatusPorDescricao(request.getParameter("estatus")));              
        }
        
        if (operacao.equals("APLICAR_CUPOM")) {            
            pedido = (Pedido) request.getSession().getAttribute("pedido");                                   
            int id_cupom_promocional = ParameterParser.toInt(request.getParameter("cupom_promocional")); 
            CupomPromocional cupom_promocional= getCupomPromocional(request, id_cupom_promocional);
            
            if (cupom_promocional != null) {
                pedido.setValor_total_com_desconto(FunctionsUtilsPagamento.calcularValorTotalComDesconto(pedido.getValor_total(),cupom_promocional));       
                request.setAttribute("cupom_promocional_usado", id_cupom_promocional);
            } else {
                Resultado resultado = new Resultado();
                resultado.setMsg(Messages.cupomPromocionalInvalido());
                request.setAttribute("messageError", resultado);
            }
            
            request.getSession().setAttribute("pedido", pedido);
            
        }
        
        if (operacao.equals("REMOVER_CUPOM")) {            
            pedido = (Pedido) request.getSession().getAttribute("pedido");                                  
            pedido.setValor_total_com_desconto(0);       
            request.getSession().setAttribute("pedido", pedido);
            request.setAttribute("cupom_promocional_usado", null);
        }
        
        return pedido;

    }

    @Override
    public void setView(Resultado resultado, HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        String operacao = request.getParameter("operacao");

        ClienteInSession.Atualizar(request);
        ClienteInSession.getAllItensCar(request);

        if (operacao.equals("FINALIZAR") || operacao.equals("APLICAR_CUPOM") || operacao.equals("REMOVER_CUPOM")) {
            
            if(resultado.getMsg() != null){                                  
                request.setAttribute("messageError", resultado);
                request.getRequestDispatcher("tela_carrinho.jsp").forward(request, response);
            
            } 
            
            request.getRequestDispatcher("tela_forma_pagamento.jsp").forward(request, response);
               
        } else if (operacao.equals("PAGAR")) {
            
            if(resultado.getMsg() != null){                  
                if(resultado.getEntidades() != null) request.getSession().setAttribute("pedido", resultado.getEntidades().get(0));
                
                request.setAttribute("messageError", resultado);
                request.getRequestDispatcher("tela_forma_pagamento.jsp").forward(request, response);
            
            } else {
                response.sendRedirect("/KeyBoard/pedido?operacao=CONSULTAR&cliente_id="+((Pedido) resultado.getEntidades().get(0)).getCliente().getId());
            }           
            
    
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
    
    public void setCupomPromocional (HttpServletRequest request, Pedido pedido) {
        
        int id_cupom_promocional = ParameterParser.toInt(request.getParameter("cupom_promocional_usado"));
        
        if(pedido.getValor_total_com_desconto() > 0) {
            pedido.getPagamento().add(new Pagamento(pedido.getValor_total() - pedido.getValor_total_com_desconto(), getCupomPromocional(request, id_cupom_promocional)));
        }
    }

    public void getCartoesUsados(HttpServletRequest request, Pedido pedido) {

        String [] valoresAndCartoes = request.getParameterValues("cartao");
        
        double valor;
        CartaoDeCredito cartao;
        
        
        for (int i = 0; i<valoresAndCartoes.length; i+=2){
            valor = ParameterParser.toDouble(valoresAndCartoes[i]);
            cartao = getCartao(request, ParameterParser.toInt(valoresAndCartoes[i+1]));
            
            System.out.println("Cartão:" + cartao);
            System.out.println("Valor cartão:" + valor);
            
            if(valor != 0.0 && cartao != null) pedido.getPagamento().add(new Pagamento(valor, cartao)); 
               
        }
    }   
    
    public void getCuponsUsados(HttpServletRequest request, Pedido pedido) {

        String [] valoresAndCartoes = request.getParameterValues("cupom");
        CupomDeTroca cupom;
        
        for (int j = 0; j<valoresAndCartoes.length; j++) {
            System.out.println("Cupom usado" + valoresAndCartoes[j]);
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
    
     public CupomPromocional getCupomPromocional (HttpServletRequest request, int idCupom) {
         
        Cliente cliente = (Cliente) request.getSession().getAttribute("cliente_info");

        for (CupomPromocional cupom : cliente.getCuponsPromocionais()) {            
            if (cupom.getId() == idCupom) {
                return cupom;
            }
        }

        return null;
    }
}
