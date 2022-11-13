/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.keyboard.model.strategy;

/**
 *
 * @author Tiago
 */
public class Messages {
    
    public static String campoObrigatorio (String nomeCampo) { return "O campo "+nomeCampo+" é obrigatório!"; }
    public static String usuarioInvalido () { return "Usuário ou Senha Inválidos!"; }
    public static String valorMinimoCartao () { return "O valor mínimo para cada cartão é sR$ 10,00!"; }
    public static String validateUsoDeCupomDesnecessario () { return "Os cupons ultrapassam o valor da compra desnecessariamente!"; }
    public static String validateIfThePaymentIsDifferentFromThePurchaseAmount () { return "Valor total de cupons e cartões é diferente do valor da compra!"; }
    public static String cupomPromocionalInvalido () { return "Cupom promocional inválido!"; }

}
