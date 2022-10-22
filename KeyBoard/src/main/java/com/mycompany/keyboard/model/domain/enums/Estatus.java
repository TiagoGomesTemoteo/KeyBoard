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
    EM_PROCESSAMENTO(7), PAGAMENTO_REALIZADO(8), EM_TRANSPORTE(9), ENTREGA_REALIZADA(10), FINALIZADO(11), APROVADA(12), REPROVADA(13), ENTREGUE(14), EM_TROCA(15), TROCA_AUTORIZADA(16), TROCADO(17);
      
    private final int estatus;
    
    private static final Map<Integer, Estatus> estatusPorValor = new HashMap<>();
    private static final Map<String, Estatus> estatusPorDescricao = new HashMap<>();
    
    static {
        for (Estatus funcao : Estatus.values()) {
            estatusPorValor.put(funcao.getEstatus(), funcao);
            estatusPorDescricao.put(funcao.toString(), funcao);
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
    
    public static Estatus pegaEstatusPorDescricao(String descricao){
        return estatusPorDescricao.get(descricao);
    }
}
