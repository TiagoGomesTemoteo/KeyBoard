/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.keyboard.control.viewhelper;

import com.mycompany.keyboard.model.domain.EntidadeDominio;
import com.mycompany.keyboard.model.domain.Pedido;
import com.mycompany.keyboard.util.ClienteInSession;
import com.mycompany.keyboard.util.Resultado;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author Tiago
 */
public class DashboardVH implements IViewHelper {

    @Override
    public EntidadeDominio getEntidade(HttpServletRequest request) {

        Pedido pedido = new Pedido();

        return pedido;

    }

    @Override
    public void setView(Resultado resultado, HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        String operacao = request.getParameter("operacao");

        ClienteInSession.Atualizar(request);
        ClienteInSession.getAllItensCar(request);

        if (operacao.equals("CONSULTAR")){
            request.getSession().setAttribute("resultado", resultado);
            request.getRequestDispatcher("dashboard.jsp").forward(request, response);   
        }   
    }   
}
