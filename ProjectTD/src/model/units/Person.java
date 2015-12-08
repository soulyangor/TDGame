/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model.units;

import model.components.Status;
import model.components.Unit;

/**
 *
 * @author Хозяин
 */
public class Person extends Unit implements Moveable {

    private double viewDistance;
    private double healPoint;
    private double speed;

    public Person(double x, double y, double speed, double viewDistance,
            double healPoint) {
        super(x, y);
        this.speed = speed;
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

    @Override
    public void move() {
        double ux = super.getX();
        double uy = super.getY();
        double angle = super.getAngle();
        if (super.getStatus() == Status.MOVE) {
            super.setX(ux + speed * Math.cos(angle));
            super.setY(uy + speed * Math.sin(angle));
        }
    }

    @Override
    public void setSpeed(double value) {
        this.speed = value;
    }

    @Override
    public double getSpeed() {
        return speed;
    }

}
