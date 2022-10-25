/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.keyboard.model.strategy;

import com.mycompany.keyboard.model.domain.CupomPromocional;
import com.mycompany.keyboard.model.domain.Item;
import java.util.List;

/**
 *
 * @author Tiago
 */
public class FunctionsUtilsPagamento {
    
    public static Double calcularValorTotal(List<Item> itens){
        
        double valorTotal = 0.0;
        
        for(Item item : itens){
            valorTotal += item.getTeclado().getValor_venda() * item.getQuantidade();
        }
        
        return valorTotal;
    }
    
    public static Double calcularValorTotalComDesconto(Double total, CupomPromocional cupom_promocional){

        return total - (total * cupom_promocional.getPorcentagem_desconto() / 100);
    
    }
}
