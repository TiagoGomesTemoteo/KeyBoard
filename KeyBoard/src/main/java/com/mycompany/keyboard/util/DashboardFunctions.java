/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.keyboard.util;

import com.mycompany.keyboard.model.domain.Item;
import com.mycompany.keyboard.model.domain.Pedido;
import com.mycompany.keyboard.model.domain.Teclado;
import java.util.List;

/**
 *
 * @author Tiago
 */
public class DashboardFunctions {
    
    public double getValorTotalVendasByTeclado(List<Pedido> pedidos, Teclado teclado) {
        double valorTotal = 0.0;
        
        for (Pedido pedido: pedidos) {
            for (Item item: pedido.getItens()) {
                if (item.getTeclado().getId() == teclado.getId()) {
                    valorTotal+=item.getQuantidade() * item.getTeclado().getValor_venda();
                }
            }
        }
        
        return valorTotal;
    }
    
}
