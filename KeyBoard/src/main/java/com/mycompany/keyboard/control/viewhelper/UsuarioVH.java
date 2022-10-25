/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.keyboard.control.viewhelper;

import com.mycompany.keyboard.model.domain.Cliente;
import com.mycompany.keyboard.model.domain.EntidadeDominio;
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
public class UsuarioVH implements IViewHelper{

    @Override
    public EntidadeDominio getEntidade(HttpServletRequest request) {
        
        String operacao = request.getParameter("operacao");
        Cliente cliente = new Cliente();
        
        if (operacao.equals("ENTRAR")) {          
            cliente.setEmail(request.getParameter("email_login"));
            cliente.setSenha(request.getParameter("senha"));
            
        } else if (operacao.equals("SAIR")) {
            request.getSession().removeAttribute("usuario");
            request.getSession().invalidate();
        }

        return cliente;
        
    }

    @Override
    public void setView(Resultado resultado, HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        
        String operacao = request.getParameter("operacao");
        
        if(operacao.equals("ENTRAR")) {
            
            if (resultado.getMsg() != null) {
                request.setAttribute("messageError", resultado);
                request.getRequestDispatcher("tela_cadastrar_cliente.jsp").forward(request, response);
            
            } else {                                
                request.getSession().setAttribute("usuario", resultado.getEntidades().get(0));
                request.getSession().setAttribute("teclados", ClienteInSession.getAllTeclados(request));//Será redirecionado para tela de teclados por isso é preciso setar todos os teclados na sessão
                request.getRequestDispatcher("lista_teclado.jsp").forward(request, response);
            }
        } else if (operacao.equals("SAIR")) {
            response.sendRedirect("/KeyBoard/tela_cadastrar_cliente.jsp");                       
        }
    }       
}

