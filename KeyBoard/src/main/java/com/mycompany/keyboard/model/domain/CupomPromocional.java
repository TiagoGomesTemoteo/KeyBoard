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
public class CupomPromocional extends FormasDePagamento{
    private int porcentagem_desconto;
    private Date dt_validade;
    private boolean ativo;
    
    public CupomPromocional() {
        super();
        this.porcentagem_desconto = 0;
        this.dt_validade = new Date();
        this.ativo = false;
    }

    public int getPorcentagem_desconto() {
        return porcentagem_desconto;
    }

    public void setPorcentagem_desconto(int porcentagem_desconto) {
        this.porcentagem_desconto = porcentagem_desconto;
    }

    public Date getDt_validade() {
        return dt_validade;
    }

    public void setDt_validade(Date dt_validade) {
        this.dt_validade = dt_validade;
    }

    public boolean isAtivo() {
        return ativo;
    }

    public void setAtivo(boolean ativo) {
        this.ativo = ativo;
    }        
}
