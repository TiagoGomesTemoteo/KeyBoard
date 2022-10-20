/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.keyboard.control;

import com.mycompany.keyboard.model.dao.*;
import com.mycompany.keyboard.model.domain.*;
import com.mycompany.keyboard.model.strategy.*;
import com.mycompany.keyboard.util.Resultado;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 * @author Tiago
 */
public class Facade implements IFacade {

    private Map<String, IDAO> daos;
    private Map<String, Map<String, List<IStrategy>>> rns;
    private Resultado resultado;

    public Facade() {
        daos = new HashMap<>();
        daos.put(Cliente.class.getName(), new ClienteDAO());
        daos.put(Teclado.class.getName(), new TecladoDAO());
        daos.put(Carrinho.class.getName(), new CarrinhoDAO());
        daos.put(Endereco.class.getName(), new EnderecoDAO());
        daos.put(Pedido.class.getName(), new PedidoDAO());
        daos.put(CartaoDeCredito.class.getName(), new CartaoDAO());
        daos.put(Troca.class.getName(), new TrocaDAO());
        
        initRns();
    }
    
    public void initRns(){
        
        
        rns = new HashMap<> (); // Inicializando map de Regras de negócio
        
        Map<String, List<IStrategy>> rns_cliente = new HashMap<>(); // Criando map das RG de todas as operações de um cliente
        Map<String, List<IStrategy>> rns_pedido = new HashMap<>();
        
        List<IStrategy> rns_salvar_pedido = new ArrayList<>();       
        List<IStrategy> rns_alterar_pedido = new ArrayList<>();
        List<IStrategy> rns_deletar_pedido = new ArrayList<>();
        List<IStrategy> rns_consultar_pedido = new ArrayList<>();
        
        List<IStrategy> rns_salvar_cliente = new ArrayList<>(); // Criando map das RG para salvar um cliente      
        List<IStrategy> rns_alterar_cliente = new ArrayList<>();
        List<IStrategy> rns_deletar_cliente = new ArrayList<>();
        List<IStrategy> rns_consultar_cliente = new ArrayList<>();
        
        rns_salvar_cliente.add(new ValidarCamposObrigatorios()); // Adicionando validação para salvar um cliente
        rns_salvar_cliente.add(new VerificarCamposInvalidos());
        
        rns_salvar_pedido.add(new CalcularValorTotalDoPedido());
        
        rns_cliente.put("SALVAR", rns_salvar_cliente); // Adicionar ao map uma operação e um map com várias validações
        rns_cliente.put("ALTERAR", rns_alterar_cliente);
        rns_cliente.put("DELETAR", rns_deletar_pedido);
        rns_cliente.put("CONSULTAR", rns_consultar_pedido);
        
        rns_pedido.put("SALVAR", rns_salvar_pedido); 
        rns_pedido.put("ALTERAR", rns_alterar_pedido);
        rns_pedido.put("DELETAR", rns_deletar_cliente);
        rns_pedido.put("CONSULTAR", rns_consultar_cliente);
        
        rns.put(Cliente.class.getName(), rns_cliente);
        rns.put(Pedido.class.getName(), rns_pedido);
    }

    private String aplicarRegras(EntidadeDominio entidade, String operacao){
        StringBuilder msg = new StringBuilder();
        
        Map <String, List<IStrategy>> regrasNegocio = rns.get(entidade.getClass().getName());
        
        if(regrasNegocio != null){
            List<IStrategy> regrasOperacao = regrasNegocio.get(operacao);
            if(regrasOperacao != null){
                for(IStrategy regras : regrasOperacao){
                    String mensagem = regras.processar(entidade, operacao);
                    if(mensagem != null){
                        msg.append(mensagem);
                        msg.append("\n");
                    }
                }
            }
        }
        if(msg.length() > 0){
            return msg.toString();
        }else{
            return null;
        }
    }

    @Override
    public Resultado salvar(EntidadeDominio entidade) {
        resultado = new Resultado();
        String nmClass = entidade.getClass().getName();
       
        String msg = aplicarRegras(entidade, "SALVAR");
        
        if(msg == null){
            IDAO dao = daos.get(nmClass);
            try{
                dao.salvar(entidade); 
                List<EntidadeDominio> entidades = new ArrayList<EntidadeDominio>();
                entidades.add(entidade);
                resultado.setEntidades(entidades);
            }catch(Exception ex){
                ex.printStackTrace();
                resultado.setMsg("Não foi possível salvar o(a)" + nmClass);
            }   
        }else{
            resultado.setMsg(msg);
        }     
        return resultado;
    }

    @Override
    public Resultado alterar(EntidadeDominio entidade) {
        resultado = new Resultado();
        String nmClass = entidade.getClass().getName();
           
        String msg = aplicarRegras(entidade, "ALTERAR");

        if(msg == null){
            IDAO dao = daos.get(nmClass);
            try{
                dao.alterar(entidade);
                List<EntidadeDominio> entidades = new ArrayList<EntidadeDominio>();
                entidades.add(entidade);
                resultado.setEntidades(entidades);
                
            }catch(Exception ex){
                ex.printStackTrace();
                resultado.setMsg("Não foi possível alterar o(a)" + nmClass);
            }   
        }     
        return resultado;
    }

    @Override
    public Resultado deletar(EntidadeDominio entidade) {
        resultado = new Resultado();
        String nmClass = entidade.getClass().getName();
           
        String msg = aplicarRegras(entidade, "EXCLUIR");

        if(msg == null){
            IDAO dao = daos.get(nmClass);
            try{
                dao.deletar(entidade);
                List<EntidadeDominio> entidades = new ArrayList<EntidadeDominio>();
                entidades.add(entidade);
                resultado.setEntidades(entidades);
                
            }catch(Exception ex){
                ex.printStackTrace();
                resultado.setMsg("Não foi possível excluir o(a)" + nmClass);
            }   
        }     
        return resultado;
    }

    @Override
    public Resultado consultar(EntidadeDominio entidade) {
       resultado = new Resultado();
        String nmClass = entidade.getClass().getName();
           
        String msg = aplicarRegras(entidade, "CONSULTAR");

        if(msg == null){
            IDAO dao = daos.get(nmClass);
            try{
                resultado.setEntidades(dao.consultar(entidade));
                
            }catch(Exception ex){
                ex.printStackTrace();
                resultado.setMsg("Não foi possível consultar o(a)" + nmClass);
            }   
        }     
        return resultado;
    }
    
    @Override
    public Resultado visualizar(EntidadeDominio entidade){
        resultado = new Resultado();
        resultado.setEntidades(new ArrayList<EntidadeDominio>(1));
        resultado.getEntidades().add(entidade);
        return resultado;
    }

    @Override
    public Resultado acessar(EntidadeDominio ed) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
