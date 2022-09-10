/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.keyboard.model.strategy;

import com.mycompany.keyboard.model.domain.EntidadeDominio;

/**
 *
 * @author Tiago
 */
public interface IStrategy {
    
    public String processar(EntidadeDominio entidade, String oporacao);
}
