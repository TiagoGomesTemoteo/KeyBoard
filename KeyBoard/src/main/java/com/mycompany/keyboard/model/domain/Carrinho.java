/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.keyboard.model.domain;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Tiago
 */
public class Carrinho extends EntidadeDominio{

    private List<Item> itens;
    private Cliente cliente;

    public Carrinho(List<Item> itens, Cliente cliente) {
        this.itens = itens;
        this.cliente = cliente;
    }
    
    public Carrinho() {
        this.itens = new ArrayList();
        this.cliente = new Cliente();
    }

    public List<Item> getItens() {
        return itens;
    }

    public void setItens(List<Item> itens) {
        this.itens = itens;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }    
    
}
