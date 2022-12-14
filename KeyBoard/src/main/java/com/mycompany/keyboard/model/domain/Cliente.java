/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.keyboard.model.domain;

import com.mycompany.keyboard.model.domain.enums.Genero;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 *
 * @author Tiago
 */
public class Cliente extends EntidadeDominio {

    private String nome;
    private Genero genero;
    private String email;
    private int rank;
    private String senha;
    private String confirme_senha;
    private Date dtNascimento;
    private String cpf;
    private boolean ativo;
    private List<Endereco> enderecos;
    private List<CartaoDeCredito> cartoesDeCredito;
    private List<CupomDeTroca> cuponsDeTroca;
    private List<CupomPromocional> cuponsPromocionais;
    private Telefone telefone;
    private int nivel_acesso;

    public Cliente(String nome, Genero genero, String email, int rank, String senha, Date dtNascimento, 
            String cpf, boolean ativo, List<Endereco> enderecos, List<CartaoDeCredito> cartoesDeCredito, 
            Telefone telefone, int id, Date dt_cadastro,
            List<CupomDeTroca> cuponsDeTroca, int nivel_acesso, List<CupomPromocional> cuponsPromocionais) {
        super(id, dt_cadastro);
        this.nome = nome;
        this.genero = genero;
        this.email = email;
        this.rank = rank;
        this.senha = senha;
        this.dtNascimento = dtNascimento;
        this.cpf = cpf;
        this.ativo = ativo;
        this.enderecos = enderecos;
        this.cartoesDeCredito = cartoesDeCredito;
        this.telefone = telefone;
        this.cuponsDeTroca = cuponsDeTroca;
        this.nivel_acesso = nivel_acesso;
        this.cuponsPromocionais = cuponsPromocionais;
    }

    public Cliente() {
        super();
        this.nome = "";
        this.genero = null;
        this.email = "";
        this.rank = 0;
        this.senha = "";
        this.confirme_senha = "";
        this.dtNascimento = new Date();
        this.cpf = "";
        this.ativo = false;
        this.enderecos = new ArrayList();
        this.cartoesDeCredito = new ArrayList();
        this.telefone = new Telefone();
        this.cuponsDeTroca = new ArrayList();
        this.nivel_acesso = 1;
        this.cuponsPromocionais = new ArrayList();
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public Genero getGenero() {
        return genero;
    }

    public void setGenero(Genero genero) {
        this.genero = genero;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getRank() {
        return rank;
    }

    public void setRank(int rank) {
        this.rank = rank;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public Date getDtNascimento() {
        return dtNascimento;
    }

    public void setDtNascimento(Date dtNascimento) {
        this.dtNascimento = dtNascimento;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public boolean isAtivo() {
        return ativo;
    }

    public void setAtivo(boolean ativo) {
        this.ativo = ativo;
    }

    public List<Endereco> getEnderecos() {
        return enderecos;
    }

    public void setEnderecos(List<Endereco> enderecos) {
        this.enderecos = enderecos;
    }

    public List<CartaoDeCredito> getCartoesDeCredito() {
        return cartoesDeCredito;
    }

    public void setCartoesDeCredito(List<CartaoDeCredito> cartoesDeCredito) {
        this.cartoesDeCredito = cartoesDeCredito;
    }

    public Telefone getTelefone() {
        return telefone;
    }

    public void setTelefone(Telefone telefone) {
        this.telefone = telefone;
    }

    public List<CupomDeTroca> getCuponsDeTroca() {
        return cuponsDeTroca;
    }

    public void setCuponsDeTroca(List<CupomDeTroca> cuponsDeTroca) {
        this.cuponsDeTroca = cuponsDeTroca;
    }    

    public int getNivel_acesso() {
        return nivel_acesso;
    }

    public void setNivel_acesso(int nivel_acesso) {
        this.nivel_acesso = nivel_acesso;
    }

    public String getConfirme_senha() {
        return confirme_senha;
    }

    public void setConfirme_senha(String confirme_senha) {
        this.confirme_senha = confirme_senha;
    }

    public List<CupomPromocional> getCuponsPromocionais() {
        return cuponsPromocionais;
    }

    public void setCuponsPromocionais(List<CupomPromocional> cuponsPromocionais) {
        this.cuponsPromocionais = cuponsPromocionais;
    }
         
}
