/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.keyboard.model.dao;


import com.mycompany.keyboard.model.domain.CartaoDeCredito;
import com.mycompany.keyboard.model.domain.Cliente;
import com.mycompany.keyboard.model.domain.CupomDeTroca;
import com.mycompany.keyboard.model.domain.CupomPromocional;
import com.mycompany.keyboard.model.domain.Endereco;
import com.mycompany.keyboard.model.domain.EntidadeDominio;
import com.mycompany.keyboard.model.domain.Item;
import com.mycompany.keyboard.model.domain.Pagamento;
import com.mycompany.keyboard.model.domain.Pedido;
import com.mycompany.keyboard.model.domain.Teclado;
import com.mycompany.keyboard.model.domain.enums.Estatus;
import com.mycompany.keyboard.model.strategy.FunctionsUtilsPagamento;
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
public class PedidoDAO extends AbstractDAO{

    private Connection conn = null;

    public PedidoDAO(Connection conn) {
        this.conn = conn;
    }

    public PedidoDAO() {}
       
    @Override
    public void salvar(EntidadeDominio entidade) {
        
        Pedido pedido = (Pedido) entidade;

        String sqlPedido = "INSERT INTO PEDIDOS (ped_id, ped_stt_id, ped_cli_id, ped_end_id, ped_valor_total, ped_dt)"
                + " VALUES(ped_id, ?,?,?,?, now())";
        
        String sqlPedidoProduto = "INSERT INTO PEDIDOS_PRODUTOS (pep_id, pep_ped_id, pep_tec_id, pep_quantidade)"
                + " VALUES(pep_id, ?,?,?)";
        
        String sqlPedidoPagamento = "INSERT INTO PAGAMENTOS (pag_id, pag_fpg_id, pag_valor, pag_ped_id, pag_cdt_id,"
                + " pag_car_id, pag_cup_id)"
                + " VALUES(pag_id, ?,?,?,?,?,?)";

        PreparedStatement stmt = null;
        ResultSet rs = null;

        try {

            this.conn = ConnectionFactory.getConnection();
            conn.setAutoCommit(false);
            
            stmt = conn.prepareStatement(sqlPedido, Statement.RETURN_GENERATED_KEYS);
            
            stmt.setInt(1, UtilsDAO.consultarIdStatusByCod(pedido.getEstatus().getEstatus(), conn));
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
 
            /*COD - CUPOM_TROCA: 1 | CART??O: 2 | CUPOM_PROMOCIONAL: 3*/
            for (Pagamento pagamento : pedido.getPagamento()){
                if (pagamento.getForma_de_pagamento() instanceof CupomDeTroca){
                    stmt.setInt(1, 1);
                    stmt.setInt(4, pagamento.getForma_de_pagamento().getId());
                    stmt.setNull(5, 0);
                    stmt.setNull(6, 0);
                    
                } else if (pagamento.getForma_de_pagamento() instanceof CartaoDeCredito){                  
                    stmt.setInt(1, 2);                    
                    stmt.setNull(4, 0);
                    stmt.setInt(5, pagamento.getForma_de_pagamento().getId());
                    stmt.setNull(6, 0);
                
                } else {
                    stmt.setInt(1, 3);                   
                    stmt.setNull(4, 0);
                    stmt.setNull(5, 0);
                    stmt.setInt(6, pagamento.getForma_de_pagamento().getId());
                } 
                
                stmt.setDouble(2, pagamento.getValor());
                stmt.setInt(3, pedido.getId());
                
                
                stmt.executeUpdate();
            }
                       
            //Gerando cupom de troca caso a compra seja paga apenas com cupons de troca e o valor total deles seja maior que o valor total da compra
            if (!FunctionsUtilsPagamento.validateIfContainCartaoDeCredito(pedido) &&
                    UtilsDAO.calcularValorCupomDeTroca(pedido) > 0) {
                UtilsDAO.gerarCupomTroca(pedido, conn);
            }
            
            //Inativar cupons usados
            UtilsDAO.inativarCuponsUsados(pedido, conn);
            
            //Limpando o carrinho quando o pedido for fechado
            CarrinhoDAO carrinhoDao = new CarrinhoDAO(conn);           
            carrinhoDao.deletarCarrinho(pedido.getCliente().getId());
                       
            conn.commit();

        } catch (Exception ex) {
            try {
                conn.rollback();
            } catch (SQLException e1) {
                System.out.println("Error: "+ e1.getMessage());
            }
            
            System.out.println("N??o foi poss??vel salvar o pedido no banco de dados.\nErro: " + ex.getMessage());

        } finally {
            ConnectionFactory.closeConnection(conn, stmt, rs);
        }
    }

