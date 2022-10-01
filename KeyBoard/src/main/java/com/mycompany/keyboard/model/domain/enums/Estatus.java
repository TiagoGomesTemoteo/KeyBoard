/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.keyboard.model.domain.enums;

import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author Tiago
 */
public enum Estatus {
    EM_ANALISE(7), PAGAMENTO_REALIZADO(8), EM_TRANSPORTE(9), ENTREGA_REALIZADA(10), FINALIZADO(11);
      
    private final int estatus;
    
    private static final Map<Integer, Estatus> estatusPorValor = new HashMap<>();
    
    static {
        for (Estatus funcao : Estatus.values()) {
            estatusPorValor.put(funcao.getEstatus(), funcao);
        }
     }
    private Estatus(final int estatus) {
        this.estatus = estatus;
    }

    public int getEstatus() {
        return estatus;
    }
    
    public static Estatus pegaEstatusPorValor(int valor){
        return estatusPorValor.get(valor);
    }
}
