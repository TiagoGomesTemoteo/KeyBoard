/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.keyboard.control.viewhelper;

import com.mycompany.keyboard.model.domain.EntidadeDominio;
import com.mycompany.keyboard.model.domain.Teclado;
import com.mycompany.keyboard.util.ParameterParser;
import com.mycompany.keyboard.util.Resultado;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author Tiago
 */
public class TecladoVH implements IViewHelper{

    @Override
    public EntidadeDominio getEntidade(HttpServletRequest request) {
        String operacao = request.getParameter("operacao");
        
        if(operacao.equals("VISUALIZAR")) {
            HttpSession session = request.getSession();
            Resultado resultado = (Resultado)session.getAttribute("resultado");
            int teclado_id = ParameterParser.toInt(request.getParameter("teclado_id"));
            
            for (EntidadeDominio teclado : resultado.getEntidades()) {
                if (teclado.getId() == teclado_id) {
                    return teclado;
                }
            } 
        }
        
        return new Teclado();
    }

    @Override
    public void setView(Resultado resultado, HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String operacao = request.getParameter("operacao");
        
        if(operacao.equals("SALVAR")){
            if(resultado.getMsg() != null){
                request.setAttribute("resultado", resultado);
                request.getRequestDispatcher("tela_editar_cliente.jsp").forward(request, response); 
            }
            
        response.sendRedirect("/KeyBoard/cliente?operacao=CONSULTAR");
            
        }else if (resultado.getMsg() == null && operacao.equals("VISUALIZAR")) {
            request.setAttribute("teclado", resultado.getEntidades().get(0));
            request.getRequestDispatcher("tela_detalhes_teclado.jsp").forward(request, response);
             
        }else if (resultado.getMsg() == null && operacao.equals("CONSULTAR")) {
            request.getSession().setAttribute("resultado",resultado);
            request.getRequestDispatcher("lista_teclado.jsp").forward(request, response);
            
        }else if (resultado.getMsg() == null && operacao.equals("ALTERAR")) {
            response.sendRedirect("/KeyBoard/cliente?operacao=CONSULTAR");
            
        }else if (resultado.getMsg() == null && operacao.equals("DELETAR")) {
            response.sendRedirect("/KeyBoard/cliente?operacao=CONSULTAR");
        }
    }
    
}
