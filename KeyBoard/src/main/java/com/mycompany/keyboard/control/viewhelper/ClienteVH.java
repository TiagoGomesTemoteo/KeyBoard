/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.keyboard.control.viewhelper;

import com.mycompany.keyboard.model.domain.Cliente;
import com.mycompany.keyboard.model.domain.Endereco;
import com.mycompany.keyboard.model.domain.EntidadeDominio;
import com.mycompany.keyboard.util.ParameterParser;
import com.mycompany.keyboard.util.Resultado;
import java.io.IOException;
import java.util.Arrays;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author Tiago
 */
public class ClienteVH implements IViewHelper {

    @Override
    public EntidadeDominio getEntidade(HttpServletRequest request) {
        
        Cliente cliente = new Cliente();

        String operacao = request.getParameter("operacao");
        
        if (operacao.equals("SALVAR") || operacao.equals("ALTERAR")) {
            
            cliente.setNome(request.getParameter("nome"));
            cliente.setDtNascimento(ParameterParser.toDate(request.getParameter("dtNascimento")));
            cliente.setTelefone(ParameterParser.toPhone(request.getParameter("telefone")));
            cliente.setGenero(ParameterParser.getGenSelected(request.getParameter("genero")));
            cliente.setCpf(request.getParameter("cpf"));
            cliente.setEmail(request.getParameter("email"));
            cliente.setSenha(request.getParameter("senha") + request.getParameter("confirmar_senha"));
            cliente.setEnderecos(Arrays.asList((Endereco) new EnderecoVH().getEntidade(request)));
            
            if(operacao.equals("ALTERAR")){
                cliente.setId(ParameterParser.toInt(request.getParameter("cliente_id")));
                cliente.getTelefone().setId(ParameterParser.toInt(request.getParameter("telefone_id")));
            }

        } else if (operacao.equals("VISUALIZAR")){
            HttpSession session = request.getSession();
            Resultado resultado = (Resultado)session.getAttribute("resultado");
            int idCliente = ParameterParser.toInt(request.getParameter("cliente_id"));
            
            for(EntidadeDominio ed : resultado.getEntidades()){
                if(ed.getId() == idCliente){
                    cliente = (Cliente)ed;
                }
            }
        } else if (operacao.equals("DELETAR")) {
            cliente.setId(ParameterParser.toInt(request.getParameter("cliente_id")));
            
            if(request.getParameter("ativo").equals("false")){
                cliente.setAtivo(true);
            }else{
                cliente.setAtivo(false);
            }
        }

        return cliente;
    }

    @Override
    public void setView(Resultado resultado, HttpServletRequest request,
            HttpServletResponse response) throws ServletException, IOException {
        
        String operacao = request.getParameter("operacao");
        
        if(resultado.getMsg() == null && operacao.equals("SALVAR")){
//            request.getSession().setAttribute("resultado",resultado);
//            rD = request.getRequestDispatcher("lista_cliente.jsp");
        response.sendRedirect("/KeyBoard/cliente?operacao=CONSULTAR");
            
        }else if(resultado.getMsg() == null && operacao.equals("VISUALIZAR")) {
            request.setAttribute("cliente", resultado.getEntidades().get(0));
            request.getRequestDispatcher("form_cliente.jsp").forward(request, response);
             
        }else if(resultado.getMsg() == null && operacao.equals("CONSULTAR")) {
            request.getSession().setAttribute("resultado",resultado);
            request.getRequestDispatcher("lista_cliente.jsp").forward(request, response);
            
        }else if(resultado.getMsg() == null && operacao.equals("ALTERAR")) {
            response.sendRedirect("/KeyBoard/cliente?operacao=CONSULTAR");
            
        }else if(resultado.getMsg() == null && operacao.equals("DELETAR")) {
            response.sendRedirect("/KeyBoard/cliente?operacao=CONSULTAR");
        }
    }

}
