/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.keyboard.model.domain.enums;

/**
 *
 * @author Tiago
 */
public enum Estatus {
    EM_ANALISE(7), PAGAMENTO_REALIZADO(8), EM_TRANSPORTE(9), ENTREGA_REALIZADA(10), FINALIZADO(11);

    private Estatus(final int estatus) {
        this.estatus = estatus;
    }

    private int estatus;

    public int getEstatus() {
        return estatus;
    }
}
