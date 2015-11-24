/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model.tasks;

import java.util.ArrayList;
import java.util.List;
import model.actions.findpathalgorithms.FieldMap;
import model.components.TDComponent;
import model.components.Unit;
import model.components.UnitGroup;

/**
 *
 * @author Хозяин
 */
public class FMove implements Task {

    private boolean complete;
    private final double x;
    private final double y;
    private UnitGroup group;

    private static final double DELTA = 2;

    public FMove(double x, double y, UnitGroup group) {
        this.x = x;
        this.y = y;
        this.group = group;
    }

    @Override
    public void execute(TDComponent component) {
        if (component instanceof Unit) {
            Unit unit = (Unit) component;
            double angle;
            double[] ux = new double[8];
            double[] uy = new double[8];
            double[] f = new double[8];
            double f0 = sForce(component, unit.getX(), unit.getY())
                    + force(x, y, unit.getX(), unit.getY());
            for (int i = 0; i < 8; i++) {
                angle = i * Math.PI / 4;
                ux[i] = unit.getX() + unit.getSpeed() * Math.cos(angle);
                uy[i] = unit.getY() + unit.getSpeed() * Math.sin(angle);
                f[i] = sForce(component, ux[i], uy[i]) + force(x, y, ux[i], uy[i]);
                f[i] -= f0;
            }
            int k = 0;
            for (int i = 1; i < 8; i++) {
                if (f[k] < f[i]) {
                    k = i;
                }
            }
            unit.setX(ux[k]);
            unit.setY(uy[k]);
            System.out.println("Сила: " + f[k]);
            unit.setAngle(k * Math.PI / 4);
            double r = Math.sqrt(Math.pow(x - unit.getX(), 2.0)
                    + Math.pow(y - unit.getY(), 2.0));
            complete = r < 2 * DELTA;
            if (complete) {
                System.out.println("dfadf");
            }
        }
    }

    @Override
    public boolean isComplete() {
        return complete;
    }

    @Override
    public void notComplete() {
        this.complete = false;
    }

    private double force(Unit unit, double px, double py) {
        double dx = px - unit.getX();
        double dy = py - unit.getY();
        double r = Math.sqrt(dx * dx + dy * dy);
        return r <= 60 ? r - 60 : 0;
    }

    private double force(double ox, double oy, double px, double py) {
        double dx = px - ox;
        double dy = py - oy;
        double r = Math.sqrt(dx * dx + dy * dy);
        return 1000 - r;
    }

    private double sForce(TDComponent unit, double px, double py) {
        double rez = 0;
        for (TDComponent c : group) {
            if (c == unit) {
                continue;
            }
            if (c instanceof Unit) {
                Unit u = (Unit) c;
                rez += force(u, px, py);
            }
        }
        return rez;
    }

}
