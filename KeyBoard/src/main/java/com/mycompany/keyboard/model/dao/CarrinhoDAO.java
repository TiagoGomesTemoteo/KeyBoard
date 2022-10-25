/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.keyboard.model.dao;

import com.mycompany.keyboard.model.domain.Carrinho;
import com.mycompany.keyboard.model.domain.EntidadeDominio;
import com.mycompany.keyboard.model.domain.Item;
import com.mycompany.keyboard.model.domain.Teclado;
import com.mycompany.keyboard.util.ConnectionFactory;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Tiago
 */
public class CarrinhoDAO extends AbstractDAO{
    
    private Connection conn;
    
    public CarrinhoDAO(Connection conn) {
        this.conn = conn;
    }

    public CarrinhoDAO() {
    }
    
    
    @Override
    public void salvar(EntidadeDominio entidade) {
        Carrinho carrinho = (Carrinho) entidade;

        String sqlAddNew = "INSERT INTO CARRINHOS (crr_id, crr_qtd_itens, crr_tec_id, crr_cli_id)"
                + " VALUES(crr_id, ?,?,?)";
        
        String sqlAddExists = "UPDATE CARRINHOS SET crr_qtd_itens =? "
                + " WHERE crr_cli_id=? AND crr_tec_id=?;";

        PreparedStatement stmt = null;
        
        try {
            
            this.conn = ConnectionFactory.getConnection();
            
            Carrinho carrinhoPersistido = (Carrinho) consultar(carrinho.getCliente().getId());

            this.conn.setAutoCommit(false);
            
            for (Item item : carrinho.getItens()) {
                for (Item itemPersistido : carrinhoPersistido.getItens()) {
                    
                    if (item.getTeclado().getId() == itemPersistido.getTeclado().getId()) {
                        
                        stmt = conn.prepareStatement(sqlAddExists);
                        item.setNewInTheCar(false);
                        
                        /*Lógica para remover itens do carrinho quando quantidade for 0*/
                        if(itemPersistido.getQuantidade() == 0 ||
                          (itemPersistido.getQuantidade() + item.getQuantidade() == 0)){
                            Carrinho carrinho_deletar = new Carrinho();
                            Item item_deletar = new Item();
                            item_deletar.setTeclado(item.getTeclado());
                            carrinho_deletar.getItens().add(item_deletar);
                            carrinho_deletar.setCliente(carrinho.getCliente());
                            
                            deletar(carrinho_deletar);
                            
                            atualizarQuantidadesProduto(item);
                            
                            
                        }else if (item.getQuantidade() <= itemPersistido.getTeclado().getQtd_disponivel()){
                            
                            System.out.println("Disponível: " + itemPersistido.getTeclado().getQtd_disponivel());  
                            System.out.println("Qtd add: " + item.getQuantidade());
                            
                            stmt.setInt(1, item.getQuantidade() + itemPersistido.getQuantidade());
                            stmt.setInt(2, carrinho.getCliente().getId());
                            stmt.setInt(3, item.getTeclado().getId());
                            
                            stmt.executeUpdate();                            
                            
                            atualizarQuantidadesProduto(item);
                        }

                    } 
                }
                
                if (item.isNewInTheCar()) {
                    
                    stmt = conn.prepareStatement(sqlAddNew);
                    
                    stmt.setInt(1, item.getQuantidade());
                    stmt.setInt(2, item.getTeclado().getId());
                    stmt.setInt(3, carrinho.getCliente().getId());

                    stmt.executeUpdate();
                    
                    atualizarQuantidadesProduto(item);                  
                    
                }
            }        
            
            this.conn.commit();

        } catch (Exception ex) {
            try {
                conn.rollback();
            } catch (SQLException e1) {
                System.out.println("Error: " + e1.getMessage());
            }

            System.out.println("Não foi possível adicionar os itens no banco.\nErro: " + ex.getMessage());

        } finally {
            ConnectionFactory.closeConnection(conn, stmt);
            
        }
    }
    
    @Override
    public void alterar(EntidadeDominio entidade) {}

