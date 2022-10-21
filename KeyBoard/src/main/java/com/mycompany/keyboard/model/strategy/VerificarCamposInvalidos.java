/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.keyboard.model.strategy;

import com.mycompany.keyboard.model.dao.ClienteDAO;
import com.mycompany.keyboard.model.domain.Cliente;
import com.mycompany.keyboard.model.domain.EntidadeDominio;
import com.mycompany.keyboard.model.domain.Telefone;

/**
 *
 * @author Tiago
 */
public class VerificarCamposInvalidos implements IStrategy {

    ClienteDAO clienteDAO;

    String camposInvalidos = "";

    @Override
    public String processar(EntidadeDominio entidade, String string) {
        if (entidade instanceof Cliente) {
            if (!((Cliente) entidade).getCpf().equals("")) {
//                validarCPF(((Cliente) entidade).getCpf());
//                validarTelefone(((Cliente) entidade).getTelefone());
            }

        }

        return camposInvalidos.equals("") ? null : camposInvalidos;
    }

    public void validarCPF(String cpf) {
        clienteDAO = new ClienteDAO();

        if (cpf.trim().length() != 11) {
            camposInvalidos = "CPF Inválido!";

        } else if (validarCpfTodosNumerosIguais(cpf)) {
            camposInvalidos = "CPF Inválido!";

        } else if (!validarFormatoCPF(cpf)) {
            camposInvalidos = "CPF Inválido!";

        } else if (clienteDAO.existeCpf(cpf)) {
            camposInvalidos = "CPF já cadastrado";

        }
    }

    public boolean validarFormatoCPF(String cpf) {

        if (calculoDeValidacao(cpf, cpf.substring(0, 9), 10, 0, 9)) {
            return calculoDeValidacao(cpf, cpf.substring(0, 10), 11, 0, 10);
        }

        return false;
    }

    public boolean calculoDeValidacao(String cpf, String numeros, int multiplicador, int resultado, int indiceNumeroVerificador) {
        for (int i = 0; i < numeros.length(); i++) {
            resultado += Character.getNumericValue(numeros.charAt(i)) * multiplicador;
            multiplicador -= 1;
        }

        resultado = (resultado * 10) % 11;

        if (resultado == 10) resultado = 0;
            
        return resultado == Character.getNumericValue(cpf.charAt(indiceNumeroVerificador));

    }

    public boolean validarCpfTodosNumerosIguais(String cpf) {
        for (int i = 0; i < 11; i++) {
            if (cpf.charAt(i) != cpf.charAt(0)) return false; 
        }

        return true;
    }
    
    public void validarTelefone(Telefone telefone){
        
        String tel = telefone.getDdd() + telefone.getNumero();
        
        if (tel.matches("[A-Z]*")) camposInvalidos += "Telefone só pode conter números!";
        if (tel.length() != 10 || tel.length() != 11) camposInvalidos += "Telefone Inválido!";
        
    }

}
