/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.keyboard.control.viewhelper;

import com.mycompany.keyboard.model.domain.*;
import com.mycompany.keyboard.model.domain.enums.Estatus;
import com.mycompany.keyboard.util.*;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author Tiago
 */
public class TrocaVH implements IViewHelper {

    @Override
    public EntidadeDominio getEntidade(HttpServletRequest request) {

        Troca troca = new Troca();

        String operacao = request.getParameter("operacao");

        if (operacao.equals("SOLICITAR_TROCA")) {
            troca.getPedidoOrigem().setId(ParameterParser.toInt(request.getParameter("pedido_origem_id")));
            getItensTroca(request, troca);
            troca.getCliente().setId(1);
            
        } else if (operacao.equals("CONSULTAR")) {
            troca.getCliente().setId(ParameterParser.toInt(request.getParameter("cliente_id")));
        
        } else if (operacao.equals("ALTERAR")) {
            troca.setId(ParameterParser.toInt(request.getParameter("troca_id")));
            troca.setEstatus(Estatus.pegaEstatusPorDescricao(request.getParameter("estatus")));
            
        } 
                
        return troca;

    }

    @Override
    public void setView(Resultado resultado, HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        String operacao = request.getParameter("operacao");

        if (operacao.equals("SOLICITAR_TROCA")) {
            response.sendRedirect("/KeyBoard/pedido?operacao=CONSULTAR&cliente_id=1");
        
        } else if (operacao.equals("CONSULTAR")) {
            request.getSession().setAttribute("resultado", resultado);
            
            if(ParameterParser.toInt(request.getParameter("cliente_id")) != 0) {
                request.getRequestDispatcher("lista_pedidos_cliente.jsp").forward(request, response);
            
            } else {
                request.getRequestDispatcher("lista_vendas.jsp").forward(request, response);
            }
                    
        } else if (operacao.equals("ALTERAR")){
            request.getSession().setAttribute("resultado", ClienteInSession.getAllPedidosTroca());
            request.getRequestDispatcher("lista_vendas.jsp").forward(request, response);
        }       
    }
    
    /*Essa função recebe todos os valores de produtos selecionados para troca bem como a quantidade de cada um*/
    /*Os ids dos produtos estão antecipados pela letra T.*/
    /*No vetor é primeiro verificado se o elemento tem a letra T para saber se aquele produto foi selecionado para troca*/
    /*Depois é verificado se o próximo valor corresponde a quantidade de produtos*/
    public void getItensTroca(HttpServletRequest request, Troca troca) {

        String [] valoresAndCartoes = request.getParameterValues("item_troca");
        
        String valor;
        int quantidade;
        int teclado_id;
        Teclado teclado;
        
        for (int i = 0; i<valoresAndCartoes.length; i++){ 
            valor = valoresAndCartoes[i];
            
            if(valor.substring(0, 1).equals("T") && i+1 < valoresAndCartoes.length) {                
                valor = valoresAndCartoes[i+1];

                if (!valor.substring(0, 1).equals("T")) {
                        teclado_id = ParameterParser.toInt(valoresAndCartoes[i].replace("T",""));
                        quantidade = ParameterParser.toInt(valoresAndCartoes[i+1]);

                        teclado = new Teclado();
                        teclado.setId(teclado_id);
                        troca.getProdutos().add(new Item(teclado, quantidade, false));
                }                
            }                                    
        }
    }       
}
