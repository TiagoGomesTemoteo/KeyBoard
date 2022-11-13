/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.keyboard.util;

import com.mycompany.keyboard.model.domain.CartaoDeCredito;
import com.mycompany.keyboard.model.domain.CupomDeTroca;
import com.mycompany.keyboard.model.domain.Endereco;
import com.mycompany.keyboard.model.domain.Item;
import com.mycompany.keyboard.model.domain.Pagamento;
import com.mycompany.keyboard.model.domain.Teclado;
import com.mycompany.keyboard.model.domain.enums.Estatus;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 *
 * @author Tiago
 */
public class Masks {
    
    public static String brazilianDate(Date data){
        
        if (data != null) return new SimpleDateFormat("dd/MM/yyyy").format(data);
        
        return null;
    }
    
    public static String buildTextFormaPagamento(List<Pagamento> pagamentos){
        
        String text = "";
        
        boolean isCartao = false;
        boolean isCupom = false;
        
        for (Pagamento pagamento : pagamentos){
            if(pagamento.getForma_de_pagamento() instanceof CartaoDeCredito) isCartao = true;
            if(pagamento.getForma_de_pagamento() instanceof CupomDeTroca) {
                System.out.println("CupomDeTroca: " + pagamento.getForma_de_pagamento().getId());
                isCupom = true;
            }
        }
        
        if (isCartao) text = "Cartão de Crédito";
        
        if (isCupom) {
            if (text.equals("")) {
                text = "Cupom de Troca";
            } else {
                text+="\nCupom de Troca"; 
            }
        }
        
        return text;
    }
    
    public static String buildDescricaoTeclado (Teclado teclado){
        return "Teclado " + teclado.getMarca() + " modelo " + teclado.getModelo() + " com " + teclado.getQtd_teclas() + " teclas";
    }
    
    public static String buildDinheiro (Double valor){
        NumberFormat f = NumberFormat.getCurrencyInstance();
        return f.format(valor);
    }
    
    public static String itemValorTotal (Item item){
        double valor = item.getQuantidade() * item.getTeclado().getValor_venda();
        
        NumberFormat f = NumberFormat.getCurrencyInstance();
        return f.format(valor);
    }
    
    public static String buildDescricaoEndereco (Endereco endereco){
        return "Rua " + endereco.getLogradouro() + ",  Nº " + endereco.getNumero() + " " + endereco.getBairro() + ", CEP "
               + endereco.getCep() + " - " + endereco.getCidade();
    }
    
    public static String buildDescricaoStatus (Estatus estatus){  
        
        if (estatus != null) {
            switch (estatus.getEstatus()) {
                case 7:  return "Em Processamento";
                case 8:  return "Pagamento Realizado";
                case 9:  return "Em Transporte";
                case 10: return "Entrega Realizada";
                case 11: return "Finalizado";
                case 12: return "Aprovada";
                case 13: return "Reprovada";
                case 14: return "Entregue";
                case 15: return "Em Troca";
                case 16: return "Troca Autorizada";
                case 17: return "Trocado";
            }
        }
        return null;
    }
}
