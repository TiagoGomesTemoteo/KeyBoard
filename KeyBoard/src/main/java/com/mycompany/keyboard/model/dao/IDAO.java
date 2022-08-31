/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.keyboard.model.dao;

import com.mycompany.keyboard.model.domain.EntidadeDominio;
import java.util.List;

/**
 *
 * @author Tiago
 */
public interface IDAO {
    
    public EntidadeDominio salvar(EntidadeDominio entidade);
    public boolean alterar(EntidadeDominio entidade);
    public boolean deletar(int id);
    public List consultar();
    public EntidadeDominio consultar(EntidadeDominio entidade);
}
