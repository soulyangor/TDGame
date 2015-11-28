/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model.tasks;

import model.actions.findpathalgorithms.Cell;
import model.actions.findpathalgorithms.Logic;
import model.components.Status;
import model.components.TDComponent;
import model.components.Unit;
import model.components.UnitGroup;

/**
 *
 * @author Хозяин
 */
public class MoveTo implements Task {

    private Cell curCell;
    private final int x;
    private final int y;

    private boolean complete = false;

    public MoveTo(double x, double y) {
        this.x = Logic.toInteger(x);
        this.y = Logic.toInteger(y);
    }

    @Override
    public void execute(TDComponent component) {
        if (component instanceof Unit) {
            Unit unit = (Unit) component;
            if (curCell == null) {
                definePath(unit);
                if (curCell == null) {
                    return;
                }
            }

            if ((unit.getStatus() == Status.STAND)
                    && ((Logic.getUnit(curCell) == null)
                    || (Logic.getUnit(curCell).getStatus() == Status.STAND))) {
                definePath(unit);
                if (curCell == null) {
                    complete = true;
                    return;
                }
            }

            move(unit, curCell);
            double r = Math.sqrt(Math.pow(
                    Logic.toDouble(curCell.getX()) - unit.getX(), 2.0)
                    + Math.pow(Logic.toDouble(curCell.getY()) - unit.getY(), 2.0));
            if (r < unit.getSpeed()) {
                curCell = curCell.getParent();
            }

            if (curCell == null) {
                complete = true;
                return;
            }

        } else {
            throw new IllegalArgumentException("Неверный компонент");
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

    // Временные методы для отладки
    public Cell getCurCell() {
        return curCell;
    }

    private void definePath(Unit unit) {
        int ex = Logic.toInteger(unit.getX());
        int ey = Logic.toInteger(unit.getY());
        Logic.setWalkablePlace(unit.getX(), unit.getY());
        curCell = Logic.searchPath(x, y, ex, ey);
    }

    private void move(Unit unit, Cell cell) {
        double ex = Logic.toDouble(cell.getX());
        double ey = Logic.toDouble(cell.getY());
        double ux = unit.getX();
        double uy = unit.getY();
        double speed = unit.getSpeed();
        double angle = Math.atan((ey - uy) / (ex - ux));
        if ((ex - ux) < 0) {
            angle += Math.PI;
        }
        if (angle < 0) {
            angle += 2 * Math.PI;
        }
        unit.setAngle(angle);

        Logic.setWalkablePlace(ux, uy);

        double c0x = ux + Logic.getCellSize() * Math.cos(angle) / 2;
        double c0y = uy + Logic.getCellSize() * Math.sin(angle) / 2;

        if (Logic.getUnit(c0x, c0y) != null) {
            unit.setStatus(Status.STAND);
        } else {
            unit.setStatus(Status.MOVE);
        }

        if (unit.getStatus() == Status.MOVE) {
            unit.setX(ux + speed * Math.cos(angle));
            unit.setY(uy + speed * Math.sin(angle));
        }
        Logic.setUnit(unit);
    }

}
