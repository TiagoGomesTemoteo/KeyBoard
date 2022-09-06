/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.keyboard.model.dao;

import com.mycompany.keyboard.model.domain.EntidadeDominio;
import com.mycompany.keyboard.model.domain.Telefone;
import com.mycompany.keyboard.model.domain.enums.TelefoneENUM;
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
public class TelefoneDAO extends AbstractDAO{
    
    private Connection conn;
    
    public TelefoneDAO (Connection conn){
        this.conn = conn;
    }
    
    @Override
    public void salvar(EntidadeDominio entidade) {
        Telefone telefone = (Telefone) entidade;

        String sql = "INSERT INTO TELEFONES (tel_id, tel_tipo, tel_ddd, tel_numero) "
                + " VALUES(tel_id, ?, ?, ?)";

        PreparedStatement stmt = null;
        ResultSet rs = null;

        try {
            if (conn == null) {
                this.conn = ConnectionFactory.getConnection();
                this.ctrlTransacao = true;

            } else {
                this.ctrlTransacao = false;
            }

            conn.setAutoCommit(false);

            stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            stmt.setString(1, telefone.getTipo().toString());
            stmt.setString(2, telefone.getDdd());
            stmt.setString(3, telefone.getNumero());
            
            stmt.executeUpdate();

            rs = stmt.getGeneratedKeys();
            
            if (rs.next()) telefone.setId(rs.getInt(1));

            if (ctrlTransacao) conn.commit();       

        } catch (Exception ex) {
            try {
                conn.rollback();
            } catch (SQLException e1) {
                System.out.println("Error: " + e1.getMessage());
            }

            System.out.println("Não foi possível salvar o telefone no banco de dados.\nErro: " + ex.getMessage());

        } finally {
            if (ctrlTransacao) ConnectionFactory.closeConnection(conn, stmt, rs);
                      
        }

    }

    @Override
    public void alterar(EntidadeDominio entidade) {
        Telefone telefone = (Telefone) entidade;

        String sql = "UPDATE TELEFONES SET tel_tipo=?, tel_ddd=?, tel_numero=? "
                + " WHERE tel_id=?";

        PreparedStatement stmt = null;
        ResultSet rs = null;

        try {
            if (conn == null) {
                this.conn = ConnectionFactory.getConnection();
                this.ctrlTransacao = true;

            } else {
                this.ctrlTransacao = false;
            }

            conn.setAutoCommit(false);

            stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            stmt.setString(1, telefone.getTipo().toString());
            stmt.setString(2, telefone.getDdd());
            stmt.setString(3, telefone.getNumero());
            stmt.setInt(4, telefone.getId());
            
            stmt.executeUpdate();

            if (ctrlTransacao) conn.commit();       

        } catch (Exception ex) {
            try {
                conn.rollback();
            } catch (SQLException e1) {
                System.out.println("Error: " + e1.getMessage());
            }

            System.out.println("Não foi possível alterar o telefone no banco de dados.\nErro: " + ex.getMessage());

        } finally {
            if (ctrlTransacao) ConnectionFactory.closeConnection(conn, stmt, rs);
                      
        }

    }

    @Override
    public void deletar(EntidadeDominio entidade) {
        Telefone telefone = (Telefone) entidade;
        String sql = "DELETE FROM TELEFONES WHERE tel_id = ?;";
        
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
            stmt.setInt(1, telefone.getId());
            
            stmt.executeUpdate();
            
            if(ctrlTransacao) conn.commit();
            
        }catch(Exception ex){
            try {
                conn.rollback();
            } catch (SQLException e1) {
                System.out.println("Error: " + e1.getMessage());
            }
            
            System.out.println("Não foi possível excluir o telefone do banco de dados" + ex.getMessage());
        
        }finally{
            if(ctrlTransacao) ConnectionFactory.closeConnection(conn, stmt);
        }      
    }

    @Override
    public List consultar(EntidadeDominio entidade) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public EntidadeDominio consultar(int id) {
        Telefone telefone = new Telefone();
        String sql = "SELECT * FROM TELEFONES WHERE tel_id = ?;";
        
        PreparedStatement stmt = null;
        ResultSet rs = null;

        try{

            stmt = conn.prepareStatement(sql);
            stmt.setInt(1, id);
            rs = stmt.executeQuery();
            
            while(rs.next()){    
                telefone.setId(rs.getInt("tel_id"));
                telefone.setTipo(TelefoneENUM.valueOf(rs.getString("tel_tipo")));
                telefone.setDdd(rs.getString("tel_ddd"));
                telefone.setNumero(rs.getString("tel_numero"));
            }
            
            return telefone;
            
        }catch(Exception ex){   
            System.out.println("Não foi possível consulta o telefone no banco de dados" + ex.getMessage());
        }
        
        return null;
    }
    
}
