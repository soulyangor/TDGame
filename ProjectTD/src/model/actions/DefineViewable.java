/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model.actions;

import java.util.LinkedList;
import java.util.List;
import model.components.Component;
import model.components.UnitGroup;
import model.components.Unit;
import model.units.Person;

/**
 *
 * @author Хозяин
 */
public class DefineViewable implements Action {

    private Person person;
    private List<Unit> units;
    private UnitGroup group;

    public List<Unit> getUnits() {
        return units;
    }

    public void setGroup(UnitGroup group) {
        this.group = group;
    }

    @Override
    public void setUnit(Unit unit) {
        if (unit instanceof Person) {
            this.person = (Person) unit;
        } else {
            throw new IllegalArgumentException();
        }
    }

    @Override
    public void act() {
        this.units = new LinkedList<>();
        for (Component component : group) {
            if (component == person) {
                continue;
            }
            if (component instanceof Unit) {
                Unit u = (Unit) component;
                if (isViewable(u)) {
                    this.units.add(u);
                }
            }
        }
    }

    private boolean isViewable(Unit unit) {
        double dx = unit.getX() - person.getX();
        if (Math.abs(dx) > person.getViewDistance()) {
            return false;
        }
        double dy = unit.getY() - person.getY();
        if (Math.abs(dy) > person.getViewDistance()) {
            return false;
        }
        double r = Math.sqrt(dx * dx + dy * dy);
        return person.getViewDistance() > r;
    }

}
