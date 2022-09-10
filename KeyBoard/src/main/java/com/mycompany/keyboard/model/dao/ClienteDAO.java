/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.keyboard.model.dao;

import com.mycompany.keyboard.model.domain.CartaoDeCredito;
import com.mycompany.keyboard.model.domain.Cliente;
import com.mycompany.keyboard.model.domain.Endereco;
import com.mycompany.keyboard.model.domain.EntidadeDominio;
import com.mycompany.keyboard.model.domain.Telefone;
import com.mycompany.keyboard.model.domain.enums.Genero;
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
public class ClienteDAO extends AbstractDAO {

    private Connection conn;
    private CartaoDAO cartaoDAO;
    private EnderecoDAO enderecoDAO;
    private TelefoneDAO telefoneDAO;

    @Override
    public void salvar(EntidadeDominio entidade) {
        Cliente cliente = (Cliente) entidade;

        String sql = "INSERT INTO CLIENTES (cli_id, cli_dt_cadastro, cli_nome, cli_genero,"
                + " cli_email, cli_senha, cli_rank, cli_dt_nascimento, cli_cpf, cli_ativo, cli_tel_id)"
                + " VALUES(cli_id, now(), ?,?,?,?,?,?,?,?,?)";

        PreparedStatement stmt = null;
        ResultSet rs = null;

        try {

            this.conn = ConnectionFactory.getConnection();
            conn.setAutoCommit(false);
            
            new TelefoneDAO(conn).salvar(cliente.getTelefone());
            
            stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            stmt.setString(1, cliente.getNome());
            stmt.setString(2, cliente.getGenero().toString());
            stmt.setString(3, cliente.getEmail());
            stmt.setString(4, cliente.getSenha());
            stmt.setInt(5, cliente.getRank());
            stmt.setDate(6,ParameterParser.utilDateToSqlDate(cliente.getDtNascimento()));
            stmt.setString(7, cliente.getCpf());
            stmt.setBoolean(8, cliente.isAtivo());
            stmt.setInt(9, cliente.getTelefone().getId());

            stmt.executeUpdate();

            rs = stmt.getGeneratedKeys();
            if (rs.next()) cliente.setId(rs.getInt(1));
            
            //Salvando Endereços
            for (Endereco endereco : cliente.getEnderecos()) {
                endereco.getCliente().setId(cliente.getId());
                new EnderecoDAO(conn).salvar(endereco);
            }

            //Salvando Cartões de Crédito
            for (CartaoDeCredito cartao : cliente.getCartoesDeCredito()) {
                cartao.getCliente().setId(cliente.getId());
                new CartaoDAO(conn).salvar(cartao);
            }
            
            conn.commit();

        } catch (Exception ex) {
            try {
                conn.rollback();
            } catch (SQLException e1) {
                System.out.println("Error: "+ e1.getMessage());
            }
            
            System.out.println("Não foi possível salvar o clinte no banco de dados.\nErro: " + ex.getMessage());

        } finally {
            ConnectionFactory.closeConnection(conn, stmt, rs);
        }
        
    }

    @Override
    public void alterar(EntidadeDominio entidade) {
        Cliente cliente = (Cliente) entidade;

        String sql = "UPDATE CLIENTES SET cli_nome=?, cli_genero=?,"
                + " cli_email=?, cli_senha=?, cli_rank=?, cli_dt_nascimento=?, "
                + " cli_cpf=?, cli_ativo=?, cli_tel_id=? "
                + "WHERE cli_id=?;";

        PreparedStatement stmt = null;

        try {
            
            this.conn = ConnectionFactory.getConnection();
            conn.setAutoCommit(false);

            new TelefoneDAO(conn).alterar(cliente.getTelefone());
            
            stmt = conn.prepareStatement(sql);
            stmt.setString(1, cliente.getNome());
            stmt.setString(2, cliente.getGenero().toString());
            stmt.setString(3, cliente.getEmail());
            stmt.setString(4, cliente.getSenha());
            stmt.setInt(5, cliente.getRank());
            stmt.setDate(6, ParameterParser.utilDateToSqlDate(cliente.getDtNascimento()));
            stmt.setString(7, cliente.getCpf());
            stmt.setBoolean(8, cliente.isAtivo());
            stmt.setInt(9, cliente.getTelefone().getId());
            stmt.setInt(10, cliente.getId());
            
            stmt.executeUpdate();

            //Alterando Endereços
            for (Endereco endereco : cliente.getEnderecos()) {
                endereco.getCliente().setId(cliente.getId());
                new EnderecoDAO(conn).alterar(endereco);
            }

            //Alterando Cartões de Crédito
            for (CartaoDeCredito cartao : cliente.getCartoesDeCredito()) {
                cartao.getCliente().setId(cliente.getId());
                new CartaoDAO(conn).alterar(cartao);
            }
            
            conn.commit();

        } catch (Exception ex) {
            try {
                conn.rollback();
            } catch (SQLException e1) {
                System.out.println("Error: "+ e1.getMessage());
            }
            
            System.out.println("Não foi possível alterar o cliente no banco de dados.\nErro: " + ex.getMessage());

        } finally {
            ConnectionFactory.closeConnection(conn, stmt);
        }
        
      
    }

