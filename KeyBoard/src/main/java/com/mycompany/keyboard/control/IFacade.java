/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.keyboard.control;

import com.mycompany.keyboard.model.domain.EntidadeDominio;
import com.mycompany.keyboard.util.Resultado;

/**
 *
 * @author Tiago
 */
public interface IFacade {

    public Resultado salvar(EntidadeDominio entidade);

    public Resultado alterar(EntidadeDominio entidade);

    public Resultado deletar(EntidadeDominio entidade);

    public Resultado consultar(EntidadeDominio entidade);

    public Resultado visualizar(EntidadeDominio entidade);

    public Resultado logar(EntidadeDominio entidade);
    
    public Resultado finalizarCompra(EntidadeDominio entidade);
}
