/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.keyboard.model.dao;


import com.mycompany.keyboard.model.domain.CupomDeTroca;
import com.mycompany.keyboard.model.domain.EntidadeDominio;
import com.mycompany.keyboard.model.domain.FormasDePagamento;
import com.mycompany.keyboard.model.domain.Item;
import com.mycompany.keyboard.model.domain.Pagamento;
import com.mycompany.keyboard.model.domain.Pedido;
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
public class PedidoDAO extends AbstractDAO{

    private Connection conn = null;

    public PedidoDAO(Connection conn) {
        this.conn = conn;
    }

    public PedidoDAO() {}
       
    @Override
    public void salvar(EntidadeDominio entidade) {
        
        Pedido pedido = (Pedido) entidade;

        String sqlPedido = "INSERT INTO PEDIDOS (ped_id, ped_stt_id, ped_cli_id, ped_end_id, ped_valor_total)"
                + " VALUES(ped_id, ?,?,?,?)";
        
        String sqlPedidoProduto = "INSERT INTO PEDIDOS_PRODUTOS (pep_id, pep_ped_id, pep_tec_id, pep_quantidade)"
                + " VALUES(pep_id, ?,?,?)";
        
        String sqlPedidoPagamento = "INSERT INTO PAGAMENTOS (pag_id, pag_fpg_id, pag_valor, pag_ped_id, pag_cdt_id,"
                + " pag_car_id)"
                + " VALUES(pag_id, ?,?,?,?,?)";

        PreparedStatement stmt = null;
        ResultSet rs = null;

        try {

            this.conn = ConnectionFactory.getConnection();
            conn.setAutoCommit(false);
            
            stmt = conn.prepareStatement(sqlPedido, Statement.RETURN_GENERATED_KEYS);
            
            stmt.setInt(1, pedido.getEstatus().getEstatus());
            stmt.setInt(2, pedido.getCliente().getId());
            stmt.setInt(3, pedido.getEndereco().getId());
            stmt.setDouble(4, pedido.getValor_total());

            stmt.executeUpdate();

            rs = stmt.getGeneratedKeys();
            if (rs.next()) pedido.setId(rs.getInt(1));
            
            stmt = conn.prepareStatement(sqlPedidoProduto);
            
            for (Item item : pedido.getItens()) {
                stmt.setInt(1, pedido.getId());
                stmt.setInt(2, item.getTeclado().getId());
                stmt.setInt(3, item.getQuantidade());
                
                stmt.executeUpdate();
            }
            
            stmt = conn.prepareStatement(sqlPedidoPagamento);
            
            for (Pagamento pagamento : pedido.getPagamento()){
                if (pagamento.getForma_de_pagamento() instanceof CupomDeTroca){
                    stmt.setInt(1, 4);
                    stmt.setInt(4, pagamento.getForma_de_pagamento().getId());
                    stmt.setNull(5, 0);
                    
                } else {
                    stmt.setInt(1, 5);
                    stmt.setInt(5, pagamento.getForma_de_pagamento().getId());
                    stmt.setNull(4, 0);
                }
                
                stmt.setDouble(2, pagamento.getValor());
                stmt.setInt(3, pedido.getId());
                
                
                stmt.executeUpdate();
            }
                       
            conn.commit();

        } catch (Exception ex) {
            try {
                conn.rollback();
            } catch (SQLException e1) {
                System.out.println("Error: "+ e1.getMessage());
            }
            
            System.out.println("Não foi possível salvar o pedido no banco de dados.\nErro: " + ex.getMessage());

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
