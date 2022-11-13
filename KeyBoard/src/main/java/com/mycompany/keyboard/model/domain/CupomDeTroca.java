/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.keyboard.model.domain;

import java.util.Date;

/**
 *
 * @author Tiago
 */
public class CupomDeTroca extends FormasDePagamento{
    
    private double valor;
    private Date validade;
    private boolean ativo;

    public CupomDeTroca (double valor, Date validade, int id, Date dt_cadastro, boolean ativo) {
        this.valor = valor;
        this.validade = validade;
        this.ativo = ativo;
    }

    public CupomDeTroca() {
        this.valor = 0.0;
        this.validade = new Date();
        this.ativo = true;
    }

    public double getValor() {
        return valor;
    }

    public void setValor(double valor) {
        this.valor = valor;
    }

    public Date getValidade() {
        return validade;
    }

    public void setValidade(Date validade) {
        this.validade = validade;
    }

    public boolean isAtivo() {
        return ativo;
    }

    public void setAtivo(boolean ativo) {
        this.ativo = ativo;
    }   
  
}
