/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.keyboard.model.strategy;

import com.mycompany.keyboard.model.domain.Carrinho;
import com.mycompany.keyboard.model.domain.Item;

/**
 *
 * @author Tiago
 */
public class FunctionsUtilsPagamento {
    
    public static Double calcularValorTotal(Carrinho carrinho){
        
        double valorTotal = 0.0;
        
        for(Item item : carrinho.getItens()){
            valorTotal += item.getTeclado().getValor_venda() * item.getQuantidade();
        }
        
        return valorTotal;
    }
}
