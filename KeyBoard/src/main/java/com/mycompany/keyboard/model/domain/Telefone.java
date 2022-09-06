/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.keyboard.model.domain;

import com.mycompany.keyboard.model.domain.enums.TelefoneENUM;
import java.util.Date;

/**
 *
 * @author Tiago
 */
public class Telefone extends EntidadeDominio {

    private TelefoneENUM tipo;
    private String ddd;
    private String numero;

    public Telefone(TelefoneENUM tipo, String ddd, String numero, int id, Date dt_cadastro) {
        super(id, dt_cadastro);
        this.tipo = tipo;
        this.ddd = ddd;
        this.numero = numero;
    }

    public Telefone(TelefoneENUM tipo, String ddd, String numero) {
        this.tipo = tipo;
        this.ddd = ddd;
        this.numero = numero;
    }

    public Telefone() {
        this.tipo = TelefoneENUM.CELULAR;
        this.ddd = "";
        this.numero = "";
    }

    public TelefoneENUM getTipo() {
        return tipo;
    }

    public void setTipo(TelefoneENUM tipo) {
        this.tipo = tipo;
    }

    public String getDdd() {
        return ddd;
    }

    public void setDdd(String ddd) {
        this.ddd = ddd;
    }

    public String getNumero() {
        return numero;
    }

    public void setNumero(String numero) {
        this.numero = numero;
    }

}
