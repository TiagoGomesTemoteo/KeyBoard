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
public class Endereco extends EntidadeDominio {

    private int cep;
    private String tipoResidencia;
    private String tipoLogradouro;
    private String logradouro;
    private String numero;
    private String observacoes;
    private String identificacao;
    private String bairro;
    private String cidade;
    private String estado;
    private String pais;
    private boolean EnderecoCobranca;
    private boolean EnderecoEntrega;
    private boolean EnderecoResidencial;
    private Cliente cliente;

    public Endereco(int cep, String tipoResidencia, String tipoLogradouro, String logradouro, String numero,
            String observacoes, String identificacao, boolean EnderecoCobranca,
            boolean EnderecoEntrega, boolean EnderecoResidencial, int id, Date dt_cadastro,
            Cliente cliente, String bairro, String cidade, String estado, String pais) {
        super(id, dt_cadastro);
        this.cep = cep;
        this.tipoResidencia = tipoResidencia;
        this.tipoLogradouro = tipoLogradouro;
        this.logradouro = logradouro;
        this.numero = numero;
        this.observacoes = observacoes;
        this.identificacao = identificacao;
        this.EnderecoCobranca = EnderecoCobranca;
        this.EnderecoEntrega = EnderecoEntrega;
        this.EnderecoResidencial = EnderecoResidencial;
        this.cliente = cliente;
        this.bairro = bairro;
        this.cidade = cidade;
        this.estado = estado;
        this.pais = pais;
    }

    public Endereco() {
        this.cep = 0;
        this.tipoResidencia = "";
        this.tipoLogradouro = "";
        this.logradouro = "";
        this.numero = "";
        this.observacoes = "";
        this.identificacao = "";
        this.EnderecoCobranca = false;
        this.EnderecoEntrega = false;
        this.EnderecoResidencial = false;
        this.cliente = new Cliente();
        this.bairro = "";
        this.cidade = "";
        this.estado = "";
        this.pais = "";
    }

    public int getCep() {
        return cep;
    }

    public void setCep(int cep) {
        this.cep = cep;
    }

    public String getTipoResidencia() {
        return tipoResidencia;
    }

    public void setTipoResidencia(String tipoResidencia) {
        this.tipoResidencia = tipoResidencia;
    }

    public String getTipoLogradouro() {
        return tipoLogradouro;
    }

    public void setTipoLogradouro(String tipoLogradouro) {
        this.tipoLogradouro = tipoLogradouro;
    }

    public String getLogradouro() {
        return logradouro;
    }

    public void setLogradouro(String logradouro) {
        this.logradouro = logradouro;
    }

    public String getNumero() {
        return numero;
    }

    public void setNumero(String numero) {
        this.numero = numero;
    }

    public String getObservacoes() {
        return observacoes;
    }

    public void setObservacoes(String observacoes) {
        this.observacoes = observacoes;
    }

    public String getIdentificacao() {
        return identificacao;
    }

    public void setIdentificacao(String identificacao) {
        this.identificacao = identificacao;
    }

    public boolean isEnderecoCobranca() {
        return EnderecoCobranca;
    }

    public void setEnderecoCobranca(boolean EnderecoCobranca) {
        this.EnderecoCobranca = EnderecoCobranca;
    }

    public boolean isEnderecoEntrega() {
        return EnderecoEntrega;
    }

    public void setEnderecoEntrega(boolean EnderecoEntrega) {
        this.EnderecoEntrega = EnderecoEntrega;
    }

    public boolean isEnderecoResidencial() {
        return EnderecoResidencial;
    }

    public void setEnderecoResidencial(boolean EnderecoResidencial) {
        this.EnderecoResidencial = EnderecoResidencial;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    public String getBairro() {
        return bairro;
    }

    public void setBairro(String bairro) {
        this.bairro = bairro;
    }

    public String getCidade() {
        return cidade;
    }

    public void setCidade(String cidade) {
        this.cidade = cidade;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public String getPais() {
        return pais;
    }

    public void setPais(String pais) {
        this.pais = pais;
    }

}
