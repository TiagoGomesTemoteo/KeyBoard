/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.keyboard.model.strategy;

import com.mycompany.keyboard.model.domain.EntidadeDominio;
import com.mycompany.keyboard.model.domain.Pedido;

/**
 *
 * @author Tiago
 */
public class CalcularValorTotalDoPedido implements IStrategy{

    @Override
    public String processar(EntidadeDominio entidade, String oporacao) {
        ((Pedido) entidade).setValor_total(FunctionsUtilsPagamento.calcularValorTotal(((Pedido)entidade).getItens()));
    
        return null;
    }
    
}
