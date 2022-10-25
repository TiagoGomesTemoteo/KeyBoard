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


    @Override
    public String processar(EntidadeDominio entidade, String operacao) {
        
        if(entidade instanceof Cliente) return validarCamposEmBrancoCliente ((Cliente)entidade, operacao);
        if(entidade instanceof Endereco) return validarCamposEmBrancoEndereco ((Endereco)entidade);
        if(entidade instanceof CartaoDeCredito) return validarCamposEmBrancoCartao ((CartaoDeCredito)entidade);
        
        return null;
    }
    
    
    public String validarCamposEmBrancoCliente(Cliente cliente, String operacao){
        
        if (cliente.getNome().equals("")) return Messages.campoObrigatorio("Nome");
        if (cliente.getGenero() == null) return Messages.campoObrigatorio("Gênero");
        if (cliente.getEmail().equals("")) return Messages.campoObrigatorio("E-mail");
        if (cliente.getDtNascimento() == null) return Messages.campoObrigatorio("Data de nascimento");
        if (cliente.getCpf().equals("")) return Messages.campoObrigatorio("CPF");
        if ((cliente.getTelefone().getDdd() + cliente.getTelefone().getNumero()).equals("")) return Messages.campoObrigatorio("Telefone");
        if (cliente.getSenha().equals("")) return Messages.campoObrigatorio("Senha");
        if (cliente.getConfirme_senha().equals("")) return Messages.campoObrigatorio("Confirme a senha");
        
        if (operacao.equals("SALVAR")) return validarCamposEmBrancoEndereco(cliente.getEnderecos().get(0));
        
        return null;
    }
    public String validarCamposEmBrancoEndereco(Endereco endereco){
        
        if(endereco.getTipoResidencia().equals("")) return Messages.campoObrigatorio("Tipo de residência");
        if(endereco.getCep() == 0) return Messages.campoObrigatorio("Cep");
        if(endereco.getNumero().equals("")) return Messages.campoObrigatorio("Número");
        if(endereco.getIdentificacao().equals("")) return Messages.campoObrigatorio("Identidade do endereço");
        if(endereco.getTipoLogradouro().equals("")) return Messages.campoObrigatorio("Tipo de logradouro");
        if(endereco.getLogradouro().equals("")) return Messages.campoObrigatorio("Logradouro");
        if(endereco.getBairro().equals("")) return Messages.campoObrigatorio("Bairro");
        if(endereco.getCidade().equals("")) return Messages.campoObrigatorio("Cidade");
        if(endereco.getEstado().equals("")) return Messages.campoObrigatorio("Estado");
        if(endereco.getPais().equals("")) return Messages.campoObrigatorio("País");
        
        return null;
    }
    public String validarCamposEmBrancoCartao(CartaoDeCredito cartao){
        
        if(cartao.getNumero() == 0) return Messages.campoObrigatorio("Número");
        if(cartao.getNomeImpressoNoCartao().equals("")) return Messages.campoObrigatorio("Nome impresso no cartão");
        if(cartao.getBandeira().toString().equals("")) return Messages.campoObrigatorio("Bandeira");
        if(cartao.getCodSeguranca() == 0) return Messages.campoObrigatorio("Código de segurança");
         
        return null;
    }
    
}
