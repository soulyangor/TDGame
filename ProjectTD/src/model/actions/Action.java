/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model.actions;

import model.components.Unit;

/**
 *
 * @author Хозяин
 */
public interface Action {
    
    void act();
    
    void setUnit(Unit unit);
    
}
