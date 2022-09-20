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
public class CupomDeTroca extends EntidadeDominio{
    private double valor;
    private Date validade;

    public CupomDeTroca(double valor, Date validade, int id, Date dt_cadastro) {
        super(id, dt_cadastro);
        this.valor = valor;
        this.validade = validade;
    }

    public CupomDeTroca() {
        this.valor = 0.0;
        this.validade = new Date();
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
  
}
