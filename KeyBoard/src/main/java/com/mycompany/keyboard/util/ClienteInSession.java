/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.keyboard.util;

import com.mycompany.keyboard.model.dao.CarrinhoDAO;
import com.mycompany.keyboard.model.dao.ClienteDAO;
import com.mycompany.keyboard.model.dao.PedidoDAO;
import com.mycompany.keyboard.model.domain.Pedido;
import javax.servlet.http.HttpServletRequest;

/**
 *
 * @author Tiago
 */
public class ClienteInSession {
        
    public static void Atualizar(HttpServletRequest request){
        request.getSession().setAttribute("cliente_info", new ClienteDAO().consultar(1));  
    }
    
    public static void getAllItensCar(HttpServletRequest request){
        request.getSession().setAttribute("cliente_carrinho", new CarrinhoDAO().consultar(1));  
    }
    
    public static Resultado getAllPedidos(){
        Resultado resultado = new Resultado();
        
        resultado.setEntidades(new PedidoDAO().consultar(new Pedido()));
        
        return resultado;
    }
}

