/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.keyboard.model.dao;

import com.mycompany.keyboard.model.domain.CartaoDeCredito;
import com.mycompany.keyboard.model.domain.EntidadeDominio;
import com.mycompany.keyboard.model.domain.enums.BandeiraCartao;
import com.mycompany.keyboard.util.ConnectionFactory;
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
public class CartaoDAO extends AbstractDAO {

    private Connection conn;

    public CartaoDAO(Connection conn) {
        this.conn = conn;
    }

    public CartaoDAO() {
    }

    @Override
    public void salvar(EntidadeDominio entidade) {
        CartaoDeCredito cartao = (CartaoDeCredito) entidade;
        int id = 0;

        String sql = "INSERT INTO CARTOES_DE_CREDITO (car_id, car_numero, car_nome_impresso_no_cartao,"
                + " car_bandeira, car_cod_seguranca, car_preferencial, car_cli_id)"
                + " VALUES(car_id, ?,?,?,?,?,?)";

        PreparedStatement stmt = null;
        ResultSet rs = null;

        try {
            if (conn == null || conn.isClosed()) {
                this.conn = ConnectionFactory.getConnection();
                this.ctrlTransacao = true;

            } else {
                this.ctrlTransacao = false;
            }

            conn.setAutoCommit(false);

            stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            stmt.setInt(1, cartao.getNumero());
            stmt.setString(2, cartao.getNomeImpressoNoCartao());
            stmt.setString(3, cartao.getBandeira().toString());
            stmt.setInt(4, cartao.getCodSeguranca());
            stmt.setBoolean(5, cartao.isPreferencial());
            stmt.setInt(6, cartao.getCliente().getId());

            stmt.executeUpdate();

            rs = stmt.getGeneratedKeys();
            if (rs.next()) {
                id = rs.getInt(1);
            }
            cartao.setId(id);

            if (ctrlTransacao) {
                conn.commit();
            }

        } catch (Exception ex) {
            try {
                conn.rollback();
            } catch (SQLException e1) {
                System.out.println("Error: " + e1.getMessage());
            }

            System.out.println("Não foi possível salvar o cartão no banco de dados.\nErro: " + ex.getMessage());

        } finally {
            if (ctrlTransacao) {
                ConnectionFactory.closeConnection(conn, stmt, rs);
            }
        }

    }

    @Override
    public void alterar(EntidadeDominio entidade) {
        CartaoDeCredito cartao = (CartaoDeCredito) entidade;

        String sql = "UPDATE CARTOES_DE_CREDITO SET car_numero=?, car_nome_impresso_no_cartao=?,"
                + " car_bandeira=?, car_cod_seguranca=?, car_preferencial=?, car_cli_id=?)"
                + " WHERE car_id=?";

        PreparedStatement stmt = null;

        try {
            if (conn == null) {
                this.conn = ConnectionFactory.getConnection();
                this.ctrlTransacao = true;

            } else {
                this.ctrlTransacao = false;
            }

            conn.setAutoCommit(false);

            stmt = conn.prepareStatement(sql);
            stmt.setInt(1, cartao.getNumero());
            stmt.setString(2, cartao.getNomeImpressoNoCartao());
            stmt.setString(3, cartao.getBandeira().toString());
            stmt.setInt(4, cartao.getCodSeguranca());
            stmt.setBoolean(5, cartao.isPreferencial());
            stmt.setInt(6, cartao.getCliente().getId());
            stmt.setInt(7, cartao.getId());

            stmt.executeUpdate();

            if (ctrlTransacao) conn.commit();
            

        } catch (Exception ex) {
            try {
                conn.rollback();
            } catch (SQLException e1) {
                System.out.println("Error: " + e1.getMessage());
            }

            System.out.println("Não foi possível salvar o cartão no banco de dados.\nErro: " + ex.getMessage());

        } finally {
            if (ctrlTransacao) ConnectionFactory.closeConnection(conn, stmt);
   
        }

    }

