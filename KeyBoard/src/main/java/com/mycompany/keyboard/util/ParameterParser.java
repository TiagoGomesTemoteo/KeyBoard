/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.keyboard.util;

import com.mycompany.keyboard.model.domain.Telefone;
import com.mycompany.keyboard.model.domain.enums.Genero;
import com.mycompany.keyboard.model.domain.enums.TelefoneENUM;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 *
 * @author Tiago
 */
public class ParameterParser {
    public static boolean toBoolean(String texto){
        return texto == null || texto.equals("") ? false : Boolean.parseBoolean(texto);
    }
    
    public static int toInt(String texto){
        return texto == null || texto.equals("") ? 0 : Integer.parseInt(texto);
    }

    public static int toInt(Object texto){
        return texto == null ? 0 : Integer.parseInt(texto.toString());
    }
    
    public static double toDouble(String texto){
        return texto == null || texto.equals("") ? 0.0 : Double.parseDouble(texto);
    }
    
    public static Date toDate(String texto){
        if(texto == null || texto.equals("")) return null;
        try {
            return new SimpleDateFormat("dd/MM/yyyy").parse(texto);
        } catch (ParseException ex) {
            return null;
        }
    }
    
    public static java.sql.Date utilDateToSqlDate (Date data){
        return new java.sql.Date(data.getTime());
    }
    
    public static java.util.Date sqlDateToUtilDate (java.sql.Date data){
        return new java.util.Date(data.getTime());
    }
    
    public static Telefone toPhone (String texto){
        
        Telefone tel = new Telefone();
        
        if(texto.trim().length() == 10){
            tel.setTipo(TelefoneENUM.FIXO);
        
        }else{
            tel.setTipo(TelefoneENUM.CELULAR);
        }
        
        tel.setDdd(texto.trim().substring(0, 2));
        tel.setNumero(texto.trim().substring(2));
        
        return tel;
    }
    
    public static Genero getGenSelected(String texto){
        return texto.equals("masculino") ? Genero.MASCULINO : Genero.FEMININO;
    }
}
