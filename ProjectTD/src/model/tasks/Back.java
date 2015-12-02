/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model.tasks;

import model.components.Status;
import model.components.TDComponent;
import model.components.Unit;
import model.logics.Cell;
import model.logics.Logic;

/**
 *
 * @author Хозяин
 */
public class Back implements Task{
    
    private Cell curCell;
    private Cell start;
    private final int x;
    private final int y;

    private boolean complete = false;

    public Back(Cell cell) {
        this.x = cell.getX();
        this.y = cell.getY();
    }

    @Override
    public void execute(TDComponent component) {
        if (component instanceof Unit) {
            Unit unit = (Unit) component;
            if (curCell == null) {
                curCell = definePath(unit);
                if (curCell == null) {
                    complete = true;
                    return;
                }
            }

            move(unit, curCell);
            double r = Math.sqrt(Math.pow(
                    Logic.toRealCoordinate(curCell.getX()) - unit.getX(), 2.0)
                    + Math.pow(Logic.toRealCoordinate(curCell.getY()) - unit.getY(), 2.0));
            if (r < unit.getSpeed()) {
                curCell = curCell.getParent();
            }

            complete = curCell == null;

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

    private Cell definePath(Unit unit) {
        int ex = Logic.toCellCoordinate(unit.getX());
        int ey = Logic.toCellCoordinate(unit.getY());
        Logic.setWalkablePlace(unit.getX(), unit.getY());
        Cell tmp = Logic.searchPath(x, y, ex, ey);
        Logic.setUnit(unit);
        return tmp;
    }

    private void move(Unit unit, Cell cell) {
        double ex = Logic.toRealCoordinate(cell.getX());
        double ey = Logic.toRealCoordinate(cell.getY());
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

        Logic.setWalkablePlace(ux, uy);

        double c0x = ux + Logic.getCellSize() * Math.cos(angle) / 2;
        double c0y = uy + Logic.getCellSize() * Math.sin(angle) / 2;

        if (Logic.getUnit(c0x, c0y) != null) {
            unit.setStatus(Status.WAIT);
        } else {
            unit.setStatus(Status.MOVE);
        }

        if (unit.getStatus() == Status.MOVE) {
            unit.setAngle(angle);
            unit.setX(ux + speed * Math.cos(angle));
            unit.setY(uy + speed * Math.sin(angle));
        }
        Logic.setUnit(unit);
    }
    
    @Override
    public String toString() {
        return "Задача - отойти в точку (" + x + "," + y + ")";
    }
}
