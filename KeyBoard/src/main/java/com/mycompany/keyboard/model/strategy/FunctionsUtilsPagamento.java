/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.keyboard.model.strategy;

import com.mycompany.keyboard.model.domain.CartaoDeCredito;
import com.mycompany.keyboard.model.domain.CupomPromocional;
import com.mycompany.keyboard.model.domain.Item;
import com.mycompany.keyboard.model.domain.Pagamento;
import com.mycompany.keyboard.model.domain.Pedido;
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
    
    public static boolean validateIfContainCartaoDeCredito (Pedido pedido) {
    
        for (Pagamento pagamento: pedido.getPagamento()) {
            if (pagamento.getForma_de_pagamento() instanceof CartaoDeCredito) {                 
                return true;
            }
        }
        
        return false;
    }
    
    public static double getValorTotalCompra (Pedido pedido) {  
        return (pedido.getValor_total_com_desconto() > 0) ? pedido.getValor_total_com_desconto() : pedido.getValor_total();       
    }
}
