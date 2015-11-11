/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model.tasks;

import model.components.Component;

/**
 *
 * @author Хозяин
 */
public interface Task {
    
    void execute(Component component);
    
    boolean isComplete();
    
    void notComplete();
    
}
