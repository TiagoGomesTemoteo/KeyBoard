/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.keyboard.model.dao;

import com.mycompany.keyboard.model.domain.EntidadeDominio;
import com.mycompany.keyboard.model.domain.Item;
import com.mycompany.keyboard.model.domain.Pedido;
import com.mycompany.keyboard.model.domain.Teclado;
import com.mycompany.keyboard.model.domain.Troca;
import com.mycompany.keyboard.model.domain.enums.Estatus;
import com.mycompany.keyboard.util.ConnectionFactory;
import com.mycompany.keyboard.util.ParameterParser;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Tiago
 */
public class TrocaDAO extends AbstractDAO{
    
    private Connection conn;
    private PedidoDAO pedidoDAO;

    public TrocaDAO(Connection conn) {
        this.conn = conn;
    }

    public TrocaDAO() {}
            
    @Override
    public void salvar(EntidadeDominio entidade) {
        Troca troca = (Troca) entidade;

        String sqlTroca = "INSERT INTO TROCA (tro_id, tro_stt_id, tro_ped_id, tro_cli_id, tro_dt)"
                + " VALUES(tro_id, ?, ?, ?, now())";
        
        String sqlItensTroca = "INSERT INTO ITENS_TROCA (itt_id, itt_tro_id, itt_tec_id, itt_quantidade)"
                + " VALUES(itt_id, ?, ?, ?)";
        

        PreparedStatement stmt = null;
        ResultSet rs = null;

        try {

            this.conn = ConnectionFactory.getConnection();
            conn.setAutoCommit(false);
            
                        
            stmt = conn.prepareStatement(sqlTroca, Statement.RETURN_GENERATED_KEYS);
            
            stmt.setInt(1, UtilsDAO.consultarIdStatusByCod(troca.getEstatus().getEstatus(), conn));
            stmt.setInt(2, troca.getPedidoOrigem().getId());
            stmt.setInt(3, troca.getCliente().getId());
            
            stmt.executeUpdate();

            rs = stmt.getGeneratedKeys();
            if (rs.next()) troca.setId(rs.getInt(1));
            
            stmt = conn.prepareStatement(sqlItensTroca);
            
            for (Item item : troca.getProdutos()) {                
                stmt.setInt(1, troca.getId());
                stmt.setInt(2, item.getTeclado().getId());
                stmt.setInt(3, item.getQuantidade());
                
                stmt.executeUpdate();
            }
                       
            conn.commit();

        } catch (Exception ex) {
            try {
                conn.rollback();
            } catch (SQLException e1) {
                System.out.println("Error: "+ e1.getMessage());
            }
            
            System.out.println("Não foi possível salvar o pedido de troca no banco de dados.\nErro: " + ex.getMessage());

        } finally {
            ConnectionFactory.closeConnection(conn, stmt, rs);
        }
        
    }

    @Override
    public void alterar(EntidadeDominio entidade) {
        Troca troca = (Troca) entidade;
        
        String sql = "UPDATE TROCA SET tro_stt_id = ? WHERE tro_id = ?";
        
        PreparedStatement stmt = null;
        
        try{
            this.conn = ConnectionFactory.getConnection();
            conn.setAutoCommit(false);  
            
            stmt = conn.prepareStatement(sql);   
            
            stmt.setInt(1, UtilsDAO.consultarIdStatusByCod(troca.getEstatus().getEstatus(), conn));
            stmt.setInt(2, troca.getId());
            
            stmt.executeUpdate();            

            conn.commit();
            
        }catch (Exception ex) {
            try {
                conn.rollback();
            } catch (SQLException e1) {
                System.out.println("Error: "+ e1.getMessage());
            }
            
            System.out.println("Não foi possível alterar a troca no banco de dados.\nErro: " + ex.getMessage());

        } finally {
            ConnectionFactory.closeConnection(conn, stmt);
        }
    }

    @Override
    public void deletar(EntidadeDominio entidade) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List consultar(EntidadeDominio entidade) {
        Troca troca = (Troca) entidade;
        
        String sql = "SELECT * FROM TROCA WHERE tro_cli_id=?;";
        String sqlAllTrocas = "SELECT * FROM TROCA;";
        String sqlItensTroca = "SELECT * FROM ITENS_TROCA WHERE itt_tro_id=?;";
        
        PreparedStatement stmt = null;
        ResultSet rs = null;
        ResultSet rsItensTroca = null;
        
        List<Troca> pedidosTroca = new ArrayList();
        Item item;
        
        try{
            this.conn = ConnectionFactory.getConnection();
            
            if(troca.getCliente().getId() != 0) {
                stmt = conn.prepareStatement(sql);                       
                stmt.setInt(1, troca.getCliente().getId());            
            
            } else {
                stmt = conn.prepareStatement(sqlAllTrocas);                                       
            }
            
            rs = stmt.executeQuery();
            
            pedidoDAO = new PedidoDAO(conn);
                     
            while(rs.next()){
                
                troca = new Troca(); 
                
                troca.setId(rs.getInt("tro_id"));
                troca.setEstatus(Estatus.pegaEstatusPorValor(UtilsDAO.consultaEstatus(rs.getInt("tro_stt_id"), conn)));
                troca.setPedidoOrigem((Pedido)pedidoDAO.consultar((rs.getInt("tro_ped_id"))));
                troca.getCliente().setId(rs.getInt("tro_cli_id"));
                troca.setDt_cadastro(ParameterParser.sqlDateToUtilDate(rs.getDate("tro_dt")));
                
                stmt = conn.prepareStatement(sqlItensTroca);
                stmt.setInt(1, troca.getId());
                rsItensTroca = stmt.executeQuery();
                
                while(rsItensTroca.next()) {
                    item = new Item();
                    
                    item.setQuantidade(rsItensTroca.getInt("itt_quantidade"));
                    item.setTeclado((Teclado)new TecladoDAO().consultar(rsItensTroca.getInt("itt_tec_id")));
                    item.setNewInTheCar(false);
                    
                    troca.getProdutos().add(item);
                }
                
                pedidosTroca.add(troca);
                                               
            }
                       
            return pedidosTroca;
            
        }catch(SQLException ex){
            System.out.println("Não foi possível consultar os pedidos de troca no banco de dados \nErro:" + ex.getMessage());
        }finally{
            ConnectionFactory.closeConnection(conn, stmt, rs);
        }
        return null; 
       
    }

    @Override
    public EntidadeDominio consultar(int id) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }  
   
}
