/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.keyboard.model.dao;

import com.mycompany.keyboard.model.domain.CupomDeTroca;
import com.mycompany.keyboard.model.domain.CupomPromocional;
import com.mycompany.keyboard.model.domain.Endereco;
import com.mycompany.keyboard.model.domain.Pagamento;
import com.mycompany.keyboard.model.domain.Pedido;
import com.mycompany.keyboard.model.strategy.FunctionsUtilsPagamento;
import com.mycompany.keyboard.util.ConnectionFactory;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 *
 * @author Tiago
 */
public class UtilsDAO {

    public static int consultarIdStatusByCod(int cod, Connection conn) {                
        String sql = "SELECT * FROM ESTATUS WHERE stt_codigo = ?";     
        PreparedStatement stmt = null;
        ResultSet rs = null;
             
        try{               
            stmt = conn.prepareStatement(sql);
            stmt.setInt(1, cod);
            rs = stmt.executeQuery();
            
            if (rs.next()) return rs.getInt("stt_id");
        
        }catch(SQLException ex){
            System.out.println("Não foi possível consultar o Status no banco de dados \nErro: " + ex.getMessage());
        }            
        return 0;
    }
    
    public static int consultaEstatus(int id, Connection conn) {               
        String sql = "SELECT * FROM ESTATUS WHERE stt_id = ?";       
        PreparedStatement stmt = null;
        ResultSet rs = null;
             
        try{              
            stmt = conn.prepareStatement(sql);
            stmt.setInt(1, id);
            rs = stmt.executeQuery();
            
            if (rs.next()) return rs.getInt("stt_codigo");
        
        }catch(SQLException ex){
            System.out.println("Não foi possível consultar o Status no banco de dados \nErro: " + ex.getMessage());
        }    
        
        return 0;
    }
    
    public static void gerarCupomTroca (Pedido pedido, Connection conn) {
        
        String sql = "INSERT INTO CUPONS_DE_TROCA (cdt_id, cdt_valor, cdt_validade, cdt_cli_id)"
                + " VALUES(cdt_id, ?, now(), ?)";

        PreparedStatement stmt = null;

        try {

            stmt = conn.prepareStatement(sql);
            
            stmt.setDouble(1, calcularValorCupomDeTroca(pedido));
            stmt.setDouble(2, pedido.getCliente().getId());

            stmt.executeUpdate();
 
        } catch (Exception ex) {
            try {
                conn.rollback();
            } catch (SQLException e1) {
                System.out.println("Error: " + e1.getMessage());
            }

            System.out.println("Não foi possível gerar o cupom de troca.\nErro: " + ex.getMessage());

        } 
        
    }
    
    
    public static void inativarCuponsUsados (Pedido pedido, Connection conn){
        
        String sqlCuponsTroca = "UPDATE CUPONS_DE_TROCA SET cdt_ativo=? WHERE cdt_id=?";
        String sqlCupomPromocional = "UPDATE CUPOM_PROMOCIONAL SET cup_ativo=? WHERE cup_id=?";

        PreparedStatement stmt = null;

        try {

            stmt = conn.prepareStatement(sqlCuponsTroca);
            
            for (Pagamento pagamento: pedido.getPagamento()) {
                if (pagamento.getForma_de_pagamento() instanceof CupomDeTroca) {
                    stmt.setBoolean(1, false);
                    stmt.setInt(2, pagamento.getForma_de_pagamento().getId());            
                    stmt.executeUpdate();
                }
            }
            
            stmt = conn.prepareStatement(sqlCupomPromocional);
            
            for (Pagamento pagamento: pedido.getPagamento()) {
                if (pagamento.getForma_de_pagamento() instanceof CupomPromocional) {
                    stmt.setBoolean(1, false);
                    stmt.setInt(2, pagamento.getForma_de_pagamento().getId());            
                    stmt.executeUpdate();
                }
            }
            
        } catch (Exception ex) {
            try {
                conn.rollback();
            } catch (SQLException e1) {
                System.out.println("Error: " + e1.getMessage());
            }

            System.out.println("Não foi possível inativar o cupom.\nErro: " + ex.getMessage());

        } 
    }
    
    
    public static double calcularValorCupomDeTroca (Pedido pedido) {
        
        double valorTotalCupons = 0.0;
        
        for (Pagamento pagamento : pedido.getPagamento()) {
            if (pagamento.getForma_de_pagamento() instanceof CupomDeTroca) {
                valorTotalCupons += pagamento.getValor();
            }
        }
        
        return (valorTotalCupons - FunctionsUtilsPagamento.getValorTotalCompra(pedido));
        
    }
}
