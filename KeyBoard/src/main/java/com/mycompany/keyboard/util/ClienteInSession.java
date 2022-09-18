/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.keyboard.util;

import com.mycompany.keyboard.model.dao.ClienteDAO;
import javax.servlet.http.HttpServletRequest;

/**
 *
 * @author Tiago
 */
public class ClienteInSession {
    
    public static void Atualizar(HttpServletRequest request){
        request.getSession().setAttribute("cliente_info", new ClienteDAO().consultar(1));  
    }
}
