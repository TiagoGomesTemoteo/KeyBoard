/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.keyboard.control;

import com.mycompany.keyboard.model.domain.EntidadeDominio;

/**
 *
 * @author Tiago
 */
public interface IFacade {
    
    public Object salvar(EntidadeDominio entidade);
    public String alterar(EntidadeDominio entidade);
    public String deletar(EntidadeDominio entidade);
    public Object consultar(EntidadeDominio entidade);
}
