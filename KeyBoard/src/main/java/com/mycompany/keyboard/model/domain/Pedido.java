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
public class Pedido extends EntidadeDominio {

    private Cliente cliente;
    private Endereco endereco;
    private List<Item> itens;
    private double valor_total;
    private Estatus estatus;

    public Pedido(Cliente cliente, Endereco endereco, List<Item> itens, double valor_total, int id, Date dt_cadastro,
            Estatus estatus) {
        super(id, dt_cadastro);
        this.cliente = cliente;
        this.endereco = endereco;
        this.itens = itens;
        this.valor_total = valor_total;
        this.estatus = estatus;
    }

    public Pedido() {
        this.cliente = new Cliente();
        this.endereco = new Endereco();
        this.itens = new ArrayList();
        this.valor_total = 0.0;
        this.estatus = Estatus.EM_ANALISE;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    public Endereco getEndereco() {
        return endereco;
    }

    public void setEndereco(Endereco endereco) {
        this.endereco = endereco;
    }

    public List<Item> getItens() {
        return itens;
    }

    public void setItens(List<Item> itens) {
        this.itens = itens;
    }

    public double getValor_total() {
        return valor_total;
    }

    public void setValor_total(double valor_total) {
        this.valor_total = valor_total;
    }

    public Estatus getEstatus() {
        return estatus;
    }

    public void setEstatus(Estatus estatus) {
        this.estatus = estatus;
    }

}