    @Override
    public void deletar(EntidadeDominio entidade) {
        Carrinho carrinho = (Carrinho) entidade;
        Teclado teclado = carrinho.getItens().get(0).getTeclado();
        
        String sql = "DELETE FROM CARRINHOS WHERE crr_tec_id=? AND crr_cli_id=?";

        PreparedStatement stmt = null;
        
        try {
            
            if (conn == null || conn.isClosed()) {
                this.conn = ConnectionFactory.getConnection();
                this.ctrlTransacao = true;

            } else {
                this.ctrlTransacao = false;
            }
            
            this.conn.setAutoCommit(false);         
            
            
            /*Ao deletar todos os itens de um produto do carrinho a quantidade disponível e bloqueada é atualizada*/
            Carrinho car = (Carrinho) new CarrinhoDAO(conn).consultar(carrinho.getCliente().getId());            
            for (Item item_persistido : car.getItens()) {
                if (item_persistido.getTeclado().getId() == teclado.getId()) {     
                    item_persistido.setQuantidade(0 - item_persistido.getQuantidade());
                    atualizarQuantidadesProduto(item_persistido);
                }
            }
            
            stmt = conn.prepareStatement(sql);
            
            stmt.setInt(1, teclado.getId());
            stmt.setInt(2, carrinho.getCliente().getId());
            
            stmt.executeUpdate();
            
            if (ctrlTransacao) conn.commit();
            
        } catch (Exception ex) {
            try {
                conn.rollback();
            } catch (SQLException e1) {
                System.out.println("Error: " + e1.getMessage());
            }

            System.out.println("Não foi possível remover o produto do carrinho.\nErro: " + ex.getMessage());

        } finally {
            if (ctrlTransacao) {
                ConnectionFactory.closeConnection(conn, stmt);
            }
        }
    }

    @Override
    public List consultar(EntidadeDominio entidade) {
        List<Carrinho> carrinho = new ArrayList();
        carrinho.add((Carrinho)consultar(((Carrinho)entidade).getCliente().getId()));
        return carrinho;
    }

    @Override
    public EntidadeDominio consultar(int id) {
        
        Carrinho carrinho = new Carrinho();
        Item item;
        
        String sql = "SELECT * FROM CARRINHOS WHERE crr_cli_id = ?;";
        
        PreparedStatement stmt = null;
        ResultSet rs = null;
        
        try{
            if (conn == null || conn.isClosed()) {
                this.conn = ConnectionFactory.getConnection();
                this.ctrlTransacao = true;

            } else {
                this.ctrlTransacao = false;
            }
            
            stmt = conn.prepareStatement(sql);
            stmt.setInt(1, id);
            rs = stmt.executeQuery();
                       
            while(rs.next()){
                
                item = new Item();
                
                item.setNewInTheCar(false);
                item.setQuantidade(rs.getInt("crr_qtd_itens"));
                item.setTeclado((Teclado) new TecladoDAO(conn).consultar(rs.getInt("crr_tec_id")));
                
                carrinho.setId(rs.getInt("crr_id"));
                
                carrinho.getItens().add(item);
            }
            
            return carrinho;
            
        }catch(SQLException ex){
            System.out.println("Não foi possível consultar o carrinho desse cliente no banco de dados \nErro:" + ex.getMessage());
        }finally{
            if(ctrlTransacao) ConnectionFactory.closeConnection(conn, stmt, rs);
        }
        return null;
    }
    
    public void deletarCarrinho(int cliente_id) {

        String sql = "DELETE FROM CARRINHOS WHERE crr_cli_id=?";

        PreparedStatement stmt = null;
        
        try {
            
            if (conn == null || conn.isClosed()) {
                this.conn = ConnectionFactory.getConnection();
                this.ctrlTransacao = true;

            } else {
                this.ctrlTransacao = false;
            }
            
            this.conn.setAutoCommit(false);
            
            stmt = conn.prepareStatement(sql);
            
            stmt.setInt(1, cliente_id);
                        
            stmt.executeUpdate();
            
            
            if (ctrlTransacao) {
                conn.commit();
            }

        } catch (Exception ex) {
            try {
                conn.rollback();
            } catch (SQLException e1) {
                System.out.println("Error: " + e1.getMessage());
            }

            System.out.println("Não foi possível remover os produtos do carrinho.\nErro: " + ex.getMessage());

        } finally {
                if (ctrlTransacao) {
                ConnectionFactory.closeConnection(conn, stmt);
            }
        }                  
    }
    
    public void atualizarQuantidadesProduto (Item item) {
            
        /*Consultando o teclado para atualizar quantidade disponivel*/
       Teclado teclado = (Teclado) new TecladoDAO(conn).consultar(item.getTeclado().getId());

       /*Atualizando quantidade disponivel e bloqueada quado o usuário remover ou adicionar um item no carrinho*/
       teclado.setQtd_bloqueada(teclado.getQtd_bloqueada() + item.getQuantidade()); // (10) + (-2) = 8
       teclado.setQtd_disponivel(teclado.getQtd_disponivel() - item.getQuantidade()); // (8) - (-2) = 10                                       

       new TecladoDAO(conn).atualizarQtdDisponivelAndQtdBloqueada(teclado);
        }
}