    @Override
    public void deletar(EntidadeDominio entidade) {
        CartaoDeCredito cartaoCredito = (CartaoDeCredito) entidade;
        
        String sql = "DELETE FROM CARTOES_DE_CREDITO WHERE car_id = ?;";
        
        PreparedStatement stmt = null;
        
        try{
            if(conn == null || this.conn.isClosed()){
                this.conn = ConnectionFactory.getConnection();
                ctrlTransacao = true; 
            }else{
                ctrlTransacao = false;
            }
            
            conn.setAutoCommit(false);

            stmt = conn.prepareStatement(sql);
            stmt.setInt(1, cartaoCredito.getId());
            
            stmt.executeUpdate();
            
            if(ctrlTransacao) conn.commit();
            
        }catch(Exception ex){
            try {
                conn.rollback();
            } catch (SQLException e1) {
                System.out.println("Error: " + e1.getMessage());
            }
            
            System.out.println("Não foi possível exclui no banco de dados" + ex.getMessage());
        
        }finally{
            if(ctrlTransacao) ConnectionFactory.closeConnection(conn, stmt);
        }      
    }

    @Override
    public List consultar(EntidadeDominio entidade) {
        CartaoDeCredito cartao;
        
        String sql = "SELECT * FROM CARTOES_DE_CREDITO WHERE car_cli_id = ?;";
        
        PreparedStatement stmt = null;
        ResultSet rs = null;
        
        List<CartaoDeCredito> cartoesCredito = new ArrayList();
        
        try{   
            if(conn == null || this.conn.isClosed()){
                this.conn = ConnectionFactory.getConnection();
                ctrlTransacao = true; 
            }else{
                ctrlTransacao = false;
            }
            
            stmt = conn.prepareStatement(sql);
            stmt.setInt(1,entidade.getId());
            rs = stmt.executeQuery();
            
            while(rs.next()){
                cartao = new CartaoDeCredito();
                
                cartao.setId(rs.getInt("car_id"));
                cartao.setNumero(rs.getInt("car_numero"));
                cartao.setNomeImpressoNoCartao(rs.getString("car_nome_impresso_no_cartao"));
                cartao.setBandeira(BandeiraCartao.valueOf(rs.getString("car_bandeira")));
                cartao.setCodSeguranca(rs.getInt("car_cod_seguranca"));
                cartao.setPreferencial(rs.getBoolean("car_preferencial"));
                cartao.getCliente().setId(rs.getInt("car_cli_id"));
                
                cartoesCredito.add(cartao);
                
            }
            
            return cartoesCredito;
        
        }catch(SQLException ex){
            System.out.println("Não foi possível consultar o cartão no banco de dados \nErro: " + ex.getMessage());
        }finally{
            if(ctrlTransacao) ConnectionFactory.closeConnection(conn, stmt, rs);
        }        
        return null;   
    }

    @Override
    public EntidadeDominio consultar(int id) {
        CartaoDeCredito cartao = new CartaoDeCredito();
        
        String sql = "SELECT * FROM CARTOES_DE_CREDITO WHERE car_id = ?";
        
        PreparedStatement stmt = null;
        ResultSet rs = null;
        
                
        try{   
            if(conn == null || this.conn.isClosed()){
                this.conn = ConnectionFactory.getConnection();
                ctrlTransacao = true; 
            }else{
                ctrlTransacao = false;
            }
            
            stmt = conn.prepareStatement(sql);
            stmt.setInt(1, id);
            rs = stmt.executeQuery();
            
            while(rs.next()){
                
                cartao.setId(rs.getInt("car_id"));
                cartao.setNumero(rs.getInt("car_numero"));
                cartao.setNomeImpressoNoCartao(rs.getString("car_nome_impresso_no_cartao"));
                cartao.setBandeira(BandeiraCartao.valueOf(rs.getString("car_bandeira")));
                cartao.setCodSeguranca(rs.getInt("car_cod_seguranca"));
                cartao.setPreferencial(rs.getBoolean("car_preferencial"));
                cartao.getCliente().setId(rs.getInt("car_cli_id"));
   
            }
            
            return cartao;
        
        }catch(SQLException ex){
            System.out.println("Não foi possível consultar o cartão no banco de dados \nErro: " + ex.getMessage());
        }finally{
            if(ctrlTransacao) ConnectionFactory.closeConnection(conn, stmt, rs);
        }        
        return null;   
    }

}
