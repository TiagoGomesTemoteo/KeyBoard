/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.keyboard.util;

import com.mycompany.keyboard.model.domain.CartaoDeCredito;
import com.mycompany.keyboard.model.domain.CupomDeTroca;
import com.mycompany.keyboard.model.domain.Pagamento;
import com.mycompany.keyboard.model.domain.Teclado;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 *
 * @author Tiago
 */
public class Masks {
    
    public static String brazilianDate(Date data){
        return new SimpleDateFormat("dd/MM/yyyy").format(data);
    }
    
    public static String buildTextFormaPagamento(List<Pagamento> pagamentos){
        
        String text = "";
        
        boolean isCartao = false;
        boolean isCupom = false;
        
        for (Pagamento pagamento : pagamentos){
            if(pagamento.getForma_de_pagamento() instanceof CartaoDeCredito) isCartao = true;
            if(pagamento.getForma_de_pagamento() instanceof CupomDeTroca) isCupom = true;
        }
        
        if (isCartao) text = "Cartão de Crédito";
        if (isCupom && text.equals("")) text = "Cupom de Troca"; else text+="\nCupom de Troca"; 
        
        return text;
    }
    
    public static String buildDescricaoTeclado (Teclado teclado){
        return "Teclado " + teclado.getMarca() + " modelo " + teclado.getModelo() + " com " + teclado.getQtd_teclas() + " teclas";
    }
}
