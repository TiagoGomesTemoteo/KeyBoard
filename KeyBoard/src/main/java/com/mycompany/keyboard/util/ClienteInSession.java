/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.keyboard.util;

import com.mycompany.keyboard.model.dao.CarrinhoDAO;
import com.mycompany.keyboard.model.dao.ClienteDAO;
import com.mycompany.keyboard.model.dao.PedidoDAO;
import com.mycompany.keyboard.model.dao.TecladoDAO;
import com.mycompany.keyboard.model.dao.TrocaDAO;
import com.mycompany.keyboard.model.domain.Cliente;
import com.mycompany.keyboard.model.domain.Pedido;
import com.mycompany.keyboard.model.domain.Teclado;
import com.mycompany.keyboard.model.domain.Troca;
import javax.servlet.http.HttpServletRequest;

/**
 *
 * @author Tiago
 */
public class ClienteInSession {
        
    public static void Atualizar(HttpServletRequest request){
        Cliente cliente = getClienteLogado(request);
        request.getSession().setAttribute("cliente_info", new ClienteDAO().consultar(cliente.getId()));  
    }
    
    public static void getAllItensCar(HttpServletRequest request){
        Cliente cliente = getClienteLogado(request);
        request.getSession().setAttribute("cliente_carrinho", new CarrinhoDAO().consultar(cliente.getId()));  
    }
    
    public static Resultado getAllPedidos(){
        Resultado resultado = new Resultado();
        
        resultado.setEntidades(new PedidoDAO().consultar(new Pedido()));
        
        return resultado;
    }
    
    public static Resultado getAllPedidosTroca(){
        Resultado resultado = new Resultado();
        
        resultado.setEntidades(new TrocaDAO().consultar(new Troca()));
        
        return resultado;
    }
    
    public static Resultado getAllTeclados(HttpServletRequest request) {
        
        Resultado resultado = new Resultado();
        
        resultado.setEntidades(new TecladoDAO().consultar(new Teclado()));
        
        return resultado;
    }
    
    public static Cliente getClienteLogado (HttpServletRequest request) {        
        Cliente cliente = (Cliente) request.getSession().getAttribute("usuario");
        return cliente;
    }
}

