/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.keyboard.control.command;

import com.mycompany.keyboard.model.domain.EntidadeDominio;

/**
 *
 * @author Tiago
 */
public class ConsultarCommand extends AbstractCommand{
    
    @Override
    public Object execute(EntidadeDominio entidade) {
        return facade.consultar(entidade);
    }
}
