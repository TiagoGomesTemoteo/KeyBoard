/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.keyboard.control;

import com.mycompany.keyboard.control.command.*;
import com.mycompany.keyboard.control.viewhelper.*;
import com.mycompany.keyboard.model.domain.EntidadeDominio;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Timer;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author Tiago
 */
@WebServlet(name = "Controller",
        urlPatterns = {"/cliente", "/endereco", "cartao"})
public class Controller extends HttpServlet {

    private static final long serialVersionUID = 1L;
    private static String operacao = "";
    private static Map<String, ICommand> cmds;
    private static Map<String, IViewHelper> vhs;

    public Controller() {
        cmds = new HashMap<>();
        cmds.put("SALVAR", new SalvarCommand());
        cmds.put("ALTERAR", new AlterarCommand());
        cmds.put("DELETAR", new DeletarCommand());
        cmds.put("CONSULTAR", new ConsultarCommand());

        vhs = new HashMap<>();
        vhs.put("/KeyBoard/cliente", new ClienteVH());
        vhs.put("/KeyBoard/endereco", new ClienteVH());
        vhs.put("/KeyBoard/cartao", new ClienteVH());

    }

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        operacao = request.getParameter("operacao");

        String uri = request.getRequestURI();
        IViewHelper viewHelper = vhs.get(uri);
        EntidadeDominio entidade = viewHelper.getEntidade(request);
        ICommand command = cmds.get(operacao);
        Object message = command.execute(entidade);
        viewHelper.setView(message, request, response);
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }
}
