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
public class Teclado extends EntidadeDominio{
    
    private String marca;
    private String modelo;
    private int qtd_teclas;
    private int polifonia_max;
    private double peso;
    private double altura;
    private double largura;
    private double comprimento;
    private String cor;
    private String voltagem;
    private boolean ativo;
    private double grupo_precificacao;
    private double valor_venda;
    private double valor_custo;
    private int qtd_bloqueada;
    private int qtd_disponivel;

    public Teclado(String marca, String modelo, int qtd_teclas, int polifonia_max, double peso, 
            double altura, double largura, double comprimento, String cor, String voltagem, boolean ativo, 
            double grupo_precificacao, double valor_venda, double valor_custo, int id, Date dt_cadastro,
            int qtd_bloqueada, int qtd_disponivel) {
        super(id, dt_cadastro);
        this.marca = marca;
        this.modelo = modelo;
        this.qtd_teclas = qtd_teclas;
        this.polifonia_max = polifonia_max;
        this.peso = peso;
        this.altura = altura;
        this.largura = largura;
        this.comprimento = comprimento;
        this.cor = cor;
        this.voltagem = voltagem;
        this.ativo = ativo;
        this.grupo_precificacao = grupo_precificacao;
        this.valor_venda = valor_venda;
        this.valor_custo = valor_custo;
        this.qtd_bloqueada = qtd_bloqueada;
        this.qtd_disponivel = qtd_disponivel;
    }
    
    public Teclado() {
        this.marca = "";
        this.modelo = "";
        this.qtd_teclas = 0;
        this.polifonia_max = 0;
        this.peso = 0.0;
        this.altura = 0.0;
        this.largura = 0.0;
        this.comprimento = 0.0;
        this.cor = "";
        this.voltagem = "";
        this.ativo = false;
        this.grupo_precificacao = 0.0;
        this.valor_venda = 0.0;
        this.valor_custo = 0.0;
        this.qtd_bloqueada = 0;
        this.qtd_disponivel = 0;
    }

    public String getMarca() {
        return marca;
    }

    public void setMarca(String marca) {
        this.marca = marca;
    }

    public String getModelo() {
        return modelo;
    }

    public void setModelo(String modelo) {
        this.modelo = modelo;
    }

    public int getQtd_teclas() {
        return qtd_teclas;
    }

    public void setQtd_teclas(int qtd_teclas) {
        this.qtd_teclas = qtd_teclas;
    }

    public int getPolifonia_max() {
        return polifonia_max;
    }

    public void setPolifonia_max(int polifonia_max) {
        this.polifonia_max = polifonia_max;
    }

    public double getPeso() {
        return peso;
    }

    public void setPeso(double peso) {
        this.peso = peso;
    }

    public double getAltura() {
        return altura;
    }

    public void setAltura(double altura) {
        this.altura = altura;
    }

    public double getLargura() {
        return largura;
    }

    public void setLargura(double largura) {
        this.largura = largura;
    }

    public double getComprimento() {
        return comprimento;
    }

    public void setComprimento(double comprimento) {
        this.comprimento = comprimento;
    }

    public String getCor() {
        return cor;
    }

    public void setCor(String cor) {
        this.cor = cor;
    }

    public String getVoltagem() {
        return voltagem;
    }

    public void setVoltagem(String voltagem) {
        this.voltagem = voltagem;
    }

    public boolean isAtivo() {
        return ativo;
    }

    public void setAtivo(boolean ativo) {
        this.ativo = ativo;
    }

    public double getGrupo_precificacao() {
        return grupo_precificacao;
    }

    public void setGrupo_precificacao(double grupo_precificacao) {
        this.grupo_precificacao = grupo_precificacao;
    }

    public double getValor_venda() {
        return valor_venda;
    }

    public void setValor_venda(double valor_venda) {
        this.valor_venda = valor_venda;
    }

    public double getValor_custo() {
        return valor_custo;
    }

    public void setValor_custo(double valor_custo) {
        this.valor_custo = valor_custo;
    }

    public int getQtd_bloqueada() {
        return qtd_bloqueada;
    }

    public void setQtd_bloqueada(int qtd_bloqueada) {
        this.qtd_bloqueada = qtd_bloqueada;
    }

    public int getQtd_disponivel() {
        return qtd_disponivel;
    }

    public void setQtd_disponivel(int qtd_disponivel) {
        this.qtd_disponivel = qtd_disponivel;
    }
    
   
}
