/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model.units;

import model.components.Unit;

/**
 *
 * @author Хозяин
 */
public class Building extends Unit implements VisibleArea {

    public Building(double x, double y) {
        super(x, y);
    }

    @Override
    public double getViewDistance() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Unit getUnit() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
