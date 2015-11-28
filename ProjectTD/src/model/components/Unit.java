/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model.components;

import java.util.Iterator;

/**
 *
 * @author Хозяин
 */
public abstract class Unit extends TDComponent {

    private double x;
    private double y;
    private double angle;
    private double speed;
    private Status status;
    
    public Unit(double x, double y) {
        super();
        this.x = x;
        this.y = y;
        this.status = Status.STAND;
    }
    
    @Override
    public Iterator createIterator(){
        return new NullIterator();
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public double getX() {
        return x;
    }

    public void setX(double x) {
        this.x = x;
    }

    public double getY() {
        return y;
    }

    public void setY(double y) {
        this.y = y;
    }

    public double getAngle() {
        return angle;
    }

    public void setAngle(double angle) {
        this.angle = angle;
    }

    public double getSpeed() {
        return speed;
    }

    public void setSpeed(double speed) {
        this.speed = speed;
    }

}
