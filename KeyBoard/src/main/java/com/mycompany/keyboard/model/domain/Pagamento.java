/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.keyboard.model.domain;

/**
 *
 * @author Tiago
 */
public class Pagamento {

    private double valor;
    private FormasDePagamento forma_de_pagamento;

    public Pagamento(double valor, FormasDePagamento forma_de_pagamento) {
        this.valor = valor;
        this.forma_de_pagamento = forma_de_pagamento;
    }

    public double getValor() {
        return valor;
    }

    public void setValor(double valor) {
        this.valor = valor;
    }

    public FormasDePagamento getForma_de_pagamento() {
        return forma_de_pagamento;
    }

    public void setForma_de_pagamento(FormasDePagamento forma_de_pagamento) {
        this.forma_de_pagamento = forma_de_pagamento;
    }

}
