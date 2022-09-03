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
public class CartaoDAO extends AbstractDAO {

    private Connection conn;

    public CartaoDAO(Connection conn) {
        this.conn = conn;
    }

    @Override
    public EntidadeDominio salvar(EntidadeDominio entidade) {
        CartaoDeCredito cartao = (CartaoDeCredito) entidade;
        int id = 0;

        String sql = "INSERT INTO CARTOES_DE_CREDITO (car_id, car_numero, car_nome_impresso_no_cartao,"
                + " car_bandeira, car_cod_seguranca, car_preferencial, car_cli_id)"
                + " VALUES(car_id, ?,?,?,?,?,?)";

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

            System.out.println("Não foi possível salvar os dados no banco de dados.\nErro: " + ex.getMessage());

        } finally {
            if (ctrlTransacao) {
                ConnectionFactory.closeConnection(conn, stmt, rs);
            }
        }

        return cartao;
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