    @Override
    public void alterar(EntidadeDominio entidade) {
        Pedido pedido = (Pedido) entidade;
        
        String sql = "UPDATE PEDIDOS SET ped_stt_id = ? WHERE ped_id = ?";
        
        PreparedStatement stmt = null;
        
        try{
            this.conn = ConnectionFactory.getConnection();
            conn.setAutoCommit(false);  
            
            stmt = conn.prepareStatement(sql);
                        
            stmt.setInt(1, UtilsDAO.consultarIdStatusByCod(pedido.getEstatus().getEstatus(), conn));
            stmt.setInt(2, pedido.getId());
            
            stmt.executeUpdate();            

            conn.commit();
            
        }catch (Exception ex) {
            try {
                conn.rollback();
            } catch (SQLException e1) {
                System.out.println("Error: "+ e1.getMessage());
            }
            
            System.out.println("N??o foi poss??vel alterar o pedido no banco de dados.\nErro: " + ex.getMessage());

        } finally {
            ConnectionFactory.closeConnection(conn, stmt);
        }
    }

    @Override
    public void deletar(EntidadeDominio entidade) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List consultar(EntidadeDominio entidade) {
        Pedido pedido = (Pedido) entidade;
        
        String sql = "SELECT * FROM PEDIDOS WHERE ped_cli_id = ? ORDER BY ped_id DESC";
        String sqlAllPedido = "SELECT * FROM PEDIDOS ORDER BY ped_id DESC";       
        String sqlProdutosPedido = "SELECT * FROM PEDIDOS_PRODUTOS WHERE pep_ped_id = ?";
        String sqlPagamentoPedido = "SELECT * FROM PAGAMENTOS WHERE pag_ped_id = ?";
        
        PreparedStatement stmt = null;
        ResultSet rs = null;
        
        List<Pedido> pedidos = new ArrayList();
        
        try{
            if (conn == null || conn.isClosed()) {
                this.conn = ConnectionFactory.getConnection();
                this.ctrlTransacao = true;
            } else {
                this.ctrlTransacao = false;
            }           
                        
            if (pedido.getCliente().getId() != 0){
                stmt = conn.prepareStatement(sql);
                stmt.setInt(1, pedido.getCliente().getId());
            } else {
                stmt = conn.prepareStatement(sqlAllPedido);
            }
    
            rs = stmt.executeQuery();
            
            while (rs.next()) {
                pedido = new Pedido();
                
                pedido.setId(rs.getInt("ped_id"));
                pedido.setEstatus(Estatus.pegaEstatusPorValor(UtilsDAO.consultaEstatus(rs.getInt("ped_stt_id"), conn)));                
                pedido.setCliente((Cliente)new ClienteDAO().consultar(rs.getInt("ped_cli_id")));
                pedido.setEndereco((Endereco)new EnderecoDAO().consultar(rs.getInt("ped_end_id")));
                pedido.setValor_total(rs.getDouble("ped_valor_total"));
                pedido.setDt_cadastro(ParameterParser.sqlDateToUtilDate(rs.getDate("ped_dt")));

                pedidos.add(pedido);
            }
            
            for (Pedido pedidoProduto : pedidos) {
                stmt = conn.prepareStatement(sqlProdutosPedido);
                stmt.setInt(1, pedidoProduto.getId());

                rs = stmt.executeQuery();

                while (rs.next()) {
                    pedidoProduto.getItens().add(new Item((Teclado)new TecladoDAO().consultar(rs.getInt("pep_tec_id")), rs.getInt("pep_quantidade"), false));
                }
            }
            
            for (Pedido pedidoPagamento : pedidos) {
                stmt = conn.prepareStatement(sqlPagamentoPedido);
                stmt.setInt(1, pedidoPagamento.getId());

                rs = stmt.executeQuery();

                while (rs.next()) {
                    switch (rs.getInt("pag_fpg_id")){
                        case 1: pedidoPagamento.getPagamento().add(new Pagamento(rs.getDouble("pag_valor"), consultaCupomDeTroca(rs.getInt("pag_cdt_id")))); break;                        
                        case 2: pedidoPagamento.getPagamento().add(new Pagamento(rs.getDouble("pag_valor"), (CartaoDeCredito)new CartaoDAO().consultar(rs.getInt("pag_car_id")))); break;
                        case 3: pedidoPagamento.getPagamento().add(new Pagamento(rs.getDouble("pag_valor"), consultaCupomPromocional(rs.getInt("pag_cup_id")))); break;
                    }   
                }
            }
            
            return pedidos;
            
        }catch(SQLException ex){
            System.out.println("N??o foi poss??vel consultar o pedido no banco de dados \nErro:" + ex.getMessage());
        }finally{
            if (ctrlTransacao) ConnectionFactory.closeConnection(conn, stmt, rs);
        }
        return null;
    }

