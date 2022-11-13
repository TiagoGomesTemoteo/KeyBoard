/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.keyboard.model.strategy;

import com.mycompany.keyboard.model.domain.CartaoDeCredito;
import com.mycompany.keyboard.model.domain.CupomDeTroca;
import com.mycompany.keyboard.model.domain.CupomPromocional;
import com.mycompany.keyboard.model.domain.EntidadeDominio;
import com.mycompany.keyboard.model.domain.Pagamento;
import com.mycompany.keyboard.model.domain.Pedido;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Tiago
 */
public class PedidoRN implements IStrategy{

    @Override
    public String processar(EntidadeDominio entidade, String oporacao) {        
        if (validateIfThePaymentIsDifferentFromThePurchaseAmount((Pedido)entidade)) 
            return Messages.validateIfThePaymentIsDifferentFromThePurchaseAmount();        
        
        if (validarDiferencaTotalCuponsAndTotalCompra((Pedido)entidade)) 
           if (validarValorMinimoCartao((Pedido)entidade)) 
               return Messages.valorMinimoCartao();
         
        if (validarUsoDeCupomDesnecessario((Pedido) entidade)) 
            return Messages.validateUsoDeCupomDesnecessario();                 

        return null;
    }
    
    public boolean validarDiferencaTotalCuponsAndTotalCompra (Pedido pedido) {
        
        double valorTotalCupons = 0.0;
        
        for (Pagamento pagamento : pedido.getPagamento()) {
            if (pagamento.getForma_de_pagamento() instanceof CupomDeTroca) {
                valorTotalCupons += pagamento.getValor();
            }
        }
                        
        return (FunctionsUtilsPagamento.getValorTotalCompra(pedido) - valorTotalCupons > 10.0); 
        
    }
    
    public boolean validarValorMinimoCartao (Pedido pedido) {
  
        for (Pagamento pagamento : pedido.getPagamento()) {
            if (pagamento.getForma_de_pagamento() instanceof CartaoDeCredito) {
                if (pagamento.getValor() < 10.0) return true;
            }
        }
        
        return false;
    }
    
    public boolean validarUsoDeCupomDesnecessario (Pedido pedido) {

        if (getQuantidadeCuponsUsados(pedido) > 1) {
            if (validateIfIsPossiblePayTheCheckWithOneCoupon(pedido)){                
                return true;
            } else {          
                if (validateIfIsPossiblePayTheCheckWithLessCoupons(pedido)) {                    
                    return true;
                }        
            }                       
        }
        return false;
    }
    
    public boolean validateIfIsPossiblePayTheCheckWithOneCoupon (Pedido pedido) {

        for (Pagamento pagamento: pedido.getPagamento()) {           
            if (pagamento.getForma_de_pagamento() instanceof CupomDeTroca){
                if (pagamento.getValor() >= FunctionsUtilsPagamento.getValorTotalCompra(pedido)) {                    
                    return true;
                }
            }
        }
        
        return false;
    } 
    
    public boolean validateIfIsPossiblePayTheCheckWithLessCoupons (Pedido pedido) {
        int count = 2;
        
        String[] idCupons = getIdCuponsUsados(pedido);
        String[] saida;
        
        while (count < getQuantidadeCuponsUsados(pedido)) {
            Combinacao combinacoes = new Combinacao(idCupons, count);
            
            while (combinacoes.hasNext()) {
                saida = combinacoes.next();               
                if (valorTotalCombinacaoCupom(pedido, saida) > FunctionsUtilsPagamento.getValorTotalCompra(pedido)) return true;                    
            } 
            
            count++;
        }
        
        return false;   
    }   
    
    public int getQuantidadeCuponsUsados (Pedido pedido) {       
        int count = 0; 
        
        for (Pagamento pagamento: pedido.getPagamento()) {
            if (pagamento.getForma_de_pagamento() instanceof CupomDeTroca) count++;  
        }              
        return count;
    }
    
    public String[] getIdCuponsUsados (Pedido pedido) {
        int count = 0;  
        
        String[] idCupons = new String[getQuantidadeCuponsUsados(pedido)];  
        
        for (Pagamento pagamento: pedido.getPagamento()) {            
            if (pagamento.getForma_de_pagamento() instanceof CupomDeTroca) {
                idCupons[count] = String.valueOf(pagamento.getForma_de_pagamento().getId());
                count++;
            }
        }               
        return idCupons;
    }
    
    public double valorTotalCombinacaoCupom (Pedido pedido, String[] combinacoes) {
        double valorTotal = 0.0;
        
        List<Pagamento> pagamentos = new ArrayList();
        
        for (Pagamento pagamento: pedido.getPagamento()) {       
            if (pagamento.getForma_de_pagamento() instanceof CupomDeTroca) {
                for (int i = 0; i < combinacoes.length; i++) {
                    if (pagamento.getForma_de_pagamento().getId() == Integer.parseInt(combinacoes[i])) {
                        pagamentos.add(pagamento);
                    }
                }
            }
        }
        
        for (Pagamento pagamento: pagamentos) {
            valorTotal += pagamento.getValor();
        }
        
        return valorTotal;
    }
    
    
    public boolean validateIfThePaymentIsDifferentFromThePurchaseAmount (Pedido pedido) {
           
        double totalCuponsAndCartoes = 0.0;

        for (Pagamento pagamento: pedido.getPagamento()) {  
            if (!(pagamento.getForma_de_pagamento() instanceof CupomPromocional)) {
                totalCuponsAndCartoes += pagamento.getValor();                  
            }
        }
            
        if (FunctionsUtilsPagamento.validateIfContainCartaoDeCredito(pedido)) {
            return (totalCuponsAndCartoes != FunctionsUtilsPagamento.getValorTotalCompra(pedido));
        
        } else {
            return (totalCuponsAndCartoes < FunctionsUtilsPagamento.getValorTotalCompra(pedido));
        }

    }
    
    
}
