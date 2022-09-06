/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.keyboard.util;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 *
 * @author Tiago
 */
public class Masks {
    
    public static String brazilianDate(Date data){
        return new SimpleDateFormat("dd/MM/yyyy").format(data);
    }
}
