/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.keyboard.model.domain;

import com.mycompany.keyboard.model.domain.enums.Estatus;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 *
 * @author Tiago
 */
public class Troca extends EntidadeDominio{
    private Cliente cliente;
    private Pedido pedidoOrigem;
    private Estatus estatus;
    private List<Item> produtos;

    public Troca(Cliente cliente, Pedido pedidoOrigem, Estatus estatus, List<Item> produtos) {
        this.cliente = cliente;
        this.pedidoOrigem = pedidoOrigem;
        this.estatus = estatus;
        this.produtos = produtos;
    }

    public Troca(Cliente cliente, Pedido pedidoOrigem, Estatus estatus, List<Item> produtos, int id, Date dt_cadastro) {
        super(id, dt_cadastro);
        this.cliente = cliente;
        this.pedidoOrigem = pedidoOrigem;
        this.estatus = estatus;
        this.produtos = produtos;
    }
   
    public Troca() {
        super();
        this.cliente = new Cliente();
        this.pedidoOrigem = new Pedido();
        this.estatus = Estatus.EM_TROCA;
        this.produtos = new ArrayList();
    }

    public Pedido getPedidoOrigem() {
        return pedidoOrigem;
    }

    public void setPedidoOrigem(Pedido pedidoOrigem) {
        this.pedidoOrigem = pedidoOrigem;
    }

    public Estatus getEstatus() {
        return estatus;
    }

    public void setEstatus(Estatus estatus) {
        this.estatus = estatus;
    }

    public List<Item> getProdutos() {
        return produtos;
    }

    public void setProdutos(List<Item> produtos) {
        this.produtos = produtos;
    }    

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }
   
}
