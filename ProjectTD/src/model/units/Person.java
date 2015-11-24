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
public class Person extends Unit{

    private double viewDistance;
    private double healPoint;
    
    
    public Person(double x, double y, double speed, double viewDistance, 
            double healPoint) {
        super(x, y);
        super.setSpeed(speed);
        this.viewDistance = viewDistance;
        this.healPoint = healPoint;
    }

    public double getViewDistance() {
        return viewDistance;
    }

    public void setViewDistance(double viewDistance) {
        this.viewDistance = viewDistance;
    }

    public double getHealPoint() {
        return healPoint;
    }

    public void setHealPoint(double healPoint) {
        this.healPoint = healPoint;
    }
    
}
