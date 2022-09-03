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
    public EntidadeDominio salvar(EntidadeDominio entidade) {
        Endereco endereco = (Endereco) entidade;
        int id = 0;

        String sql = "INSERT INTO ENDERECOS (end_id, end_tp_residencia, end_tp_logradouro, end_logradouro, "
                + "end_numero, end_observacoes, end_identificacao, end_endereco_cobranca, "
                + "end_endereco_entrega, end_endereco_residencial, end_cli_id, end_cep)"
                + " VALUES(car_id, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

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

            System.out.println("Não foi possível salvar os dados no banco de dados.\nErro: " + ex.getMessage());

        } finally {
            if (ctrlTransacao) {
                ConnectionFactory.closeConnection(conn, stmt, rs);
            }
        }

        return endereco;
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
