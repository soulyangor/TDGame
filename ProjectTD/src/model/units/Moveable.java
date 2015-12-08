/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model.units;

/**
 *
 * @author Вячеслав
 */
public interface Moveable {
    
    public void move();
    
    public void setSpeed(double value);
    
    public double getSpeed();
    
}
