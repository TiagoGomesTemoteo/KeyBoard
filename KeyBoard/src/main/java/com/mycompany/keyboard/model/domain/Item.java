/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.keyboard.model.domain;

/**
 *
 * @author Tiago
 */
public class Item extends EntidadeDominio{
    private Teclado teclado;
    private int quantidade;
    private boolean newInTheCar;

    public Item(Teclado teclado, int quantidade, boolean newInTheCar) {
        this.teclado = teclado;
        this.quantidade = quantidade;
        this.newInTheCar = true;
    }
    
    public Item() {
        this.teclado = new Teclado();
        this.quantidade = 0;
        this.newInTheCar = true;
    }

    public Teclado getTeclado() {
        return teclado;
    }

    public void setTeclado(Teclado teclado) {
        this.teclado = teclado;
    }

    public int getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(int quantidade) {
        this.quantidade = quantidade;
    }

    public boolean isNewInTheCar() {
        return newInTheCar;
    }

    public void setNewInTheCar(boolean newInTheCar) {
        this.newInTheCar = newInTheCar;
    } 
}
