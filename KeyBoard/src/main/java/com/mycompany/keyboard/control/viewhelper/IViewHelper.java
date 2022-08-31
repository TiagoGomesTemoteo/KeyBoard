/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.keyboard.control.viewhelper;

import com.mycompany.keyboard.model.domain.EntidadeDominio;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author Tiago
 */
public interface IViewHelper {
    
    public EntidadeDominio getEntidade(HttpServletRequest request);
    
    public void setView(Object resultado, HttpServletRequest request, 
            HttpServletResponse response)throws ServletException, IOException; 
}
