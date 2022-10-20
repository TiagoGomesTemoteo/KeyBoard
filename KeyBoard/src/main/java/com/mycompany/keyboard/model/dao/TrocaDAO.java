/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.keyboard.model.dao;

import com.mycompany.keyboard.model.domain.EntidadeDominio;
import com.mycompany.keyboard.model.domain.Item;
import com.mycompany.keyboard.model.domain.Troca;
import com.mycompany.keyboard.util.ConnectionFactory;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

/**
 *
 * @author Tiago
 */
public class TrocaDAO extends AbstractDAO{
    
    private Connection conn;

    public TrocaDAO(Connection conn) {
        this.conn = conn;
    }

    public TrocaDAO() {}
            
    @Override
    public void salvar(EntidadeDominio entidade) {
        Troca troca = (Troca) entidade;

        String sqlTroca = "INSERT INTO TROCA (tro_id, tro_stt_id, tro_ped_id)"
                + " VALUES(tro_id, ?, ?)";
        
        String sqlItensTroca = "INSERT INTO ITENS_TROCA (itt_id, itt_tro_id, itt_tec_id, itt_quantidade)"
                + " VALUES(itt_id, ?, ?, ?)";
        

        PreparedStatement stmt = null;
        ResultSet rs = null;

        try {

            this.conn = ConnectionFactory.getConnection();
            conn.setAutoCommit(false);
            
                        
            stmt = conn.prepareStatement(sqlTroca, Statement.RETURN_GENERATED_KEYS);
            
            stmt.setInt(1, troca.getEstatus().getEstatus());
            stmt.setInt(2, troca.getPedidoOrigem().getId());
            
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
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void deletar(EntidadeDominio entidade) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List consultar(EntidadeDominio entidade) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates. 
    }

    @Override
    public EntidadeDominio consultar(int id) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }   
}
