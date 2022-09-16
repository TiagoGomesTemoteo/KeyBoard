/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.keyboard.model.dao;

import com.mycompany.keyboard.model.domain.EntidadeDominio;
import com.mycompany.keyboard.model.domain.Teclado;
import com.mycompany.keyboard.util.ConnectionFactory;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Tiago
 */
public class TecladoDAO extends AbstractDAO{
    
    private Connection conn;
    
    @Override
    public void salvar(EntidadeDominio entidade) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
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
        Teclado teclado;
        String sql = "SELECT * FROM TECLADOS;";
        
        PreparedStatement stmt = null;
        ResultSet rs = null;
        
        List<Teclado> teclados = new ArrayList();
        
        try{
            this.conn = ConnectionFactory.getConnection();
            stmt = conn.prepareStatement(sql);
            
            rs = stmt.executeQuery();
            
            while(rs.next()){
                
                teclado = new Teclado();
                
                teclado.setId(rs.getInt("tec_id"));
                teclado.setMarca(rs.getString("tec_marca"));
                teclado.setModelo(rs.getString("tec_modelo"));
                teclado.setQtd_teclas(rs.getInt("tec_qtd_teclas"));
                teclado.setPolifonia_max(rs.getInt("tec_polifonia_max"));
                teclado.setPeso(rs.getDouble("tec_peso"));
                teclado.setAltura(rs.getDouble("tec_altura"));
                teclado.setLargura(rs.getDouble("tec_largura"));
                teclado.setComprimento(rs.getDouble("tec_comprimento"));
                teclado.setCor(rs.getString("tec_cor"));
                teclado.setVoltagem(rs.getString("tec_voltagem"));
                teclado.setAtivo(rs.getBoolean("tec_is_ativo"));
                teclado.setGrupo_precificacao(rs.getDouble("tec_grupo_precificacao"));
                teclado.setValor_venda(rs.getDouble("tec_valor_venda"));
                teclado.setValor_custo(rs.getDouble("tec_valor_custo"));
                teclado.setQtd_disponivel(rs.getInt("est_qtd_disponivel"));
                teclado.setQtd_bloqueada(rs.getInt("est_qtd_bloqueada"));

                teclados.add(teclado);
            }
            
            return teclados;
            
        }catch(SQLException ex){
            System.out.println("Não foi possível consultar o teclado no banco de dados \nErro:" + ex.getMessage());
        }finally{
            ConnectionFactory.closeConnection(conn, stmt, rs);
        }
        return null;
    }

    @Override
    public EntidadeDominio consultar(int id) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
