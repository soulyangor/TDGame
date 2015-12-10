/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model.tasks;

import model.components.Status;
import model.components.TDComponent;
import model.components.Unit;
import model.logics.Area;
import model.logics.AreaLogic;
import model.logics.Cell;
import model.logics.areas.Square;
import model.logics.grids.WorldGrid;
import model.units.Person;

/**
 *
 * @author Sokolov@ivc.org
 */
public class MoveAreaTo implements Task {

    private static final double MIN_VALUE = 10e-3;

    private Cell curCell;
    private final int x;
    private final int y;

    private final Area area = new Square(2);

    private boolean complete = false;

    /**
     * Конструктор. Создаёт экземпляр задачи для движения в точку с координатами
     * (x, y)
     *
     * @param x double
     * @param y double
     */
    public MoveAreaTo(double x, double y) {
        this.x = WorldGrid.getInstance().toCellCoordinate(x);
        this.y = WorldGrid.getInstance().toCellCoordinate(y);
        AreaLogic.setArea(area);
    }

    /**
     * Конструктор. Создаёт экземпляр задачи для движения в точку cell
     *
     * @param cell Cell
     */
    public MoveAreaTo(Cell cell) {
        this.x = cell.getX();
        this.y = cell.getY();
        AreaLogic.setArea(area);
    }

    /**
     * Выполнение задачи для компонента component
     *
     * @param component TDComponent
     */
    @Override
    public void execute(TDComponent component) {
        if (component instanceof Person) {
            Person person = (Person) component;
            if (curCell == null) {
                curCell = definePath(person);
                if (curCell == null) {
                    complete = true;
                    return;
                }
                defineAngle(person, curCell);
            }

            move(person);
            double r = Math.sqrt(Math.pow(WorldGrid.getInstance().toRealCoordinate(curCell.getX()) - person.getX(), 2.0)
                    + Math.pow(WorldGrid.getInstance().toRealCoordinate(curCell.getY()) - person.getY(), 2.0));
            if (r < person.getSpeed()) {
                curCell = curCell.getCell();
                if (curCell != null) {
                    defineAngle(person, curCell);
                }
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
        int ex = WorldGrid.getInstance().toCellCoordinate(unit.getX());
        int ey = WorldGrid.getInstance().toCellCoordinate(unit.getY());
        WorldGrid.getInstance().setWalkablePlace(unit.getX(), unit.getY());
        Cell tmp = AreaLogic.searchPath(x, y, ex, ey);
        WorldGrid.getInstance().setUnit(unit);
        return tmp;
    }

    private void defineAngle(Unit unit, Cell cell) {
        double ex = WorldGrid.getInstance().toRealCoordinate(cell.getX());
        double ey = WorldGrid.getInstance().toRealCoordinate(cell.getY());
        double ux = unit.getX();
        double uy = unit.getY();
        double angle;
        if (Math.abs(ex - ux) < MIN_VALUE) {
            double c = Math.signum(ex - ux);
            double z = Math.signum(ey - uy);
            angle = Math.atan(z / (c * MIN_VALUE));
        } else {
            angle = Math.atan((ey - uy) / (ex - ux));
        }
        if ((ex - ux) < 0) {
            angle += Math.PI;
        }
        if (angle < 0) {
            angle += 2 * Math.PI;
        }
        unit.setAngle(angle);
    }

    private Unit getUnitByMove(Unit unit) {
        double ux = unit.getX();
        double uy = unit.getY();
        double angle = unit.getAngle();
        WorldGrid.getInstance().setWalkablePlace(ux, uy);
        double c0x = ux + WorldGrid.getInstance().getCellSize() * Math.cos(angle) / 2;
        double c0y = uy + WorldGrid.getInstance().getCellSize() * Math.sin(angle) / 2;
        return WorldGrid.getInstance().getUnit(c0x, c0y);
    }

    private void move(Person person) {
        if (getUnitByMove(person) != null) {
            person.setStatus(Status.WAIT);
        } else {
            person.setStatus(Status.MOVE);
        }
        person.move();
        WorldGrid.getInstance().setUnit(person);
    }

    @Override
    public String toString() {
        return "Задача - идти в точку (" + x + "," + y + ")";
    }

}
