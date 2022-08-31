/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.keyboard.control.command;

import com.mycompany.keyboard.control.Facade;
import com.mycompany.keyboard.control.IFacade;

/**
 *
 * @author Tiago
 */
public abstract class AbstractCommand implements ICommand{
    
    protected IFacade facade = new Facade();
}
