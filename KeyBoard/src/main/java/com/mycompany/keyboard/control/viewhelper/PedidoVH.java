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
import com.mycompany.keyboard.model.domain.FormasDePagamento;
import com.mycompany.keyboard.model.domain.Pagamento;
import com.mycompany.keyboard.model.domain.Pedido;
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

        CupomDeTroca cupom;

        String operacao = request.getParameter("operacao");

        if (operacao.equals("FINALIZAR")) {

            pedido.setCliente((Cliente) request.getSession().getAttribute("cliente_info"));
            pedido.setItens(((Carrinho) request.getSession().getAttribute("cliente_carrinho")).getItens());
            pedido.getEndereco().setId(ParameterParser.toInt(request.getParameter("endereco_entrega")));
            pedido.setValor_total(ParameterParser.toDouble(request.getParameter("valor_total")));

            request.getSession().setAttribute("pedido", pedido);

        }

        if (operacao.equals("PAGAR")) {
            pedido = (Pedido) request.getSession().getAttribute("pedido");

            if (request.getParameter("cartao1") != null) {
                Double valor = ParameterParser.toDouble(request.getParameter("valor_cartao1"));
                FormasDePagamento forma_de_pagamento = getCartaoSelecionado(request, "cartao1");
                pedido.getPagamento().add(new Pagamento(valor, forma_de_pagamento));
            }

            if (request.getParameter("cartao2") != null) {
                Double valor = ParameterParser.toDouble(request.getParameter("valor_cartao2"));
                FormasDePagamento forma_de_pagamento = getCartaoSelecionado(request, "cartao2");
                pedido.getPagamento().add(new Pagamento(valor, forma_de_pagamento));
            }

            if (request.getParameter("cupom1") != null) {
                cupom = (CupomDeTroca) getCupomUsado(request, 1);
                pedido.getPagamento().add(new Pagamento(cupom.getValor(), cupom));

            }
            if (request.getParameter("cupom2") != null) {
                cupom = (CupomDeTroca) getCupomUsado(request, 2);
                pedido.getPagamento().add(new Pagamento(cupom.getValor(), cupom));
            }
            if (request.getParameter("cupom3") != null) {
                cupom = (CupomDeTroca) getCupomUsado(request, 3);
                pedido.getPagamento().add(new Pagamento(cupom.getValor(), cupom));
            }
            if (request.getParameter("cupom4") != null) {
                cupom = (CupomDeTroca) getCupomUsado(request, 4);
                pedido.getPagamento().add(new Pagamento(cupom.getValor(), cupom));
            }

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
            
        } else if (resultado.getMsg() == null && operacao.equals("CONSULTAR")) {
            request.getSession().setAttribute("resultado", resultado);
            request.getRequestDispatcher("tela_carrinho.jsp").forward(request, response);
        }
    }

    public FormasDePagamento getCartaoSelecionado(HttpServletRequest request, String nomeCartaoUsado) {

        int cartao_selecionado_id = ParameterParser.toInt(request.getParameter(nomeCartaoUsado));

        Cliente cliente = (Cliente) request.getSession().getAttribute("cliente_info");

        for (CartaoDeCredito cartao : cliente.getCartoesDeCredito()) {
            if (cartao.getId() == cartao_selecionado_id) {
                return cartao;
            }
        }

        return null;
    }

    public FormasDePagamento getCupomUsado(HttpServletRequest request, int idCupomUsado) {

        Cliente cliente = (Cliente) request.getSession().getAttribute("cliente_info");

        for (CupomDeTroca cupom : cliente.getCuponsDeTroca()) {
            if (cupom.getId() == idCupomUsado) {
                return cupom;
            }
        }

        return null;
    }
}
