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
public class EntidadeDominio {

    private int id;
    private Date dt_cadastro;

    public EntidadeDominio() {
        this.id = 0;
        this.dt_cadastro = new Date();
    }

    public EntidadeDominio(int id, Date dt_cadastro) {
        this.id = id;
        this.dt_cadastro = dt_cadastro;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Date getDt_cadastro() {
        return dt_cadastro;
    }

    public void setDt_cadastro(Date dt_cadastro) {
        this.dt_cadastro = dt_cadastro;
    }

}
