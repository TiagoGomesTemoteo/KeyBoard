/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.keyboard.control;

import com.mycompany.keyboard.model.dao.ClienteDAO;
import com.mycompany.keyboard.model.dao.IDAO;
import com.mycompany.keyboard.model.domain.Cliente;
import com.mycompany.keyboard.model.domain.EntidadeDominio;
import com.mycompany.keyboard.model.strategy.IStrategy;
import com.mycompany.keyboard.model.strategy.ValidarCamposObrigatorios;
import com.mycompany.keyboard.util.Resultado;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.swing.JOptionPane;

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
        
        initRns();
    }
    
    public void initRns(){
        
        
        rns = new HashMap<> (); // Inicializando map de Regras de negócio
        
        List<IStrategy> rns_salvar_cliente = new ArrayList<>(); // Criando map das RG para salvar um cliente
        rns_salvar_cliente.add(new ValidarCamposObrigatorios()); // Adicionando validação para salvar um cliente
        
        List<IStrategy> rns_alterar_cliente = new ArrayList<>();
        List<IStrategy> rns_deletar_cliente = new ArrayList<>();
        List<IStrategy> rns_consultar_cliente = new ArrayList<>();
        
        
        Map<String, List<IStrategy>> rns_cliente = new HashMap<>(); // Criando map das RG de todas as operações de um cliente
        
        rns_cliente.put("SALVAR", rns_salvar_cliente); // Adicionar ao map uma operação e um map com várias validações
        rns_cliente.put("ALTERAR", rns_salvar_cliente);
        rns_cliente.put("DELETAR", rns_salvar_cliente);
        rns_cliente.put("CONSULTAR", rns_salvar_cliente);
        
        rns.put(Cliente.class.getName(), rns_cliente);
    }

    private String aplicarRegras(EntidadeDominio entidade, String operacao){
        StringBuilder msg = new StringBuilder();
        
        Map <String, List<IStrategy>> regrasNegocio = rns.get(entidade.getClass().getName());
        
        if(regrasNegocio != null){
            List<IStrategy> regrasOperacao = regrasNegocio.get(operacao);
            if(regrasOperacao != null){
                for(IStrategy regras : regrasOperacao){
                    String mensagem = regras.processar(entidade);
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
