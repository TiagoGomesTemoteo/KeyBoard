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
public class ClienteDAO extends AbstractDAO {

    private Connection conn;
    private CartaoDAO cartaoDAO;
    private EnderecoDAO enderecoDAO;

    @Override
    public EntidadeDominio salvar(EntidadeDominio entidade) {
        Cliente cliente = (Cliente) entidade;
        int id = 0;

        this.conn = ConnectionFactory.getConnection();

        String sql = "INSER INTO CLIENTES (cli_id, cli_dt_cadastro, cli_nome, cli_genero,"
                + " cli_email, cli_senha, cli_rank, cli_dt_nascimento, cli_cpf, cli_ativo, cli_telefone)"
                + " VALUES(cli_id, now(), ?,?,?,?,?,?,?,?,?)";

        PreparedStatement stmt = null;
        ResultSet rs = null;

        try {
            if (conn == null) {
                this.conn = ConnectionFactory.getConnection();
            }

            conn.setAutoCommit(false);

            stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            stmt.setString(1, cliente.getNome());
            stmt.setString(2, cliente.getGenero().toString());
            stmt.setString(3, cliente.getEmail());
            stmt.setString(4, cliente.getSenha());
            stmt.setInt(5, cliente.getRank());
            stmt.setDate(6, new java.sql.Date(cliente.getDtNascimento().getTime()));
            stmt.setInt(7, cliente.getCpf());
            stmt.setBoolean(8, cliente.isAtivo());
            stmt.setString(9, cliente.getTelefone().getDdd() + cliente.getTelefone().getNumero());

            stmt.executeUpdate();

            rs = stmt.getGeneratedKeys();
            if (rs.next()) {
                id = rs.getInt(1);
            }
            cliente.setId(id);

            //Salvando Endereços
            enderecoDAO = new EnderecoDAO(conn);
            for (Endereco endereco : cliente.getEnderecos()) {
                endereco.getCliente().setId(id);
                enderecoDAO.salvar(entidade);
            }

            //Salvando Cartões de Crédito
            cartaoDAO = new CartaoDAO(conn);
            for (CartaoDeCredito cartao : cliente.getCartoesDeCredito()) {
                cartao.getCliente().setId(id);
                cartaoDAO.salvar(cartao);
            }

        } catch (Exception ex) {
            try {
                conn.rollback();
            } catch (SQLException e1) {
                System.out.println("Error: "+ e1.getMessage());
            }
            
            System.out.println("Não foi possível salvar os dados no banco de dados.\nErro: " + ex.getMessage());

        } finally {
            ConnectionFactory.closeConnection(conn, stmt, rs);
        }
        
        return cliente;
    }

    @Override
    public boolean alterar(EntidadeDominio entidade) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean deletar(int id) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List consultar() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public EntidadeDominio consultar(EntidadeDominio entidade) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
