/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.keyboard.model.strategy;

import com.mycompany.keyboard.model.domain.CartaoDeCredito;
import com.mycompany.keyboard.model.domain.Cliente;
import com.mycompany.keyboard.model.domain.Endereco;
import com.mycompany.keyboard.model.domain.EntidadeDominio;

/**
 *
 * @author Tiago
 */
public class ValidarCamposObrigatorios implements IStrategy{

    String camposObrigatorios = "Os campos abaixo são obrigatórios: ";
    
    @Override
    public String processar(EntidadeDominio entidade) {
        
        if(entidade instanceof Cliente) return validarCamposEmBrancoCliente ((Cliente)entidade);
        if(entidade instanceof Endereco) return validarCamposEmBrancoEndereco ((Endereco)entidade);
        if(entidade instanceof CartaoDeCredito) return validarCamposEmBrancoCartao ((CartaoDeCredito)entidade);
        
        return null;
    }
    
    public String validarCamposEmBrancoCliente(Cliente cliente){
        
        if (cliente.getNome().equals("")) camposObrigatorios += "\n Nome";
        if (cliente.getGenero().toString().equals("")) camposObrigatorios += "\n Gênero";
        if (cliente.getEmail().equals("")) camposObrigatorios += "\n E-mail";
        if (cliente.getDtNascimento() == null) camposObrigatorios += "\n Data de nascimento";
        if (cliente.getCpf().equals("")) camposObrigatorios += "\n CPF";
        if ((cliente.getTelefone().getDdd() + cliente.getTelefone().getNumero()).equals("")) camposObrigatorios += "\n Telefone";
        
        return camposObrigatorios.equals("Os campos abaixo são obrigatórios: ")? null : camposObrigatorios;
    }
    public String validarCamposEmBrancoEndereco(Endereco endereco){
        
        if(endereco.getTipoResidencia().equals("")) camposObrigatorios += "Tipo de residência";
        if(endereco.getCep() == 0) camposObrigatorios += "CEP";
        if(endereco.getNumero().equals("")) camposObrigatorios += "Número";
        if(endereco.getIdentificacao().equals("")) camposObrigatorios += "Identificação do endereço";
        
        return camposObrigatorios.equals("Os campos abaixo são obrigatórios: ")? null : camposObrigatorios;
    }
    public String validarCamposEmBrancoCartao(CartaoDeCredito cartao){
        
        if(cartao.getNumero() == 0) camposObrigatorios += "Número do cartão";
        if(cartao.getNomeImpressoNoCartao().equals("")) camposObrigatorios += "Nome impresso no cartão";
        if(cartao.getBandeira().toString().equals("")) camposObrigatorios += "Bandeira";
        if(cartao.getCodSeguranca() == 0) camposObrigatorios += "Código de segurança";
         
        return camposObrigatorios.equals("Os campos abaixo são obrigatórios: ")? null : camposObrigatorios;
    }
    
}
