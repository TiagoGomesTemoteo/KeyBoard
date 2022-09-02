/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.keyboard.model.domain;

import com.mycompany.keyboard.model.domain.enums.BandeiraCartao;
import java.util.Date;

/**
 *
 * @author Tiago
 */
public class CartaoDeCredito extends EntidadeDominio {

    private int numero;
    private String nomeImpressoNoCartao;
    private BandeiraCartao bandeira;
    private int codSeguranca;
    private boolean preferencial;
    private Cliente cliente;

    public CartaoDeCredito(int numero, String nomeImpressoNoCartao, BandeiraCartao bandeira,
            int codSeguranca, boolean preferencial, int id, Date dt_cadastro,
            Cliente cliente) {
        super(id, dt_cadastro);
        this.numero = numero;
        this.nomeImpressoNoCartao = nomeImpressoNoCartao;
        this.bandeira = bandeira;
        this.codSeguranca = codSeguranca;
        this.preferencial = preferencial;
        this.cliente = cliente;
    }

    public CartaoDeCredito() {
        this.numero = 0;
        this.nomeImpressoNoCartao = "";
        this.bandeira = BandeiraCartao.VISA;
        this.codSeguranca = 0;
        this.preferencial = false;
        this.cliente = new Cliente();
    }

    public int getNumero() {
        return numero;
    }

    public void setNumero(int numero) {
        this.numero = numero;
    }

    public String getNomeImpressoNoCartao() {
        return nomeImpressoNoCartao;
    }

    public void setNomeImpressoNoCartao(String nomeImpressoNoCartao) {
        this.nomeImpressoNoCartao = nomeImpressoNoCartao;
    }

    public BandeiraCartao getBandeira() {
        return bandeira;
    }

    public void setBandeira(BandeiraCartao bandeira) {
        this.bandeira = bandeira;
    }

    public int getCodSeguranca() {
        return codSeguranca;
    }

    public void setCodSeguranca(int codSeguranca) {
        this.codSeguranca = codSeguranca;
    }

    public boolean isPreferencial() {
        return preferencial;
    }

    public void setPreferencial(boolean preferencial) {
        this.preferencial = preferencial;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

}
