/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model.actions;

import model.actions.findpathalgorithms.FieldMap;
import model.components.Unit;

/**
 *
 * @author Хозяин
 */
public class Move implements Action {

    private Unit unit;
    private double x;
    private double y;

    public Move() {
    }

    @Override
    public void setUnit(Unit unit) {
        this.unit = unit;
    }

    public void setXY(double x, double y) {
        this.x = x;
        this.y = y;
    }

    @Override
    public void act() {
        double ux = unit.getX();
        double uy = unit.getY();
        double speed = unit.getSpeed();
        double angle = Math.atan((y - uy) / (x - ux));
        if ((x - ux) < 0) {
            angle += Math.PI;
        }
        if (angle < 0) {
            angle += 2 * Math.PI;
        }
        unit.setAngle(angle);
        double c0x = ux + FieldMap.getCellSize() * Math.cos(angle) / 2;
        double c0y = uy + FieldMap.getCellSize() * Math.sin(angle) / 2;
        FieldMap.setWalkablePlace(ux, uy);
        if (FieldMap.isWalkable(c0x, c0y)) {
            unit.setX(ux + speed * Math.cos(angle));
            unit.setY(uy + speed * Math.sin(angle));
        }
        FieldMap.setUnwalkablePlace(unit.getX(), unit.getY());
    }

}
