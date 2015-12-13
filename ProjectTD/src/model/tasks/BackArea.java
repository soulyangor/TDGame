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
import model.logics.Logic;
import model.logics.Place;
import model.logics.areas.Square;
import model.logics.grids.GameField;
import model.logics.grids.WorldGrid;
import model.units.Insertable;
import model.units.Person;

/**
 *
 * @author Вячеслав
 */
public class BackArea implements Task {

    private static final double MIN_VALUE = 10e-3;

    private Cell curCell;
    private final int x;
    private final int y;

    private Area area = new Square(1);

    private boolean complete = false;

    public BackArea(Cell cell) {
        this.x = cell.getX();
        this.y = cell.getY();
        AreaLogic.setArea(area);
    }

    @Override
    public void execute(TDComponent component) {
        if (component instanceof Person) {
            Person person = (Person) component;
            if (person instanceof Insertable) {
                this.area = ((Insertable) person).getArea();
                AreaLogic.setArea(area);
            }
            if (curCell == null) {
                curCell = definePath(person);
                if (curCell == null) {
                    complete = true;
                    return;
                }
                defineAngle(person, curCell);
            }

            move(person);
            double r = Math.sqrt(Math.pow(GameField.toRealCoordinate(curCell.getX()) - person.getX(), 2.0)
                    + Math.pow(GameField.toRealCoordinate(curCell.getY()) - person.getY(), 2.0));
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
        WorldGrid.getInstance().setCellValue(unit);
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

        for (Place pl : area.getPlaces()) {
            double c0x = pl.getXInc() * WorldGrid.getInstance().getCellSize()
                    + ux + WorldGrid.getInstance().getCellSize() * Math.cos(angle) / 2;
            double c0y = pl.getYInc() * WorldGrid.getInstance().getCellSize()
                    + uy + WorldGrid.getInstance().getCellSize() * Math.sin(angle) / 2;
            if (WorldGrid.getInstance().getCellValue(c0x, c0y) != null) {
                return WorldGrid.getInstance().getCellValue(c0x, c0y);
            }
        }
        return null;
    }

    private void move(Person person) {
        if (getUnitByMove(person) != null) {
            person.setStatus(Status.WAIT);
            ConflictManager.addUnit(person);
            System.out.println(person + ": не топаю");
        } else {
            person.setStatus(Status.MOVE);
            ConflictManager.removeUnit(person);
            System.out.println(person + ": топаю");
        }
        person.move();
        WorldGrid.getInstance().setCellValue(person);
    }

    @Override
    public String toString() {
        return "Задача - отойти в точку (" + x + "," + y + ")";
    }
}
