/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.keyboard.model.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

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
}