    @Override
    public EntidadeDominio consultar(int pedido_id) {
        
        Pedido pedido = new Pedido();
        
        String sql = "SELECT * FROM PEDIDOS WHERE ped_id = ?";     
        String sqlProdutosPedido = "SELECT * FROM PEDIDOS_PRODUTOS WHERE pep_ped_id = ?";
        String sqlPagamentoPedido = "SELECT * FROM PAGAMENTOS WHERE pag_ped_id = ?";
        
        PreparedStatement stmt = null;
        ResultSet rs = null;        
        
        try{
            if (conn == null || conn.isClosed()) {
                this.conn = ConnectionFactory.getConnection();
                this.ctrlTransacao = true;

            } else {
                this.ctrlTransacao = false;
            }                                   
            
            stmt = conn.prepareStatement(sql);
            stmt.setInt(1, pedido_id);
    
            rs = stmt.executeQuery();
            
            while (rs.next()) {
                pedido = new Pedido();
                
                pedido.setId(rs.getInt("ped_id"));
                pedido.setEstatus(Estatus.pegaEstatusPorValor(UtilsDAO.consultaEstatus(rs.getInt("ped_stt_id"), conn)));               
                pedido.setCliente((Cliente)new ClienteDAO().consultar(rs.getInt("ped_cli_id")));
                pedido.setEndereco((Endereco)new EnderecoDAO().consultar(rs.getInt("ped_end_id")));
                pedido.setValor_total(rs.getDouble("ped_valor_total"));
                pedido.setDt_cadastro(ParameterParser.sqlDateToUtilDate(rs.getDate("ped_dt")));
                
            }
                   
            /*Consulta os produtos desse pedido*/
            stmt = conn.prepareStatement(sqlProdutosPedido);
            stmt.setInt(1, pedido.getId());

            rs = stmt.executeQuery();

            while (rs.next()) {
                pedido.getItens().add(new Item((Teclado)new TecladoDAO().consultar(rs.getInt("pep_tec_id")), rs.getInt("pep_quantidade"), false));
            }
                        
            /*Consulta a forma de pagamento de pedido*/
            stmt = conn.prepareStatement(sqlPagamentoPedido);
            stmt.setInt(1, pedido.getId());

            rs = stmt.executeQuery();

            while (rs.next()) {
                switch (rs.getInt("pag_fpg_id")){
                    case 1: pedido.getPagamento().add(new Pagamento(rs.getDouble("pag_valor"), consultaCupomDeTroca(rs.getInt("pag_cdt_id")))); break;                        
                    case 2: pedido.getPagamento().add(new Pagamento(rs.getDouble("pag_valor"), (CartaoDeCredito)new CartaoDAO().consultar(rs.getInt("pag_car_id")))); break;
                    case 3: pedido.getPagamento().add(new Pagamento(rs.getDouble("pag_valor"), consultaCupomPromocional(rs.getInt("pag_cup_id")))); break;
                } 

            }
                        
            return pedido;
            
        }catch(SQLException ex){
            System.out.println("N??o foi poss??vel consultar o pedido no banco de dados \nErro:" + ex.getMessage());
        }finally{
            if (ctrlTransacao) ConnectionFactory.closeConnection(conn, stmt, rs);
        }
        return null;
    }
    
    
    public CupomDeTroca consultaCupomDeTroca (int id) {
        CupomDeTroca cupom_de_troca = new CupomDeTroca();
        
        String sql = "SELECT * FROM CUPONS_DE_TROCA WHERE cdt_id = ?";
        
        PreparedStatement stmt = null;
        ResultSet rs = null;
             
        try{   
            
            stmt = conn.prepareStatement(sql);
            stmt.setInt(1, id);
            rs = stmt.executeQuery();
            
            while(rs.next()){                               
                cupom_de_troca.setId(rs.getInt("cdt_id"));
                cupom_de_troca.setValor(rs.getDouble("cdt_valor"));
                cupom_de_troca.setValidade(rs.getDate("cdt_validade"));
                cupom_de_troca.setAtivo(rs.getBoolean("cdt_ativo"));
                System.out.println("Cupom ativo:" + rs.getBoolean("cdt_ativo"));
            }
            
            return cupom_de_troca;
        
        }catch(SQLException ex){
            System.out.println("N??o foi poss??vel consultar o cupom de troca no banco de dados \nErro: " + ex.getMessage());
        }       
        return null;
    }
    
    public CupomPromocional consultaCupomPromocional (int id) {
        CupomPromocional cupom_promocional = new CupomPromocional();
        
        String sql = "SELECT * FROM CUPOM_PROMOCIONAL WHERE cup_id = ?";
        
        PreparedStatement stmt = null;
        ResultSet rs = null;
             
        try{   
            
            stmt = conn.prepareStatement(sql);
            stmt.setInt(1, id);
            rs = stmt.executeQuery();
            
            while(rs.next()){                               
                cupom_promocional.setId(rs.getInt("cup_id"));
                cupom_promocional.setPorcentagem_desconto(rs.getInt("cup_porcentagem_desconto"));
                cupom_promocional.setDt_validade(rs.getDate("cup_dt_validade"));
                cupom_promocional.setAtivo(rs.getBoolean("cup_ativo"));                
            }
            
            return cupom_promocional;
        
        }catch(SQLException ex){
            System.out.println("N??o foi poss??vel consultar o cupom promocional no banco de dados \nErro: " + ex.getMessage());
        }       
        return null;
    }    
   
}
