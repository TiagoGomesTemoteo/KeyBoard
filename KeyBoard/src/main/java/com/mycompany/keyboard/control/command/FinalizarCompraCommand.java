/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.keyboard.control.command;

import com.mycompany.keyboard.model.domain.EntidadeDominio;
import com.mycompany.keyboard.util.Resultado;

/**
 *
 * @author Tiago
 */
public class FinalizarCompraCommand extends AbstractCommand{
    
    @Override
    public Resultado execute(EntidadeDominio entidade) {
        return facade.finalizarCompra(entidade);
    }
}
