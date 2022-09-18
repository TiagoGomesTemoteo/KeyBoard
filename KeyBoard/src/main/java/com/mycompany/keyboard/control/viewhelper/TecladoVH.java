/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.keyboard.control.viewhelper;

import com.mycompany.keyboard.model.domain.EntidadeDominio;
import com.mycompany.keyboard.model.domain.Teclado;
import com.mycompany.keyboard.util.Resultado;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author Tiago
 */
public class TecladoVH implements IViewHelper{

    @Override
    public EntidadeDominio getEntidade(HttpServletRequest request) {
     
        return new Teclado();
    }

    @Override
    public void setView(Resultado resultado, HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String operacao = request.getParameter("operacao");
        
        if(operacao.equals("SALVAR")){
//            request.getSession().setAttribute("resultado",resultado);
//            rD = request.getRequestDispatcher("lista_cliente.jsp");
            if(resultado.getMsg() != null){
                request.setAttribute("resultado", resultado);
                request.getRequestDispatcher("tela_editar_cliente.jsp").forward(request, response); 
            }
        response.sendRedirect("/KeyBoard/cliente?operacao=CONSULTAR");
            
        }else if(resultado.getMsg() == null && operacao.equals("VISUALIZAR")) {
            request.setAttribute("cliente", resultado.getEntidades().get(0));
            request.getRequestDispatcher("tela_editar_cliente.jsp").forward(request, response);
             
        }else if(resultado.getMsg() == null && operacao.equals("CONSULTAR")) {
            request.getSession().setAttribute("resultado",resultado);
            request.getRequestDispatcher("lista_teclado.jsp").forward(request, response);
            
        }else if(resultado.getMsg() == null && operacao.equals("ALTERAR")) {
            response.sendRedirect("/KeyBoard/cliente?operacao=CONSULTAR");
            
        }else if(resultado.getMsg() == null && operacao.equals("DELETAR")) {
            response.sendRedirect("/KeyBoard/cliente?operacao=CONSULTAR");
        }
    }
    
}
