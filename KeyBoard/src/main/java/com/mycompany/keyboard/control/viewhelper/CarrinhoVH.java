/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.keyboard.control.viewhelper;

import com.mycompany.keyboard.model.domain.Carrinho;
import com.mycompany.keyboard.model.domain.EntidadeDominio;
import com.mycompany.keyboard.model.domain.Item;
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
        
        if (operacao.equals("SALVAR")) {
            item.setQuantidade(1);
            item.setNewInTheCar(true);
            item.getTeclado().setId(ParameterParser.toInt(request.getParameter("teclado_id")));
            carrinho.getItens().add(item);
            carrinho.getCliente().setId(ParameterParser.toInt(request.getParameter("cliente_id")));
            
        } 

        return carrinho;
        
    }

    @Override
    public void setView(Resultado resultado, HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        
        String operacao = request.getParameter("operacao");
        
        if(operacao.equals("SALVAR")){
            if(resultado.getMsg() != null){
                request.setAttribute("resultado", resultado);
                request.getRequestDispatcher("lista_teclado.jsp").forward(request, response); 
            }
            
            response.sendRedirect("/KeyBoard/cliente?operacao=CONSULTAR");
        }
//            
//        }else if(resultado.getMsg() == null && operacao.equals("VISUALIZAR")) {
//            request.setAttribute("cliente", resultado.getEntidades().get(0));
//            request.getRequestDispatcher("tela_editar_cliente.jsp").forward(request, response);
//             
//        }else if(resultado.getMsg() == null && operacao.equals("CONSULTAR")) {
//            request.getSession().setAttribute("resultado",resultado);
//            request.getRequestDispatcher("lista_cliente.jsp").forward(request, response);
//            
//        }else if(resultado.getMsg() == null && operacao.equals("ALTERAR")) {
//            response.sendRedirect("/KeyBoard/cliente?operacao=CONSULTAR");
//            
//        }else if(resultado.getMsg() == null && operacao.equals("DELETAR")) {
//            response.sendRedirect("/KeyBoard/cliente?operacao=CONSULTAR");
//        }
    
    }
}

