/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.keyboard.model.dao;

import com.mycompany.keyboard.model.domain.Endereco;
import com.mycompany.keyboard.model.domain.EntidadeDominio;
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
public class EnderecoDAO extends AbstractDAO {

    private Connection conn;

    public EnderecoDAO(Connection conn) {
        this.conn = conn;
    }

    @Override
    public void salvar(EntidadeDominio entidade) {
        Endereco endereco = (Endereco) entidade;
        int id = 0;

        String sql = "INSERT INTO ENDERECOS (end_id, end_tp_residencia, end_tp_logradouro, end_logradouro, "
                + "end_numero, end_observacoes, end_identificacao, end_endereco_cobranca, "
                + "end_endereco_entrega, end_endereco_residencial, end_cli_id, end_cep, end_bairro, end_cidade,"
                + "end_estado, end_pais)"
                + " VALUES(end_id, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

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
            stmt.setString(1, endereco.getTipoResidencia());
            stmt.setString(2, endereco.getTipoLogradouro());
            stmt.setString(3, endereco.getLogradouro());
            stmt.setString(4, endereco.getNumero());
            stmt.setString(5, endereco.getObservacoes());
            stmt.setString(6, endereco.getIdentificacao());
            stmt.setBoolean(7, endereco.isEnderecoCobranca());
            stmt.setBoolean(8, endereco.isEnderecoEntrega());
            stmt.setBoolean(9, endereco.isEnderecoResidencial());
            stmt.setInt(10, endereco.getCliente().getId());
            stmt.setInt(11, endereco.getCep());
            stmt.setString(12, endereco.getBairro());
            stmt.setString(13, endereco.getCidade());
            stmt.setString(14, endereco.getEstado());
            stmt.setString(15, endereco.getPais());


            stmt.executeUpdate();

            rs = stmt.getGeneratedKeys();
            if (rs.next()) {
                id = rs.getInt(1);
            }
            endereco.setId(id);

            if (ctrlTransacao) {
                conn.commit();
            }

        } catch (Exception ex) {
            try {
                conn.rollback();
            } catch (SQLException e1) {
                System.out.println("Error: " + e1.getMessage());
            }

            System.out.println("Não foi possível salvar o endereço no banco de dados.\nErro: " + ex.getMessage());

        } finally {
            if (ctrlTransacao) {
                ConnectionFactory.closeConnection(conn, stmt, rs);
            }
        }

    }

    @Override
    public void alterar(EntidadeDominio entidade) {
        Endereco endereco = (Endereco) entidade;

        String sql = "UPDATE ENDERECOS SET end_tp_residencia=?, end_tp_logradouro=?, end_logradouro=?, "
                + "end_numero=?, end_observacoes=?, end_identificacao=?, end_endereco_cobranca=?, "
                + "end_endereco_entrega=?, end_endereco_residencial=?, end_cep=?, "
                + "end_bairro=?, end_cidade=?, end_estado=?, end_pais=? "
                + "WHERE end_id = ?";

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
            stmt.setString(1, endereco.getTipoResidencia());
            stmt.setString(2, endereco.getTipoLogradouro());
            stmt.setString(3, endereco.getLogradouro());
            stmt.setString(4, endereco.getNumero());
            stmt.setString(5, endereco.getObservacoes());
            stmt.setString(6, endereco.getIdentificacao());
            stmt.setBoolean(7, endereco.isEnderecoCobranca());
            stmt.setBoolean(8, endereco.isEnderecoEntrega());
            stmt.setBoolean(9, endereco.isEnderecoResidencial());
            stmt.setInt(10, endereco.getCep());
            stmt.setString(11, endereco.getBairro());
            stmt.setString(12, endereco.getCidade());
            stmt.setString(13, endereco.getEstado());
            stmt.setString(14, endereco.getPais());
            stmt.setInt(15, endereco.getId());

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

            System.out.println("Não foi possível alterar o endereço no banco de dados.\nErro: " + ex.getMessage());

        } finally {
            if (ctrlTransacao) {
                ConnectionFactory.closeConnection(conn, stmt);
            }
        }
    }

    @Override
    public void deletar(EntidadeDominio entidade) {
        Endereco endereco = (Endereco) entidade;
        String sql = "DELETE FROM ENDERECOS WHERE end_id = ?;";
        
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
            stmt.setInt(1, endereco.getId());
            
            stmt.executeUpdate();
            
            if(ctrlTransacao) conn.commit();
            
        }catch(Exception ex){
            try {
                conn.rollback();
            } catch (SQLException e1) {
                System.out.println("Error: " + e1.getMessage());
            }
            
            System.out.println("Não foi possível excluir o endereço do banco de dados" + ex.getMessage());
        
        }finally{
            if(ctrlTransacao) ConnectionFactory.closeConnection(conn, stmt);
        }      
    }

    @Override
    public List consultar(EntidadeDominio entidade) {
        Endereco endereco;
        String sql = "SELECT * FROM ENDERECOS WHERE end_cli_id = ?;";
        
        PreparedStatement stmt = null;
        ResultSet rs = null;
        
        List<Endereco> enderecos = new ArrayList();
        
        try{
            if(conn == null || this.conn.isClosed()){
                this.conn = ConnectionFactory.getConnection();
                ctrlTransacao = true; 
            }else{
                ctrlTransacao = false;
            }
            
            conn.setAutoCommit(false);

            stmt = conn.prepareStatement(sql);
            stmt.setInt(1, entidade.getId());
            rs = stmt.executeQuery();
            
            while(rs.next()){
                endereco = new Endereco();
                
                endereco.setId(rs.getInt("end_id"));
                endereco.setTipoResidencia(rs.getString("end_tp_residencia"));
                endereco.setTipoLogradouro(rs.getString("end_tp_logradouro"));
                endereco.setLogradouro(rs.getString("end_logradouro"));
                endereco.setNumero(rs.getString("end_numero"));
                endereco.setObservacoes(rs.getString("end_observacoes"));
                endereco.setIdentificacao(rs.getString("end_identificacao"));
                endereco.setEnderecoCobranca(rs.getBoolean("end_endereco_cobranca"));
                endereco.setEnderecoEntrega(rs.getBoolean("end_endereco_entrega"));
                endereco.setEnderecoResidencial(rs.getBoolean("end_endereco_residencial"));
                endereco.getCliente().setId(rs.getInt("end_cli_id"));
                endereco.setCep(rs.getInt("end_cep"));
                endereco.setBairro(rs.getString("end_bairro"));
                endereco.setCidade(rs.getString("end_cidade"));
                endereco.setEstado(rs.getString("end_estado"));
                endereco.setPais(rs.getString("end_pais"));
                
                enderecos.add(endereco);
                
            }
            
            return enderecos;
            
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
        
        return null;
    }

    @Override
    public EntidadeDominio consultar(int id) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