    @Override
    public void deletar(EntidadeDominio entidade) {
        Cliente cliente = (Cliente) entidade;

        String sql = "UPDATE CLIENTES SET cli_ativo=? "
                + "WHERE cli_id=?;";

        PreparedStatement stmt = null;

        try {
            
            this.conn = ConnectionFactory.getConnection();
            conn.setAutoCommit(false);

            stmt = conn.prepareStatement(sql);
            
            stmt.setBoolean(1, cliente.isAtivo());
            stmt.setInt(2, cliente.getId());
            
            stmt.executeUpdate();

            conn.commit();

        } catch (Exception ex) {
            try {
                conn.rollback();
            } catch (SQLException e1) {
                System.out.println("Error: "+ e1.getMessage());
            }
            
            System.out.println("Não foi possível inativar o clinte no banco de dados.\nErro: " + ex.getMessage());

        } finally {
            ConnectionFactory.closeConnection(conn, stmt);
        }
    }

    @Override
    public List consultar(EntidadeDominio entidade) {
        Cliente cliente;
        String sql = "SELECT * FROM CLIENTES;";
        
        PreparedStatement stmt = null;
        ResultSet rs = null;
        
        List<Cliente> clientes = new ArrayList();
        
        try{
            this.conn = ConnectionFactory.getConnection();
            stmt = conn.prepareStatement(sql);
            
            rs = stmt.executeQuery();
           
            enderecoDAO = new EnderecoDAO(conn);
            cartaoDAO = new CartaoDAO(conn);
            telefoneDAO = new TelefoneDAO(conn);
            
            while(rs.next()){
                cliente = new Cliente(); 
                cliente.setId(rs.getInt("cli_id"));
                cliente.setDt_cadastro(rs.getDate("cli_dt_cadastro"));
                cliente.setNome(rs.getString("cli_nome"));
                cliente.setGenero(Genero.valueOf(rs.getString("cli_genero")));
                cliente.setEmail(rs.getString("cli_email"));
                cliente.setRank(rs.getInt("cli_rank"));
                cliente.setDtNascimento(rs.getDate("cli_dt_nascimento"));
                cliente.setCpf(rs.getString("cli_cpf"));
                cliente.setAtivo(rs.getBoolean("cli_ativo"));
                cliente.setTelefone((Telefone)telefoneDAO.consultar(rs.getInt("cli_tel_id")));
                cliente.setEnderecos(enderecoDAO.consultar(cliente));
                cliente.setCartoesDeCredito(cartaoDAO.consultar(cliente));
                
                clientes.add(cliente);
            }
            
            return clientes;
            
        }catch(SQLException ex){
            System.out.println("Não foi possível consultar fornecedor no banco de dados \nErro:" + ex.getMessage());
        }finally{
            ConnectionFactory.closeConnection(conn, stmt, rs);
        }
        return null;
    }

    @Override
    public EntidadeDominio consultar(int id) {
        Cliente cliente = new Cliente();
        
        String sql = "SELECT * FROM CLIENTES WHERE cli_id = ?;";
        
        PreparedStatement stmt = null;
        ResultSet rs = null;
        
        try{
            this.conn = ConnectionFactory.getConnection();
            
            stmt = conn.prepareStatement(sql);
            stmt.setInt(1,cliente.getId());
            rs = stmt.executeQuery();
           
            enderecoDAO = new EnderecoDAO(conn);
            cartaoDAO = new CartaoDAO(conn);
            telefoneDAO = new TelefoneDAO(conn);
            
            while(rs.next()){
                
                cliente.setId(rs.getInt("cli_id"));
                cliente.setDt_cadastro(rs.getDate("cli_dt_cadastro"));
                cliente.setNome(rs.getString("cli_nome"));
                cliente.setGenero(Genero.valueOf(rs.getString("cli_genero")));
                cliente.setEmail(rs.getString("cli_email"));
                cliente.setRank(rs.getInt("cli_rank"));
                cliente.setDtNascimento(rs.getDate("cli_dt_nascimento"));
                cliente.setCpf(rs.getString("cli_cpf"));
                cliente.setAtivo(rs.getBoolean("cli_ativo"));
                cliente.setTelefone((Telefone)telefoneDAO.consultar(rs.getInt("cli_tel_id")));
                cliente.setEnderecos(enderecoDAO.consultar(cliente));
                cliente.setCartoesDeCredito(cartaoDAO.consultar(cliente));
                
            }
            
            return cliente;
            
        }catch(SQLException ex){
            System.out.println("Não foi possível consultar fornecedor no banco de dados \nErro:" + ex.getMessage());
        }finally{
            ConnectionFactory.closeConnection(conn, stmt, rs);
        }
        return null;
    }
    
    public boolean existeCpf(String cpf) {
        
        String sql = "SELECT * FROM CLIENTES WHERE cli_cpf = ?;";
        
        PreparedStatement stmt = null;
        ResultSet rs = null;
        
        try{
            this.conn = ConnectionFactory.getConnection();
            
            stmt = conn.prepareStatement(sql);
            stmt.setString(1,cpf);
            rs = stmt.executeQuery();
                 
            return rs.next() == true;
          
        }catch(SQLException ex){
            System.out.println("Não foi possível consultar fornecedor no banco de dados \nErro:" + ex.getMessage());
        }finally{
            ConnectionFactory.closeConnection(conn, stmt, rs);
        }
        return false;
    }
    
    

}
